package com.nesei.minhash.service.impl;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.nesei.minhash.core.HashGenerator;
import com.nesei.minhash.service.HashService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wzy
 * @Date 2016/11/17 17:10
 */
@Service("HashService")
public class HashServiceImpl implements HashService {
    private HashGenerator hashGenerator=new HashGenerator();

    public String String2Shingle(String string) throws Exception {
        String stringsplit = "|";
        int shingleSize = 2;

        //hanlp分词
        List<Term> termList = StandardTokenizer.segment(string);

        StringBuilder strShingles = new StringBuilder();

        for (int listIndex = 0; listIndex < termList.size() - shingleSize; listIndex++) {
            int i = listIndex;
            int num = 0;
            String window = "";

            while (i < termList.size() && num < shingleSize) {
                if(termList.get(i).nature!= Nature.w){
                    window = window + termList.get(i).word;
                    num++;
                }
                i++;
            }

            strShingles.append("|"+window);

        }

        return strShingles.toString().substring(1);
    }

    public String shingle2Minvalue(String shingle) {
//        MinHashGenerator minHashGenerator = new MinHashGenerator();
//        return minHashGenerator.shigle2Minvalue(shingle);
        return hashGenerator.shigle2Minvalue(shingle,500);
    }

    public String shingle2OnePerHash(String shingle, int K, int partitionNum) {
        return hashGenerator.shingle2OnePerHash(shingle,K,partitionNum);
    }
}

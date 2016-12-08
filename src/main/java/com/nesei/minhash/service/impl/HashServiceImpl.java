package com.nesei.minhash.service.impl;

import com.beust.jcommander.internal.Lists;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.nesei.minhash.core.HashGenerator;
import com.nesei.minhash.service.HashService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wzy
 * @Date 2016/11/17 17:10
 */
@Service("HashService")
public class HashServiceImpl implements HashService {

    /** minvalueHash置换次数 */
    private final int K = 1000;

    /**  */
    private HashGenerator hashGenerator = new HashGenerator();

    /**
     *
     * @param string
     * @return
     *
     * @throws Exception
     */
    public String String2Shingle(String string) throws Exception {
        String stringsplit = "|";
        int    shingleSize = 2;

        // hanlp分词
        List<Term>    termList    = StandardTokenizer.segment(string);
        StringBuilder strShingles = new StringBuilder();

        for (int listIndex = 0; listIndex < termList.size() - shingleSize; listIndex++) {
            int    i      = listIndex;
            int    num    = 0;
            String window = "";

            while ((i < termList.size()) && (num < shingleSize)) {
                if (termList.get(i).nature != Nature.w) {
                    window = window + termList.get(i).word;
                    num++;
                }

                i++;
            }

            strShingles.append("|" + window);
        }

        return strShingles.toString();
    }

    /**
     *
     * @param strings
     * @return
     *
     * @throws Exception
     */
    public List<String> String2Shingle(List<String> strings) throws Exception {
        List<String> shingles = Lists.newArrayList();

        if (CollectionUtils.isEmpty(strings)) {
            return shingles;
        }

        for (int i = 0; i < strings.size(); i++) {
            shingles.add(String2Shingle(strings.get(i)));
        }

        return shingles;
    }

    /**
     *
     * @param string
     * @return
     *
     * @throws Exception
     */
    public String string2Minvalue(String string) throws Exception {
        String shingle = String2Shingle(string);

        return shingle2Minvalue(shingle);
    }

    /**
     *
     * @param strings
     * @return
     *
     * @throws Exception
     */
    public List<String> string2Minvalue(List<String> strings) throws Exception {
        List<String> minHashes = Lists.newArrayList();

        if (CollectionUtils.isEmpty(strings)) {
            return minHashes;
        }

        for (int i = 0; i < strings.size(); i++) {
            minHashes.add(string2Minvalue(strings.get(i)));
        }

        return minHashes;
    }

    /**
     *
     * @param shingle
     * @return
     */
    public String shingle2Minvalue(String shingle) {
        return hashGenerator.shigle2Minvalue(shingle, K);
    }

    /**
     *
     * @param strings
     * @return
     *
     * @throws Exception
     */
    public List<String> shingle2Minvalue(List<String> strings) throws Exception {
        List<String> minHashes = Lists.newArrayList();

        if (CollectionUtils.isEmpty(strings)) {
            return minHashes;
        }

        for (int i = 0; i < strings.size(); i++) {
            minHashes.add(shingle2Minvalue(strings.get(i)));
        }

        return minHashes;
    }

    /**
     *
     * @param shingle
     * @param K
     * @param partitionNum
     * @return
     */
    public String shingle2OnePerHash(String shingle, int K, int partitionNum) {
        return hashGenerator.shingle2OnePerHash(shingle, K, partitionNum);
    }

    /**
     *
     * @param shingles
     * @param K
     * @param partitionNum
     * @return
     */
    public List<String> shingle2OnePerHash(List<String> shingles, int K, int partitionNum) {
        List<String> minHashes = Lists.newArrayList();

        if (CollectionUtils.isEmpty(shingles)) {
            return minHashes;
        }

        for (int i = 0; i < shingles.size(); i++) {
            minHashes.add(shingle2OnePerHash(shingles.get(i), K, partitionNum));
        }

        return minHashes;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

package com.nesei.minhash.service.impl;

import com.nesei.minhash.service.CalculateService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author wzy
 * @Date 2016/11/18 20:30
 */
@Service("CalculateService")
public class CalculateServiceImpl implements CalculateService {

    public float calcXlsOfMinhash(String hashA, String hashB) {
        float xslMinwise = 0.0f;
        String[] hashArrayA = hashA.split("\\|");
        String[] hashArrayB = hashB.split("\\|");
        int count = 0;
        int size = Math.min(hashArrayA.length, hashArrayB.length);

        for (int i = 0; i < size; i++) {
            if (!hashArrayA[i].equals("*") && hashArrayA[i].equals(hashArrayB[i])) {
                count++;
            }
        }

        xslMinwise = (float) count / size;
        return xslMinwise;
    }

    public float calcXlsOfOnePerHash(String hashA, String hashB, boolean isEmptyPartitionEqual) {
        float xslMinwise = 0.0f;
        String[] hashArrayA = hashA.split("\\|");
        String[] hashArrayB = hashB.split("\\|");
        int count = 0;
        int size = Math.min(hashArrayA.length, hashArrayB.length);

        for (int i = 0; i < size; i++) {
            if (hashArrayA[i].equals("*")) {//认为空区相等
                //认为空区相等
                if (isEmptyPartitionEqual) {
                    count++;
                }
            } else {//认为空区不相等
                if (hashArrayA[i].equals(hashArrayB[i])) {
                    count++;
                }
            }
        }

        xslMinwise = (float) count / size;
        return xslMinwise;
    }

    public float calcXslOfShingle(String shgA, String shgB) {
        int samenum = 0;
        float xsl = 0.0f;
        String[] arrStrA = shgA.split("\\|");
        String[] arrStrB = shgB.split("\\|");
        int strASize = arrStrA.length;
        int strBSize = arrStrB.length;
        HashMap<String, String> ht1 = new HashMap<String, String>();
        HashMap<String, String> ht2 = new HashMap<String, String>();

        for (int i = 0; i < strASize; i++) {
            String ashg = arrStrA[i];

            if (!ht1.containsKey(ashg)) {
                ht1.put(ashg, ashg);
            }
        }

        for (int i = 0; i < strBSize; i++) {
            String bshg = arrStrB[i];

            if (!ht2.containsKey(bshg)) {
                ht2.put(bshg, bshg);
            }
        }

        for (String bshg : ht2.keySet()) {
            if (ht1.containsKey(bshg)) {
                samenum++;
            }
        }


        xsl = (float) samenum / (float) (ht1.size() + ht2.size() - samenum);

        return xsl;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

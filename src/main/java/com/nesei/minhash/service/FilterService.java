package com.nesei.minhash.service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/12/6 17:03
 */
public interface FilterService {

    /**
     * 按照输入条件得到文本是否被过滤掉
     *
     * @param DCHash
     * @param BDHash
     * @param observationPoints
     * @param TL
     * @param TU
     * @param e
     * @return
     */
    boolean filterMH(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e);

    boolean filterOPH(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e);

    double filterMH(String hashA, String hashB, List<Integer> observationPoints, HashMap<Integer, Double> TLs, HashMap<Integer, Double> TUs);

    double filterMH(String hashA, String hashB, List<Integer> observationPoints, List<Double> TLs, List<Double> TUs);

    double getUpper(int k, double T, double e);

    double getLower(int k, double T, double e);
}


//~ Formatted by Jindent --- http://www.jindent.com

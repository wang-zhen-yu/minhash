package com.nesei.minhash.service;

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
    boolean filter(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e);
}


//~ Formatted by Jindent --- http://www.jindent.com

package com.nesei.minhash.service;

/**
 * @Author wzy
 * @Date 2016/11/18 20:22
 */
public interface CalculateService {

    /**
     * 计算两个minhash串相似度
     *
     * @param hashA
     * @param hashB
     * @return
     */
    float calcXlsOfMinhash(String hashA, String hashB);

    /**
     * 计算两个one permutation hash相似度
     * @param hashA
     * @param hashB
     * @param isEmptyPartitionEqual 两个空区是否认为相等
     * @return
     */
    float calcXlsOfOnePerHash(String hashA, String hashB, boolean isEmptyPartitionEqual);

    /**
     * 计算两个shingle串相似度
     *
     * @param shgA
     * @param shgB
     * @return
     */
    float calcXslOfShingle(String shgA, String shgB);
}


//~ Formatted by Jindent --- http://www.jindent.com

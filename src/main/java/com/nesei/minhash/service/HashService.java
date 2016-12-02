package com.nesei.minhash.service;

/**
 * @Author wzy
 * @Date 2016/11/17 15:37
 */
public interface HashService {

    /**
     * String转换成shingle
     *
     * @param string
     * @return
     *
     * @throws Exception
     */
    String String2Shingle(String string) throws Exception;

    /**
     * shingle转换成minvaluehash
     *
     * @param shingle
     * @return
     */
    String shingle2Minvalue(String shingle);

    /**
     * shingle转换成one permutation hash
     *
     * @param shingle
     * @param K 置换次数
     * @param partitionNum  分区数量
     * @return
     */
    String shingle2OnePerHash(String shingle, int K, int partitionNum);
}


//~ Formatted by Jindent --- http://www.jindent.com

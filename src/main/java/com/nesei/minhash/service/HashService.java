package com.nesei.minhash.service;

import java.util.List;

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
     * @throws Exception
     */
    String String2Shingle(String string) throws Exception;

    /**
     * String列表转换成shingle列表
     *
     * @param strings
     * @return
     * @throws Exception
     */
    List<String> String2Shingle(List<String> strings) throws Exception;

    /**
     * sting转换为minvaluehash
     *
     * @param string
     * @return
     */
    String string2Minvalue(String string) throws Exception;

    /**
     * string列表转换为minvaluehash列表
     *
     * @param string
     * @return
     */
    List<String> string2Minvalue(List<String> string) throws Exception;

    /**
     * shingle列表转换成minvaluehash列表
     *
     * @param shingle
     * @return
     */
    String shingle2Minvalue(String shingle);

    /**
     * shingle列表转换成minvaluehash列表
     *
     * @param strings
     * @return
     * @throws Exception
     */
    List<String> shingle2Minvalue(List<String> strings) throws Exception;

    /**
     * shingle转换成one permutation hash
     *
     * @param shingle
     * @param K            置换次数
     * @param partitionNum 分区数量
     * @return
     */
    String shingle2OnePerHash(String shingle, int K, int partitionNum);

    /**
     * shingle列表转换成one permutation hash列表
     *
     * @param shingle
     * @param K
     * @param partitionNum
     * @return
     */
    List<String> shingle2OnePerHash(List<String> shingle, int K, int partitionNum);
}


//~ Formatted by Jindent --- http://www.jindent.com

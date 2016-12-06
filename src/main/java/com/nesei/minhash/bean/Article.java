package com.nesei.minhash.bean;

/**
 * @Author wzy
 * @Date 2016/12/6 17:09
 */
public class Article {

    /**  */
    private String filePath;

    /**  */
    private String context;

    /**  */
    private String shingle;

    /**  */
    private String minHash;

    /**  */
    private String onePermHash;

    /**
     * @return
     */
    public String getOnePermHash() {
        return onePermHash;
    }

    /**
     * @param onePermHash
     */
    public void setOnePermHash(String onePermHash) {
        this.onePermHash = onePermHash;
    }

    /**
     * @return
     */
    public String getMinHash() {
        return minHash;
    }

    /**
     * @param minHash
     */
    public void setMinHash(String minHash) {
        this.minHash = minHash;
    }

    /**
     * @return
     */
    public String getShingle() {
        return shingle;
    }

    /**
     * @param shingle
     */
    public void setShingle(String shingle) {
        this.shingle = shingle;
    }

    /**
     * @return
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

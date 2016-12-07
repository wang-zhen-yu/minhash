package com.nesei.minhash.core;

import com.planetj.math.rabinhash.RabinHashFunction32;
import org.aspectj.lang.annotation.Aspect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author wzy
 * @Date 2016/11/20 16:01
 */
@Aspect
public class HashGenerator {

    /**  */
    private static double num = Math.pow(2, 17);

    /**
     * 映射文件总数量
     */
    private static int K_All = 1000;

    /**  */
    private static String[] pers = loadPer();

    /**
     * shingle生成minvaluehash
     *
     * @param shingle
     * @param K
     * @return
     */
    public String shigle2Minvalue(String shingle, int K) {
        int[]  hashcode  = shgs2HashCode(shingle);
        String minValues = "";

        for (int i = 0; i < K; i++) {
            int[] mappedHash = mapHash(pers[i], hashcode);
            int   minValue   = findMinFromMappedHash(mappedHash);

            minValues += minValue + "|";
        }

        return minValues;
    }

    /**
     * @param shingle
     * @param K 置换次数
     * @param partitionNum  分区数量
     * @return
     */
    public String shingle2OnePerHash(String shingle, int K, int partitionNum) {
        StringBuilder onePerHash=new StringBuilder();

        int[] hashcode = shgs2HashCode(shingle);

        // 进行K次映射
        for (int i = 0; i < K; i++) {
            hashcode = mapHash(pers[i], hashcode);
        }

        //矩阵化：将置换结果二进制矩阵化
        int[] allSet=new int[(int)num];
        for(int i=0;i<hashcode.length;i++){
            allSet[hashcode[i]]++;
        }

        int partitionLength=(int)Math.ceil(num/partitionNum);//各分区长度
        //分区
        for(int i=0;i<partitionNum;i++){
            int min=(int)num+1;
            int index=partitionLength*i;
            boolean isEmptyPartition=true;//是否为空区

            while(index<(partitionLength*i+partitionLength)&&index<num){
                if(allSet[index]>0){
                    onePerHash.append(index-partitionLength*i).append("|");
                    isEmptyPartition=false;
                    break;
                }
                index++;
            }
            //空区，设为*
            if(isEmptyPartition){
                onePerHash.append("*").append("|");
            }

        }
        return  onePerHash.toString();
    }

    /**
     * 向各个方向映射哈希值
     *
     * @param strPer
     * @param array_hashCode
     * @return
     */
    private int[] mapHash(String strPer, int[] array_hashCode) {
        int[] mappedHash = new int[array_hashCode.length];
        int   size       = array_hashCode.length;

        for (int i = 0; i < size; i++) {
            int data = array_hashCode[i];

            mappedHash[i] = Integer.parseInt(strPer.substring(data * 6, data * 6 + 6));
        }

        return mappedHash;
    }

    /**
     * 从hash值中找出minhash
     *
     * @param mappedHash
     * @return
     */
    private int findMinFromMappedHash(int[] mappedHash) {
        int min = (int) num + 1;

        for (int i = 0; i < mappedHash.length; i++) {
            int value = mappedHash[i];

            if (value < min) {
                min = value;
            }
        }

        return min;
    }

    /**
     * 将shingle字符串变成hash值数组，个数为shingle个数
     *
     * @param shgs shingle字符串，|分割
     * @return
     */
    private int[] shgs2HashCode(String shgs) {
        String[] array_shgs     = shgs.split("\\|");
        int      needSize       = array_shgs.length;
        int[]    array_hashCode = new int[needSize];

        for (int i = 0; i < needSize; i++) {
            array_hashCode[i] = string2Hash(array_shgs[i]);
        }

        return array_hashCode;
    }

    /**
     * 将文本变成rabinhash值
     *
     * @param str
     * @return
     */
    private int string2Hash(String str) {
        RabinHashFunction32 rabin = RabinHashFunction32.DEFAULT_HASH_FUNCTION;
        int                 hash  = (int) (Math.abs(rabin.hash(str)) % num);

        return hash;
    }

    /**
     * 加载所有minvaluehash投影文件
     *
     * @return
     */
    private static String[] loadPer() {
        String[] pers = new String[K_All];

        try {
            for (int i = 0; i < K_All; i++) {
                pers[i] = loadPer(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pers;
    }

    /**
     * 加载minvaluehash投影文件k
     *
     * @param k
     * @return
     */
    private static String loadPer(int k) {
        String          str_per = null;
        FileInputStream f       = null;

        try {
            File file = new File(getRoot() + "/p" + k + ".txt");

            f = new FileInputStream(file);

            byte[] buf = new byte[f.available()];

            f.read(buf, 0, f.available());
            str_per = new String(buf);
            ;
        } catch (Exception e) {
            System.out.println("加载minvaluehash投影文件k出现异常：" + e);
        } finally {
            try {
                f.close();
            } catch (IOException e) {
                System.out.println("关闭加载minvaluehash投影文件k出现异常：" + e);
            }
        }

        return str_per;
    }

    /**
     * 获取系统目录
     *
     * @return
     */
    private static String getRoot() {
        return System.getProperty("user.dir") + "/src/main/resources/data" + File.separator + "permer" + File.separator;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

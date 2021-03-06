package com.nesei.minhash.core;

import com.nesei.minhash.service.CalculateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/11/20 19:10
 */
@Component
public class Filter {
    @Autowired
    private CalculateService calculateService;

    public double filterMH(String hashA, String hashB, List<Integer> observationPoints, HashMap<Integer,Double> TLs, HashMap<Integer,Double> TUs) {
        if (CollectionUtils.isEmpty(observationPoints)) {
            throw new RuntimeException("观测点列表为空！");
        }

        String[] hashArrayA = hashA.split("\\|");
        String[] hashArrayB = hashB.split("\\|");
        int count = 0;
        int size = Math.min(hashArrayA.length, hashArrayB.length);

        for (int i = 0; i < size; i++) {
            if (hashArrayA[i].equals(hashArrayB[i])) {
                count++;
            }

            if(observationPoints.contains(i)){
                double TL=TLs.get(i);
                double TU=TUs.get(i);
                double tmpXsl=count/i;

                if(tmpXsl<TL||tmpXsl>TU){
                    return i;
                }
            }
        }

        return (double)count/size;
    }

    public double filterMH2(String hashA, String hashB, List<Integer> observationPoints, List<Double> TLs, List<Double> TUs) {
        if (CollectionUtils.isEmpty(observationPoints)) {
            throw new RuntimeException("观测点列表为空！");
        }

        String[] hashArrayA = hashA.split("\\|");
        String[] hashArrayB = hashB.split("\\|");
        int count = 0;
        int lastObs=0;
        int size = Math.min(hashArrayA.length, hashArrayB.length);

        //遍历观测点
        for(int i=0;i<observationPoints.size();i++){
            int obs=observationPoints.get(i);
            //遍历从上个观测点到现在这个观测点
            for(int j=lastObs;j<obs;j++){
                if (hashArrayA[j].equals(hashArrayB[j])) {
                    count++;
                }
            }
            lastObs=obs;
            double TL=TLs.get(i);
            double TU=TUs.get(i);
            double tmpXsl=count/obs;

            //判断当前观测点是否过滤
            if(tmpXsl<TL||tmpXsl>TU){
                return obs;
            }
        }

        //遍历从最后一个观测点到结尾
        for(int i=lastObs;i<size;i++){
            if (hashArrayA[i].equals(hashArrayB[i])) {
                count++;
            }
        }

        return (double)count/size;
    }

    public boolean filterMH(String hashA, String hashB, List<Integer> observationPoints, double TL, double TU, double e) {
        if (CollectionUtils.isEmpty(observationPoints)) {
            throw new RuntimeException("观测点列表为空！");
        }

        double tmpTL = 0, tmpTU = 1;

        for (int i = 0; i < observationPoints.size(); i++) {
            //现在所在观测点
            int k = observationPoints.get(i);

            tmpTU = getUpper(k, TU, e);
            tmpTL = getLower(k, TL, e);
            double xsl = calculateService.calcXlsOfMinhash(hashA.substring(0, k), hashB.substring(0, k));

            //如果相似率大于上阈值或者小于下阈值
            if (xsl > tmpTU || xsl < tmpTL) return false;
        }
        return true;
    }

    public boolean filterOPH(String hashA, String hashB, List<Integer> observationPoints, double TL, double TU, double e) {
        if (CollectionUtils.isEmpty(observationPoints)) {
            throw new RuntimeException("观测点列表为空！");
        }

        double tmpTL = 0, tmpTU = 1;

        for (int i = 0; i < observationPoints.size(); i++) {
            //现在所在观测点
            int k = observationPoints.get(i);

            tmpTU = getUpper(k, TU, e);
            tmpTL = getLower(k, TL, e);
            double xsl = calculateService.calcXlsOfOnePerHash(hashA.substring(0, k), hashB.substring(0, k), false);

            //如果相似率大于上阈值或者小于下阈值
            if (xsl > tmpTU || xsl < tmpTL) return false;
        }
        return true;
    }

    /**
     * 计算上界阈值
     *
     * @param k 观测点所在位置，比如第一个观测点在第100次，那k就是100
     * @param T 下阈值
     * @param e 最小概率
     * @return
     */
    public double getUpper(int k, double T, double e) {
        int x = k + 1;
        double totalProbability = 0;

        while ((x > 0) && (totalProbability < e)) {
            x--;
            totalProbability += getProbability(k, x, T);
        }

        return x / k;
    }

    /**
     * 计算下界阈值
     *
     * @param k 观测点所在位置，比如第一个观测点在第100次，那k就是100
     * @param T 下阈值
     * @param e 最小概率
     * @return
     */
    public double getLower(int k, double T, double e) {
        int i = -1;
        double totalProbability = 0;

        while ((i < k) && (totalProbability < e)) {
            i++;
            totalProbability += getProbability(k, i, T);
        }

        return (double) i / k;
    }

    /**
     * 计算k次中得到x的概率
     *
     * @param k
     * @param i
     * @param T
     * @return
     */
    private static double getProbability(int k, int i, double T) {
        double probability = 0;

        probability = take(k, i) * Math.pow(T, i) * Math.pow((1 - T), (k - i));

        return probability;
    }


    /**
     * C(n,m)
     *
     * @param n 总数
     * @param m 取出个数
     * @return
     */
    public static double take(int n, int m) {
        double r = 1;

        for (int i = 0; i < m; i++) {
            r = r * (n - i) / (m - i);
        }

        return r;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

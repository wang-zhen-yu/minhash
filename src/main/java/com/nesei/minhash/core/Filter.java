package com.nesei.minhash.core;

/**
 * @Author wzy
 * @Date 2016/11/20 19:10
 */
public class Filter {
//    public boolean filterHash(int K, double upper, double lower, List<Integer> observationList){
//
//    }

    public static void main(String[] args) throws Exception {
        double result = 0;
        int m = 60;
        int total = 100;

        double upper=getUpper(total,m,0.3);
        System.out.println(result);
    }

    public static double getUpper(int total, int x, double T) {
        double result=0;
        for (int i = 0; i < x; i++) {
            result +=  F(total, x) * Math.pow(T, x) * Math.pow((1 - T), (total - x));
        }
        return result;
    }

    public static double getLower(int total, int x, double T) {
        double result=0;
        for (int i = x; i < total; i++) {
            result +=  F(total, x) * Math.pow(T, x) * Math.pow((1 - T), (total - x));
        }
        return result;
    }

    public static double F(int n, int m) {
        double r = 1;
        for (int i = 0; i < m; i++) {
            r = r * (n - i) / (m - i);
        }
        return r;
    }
}

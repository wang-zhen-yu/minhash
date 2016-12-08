package com.nesei.minhash.service.impl;

import com.nesei.minhash.core.Filter;
import com.nesei.minhash.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/12/6 17:07
 */
@Service("FilterService")
public class FilterServiceImpl implements FilterService {
    @Autowired
    private Filter filter;

    public boolean filterMH(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e) {
        return filter.filterMH(DCHash, BDHash, observationPoints, TL, TU, e);
    }

    public boolean filterOPH(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e) {
        return filter.filterOPH(DCHash, BDHash, observationPoints, TL, TU, e);
    }

    public double filterMH(String hashA, String hashB, List<Integer> observationPoints, HashMap<Integer, Double> TLs, HashMap<Integer, Double> TUs) {
        return filter.filterMH(hashA,hashB,observationPoints,TLs,TUs);
    }

    public double filterMH(String hashA, String hashB, List<Integer> observationPoints, List<Double> TLs, List<Double> TUs) {
        return filter.filterMH2(hashA,hashB,observationPoints,TLs,TUs);
    }

    public double getUpper(int k, double T, double e) {
        return filter.getUpper(k, T, e);
    };

    public double getLower(int k, double T, double e) {
        return filter.getLower(k, T, e);
    };
}

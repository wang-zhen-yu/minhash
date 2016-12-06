package com.nesei.minhash.service.impl;

import com.nesei.minhash.core.Filter;
import com.nesei.minhash.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wzy
 * @Date 2016/12/6 17:07
 */
@Service("FilterService")
public class FilterServiceImpl implements FilterService {
    @Autowired
    private Filter filter;

    public boolean filter(String DCHash, String BDHash, List<Integer> observationPoints, double TL, double TU, double e) {
        return filter.filterHash(DCHash, BDHash, observationPoints, TL, TU, e);
    }
}

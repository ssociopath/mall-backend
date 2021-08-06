package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.CsmInf;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * @author bobo
 * @date 2021/8/6
 */

public interface CsmInfRepository extends DataRepository<CsmInf, Integer> {
    CsmInf findByCustomerId(Integer customerId);
}

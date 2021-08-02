package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.PunchIn;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.Optional;

/**
 * @author bobo
 * @date 2021/4/20
 */

public interface PunchInRepository extends DataRepository<PunchIn, Integer> {
    Optional<PunchIn> findPunchInByCustomerIdAndYearAndMonth(Integer customerId, Integer year, Integer month);
}

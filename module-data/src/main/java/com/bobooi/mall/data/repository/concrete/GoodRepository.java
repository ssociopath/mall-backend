package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.Good;
import com.bobooi.mall.data.repository.DataRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author bobo
 * @date 2021/8/3
 */

public interface GoodRepository  extends DataRepository<Good, Integer> {

    @Modifying
    @Query(value = "update good set stock=stock-1 where good_id=:id and stock>0",nativeQuery=true)
    int deductStock(@Param("id") Integer id);

    @Query(value = "select stock from good WHERE good_id=:id for update",nativeQuery=true)
    int getStockByGoodId(@Param("id") Integer id);


    @Modifying
    @Query(value = "delete from good WHERE good_id=:id",nativeQuery=true)
    void deleteByGoodId(@Param("id") Integer id);

    @Modifying
    @Query(value = "update good set stock=0 where good_id=:id",nativeQuery=true)
    void updateStockByGoodId(@Param("id") Integer id);

}

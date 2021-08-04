package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.entity.PdtType;
import com.bobooi.mall.data.repository.concrete.PdtInfoRepository;
import com.bobooi.mall.data.repository.concrete.PdtTypeRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Service
@Slf4j
public class GoodsService extends BaseDataService<PdtInf,Integer> {
    @Resource
    PdtTypeRepository pdtTypeRepository;

    @Resource
    PdtInfoRepository pdtInfoRepository;

    public List<PdtType> getProductCategory(){
        return pdtTypeRepository.findAll();
    }

    public List<PdtInf> getAllPdtInfByCategoryId(Integer categoryId){
        return pdtInfoRepository.findAllByCategoryId(categoryId);
    }

    public PdtInf getPdtInfByPdtId(Integer productId){
        return pdtInfoRepository.findByProductId(productId);
    }
}

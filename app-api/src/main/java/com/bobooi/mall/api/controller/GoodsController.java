package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.GoodsVO;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.data.repository.concrete.SupplierInfoRepository;
import com.bobooi.mall.data.service.concrete.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@CrossOrigin("*")
@Api(tags = "商品信息")
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    SupplierInfoRepository supplierInfoRepository;

    @Resource
    GoodsService goodsService;

    /**
     * 获取所有商品分类
     *
     * @return 商品分类列表
     */
    @ApiOperation("获取所有商品分类列表")
    @GetMapping("/category")
    public ApplicationResponse getAllProductCategories() {
        return ApplicationResponse.succeed(goodsService.getProductCategory());
    }

    /**
     * 根据商品分类id获取所有商品数据（未分页）
     *
     * @return k
     */
    @ApiOperation("根据商品分类id获取所有商品展示数据")
    @GetMapping("/allProductInfo")
    public ApplicationResponse getAllProductInfoByCategoryId(Integer categoryId) {
        return ApplicationResponse.succeed(goodsService.getAllPdtInfByCategoryId(categoryId)
                .stream()
                .map(pdtInfo ->
                        GoodsVO.fromPdtInfAndSupplierInf(pdtInfo,
                                supplierInfoRepository.findBySupplierId(pdtInfo.getSupplierId()))
                ).collect(Collectors.toList())
        );
    }
}

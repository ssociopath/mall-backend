package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.GoodsVO;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.data.entity.PdtCategory;
import com.bobooi.mall.data.entity.PdtDetailInf;
import com.bobooi.mall.data.entity.SupplierInf;
import com.bobooi.mall.data.repository.concrete.SupplierInfoRepository;
import com.bobooi.mall.data.service.concrete.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
    public ApplicationResponse<List<PdtCategory>> getAllProductCategories() {
        return ApplicationResponse.succeed(goodsService.getProductCategory());
    }

    /**
     * 根据商品分类id获取所有商品数据（未分页）
     *
     * @return
     */
    @ApiOperation("根据商品分类id获取所有商品展示数据")
    @GetMapping("/allProductInfo/{categoryId}")
    public ApplicationResponse<List<GoodsVO>> getAllProductInfoByCategoryId(@PathVariable Integer categoryId) {
        return ApplicationResponse.succeed(goodsService.getAllPdtInfByCategoryId(categoryId)
                .stream()
                .map(pdtInfo ->
                        GoodsVO.fromPdtInfAndSupplierInf(pdtInfo,
                                supplierInfoRepository.findBySupplierId(pdtInfo.getSupplierId()))
                ).collect(Collectors.toList())
        );
    }

    /**
     * 根据商品id获取商品详细数据
     *
     * @return
     */
    @ApiOperation("根据商品id获取商品详细数据")
    @GetMapping("/productDetailInfo/{productId}")
    public ApplicationResponse<PdtDetailInf> getPdtDetailInfoByProductId(@PathVariable Integer productId) {
        return ApplicationResponse.succeed(goodsService.getPdtDetailInfoByPdtId(productId));
    }

    /**
     * 获取所有商品详细数据
     *
     * @return
     */
    @ApiOperation("获取所有商品详细数据")
    @GetMapping("/allProductDetailInfo")
    public ApplicationResponse<List<PdtDetailInf>> getAllPdtDetailInfo() {
        return ApplicationResponse.succeed(goodsService.getAllPdtDetailInfo());
    }

    /**
     * 获取所有供应商数据给管理员
     *
     * @return
     */
    @ApiOperation("获取所有供应商数据给管理员")
    @GetMapping("/allSupplierInfo")
    public ApplicationResponse<List<SupplierInf>> getAllSupplierInfo() {
        return ApplicationResponse.succeed(goodsService.getAllSupplierInfo());
    }


}

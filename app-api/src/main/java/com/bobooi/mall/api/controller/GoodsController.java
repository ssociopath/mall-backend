package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.GoodsVO;
import com.bobooi.mall.api.module.vo.PdtDetailVO;
import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.entity.*;
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
     * 获取所有供应商列表
     *
     * @return 供应商列表
     */
    @ApiOperation("获取所有供应商列表")
    @GetMapping("/allSupplierInfo")
    public ApplicationResponse<List<SupplierInf>> getAllSupplierInfo() {
        return ApplicationResponse.succeed(goodsService.getAllSupplierInfo());
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
    public ApplicationResponse<PdtDetailVO> getPdtDetailInfoByProductId(@PathVariable Integer productId) {
        return ApplicationResponse.succeed(
                PdtDetailVO.fromPdtDetailInfList(
                        goodsService.getPdtDetailInfoByPdtId(productId)
                ));
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
     * 删除商品
     *
     * @return
     */
    @ApiOperation("删除商品")
    @DeleteMapping("/deleteProduct/{productId}")
    public ApplicationResponse<Boolean> deleteProduct(@PathVariable Integer productId) {
        PdtInf pdtInf = goodsService.getOneOr(productId, null);
        if (null == pdtInf) {
            return ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG, "商品不存在");
        }
        goodsService.deleteAllProductInfo(productId);
        return ApplicationResponse.succeed("删除商品成功");
    }

    /**
     * 修改商品基本信息product_info表
     *
     * @return 返回处理信息
     */
    @ApiOperation("修改商品基本信息")
    @PostMapping("/editProductInfo")
    public ApplicationResponse<CartGoods> addCartGoods(Integer productId, String productName, String description, String picUrl, Float price, Integer inventory) {
        AssertUtils.notNull(productId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productName, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(description, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(picUrl, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(price, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(inventory, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return goodsService.updateProductInfo(productId, productName, description, picUrl,price,inventory)
                ?ApplicationResponse.succeed("修改商品信息成功")
                :ApplicationResponse.fail(SystemCodeEnum.FAILURE,"修改商品信息失败");
    }
}

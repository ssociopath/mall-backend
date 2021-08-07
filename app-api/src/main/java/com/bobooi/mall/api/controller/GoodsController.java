package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.GoodsVO;
import com.bobooi.mall.api.module.vo.PdtDetailVO;
import com.bobooi.mall.api.module.vo.SecGoodsVO;
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
import java.sql.Timestamp;
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
     * 增加商品分类
     *
     * @return 商品分类实体
     */
    @ApiOperation("增加商品分类")
    @PostMapping("/category/add")
    public ApplicationResponse<PdtCategory> addProductCategory(@RequestParam String categoryName) {
        return ApplicationResponse.succeed("新增商品分类成功", goodsService.addProductCategory(categoryName));
    }

    /**
     * 删除商品分类信息
     *
     * @return
     */
    @ApiOperation("删除商品分类信息")
    @DeleteMapping("/category/{categoryId}")
    public ApplicationResponse deleteProductCategory(@PathVariable Integer categoryId) {
        AssertUtils.notNull(categoryId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        goodsService.deleteProductCategory(categoryId);
        return ApplicationResponse.succeed("商品分类删除成功");
    }

    /**
     * 修改商品分类信息
     *
     * @return 商品分类实体
     */
    @ApiOperation("修改商品分类信息")
    @PostMapping("/category/update")
    public ApplicationResponse<PdtCategory> updateProductCategory(PdtCategory pdtCategory) {
        AssertUtils.notNull(pdtCategory, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtCategory.getCategoryId(), new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtCategory.getCategoryName(), new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return ApplicationResponse.succeed("商品分类修改成功", goodsService.updateProductCategory(pdtCategory));
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
     * 增加商品供应商信息
     *
     * @return 供应商实体
     */
    @ApiOperation("增加商品供应商信息")
    @PostMapping("/supplier/add")
    public ApplicationResponse<SupplierInf> addSupplier(@RequestParam String supplierName,@RequestParam String address) {
        return ApplicationResponse.succeed("新增供应商信息成功", goodsService.addSupplierInfo(supplierName,address));
    }

    /**
     * 删除供应商信息
     *
     * @return
     */
    @ApiOperation("删除供应商信息")
    @DeleteMapping("/supplier/{supplierId}")
    public ApplicationResponse deleteSupplierInfo(@PathVariable Integer supplierId) {
        AssertUtils.notNull(supplierId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        goodsService.deleteSupplierInfo(supplierId);
        return ApplicationResponse.succeed("供应商信息删除成功");
    }

    /**
     * 修改供应商信息
     *
     * @return 供应商信息实体
     */
    @ApiOperation("修改供应商信息")
    @PostMapping("/supplier/update")
    public ApplicationResponse<SupplierInf> updateSupplierInfo(SupplierInf supplierInf) {
        AssertUtils.notNull(supplierInf, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(supplierInf.getSupplierId(), new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(supplierInf.getSupplierName(), new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(supplierInf.getAddress(), new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return ApplicationResponse.succeed("供应商信息修改成功", goodsService.updateSupplierInf(supplierInf));
    }

    /**
     * 根据商品分类id获取所有商品数据（未分页）
     *
     * @return 该分类下所有商品展示数据【不含秒杀商品】
     */
    @ApiOperation("根据商品分类id获取所有商品展示数据【不含秒杀商品】")
    @GetMapping("/allProductInfo/{categoryId}")
    public ApplicationResponse<List<GoodsVO>> getAllProductInfoByCategoryId(@PathVariable Integer categoryId) {
        return ApplicationResponse.succeed(
                goodsService.getAllPdtInfByCategoryId(categoryId)
                        .stream()
                        .filter(pdfInfo -> pdfInfo.getRestTime() == null)
                        .map(pdtInfo -> GoodsVO.fromPdtInfAndSupplierInf(pdtInfo, supplierInfoRepository.findBySupplierId(pdtInfo.getSupplierId()))
                        ).collect(Collectors.toList())
        );
    }

    /**
     * "获取所有秒杀商品展示数据
     *
     * @return 所有秒杀商品展示数据
     */
    @ApiOperation("获取所有秒杀商品展示数据")
    @GetMapping("/allSecProductInfo")
    public ApplicationResponse<List<GoodsVO>> getAllSecProductInfo() {
        return ApplicationResponse.succeed(
                goodsService.findAll()
                        .stream()
                        .filter(pdfInfo -> pdfInfo.getRestTime() != null)
                        .map(pdtInfo -> GoodsVO.fromPdtInfAndSupplierInf(pdtInfo, supplierInfoRepository.findBySupplierId(pdtInfo.getSupplierId())))
                        .collect(Collectors.toList())
        );
    }

    /**
     * 根据商品id获取商品详细数据
     *
     * @return 商品详细数据
     */
    @ApiOperation("根据商品id获取商品详细数据")
    @GetMapping("/productDetailInfo/{productId}")
    public ApplicationResponse<PdtDetailVO> getPdtDetailInfoByProductId(@PathVariable Integer productId) {
        return ApplicationResponse.succeed(PdtDetailVO.fromPdtDetailInfList(goodsService.getPdtDetailInfoByPdtId(productId)));
    }

    /**
     * 获取所有商品详细数据
     *
     * @return 所有商品详细数据
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
    public ApplicationResponse deleteProduct(@PathVariable Integer productId) {
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
    public ApplicationResponse updateProductInfo(Integer productId, String productName, String description, String picUrl, Float price, Integer inventory, Timestamp restTime) {
        AssertUtils.notNull(productId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productName, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(description, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(picUrl, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(price, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(inventory, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return goodsService.updateProductInfo(productId, productName, description, picUrl, price, inventory,restTime)
                ? ApplicationResponse.succeed("修改商品信息成功")
                : ApplicationResponse.fail(SystemCodeEnum.FAILURE, "修改商品信息失败");
    }

    /**
     * 添加商品
     *
     * @return 返回处理信息
     */
    @ApiOperation("添加商品")
    @PostMapping("/addProduct")
    public ApplicationResponse addProduct(PdtInf pdtInf,Integer productTypeId) {
        AssertUtils.isNull(pdtInf.getProductId(),new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"新增商品id应为空"));
        AssertUtils.notNull(pdtInf.getProductName(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtInf.getCategoryId(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtInf.getSupplierId(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtInf.getPrice(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtInf.getDescription(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(pdtInf.getInventory(),new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productTypeId,new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return goodsService.addProduct(pdtInf,productTypeId)
                ? ApplicationResponse.succeed("添加商品成功")
                : ApplicationResponse.fail(SystemCodeEnum.FAILURE, "添加商品失败");
    }
}

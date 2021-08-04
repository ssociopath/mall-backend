package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.CartGoodsVO;
import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.entity.CartGoods;
import com.bobooi.mall.data.entity.CsmLogin;
import com.bobooi.mall.data.repository.concrete.CartGoodsRepository;
import com.bobooi.mall.data.service.concrete.CartGoodsService;
import com.bobooi.mall.data.service.concrete.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bobo
 * @date 2021/8/4
 */

@CrossOrigin("*")
@Api(tags = "购物车管理")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartGoodsService cartGoodsService;
    @Resource
    private UserService userService;

    /**
     * 根据用户id获取购物车
     * @return 用户购物车信息
     */
    @ApiOperation("获取当前用户购物车内容")
    @GetMapping
    public ApplicationResponse<List<CartGoodsVO>> getUserCart() {
        CsmLogin csmLogin = userService.info();
        if(csmLogin ==null){
            return ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG, "用户不存在！");
        }
        return ApplicationResponse.succeed(cartGoodsService.getCartGoodsList(csmLogin.getCustomerId())
                .stream().map(CartGoodsVO::fromCartGoodsBO).collect(Collectors.toList()));
    }

    /**
     * 添加商品至获取购物车
     * @return 返回处理信息
     */
    @ApiOperation("添加商品至获取购物车")
    @PostMapping("/add")
    public ApplicationResponse<CartGoods> addCartGoods(Integer productId, Integer productAmount,  Integer productTypeId) {
        Integer customerId = userService.info().getCustomerId();
        AssertUtils.notNull(customerId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productAmount, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productTypeId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        return ApplicationResponse.succeed("添加至购物车成功",cartGoodsService.addCartGoods(customerId, productId, productAmount, productTypeId));
    }
}
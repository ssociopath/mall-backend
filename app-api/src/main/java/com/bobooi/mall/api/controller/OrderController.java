package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.data.dto.BatchOperationResultDTO;
import com.bobooi.mall.data.entity.OrderMaster;
import com.bobooi.mall.data.entity.PdtCategory;
import com.bobooi.mall.data.service.concrete.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bobo
 * @date 2021/8/6
 */

@CrossOrigin("*")
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation("获取所有订单信息【需要管理员】")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    @GetMapping("/all")
    public ApplicationResponse<List<OrderMaster>> getAllOrders() {
        return ApplicationResponse.succeed(orderService.findAll());
    }

    @ApiOperation("获取当前用户订单信息")
    @GetMapping
    public ApplicationResponse<List<OrderMaster>> getAllOrdersByUser() {
        return ApplicationResponse.succeed(orderService.findAllByCustomerId());
    }

    @ApiOperation("根据购物车商品id列表生成订单")
    @PostMapping("/generate")
    public ApplicationResponse<BatchOperationResultDTO<String>> generateOrder(Integer[] cartGoodsIds, Integer customerAddrId, Integer point) {
        return ApplicationResponse.succeed(orderService.addOrders(cartGoodsIds, customerAddrId, point));
    }
}

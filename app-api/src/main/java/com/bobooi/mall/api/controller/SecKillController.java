package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.service.concrete.GoodService;
import com.bobooi.mall.data.service.concrete.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author bobo
 * @date 2021/8/3
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/secKill")
public class SecKillController {
    @Resource
    private OrderService orderService;

    @GetMapping("/init")
    public ApplicationResponse<String> initOrder(){
        orderService.initRedis(1);
        return ApplicationResponse.succeed("初始化成功");
    }

    @GetMapping
    public ApplicationResponse<String> secKill(){
        return orderService.secKill(1)?
                ApplicationResponse.succeed("创建订单成功"):
                ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG,"创建订单失败");
    }
}

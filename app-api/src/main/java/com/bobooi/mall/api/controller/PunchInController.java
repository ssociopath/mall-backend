package com.bobooi.mall.api.controller;

import com.bobooi.mall.data.entity.CsmLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.entity.PunchIn;
import com.bobooi.mall.data.service.concrete.PunchInService;
import com.bobooi.mall.data.service.concrete.UserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author bobo
 * @date 2021/4/20
 */

@CrossOrigin("*")
@RestController
@Api(tags = "签到管理")
@RequestMapping("/punch")
public class PunchInController {
    @Resource
    PunchInService punchInService;

    @ApiOperation("获取该用户当月的签到日期")
    @GetMapping
    public ApplicationResponse<List<Integer>> getMonthlyPunchIn() {
        LocalDate today = LocalDate.now();
        PunchIn punchIn = punchInService.getMonthlyPunchIn(LocalDate.now());
        if (punchIn == null) {
            return ApplicationResponse.succeed(new ArrayList<>());
        }
        return ApplicationResponse.succeed(IntStream.range(1, today.getDayOfMonth() + 1)
                .filter(day -> PunchInService.checkHasPunchedIn(punchIn.getDailyBitmap(), day))
                .boxed()
                .collect(Collectors.toList())
        );
    }

    @ApiOperation("当前用户今日签到接口")
    @PostMapping
    public ApplicationResponse<String> punchIn() {
        return punchInService.dailyPunchIn(LocalDate.now())
                ? ApplicationResponse.succeed()
                : ApplicationResponse.fail(SystemCodeEnum.FAILURE, "你今天已经签到过了哦");
    }
}

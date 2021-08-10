package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.data.entity.search.ESProduct;
import com.bobooi.mall.data.repository.concrete.search.EsProductRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bobo
 * @date 2021/8/11
 */

@CrossOrigin("*")
@RestController
@Api(tags = "商品搜索")
@RequestMapping("/search")
public class SearchController {
    @Resource
    private EsProductRepository esProductRepository;

    @GetMapping
    public ApplicationResponse<List<ESProduct>> queryAllArticles(String keyword) {
        List<ESProduct> es = esProductRepository.findByProductTypeName(keyword);
        return ApplicationResponse.succeed(es);
    }
}

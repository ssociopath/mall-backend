package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.entity.PdtDetailInf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdtDetailVO {
    private Integer productId;
    private String productName;
    private String picUrl;
    private Float price;
    private Integer inventory;
    private String description;
    private String supplierName;
    private List<String> productTypeNameList;
    private String supplierAddress;

    public static PdtDetailVO fromPdtDetailInfList(List<PdtDetailInf> pdtDetailInfList) {
        PdtDetailInf firstPdtDetailInf=pdtDetailInfList.get(0);
        return PdtDetailVO.builder()
                .productTypeNameList(pdtDetailInfList.stream()
                        .map(pdtDetailInf -> pdtDetailInf.getProductTypeName())
                        .collect(Collectors.toList()))
                .productId(firstPdtDetailInf.getProductId())
                .productName(firstPdtDetailInf.getProductName())
                .picUrl(firstPdtDetailInf.getPicUrl())
                .price(firstPdtDetailInf.getPrice())
                .inventory(firstPdtDetailInf.getInventory())
                .description(firstPdtDetailInf.getDescription())
                .supplierName(firstPdtDetailInf.getSupplierName())
                .supplierAddress(firstPdtDetailInf.getSupplierAddress())
                .build();
    }
}

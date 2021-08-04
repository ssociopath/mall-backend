package com.bobooi.mall.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Entity
@Table(name="product_detail_view")
@NoArgsConstructor
@AllArgsConstructor
public class PdtDetailInf {
    @Id
    @Column
    private Integer productId;

    @Column
    private String productName;

    @Column
    private String description;

    @Column
    private String picUrl;

    @Column
    private Float price;

    @Column
    private Integer inventory;

    @Column
    private String supplierName;

    @Column
    private String productTypeName;
}

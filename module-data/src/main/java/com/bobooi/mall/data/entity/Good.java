package com.bobooi.mall.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author bobo
 * @date 2021/8/3
 * // TODO 注意，是秒杀测试类，不可作为业务使用
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodId;
    private Integer stock;
}

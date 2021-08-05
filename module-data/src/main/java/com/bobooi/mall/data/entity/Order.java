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
@Table(name="order_msg")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer goodId;
    private Integer customerId;
}
package com.bobooi.mall.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Entity
@Table(name="order_cart")
@NoArgsConstructor
@AllArgsConstructor
public class OrderCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    private Integer customerId;
    private Integer productAmount;
}

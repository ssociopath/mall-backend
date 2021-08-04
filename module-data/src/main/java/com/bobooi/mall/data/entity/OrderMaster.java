package com.bobooi.mall.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Entity
@Table(name="order_master")
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer orderSn;
    private Integer customerId;
    private String shippingUser;
    private Integer customerAddrId;
    private Float orderMoney;
    private Float districtMoney;
    private Float paymentMoney;
    private Timestamp payTime;
    private Integer orderStatus;
}
package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.bo.UserDetailBO;
import com.bobooi.mall.data.entity.CsmAddr;
import com.bobooi.mall.data.entity.CsmInf;
import com.bobooi.mall.data.entity.CsmLogin;
import com.bobooi.mall.data.service.concrete.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/8/6
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailVO {
    private Integer customerId;
    private String loginName;
    private String customerName;
    private Integer identityCardType;
    private String identityCardNo;
    private String mobilePhone;
    private Character gender;
    private Integer userPoint;

    public static UserDetailVO fromUserDetailBO(UserDetailBO userDetailBO){
        CsmLogin csmLogin = userDetailBO.getCsmLogin();
        CsmInf csmInf = userDetailBO.getCsmInf();
        return UserDetailVO.builder()
                .customerId(csmInf.getCustomerId())
                .loginName(csmLogin.getLoginName())
                .customerName(csmInf.getCustomerName())
                .identityCardType(csmInf.getIdentityCardType())
                .identityCardNo(csmInf.getIdentityCardNo())
                .mobilePhone(csmInf.getMobilePhone())
                .gender(csmInf.getGender())
                .userPoint(csmInf.getUserPoint())
                .build();
    }
}

package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.Constant;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.repository.concrete.CsmLoginRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.bobooi.mall.data.entity.CsmLogin;

import javax.annotation.Resource;

/**
 * @author bobo
 * @date 2021/3/31
 */
@Service
@Slf4j
public class UserService extends BaseDataService<CsmLogin, Integer> {
    @Resource
    CsmLoginRepository csmLoginRepository;

    public CsmLogin addUser(CsmLogin csmLogin) throws Exception{
        AssertUtils.isNull(csmLoginRepository.findCsmLoginByLoginName(csmLogin.getLoginName()), new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "帐号已存在！"));
        AssertUtils.isFalse(csmLogin.getPassword().length() > Constant.PASSWORD_MAX_LEN, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "密码不能超过8位！"));
        csmLogin.setPassword(DigestUtils.sha1Hex(csmLogin.getPassword()));
        return this.insert(csmLogin);
    }

    public CsmLogin getUserByAccount(String account){
        AssertUtils.notNull(account, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, "帐号已存在！"));
        return csmLoginRepository.findCsmLoginByLoginName(account);
    }

    public boolean match(String account, String password){
        CsmLogin loginCsmLogin = csmLoginRepository.findCsmLoginByLoginNameAndPassword(account, DigestUtils.sha1Hex(password));
        return null!= loginCsmLogin;
    }

    public CsmLogin info(){
        String account = JwtUtil.getCurrentClaim(JwtUtil.ACCOUNT);
        return getUserByAccount(account);
    }
}

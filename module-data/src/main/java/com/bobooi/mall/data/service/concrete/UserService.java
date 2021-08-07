package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.Constant;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.bo.UserDetailBO;
import com.bobooi.mall.data.entity.CsmAddr;
import com.bobooi.mall.data.repository.concrete.CsmAddrRepository;
import com.bobooi.mall.data.repository.concrete.CsmInfRepository;
import com.bobooi.mall.data.repository.concrete.CsmLoginRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.bobooi.mall.data.entity.CsmLogin;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author bobo
 * @date 2021/3/31
 */
@Service
@Slf4j
public class UserService extends BaseDataService<CsmLogin, Integer> {
    @Resource
    CsmLoginRepository csmLoginRepository;
    @Resource
    CsmInfRepository csmInfRepository;
    @Resource
    CsmAddrRepository csmAddrRepository;

    public List<CsmAddr> getUserAddrList(){
        return csmAddrRepository.findAll();
    }

    public List<UserDetailBO> getDetailUserList(){
        return csmLoginRepository.findAll().stream()
                .filter(csmLogin -> csmLogin.getRoleId()==1)
                .map(csmLogin -> new UserDetailBO(csmLogin, csmInfRepository.findByCustomerId(csmLogin.getCustomerId())))
                .collect(Collectors.toList());
    }

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

    public List<CsmAddr> getAllCsmAddress(){
        return csmAddrRepository.findAll();
    }

    public List<CsmAddr> getCsmAddressListByCustomerId(Integer customerId){
        return csmAddrRepository.findAllByCustomerId(customerId);
    }

    public CsmAddr addCsmAddress(Integer customerId,Integer zipcode,String address){
        return csmAddrRepository.save(new CsmAddr(null,customerId,zipcode,address,null));
    }

    public CsmAddr updateCsmAddress(Integer customerAddrId,Integer zipcode,String address){
        CsmAddr csmAddrInDB=csmAddrRepository.getById(customerAddrId);
        AssertUtils.notNull(csmAddrInDB,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"该地址不存在"));
        csmAddrInDB.setZipcode(zipcode);
        csmAddrInDB.setAddress(address);
        return csmAddrRepository.save(csmAddrInDB);
    }

    public CsmAddr setDefaultCsmAddress(Integer customerAddrId){
        CsmAddr csmAddrInDB=csmAddrRepository.getById(customerAddrId);
        AssertUtils.notNull(csmAddrInDB,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"该地址不存在"));
        Optional.ofNullable(csmAddrRepository.findByCustomerIdAndIsDefault(csmAddrInDB.getCustomerId(),1))
                .ifPresent(oldDefaultCsmAddr-> {
                    oldDefaultCsmAddr.setIsDefault(0);
                    csmAddrRepository.save(oldDefaultCsmAddr);
                });
        csmAddrInDB.setIsDefault(1);
        return csmAddrRepository.save(csmAddrInDB);
    }

    public void deleteCsmAddrByCsmAddrId(Integer customerAddrId){
        CsmAddr csmAddrInDB=csmAddrRepository.getById(customerAddrId);
        AssertUtils.notNull(csmAddrInDB,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"该地址不存在"));
        csmAddrRepository.deleteById(customerAddrId);
    }
}

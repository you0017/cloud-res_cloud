package com.yc.service.impl;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mapper.ResUserMapper;
import com.yc.pojo.ResUser;
import com.yc.pojo.ResUserVO;
import com.yc.service.ResUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ResUserServiceImpl extends ServiceImpl<ResUserMapper, ResUser> implements UserDetailsService, ResUserService {
    private ResUserMapper resUserMapper;

    @Autowired
    public void setResUserMapper(ResUserMapper resUserMapper) {
        this.resUserMapper = resUserMapper;
    }

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //security进行校验的时候会进到这个方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            //这是一个userDetails对象
            ResUser user = this.lambdaQuery().eq(ResUser::getUsername, username).one();
            return user;
        }catch (Exception e){
            log.error("用户:"+username+"不存在",e);
            return null;
        }
    }

    @Transactional
    @Override
    public int regUser(ResUserVO resUserVO) {
        //判断用户是否存在
        List<ResUser> list = this.lambdaQuery().eq(ResUser::getUsername, resUserVO.getUsername()).list();
        if (list!=null&&list.size()>0){
            throw new RuntimeException("用户:"+resUserVO.getUsername()+"已存在");
        }

        ResUser resUser = new ResUser();
        resUser.setUsername(resUserVO.getUsername());
        resUser.setPassword(passwordEncoder.encode(resUserVO.getPassword()));
        resUser.setEmail(resUserVO.getEmail());

        resUserMapper.insert(resUser);

        return resUser.getUserid();
    }

}

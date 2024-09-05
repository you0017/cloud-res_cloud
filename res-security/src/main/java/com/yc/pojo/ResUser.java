package com.yc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

//PO类，与数据表相关
@TableName("resuser")
@Data
public class ResUser implements Serializable, UserDetails {

    @TableId(type = IdType.AUTO)
    private Integer userid;
    @TableField("username")
    private String username;
    @TableField("pwd")
    private String password;  //UserDetail要求是password
    @TableField("email")
    private String email;

    @TableField(exist = false)
    private String role="user";
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override//true代表账户没过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override//true代表账户没被锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override//true代表密码没过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override//true代表账户可用
    public boolean isEnabled() {
        return true;
    }
}

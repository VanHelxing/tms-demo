package com.vhx.tmsdemo.service;

import com.vhx.tmsdemo.entity.system.SystemRole;
import com.vhx.tmsdemo.entity.system.SystemUser;
import com.vhx.tmsdemo.mapper.UserDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        SystemUser user = userDao.findByUserName(userName);
        if(user != null){
            throw new UsernameNotFoundException("该用户名不存在！");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(SystemRole role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println(role.getName());
        }
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}

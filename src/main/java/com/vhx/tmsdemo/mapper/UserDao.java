package com.vhx.tmsdemo.mapper;

import com.vhx.tmsdemo.entity.system.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    /**
     * 通过客户编号查询客户信息
     * @param userName
     * @return
     */
    public SystemUser findByUserName(@Param("userName") String userName);
}

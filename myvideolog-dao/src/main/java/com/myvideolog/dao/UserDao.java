package com.myvideolog.dao;

import com.myvideolog.dao.domain.User;
import com.myvideolog.dao.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getUserByPhone(String phone);
    Integer addUser(User user);
    Integer addUserInfo(UserInfo userInfo);
}

package com.myvideolog.dao;

import com.myvideolog.domain.User;
import com.myvideolog.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserDao {
    User getUserByPhone(String phone);
    Integer addUser(User user);
    Integer addUserInfo(UserInfo userInfo);

    User getUserById(Long userId);

    UserInfo getUserInfoByUserId(Long userId);

    Integer updateUserInfos(UserInfo userInfo);

    Integer updateUsers(User user);

    String getRefreshTokenByUserId(Long userId);

    User getUserByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);
}

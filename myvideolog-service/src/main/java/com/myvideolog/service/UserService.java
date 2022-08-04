package com.myvideolog.service;

import com.mysql.cj.util.StringUtils;
import com.myvideolog.dao.UserDao;
import com.myvideolog.domain.User;
import com.myvideolog.domain.UserInfo;
import com.myvideolog.domain.constant.UserConstant;
import com.myvideolog.domain.exception.ConditionException;
import com.myvideolog.service.util.MD5Util;
import com.myvideolog.service.util.RSAUtil;
import com.myvideolog.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("The phone number cannot be empty!");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser != null){
            throw new ConditionException("This phone number is already existed!");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("Password decryption fail!");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userDao.addUser(user);
        //add user info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
    }

    public User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }

    public String login(User user) throws Exception{
        String phone = user.getPhone() == null ? "" : user.getPhone();
        String email = user.getEmail() == null ? "" : user.getEmail();
        if(StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(email)){
            throw new ConditionException("Phone number cannot be empty!");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser == null) {
            throw new ConditionException("User not exist!");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e){
            throw new ConditionException("Password decryption fail!");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if(!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("Password not match!");
        }

        return TokenUtil.generateToken(dbUser.getId());

    }

    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }
    public void updateUsers(User user) throws Exception{
        Long id = user.getId();
        User dbUser = userDao.getUserById(id);
        if(dbUser == null){
            throw new ConditionException("User not existed");
        }
        if(!StringUtils.isNullOrEmpty(user.getPassword())){
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(new Date());
        userDao.updateUsers(user);
    }
    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfos(userInfo);
    }

    public String getRefreshTokenByUserId(Long userId) {
        return userDao.getRefreshTokenByUserId(userId);
    }


    public User getUserById(Long followingId) {
        return userDao.getUserById(followingId);
    }

    public List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList) {
        return userDao.getUserInfoByUserIds(userIdList);
    }
}

package com.myvideolog.api.support;


import com.myvideolog.domain.exception.ConditionException;
import com.myvideolog.service.UserService;
import com.myvideolog.service.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {

    @Autowired
    private UserService userService;
    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if(userId < 0) {
            throw new ConditionException("Invalid user!");
        }
        return userId;
    }

    private void verifyRefreshToken(Long userId){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String refreshToken = requestAttributes.getRequest().getHeader("refreshToken");
        String dbRefreshToken = userService.getRefreshTokenByUserId(userId);
        if(!dbRefreshToken.equals(refreshToken)){
            throw new ConditionException("Invalid token！");
        }
    }
}

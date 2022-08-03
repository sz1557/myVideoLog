package com.myvideolog.api;

import com.myvideolog.dao.domain.JsonReponse;
import com.myvideolog.dao.domain.User;
import com.myvideolog.service.UserService;
import com.myvideolog.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonReponse<String> getRsaPublicKey(){
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonReponse<>(pk);
    }

    @PostMapping("/users")
    public JsonReponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonReponse.success();
    }

    @PostMapping("/user-tokens")
    public JsonReponse<String> login(@RequestBody User user){
        String token = userService.login(user);
        return new JsonReponse<>(token);
    }
}

package com.myvideolog.api;

import com.myvideolog.domain.JsonResponse;
import com.myvideolog.domain.UserFollowing;
import com.myvideolog.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFollowingApi {
    @Autowired
    private UserFollowingService userFollowingService;

    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }
}

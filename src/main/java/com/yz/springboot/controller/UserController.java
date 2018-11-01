package com.yz.springboot.controller;

import com.yz.springboot.entity.BaseResponse;
import com.yz.springboot.entity.LoginBean;
import com.yz.springboot.entity.TUser;
import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.retention.AuthToken;
import com.yz.springboot.retention.InjectUser;
import com.yz.springboot.service.IUserService;
import com.yz.springboot.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author huiRan
 * @date 2018/9/3
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final IUserService service;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }


    @AuthToken
    @PostMapping(value = "/updateUserAge")
    public BaseResponse<String> updateUserAge(@InjectUser TUser user, @RequestParam(value = "uid") Long uid,
                                              @RequestParam(value = "age") Integer age) {
        if (!uid.equals(user.getUid())) {
            throw new CommonException(ErrorEnum.USER_NOT_EXIST_ERROR);
        }
        user.setAge(age);
        int count = service.updateUser(user);
        if (count < 0) {
            throw new CommonException(ErrorEnum.MODIFY_ERROR);
        }
        logger.debug("count=" + count);
        return ResponseUtils.successNoData();
    }

    @GetMapping(value = "/queryUserByPrimaryKey")
    public BaseResponse<TUser> queryUserByPrimaryKey(@RequestParam(value = "id") Integer id) {
        TUser user = service.queryByPrimaryKey(id);
        return ResponseUtils.success(user);
    }

    @AuthToken
    @GetMapping(value = "/queryUser")
    public BaseResponse<TUser> queryUserById(@InjectUser TUser user, @RequestParam(value = "uid") Long uid) {
        if (!uid.equals(user.getUid())) {
            throw new CommonException(ErrorEnum.USER_NOT_EXIST_ERROR);
        }
        return ResponseUtils.success(user);
    }

    @PostMapping(value = "/login")
    public BaseResponse<LoginBean> userLogin(@RequestParam(value = "mobile") String mobile,
                                             @RequestParam(value = "password") String password) {
        ValidationUtils.checkMobile(mobile);
        ValidationUtils.checkPassword(password);

        logger.debug("---------userLogin----------");

        TUser user = service.queryByMobile(mobile);
        if (user == null) {
            throw new CommonException(ErrorEnum.USER_NOT_EXIST_ERROR);
        }
        if (!user.getPassword().equals(password)) {
            throw new CommonException(ErrorEnum.PASSWORD_ERROR);
        }

        return ResponseUtils.success(getLoginBean(user));
    }

    @PostMapping(value = "/register")
    public BaseResponse<LoginBean> registerUser(@RequestParam(value = "mobile") String mobile,
                                                @RequestParam(value = "password") String password) {
        ValidationUtils.checkMobile(mobile);
        ValidationUtils.checkPassword(password);

        TUser user = service.queryByMobile(mobile);
        if (user != null) {
            throw new CommonException(ErrorEnum.MOBILE_EXIST_ERROR);
        }

        user = new TUser();
        user.setNick(TextUtils.getRandomString(10));
        user.setMobile(mobile);
        user.setPassword(password);
        user.setUid(UidGenerator.getDefault().nextId());

        int count = service.registerUser(user);
        if (count < 0) {
            throw new CommonException(ErrorEnum.REGISTER_ERROR);
        }

        System.out.println("insert----count=" + count);
        return ResponseUtils.success(getLoginBean(user));
    }


    private LoginBean getLoginBean(TUser user) {
        Long uid = user.getUid();
        String token = AuthUtils.createToken(uid.toString());
        return new LoginBean(token, user);
    }
}

package com.yz.springboot.service;

import com.yz.springboot.entity.TUser;

/**
 * @author huiRan
 * @date 2018/9/13
 */
public interface IUserService {

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    int updateUser(TUser user);

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    TUser queryByPrimaryKey(Integer id);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    TUser queryByMobile(String mobile);

    /**
     * 根据uid查询用户
     * @param uid uid
     * @return user
     */
    TUser queryByUid(Long uid);

    /**
     * 注册新用户
     *
     * @param user
     * @return
     */
    int registerUser(TUser user);



}

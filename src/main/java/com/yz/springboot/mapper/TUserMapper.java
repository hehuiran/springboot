package com.yz.springboot.mapper;

import com.yz.springboot.entity.TUser;

public interface TUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    int insert(TUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    int insertSelective(TUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    TUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    int updateByPrimaryKeySelective(TUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user
     *
     * @mbggenerated Tue Sep 18 14:00:51 CST 2018
     */
    int updateByPrimaryKey(TUser record);

    TUser selectByMobile(String mobile);

    TUser selectByUid(Long uid);
}
package com.yz.springboot.service.impl;

import com.yz.springboot.entity.TUser;
import com.yz.springboot.mapper.TUserMapper;
import com.yz.springboot.service.IUserService;
import com.yz.springboot.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author huiRan
 * @date 2018/9/13
 */
@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int updateUser(TUser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @SuppressWarnings("unchecked")
    @Override
    public TUser queryByPrimaryKey(Integer id) {
        String moduleName = this.getClass().getSimpleName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String key = RedisUtils.keyBuilder(moduleName, methodName, id.toString());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        TUser user = (TUser) valueOperations.get(key);
        if (user == null) {
            synchronized (this) {
                user = (TUser) valueOperations.get(key);
                if (user == null) {
                    user = userMapper.selectByPrimaryKey(id);
                    valueOperations.set(key, user);
                }
            }
        }
        return user;
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TUser queryByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public TUser queryByUid(Long uid) {
        return userMapper.selectByUid(uid);
    }

    @Override
    public int registerUser(TUser user) {
        return userMapper.insert(user);
    }
}

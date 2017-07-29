package com.ggj.article.module.business.dao;

import base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author:gaoguangjin
 * @date 2017/7/29 8:59
 */

public class UserInfoMapperTest extends BaseTest{
    @Autowired
    protected UserInfoMapper userInfoMapper;
    @Test
    public void getUserNameByUserId() throws Exception {
        Assert.assertTrue("管理员".equals(userInfoMapper.findUserInfoList(10).get(0).getUserName()));
    }

}
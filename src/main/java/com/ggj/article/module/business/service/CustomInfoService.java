package com.ggj.article.module.business.service;

import java.util.Date;
import java.util.List;

import com.ggj.article.module.business.bean.UserInfoRole;
import com.ggj.article.module.business.dao.UserInfoRoleMapper;
import com.ggj.article.module.common.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggj.article.module.business.bean.CustomInfo;
import com.ggj.article.module.business.bean.CustomUserInfo;
import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.dao.CustomInfoMapper;
import com.ggj.article.module.business.dao.CustomUserInfoMapper;
import com.ggj.article.module.business.dao.UserInfoMapper;
import com.ggj.article.module.common.GlobalConstants;
import com.ggj.article.module.common.crud.CrudService;
import com.ggj.article.module.common.utils.IdGen;

/**
 * @author:gaoguagjin
 * @date 2017/5/14 18:14
 */
@Service
@Transactional(readOnly = true)
public class CustomInfoService extends CrudService<CustomInfoMapper, CustomInfo> {
    @Autowired
    protected UserInfoMapper userInfoMapper;
    @Autowired
    protected CustomUserInfoMapper customUserInfoMapperMapper;
    @Autowired
    protected UserInfoRoleMapper userInfoRoleMapper;
    @Transactional(readOnly = false)
    public void save(CustomInfo customInfo) {
        UserInfo userInfo = new UserInfo();
        String salt = IdGen.getSalt("");
        String enyCrptPassword = new SimpleHash(GlobalConstants.HASH_ALGORITHM_NMAE, customInfo.getPassword(),
                ByteSource.Util.bytes(salt), GlobalConstants.HASH_ITERATIONS).toHex();
        userInfo.setPassword(enyCrptPassword);
        userInfo.setSalt(salt);
        userInfo.setUserName(customInfo.getUserName());
        userInfo.setLoginName(customInfo.getLoginName());
        userInfo.setPhoneNumber(customInfo.getPhoneNumber());
        //userInfo.setId(customInfo.getUserId());
        //客户
        userInfo.setUserType(1l);
        userInfo.setCreateDate(new Date());
        if (customInfo.getId() == null) {
            customInfo.setCreateDate(new Date());
            //保存用户基本信息表
            userInfoMapper.insert(userInfo);
            customInfo.setUserId(userInfo.getId());
            customInfo.setCustomStatus(0l);
            //保存客户信息表
            dao.insert(customInfo);
            CustomUserInfo customUserInfo = new CustomUserInfo();
            customUserInfo.setCustomId(customInfo.getId());
            //创建人id
            customUserInfo.setUserId(UserUtils.getPrincipal().getId());
            customUserInfo.setCreateDate(new Date());
            customUserInfoMapperMapper.insert(customUserInfo);
        } else {
            userInfo.setId(customInfo.getUserId());
            userInfoMapper.update(userInfo);
            dao.update(customInfo);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(CustomInfo entity) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(entity.getUserId());
        userInfo.setFlag("1");
        userInfoMapper.delete(userInfo);

        entity.setFlag("1");
        dao.delete(entity);
        CustomUserInfo customUserInfo = new CustomUserInfo();
        customUserInfo.setCustomId(entity.getId());
        customUserInfoMapperMapper.delete(customUserInfo);
    }

    @Transactional(readOnly = false)
    public void verify(CustomInfo customInfo) {
        customInfo.setCustomStatus(1l);
        dao.verify(customInfo);
    }
    @Transactional(readOnly = false)
    public void approveSave(CustomInfo customInfo) {
        customInfo.setCustomStatus(1l);
        dao.update(customInfo);
        // 删除所有角色权限
        userInfoRoleMapper.delete(new UserInfoRole(customInfo.getUserId()));
        if (!CollectionUtils.isEmpty(customInfo.getRoleIdList())) {
            customInfo.setCreateDate(new Date());
            customInfo.setId(customInfo.getUserId());
            userInfoRoleMapper.saveUserInfoRole(customInfo);
        }
    }

    public CustomInfo getRole(Integer id) {
        return dao.getRole(id);
    }

    public List<CustomUserInfo> getCustomUser(CustomUserInfo customUserInfo) {
        return customUserInfoMapperMapper.findList(customUserInfo);
    }
}

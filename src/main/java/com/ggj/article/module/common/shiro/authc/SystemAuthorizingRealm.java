package com.ggj.article.module.common.shiro.authc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ggj.article.module.business.bean.CustomUserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggj.article.module.business.bean.CustomInfo;
import com.ggj.article.module.business.bean.CustomStatusEnum;
import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.business.service.CustomInfoService;
import com.ggj.article.module.business.service.UserInfoService;
import com.ggj.article.module.sys.entity.Menu;
import com.ggj.article.module.sys.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CustomInfoService customInfoService;
    @Autowired
    private MenuService menuService;

    // [2]授权(Authorization) 这个是显示调用的，当我们调用具体的hasRole()才会调用这个
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("" + principals);
        // 认证成功之后就进行授权
        Principal principal = (Principal) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        /********常规的这一步是从根据用户名 从数据库查询出来对应的角色和权限 然后放到simpleAuthorizeation里面*******/
        // 自定义两个角色student teacher
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("student");
        roleSet.add("teacher");
        sai.setRoles(roleSet);
        Set<String> permissionsSet = new HashSet<String>();
        String roleIds = principal.getUserInfo().getRoleIds();
        List<Menu> menuList = menuService.findUserRoleMenuList(roleIds);
        for (Menu menu : menuList) {
            if (StringUtils.isNotEmpty(menu.getPermission())) {
                String[] arrags = StringUtils.split(menu.getPermission(), ",");
                for (String arrag : arrags) {
                    permissionsSet.add(arrag);
                }
            }
        }
        principal.setMenuList(menuList);
//        permissionsSet.add("sys:dict:view");
//        permissionsSet.add("sys:dict:edit");
//        permissionsSet.add("bussiness:media:view");
//        permissionsSet.add("bussiness:media:edit");
//        permissionsSet.add("bussiness:mediaEditor:edit");
//        permissionsSet.add("bussiness:mediaSettle:view");
//        permissionsSet.add("bussiness:mediaSettle:edit");
//        permissionsSet.add("bussiness:userInfo:view");
//        permissionsSet.add("bussiness:userInfo:edit");
//        permissionsSet.add("bussiness:customInfo:view");
//        permissionsSet.add("bussiness:customInfo:edit");
//        permissionsSet.add("bussiness:customInfo:verify");
//        permissionsSet.add("bussiness:article:edit");
//        permissionsSet.add("bussiness:article:view");
//        permissionsSet.add("bussiness:article:add");
//        //编辑审核
//        permissionsSet.add("bussiness:article:verify");
//
//        permissionsSet.add("bussiness:settle:edit");
//        permissionsSet.add("bussiness:settle:view");
//        permissionsSet.add("bussiness:settle:form");
//        permissionsSet.add("sys:menu:view");
//        permissionsSet.add("sys:menu:edit");
//        permissionsSet.add("sys:role:view");
//        permissionsSet.add("sys:role:edit");
//        permissionsSet.add("sys:user:list");
//        permissionsSet.add("sys:user:edit");
        sai.setStringPermissions(permissionsSet);
        sai.addStringPermission("user");
        log.info("授权成功");
        return sai;
    }

    // // [1]认证(authentication) 我们认证时候获取的密码应该是加密之后的密码。
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginName = (String) token.getPrincipal();
        // 防止注入检查
        if (StringUtils.isEmpty(loginName))
            throw new AuthenticationException("");
        UserInfo userinfo = null;
        try {
            List<UserInfo> userinfoList = userInfoService.findList(new UserInfo(loginName));
            if (!CollectionUtils.isEmpty(userinfoList)) {
                userinfo = userinfoList.get(0);
                //客户
                if (userinfo.getUserType() == 1) {
                    List<CustomInfo> listCustomInfo = customInfoService.findList(new CustomInfo(userinfo.getId()));
                    if (!CollectionUtils.isEmpty(listCustomInfo)) {
                        CustomInfo customInfo = listCustomInfo.get(0);
                        CustomStatusEnum customStatusEnum = CustomStatusEnum.valueOf(Integer.parseInt(customInfo.getCustomStatus() + ""));
                        userinfo.setCustomInfo(customInfo);
                        if (customStatusEnum != CustomStatusEnum.endProcessing) {
                            userinfo = null;
                        }
                    } else {
                        userinfo = null;
                    }
                 //编辑
                }else if(userinfo.getUserType() == 2){

                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        if (userinfo == null)
            throw new AuthenticationException("");
        return new SimpleAuthenticationInfo(new Principal(loginName, userinfo), userinfo.getPassword(),
                ByteSource.Util.bytes(userinfo.getSalt()), getName());
    }
}

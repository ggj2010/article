package com.ggj.article.module.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ggj.article.module.common.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoguangjin
 * @ClassName:Role.java
 * @Description: 角色
 * @Date 2015-11-2 下午5:00:15
 */
@Getter
@Setter
public class Role extends BaseEntity {

    private static final long serialVersionUID = -2034069739195743884L;

    private String name;

    private String menuIds;

    private List<Menu> menuList = new ArrayList<Menu>();

    private List<Integer> menuIdsList = new ArrayList<Integer>();

    public Role(int id) {
        this.id = id;
    }

    public Role() {
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        // 防止重复赋值
        menuList = new ArrayList<Menu>();
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");
            for (String id : ids) {
                menuList.add(new Menu(Integer.parseInt(id)));
            }
        }
    }

    /* 根据list获取menuId */
    public List<Integer> getMenuIdList() {
        menuIdsList = new ArrayList<Integer>();
        for (Menu menu : menuList) {
            menuIdsList.add(menu.getId());
        }
        return menuIdsList;
    }
}

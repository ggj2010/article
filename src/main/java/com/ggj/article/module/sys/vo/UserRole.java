package com.ggj.article.module.sys.vo;


import com.ggj.article.module.business.bean.UserInfo;
import com.ggj.article.module.common.persistence.BaseEntity;
import com.ggj.article.module.sys.entity.Role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRole extends BaseEntity {
	private static final long serialVersionUID = 2693422935129817505L;
	private Role role;
	private List<UserInfo> userLsit;
}

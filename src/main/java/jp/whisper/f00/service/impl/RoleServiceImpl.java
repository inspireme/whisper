package jp.whisper.f00.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.whisper.common.service.impl.BaseServiceImpl;
import jp.whisper.f00.service.RoleService;
import jp.whisper.f00.dao.RoleDAO;
import jp.whisper.f00.model.Role;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService
{
	@Autowired
	public RoleServiceImpl(RoleDAO roleDAO){
		super();
		super.setBaseDAO(roleDAO);
	}

	public RoleDAO getRoleDAO()
	{
		return (RoleDAO)super.baseDAO;
	}
}


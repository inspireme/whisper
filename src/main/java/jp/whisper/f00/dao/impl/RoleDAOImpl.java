package jp.whisper.f00.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.whisper.common.dao.impl.BaseDAOImpl;
import jp.whisper.f00.dao.RoleDAO;
import jp.whisper.f00.model.Role;

@Repository
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO
{
	@Autowired
	public RoleDAOImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
	}
}


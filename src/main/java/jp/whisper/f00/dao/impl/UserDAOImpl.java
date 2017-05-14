package jp.whisper.f00.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.whisper.common.dao.impl.BaseDAOImpl;
import jp.whisper.f00.dao.UserDAO;
import jp.whisper.f00.model.User;

@Repository
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO
{
	@Autowired
	public UserDAOImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
	}
}


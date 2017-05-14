package jp.whisper.f00.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.whisper.common.dao.impl.BaseDAOImpl;
import jp.whisper.f00.dao.AuthorityDAO;
import jp.whisper.f00.model.Authority;

@Repository
public class AuthorityDAOImpl extends BaseDAOImpl<Authority> implements AuthorityDAO
{
	private static final String	LIST_AUTHORITIES_BY_USERMAP	= ".listAuthoritiesByUserMap";

	@Autowired
	public AuthorityDAOImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public List<Authority> listAuthoritiesByUserMap(Map<String, Object> userMap) {
		return this.getSqlSession().selectList(getSqlMapNamespace() + LIST_AUTHORITIES_BY_USERMAP, userMap);
	}
}


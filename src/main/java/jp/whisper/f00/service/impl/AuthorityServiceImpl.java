package jp.whisper.f00.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.whisper.common.service.impl.BaseServiceImpl;
import jp.whisper.f00.dao.AuthorityDAO;
import jp.whisper.f00.model.Authority;
import jp.whisper.f00.service.AuthorityService;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements AuthorityService
{
	@Autowired
	public AuthorityServiceImpl(AuthorityDAO authorityDAO){
		super();
		super.setBaseDAO(authorityDAO);
	}

	public AuthorityDAO getAuthorityDAO()
	{
		return (AuthorityDAO)super.baseDAO;
	}

	@Override
	public List<Authority> listAuthoritiesByUserMap(Map<String, Object> userMap) {
		return this.getAuthorityDAO().listAuthoritiesByUserMap(userMap);
	}
}


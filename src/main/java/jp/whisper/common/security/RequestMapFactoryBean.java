package jp.whisper.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Repository;

import jp.whisper.f00.model.Authority;
import jp.whisper.f00.service.AuthorityService;

@Repository("requestMap")
public class RequestMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>> {
	@Autowired
	private AuthorityService authorityService;

	private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

	@Override
	public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getObject() throws Exception {

		List<Authority> authorityList = authorityService.listAll();
		for (Authority auth : authorityList) {
			RequestMatcher matcher = new AntPathRequestMatcher(auth.getAuthUrl());
			List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
			ConfigAttribute config = new SecurityConfig(auth.getCode());
			list.add(config);
			requestMap.put(matcher, list);
		}
		return requestMap;
	}

	@Override
	public Class<?> getObjectType() {
		return LinkedHashMap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

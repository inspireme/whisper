package jp.whisper.common.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import jp.whisper.f00.model.Authority;
import jp.whisper.f00.model.User;
import jp.whisper.f00.service.AuthorityService;
import jp.whisper.f00.service.UserService;

@Repository("userDetailsService")
public class UserSecurityServiceImpl implements UserDetailsService {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

		User user = userService.getByLoginId(loginId);
		if (user == null) {
			throw new UsernameNotFoundException(loginId + "が存在しません");
		}

		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();

		// add the authority from the roles
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("loginId", loginId);
		List<Authority> authorityList = authorityService.listAuthoritiesByUserMap(userMap);

		for (Authority authority : authorityList) {
			if (authority == null) {
				continue;
			}
			authsList.add(new SimpleGrantedAuthority(authority.getCode()));
		}

		CustomerUser userdetail = new CustomerUser(user.getLoginId(), user.getPassword(), true, true, true, true,
				authsList);
		return userdetail;
	}

}

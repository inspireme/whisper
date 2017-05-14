package jp.whisper.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomerUser extends User {

	public CustomerUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	public CustomerUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
}

package jp.whisper.common.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository("customerAuthenticationProvider")
public class CustomerAuthenticationProvider extends DaoAuthenticationProvider {
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		super.additionalAuthenticationChecks(userDetails, authentication);

//		CustomerAuthenticationToken customerAuthenticationToken = (CustomerAuthenticationToken) authentication;
//		String requestedCompanyId = customerAuthenticationToken.getCompanyId();
//		String companyId = ((CustomerUser) userDetails).getAccount().getCompanyId();
//		if (!companyId.equals(requestedCompanyId)) {
//			throw new BadCredentialsException(
//					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//		}
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
//		String companyId = ((CustomerUser) user).getAccount().getCompanyId();
		return new CustomerAuthenticationToken(user, authentication.getCredentials(),
				user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomerAuthenticationToken.class.isAssignableFrom(authentication);
	}
}

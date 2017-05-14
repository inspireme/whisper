package jp.whisper.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

public class CustomerAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	protected CustomerAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl); // defaultFilterProcessesUrl
	}

	private final Log log = LogFactory.getLog(getClass());
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_FORM_URL_KEY = "spring-security-redirect";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private String urlParameter = SPRING_SECURITY_FORM_URL_KEY;
	private boolean postOnly = true;

	// ~ Constructors
	// ===================================================================================================

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		CustomerAuthenticationToken authRequest = new CustomerAuthenticationToken(username, password);

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);

		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, username);
		}

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		Authentication authentication = null;
		try {
			authentication = this.getAuthenticationManager().authenticate(authRequest);

			boolean result = authentication.isAuthenticated();
			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();

			if (result) {
				// ログイン成功
				log.info("ログインを成功しました。user:" + username + ",password:" + password + ",sessionid:"
						+ details.getSessionId() + ",remoteAddress:" + details.getRemoteAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("ログインを失敗しました。user:" + username + ",password:" + password);
			throw e;
		}

		return authentication;

	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected String obtainUrl(HttpServletRequest request) {
		return request.getParameter(urlParameter);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 *
	 * @param usernameParameter
	 *            the parameter name. Defaults to "username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from
	 * the login request..
	 *
	 * @param passwordParameter
	 *            the parameter name. Defaults to "password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}

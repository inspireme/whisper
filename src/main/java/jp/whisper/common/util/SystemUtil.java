package jp.whisper.common.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SystemUtil {

	private SystemUtil() {

	}

	/**
	 * ユーザーログインIDを取得する
	 *
	 * @return
	 */
	public static String whoAmI() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}

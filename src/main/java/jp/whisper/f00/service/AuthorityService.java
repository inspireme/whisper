package jp.whisper.f00.service;

import java.util.List;
import java.util.Map;

import jp.whisper.common.service.BaseService;
import jp.whisper.f00.model.Authority;

public interface AuthorityService extends BaseService<Authority> {
	/**
	 * ユーザー情報によって、該当権限を取得する
	 * @param userMap
	 * @return
	 */
	public List<Authority> listAuthoritiesByUserMap(Map<String, Object> userMap);
}

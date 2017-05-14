package jp.whisper.f00.dao;

import java.util.List;
import java.util.Map;

import jp.whisper.common.dao.BaseDAO;
import jp.whisper.f00.model.Authority;

public interface AuthorityDAO extends BaseDAO<Authority>
{
	/**
	 * ユーザー情報によって、該当権限を取得する
	 * @param userMap
	 * @return
	 */
	public List<Authority> listAuthoritiesByUserMap(Map<String, Object> userMap);
}



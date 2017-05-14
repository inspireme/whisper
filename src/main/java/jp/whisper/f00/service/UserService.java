package jp.whisper.f00.service;

import jp.whisper.common.service.BaseService;
import jp.whisper.f00.model.User;


public interface UserService extends BaseService<User>
{
	public void createUser() throws Exception;

	/**
	 *loginIdによって、唯一のユーザー情報を取得する
	 * @param loginId
	 * @return
	 */
	public User getByLoginId(String loginId);
}


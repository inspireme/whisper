package jp.whisper.f00.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.whisper.common.service.BusinessException;
import jp.whisper.common.service.impl.BaseServiceImpl;
import jp.whisper.f00.dao.UserDAO;
import jp.whisper.f00.model.Authority;
import jp.whisper.f00.model.User;
import jp.whisper.f00.service.AuthorityService;
import jp.whisper.f00.service.UserService;

@Service()
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	public UserServiceImpl(UserDAO userDao) {
		super();
		super.setBaseDAO(userDao);
	}

	@Autowired
	private AuthorityService authorityService;

	@Override
	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void createUser() throws Exception {
		// １．ユーザー情報登録する
		User user = new User();
		user.setId(2L);
		user.setLoginId("222222");
		user.setName("name2");
		this.baseDAO.insert(user);

		// ２．権限情報を付与する
		Authority auth = new Authority();
		// auth.setId(2L);
		auth.setAuthType("2");
		auth.setAuthUrl("/");
		auth.setCode("AUTH_PORTAL");
		auth.setDisplayName("ユーザー管理");
		authorityService.create(auth);

		// User user1 = this.get(12L);

		throw new BusinessException("トランザクションテスト");
	}

	@Override
	public User getByLoginId(String loginId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		List<User> userList = this.baseDAO.findBy(map);

		return (userList == null || userList.size() < 1) ? null : userList.get(0);
	}

}

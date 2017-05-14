package jp.whisper.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.whisper.common.util.StringUtil;
import jp.whisper.common.util.SystemUtil;
import jp.whisper.f00.model.Authority;
import jp.whisper.f00.service.AuthorityService;

@Controller
@RequestMapping("/portal")
public class PortalController {

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(ModelMap model) {

		return "portal";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView();
		String op = ServletRequestUtils.getStringParameter(request, "op");
		view.setViewName("login");
		if (StringUtil.isNotBlank(op)) {
			if ("error".equals(op)) {
				view.addObject("messages", "アカウントまたパスワードが間違っています");
			}
		}

		return view;
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(ModelMap model) {
		String loginId = SystemUtil.whoAmI();
		// カレントユーザーの権限情報を取得する
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("loginId", loginId);
		List<Authority> authorityList = authorityService.listAuthoritiesByUserMap(userMap);
		model.put("authorityList", authorityList);

		return "menu";
	}
}

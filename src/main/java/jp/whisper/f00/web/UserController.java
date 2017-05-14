package jp.whisper.f00.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.whisper.common.constant.WebNames;
import jp.whisper.common.util.JsonUtil;
import jp.whisper.common.util.ResponseUtil;
import jp.whisper.common.util.StringUtil;
import jp.whisper.f00.model.User;
import jp.whisper.f00.model.UserQuery;
import jp.whisper.f00.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, UserQuery query) {
		int pageSize = ServletRequestUtils.getIntParameter(request, WebNames.PAGESIZE, 10);
		int currentPage = ServletRequestUtils.getIntParameter(request, WebNames.CURRENT_PAGE, 1);
		String name = ServletRequestUtils.getStringParameter(request, "name", null);
		String loginId = ServletRequestUtils.getStringParameter(request, "loginId", null);

		query.setPageSize(pageSize);
		query.setCurrentPage(currentPage);
		query.setName(StringUtil.isBlank(name) ? null : name);
		query.setLoginId(StringUtil.isBlank(loginId) ? null : loginId);

		return new ModelAndView("f00/listUsers").addObject("userList", userService.query(query))
				.addObject("messages", "テスト").addObject("query", query);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, ModelMap model) {
		// model.addAttribute("user", user);
		if (StringUtil.isBlank(id)) {

		} else {// ユーザー情報を取得する
			User user = this.userService.get(Long.parseLong(id));
			model.put("user", user);
		}
		return "f00/editUser";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, User user) {
		// model.addAttribute("user", user);
		if (user.getId() == null) {
			this.userService.create(user);
		} else {
			this.userService.update(user);
		}

		return "redirect:/user/edit?id=" + user.getId();
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
		// model.addAttribute("user", user);
		this.userService.delete(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void detail(@PathVariable("id") Long id, HttpServletResponse res) throws IOException {
		User user = this.userService.get(id);
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("user", user);
		hm.put("success", Boolean.TRUE);
		ResponseUtil.writeJson(res, JsonUtil.toJSONString(hm));
	}

}

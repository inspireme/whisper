package jp.whisper.f00.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.whisper.f00.model.User;
import jp.whisper.f00.model.UserQuery;
import jp.whisper.f00.service.UserService;

@RestController
@RequestMapping("/userservice")
public class UserControllerRest {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> list() {
		UserQuery query = new UserQuery();

		query.setPageSize(10);
		query.setCurrentPage(1);

		return userService.query(query);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User detail(@PathVariable("id") Long id) throws IOException {
		return this.userService.get(id);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
		return "redirect:/user/edit?id=" + user.getId();
	}
}

package jp.whisper.f00.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.whisper.common.util.DevLog;
import jp.whisper.f00.model.Authority;
import jp.whisper.f00.service.AuthorityService;

@Controller
@RequestMapping("/authority")
public class AuthorityController
{
    @Autowired
	private AuthorityService authorityService;

    @RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("listAuthoritys");
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable String id, ModelMap model) {
		//model.addAttribute("user", user);
		return "detail";
	}

}


package com.knife.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.knife.interceptor.SessionInterceptor;
import com.knife.pojo.User;

@Before(SessionInterceptor.class)
public class IndexController extends Controller {
	private User user;

	@ClearInterceptor(ClearLayer.ALL)
	public void login() {
		user = getModel(User.class);
		user = User.dao.findFirst(
				"select * from kf_user where username=? and password=?",
				user.getStr(User.USERNAME), user.getStr(User.PASSWORD));
		JSONObject json = new JSONObject();
		if (user == null) {
			json.put("error", 1);
			json.put("msg", "用户名或密码错误~");
		} else {
			setSessionAttr("user", user);
			json.put("error", 0);
			json.put("msg", "登陆成功");
		}
		renderJson(json.toJSONString());
	}

	public void index() {
		if (getSessionAttr("user") != null) {
			render("index.html");
			//render("kf_index.html");
		} else {
			render("login.html");
		}
	}

}

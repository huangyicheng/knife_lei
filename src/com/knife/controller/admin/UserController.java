package com.knife.controller.admin;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.knife.interceptor.SessionInterceptor;
import com.knife.pojo.User;

@Before(SessionInterceptor.class)
public class UserController extends Controller {

	public void add() {
		render("add.html");
	}

	public void addUser() {
 		User user = getModel(User.class);
		user.save();
		setAttr("error", 0);
		setAttr("msg", "success");
		
		renderJson();
	}

	public void list() {
		Integer pageNum = getParaToInt("p", 1);
		Page<User> page = User.dao.paginate(pageNum, 10, "select * ","from kf_user");
		setAttr("page", page);
		render("list.html");
		
	}
}
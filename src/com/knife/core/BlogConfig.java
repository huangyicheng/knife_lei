package com.knife.core;

import java.util.TreeSet;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.knife.controller.CategorySuperController;
import com.knife.controller.IndexController;
import com.knife.controller.admin.ArticleController;
import com.knife.controller.admin.CategoryController;
import com.knife.controller.admin.ProjectController;
import com.knife.controller.admin.UserController;
import com.knife.handler.HtmlExtensionHandler;
import com.knife.pojo.Article;
import com.knife.pojo.CategorySub;
import com.knife.pojo.CategorySuper;
import com.knife.pojo.Comment;
import com.knife.pojo.Message;
import com.knife.pojo.Project;
import com.knife.pojo.User;

import freemarker.template.TemplateModelException;


public class BlogConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("a_little_config.txt");
		//me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setDevMode(true);
		me.setBaseViewPath("/WEB-INF/template");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",IndexController.class);
		me.add("/article", com.knife.controller.ArticleController.class,"article");
		me.add("/categorySuper", CategorySuperController.class,"/");
		//backend
		me.add("/admin", com.knife.controller.admin.IndexController.class,"admin");
		me.add("/admin/user", UserController.class, "admin/user");
		me.add("/admin/article", ArticleController.class, "admin/article");
		me.add("/admin/article/category", CategoryController.class,"admin/article/category");
		me.add("/admin/article/project", ProjectController.class,"admin/article/project");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		me.add(new EhCachePlugin());
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("kf_user", User.class);	// 映射blog 表到 Blog模型
		arp.addMapping("kf_article", Article.class);
		arp.addMapping("kf_category_sub", CategorySub.class);
		arp.addMapping("kf_category_super", CategorySuper.class);
		arp.addMapping("kf_project", Project.class);
		arp.addMapping("kf_message", Message.class);
		arp.addMapping("kf_comment", Comment.class);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) 
	{
		me.add(new HtmlExtensionHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
//	public static void main(String[] args) {
//		JFinal.start("WebRoot", 80, "/", 5);
//	}
//	public static void updateCategorySuperList() {
//		try {
//			FreeMarkerRender.getConfiguration().setSharedVariable(
//					"categorySuperList",
//					new TreeSet<CategorySuper>(CategorySuper.dao
//							.find("select * from kf_category_super")));
//		} catch (TemplateModelException e) {
//			//log.error("set freemarkerrender share variable categorySuperList failed",e);
//		}
//	}
}

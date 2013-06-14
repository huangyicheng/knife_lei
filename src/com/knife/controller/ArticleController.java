package com.knife.controller;


import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.knife.pojo.Article;
import com.knife.pojo.CategorySuper;
import com.knife.pojo.Comment;

public class ArticleController extends Controller {
	public void index() {
		final Integer articleId = getParaToInt(0);
		Article article =Article.dao.findById(articleId);
		Article.dao.clear().set("id", articleId).set("viewCount", article.getInt("viewCount") + 1).update();
		setAttr("article", article);
		
		List<CategorySuper> categorysupers = CategorySuper.dao.find("select * from kf_category_super");
		setAttr("CategorySupers", categorysupers);
		
		List<Article> hotsView = Article.dao.find("select id,title,viewCount from kf_article order by viewCount desc limit 0,5");
		setAttr("hotsView", hotsView);
		
		List<Record> records = Db.find("select count(*) num,year(createDateTime) year,month(createDateTime) month from kf_article group by year(createDateTime),month(createDateTime)");
		setAttr("catbytime",records);
		
		render("detail.html");
	}
	
	public void addComment(){
		Comment comment = getModel(Comment.class);
		comment.set("dateTime", new Date());
		comment.save();
		Article article = Article.dao.findById(comment.getInt(Comment.ARTICLE_ID));
		article.set("replyCount", article.getInt("replyCount") + 1).update();
//		CacheKit.remove("article", "id_" + comment.getInt(Comment.ARTICLE_ID));
//		CacheKit.remove("article", "recently_comments");
//		Email _email = new Email();
//		_email.setSubject("有来自" + comment.getStr(Comment.NICK) + "的新评论");
//		_email.setContent("<strong>内容</strong>:" + comment.getStr(Comment.CONTENT) + "<div><a href='http://abap.cloudfoundry.com/admin'>快去处理吧</a></div>");
//		_email.send();
		JSONObject json = new JSONObject();
		json.put("error", 0);
		json.put("msg", "success");
		renderJson(json.toJSONString());
	}
}

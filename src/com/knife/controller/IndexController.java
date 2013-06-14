package com.knife.controller;


import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.knife.pojo.Article;
import com.knife.pojo.CategorySuper;

public class IndexController extends Controller 
{
	public void index() 
	{
		final  Integer pageNum = getParaToInt("p", 1);
		Page<Article> page_news = Article.dao.paginate(pageNum, 2, 
				"select id,title,viewCount,replyCount,createDateTime,updateDateTime,content,tags,type", 
				" from kf_article order by id desc");
		setAttr("page_news", page_news);
//		List<Article> blog = Article.dao.find("select * from kf_article order by id desc limit 0,10");
//		setAttr("blogs", blog);
		
		List<CategorySuper> categorysupers = CategorySuper.dao.find("select * from kf_category_super");
		setAttr("CategorySupers", categorysupers);
		
		List<Article> hotsView = Article.dao.find("select id,title,viewCount from kf_article order by viewCount desc limit 0,5");
		setAttr("hotsView", hotsView);
		
		List<Record> records = Db.find("select count(*) num,year(createDateTime) year,month(createDateTime) month from kf_article group by year(createDateTime),month(createDateTime)");
		setAttr("catbytime",records);
		//render("kf_index.html");
		render("index.html");
	}
}

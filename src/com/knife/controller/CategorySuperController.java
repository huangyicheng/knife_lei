package com.knife.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.knife.core.BlogConstants;
import com.knife.pojo.Article;
import com.knife.pojo.CategorySuper;

public class CategorySuperController extends Controller
{
	public void index() 
	{
		final Integer categorySuperId = getParaToInt(0);
		Integer pageNum = getParaToInt("p", 1);
		
		Page<Article> page = Article.dao.paginate(pageNum, 2,
				"select id,title,viewCount,replyCount,createDateTime,updateDateTime,content,tags,type", 
				" from kf_article where categorySuperId=? order by id desc" , categorySuperId);
//		Page<Article> page = Article.dao.paginateByCache("article", "categorySuperId_" + categorySuperId + "_" + pageNum, pageNum, BlogConstants.PAGE_SIZE, 
//				"select a.* ", 
//				"from article a,category_sub c where a.finish = 1 and a.categorySubId = c.id and c.pId = ? order by id desc",
//				categorySuperId);
//		CategorySuper categorySuper = CacheKit.handle("article", "categorySuperId_" + categorySuperId, new IDataLoader() {
//			@Override
//			public Object load() {
//				return CategorySuper.dao.findById(categorySuperId);
//			}
//		}); 
		setAttr("page_news", page);
//		List<Article> blog = Article.dao.find("select * from kf_article order by id desc limit 0,10");
//		setAttr("blogs", blog);
		
		List<CategorySuper> categorysupers = CategorySuper.dao.find("select * from kf_category_super");
		setAttr("CategorySupers", categorysupers);
		
		List<Article> hotsView = Article.dao.find("select id,title,viewCount from kf_article order by viewCount desc limit 0,5");
		setAttr("hotsView", hotsView);
		render("kf_index.html");
	}

}

package com.knife.pojo;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jfinal.plugin.activerecord.Model;

public class CategorySuper extends Model<CategorySuper> implements Comparable<CategorySuper>{
	private static final long serialVersionUID = 510211481802210079L;
	public static final CategorySuper dao = new CategorySuper();
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String ORDER = "order";
	


	private Set<CategorySub> _categorySubList;
	public Set<CategorySub> getCategorySubList() {
		if (_categorySubList == null) {
			_categorySubList = new TreeSet<CategorySub>(CategorySub.dao.find("select * from kf_category_sub where pId = ?", getInt(ID)));
		}
		return _categorySubList;
	}

    private int _number;
    
	public int get_number() {
		List<Article> list = Article.dao.find("select * from kf_article where categorySuperId = ?",getInt(ID));
		_number = list.size();
		return _number;
	}
	@Override
	public int compareTo(CategorySuper o) {
		if(getInt(ORDER) >= o.getInt(ORDER))
			return 1;
		else
			return -1;
	}


}

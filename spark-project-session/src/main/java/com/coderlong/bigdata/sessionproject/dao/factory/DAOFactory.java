package com.coderlong.bigdata.sessionproject.dao.factory;

import com.coderlong.bigdata.sessionproject.dao.IAdBlacklistDAO;
import com.coderlong.bigdata.sessionproject.dao.IAdClickTrendDAO;
import com.coderlong.bigdata.sessionproject.dao.IAdProvinceTop3DAO;
import com.coderlong.bigdata.sessionproject.dao.IAdStatDAO;
import com.coderlong.bigdata.sessionproject.dao.IAdUserClickCountDAO;
import com.coderlong.bigdata.sessionproject.dao.IAreaTop3ProductDAO;
import com.coderlong.bigdata.sessionproject.dao.IPageSplitConvertRateDAO;
import com.coderlong.bigdata.sessionproject.dao.ISessionAggrStatDAO;
import com.coderlong.bigdata.sessionproject.dao.ISessionDetailDAO;
import com.coderlong.bigdata.sessionproject.dao.ISessionRandomExtractDAO;
import com.coderlong.bigdata.sessionproject.dao.ITaskDAO;
import com.coderlong.bigdata.sessionproject.dao.ITop10CategoryDAO;
import com.coderlong.bigdata.sessionproject.dao.ITop10SessionDAO;
import com.coderlong.bigdata.sessionproject.dao.impl.AdBlacklistDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.AdClickTrendDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.AdProvinceTop3DAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.AdStatDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.AdUserClickCountDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.AreaTop3ProductDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.PageSplitConvertRateDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.SessionAggrStatDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.SessionDetailDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.SessionRandomExtractDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.TaskDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.Top10CategoryDAOImpl;
import com.coderlong.bigdata.sessionproject.dao.impl.Top10SessionDAOImpl;

/**
 * DAO工厂类
 * @author Administrator
 *
 */
public class DAOFactory {


	public static ITaskDAO getTaskDAO() {
		return new TaskDAOImpl();
	}

	public static ISessionAggrStatDAO getSessionAggrStatDAO() {
		return new SessionAggrStatDAOImpl();
	}
	
	public static ISessionRandomExtractDAO getSessionRandomExtractDAO() {
		return new SessionRandomExtractDAOImpl();
	}
	
	public static ISessionDetailDAO getSessionDetailDAO() {
		return new SessionDetailDAOImpl();
	}
	
	public static ITop10CategoryDAO getTop10CategoryDAO() {
		return new Top10CategoryDAOImpl();
	}
	
	public static ITop10SessionDAO getTop10SessionDAO() {
		return new Top10SessionDAOImpl();
	}
	
	public static IPageSplitConvertRateDAO getPageSplitConvertRateDAO() {
		return new PageSplitConvertRateDAOImpl();
	}
	
	public static IAreaTop3ProductDAO getAreaTop3ProductDAO() {
		return new AreaTop3ProductDAOImpl();
	}
	
	public static IAdUserClickCountDAO getAdUserClickCountDAO() {
		return new AdUserClickCountDAOImpl();
	}
	
	public static IAdBlacklistDAO getAdBlacklistDAO() {
		return new AdBlacklistDAOImpl();
	}
	
	public static IAdStatDAO getAdStatDAO() {
		return new AdStatDAOImpl();
	}
	
	public static IAdProvinceTop3DAO getAdProvinceTop3DAO() {
		return new AdProvinceTop3DAOImpl();
	}
	
	public static IAdClickTrendDAO getAdClickTrendDAO() {
		return new AdClickTrendDAOImpl();
	}
	
}

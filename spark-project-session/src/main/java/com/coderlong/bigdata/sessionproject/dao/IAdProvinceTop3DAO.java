package com.coderlong.bigdata.sessionproject.dao;


import com.coderlong.bigdata.sessionproject.domain.AdProvinceTop3;

import java.util.List;

/**
 * 各省份top3热门广告DAO接口
 * @author Administrator
 *
 */
public interface IAdProvinceTop3DAO {

	void updateBatch(List<AdProvinceTop3> adProvinceTop3s);
	
}

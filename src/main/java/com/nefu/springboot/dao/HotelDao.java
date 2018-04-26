package com.nefu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nefu.springboot.vo.Hotel;
import com.nefu.springboot.vo.Produce;

/**
 * 对酒店的相关操作
 * @author   xzc
 * @since
 */
public interface HotelDao {
	
	public void addHotel(Map<String, Object> parm);
	
	public List<Map<String, Object>> getHotelInfo(@Param("id") int id);
	
	public void updateStatus(Map<String, Object> parm);
	
	public void updateHotel(Hotel hotel);
	
	public void addProduce(Map<String, Object> parm);
	
	public List<Map<String, Object>> getProInfo(@Param("id") int id);
	
	public void addProduceTime(Map<String, Object> parm);
	
	public void updatePro(Produce produce);
	
	public List<Map<String, Object>> getHotelProduceInfo(Map<String, Object> parm);
	
	public List<Map<String, Object>> getHotelProInfoById(Map<String, Object> parm);

}

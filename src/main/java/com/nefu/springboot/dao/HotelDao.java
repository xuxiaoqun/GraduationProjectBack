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
	
	public Map<String, Object> getHotelProInfoById(int hotel_id);
	
	/**
	 * 根据房型id和入住日期离店日期查找当前房型是否已满
	 * @param parm
	 * @return
	 */
	public Map<String, Object> getProInfoById(Map<String, Object> parm);
	
	/**
	 * 根据酒店id获取当前酒店的所有房型id
	 * @param hotel_id
	 * @return
	 */
	public List<Integer> getProduceIdByHotelId(int hotel_id);
	
	/**
	 * 根据酒店id查看当前酒店下的所有评价
	 * @param hotel_id
	 * @return
	 */
	public List<Map<String, Object>> getEvaluationByHotelId(int hotel_id);

}

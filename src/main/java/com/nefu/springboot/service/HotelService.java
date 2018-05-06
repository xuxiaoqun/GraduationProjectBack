package com.nefu.springboot.service;

import java.util.List;
import java.util.Map;

import com.nefu.springboot.vo.Hotel;
import com.nefu.springboot.vo.Produce;

/**
 * 酒店业务的相关操作
 * 
 * @author xzc
 * @since
 */
public interface HotelService {

	/**
	 * 添加酒店信息
	 * 
	 * @param parm
	 */
	public void save(Hotel hotel);

	/**
	 * 根据id查询酒店信息，若id为null，则查询全部酒店信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getHotelInfo(int id);

	/**
	 * 根据id删除酒店信息(实际上将该酒店信息以及该酒店下对应的房型信息的status状态给置为无效)
	 * 
	 * @param id
	 */
	public void deleteHotel(int id);

	/**
	 * 更新酒店信息
	 * 
	 * @param hotel
	 */
	public void updateHotel(Hotel hotel);

	/**
	 * 添加房型信息
	 * 
	 * @param produce
	 */
	public void saveProduce(Produce produce);

	/**
	 * 根据酒店id获取房型信息
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getProInfo(int id);

	/**
	 * 根据房型id将房型信息置为无效
	 * 
	 * @param id
	 */
	public void deleteProduce(int id);

	/**
	 * 更新对应的房型信息
	 * 
	 * @param produce
	 */
	public void updateProduce(Produce produce);

	/**
	 * 获取所有的酒店和酒店下面的房型的组合信息
	 */
	public List<Map<String, Object>> getHotelProduceInfo(String startDate, String endDate);

	/**
	 * 获取单个酒店下的酒店信息
	 * 
	 * @param hotel_id
	 * @return
	 */
	public Map<String, Object> getHotelProInfoById(int hotel_id);
	
	/**
	 * 根据酒店id和入住日期离店日期查找相关房型
	 * @param parm
	 * @return
	 */
	public List<Map<String, Object>> getProInfoById(String hotel_id,String arrivalDate,String leaveDate) throws Exception;

	/**
	 * 根据酒店id查看当前酒店下的所有评价
	 * @param hotel_id
	 * @return
	 */
	public List<Map<String, Object>> getEvaluationByHotelId(String hotel_id);
}

package com.nefu.springboot.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nefu.springboot.dao.HotelDao;
import com.nefu.springboot.service.HotelService;
import com.nefu.springboot.vo.Hotel;
import com.nefu.springboot.vo.Produce;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

	private static final Logger log = LoggerFactory.getLogger(HotelService.class);

	@Autowired
	HotelDao hotelDao;

	@Override
	public void save(Hotel hotel) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", hotel.getName());
		data.put("address", hotel.getAddress());
		data.put("star", hotel.getStar());
		data.put("phone", hotel.getPhone());
		String openTime = hotel.getOpenTime().replace("Z", " UTC");
		try {
			openTime = DateFormatUtils.format(DateUtils.parseDate(openTime, "yyyy-MM-dd'T'HH:mm:ss.SSS Z"),
					"yyyy-MM-dd");
		} catch (ParseException e) {
			openTime = null;
			log.info("营业日期异常：", e);
		}
		data.put("openTime", openTime);
		data.put("picture", getString(hotel.getPicture()));
		data.put("flag", getString(hotel.getFlag()));
		data.put("consumer_id", hotel.getConsumer_id());
		data.put("status", "Y");
		data.put("grade", "");
		hotelDao.addHotel(data);
	}

	@Override
	public List<Map<String, Object>> getHotelInfo(int id) {
		List<Map<String, Object>> hotelList = hotelDao.getHotelInfo(id);
		for (Map<String, Object> hotelMap : hotelList) {
			String[] pictures = MapUtils.getString(hotelMap, "picture").split(",");
			hotelMap.put("picture", pictures);
		}
		return hotelList;
	}

	/**
	 * 将字符串数组变为字符串 ","区分
	 * 
	 * @param str
	 * @return
	 */
	public String getString(String[] str) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			buffer.append(str[i] + ",");
		}
		return buffer.toString().substring(0, buffer.length() - 1);
	}

	@Override
	public void deleteHotel(int id) {
		// 将对于酒店信息置为无效
		Map<String, Object> hotel = new HashMap<String, Object>();
		hotel.put("table", "hotel");
		hotel.put("field", "hotel_id");
		hotel.put("id", id);
		hotel.put("status", "N");
		hotelDao.updateStatus(hotel);

		// 将该酒店对应的房型信息置为无效
		Map<String, Object> produce = new HashMap<String, Object>();
		produce.put("table", "produce");
		produce.put("field", "hotel_id");
		produce.put("id", id);
		produce.put("status", "N");
		hotelDao.updateStatus(produce);
	}

	@Override
	public void updateHotel(Hotel hotel) {
		hotelDao.updateHotel(hotel);

	}

	@Override
	public void saveProduce(Produce produce) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("hotel_id", produce.getHotel_id());
		data.put("houseType", produce.getHouseType());
		data.put("bedType", produce.getBedType());
		data.put("price", produce.getPrice());
		data.put("amount", produce.getAmount());
		data.put("picture", getString(produce.getPicture()));
		data.put("capacity", produce.getCapacity());
		data.put("flag", getString(produce.getFlag()));
		data.put("status", "Y");
		saveBitchProduce(data, produce.getDate(),produce.getAmount());
	}

	/**
	 * 根据房型的日期批量插入数据（间隔多少天就按照多少条记录插入）
	 * 
	 * @param data
	 * @param date
	 */
	private void saveBitchProduce(Map<String, Object> data, String[] date, String restAmount) {
		Date start;
		Date end;
		try {
			start = DateUtils.parseDate(date[0].replace("Z", " UTC"), "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			end = DateUtils.parseDate(date[1].replace("Z", " UTC"), "yyyy-MM-dd'T'HH:mm:ss.SSS Z");

			// 将房型信息插入produce表中（以一条记录的形式插入）
			data.put("startDate", DateFormatUtils.format(start, "yyyy-MM-dd"));
			data.put("endDate", DateFormatUtils.format(end, "yyyy-MM-dd"));
			hotelDao.addProduce(data);

			// 将对于房型的日期以间隔多少天就按照多少条记录插入produce_time表中
			int days = (int) ((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
			log.info("发布房型的日期间隔天数：" + days);

			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("produce_id", data.get("id"));
			for (int i = 0; i < days; i++) {
				parm.put("startDate", DateFormatUtils.format(DateUtils.addDays(start, i), "yyyy-MM-dd"));
				parm.put("endDate", DateFormatUtils.format(DateUtils.addDays(start, i + 1), "yyyy-MM-dd"));
				parm.put("isReserve", "N");
				parm.put("restAmount", restAmount);
				hotelDao.addProduceTime(parm);
			}
		} catch (ParseException e) {
			log.info("入住日期和离开日期异常：", e);
		}
	}

	@Override
	public List<Map<String, Object>> getProInfo(int id) {
		List<Map<String, Object>> proList = hotelDao.getProInfo(id);
		for (Map<String, Object> proMap : proList) {
			String[] pictures = MapUtils.getString(proMap, "picture").split(",");
			proMap.put("picture", pictures);
		}
		return proList;
	}

	@Override
	public void deleteProduce(int produce_id) {
		// 将该酒店对应的房型信息置为无效
		Map<String, Object> produce = new HashMap<String, Object>();
		produce.put("table", "produce");
		produce.put("field", "produce_id");
		produce.put("id", produce_id);
		produce.put("status", "N");
		hotelDao.updateStatus(produce);

	}

	@Override
	public void updateProduce(Produce produce) {
		hotelDao.updatePro(produce);
	}

	@Override
	public List<Map<String, Object>> getHotelProduceInfo() {
		List<Map<String, Object>> hotelProList = hotelDao.getHotelProduceInfo();
		for (Map<String, Object> hotelPro : hotelProList) {
			String[] pictures = MapUtils.getString(hotelPro, "hotel_picture").split(",");
			hotelPro.put("hotel_picture", pictures);
		}
		return hotelProList;
	}

	
	@Override
	public Map<String, Object> getHotelProInfoById(int hotel_id) {
		Map<String, Object> hotelPro = hotelDao.getHotelProInfoById(hotel_id);	
//			String[] hotel_picture = MapUtils.getString(hotelPro, "hotel_picture").split(",");
//			hotelPro.put("hotel_picture", hotel_picture);		
//			String[] pro_picture = MapUtils.getString(MapUtils.getMap(hotelPro, "pro_info"), "pro_picture").split(",");
//			MapUtils.getMap(hotelPro, "pro_info").put("pro_picture", pro_picture);
				
		return hotelPro;
	}

}

package com.nefu.springboot.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nefu.springboot.service.HotelService;
import com.nefu.springboot.util.FileUtils;
import com.nefu.springboot.vo.Hotel;
import com.nefu.springboot.vo.Produce;

@RestController
public class BusinessController {
	
	private static final Logger log = LoggerFactory.getLogger(BusinessController.class);
	
	@Autowired
	HotelService hotelService;

	@RequestMapping("/uploadFile")
	public String uploadFile(MultipartFile file, String hotelName) throws Exception {
		return FileUtils.saveFile(file, hotelName);
	}
	
	@RequestMapping("/releaseHotel")
	public Boolean releaseHotel(Hotel hotel) throws ParseException{
		hotelService.save(hotel);
		return true;
	}
	
	@RequestMapping("/getHotelInfo")
	public List<Map<String, Object>> getHotelInfo(String id){
		log.info("获取当前用户的id:" + id);
		return hotelService.getHotelInfo(id == null || id == "" ? 0 : Integer.valueOf(id));
	}
	
	@RequestMapping("/deleteHotel")
	public Boolean deleteHotel(String hotel_id){
		log.info("删除酒店信息的传参id:" + hotel_id);
		hotelService.deleteHotel( Integer.valueOf(hotel_id));
		return true;
	}
	
	@RequestMapping("/updateHotel")
	public Boolean updateHotel(Hotel hotel){
		log.info("更新酒店的信息:" + hotel);
		hotelService.updateHotel(hotel);
		return true;
	}
	
	@RequestMapping("/uploadHouseFile")
	public String uploadHouseFile(MultipartFile file, String hotelName, String houseName) throws Exception {
		return FileUtils.saveFile(file, hotelName, houseName);
	}
	
	@RequestMapping("/releaseProduct")
	public Boolean releaseProduct(Produce produce){
		log.info("发布酒店房型的信息:" + produce);
		hotelService.saveProduce(produce);
		return true;
	}
	
	@RequestMapping("/getProInfo")
	public List<Map<String, Object>> getProInfo(String id){
		return hotelService.getProInfo(id == null || id == "" ? 0 : Integer.valueOf(id));
	}
	
	@RequestMapping("/deletePro")
	public Boolean deletePro(String produce_id){
		log.info("删除酒店房型信息的传参id:" + produce_id);
		hotelService.deleteProduce(Integer.valueOf(produce_id));
		return true;
	}
	
	@RequestMapping("/updatePro")
	public Boolean updatePro(Produce produce){
		log.info("更新房型的信息:" + produce);
		hotelService.updateProduce(produce);
		return true;
	}
	
	@RequestMapping("/getHotelProInfo")
	public List<Map<String, Object>> getHotelProduceInfo() {
		return hotelService.getHotelProduceInfo();
	}
	
	@RequestMapping("/getHotelProInfoById")
	public Map<String, Object> getHotelProInfoById(String hotel_id) {
		System.out.println(hotel_id);
		return hotelService.getHotelProInfoById(Integer.valueOf(hotel_id));
	}
}

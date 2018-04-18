package com.nefu.springboot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件的相关工具类
 * @author   xzc
 * @since
 */
public class FileUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 将文件保存在本地磁盘的指定路径
	 * @param file 上传的文件
	 * @param names 文件的文件保存的路径名(/酒店名/房型名)
	 * @return 相对于服务器的文件地址
	 * @throws Exception
	 */
	public static String saveFile(MultipartFile file, String...names) throws Exception{
		
		//相对于服务器的相对路径
		String relativePath = "picture/";
		String filePath = null;
		for(int i = 0; i < names.length; i++){
			relativePath += names[i] + "/";
		}
		//绝对路径
//		String path = System.getProperty("user.dir") + "/src/main/resources/static/" + relativePath;
		String path = "D:/upload/" + relativePath;
		log.info("当前图片存放的目录:" + path);
		
		File newFile = new File(path);
		//判断当前文件路径是否存在，不存在则创建
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		if (file.getOriginalFilename() != null) {
			String fileNameDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
			//图片命名yyyyMMdd_fileName.jpg
			
			relativePath += fileNameDate + "_" + file.getOriginalFilename();
			
			filePath = path + fileNameDate + "_" + file.getOriginalFilename();
			File file1 = new File(filePath);
			
			InputStream fileSource = file.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(file1);
			byte b[] = new byte[1024];
			int n;
			while ((n = fileSource.read(b)) != -1) {
				outputStream.write(b, 0, n);
			}
			// 关闭输入输出流
			outputStream.close();
			fileSource.close();
		}
		log.info("文件相对于服务器的路径地址：" + relativePath);
		return relativePath;
	}
}

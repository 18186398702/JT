package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileServiceImpl implements FileService{
	@Value("${image.localpath}")
	private String fileDir ; //文件存储根目录
	@Value("${image.urlpath}")
	private String urlPath ; //回显访问路径
	/**
	 * 1.判断是否为图片类型 jpg|png|git
	 * 2.判断是否为恶意程序
	 * 3.分类存储
	 * 4.不能让图片重名  UUID()+随机数
	 * 5.实现文件上传
	 */
	
	@Override
	public PicUploadResult fileUpload(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		// 1.获取文件名称
		String fileName = uploadFile.getOriginalFilename();
		//将图片信息转化为小写字符
		fileName = fileName.toLowerCase();
		// 2.获取数据类型
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		//一.判断是否为图片类型
		if (fileName.matches("^.+\\.[jpg|png|gif]$")) {
			//图片类型不匹配
			result.setError(1);
			return result;
		}
		//二.判断是否为恶意程序
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			//如果是图片文件则有高度和宽度
			int width = image.getWidth();
			int height = image.getHeight();
			if (width==0||height==0) {
				result.setError(1);
				return result;
			}
			//三. 表示图片正常，进行分类存储
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//定义文件存储目录
			String localPathDir = fileDir + dateDir;
			File dataDirFile = new File(localPathDir);
			if (!dataDirFile.exists()) {
				dataDirFile.mkdirs();
			}
			//四.生成文件唯一名称
			String uuid = UUID.randomUUID().toString().replace("-", "");
			int randomNum = new Random().nextInt(1000);
			//得到文件名称：xxxxxx110.jpg
			String imageFileName = uuid+randomNum+fileType;
			//五。实现文件上传
			String realFilePath = localPathDir+"/"+imageFileName;
			File imageFile = new File(realFilePath);
			uploadFile.transferTo(imageFile);
			//六.实现图片的回显
			result.setHeight(height+"");
			result.setWidth(width+"");
			String url = urlPath + dateDir + "/" + imageFileName;
			result.setUrl(url);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		return result;
	}

}

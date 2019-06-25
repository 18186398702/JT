package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 褰撴枃浠朵笂浼犲畬鎴愬悗锛岄噸瀹氬悜鍒版枃浠朵笂浼犻〉闈�
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * 
	 */
	@RequestMapping("fileDemo")
	public String fileDemo(MultipartFile image) throws IllegalStateException, IOException {
		String dir = "E:/360Downloads/uploadFiles";//瀹氫箟鏂囦欢澶瑰悕绉�
		//鑾峰彇鏂囦欢鍚嶇О
		String fileName = image.getOriginalFilename();
		//鍒ゆ柇鏂囦欢澶规槸鍚﹀瓨鍦�
		File file = new File(dir);
		if (!file.exists()) {
			//濡傛灉涓嶅瓨鍦ㄦ柊寤烘枃浠跺す
			file.mkdirs();
		}
		//涓婁紶鍥剧墖
		image.transferTo(new File(dir+"/"+fileName));
		return "redirect:/File.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload(MultipartFile uploadFile) {
		return fileService.fileUpload(uploadFile);
		
	}
	
}

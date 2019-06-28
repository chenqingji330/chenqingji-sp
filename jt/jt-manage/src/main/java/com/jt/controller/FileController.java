package com.jt.controller;

import java.io.File;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	 
	
	/**
	 * 1获取用户得文件信息 包含:名称 
	 * 2 指定文件上传的路径 判断文件是否存在,
	 * 3实现文件的上传
	 * @param fileImage
	 * @return
	 * @throws Exception 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws Exception {
		
		
		//1获取input标签中的name属性
		String inputName=fileImage.getName();
		System.out.println(inputName);
		//2获取文件名称
		String fileName = fileImage.getOriginalFilename();
		
		//3 定义文件夹路径
		File fileDir = new File("D:/1-jt/image");
		if(!fileDir.exists()) {
			//创建文件夹
			fileDir.mkdirs();
			
		}
		//4 实现文件的上传
		fileImage.transferTo(new File("D:/1-jt/image/"+fileName));
		
		return "redirect:file.jsp";
		
		
	}
	
	/**
	 * 1 获取图片名称
	 * 2 校验是否为图片类型 jpg/png/gif
	 * 3校验是否为恶意程序 木马
	 * 4份文件保存:按照时间存储
	 * 5防止重名:UUID32位16进制
	 * @param uploadfile
	 * @return
	 */
//	@RequestMapping("/pic/upload")
//	@ResponseBody
//	public ImageVo fileUpload(MultipartFile uploadfile) {
//		ImageVo fileUpload = fileService.fileUpload( uploadfile);
//		return fileUpload;
//
//	
//		
//	}
	
	//实现文件上传
		@RequestMapping("/pic/upload")
		@ResponseBody
		public ImageVo uploadFile(MultipartFile uploadFile) {
			ImageVo updateFile = fileService.updateFile(uploadFile);
			return updateFile;
		}
	 

}

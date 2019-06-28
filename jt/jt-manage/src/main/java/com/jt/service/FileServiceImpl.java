package com.jt.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVo;


@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	@Value("${image.localDirPath}")
	private String localDirPath;
   @Value("${image.urlPath}")
    private String urlPath;

	@Override
	public ImageVo  updateFile(MultipartFile uploadFile)  {
//		ImageVo imageVo = new ImageVo();
//		//1 获取图片名称
//		String fileName = uploadfile.getOriginalFilename();
//		fileName=fileName.toLowerCase();
//		//2校验图片类型,使用正则表达式判断字符串
//		//正则表达式
//		//
//	
//		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
//			imageVo.setError(1);
//			return imageVo;}
//		
//		//3判断是否为恶意程序   图片模板
//		try {
//			BufferedImage bufferedImage=ImageIO.read(uploadfile.getInputStream());
//			int width=bufferedImage.getWidth();
//			int height = bufferedImage.getHeight();
//			if(width==0||height==0) {
//				imageVo.setError(1);
//				return imageVo;
//				
//			}
//			
//			//4时间转化为字符串
//			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//			
//			//5准备文件夹
//		String localDir=localDirPath+dateDir;
//		File dirFile = new File(localDir);
//		if(!dirFile.exists()) {
//			dirFile.mkdirs();
//			
//		}
//		
//		//6 实现文件的上传
//		String uuid = UUID.randomUUID().toString().replace("-", "");
//		String fileType  = fileName.substring(fileName.lastIndexOf("."));
//		//拼接新的文件名称
//		//D:/1-jt/image/yyyy/MM/dd/文件名称.类型
//		String realLocalPath = localDir+"/"+uuid+fileType;
//		
//		//7.2完成文件上传
//		uploadfile.transferTo(new File(realLocalPath));
//		//将文件文件信息回传给页面
//		imageVo.setError(0)
//		   .setHeight(height)
//		   .setWidth(width)
//		   .setUrl("https://img14.360buyimg.com/n0/jfs/t1/32386/3/9341/287224/5ca5a2afEb0d96c09/b5315f160ca38265.jpg");
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			imageVo.setError(1);
//			return imageVo;
//			
//		}
//		
//			
//		
//		return imageVo;
//		
//	}

		ImageVo imageVO = new ImageVo();
		//1.获取图片名称  a.jpg  A.JPG
		String fileName = uploadFile.getOriginalFilename();
		//将字符统一转化为小写
		fileName = fileName.toLowerCase();
		
		//2.校验图片类型  使用正则表达式判断字符串.
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			imageVO.setError(1); //表示上传有无
			return imageVO;
		}

		//3.判断是否为恶意程序
		try {
			BufferedImage bufferedImage = 
				ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0 || height ==0) {
				imageVO.setError(1);
				return imageVO;
			}
			
			//4.时间转化为字符串 2019/5/31
			String dateDir = 
					new SimpleDateFormat("yyyy/MM/dd")
					.format(new Date());
			
			//5.准备文件夹  D:/1-jt/image/yyyy/MM/dd
			String localDir = localDirPath + dateDir;
			File dirFile = new File(localDir);
			if(!dirFile.exists()) {
				//如果文件不存在,则创建文件夹
				dirFile.mkdirs();
			}
			//b8a7ff05-8356-11e9-9997-e0d55e0fcfd8
			//6.使用UUID定义文件名称 uuid.jpg
			String uuid = 
			UUID.randomUUID().toString().replace("-","");
			//图片类型 a.jpg 动态获取 ".jpg"
			String fileType = 
			fileName.substring(fileName.lastIndexOf("."));
			
			//拼接新的文件名称
			//D:/1-jt/image/yyyy/MM/dd/文件名称.类型
			String realLocalPath = localDir+"/"+uuid+fileType;
			
			//7.2完成文件上传
			uploadFile.transferTo(new File(realLocalPath));
			
			//8虚拟地址
			
			String realUrlPath=urlPath+dateDir+"/"+uuid+fileType;
			
			
			//将文件文件信息回传给页面
			imageVO.setError(0)
				   .setHeight(height)
				   .setWidth(width)
				   .setUrl(realUrlPath);
				//暂时写死.后期维护.
		} catch (Exception e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}

		return imageVO;
	}

		
		

}
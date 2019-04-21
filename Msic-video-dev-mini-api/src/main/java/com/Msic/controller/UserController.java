package com.Msic.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Msic.pojo.Users;
import com.Msic.pojo.UsersReport;
import com.Msic.pojo.vo.PublisherVideo;
import com.Msic.pojo.vo.UsersVO;
import com.Msic.service.UserService;
import com.Msic.utils.MsicJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




@RestController
@Api(value="用户相关业务的接口",tags= {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController{
	
	@Autowired
	private UserService userService;
	
	
	@ApiOperation(value="用户上传头像",notes="用户上传头像的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping("/uploadFace")
	public MsicJSONResult uploadFace(String userId,@RequestParam("file") MultipartFile[] files) throws Exception{

		// 文件保存的命名空间
		String fileSpace = "D:/Program Files/Msic_videos_dev";
		//保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/face";
		
		if(StringUtils.isBlank(userId)) {
			return MsicJSONResult.errorMsg("用户id不能为空...");
		}

		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if(files != null && files.length > 0 ) {
				
				 String fileName = files[0].getOriginalFilename();
				 if (StringUtils.isNotBlank(fileName)) {
					//文件上传的最终保存路径
					 String finalFacePath = fileSpace  + uploadPathDB + "/" + fileName;
					 //设置数据库保存的路径
					 uploadPathDB +=("/" + fileName);
					 
					 File outFile= new File(finalFacePath);
					 if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						 //创建父文件夹
						 outFile.getParentFile().mkdirs();
					 }
					 
					 fileOutputStream = new FileOutputStream(outFile);
					 inputStream = files[0].getInputStream();
					 IOUtils.copy(inputStream, fileOutputStream);
				} 
				 
			}
			else {
				return MsicJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsicJSONResult.errorMsg("上传出错...");
		}finally {
			if(fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
				
			}
		}
		
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		userService.updateUserInfo(user);
		
		return MsicJSONResult.ok(uploadPathDB);
		
	}
	
	@ApiOperation(value="查询用户信息",notes="查询用户信息")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping("/query")
	public MsicJSONResult query(String userId , String fanId) throws Exception{

		
		
		if(StringUtils.isBlank(userId)) {
			return MsicJSONResult.errorMsg("用户id不能为空...");
		}

		Users userInfo = userService.queryUserInfo(userId);
		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userInfo, usersVO);
		
		
		usersVO.setFollow(userService.queryIfFollow(userId, fanId));
		
		return MsicJSONResult.ok(usersVO);
		
	}
	
	@PostMapping("/queryPublisher")
	public MsicJSONResult queryPublisher(String loginUserId, String videoId, String publishUserId) throws Exception{

		
		
		if(StringUtils.isBlank(publishUserId)) {
			return MsicJSONResult.errorMsg("");
		}

		// 1.查询视频发布者的信息
		Users userInfo = userService.queryUserInfo(publishUserId);
		UsersVO publisher = new UsersVO();
		BeanUtils.copyProperties(userInfo, publisher);
		
		
		// 2.查询当前登陆者和视频的点赞关系
		boolean usersLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
		
		PublisherVideo bean = new PublisherVideo();
		bean.setPublisher(publisher);
		bean.setUserLikeVideo(usersLikeVideo);
		
		return MsicJSONResult.ok(bean);
		
	}
	
	
	@PostMapping("/beyourfans")
	public MsicJSONResult beyourfans(String userId, String fanId) throws Exception{

		
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
			return MsicJSONResult.errorMsg("");
		}

		userService.saveUserFanRelation(userId, fanId);
		
		return MsicJSONResult.ok("关注成功！");
		
	}
	
	@PostMapping("/dontbeyourfans")
	public MsicJSONResult dontbeyourfans(String userId, String fanId) throws Exception{

		
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
			return MsicJSONResult.errorMsg("");
		}

		userService.deleteUserFanRelation(userId, fanId);
		
		return MsicJSONResult.ok("取消关注成功！");
		
	}
	
	@PostMapping("/reportUser")
	public MsicJSONResult reportUser(@RequestBody UsersReport usersReport) throws Exception {
		
		// 保存举报信息
		userService.reportUser(usersReport);
		
		return MsicJSONResult.errorMsg("举报成功...有你平台变得更美好...");
	}
}

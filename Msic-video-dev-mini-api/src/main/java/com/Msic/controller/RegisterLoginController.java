package com.Msic.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Msic.pojo.Users;
import com.Msic.service.UserService;
import com.Msic.utils.MD5Utils;
import com.Msic.utils.MsicJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




@RestController
@Api(value="用户注册的接口",tags= {"注册和登录的controller"})
public class RegisterLoginController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户登录",notes="用户注册的接口")
	@PostMapping("/regist")
	public MsicJSONResult regist(@RequestBody Users user) throws Exception{
		
		//1.判断用户名和密码必须不为空
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
			return MsicJSONResult.errorMsg("用户名和密码不能为空");
		}
		
		
		//2.判断用户名是否存在
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		
		//3.保存用户，注册信息
		if(!usernameIsExist) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCount(0);
			user.setReceiveLikeCounts(0);
			user.setFollowCounts(0);
			userService.saveUser(user);
		}else {
			return MsicJSONResult.errorMsg("用户名已存在，请换一个再试");
		}
		
		
		return MsicJSONResult.ok();
	}
	
}

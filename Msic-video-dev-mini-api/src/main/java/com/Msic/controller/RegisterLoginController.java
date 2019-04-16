package com.Msic.controller;


import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Msic.pojo.Users;
import com.Msic.pojo.vo.UsersVO;
import com.Msic.service.UserService;
import com.Msic.utils.MD5Utils;
import com.Msic.utils.MsicJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




@RestController
@Api(value="用户注册的接口",tags= {"注册和登录的controller"})
public class RegisterLoginController extends BasicController{
	
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
		
		user.setPassword("");
		
		/*
		 * String uniqueToken = UUID.randomUUID().toString();
		 * redis.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 *
		 * 30);
		 * 
		 * UsersVO userVO = new UsersVO(); BeanUtils.copyProperties(user, userVO);
		 * userVO.setUserToken(uniqueToken);
		 */
		
		UsersVO userVO = setUserRedisSessionToken(user);
		
		return MsicJSONResult.ok(userVO);
	}
	
	public UsersVO setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);
		userVO.setUserToken(uniqueToken);
		return userVO;
	}
	
	@ApiOperation(value="用户登录",notes="用户登录的接口")
	@PostMapping("/login")
	public MsicJSONResult login(@RequestBody Users user) throws Exception{
		String username = user.getUsername();
		String password = user.getPassword();
		
//		Thread.sleep(3000);
		
		//1.判断用户名和密码必须不为空
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
			return MsicJSONResult.errorMsg("用户名和密码不能为空");
		}
		
		
		//2.判断用户名是否存在
		Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(user.getPassword()));
		
		//3.保存用户，注册信息
		if(userResult != null) {
			userResult.setPassword("");
			UsersVO userVO = setUserRedisSessionToken(userResult);
			return MsicJSONResult.ok(userVO);
		}else {
			return MsicJSONResult.errorMsg("用户名或密码不正确，请重试...");
		}
		
	}
	
	@ApiOperation(value="用户注销",notes="用户注销的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping("/logout")
	public MsicJSONResult logout(String userId) throws Exception{
		redis.del(USER_REDIS_SESSION + ":" + userId);
		return MsicJSONResult.ok();
		
	}
	
}

package com.Msic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.Msic.utils.RedisOperator;



@RestController
public class BasicController {
	
	@Autowired
	public RedisOperator redis;
	
	public static final String USER_REDIS_SESSION = "user-redis-session";
	
	// 文件保存的命名空间
	public static final String FILE_SAPCE = "D:/Program Files/Msic_videos_dev";
	
	//ffmpeg所在目录
	public static final String FFMPEG_EXE = "D:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe";
}

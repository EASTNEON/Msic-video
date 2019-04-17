package com.Msic.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Msic.service.BgmService;
import com.Msic.utils.MsicJSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




@RestController
@Api(value="背景音乐业务的接口", tags= {"背景音乐业务的controller"})
@RequestMapping("/bgm")
public class BgmController {
	
	private BgmService bgmService;
	
	@ApiOperation(value="获取背景音乐列表",notes="获取背景音乐列表的接口")
	@PostMapping("/list")
	public MsicJSONResult list() {
		return MsicJSONResult.ok(bgmService.queryBgmList());
	}
	
}
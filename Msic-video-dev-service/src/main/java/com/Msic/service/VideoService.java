package com.Msic.service;


import java.util.List;

import com.Msic.pojo.Bgm;
import com.Msic.pojo.Videos;
import com.Msic.utils.PagedResult;



public interface VideoService {

    
	
	/**
     * @Description:保存视频
     */
	public String saveVideo(Videos video); 
	

	/**
     * @Description:修改视频的封面
     */
	public String updateVideo(String videoId, String coverPath); 

	/**
     * @Description:分页查询视频列表
     */
	public PagedResult getAllVideos(Integer page, Integer pageSize);
	
}

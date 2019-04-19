package com.Msic.service;


import java.util.List;

import com.Msic.pojo.Bgm;
import com.Msic.pojo.Videos;



public interface VideoService {

    
	
	/**
     * @Description:保存视频
     */
	public String saveVideo(Videos video); 
	

	/**
     * @Description:修改视频的封面
     */
	public String updateVideo(String videoId, String coverPath); 

	
}

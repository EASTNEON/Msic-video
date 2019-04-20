package com.Msic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.Msic.pojo.Videos;
import com.Msic.pojo.vo.VideosVO;
import com.Msic.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {

	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
	
	/**
	 * @Description: 对视频喜欢的数量的累加
	 * */
	public void addVideoLikeCount(String videoId);
	
	/**
	 * @Description: 对视频喜欢的数量的累减
	 * */
	public void reduceVideoLikeCount(String videoId);
}
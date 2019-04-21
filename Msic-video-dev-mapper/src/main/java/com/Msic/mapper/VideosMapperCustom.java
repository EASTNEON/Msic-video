package com.Msic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.Msic.pojo.Videos;
import com.Msic.pojo.vo.VideosVO;
import com.Msic.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {


	/**
	 * @Description: 条件查询所有视频列表
	 */
	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc, @Param("userId") String userId);
	
	/**
	 * @Description: 查询关注的视频
	 */
	public List<VideosVO> queryMyFollowVideos(String userId);
	
	/**
	 * @Description: 查询点赞视频
	 */
	public List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);
	
	/**
	 * @Description: 对视频喜欢的数量的累加
	 * */
	public void addVideoLikeCount(String videoId);
	
	/**
	 * @Description: 对视频喜欢的数量的累减
	 * */
	public void reduceVideoLikeCount(String videoId);
}
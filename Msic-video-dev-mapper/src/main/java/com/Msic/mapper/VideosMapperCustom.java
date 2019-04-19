package com.Msic.mapper;

import java.util.List;

import com.Msic.pojo.Videos;
import com.Msic.pojo.vo.VideosVO;
import com.Msic.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {

	public List<VideosVO> queryAllVideos();
}
package com.Msic.service.impl;



import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.Msic.mapper.VideosMapper;
import com.Msic.mapper.VideosMapperCustom;
import com.Msic.pojo.Videos;
import com.Msic.pojo.vo.VideosVO;
import com.Msic.service.VideoService;
import com.Msic.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		String id = sid.nextShort();
		video.setId(id);
		
		videosMapper.insertSelective(video);
		
		return id;
	}


	@Override
	public String updateVideo(String videoId, String coverPath) {
		
		Videos video = new Videos();
		video.setId(videoId);
		video.setCoverPath(coverPath);
		
		videosMapper.updateByPrimaryKeySelective(video);
		return null;
	}


	@Override
	public PagedResult getAllVideos(Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		
		List<VideosVO> list= videosMapperCustom.queryAllVideos();
		
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pageResult = new PagedResult();
		pageResult.setPage(page);
		pageResult.setTotal(pageList.getPages());
		pageResult.setRows(list);
		pageResult.setRecords(pageList.getTotal());
		
		return pageResult;
	}

}

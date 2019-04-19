package com.Msic.service.impl;



import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.Msic.mapper.VideosMapper;

import com.Msic.pojo.Videos;

import com.Msic.service.VideoService;



@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
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

}

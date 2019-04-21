package com.Msic.service.impl;



import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Msic.mapper.SearchRecordsMapper;
import com.Msic.mapper.UsersLikeVideoMapper;
import com.Msic.mapper.UsersMapper;
import com.Msic.mapper.VideosMapper;
import com.Msic.mapper.VideosMapperCustom;
import com.Msic.pojo.SearchRecords;
import com.Msic.pojo.UsersLikeVideo;
import com.Msic.pojo.Videos;
import com.Msic.pojo.vo.VideosVO;
import com.Msic.service.VideoService;
import com.Msic.utils.PagedResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;




@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	
	@Autowired
	private UsersLikeVideoMapper usersLikeVideoMapper;
	
	
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


	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
		
		//保存热搜词
		String desc = video.getVideoDesc();
		String userId = video.getUserId();
		if(isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			String recordId = sid.nextShort();
			record.setId(recordId);
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page, pageSize);
		
		List<VideosVO> list= videosMapperCustom.queryAllVideos(desc, userId);
		
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pageResult = new PagedResult();
		pageResult.setPage(page);
		pageResult.setTotal(pageList.getPages());
		System.out.println("totalpages:" + pageList.getPages());
		pageResult.setRows(list);
		pageResult.setRecords(pageList.getTotal());
		
		return pageResult;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		System.out.println("totalpages:" + pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);
				
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(list);
		pagedResult.setPage(page);
		pagedResult.setRecords(pageList.getTotal());
		
		return pagedResult;
	}


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getHotwords() {
		
		return searchRecordsMapper.getHotwords();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
		//1. 保存用户和视频的喜欢点赞关联关系表
		String likeId = sid.nextShort();
		
		UsersLikeVideo ulv = new UsersLikeVideo();
		ulv.setId(likeId);
		ulv.setUserId(userId);
		ulv.setVideoId(videoId);
		usersLikeVideoMapper.insert(ulv);
		
		//2. 视频喜欢数量累加
		videosMapperCustom.addVideoLikeCount(videoId);
		System.out.println(videoId+ "2");
		
		//3.  用户受喜欢数量的累加
		usersMapper.addReceiveLikeCount(videoCreaterId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
		//1. 删除用户和视频的喜欢点赞关联关系表
		Example example= new Example(UsersLikeVideo.class);
		Criteria crateria = example.createCriteria();
		
		crateria.andEqualTo("userId", userId);
		crateria.andEqualTo("videoId", videoId);
		
		usersLikeVideoMapper.deleteByExample(example);
		
				
		//2. 视频喜欢数量累减
		videosMapperCustom.reduceVideoLikeCount(videoId);
				
		//3.  用户受喜欢数量的累减
		usersMapper.reduceReceiveLikeCount(videoCreaterId);
		
	}

}

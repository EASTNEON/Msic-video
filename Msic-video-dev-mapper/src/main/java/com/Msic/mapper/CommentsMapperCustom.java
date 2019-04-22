package com.Msic.mapper;

import java.util.List;

import com.Msic.pojo.Comments;
import com.Msic.pojo.vo.CommentsVO;
import com.Msic.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}
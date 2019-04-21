package com.Msic.mapper;

import com.Msic.pojo.Users;
import com.Msic.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

	/**
	 * @Description: 用户受喜欢数累加
	 * */
	public void addReceiveLikeCount(String userId);

	/**
	 * @Description: 用户受喜欢数累减
	 * */
	public void reduceReceiveLikeCount(String userId);
	
	
	/**
	 * @Description: 增加粉丝数量
	 * */
	public void addFansCount(String userId);
	
	/**
	 * @Description: 减少粉丝数量
	 * */
	public void reduceFansCount(String userId);
	
	/**
	 * @Description: 增加关注数量
	 * */
	public void addFollowersCount(String userId);
	
	/**
	 * @Description: 减少关注数量
	 * */
	public void reduceFollowersCount(String userId);
}
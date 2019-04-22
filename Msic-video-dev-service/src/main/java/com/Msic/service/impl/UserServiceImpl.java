package com.Msic.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Msic.mapper.CommentsMapper;
import com.Msic.mapper.UsersFansMapper;
import com.Msic.mapper.UsersLikeVideoMapper;
import com.Msic.mapper.UsersMapper;
import com.Msic.mapper.UsersReportMapper;
import com.Msic.pojo.Comments;
import com.Msic.pojo.Users;
import com.Msic.pojo.UsersFans;
import com.Msic.pojo.UsersLikeVideo;
import com.Msic.pojo.UsersReport;
import com.Msic.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private UsersFansMapper usersFansMapper;
	
	@Autowired
	private UsersLikeVideoMapper usersLikeVideoMapper;
	
	
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private UsersReportMapper usersReportMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		
		Users user = new Users();
		user.setUsername(username);
		
		Users result = userMapper.selectOne(user);
		return result == null ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users user) {
		
		String userId = sid.nextShort();
		user.setId(userId);
		userMapper.insert(user);

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String password) {

		Example userExample = new Example(Users.class);
		//通过Example创建Criteria(接口)，Criteria有andEqualTo，like，大于小于等方法
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", password);
		Users result = userMapper.selectOneByExample(userExample);
		
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(Users user) {
		Example userExample = new Example(Users.class);
		//通过Example创建Criteria(接口)，Criteria有andEqualTo，like，大于小于等方法
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", user.getId());
		userMapper.updateByExampleSelective(user, userExample);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		
		Example userExample = new Example(Users.class);
		//通过Example创建Criteria(接口)，Criteria有andEqualTo，like，大于小于等方法
		Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("id", userId);
		Users user = userMapper.selectOneByExample(userExample);
		
		return user;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean isUserLikeVideo(String userId, String videoId) {
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return false;
		}
		
		Example example = new Example(UsersLikeVideo.class);
		//通过Example创建Criteria(接口)，Criteria有andEqualTo，like，大于小于等方法
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId",userId);
		criteria.andEqualTo("videoId",videoId);
		
		List<UsersLikeVideo> list = usersLikeVideoMapper.selectByExample(example);
		
		if(list != null && list.size() > 0) {
			return true;
		}
		
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUserFanRelation(String userId, String fanId) {
		
		String relId = sid.nextShort();
		
		UsersFans userFan = new UsersFans();
		userFan.setId(relId);
		userFan.setUserId(userId);
		userFan.setFanId(fanId);
		
		usersFansMapper.insert(userFan);
		userMapper.addFansCount(userId);
		userMapper.addFollowersCount(fanId);
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteUserFanRelation(String userId, String fanId) {
		
		Example example = new Example(UsersFans.class);
		//通过Example创建Criteria(接口)，Criteria有andEqualTo，like，大于小于等方法
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId",userId);
		criteria.andEqualTo("fanId",fanId);
		
		usersFansMapper.deleteByExample(example);
		
		userMapper.reduceFansCount(userId);
		userMapper.reduceFollowersCount(fanId);
		
	}

	@Override
	public boolean queryIfFollow(String userId, String fanId) {
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("userId",userId);
		criteria.andEqualTo("fanId",fanId);
		
		List<UsersFans> list = usersFansMapper.selectByExample(example);
		
		if(list != null && !list.isEmpty() && list.size() > 0) {
			return true;
		}
		
		
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void reportUser(UsersReport userReport) {
		
		String urId = sid.nextShort();
		userReport.setId(urId);
		userReport.setCreateDate(new Date());
		
		usersReportMapper.insert(userReport);
	}

	
	
}

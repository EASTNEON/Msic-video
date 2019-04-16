package com.Msic.pojo.vo;

import java.util.Date;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="用户对象",description="这是用户对象")
public class UsersVO {
    
	//前端不一定要传入或显示
	@ApiModelProperty(hidden=true)
    private String id;

	@ApiModelProperty(hidden=true)
    private String userToken;
	
    /**
     * 用户名
     * */
  //前端要传入和显示
    @ApiModelProperty(value="用户名",name="username",example="cyjuser",required=true)
    private String username;
    
    /**
     * 密码
     * */
    @ApiModelProperty(value="密码",name="password",example="123456",required=true)
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     * */
    @ApiModelProperty(hidden=true)
    private String faceImage;

    /**
     * 昵称
     * */
    @ApiModelProperty(hidden=true)
    private String nickname;

    /**
     * 我的粉丝数量
     * */
    @ApiModelProperty(hidden=true)
    private Integer fansCount;

    /**
     * 我关注的总人数
     * */
    @ApiModelProperty(hidden=true)
    private Integer followCounts;

    /**
     * 我收到的赞美/收藏的数量
     * */
    @ApiModelProperty(hidden=true)
    private Integer receiveLikeCounts;

    
    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }



    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return face_image
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * @param faceImage
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return fans_count
     */
    public Integer getFansCount() {
        return fansCount;
    }

    /**
     * @param fansCount
     */
    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    /**
     * @return follow_counts
     */
    public Integer getFollowCounts() {
        return followCounts;
    }

    /**
     * @param followCounts
     */
    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    /**
     * @return receive_like_counts
     */
    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    /**
     * @param receiveLikeCounts
     */
    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
    
    

}
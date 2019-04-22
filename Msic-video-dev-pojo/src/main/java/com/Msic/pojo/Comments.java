package com.Msic.pojo;

import java.util.Date;
import javax.persistence.*;

public class Comments {
    @Id
    private String id;
    
	@Column(name = "father_comment_id")
    private String fatherCommentId;

    @Column(name = "to_user_id")
    private String toUserId;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "from_user_id")
    private String fromUserId;

    private String comment;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return video_id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return from_user_id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getFatherCommentId() {
		return fatherCommentId;
	}

	public void setFatherCommentId(String fatherCommentId) {
		this.fatherCommentId = fatherCommentId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
}
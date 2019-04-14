package com.Msic.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "users_report")
public class UsersReport {
    @Id
    private String id;

    /**
     * 被举报者id
     */
    @Column(name = "deal_user_id")
    private String dealUserId;

    /**
     * 被举报视频id
     */
    @Column(name = "deal_video_id")
    private String dealVideoId;

    private String title;

    private String content;

    /**
     * 举报者id
     */
    private String userid;

    @Column(name = "create_date")
    private Date createDate;

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
     * 获取被举报者id
     *
     * @return deal_user_id - 被举报者id
     */
    public String getDealUserId() {
        return dealUserId;
    }

    /**
     * 设置被举报者id
     *
     * @param dealUserId 被举报者id
     */
    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    /**
     * 获取被举报视频id
     *
     * @return deal_video_id - 被举报视频id
     */
    public String getDealVideoId() {
        return dealVideoId;
    }

    /**
     * 设置被举报视频id
     *
     * @param dealVideoId 被举报视频id
     */
    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取举报者id
     *
     * @return userid - 举报者id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置举报者id
     *
     * @param userid 举报者id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
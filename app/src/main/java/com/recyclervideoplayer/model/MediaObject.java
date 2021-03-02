package com.recyclervideoplayer.model;

public class MediaObject {
    private int uId;
    private String title;
    private String mediaUrl;
    private String mediaCoverImgUrl;
    private String userHandle;
    private int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MediaObject(String title, String mediaUrl, String mediaCoverImgUrl, String userHandle) {
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.mediaCoverImgUrl = mediaCoverImgUrl;
        this.userHandle = userHandle;
    }

    public MediaObject(String title, String mediaUrl, String mediaCoverImgUrl, String userHandle, int type) {
        this.title = title;
        this.mediaUrl = mediaUrl;
        this.mediaCoverImgUrl = mediaCoverImgUrl;
        this.userHandle = userHandle;
        this.type = type;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String mUserHandle) {
        this.userHandle = mUserHandle;
    }

    public int getId() {
        return uId;
    }

    public void setId(int uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getUrl() {
        return mediaUrl;
    }

    public void setUrl(String mUrl) {
        this.mediaUrl = mUrl;
    }

    public String getCoverUrl() {
        return mediaCoverImgUrl;
    }

    public void setCoverUrl(String mCoverUrl) {
        this.mediaCoverImgUrl = mCoverUrl;
    }
}

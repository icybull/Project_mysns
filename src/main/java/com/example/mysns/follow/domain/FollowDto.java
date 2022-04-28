package com.example.mysns.follow.domain;

public class FollowDto {

    private int follow_No;
    private int following;
    private int follower;

    public int getFollow_No() {
        return follow_No;
    }

    public void setFollow_No(int follow_No) {
        this.follow_No = follow_No;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}

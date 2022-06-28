package com.example.mysns.follow.service;

import com.example.mysns.dao.DataDao;
import com.example.mysns.follow.domain.FollowDto;
import com.example.mysns.member.service.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final String packageName = "com.example.mysns.follow.domain.";
    private final Logger logger = LogManager.getLogger(FollowService.class);
    private DataDao dataDao;


    @Autowired
    public FollowService(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    public int chkFollow(int myId, int follower){
        FollowDto followDto = new FollowDto();
        followDto.setFollowing(myId);
        followDto.setFollower(follower);
        return dataDao.selectOne(packageName+"chkFollow", followDto);
    }
    public int insFollow(int myId, int follower){
        FollowDto followDto = new FollowDto();
        followDto.setFollower(follower);
        followDto.setFollowing(myId);
        return dataDao.insert(packageName+"insFollow", followDto);
    }

    public int delFollow(int myId, int follower){
        FollowDto followDto = new FollowDto();
        followDto.setFollower(follower);
        followDto.setFollowing(myId);
        return dataDao.delete(packageName+"delFollow", followDto);
    }

    public int getFollowingCnt(int myId){
        FollowDto followDto = new FollowDto();
        followDto.setFollowing(myId);
        return dataDao.selectOne(packageName+"getFollowingCnt", followDto);
    }

    public int getFollowerCnt(int myId){
        FollowDto followDto = new FollowDto();
        followDto.setFollower(myId);
        return dataDao.selectOne(packageName+"getFollowerCnt", followDto);
    }

}

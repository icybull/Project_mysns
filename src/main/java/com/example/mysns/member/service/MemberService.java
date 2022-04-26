package com.example.mysns.member.service;

import com.example.mysns.dao.DataDao;
import com.example.mysns.member.domain.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


@Transactional
@Service
public class MemberService {

    private final String packageName = "com.example.mysns.member.domain.";
    private final Logger logger = LogManager.getLogger(MemberService.class);

    private DataDao dataDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    // 회원가입 정보 체크 후 저장
    public String chkMemberJoin(Member member){
        if( dataDao.selectList(packageName+"findByEmail", member).size() > 0){
            return "isEmail";
        } else if(dataDao.selectList(packageName+"findByNick", member).size() > 0){
            return "isNick";
        } else {
            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(member.getPwd());
            member.setPwd(encodedPassword);
            dataDao.insert(packageName+"insMember", member);
            return "ok";
        }
    }

    // 이메일, 비밀번호 일치 체크 후 로그인
    public String chkMemberLogin(HttpServletRequest request, Member member){
        HttpSession session = request.getSession();
        logger.debug("findByEmail > ::::::::::::::::::::::::");

        List<Member> list = dataDao.selectList(packageName+"findByEmail", member);
        if(list.size() == 0){
            return "notEmail";
        }
        System.out.println(list.size()+" //// "+ list.get(0));
        if(!passwordEncoder.matches(member.getPwd(), list.get(0).getPwd())){
            return "notPwd";
        }
        session.setAttribute("id",list.get(0).getId());
        session.setAttribute("name", list.get(0).getName());
        session.setAttribute("nick", list.get(0).getNick());
        return "ok";
    }




}

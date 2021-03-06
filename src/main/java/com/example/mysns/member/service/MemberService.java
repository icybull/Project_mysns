package com.example.mysns.member.service;

import com.example.mysns.dao.DataDao;
import com.example.mysns.member.domain.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


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
        logger.info("findByEmail > ::::::::::::::::::::::::");

        List<Member> list = dataDao.selectList(packageName+"findByEmail", member);
        if(list.size() == 0){
            return "notEmail";
        }
        if(!passwordEncoder.matches(member.getPwd(), list.get(0).getPwd())){
            return "notPwd";
        }
        session.setAttribute("profileImg", list.get(0).getProfileImg());
        session.setAttribute("id",list.get(0).getId());
        session.setAttribute("name", list.get(0).getName());
        session.setAttribute("nick", list.get(0).getNick());
        return "ok";
    }

    public Member getMyPageMember(int myId){
        Member member = new Member();
        member.setId(myId);
        return dataDao.selectOne(packageName+"getMyPageMember", member);
    }

    public List<Member> selectAllMember(){
        return dataDao.selectList(packageName+"selAllMember");
    }

    public Member getMemberById(int myId){
        Member member = new Member();
        member.setId(myId);
        return dataDao.selectOne(packageName+"findById", member);
    }
    public List<Member> findByEmail(Member member){
        return dataDao.selectList(packageName+"findByEmail", member);
    }
    public List<Member> findByNick(Member member){
        return dataDao.selectList(packageName+"findByNick", member);
    }

    public String updProfileImg(Member member, Part file, String path){
        String fileName = "";
        try{
            fileName = file.getSubmittedFileName();
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "_" + fileName;
            member.setProfileImg(fileName);
            fileUpload(file,fileName,path);
            dataDao.update(packageName+"updProfileImg", member);
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            return fileName;
        }

    }
    public int updBasicImg(Member member){
        return dataDao.update(packageName+"updBasicImg", member);
    }
    public int updEmail(Member member){
        return dataDao.update(packageName+"updEmail",member);
    }
    public int updNick(Member member){
        return dataDao.update(packageName+"updNick",member);
    }
    public int updPwd(Member member){
        String encodedPassword = passwordEncoder.encode(member.getOldObj());
        member.setPwd(encodedPassword);
        return dataDao.update(packageName+"updPwd",member);
    }
    private void fileUpload(Part file,String fileName, String path) throws IOException {
        InputStream inputStream = file.getInputStream();
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + fileName);
        System.out.println(path + File.separator + fileName);
        int size = 0;
        byte[] buffer = new byte[1024];
        while ((size = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, size);
        }
        if (inputStream != null){
            inputStream.close();
        }
        if(fileOutputStream != null){
            fileOutputStream.close();
        }
    }
}

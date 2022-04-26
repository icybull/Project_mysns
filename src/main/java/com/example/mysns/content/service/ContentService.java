package com.example.mysns.content.service;

import com.example.mysns.content.domain.Content;
import com.example.mysns.dao.DataDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class ContentService {

    private final String packageName = "com.example.mysns.content.domain.";
    private final Logger logger = LogManager.getLogger(ContentService.class);
    private DataDao dataDao;

    @Autowired
    public ContentService(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    //게시판 컨텐츠 데이터들 리턴
    public List<Content> showContents( int myId, int page){
        System.out.println(page);
        Content content = new Content();
        content.setPage(page);
        List<Content> list = dataDao.selectList(packageName+"selContents",content);
        for(Content c : list){
            if(c.getM_id() == myId){
                c.setMine(true);
            } else {
                c.setMine(false);
            }
        }
        return list;
    }
    public List<Content> searchContents(int myId, int page, Content content){
        content.setPage(page);
        List<Content> list = dataDao.selectList(packageName+"searchContents",content);
        for(Content c : list){
            if(c.getM_id() == myId){
                c.setMine(true);
            } else {
                c.setMine(false);
            }
        }
        return list;
    }

    // 총 페이지 숫자 리턴
    public int selPageNum(){
        int preCount = dataDao.selectOne(packageName+"contentCnt");
        int pageCnt = preCount % 8 == 0 ? preCount / 8 - 1 : preCount / 8;
        return pageCnt;
    }
    public int searchPageNum(Content content){
        int preCount = dataDao.selectOne(packageName+"searchCnt", content);
        int pageCnt = preCount % 8 == 0 ? preCount / 8 - 1 : preCount / 8;
        return pageCnt;
    }

    // 컨텐츠 추가
    public ResponseEntity<?> insContent(Content content, Part file, String path) {
        try {
            String fileName = file.getSubmittedFileName();
            UUID uuid = UUID.randomUUID();
            fileName = uuid.toString() + "_" + fileName;
            content.setImg(fileName);
            dataDao.insert(packageName + "insContent", content);
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

    // 바꿀 컨텐츠
    public Content preContent(Content content){
        return dataDao.selectOne(packageName+"modifyContent",content);
    }
    // 바뀌는 컨텐츠
    public ResponseEntity<?> updContent(Content content, Part file, String path){
        return new ResponseEntity("", HttpStatus.OK);
    }
    // 컨텐츠 삭제
    public int deleteContent(Content content){
        return dataDao.delete(packageName+"deleteContent",content);
    }

    //마이페이지
    public List<Content> getMyPageContents(int myId){
        Content content = new Content();
        content.setM_id(myId);
        return dataDao.selectList(packageName+"getMyPageContents", content);
    }
}

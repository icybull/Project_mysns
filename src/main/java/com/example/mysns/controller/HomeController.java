package com.example.mysns.controller;

import com.example.mysns.content.domain.Content;
import com.example.mysns.content.service.ContentService;
import com.example.mysns.member.domain.Member;
import com.example.mysns.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.List;

@Controller
@RequestMapping("/home")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10 * 5)
public class HomeController {

    @Autowired
    private MemberService memberService;
    private ContentService contentService;

    @Autowired
    public HomeController(ContentService contentService) {
        this.contentService = contentService;
    }




    //검색

    // new 화면
    @GetMapping("/new") // 세션은 한번 getSession() 하면 parameter로 받으면 된다.
    public String newContent(HttpSession session, Model model){
        String nick = String.valueOf(session.getAttribute("nick"));
        String name = String.valueOf(session.getAttribute("name"));
        if(nick.equals("") || nick == null || name.equals("") || name == null || session.getAttribute("id") == null){
            return "redirect:/";
        }
        model.addAttribute("nick",nick);
        model.addAttribute("name",session.getAttribute("name"));
        return "new";
    }

    // 새로운 content 추가
    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<?> insContent(HttpServletRequest request, Content content, @RequestParam("file") Part file ) {
        if(file.getSize() == 0){
            return new ResponseEntity("notFile", HttpStatus.OK);
        } else {
            HttpSession session = request.getSession();
            content.setM_id(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
            String path = request.getServletContext().getRealPath("/upload");
            System.out.println(path);
            ResponseEntity result = contentService.insContent(content,file,path);
            return result;
        }
    }



    // 홈 화면 컨텐츠 보여주기 + 검색 컨텐츠 보여주기
    @GetMapping("/show")
    public String showContents(HttpServletRequest request, @RequestParam("page") int page, @RequestParam(value = "searchWord",required = false) String searchWord , Model model){
        HttpSession session = request.getSession();
        String nick = String.valueOf(session.getAttribute("nick"));
        String name = String.valueOf(session.getAttribute("name"));
        if(nick.equals("") || nick == null || name.equals("") || name == null || session.getAttribute("id") == null){
            return "redirect:/";
        }
        model.addAttribute("nick", nick);
        model.addAttribute("name", name);
        int myId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        model.addAttribute("page",page);

        List<Content> cdArr = null;
        int pageCnt = 0;
        if(searchWord == null || searchWord.equals("")){
            cdArr = contentService.showContents(myId, page);
            pageCnt = contentService.selPageNum();
        } else {
            Content content = new Content();
            content.setSearchWord(searchWord);
            cdArr = contentService.searchContents(myId,page, content);
            page = contentService.searchPageNum(content);
            model.addAttribute(("searchWord"),searchWord);
        }

        model.addAttribute("num", page);
        model.addAttribute("cdArr", cdArr);
        model.addAttribute("pageCnt", pageCnt);
        return "list";
    }

    //삭제버튼
    @GetMapping("/delete")
    public String deleteContent(Model model, Content content){
        int result = contentService.deleteContent(content);
        if(result > -1){
            model.addAttribute("msg","삭제 완료하였습니다.");
            model.addAttribute("url","/home/show?page=0");
        } else {
            model.addAttribute("msg","삭제 데이터가 존재 하지 않습니다.");
            model.addAttribute("url","/home/show?page=0");
        }
        return "alert";
    }

    // 수정버튼
    @GetMapping("/modify")
    public String modifyContent(Model model, HttpSession session, Content content){
        String nick = String.valueOf(session.getAttribute("nick"));
        String name = String.valueOf(session.getAttribute("name"));
        if(nick.equals("") || nick == null || name.equals("") || name == null || session.getAttribute("id") == null){
            return "redirect:/";
        }
        model.addAttribute("nick",nick);
        model.addAttribute("name",session.getAttribute("name"));
        Content resultContent = contentService.preContent(content);
        model.addAttribute("content",resultContent);
        return "new";
    }
    @ResponseBody
    @PostMapping("/modify")
    public ResponseEntity<?> updContent(HttpServletRequest request, Content content, @RequestParam("file") Part file){
        if(file.getSize() == 0){
            return new ResponseEntity("notFile", HttpStatus.OK);
        } else {
            HttpSession session = request.getSession();
            content.setM_id(Integer.parseInt(String.valueOf(session.getAttribute("id"))));
            String path = request.getServletContext().getRealPath("/upload");
            System.out.println(path);
            ResponseEntity result = contentService.updContent(content,file,path);
            return result;
        }
    }

    @GetMapping("/mypage")
    public String myPage( HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String nick = String.valueOf(session.getAttribute("nick"));
        String name = String.valueOf(session.getAttribute("name"));
        if(nick.equals("") || nick == null || name.equals("") || name == null || session.getAttribute("id") == null){
            return "redirect:/";
        }
        model.addAttribute("nick", nick);
        model.addAttribute("name", name);
        int myId = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        Member member = memberService.getMyPageMember(myId);
        List<Content> contentList = contentService.getMyPageContents(myId);

        return "mypage";
    }
}

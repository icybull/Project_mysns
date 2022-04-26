package com.example.mysns.login.controller;

import com.example.mysns.member.domain.Member;
import com.example.mysns.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/login/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login/success")
    public String loginSuccess(){
//        HttpSession session = request.getSession();
//        model.addAttribute("name", session.getAttribute("name"));
//        model.addAttribute("nick", session.getAttribute("nick"));
        return "redirect:/home/show?page=0";
    }
    //로그인
    @ResponseBody
    @PostMapping("/login")
    public String login(HttpServletRequest request, Member member, Model model){
        String result = memberService.chkMemberLogin(request, member);
        return result;
    }

    //회원가입 페이지로 이동
    @GetMapping("/login/join")
    public String join(){
        return "join";
    }

    //회원가입 중복체크
    @ResponseBody
    @PostMapping("/login/join")
    public String regMember(Member member, Model model){
        String result = memberService.chkMemberJoin(member);
        return result;
    }


}

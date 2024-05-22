package org.zerock.springboot_test_240521.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springboot_test_240521.dto.MemberJoinDTO;
import org.zerock.springboot_test_240521.dto.PageRequestDTO;
import org.zerock.springboot_test_240521.service.MemberService;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("--------------- login GET ---------------");
        log.info("--------------- error : " + error + "---------------");
        if (logout != null){
            log.info("user logout --------------");
        }
    }



    @GetMapping("/join")
    public void joinGET(){
        log.info("join get ---------------------");
    }

    @PostMapping("/join")
    public String joinPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("join post --------------------");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);
        } catch (MemberService.MIdExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }



    @PostMapping("/confirm")
    public String confirmPOST(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes) {
        log.info("confirm post -------------------");
        try {
            memberService.confirmMid(memberJoinDTO);
        } catch (MemberService.MIdExistException e) {
            redirectAttributes.addFlashAttribute("error", "mid");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");
//        redirectAttributes.addFlashAttribute("mid", memberJoinDTO.getMid());
        return "redirect:/member/join";
    }



    @GetMapping( {"modify", "myPage"})
    public void modify(Principal principal, PageRequestDTO pageRequestDTO, Model model) {
        MemberJoinDTO memberJoinDTO = memberService.myPage(principal.getName());
        model.addAttribute("memberDTO", memberJoinDTO);
    }

    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO, MemberJoinDTO memberJoinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            log.info("has error----------------");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("mid", memberJoinDTO.getMid());
            return "redirect:/member/modify?" + link;
        }

        memberService.modify(memberJoinDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("mid", memberJoinDTO.getMid());
        return "redirect:/member/myPage";
    }



//    @PreAuthorize("principal.username == #memberJoinDTO.mid")
    @PostMapping("/remove")
    public String remove(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        String mid = memberJoinDTO.getMid();
        HttpSession session = req.getSession();

        log.info("remove post ----- " + mid);

        memberService.remove(mid);
        session.invalidate();
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }

}


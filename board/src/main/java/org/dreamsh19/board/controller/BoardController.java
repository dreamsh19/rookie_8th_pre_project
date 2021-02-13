package org.dreamsh19.board.controller;

import lombok.RequiredArgsConstructor;
import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.dto.PageResultDTO;
import org.dreamsh19.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")

@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("pageResult", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }
}

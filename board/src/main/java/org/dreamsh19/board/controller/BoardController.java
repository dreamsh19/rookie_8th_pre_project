package org.dreamsh19.board.controller;

import lombok.RequiredArgsConstructor;
import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")

@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        model.addAttribute("pageResult", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {
        BoardDTO boardDTO = boardService.getBoardDTO(bno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {

        boardService.deleteByBnoWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        boardService.update(dto);

        redirectAttributes.addAttribute("bno", dto.getBno());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("page", requestDTO.getPage());

        return "redirect:/board/read";
    }
}

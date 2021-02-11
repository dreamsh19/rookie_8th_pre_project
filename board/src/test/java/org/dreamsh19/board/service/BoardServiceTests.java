package org.dreamsh19.board.service;

import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest() {
        BoardDTO dto = BoardDTO.builder()
                .title("test title")
                .content("test content")
                .writerEmail("user1@test.io")
                .build();
        Long bno = boardService.register(dto);
    }

    @Test
    public void listTest() {
        PageResultDTO<Object[], BoardDTO> pageResultDTO = boardService.getList(new PageRequestDTO());
        for (BoardDTO boardDTO : pageResultDTO.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void getTest() {
        BoardDTO result = boardService.getBoardDTO(100L);
        System.out.println(result);
    }

    @Test
    public void deleteTest(){
        boardService.deleteByBnoWithReplies(1L);
    }

    @Test
    public void updateTest(){
        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("Updated Title")
                .content("Updated Content")
                .build();
        boardService.update(dto);
    }
}

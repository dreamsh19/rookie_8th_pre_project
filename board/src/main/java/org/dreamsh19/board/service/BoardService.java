package org.dreamsh19.board.service;

import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.dto.PageRequestDTO;
import org.dreamsh19.board.dto.PageResultDTO;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto) {
        Member writer = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(writer)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long count){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .replyCount(count.intValue())
                .build();
        return boardDTO;
    }
    PageResultDTO<Object[], BoardDTO> getList(PageRequestDTO pageRequestDTO);
}

package org.dreamsh19.board.service;

import lombok.RequiredArgsConstructor;
import org.dreamsh19.board.dto.BoardDTO;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto); // 이때 board.bno == null
        boardRepository.save(board);
        // 이때 board.bno != null
        return board.getBno();
    }
}

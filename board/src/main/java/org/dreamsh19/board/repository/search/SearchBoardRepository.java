package org.dreamsh19.board.repository.search;

import org.dreamsh19.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}

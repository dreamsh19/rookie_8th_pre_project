package org.dreamsh19.board.repository;

import org.dreamsh19.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // JPQL
    // 연관관계 있음(fk) join 뒤에 on이 없지만 알아서 가져와줌.
    @Query("select b,w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno); // @Param으로 지정한 걸 Query에 넣는다


    // FK 없음. on 필요
    @Query("select b,r from Board b left join Reply r on r.board = b where b.bno=:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
}

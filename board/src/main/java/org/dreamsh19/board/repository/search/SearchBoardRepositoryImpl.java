package org.dreamsh19.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.QBoard;
import org.dreamsh19.board.entity.QMember;
import org.dreamsh19.board.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }



    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) conditionBuilder.or(board.title.contains(keyword));
        if (type.contains("c")) conditionBuilder.or(board.content.contains(keyword));
        if (type.contains("w")) conditionBuilder.or(member.email.contains(keyword));

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
        tuple.where(conditionBuilder);
        tuple.groupBy(board);
        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }


}

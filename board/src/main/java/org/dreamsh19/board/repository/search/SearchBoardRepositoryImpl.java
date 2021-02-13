package org.dreamsh19.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.dreamsh19.board.entity.Board;
import org.dreamsh19.board.entity.QBoard;
import org.dreamsh19.board.entity.QMember;
import org.dreamsh19.board.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

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

        if (type != null) {
            if (type.contains("t")) conditionBuilder.or(board.title.contains(keyword));
            if (type.contains("c")) conditionBuilder.or(board.content.contains(keyword));
            if (type.contains("w")) conditionBuilder.or(member.email.contains(keyword));
        }
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
        tuple.where(conditionBuilder);
        tuple.groupBy(board);

        pageable.getSort().stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });


        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        log.info(result);
        Long count = tuple.fetchCount();
        log.info("COUNT: " + count);

        return new PageImpl<Object[]>(result.stream().map(tupl -> tupl.toArray()).collect(Collectors.toList()),
                pageable, count);
    }


}

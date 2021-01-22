package org.dreamsh19.mreview.repository;

import org.dreamsh19.mreview.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

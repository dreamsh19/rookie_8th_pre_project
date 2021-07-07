package org.dreamsh19.mreview.repository;

import org.dreamsh19.mreview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

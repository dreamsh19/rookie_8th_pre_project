package org.dreamsh19.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String uuid;

    private String imageName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}

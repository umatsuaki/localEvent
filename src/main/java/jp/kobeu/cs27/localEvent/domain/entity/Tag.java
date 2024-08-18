package jp.kobeu.cs27.localEvent.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * タグを表すエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    // タグID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tid;

    // タグ名
    private String name;

    // タグの説明
    private String description;

}

package jp.kobeu.cs27.localEvent.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * エリアを表すエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Area {

        // エリアID
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int aid;

        // エリア名
        private String name;

        // エリアの説明
        private String description;

}
package jp.kobeu.cs27.localEvent.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ユーザとタグの組み合わせを表すエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTag {

    // ユーザータグID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int utid;

    // ユーザーID
    private int uid;

    // タグID
    private int tid;



}

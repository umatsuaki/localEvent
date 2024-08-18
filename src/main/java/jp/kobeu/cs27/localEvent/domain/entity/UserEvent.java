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
 * ユーザとイベントの組み合わせを表すエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {

    // ユーザーイベントID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ueid;

    // ユーザーID
    private int uid;

    // イベントID
    private int eid;
}

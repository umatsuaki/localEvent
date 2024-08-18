package jp.kobeu.cs27.localEvent.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * イベントタグ登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventTagForm {

    // イベントタグID
    private int etid;

    // イベントID
    private int eid;

    // タグID
    private int tid;

}

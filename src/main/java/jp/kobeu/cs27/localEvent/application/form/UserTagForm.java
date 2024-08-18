package jp.kobeu.cs27.localEvent.application.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ユーザタグ登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTagForm {

    // ユーザタグID
    private int utid;

    // ユーザID
    private int uid;

    // タグID
    private int tid;
}

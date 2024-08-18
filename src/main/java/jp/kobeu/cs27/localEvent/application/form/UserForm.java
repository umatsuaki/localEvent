package jp.kobeu.cs27.localEvent.application.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ユーザー登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    
    //ユーザーID
    private int uid;

    //ユーザー名
    @NotBlank
    private String name;

    //メールアドレス
    private String email;

    //パスワード
    private String password;

    //ユーザーの住んでいる地域ID
    private int aid;

}

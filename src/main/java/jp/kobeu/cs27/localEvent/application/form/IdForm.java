package jp.kobeu.cs27.localEvent.application.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 削除用IDフォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdForm {
    
    //ユーザID
    @NotNull
    private int id;
}

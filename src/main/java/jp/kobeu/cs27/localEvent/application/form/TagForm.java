package jp.kobeu.cs27.localEvent.application.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * タグ登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagForm {
    
    //タグID
    private int tid;

    //タグ名
    @NotBlank
    private String name;

    //タグの説明
    private String description;
}

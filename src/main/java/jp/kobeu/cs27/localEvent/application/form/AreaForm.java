package jp.kobeu.cs27.localEvent.application.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * エリア登録・更新フォーム
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AreaForm {

    // エリアID
    private int aid;

    // エリア名
    @NotBlank
    private String name;

    // エリアの説明
    private String description;
}

package jp.kobeu.cs27.localEvent.application.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.kobeu.cs27.localEvent.application.form.AreaForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.service.AreaService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AreaController {

    private final AreaService areaService;

    /**
     * 地域登録が可能か確認する
     * 地域が登録済みであった場合、タグ登録ページに戻る
     * 
     * 
     */
    @GetMapping("/area/confirm")
    public String confirmAreaResistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated AreaForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isAreaFormError", true);

            return "redirect:/area";
        }

        // 地域IDを変数に格納する
        final int aid = form.getAid();

        // 地域が既に存在するか確認する
        if (areaService.existsArea(aid)) {
            attributes.addFlashAttribute("isAreaAlreadyExistsError", true);
            return "redirect:/area";
        }

        model.addAttribute("aid", aid);
        model.addAttribute("name", form.getName());
        model.addAttribute("description", form.getDescription());

        return "areainputconfirm";

    }

    /**
     * 地域を登録する
     */
    @PostMapping("/area/register")
    public String registerArea(Model model, RedirectAttributes attributes, @ModelAttribute @Validated AreaForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、地域登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isAreaFormError", true);

            return "redirect:/area";
        }

        // 地域を登録する
        try {
            areaService.createArea(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isAreaAlreadyExistsError", true);
            return "redirect:/area";
        }

        return "redirect:/";

    }

}

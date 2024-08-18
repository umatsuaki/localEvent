package jp.kobeu.cs27.localEvent.application.controller.view;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import jp.kobeu.cs27.localEvent.application.form.TagForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.service.TagService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * タグ登録が可能か確認する
     * タグが登録済みであった場合、タグ登録ページに戻る
     * 
     * 
     */
    @GetMapping("/tag/confirm")
    public String confirmTagResistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated TagForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isTagFormError", true);

            return "redirect:/tag";
        }

        // タグIDを変数に格納する
        final int tid = form.getTid();

        // タグが既に存在するか確認する
        if (tagService.existsTag(tid)) {
            attributes.addFlashAttribute("isTagAlreadyExistsError", true);

            return "redirect:/tag";
        }

        model.addAttribute("tid", tid);
        model.addAttribute("name", form.getName());
        model.addAttribute("description", form.getDescription());

        return "taginputconfirm";

    }

    /**
     * タグを登録する
     */
    @PostMapping("/tag/register")
    public String registerTag(Model model, RedirectAttributes attributes, @ModelAttribute @Validated TagForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isTagFormError", true);

            return "redirect:/tag";
        }

        // タグを登録する
        try {
            tagService.addTag(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isTagAlreadyExistsError", true);
            return "redirect:/tag";
        }

        return "redirect:/";

    }

     

}

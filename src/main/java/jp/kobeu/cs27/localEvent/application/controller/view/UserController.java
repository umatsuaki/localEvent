package jp.kobeu.cs27.localEvent.application.controller.view;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.kobeu.cs27.localEvent.application.form.UserForm;
import jp.kobeu.cs27.localEvent.application.form.UserTagForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.entity.Area;
import jp.kobeu.cs27.localEvent.domain.entity.User;
import jp.kobeu.cs27.localEvent.domain.service.AreaService;
import jp.kobeu.cs27.localEvent.domain.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AreaService areaService;

    /**
     * ユーザ登録が可能か確認する
     * ユーザが登録済みであった場合、ユーザ登録ページに戻る
     * 
     * 
     */
    @GetMapping("/user/confirm")
    public String confirmAreaResistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UserForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isUserFormError", true);

            return "redirect:/user";
        }

        // タグIDを変数に格納する
        final int uid = form.getUid();

        // タグが既に存在するか確認する
        if (userService.existsUser(uid)) {
            attributes.addFlashAttribute("isUserAlreadyExistsError", true);

            return "redirect:/user";
        }

        model.addAttribute("uid", uid);
        model.addAttribute("name", form.getName());
        model.addAttribute("email", form.getEmail());
        model.addAttribute("password", form.getPassword());
        model.addAttribute("aid", form.getAid());

        int aid = form.getAid();

        Area area;
        try {
            area = areaService.getArea(aid);
            model.addAttribute("areaName", area.getName());
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isAreaNotFoundError", true);
            return "redirect:/user";
        }

        return "userinputconfirm";

    }

    /**
     * ユーザIDを受け取りユーザ詳細画面を表示する
     * 
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return
     */
    @GetMapping("/user/detail")
    public String showUserDetail(Model model, RedirectAttributes attributes,
            @RequestParam("uid") int uid) {

        // ユーザが存在するか確認する
        if (!userService.existsUser(uid)) {
            attributes.addFlashAttribute("isUserNotFoundError", true);
            return "redirect:/userlist";
        }

        // ユーザを取得する
        User user = userService.getUser(uid);

        model.addAttribute("uid", uid);
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("aid", user.getAid());

        int aid = user.getAid();

        Area area;
        try {
            area = areaService.getArea(aid);
            model.addAttribute("areaName", area.getName());
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isAreaNotFoundError", true);
            return "redirect:/userlist";
        }

        return "userdetail";

    }

    /**
     * ユーザを登録する
     */
    @PostMapping("/user/register")
    public String registerArea(Model model, RedirectAttributes attributes, @ModelAttribute @Validated UserForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isUserFormError", true);

            return "redirect:/user";
        }

        // タグを登録する
        try {
            userService.addUser(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isUserAlreadyExistsError", true);
            return "redirect:/user";
        }

        return "redirect:/";

    }

    /**
     * ユーザとタグを紐付ける
     */
    @PostMapping("/user/tag")
    public String connectUserTag(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UserTagForm form,
            BindingResult bindingResult) {

        // ユーザIDとタグIDを変数に格納する
        final int uid = form.getUid();
        final int tid = form.getTid();

        // タグが既に存在するか確認する
        if (userService.existsUserTag(uid, tid)) {
            attributes.addFlashAttribute("isUserTagAlreadyExistsError", true);
            return "redirect:/userlist";
        }

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isUserFormError", true);

            return "redirect:/userlist";
        }

        // タグを登録する
        try {
            userService.addTagToUser(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isUserAlreadyExistsError", true);
            return "redirect:/userlist";
        }

        return "redirect:/userlist";

    }

    /**
     * ユーザとタグの紐付けを解除する
     *
     * 
     */
    @PostMapping("/user/tag/delete")
    public String deleteUserTag(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated UserTagForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isUserFormError", true);

            return "redirect:/userlist";
        }

        // タグとユーザの紐付けを解除する
        try {
            userService.deleteTagFromUser(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isUserAlreadyExistsError", true);
            return "redirect:/userlist";
        }

        return "redirect:/userlist";

    }

    /**
     * ユーザ削除画面を表示する
     */
    @GetMapping("/user/delete/{uid}")
    public String deleteEvent(Model model, RedirectAttributes attributes, @PathVariable("uid") int uid) {

        // イベントが存在しない場合は例外を投げる
        if (!userService.existsUser(uid)) {
            attributes.addFlashAttribute("isUserNotFoundError", true);
            return "redirect:/userlist";
        }

        // ユーザを取得する
        User user = userService.getUser(uid);
        model.addAttribute("uid", user.getUid());
        model.addAttribute("name", user.getName());
        return "userdeleteconfirm";

    }

    /**
     * ユーザを削除する
     */
    @GetMapping("/user/delete/confirm/{uid}")
    public String showDeleteEventConfirmPage(Model model, RedirectAttributes attributes,
            @PathVariable("uid") int uid) {

        // イベントが存在しない場合は例外を投げる
        if (!userService.existsUser(uid)) {
            attributes.addFlashAttribute("isUserNotFoundError", true);
            return "redirect:/userlist";
        }
        // イベントを削除する
        userService.deleteUser(uid);
        return "redirect:/userlist";
    }

    /**
     * ユーザを更新する
     */
    @GetMapping("/user/update/confirm/{uid}")
    public String updateUser(Model model, RedirectAttributes attributes, @PathVariable("uid") int uid,
            @ModelAttribute @Validated UserForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isUserFormError", true);

            return "redirect:/userlist";
        }

        form.setUid(uid);
        // ユーザを更新する
        try {
            userService.updateUser(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isUserAlreadyExistsError", true);
            return "redirect:/userlist";
        }
        attributes.addFlashAttribute("isUserUpdateSuccess", true);
        return "redirect:/user/update/" + uid;

    }

    /**
     * ユーザの更新のページを表示する
     */
    @GetMapping("/user/update/{uid}")
    public String confirmUserUpdate(
            Model model,
            RedirectAttributes attributes,
            @PathVariable("uid") int uid) {

        // ユーザが存在しない場合は例外を投げる
        if (!userService.existsUser(uid)) {
            attributes.addFlashAttribute("isUserNotFoundError", true);
            return "redirect:/userlist";
        }

        // ユーザを取得する
        User user = userService.getUser(uid);
        List<Area> areaList = areaService.getAllareas();

        // ユーザ情報をモデルに格納する
        model.addAttribute(new UserForm());
        model.addAttribute("uid", user.getUid());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("aid", user.getAid());
        model.addAttribute("areaList", areaList);

        // ユーザ情報更新ページ
        return "userupdate";
    }

}

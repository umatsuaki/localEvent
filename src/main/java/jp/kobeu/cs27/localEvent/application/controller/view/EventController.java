package jp.kobeu.cs27.localEvent.application.controller.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.kobeu.cs27.localEvent.application.form.EventForm;
import jp.kobeu.cs27.localEvent.application.form.EventTagForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.entity.Area;
import jp.kobeu.cs27.localEvent.domain.entity.Event;
import jp.kobeu.cs27.localEvent.domain.repository.EventRepository;
import jp.kobeu.cs27.localEvent.domain.service.AreaService;
import jp.kobeu.cs27.localEvent.domain.service.EventService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AreaService areaService;
    private final EventRepository events;

    /**
     * 地域登録が可能か確認する
     * 地域が登録済みであった場合、地域登録ページに戻る
     * 
     * 
     */
    @GetMapping("/event/confirm")
    public String confirmEventResistration(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated EventForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            // エラーログを出力してみる
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Field: " + error.getObjectName() + ", Error: " + error.getDefaultMessage());
            }
            System.out.println("EventForm content: " + form.toString());
            attributes.addFlashAttribute("isEventFormError", true);
            return "redirect:/event";
        }

        // 地域IDを変数に格納する
        final int eid = form.getEid();

        // 地域が既に存在するか確認する
        if (eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventAlreadyExistsError", true);

            return "redirect:/event";
        }

        eventService.setEventModel(form, model);

        if (form.getImage() != null) {
            try {
                MultipartFile image = form.getImage();
                InputStream inputStream = image.getInputStream();
                byte[] bytes = inputStream.readAllBytes();
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                String imageLink = "data:application/pdf;base64," + encodedString;
                model.addAttribute("imageLink", imageLink);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final int aid = form.getAid();
        try {
            Area area = areaService.getArea(aid);
            model.addAttribute("areaName", area.getName());
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isAreaNotFoundError", true);
            return "redirect:/event";
        }

        return "eventinputconfirm";

    }

    /**
     * イベントIDを受け取り、イベント詳細画面を表示する
     * 
     * @param model
     * @param attributes
     * @param form
     * @param bindingResult
     * @return
     * 
     */

    @GetMapping("/event/detail")
    public String showEventDetail(Model model, RedirectAttributes attributes, @RequestParam("eid") int eid,
            HttpServletRequest request) {

        // イベントが存在しない場合は例外を投げる
        if (!eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventNotFoundError", true);
            return "redirect:/eventlist";
        }

        // イベントを取得する
        Event event = eventService.getEvent(eid);
        eventService.setEventModel(event, model);

        final int aid = event.getAid();
        try {
            Area area = areaService.getArea(aid);
            model.addAttribute("areaName", area.getName());
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isAreaNotFoundError", true);
            return "redirect:/eventlist";
        }

        String referer = request.getHeader("referer");
        model.addAttribute("fromEventList", referer != null && referer.contains("/eventlist"));
        model.addAttribute("fromService", referer != null && referer.contains("/service"));

        return "eventdetail";

    }

    /**
     * イベントを登録する
     */
    @PostMapping("/event/register")
    public String registerTag(Model model, RedirectAttributes attributes, @ModelAttribute @Validated EventForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、イベント登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isEventFormError", true);
            System.out.println("バリデーションエラー");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println("Field: " + error.getObjectName() + ", Error: " + error.getDefaultMessage());
            }
            System.out.println("EventForm content: " + form.toString());
            return "redirect:/event";
        }

        System.out.println("バリデーションOK");

        // イベントを登録する
        try {
            eventService.addEvent(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isEventAlreadyExistsError", true);
            return "redirect:/event";
        }

        return "redirect:/";

    }

    /**
     * イベントとタグを紐付ける
     */
    @PostMapping("/event/tag")
    public String connectEventTag(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated EventTagForm form,
            BindingResult bindingResult) {

        // イベントIDとタグIDを変数に格納する
        final int eid = form.getEid();
        final int tid = form.getTid();

        // タグが既に存在するか確認する
        if (eventService.existsEventTag(eid, tid)) {
            attributes.addFlashAttribute("isEventTagAlreadyExistsError", true);
            return "redirect:/eventlist";
        }

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isTagFormError", true);
            return "redirect:/eventlist";
        }

        // タグとイベントを紐付ける
        try {
            eventService.addTagToEvent(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isTagAlreadyExistsError", true);
            return "redirect:/eventlist";
        }

        return "redirect:/eventlist";

    }

    /**
     * イベントとタグの紐付けを解除する
     */
    @PostMapping("/event/tag/delete")
    public String deleteEventTag(Model model, RedirectAttributes attributes,
            @ModelAttribute @Validated EventTagForm form,
            BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、タグ登録ページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isTagFormError", true);
            return "redirect:/eventlist";
        }

        // タグとイベントを紐付けを解除する
        try {
            eventService.deleteTagFromEvent(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isTagAlreadyExistsError", true);
            return "redirect:/eventlist";
        }

        return "redirect:/eventlist";

    }

    /**
     * イベントを削除する
     */
    @GetMapping("/event/delete/{eid}")
    public String deleteEvent(Model model, RedirectAttributes attributes, @PathVariable("eid") int eid) {

        // イベントが存在しない場合は例外を投げる
        if (!eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventNotFoundError", true);
            return "redirect:/eventlist";
        }

        // イベントを取得する
        Event event = eventService.getEvent(eid);
        model.addAttribute("eid", event.getEid());
        model.addAttribute("name", event.getName());
        return "eventdeleteconfirm";

    }

    /**
     * イベント削除確認画面を表示する
     */
    @GetMapping("/event/delete/confirm/{eid}")
    public String showDeleteEventConfirmPage(Model model, RedirectAttributes attributes,
            @PathVariable("eid") int eid) {

        // イベントが存在しない場合は例外を投げる
        if (!eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventNotFoundError", true);
            return "redirect:/eventlist";
        }
        // イベントを削除する
        eventService.deleteEvent(eid);
        return "redirect:/eventlist";
    }

    /**
     * イベントを更新する
     */
    @PostMapping("/event/update/confirm/{eid}")
    public String updateEvent(Model model, RedirectAttributes attributes, @PathVariable("eid") int eid,
            @ModelAttribute @Validated EventForm form, BindingResult bindingResult) {

        // フォームにバリデーション違反があった場合、イベントページに戻る
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("isEventFormError", true);

            return "redirect:/eventlist";
        }

        form.setEid(eid);
        // イベントを更新する
        try {
            eventService.updateEvent(form);
        } catch (ValidationException e) {
            attributes.addFlashAttribute("isEventAlreadyExistsError", true);
            return "redirect:/eventlist";
        }
        attributes.addFlashAttribute("isEventUpdateSuccess", true);
        return "redirect:/event/update/" + eid;

    }

    /**
     * イベントの更新のページを表示する
     */
    @GetMapping("/event/update/{eid}")
    public String confirmUserUpdate(
            Model model,
            RedirectAttributes attributes,
            @PathVariable("eid") int eid) {

        // イベントが存在しない場合は例外を投げる
        if (!eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventNotFoundError", true);
            return "redirect:/eventlist";
        }

        // イベントを取得する
        Event event = eventService.getEvent(eid);
        List<Area> areaList = areaService.getAllareas();

        // イベント情報をモデルに格納する
        model.addAttribute(new EventForm());
        eventService.setEventModel(event, model);
        model.addAttribute("areaList", areaList);
        model.addAttribute("event", event);

        // イベント情報更新ページ
        return "eventupdate";
    }

    /**
     * イベント詳細ページで画像のページを表示する
     */
    @RequestMapping("/event/detail/preview/{eid}")
    public void previewEventDetailImage(@PathVariable("eid") int eid, HttpServletResponse response) {
        // eidからイベントを取得する
        Optional<Event> event = events.findById(eid);
        if (event.isPresent()) {
            Event eventEntity = event.get();
            // イベントの画像を取得する
            Blob image = eventEntity.getImage();

            if (image != null) {
                try {
                    // ファイルダウンロードの設定を実施
                    // ファイルの種類は指定しない
                    response.setContentType("application/pdf");
                    response.setHeader("Cache-Control", "private");
                    response.setHeader("Pragma", "");
                    response.setHeader("Content-Disposition", "inline;");
                    // ダウンロードファイルへ出力
                    try (OutputStream out = response.getOutputStream(); InputStream in = image.getBinaryStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                        out.flush();
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    /**
     * イベント更新ページで画像を消去する
     */
    @RequestMapping("/event/update/deleteImage/{eid}")
    public String deleteEventImage(Model model, RedirectAttributes attributes, @PathVariable("eid") int eid) {
        // イベントが存在しない場合は例外を投げる
        if (!eventService.existsEvent(eid)) {
            attributes.addFlashAttribute("isEventNotFoundError", true);
            return "redirect:/event/update/" + eid;
        }
        // イベントの画像を消去する
        eventService.deleteEventImage(eid);
        return "redirect:/event/update/" + eid;
    }

}

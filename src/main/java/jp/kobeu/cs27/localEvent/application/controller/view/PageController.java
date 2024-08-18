package jp.kobeu.cs27.localEvent.application.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import jp.kobeu.cs27.localEvent.application.form.AreaForm;
import jp.kobeu.cs27.localEvent.application.form.EventForm;
import jp.kobeu.cs27.localEvent.application.form.EventTagForm;
import jp.kobeu.cs27.localEvent.application.form.IdForm;
import jp.kobeu.cs27.localEvent.application.form.TagForm;
import jp.kobeu.cs27.localEvent.application.form.UserForm;
import jp.kobeu.cs27.localEvent.application.form.UserTagForm;
import jp.kobeu.cs27.localEvent.domain.entity.Area;
import jp.kobeu.cs27.localEvent.domain.entity.Event;
import jp.kobeu.cs27.localEvent.domain.entity.Tag;
import jp.kobeu.cs27.localEvent.domain.entity.User;
import jp.kobeu.cs27.localEvent.domain.entity.UserTag;
import jp.kobeu.cs27.localEvent.domain.service.AreaService;
import jp.kobeu.cs27.localEvent.domain.service.EventService;
import jp.kobeu.cs27.localEvent.domain.service.TagService;
import jp.kobeu.cs27.localEvent.domain.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PageController {

   /**
    * ログイン画面を表示する
    */

   private final AreaService areaService;
   private final UserService userService;
   private final EventService eventService;
   private final TagService tagService;

   @GetMapping("/")
   public String loginPage() {

      return "index";
   }

   @GetMapping("/tag")
   public String showTagPage(Model model) {

      model.addAttribute(new TagForm());

      return "taginput";
   }

   @GetMapping("/area")
   public String showAreaPage(Model model) {

      model.addAttribute(new AreaForm());

      return "areainput";
   }

   @GetMapping("/event")
   public String showEventPage(Model model) {

      List<Area> areaList = areaService.getAllareas();
      model.addAttribute(new EventForm());
      model.addAttribute("areaList", areaList);

      return "eventinput";
   }

   @GetMapping("/user")
   public String showUserPage(Model model) {

      List<Area> areaList = areaService.getAllareas();
      model.addAttribute("areaList", areaList);
      model.addAttribute(new UserForm());

      return "userinput";
   }

   @GetMapping("/userlist")
   public String showUserListPage(Model model, RedirectAttributes attributes,
         @ModelAttribute @Validated UserTagForm form,
         BindingResult bindingResult) {

      List<User> userList = userService.getusers();
      List<Tag> tagList = tagService.getTags();
      model.addAttribute("tagList", tagList);
      model.addAttribute("userList", userList);
      model.addAttribute("userService", userService);

      return "userlistpage";
   }

   @GetMapping("/eventlist")
   public String showEventListPage(Model model, RedirectAttributes attributes,
         @ModelAttribute @Validated EventTagForm form,
         BindingResult bindingResult) {

      List<Event> eventList = eventService.getevents();
      List<Event> eventListSorted = eventService.getEventsByStartday(eventList);
      List<Tag> tagList = tagService.getTags();
      model.addAttribute("tagList", tagList);
      model.addAttribute("eventList", eventListSorted);
      model.addAttribute("eventService", eventService);

      return "eventlistpage";
   }

   @GetMapping("/login")
   public String showLoginPage(Model model) {

      model.addAttribute(new IdForm());
      List<User> userList = userService.getusers();
      model.addAttribute("userList", userList);

      return "login";
   }

   @GetMapping("/service")
   public String showServicePage(Model model, RedirectAttributes attributes,
         @ModelAttribute @Validated IdForm form,
         BindingResult bindingResult) {

      // フォームにバリデーション違反があった場合、タグ登録ページに戻る
      if (bindingResult.hasErrors()) {
         attributes.addFlashAttribute("isEventFormError", true);

         return "redirect:/login";
      }

      // ユーザIDを変数に格納する
      final int uid = form.getId();

      // ユーザが存在するか確認する
      if (!userService.existsUser(uid)) {
         attributes.addFlashAttribute("isUserAlreadyExistsError", true);

         return "redirect:/login";
      }

      // ユーザIDからユーザを取得する
      User user = userService.getUser(uid);
      int aid = user.getAid();
      List<UserTag> userTagList = userService.getUserTagsByUid(uid);
      List<Integer> tidList = userService.getTidsByUserTags(userTagList);
      List<Event> eventList = eventService.getEventsByTagIdList(tidList);
      List<Event> eventListByArea = eventService.getEventsByAreaId(aid, eventList);
      List<Event> eventListByOneMonth = eventService.getEventsByOneMonth(eventListByArea);

      model.addAttribute("user", user);
      model.addAttribute("eventList", eventListByOneMonth);
      model.addAttribute("eventService", eventService);

      return "service";
   }

}

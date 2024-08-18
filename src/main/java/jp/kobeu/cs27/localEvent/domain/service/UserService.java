package jp.kobeu.cs27.localEvent.domain.service;

import static jp.kobeu.cs27.localEvent.configuration.exception.ErrorCode.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.kobeu.cs27.localEvent.application.form.UserForm;
import jp.kobeu.cs27.localEvent.application.form.UserTagForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.entity.Area;
import jp.kobeu.cs27.localEvent.domain.entity.Event;
import jp.kobeu.cs27.localEvent.domain.entity.Tag;
import jp.kobeu.cs27.localEvent.domain.entity.User;
import jp.kobeu.cs27.localEvent.domain.entity.UserEvent;
import jp.kobeu.cs27.localEvent.domain.entity.UserTag;
import jp.kobeu.cs27.localEvent.domain.repository.EventRepository;
import jp.kobeu.cs27.localEvent.domain.repository.TagRepository;
import jp.kobeu.cs27.localEvent.domain.repository.UserEventRepository;
import jp.kobeu.cs27.localEvent.domain.repository.UserRepository;
import jp.kobeu.cs27.localEvent.domain.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository users;
    private final TagRepository tags;
    private final EventRepository events;
    private final UserTagRepository userTags;
    private final UserEventRepository userEvents;
    private final AreaService areaService;

    /**
     * ユーザを追加する
     * 
     * @param form ユーザのフォーム
     * @return 追加したユーザ
     */

    public User addUser(UserForm form) {

        // ユーザIDを変数に格納する
        final int uid = form.getUid();

        // ユーザが既に存在する場合は例外を投げる
        if (users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_ALREADY_EXISTS,
                    "create the User",
                    String.format("User id %d already exists", uid));
        }

        // ユーザをDBに登録し、登録したユーザの情報を戻り値として返す
        return users.save(new User(uid, form.getName(), form.getEmail(), form.getPassword(), form.getAid()));

    }

    /**
     * ユーザを更新する
     * 
     * @param form ユーザのフォーム
     * @return 更新したユーザ
     */
    public User updateUser(UserForm form) {

        // ユーザIDを変数に格納する
        final int uid = form.getUid();

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "update the user",
                    String.format("user id %d not found", uid));
        }

        // ユーザをDBに登録し、登録したユーザの情報を戻り値として返す
        return users.save(new User(uid, form.getName(), form.getEmail(), form.getPassword(), form.getAid()));
    }

    /**
     * ユーザを削除する
     * 
     * @param tid ユーザID
     */
    @Transactional
    public void deleteUser(int uid) {

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "delete the User",
                    String.format("User id %d not found", uid));
        }

        // ユーザを削除する
        users.deleteById(uid);
    }

    /**
     * 指定したユーザがDBに存在するかどうかを返す
     * 
     * @param tid ユーザID
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
    public boolean existsUser(int uid) {
        return users.existsByUid(uid);
    }

    /**
     * ユーザの情報を取得する
     * 
     * @param tid ユーザID
     * @return ユーザの情報
     */
    public User getUser(int uid) {

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the User",
                    String.format("User id %d not found", uid));
        }

        // ユーザの情報を取得する
        return users.findById(uid).get();
    }

    /**
     * ユーザの一覧を取得する
     * 
     * @return ユーザの一覧
     */
    public List<User> getusers() {
        return users.findAllByOrderByUidAsc();
    }

    /**
     * ユーザとタグを紐付ける
     */
    public void addTagToUser(UserTagForm form) {

        // ユーザIDを変数に格納する
        final int uid = form.getUid();
        // タグIDを変数に格納する
        final int tid = form.getTid();

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the User",
                    String.format("User id %d not found", uid));
        }

        // タグが存在しない場合は例外を投げる
        if (!tags.existsByTid(tid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the tag",
                    String.format("Tag id %d not found", tid));
        }

        // ユーザとタグを紐付ける
        userTags.save(new UserTag(0, uid, tid));
    }

    /**
     * ユーザとタグの紐付けを解除する
     */
    @Transactional
    public void deleteTagFromUser(UserTagForm form) {

        // ユーザIDを変数に格納する
        final int uid = form.getUid();
        // タグIDを変数に格納する
        final int tid = form.getTid();

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the User",
                    String.format("User id %d not found", uid));
        }

        // タグが存在しない場合は例外を投げる
        if (!tags.existsByTid(tid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the tag",
                    String.format("Tag id %d not found", tid));
        }

        // ユーザとタグの紐付けを解除する
        userTags.deleteByUidAndTid(uid, tid);
    }

    /**
     * ユーザIDからユーザタグの一覧を取得する
     */
    public List<UserTag> getUserTagsByUid(int uid) {

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the User",
                    String.format("User id %d not found", uid));
        }

        // ユーザタグの一覧を取得する
        return userTags.findAllByUid(uid);
    }

    /**
     * ユーザリストのエリアIDからエリアの一覧を取得する
     *
     * @param userList
     * @return
     */
    public List<Area> getAreasByUserList(List<User> userList) {
        return userList.stream().map(User::getAid).map(areaService::getArea).toList();
    }

    /**
     * ユーザタグのリストからタグIDのリストを取得する
     */
    public List<Integer> getTidsByUserTags(List<UserTag> userTags) {
        return userTags.stream().map(UserTag::getTid).toList();
    }

    /**
     * ユーザIDからタグのリストを入手する
     */
    public List<Tag> getTagsByUid(int uid) {
        List<UserTag> userTagList = getUserTagsByUid(uid);
        List<Integer> tidList = getTidsByUserTags(userTagList);
        return tags.findAllByTidIn(tidList);
    }

    /**
     * ユーザタグが存在するかどうかを返す
     */
    public boolean existsUserTag(int uid, int tid) {
        return userTags.existsByUidAndTid(uid, tid);
    }

    /**
     * ユーザイベントが存在するかどうかを返す
     * 
     */
    public boolean existsUserEvent(int uid, int eid) {
        return userEvents.existsByUidAndEid(uid, eid);
    }

    /**
     * ユーザとイベントを紐付ける
     */

    public void addEventToUser(int uid, int eid) {

        // ユーザが存在しない場合は例外を投げる
        if (!users.existsByUid(uid)) {
            throw new ValidationException(
                    USER_DOES_NOT_EXIST,
                    "get the User",
                    String.format("User id %d not found", uid));
        }

        // イベントが存在しない場合は例外を投げる
        if (!events.existsByEid(eid)) {
            throw new ValidationException(
                    EVENT_DOES_NOT_EXIST,
                    "get the Event",
                    String.format("Event id %d not found", eid));
        }

        // ユーザイベントが既に存在するか確認する
        if (!existsUserEvent(uid, eid)) {
            // ユーザとイベントを紐付ける
            userEvents.save(new UserEvent(0, uid, eid));
        }

    }

    /**
     * ユーザIDから紐付けられているイベントの一覧を取得する
     */
     public List<Event> getEventsByUid(int uid) {
         List<UserEvent> userEventList = userEvents.findAllByUid(uid);
         List<Integer> eidList = userEventList.stream().map(UserEvent::getEid).toList();
         return events.findAllByEidIn(eidList);
     }

}

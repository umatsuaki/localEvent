package jp.kobeu.cs27.localEvent.domain.service;

import static jp.kobeu.cs27.localEvent.configuration.exception.ErrorCode.TAG_ALREADY_EXISTS;
import static jp.kobeu.cs27.localEvent.configuration.exception.ErrorCode.TAG_DOES_NOT_EXIST;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.kobeu.cs27.localEvent.application.form.TagForm;
import jp.kobeu.cs27.localEvent.configuration.exception.ValidationException;
import jp.kobeu.cs27.localEvent.domain.entity.Tag;
import jp.kobeu.cs27.localEvent.domain.repository.EventTagRepository;
import jp.kobeu.cs27.localEvent.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tags;
    private final EventTagRepository eventTags;

    /**
     * タグを追加する
     * 
     * @param form タグのフォーム
     * @return 追加したタグ
     */

    public Tag addTag(TagForm form) {

        // タグIDを変数に格納する
        final int tid = form.getTid();

        // タグが既に存在する場合は例外を投げる
        if (tags.existsByTid(tid)) {
            throw new ValidationException(
                    TAG_ALREADY_EXISTS,
                    "create the tag",
                    String.format("tag id %d already exists", tid));
        }

        // タグをDBに登録し、登録したタグの情報を戻り値として返す
        return tags.save(new Tag(tid, form.getName(), form.getDescription()));

    }

    /**
     * タグを更新する
     * 
     * @param form タグのフォーム
     * @return 更新したタグ
     */
    public Tag updateTag(TagForm form) {

        // タグIDを変数に格納する
        final int tid = form.getTid();

        // タグが存在しない場合は例外を投げる
        if (!tags.existsByTid(tid)) {
            throw new ValidationException(
                    TAG_DOES_NOT_EXIST,
                    "update the tag",
                    String.format("tag id %d not found", tid));
        }

        // タグをDBに登録し、登録したタグの情報を戻り値として返す
        return tags.save(new Tag(tid, form.getName(), form.getDescription()));
    }

    /**
     * タグを削除する
     * 
     * @param tid タグID
     */
    @Transactional
    public void deleteTag(int tid) {

        // タグが存在しない場合は例外を投げる
        if (!tags.existsByTid(tid)) {
            throw new ValidationException(
                    TAG_DOES_NOT_EXIST,
                    "delete the tag",
                    String.format("tag id %d not found", tid));
        }

        // タグを削除する
        tags.deleteById(tid);

        // タグとイベントの関連を削除する
        eventTags.deleteByEtid(tid);
    }

    /**
     * 指定したタグがDBに存在するかどうかを返す
     * 
     * @param tid タグID
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
     public boolean existsTag(int tid) {
        return tags.existsByTid(tid);
     }

    /**
     * タグの情報を取得する
     * 
     * @param tid タグID
     * @return タグの情報
     */
    public Tag getTag(int tid) {

        // タグが存在しない場合は例外を投げる
        if (!tags.existsByTid(tid)) {
            throw new ValidationException(
                    TAG_DOES_NOT_EXIST,
                    "get the tag",
                    String.format("tag id %d not found", tid));
        }

        // タグの情報を取得する
        return tags.findById(tid).get();
    }

    /**
     * タグの一覧を取得する
     * 
     * @return タグの一覧
     */
    public List<Tag> getTags() {
        return tags.findAllByOrderByTidAsc();
    }

}

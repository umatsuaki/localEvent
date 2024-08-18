package jp.kobeu.cs27.localEvent.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import jp.kobeu.cs27.localEvent.domain.entity.UserEvent;

/**
 * ユーザーとイベントの関連のリポジトリ
 */
@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Integer> {
    /**
     * すべてのユーザイベントをID順に取得する
     * 
     * @param ueid ユーザーID
     * 
     * @return ユーザーのリスト
     * 
     */
    List<UserEvent> findAllByOrderByUeidAsc();

    /**
     * ユーザイベントIDが一致するユーザイベントを取得する
     */
    UserEvent findByUeid(int ueid);

    /**
     * ユーザIDに対応するユーザイベントを取得する
     */
    List<UserEvent> findAllByUid(int uid);

    /**
     * ユーザイベントIDが一致するユーザイベントを削除する
     */
    void deleteByUeid(int ueid);

    /**
     * ユーザイベントが存在するかどうかを返す
     */
    boolean existsByUidAndEid(int uid, int eid);

    /**
     * ユーザIDとイベントIDに対応するユーザイベントを削除する
     */
    void deleteByUidAndEid(int uid, int eid);
}

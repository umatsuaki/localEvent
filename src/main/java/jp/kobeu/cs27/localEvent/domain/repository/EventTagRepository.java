package jp.kobeu.cs27.localEvent.domain.repository;

import java.util.List;
import jp.kobeu.cs27.localEvent.domain.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * イベントとタグの関連のリポジトリ
 */
@Repository
public interface EventTagRepository extends JpaRepository<EventTag, Integer> {
    /**
     * すべてのユーザーをID順に取得する
     * 
     * @param uid ユーザーID
     * 
     * @return ユーザーのリスト
     * 
     */
    List<EventTag> findAllByOrderByEtidAsc();

    /**
     * タグが一致するイベントタグをすべて削除する
     * 
     * @param tid タグID
     */
    void deleteByEtid(int etid);

    /**
     * イベントタグIDに対応するイベントタグを入手する
     * 
     * @param etid イベントタグID
     */
    EventTag findByEtid(int etid);

    /**
     * タグIDに対応するイベントタグを入手する
     * 
     * @param tid タグID
     */
    List<EventTag> findAllByTid(int tid);

    /**
     * イベントIDに対応するイベントタグを入手する
     * 
     * @param eid イベントID
     * @return イベントタグのリスト
     */
    List<EventTag> findAllByEid(int eid);

    /**
     * タグIDのリストに対応するイベントタグのリストを入手する
     * 
     * @param tidList タグIDのリスト
     */
    List<EventTag> findAllByTidIn(List<Integer> tidList);

    /**
     * イベントタグのリストからイベントIDのリストを入手する
     * 
     * @param eventTagList イベントタグのリスト
     */
    List<EventTag> findAllByEtidIn(List<EventTag> eventTagList);

    /**
     * イベントIDとタグIDに対応するイベントタグが存在するかどうかを返す
     */
    boolean existsByEidAndTid(int eid, int tid);

    /**
     * イベントIDとタグIDに対応するイベントタグを削除する
     */
    void deleteByEidAndTid(int eid, int tid);

}

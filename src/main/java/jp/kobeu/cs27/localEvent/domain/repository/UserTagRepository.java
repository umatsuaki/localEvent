package jp.kobeu.cs27.localEvent.domain.repository;

import java.util.List;
import jp.kobeu.cs27.localEvent.domain.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * ユーザータグのリポジトリ
 */
@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Integer> {
    /**
     * すべてのユーザータグをID順に取得する
     * 
     * @param uid ユーザーID
     * 
     * @return ユーザーのリスト
     * 
     */
    List<UserTag> findAllByOrderByUtidAsc();

    /**
     * ユーザタグIDが一致するユーザタグを取得する
     */
    UserTag findByUtid(int utid);

    /**
     * ユーザIDに対応するユーザタグを取得する
     */
    List<UserTag> findAllByUid(int uid);

    /**
     * ユーザタグIDが一致するユーザタグを削除する
     */
    void deleteByUtid(int utid);

    /**
     * ユーザタグが存在するかどうかを返す
     */
    boolean existsByUidAndTid(int uid, int tid);

    /**
     * ユーザIDとタグIDに対応するユーザタグを削除する
     */
    void deleteByUidAndTid(int uid, int tid);

}

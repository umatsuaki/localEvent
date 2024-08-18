package jp.kobeu.cs27.localEvent.domain.repository;

import java.util.List;

import jp.kobeu.cs27.localEvent.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * タグのリポジトリ
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    /**
     * すべてのタグをID順に取得する
     * 
     * @param tud タグID
     * 
     * @return タグのリスト
     * 
     */
    List<Tag> findAllByOrderByTidAsc();

    /**
     * エリアIDに対応するエリアがあるか
     * 
     * @param tid エリアID
     * @return エリアがあるかどうか
     */
    boolean existsByTid(int tid);

    /**
     * タグIDのリストからタグのリストを入手する
     */
    List<Tag> findAllByTidIn(List<Integer> tidList);

}

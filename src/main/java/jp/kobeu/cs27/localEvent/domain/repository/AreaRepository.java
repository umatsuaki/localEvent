package jp.kobeu.cs27.localEvent.domain.repository;

import java.util.List;
import jp.kobeu.cs27.localEvent.domain.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * エリアーのリポジトリ
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    /**
     * すべてのエリアをID順に取得する
     * 
     * @param aid エリアID
     * 
     * @return エリアのリスト
     * 
     */
    List<Area> findAllByOrderByAidAsc();

    /**
     * エリアIDに対応するエリアがあるか
     * 
     * @param aid エリアID
     * @return エリアがあるかどうか
     */
    boolean existsByAid(int aid);

}

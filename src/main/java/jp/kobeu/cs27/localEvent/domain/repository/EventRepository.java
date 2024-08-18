package jp.kobeu.cs27.localEvent.domain.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.kobeu.cs27.localEvent.domain.entity.Event;

/*
 * イベントのリポジトリ
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    /**
     * すべてのイベントをID順に取得する
     * 
     * @param eid イベントID
     * 
     * @return イベントのリスト
     * 
     * 
     */
    List<Event> findAllByOrderByEidAsc();

    /**
     * イベントIDが一致するイベントを削除する
     * 
     * @param eid イベントID
     */
    void deleteByEid(int eid);

    /**
     * イベントIDに対応するイベントがあるか
     * 
     * @param eid イベントID
     * @return イベントがあるかどうか
     */
    boolean existsByEid(int eid);

    /**
     * イベントIDのリストに対応するイベントのリストを入手する
     */
    List<Event> findAllByEidIn(List<Integer> eidList);

}

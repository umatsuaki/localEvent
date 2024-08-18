package jp.kobeu.cs27.localEvent.domain.repository;
import java.util.List;
import jp.kobeu.cs27.localEvent.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * ユーザーのリポジトリ
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * すべてのユーザーをID順に取得する
     * 
     * @param uid ユーザーID
     * 
     * @return ユーザーのリスト
     * 
     */
    List<User> findAllByOrderByUidAsc();

    /**
     * ユーザーIDが一致するユーザーがあるかどうか
     * 
     * @param uid ユーザーID
     * 
     * @return ユーザーがあるかどうか
     */

    boolean existsByUid(int uid);

}

package jp.kobeu.cs27.localEvent.domain.entity;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * イベントを表すエンティティ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    // イベントID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;

    // イベント名
    private String name;

    // イベントの説明
    private String description;

    // イベントの開始日
    private LocalDate startday;

    // イベントの終了日
    private LocalDate endday;

    // イベントの開催時間の開始
    private LocalTime starttime;

    // イベントの開催時間の終了
    private LocalTime endtime;

    //　イベントの申込み開始日
    private LocalDate startdayOfApplication;

    // イベントの申込み終了日
    private LocalDate enddayOfApplication;

    // イベントの申込み開始時間
    private LocalTime starttimeOfApplication;

    // イベントの申込み終了時間
    private LocalTime endtimeOfApplication;

    // イベントの会場
    private String place;

    // イベントの参加費
    private int fee;

    // イベントに駐車場があるか
    private boolean parking;

    // イベントのアクセス
    private String access;

    // イベントのエリアID
    private int aid;

    // イベントの主催者
    private String organizer;

    // イベントの定員
    private int capacity;

    // イベントの問い合わせ先
    private String contact;

    // イベントのURL
    private String url;

    // イベントの画像
    @Lob
    private Blob image;

    // イベントの画像があるか
    private boolean imageExist;

}

package jp.kobeu.cs27.localEvent.application.form;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * イベント登録・更新フォーム
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventForm {

    // イベントID
    private int eid;

    // イベント名
    private String name;

    // イベントの説明
    private String description;

    // イベントの開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startday;

    // イベントの終了日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endday;

    // イベントの開催時間の開始
    private LocalTime starttime;

    // イベントの開催時間の終了
    private LocalTime endtime;

    // イベントの申込み開始日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdayOfApplication;

    // イベントの申込み終了日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    private MultipartFile image;

}

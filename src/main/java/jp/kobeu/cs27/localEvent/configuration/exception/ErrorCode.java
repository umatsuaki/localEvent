package jp.kobeu.cs27.localEvent.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラーコード
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_DOES_NOT_EXIST(11),
    USER_ALREADY_EXISTS(12),
    EVENT_DOES_NOT_EXIST(21),
    EVENT_ALREADY_EXISTS(22),
    Area_DOES_NOT_EXIST(31),
    Area_ALREADY_EXISTS(32),
    TAG_DOES_NOT_EXIST(41),
    TAG_ALREADY_EXISTS(42),
    CONTROLLER_VALIDATION_ERROR(97),
    CONTROLLER_REJECTED(98),
    OTHER_ERROR(99);

    private final int code;
}

package com.dokidoki.auction.common.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " 유효하지 않은 입력값입니다"),
    METHOD_NOT_ALLOWED(405, "C002", " HTTP 요청 메서드가 서버에서 허용되지 않습니다"),
    ENTITY_NOT_FOUND(404, "C003", "해당 엔티티가 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버에서 예상치 못한 오류가 발생했습니다"),
    INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 타입입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "접근 권한이 없습니다."),
    UNAUTHORIZED(401, "C007", "인증이 거부되었습니다"),
    INVALID_INPUT_USER(400, "C008", " 유효하지 않은 사용자 입니다."),


    // Auction
    INVALID_INPUT_AUCTION(400, "C001", " 유효하지 않은 경매 입니다."),


    // Business
    BUSINESS_EXCEPTION_ERROR(400, "B001", "비즈니스 로직에서 오류가 발생해 실패하였습니다.");



    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}


package com.dokidoki.bid.common.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(404, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    UNAUTHORIZED(401, "C007", "UnAuthorized"),

    // Business
    BUSINESS_EXCEPTION_ERROR(400, "B001", "Business Exception Occurs"),
    IS_NOT_SELLER(400, "B002", "Is not seller"),


    // Bid
    DIFFERENT_PRICE_SIZE(400, "BID001", "Different Price Size"),
    DIFFERENT_HIGHEST_PRICE(400, "BID002", "Different Highest Price"),
    FAILURE_GET_REALTIME_LOCK(500, "BID003", "Failure getting realtime lock"),
    AUCTION_ALREADY_ENDED(400, "BID004", "Already Ended"),
    SELLER_CANNOT_BID(400, "BID005", "Seller can not bid");

    private final String code;
    private final String message;
    private int status;

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
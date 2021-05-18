package com.springbank.bankacc.cmd.api.dtos;

import com.springbank.bankacc.core.dtos.BaseResponse;

public class OpenAccountResponse extends BaseResponse {
    private String id;

    public OpenAccountResponse(String message) {
        super(message);
    }

    public OpenAccountResponse(String message, String id) {
        super(message);
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

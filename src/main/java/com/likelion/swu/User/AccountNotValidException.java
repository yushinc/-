package com.likelion.swu.User;

import java.util.List;

public class AccountNotValidException extends RuntimeException{
    private List<String> errorList;

    public AccountNotValidException(List<String> errorList, String message) {
        super(message);
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}

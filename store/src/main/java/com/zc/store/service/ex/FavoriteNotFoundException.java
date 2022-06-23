package com.zc.store.service.ex;

public class FavoriteNotFoundException extends ServiceException {
    public FavoriteNotFoundException() {
        super();
    }

    public FavoriteNotFoundException(String message) {
        super(message);
    }

    public FavoriteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FavoriteNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FavoriteNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

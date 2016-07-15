package com.github.jonathanxd.iutils.exceptions;

/**
 * Created by jonathan on 15/07/16.
 */
public class JStringApplyException extends RuntimeException {
    public JStringApplyException() {
        super();
    }

    public JStringApplyException(String message) {
        super(message);
    }

    public JStringApplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JStringApplyException(Throwable cause) {
        super(cause);
    }

    protected JStringApplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

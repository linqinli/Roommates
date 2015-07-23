package com.netease.exception;

/**
 * @author hzlinqinli
 *
 */
public class StorageException extends Exception {
    private static final long serialVersionUID = 1;

    public StorageException() {
        super();
    }

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(Throwable t) {
        super(t);
    }

    public StorageException(String msg, Throwable t) {
        super(msg, t);
    }
}

package com.zc.store.controller.ex;

/**
 *  文件状态异常，比如上传一个文件时这个文件正在被使用，系统会提示你先关闭，这个文件的打开和关闭就是文件的状态
 */
public class FileStateException extends FileUploadException {

    public FileStateException() {
        super();
    }

    public FileStateException(String message) {
        super(message);
    }

    public FileStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStateException(Throwable cause) {
        super(cause);
    }

    protected FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

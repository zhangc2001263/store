package com.zc.store.controller.ex;

/**
 * 文件为空异常，就是没有选择文件就上传，或者选择了个0字节大小的文件都会报这个异常
 */
public class FileEmptyException extends FileUploadException{

    public FileEmptyException() {
        super();
    }

    public FileEmptyException(String message) {
        super(message);
    }

    public FileEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileEmptyException(Throwable cause) {
        super(cause);
    }

    protected FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

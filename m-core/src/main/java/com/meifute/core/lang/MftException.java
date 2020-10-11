package com.meifute.core.lang;

/**
 * @author wzeng
 * @date 2020/7/30
 * @Description
 */
public class MftException extends  Exception{
    int errorCode=0;

    public MftException(String message, Throwable cause) {
        super(message, cause);
    }

    public MftException(String message) {
        super(message);
    }

    public MftException() {
        super();
    }

    public MftException(Throwable cause) {
        super(cause);
    }

    public MftException(String message, Throwable cause,int errorCode) {
       this(message, cause);
        this.errorCode=errorCode;

    }

    public MftException(String message,int errorCode) {
        this(message);
        this.errorCode=errorCode;
    }

    public MftException(int errorCode) {
        this();
        this.errorCode=errorCode;
    }

    public MftException(Throwable cause,int errorCode) {
        this(cause);
        this.errorCode=errorCode;
    }

   public int getErrorCode(){
        return this.errorCode;
   }
}

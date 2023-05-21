package bll.exceptions;

/**
 * The ApplicationException wraps all checked standard Java exception and enriches them with a custom error code.
 * You can use this code to retrieve localized error messages.
 */

public class ApplicationException extends Exception {
    private String errorCode = "Unknown_Exception";
    public ApplicationException(String message, Throwable cause,String errorCode)  {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public ApplicationException(String message)  {
        super(message);
    }
    public ApplicationException (String message, Throwable cause)  {
        super(message, cause);
    }
    public ApplicationException(String message, String errorCode)  {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause, String errorCode)  {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

}



package springboot.Exception;

import org.springframework.http.HttpStatus;

public class ApiError {
	private HttpStatus HttpCode;
	private String Message;
	private String Path;
	public ApiError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiError(HttpStatus httpCode, String message, String path) {
		super();
		HttpCode = httpCode;
		Message = message;
		Path = path;
	}
	/**
	 * @return the httpCode
	 */
	public HttpStatus getHttpCode() {
		return HttpCode;
	}
	/**
	 * @param httpCode the httpCode to set
	 */
	public void setHttpCode(HttpStatus httpCode) {
		HttpCode = httpCode;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return Path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		Path = path;
	}
	
}

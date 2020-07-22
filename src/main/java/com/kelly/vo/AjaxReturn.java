package com.kelly.vo;

public class AjaxReturn<T> {

	private String code;

	private String message;

	private T retData;

	private AjaxReturn(String code, T obj) {
		this.code = code;
		this.retData = obj;
	}

	private AjaxReturn(String code, T obj, String message) {
		this.code = code;
		this.retData = obj;
		this.message = message;
	}

	private AjaxReturn() {

	}

	public  static <T> AjaxReturn<T> success() {
		return new AjaxReturn<T>("0", null, "操作成功");
	}

	public static <T> AjaxReturn<T> success(T t) {
		return new AjaxReturn<T>("0", t);
	}

	public static <T> AjaxReturn<T> error(String errorMessage) {
		return new AjaxReturn<T>("1", null, errorMessage);
	}

	public static <T> AjaxReturn<T> success(T t, String message) {
		return new AjaxReturn<T>("0", t, message);
	}


	public T getRetData() {
		return retData;
	}

	public void setRetData(T t) {
		this.retData = t;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package com.example.cp.common.exception;

/**
 * @Description: 自定义异常类
 * @Author: chenping
 * @Date: 2020-05-23
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(String msg, Exception e) {
		super(msg, e);
	}

	public CustomException(Exception e) {
		super(e);
	}
}

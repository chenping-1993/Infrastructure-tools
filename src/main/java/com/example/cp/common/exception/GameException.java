package com.example.cp.common.exception;

/**
 * @Description: 自定义异常类
 * @Author: chenping
 * @Date: 2020-05-23
 */
public class GameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameException(String msg) {
		super(msg);
	}

	public GameException(String msg, Exception e) {
		super(msg, e);
	}

	public GameException(Exception e) {
		super(e);
	}
}

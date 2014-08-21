package com.example.test02.core;

import java.io.Serializable;


public class BaseOutput extends Object implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 * true:成功调用；false：系统错误/传入参数错误
	 */
	private Boolean flag;
	
	/**
	 * 错误原因
	 */
	private String message;

	public boolean getFlag() {
		return flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}

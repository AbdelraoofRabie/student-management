package com.boubyan.studentmanagement.common;

import java.io.Serializable;

public class GenericResponse<T> implements Serializable {

	private static final long serialVersionUID = 2093778774969805269L;

	private T data;

	private String message;

	public GenericResponse(T data, String message) {
		this.data = data;
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

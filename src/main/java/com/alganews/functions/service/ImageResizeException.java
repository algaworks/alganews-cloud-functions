package com.alganews.functions.service;

public class ImageResizeException extends RuntimeException {
	
	public ImageResizeException() {
	}
	
	public ImageResizeException(String message) {
		super(message);
	}
	
	public ImageResizeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ImageResizeException(Throwable cause) {
		super(cause);
	}
	
	public ImageResizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

package com.alganews.functions.util;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class ContentTypeUtil {
	
	public static String getContentTypeBy(String fileName) {
		String extension = getExtension(fileName);
		
		if (extension.equalsIgnoreCase("jpg")) {
			extension = "jpeg";
		}
		
		return "image/" + extension.toLowerCase();
	}
	
}

package com.alganews.functions.util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TaskUtil {
	
	public static <T> CompletableFuture<List<T>> join(List<CompletableFuture<T>> futures) {
		CompletableFuture[] tasks = futures.toArray(new CompletableFuture[futures.size()]);
		
		return CompletableFuture.allOf(tasks)
				.thenApply(ignored -> futures.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList())
				);
	}
	
}

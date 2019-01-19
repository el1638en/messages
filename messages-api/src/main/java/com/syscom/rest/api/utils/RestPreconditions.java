package com.syscom.rest.api.utils;

public class RestPreconditions {

	public static <T> T checkFound(final T resource) {
		if (resource == null) {
			throw new IllegalArgumentException("Null Object is rejected by the API !");
		}
		return resource;
	}

}

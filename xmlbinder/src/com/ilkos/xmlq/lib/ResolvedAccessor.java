package com.ilkos.xmlq.lib;

import java.lang.reflect.InvocationTargetException;

public interface ResolvedAccessor {
	public Class<?> getValueType();
	public void set(Object root, Object val) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
}

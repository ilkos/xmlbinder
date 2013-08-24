package com.ilkos.xmlq.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodAccessor implements ResolvedAccessor {
	private final Class<?> type;
	private final Method method;

	public MethodAccessor(Class<?> type, Method m) {
		this.type = type;
		this.method = m;
	}

	@Override
	public Class<?> getValueType() {
		return type;
	}

	@Override
	public void set(Object root, Object val) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		method.invoke(root, val);
	}

}

package com.ilkos.xmlq.lib;

import java.lang.reflect.Method;
import java.util.HashMap;

public class ClassResolver implements XMLClassResolver {
	private final HashMap<Class<?>, HashMap<String, Method>> setters = new HashMap<Class<?>, HashMap<String, Method>>();

	@Override
	public Method getter(Class<?> cls, String field) {
		return null;
	}

	@Override
	public Method setter(Class<?> cls, String field) throws ResolverExceptionMethodNotFound {
		HashMap<String, Method> cSetters = setters.get(cls);
		if (cSetters == null) {
			cSetters = resolveClassMethods(cls);
		}

		Method m = cSetters.get(field.toLowerCase());
		if (m != null) {
			return m;
		}
		throw new ResolverExceptionMethodNotFound("Setter not found " + cls + " " + field);
	}

	private HashMap<String, Method> resolveClassMethods(Class<?> cls) {
		HashMap<String, Method> resolved = new HashMap<String, Method>();

		Method[] allMethods = cls.getDeclaredMethods();
		for (Method m : allMethods) {
			if (!m.getName().startsWith("set")) continue;
			
			resolved.put(m.getName().substring(3).toLowerCase(), m);
		}
		
		setters.put(cls, resolved);
		return resolved;
	}
}

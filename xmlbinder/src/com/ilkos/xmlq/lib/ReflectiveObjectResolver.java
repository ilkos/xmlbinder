package com.ilkos.xmlq.lib;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReflectiveObjectResolver implements XMLObjectResolver {
	private final HashMap<Class<?>, HashMap<String, ResolvedAccessor>> resolvedClasses = new HashMap<Class<?>,
			HashMap<String, ResolvedAccessor>>();

	public boolean isResolved(Class<?> cls) {
		return resolvedClasses.containsKey(cls);
	}

	public HashMap<String, ResolvedAccessor> resolve(Class<?> cls) {
		if (isResolved(cls)) {
			return resolvedClasses.get(cls);
		}
		
		final HashMap<String, ResolvedAccessor> r = processClass(cls);
		resolvedClasses.put(cls, r);
		return r;
	}

	private boolean isInstantiatable(Class<?> cls) {
		return !(cls.isArray() || cls.isInterface() || cls.isEnum());
	}

	private HashMap<String, ResolvedAccessor> processClass(Class<?> cls) {
		HashMap<String, ResolvedAccessor> resolved = new HashMap<String, ResolvedAccessor>();

		Method[] allMethods = cls.getDeclaredMethods();
		for (Method m : allMethods) {
			if (m.getName().startsWith("set")) {
				Class<?>[] parameters = m.getParameterTypes();

				// a setter must take a single argument, of the type to be set
				if (parameters.length != 1) {
					continue;
				}

				// we cannot instantiate the target object, discard
				if (!isInstantiatable(parameters[0])) {
					continue;
				}

				// collections are not assigned directly, rather we append elements on them
				if (Collection.class.isAssignableFrom(parameters[0]) ||
						Map.class.isAssignableFrom(parameters[0])) {
					continue;
				}

				final String accessorName = m.getName().substring(3).toLowerCase();
				if (resolved.containsKey(accessorName)) {
					throw new IllegalArgumentException("Ambiguity: name " + accessorName + " already exists");
				}

				resolved.put(accessorName, new MethodAccessor(parameters[0], m));
			}
			else if (m.getName().startsWith("get")) {
				// TODO, collections
			}
		}

		return resolved;
	}

	@Override
	public ResolvedAccessor getAttributeObject(Class<?> cls, String xmlAttributeName) {
		return resolve(cls).get(xmlAttributeName.toLowerCase());
	}

	@Override
	public ResolvedAccessor getElementObject(Class<?> cls, String xmlElementName) {
		return resolve(cls).get(xmlElementName.toLowerCase());
	}

	@Override
	public ResolvedAccessor getPCDataObject(Class<?> cls) {
		return null;
	}
}

package com.ilkos.xmlq.lib;

import java.lang.reflect.Method;

public interface XMLClassResolver {
	Method getter(Class<?> cls, String field);
	Method setter(Class<?> cls, String field) throws ResolverExceptionMethodNotFound;
}

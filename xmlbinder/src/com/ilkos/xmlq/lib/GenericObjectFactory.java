package com.ilkos.xmlq.lib;

import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class GenericObjectFactory {

	public static <T> T instantiate(Class<T> cls) throws XMLBinderException {
		T obj = null;
		try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		}
		return obj;
	}
}

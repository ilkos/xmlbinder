package com.ilkos.xmlq.lib;

public interface TypeConverter {
	Object convert(Class<?> target, String s);
}

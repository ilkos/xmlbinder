package com.ilkos.xmlq.lib;

public class SimpleTypeConverter {

	public Object convert(Class<?> target, String s) {
		if (target.isAssignableFrom(String.class)) {
			return s;
		}
		else if (target.isAssignableFrom(Boolean.class)) {
			return Boolean.parseBoolean(s);
		}
		else if (target.isAssignableFrom(Byte.class)) {
			return Byte.parseByte(s);
		}
		else if (target.isAssignableFrom(Short.class)) {
			return Short.parseShort(s);
		}
		else if (target.isAssignableFrom(Integer.class)) {
			return Integer.parseInt(s);
		}
		else if (target.isAssignableFrom(Float.class)) {
			return Float.parseFloat(s);
		}
		else if (target.isAssignableFrom(Long.class)) {
			return Long.parseLong(s);
		}
		else if (target.isAssignableFrom(Float.class)) {
			return Float.parseFloat(s);
		}
		else if (target.isAssignableFrom(Double.class)) {
			return Double.parseDouble(s);
		}
		else {
			throw new IllegalArgumentException("Invalid class type " + target);
		}
	}

	public boolean isSimple(Class<?> cls) {
		return true;
	}
}

package com.ilkos.xmlq.lib;

public interface XMLObjectResolver {
	public ResolvedAccessor getAttributeObject(Class<?> cls, String xmlAttributeName);
	public ResolvedAccessor getElementObject(Class<?> cls, String xmlElementName);
	public ResolvedAccessor getPCDataObject(Class<?> cls);
}

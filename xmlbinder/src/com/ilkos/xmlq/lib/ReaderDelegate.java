package com.ilkos.xmlq.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class ReaderDelegate {
	private final XMLStreamReader reader;
	private final XMLClassResolver resolver;
	private static final TypeConverter simpleTypeConverter = new SimpleTypeConverter();

	public ReaderDelegate(XMLStreamReader reader, XMLClassResolver resolver) {
		this.reader = reader;
		this.resolver = resolver;
	}

	public <T> T deserialise(Class<T> cls) throws XMLBinderException {
		if (!reader.isStartElement()) {
			throw new XMLBinderException("Expected start element not found");
		}
		final String elementName = reader.getName().getLocalPart();

		final T inst = instantiate(cls);

		// TODO: go through attributes if any
		for (int i = 0; i < reader.getAttributeCount(); ++i) {

		}

		// TODO: deserialise for collections
		try {
			reader.next();
			while (!reader.isEndElement()) {

				if (reader.isStartElement()) {
					try {
						Method setter = resolver.setter(cls, reader.getName().getLocalPart());
						setter.invoke(inst, deserialise(setter.getParameterTypes()[0]));
					}
					catch (ResolverExceptionMethodNotFound e) {
						throw new XMLBinderException();
					}
				}
				else if (reader.isCharacters()) {
					try {
						Method setter = resolver.setter(cls, elementName);
						setter.invoke(inst, simpleTypeConverter.convert(setter.getParameterTypes()[0], reader.getText()));
					}
					catch (ResolverExceptionMethodNotFound e) {
						throw new XMLBinderException();
					}
				}

				if (!reader.hasNext()) {
					throw new XMLBinderException("Unexpected end of stream");
				}
				reader.next();
			}
			return inst;
		}
		catch (XMLStreamException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		}
	}

	private <T> T instantiate(Class<T> cls) throws XMLBinderException {
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
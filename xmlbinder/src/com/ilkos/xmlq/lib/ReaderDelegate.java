package com.ilkos.xmlq.lib;

import java.lang.reflect.InvocationTargetException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class ReaderDelegate {
	private final XMLStreamReader reader;
	private final XMLObjectResolver resolver;

	public ReaderDelegate(XMLStreamReader reader, XMLObjectResolver resolver) {
		this.reader = reader;
		this.resolver = resolver;
	}

	public <T> T deserialiseElement(Class<T> cls) throws XMLBinderException {
		if (!reader.isStartElement()) {
			throw new XMLBinderException("Expected start element not found");
		}

		final T inst = GenericObjectFactory.instantiate(cls);

		// TODO: go through attributes if any
		for (int i = 0; i < reader.getAttributeCount(); ++i) {

		}

		try {
			reader.next();
			while (!reader.isEndElement()) {

				if (reader.isStartElement()) {
					ResolvedAccessor elementAccessor = resolver.getElementObject(cls, reader.getName().getLocalPart());
					if (elementAccessor != null) {
						// leaf nodes
						if (SimpleTypeConverter.isSimple(elementAccessor.getValueType())) {
							elementAccessor.set(inst,
									SimpleTypeConverter.convert(elementAccessor.getValueType(),
											deserialiseField()));
						}
						else {
							elementAccessor.set(inst, deserialiseElement(elementAccessor.getValueType()));
						}
					}
					else {
						throw new XMLBinderException("Could not resolve " + reader.getName().getLocalPart());
					}
				}
				else if (reader.isCharacters()) {
					// TODO
					// ResolvedAccessor pcDataAccessor = resolver.getPCDataObject(cls);
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
	
	public String deserialiseField() throws XMLBinderException {
		if (!reader.isStartElement()) {
			throw new XMLBinderException("Expected start element not found");
		}
		
		if (reader.getAttributeCount() > 0) {
			throw new XMLBinderException("Attributes present on field");
		}
		
		String result = null;

		try {
			reader.next();
			while (!reader.isEndElement()) {
				if (reader.isStartElement()) {
					throw new XMLBinderException("Nested element present on field");
				}
				else if (reader.isCharacters()) {
					result = reader.getText();
				}
				
				reader.next();
			}
		}
		catch (XMLStreamException e) {
			e.printStackTrace();
			throw new XMLBinderException();
		}
		
		return result;
	}
}

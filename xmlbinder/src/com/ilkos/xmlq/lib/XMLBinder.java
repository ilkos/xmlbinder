package com.ilkos.xmlq.lib;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class XMLBinder {
	// TODO: to be replaced with generic provider
	private final XMLInputFactory xmlFactory;

	// Resolver interface - strict / non-strict / defaulting implementations
	private final XMLObjectResolver resolver;

	public XMLBinder(XMLInputFactory xmlFactory, XMLObjectResolver resolver) {
		this.xmlFactory = xmlFactory;
		this.resolver = resolver;
	}

	public <T> T fromXML(InputStream xmlInput, Class<T> cls) throws XMLBinderException {
		XMLStreamReader streamReader;
		try {
			streamReader = initialiseStreamReader(xmlInput);
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw new XMLBinderException("Cannot initialise stream reader");
		}

		T data;
		try {
			ReaderDelegate reader = new ReaderDelegate(streamReader, resolver);
			data = reader.deserialise(cls);
		} finally {
			try {
				streamReader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	private XMLStreamReader initialiseStreamReader(InputStream in) throws XMLBinderException, XMLStreamException {
		XMLStreamReader r = xmlFactory.createXMLStreamReader(in);

		if (r.getEventType() != XMLStreamConstants.START_DOCUMENT) {
			r.close();
			throw new XMLBinderException("Not at start document");
		}

		if (!r.hasNext()) {
			r.close();
			throw new XMLBinderException("No available input");
		}
		
		r.next();

		return r;
	}
}

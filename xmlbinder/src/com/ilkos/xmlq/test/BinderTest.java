package com.ilkos.xmlq.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLInputFactory;

import org.junit.Test;

import com.ilkos.xmlq.lib.ReflectiveObjectResolver;
import com.ilkos.xmlq.lib.XMLBinder;
import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class BinderTest {

	public static class TestContainer {
		private String innerText;
		
		public void setInnerText(String container) {
			innerText = container;
		}
		
		public String getInnerText() {
			return innerText;
		}
	}

	@Test
	public void test() throws FileNotFoundException, XMLBinderException, UnsupportedEncodingException {
		String xml = "<text>" +
				"<innerText>Inner test</innerText>" +
				"</text>";
		XMLBinder nQ = new XMLBinder(XMLInputFactory.newInstance(), new ReflectiveObjectResolver());
		
		TestContainer res = nQ.fromXML(
				new ByteArrayInputStream(xml.getBytes("UTF-8")),
				TestContainer.class);
		assertEquals(res.getInnerText(), "Inner test");
	}

}

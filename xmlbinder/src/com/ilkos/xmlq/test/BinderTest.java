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
		private Integer magic;
		
		public void setInnerText(String container) {
			innerText = container;
		}
		
		public String getInnerText() {
			return innerText;
		}
		
		public void setMagic(Integer magic) {
			this.magic = magic;
		}
		
		public Integer getMagic() {
			return magic;
		}
	}

	@Test
	public void test() throws FileNotFoundException, XMLBinderException, UnsupportedEncodingException {
		String xml = "<text>" +
				"<innerText>Inner test</innerText>" +
				"<magic>42</magic>" +
				"</text>";
		XMLBinder nQ = new XMLBinder(XMLInputFactory.newInstance(), new ReflectiveObjectResolver());
		
		TestContainer res = nQ.fromXML(
				new ByteArrayInputStream(xml.getBytes("UTF-8")),
				TestContainer.class);
		assertEquals(res.getInnerText(), "Inner test");
		assertEquals(res.getMagic(), Integer.valueOf(42));
	}

}

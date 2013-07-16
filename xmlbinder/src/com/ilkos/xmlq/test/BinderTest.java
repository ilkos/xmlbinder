package com.ilkos.xmlq.test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;

import org.junit.Test;

import com.ilkos.xmlq.lib.ClassResolver;
import com.ilkos.xmlq.lib.XMLBinder;
import com.ilkos.xmlq.lib.exception.XMLBinderException;

public class BinderTest {

	@Test
	public void test() throws FileNotFoundException, XMLBinderException {
		XMLBinder nQ = new XMLBinder(XMLInputFactory.newInstance(), new ClassResolver());
		
		TestContainer res = nQ.fromXML(new FileInputStream("src/com/ilkos/xmlq/test/test.txt"),
				TestContainer.class);
		assertEquals(res.getTest(), "Hello world");
	}

}

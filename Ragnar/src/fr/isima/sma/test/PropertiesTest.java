package fr.isima.sma.test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.isima.sma.resources.Properties;

public class PropertiesTest {
	
	Properties p;
	
	@Before
	public void before() {
		p = Properties.getInstance();
	}	
	
	@After
	public void after() {
		
	}
	
	@Test
	public void testSingleton() {
		assertEquals(p, Properties.getInstance());
	}
	
	@Test
	public void testChangeFile() throws IOException {
		p.setPropertiesFileName("TESTsimulation.properties");
		assertEquals("TESTsimulation.properties", p.getPropertiesFileName());
		assertEquals("agents.txt", p.getProperty("agentsFile"));
	}
	
	@Test
	public void testDefaultValues() throws IOException {
		p.setPropertiesFileName("TESTsimulation.properties");
		assertEquals("64", p.getProperty("caseSize"));
		assertEquals("agents.txt", p.getProperty("agentsFile"));
	}
	
	@Test
	public void testLoadedValues() throws IOException {
		p.setPropertiesFileName("TESTsimulation.properties");
		assertEquals("999", p.getProperty("daysperyear"));
		assertEquals("TESTassets.rc", p.getProperty("resources"));
	}
	
	@Test (expected=FileNotFoundException.class)
	public void testUnknownFile() throws IOException {
		p.setPropertiesFileName("TESTunknown.properties");
		assertEquals("TESTsimulation.properties", p.getPropertiesFileName());
	}
	
	@Test
	public void testUnknownProperty() throws IOException {
		p.setPropertiesFileName("TESTsimulation.properties");
		assertEquals(null, p.getProperty("Unknown"));
		assertEquals(null, p.getProperty(""));
	}

}

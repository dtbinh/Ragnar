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
		p.setPropertiesFileName("test/TESTsimulation.properties");
		assertEquals("test/TESTsimulation.properties", p.getPropertiesFileName());
		assertEquals("settings/agents.txt", p.getProperty("agentsFile"));
	}
	
	@Test
	public void testDefaultValues() throws IOException {
		p.setPropertiesFileName("test/TESTsimulation.properties");
		assertEquals("64", p.getProperty("caseSize"));
		assertEquals("settings/agents.txt", p.getProperty("agentsFile"));
	}
	
	@Test
	public void testLoadedValues() throws IOException {
		p.setPropertiesFileName("test/TESTsimulation.properties");
		assertEquals("999", p.getProperty("daysperyear"));
		assertEquals("test/TESTassets.rc", p.getProperty("resources"));
	}
	
	@Test (expected=FileNotFoundException.class)
	public void testUnknownFile() throws IOException {
		p.setPropertiesFileName("test/TESTunknown.properties");
		assertEquals("test/TESTsimulation.properties", p.getPropertiesFileName());
	}
	
	@Test
	public void testUnknownProperty() throws IOException {
		p.setPropertiesFileName("test/TESTsimulation.properties");
		assertEquals(null, p.getProperty("Unknown"));
		assertEquals(null, p.getProperty(""));
	}

}

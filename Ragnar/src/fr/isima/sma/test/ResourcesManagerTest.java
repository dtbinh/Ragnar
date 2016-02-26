package fr.isima.sma.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.isima.sma.resources.ResourcesManager;

public class ResourcesManagerTest {

	ResourcesManager r;
	
	@Before
	public void before() {
		r = ResourcesManager.getInstance();
	}	
	
	@After
	public void after() {
		
	}
	
	@Test
	public void testSingleton() {
		assertEquals(r, ResourcesManager.getInstance());
	}
	
	@Test
	public void testLoadedValues() throws IOException {
		assertNotEquals(null, r.getImage("hero"));
	}
	
	@Test
	public void testUnknownAsset() throws IOException {
		assertEquals(null, r.getImage("Unknown"));
		assertEquals(null, r.getImage(""));
	}

}

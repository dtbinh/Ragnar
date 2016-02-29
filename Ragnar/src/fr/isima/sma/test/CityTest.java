package fr.isima.sma.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.isima.sma.world.Citizen;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;

public class CityTest {

	City c;
	
	@Before
	public void before() {
		c = new City();
		c.loadCityFromFile("test/ragnar.txt");
		Humanoid.setCity(c);
	}
	
	@After
	public void after() {
		c = null;
	}
	
	@Test
	public void testLoadCity() {
		c.loadCityFromFile("test/ragnar.txt");
		assertEquals(10, c.getSizeX());
	}
	
	@Test
	public void testLoadNonSquareCity() {
		c.loadCityFromFile("test/TESTragnar.txt");
		assertEquals(10, c.getSizeX());
		assertEquals(22, c.getSizeY());
	}
	
	@Test
	public void testBigCity() {
		c.loadCityFromFile("test/ragnar2000.txt");
		assertEquals(20, c.getSizeX());
		assertEquals(20, c.getSizeY());
	}
	
	@Test
	public void testGoodAgents() {
		c.loadCityFromFile("test/TESTragnar.txt");
		c.loadAgentsFromFile("test/agents.txt");
		Citizen ci = new Citizen("Benoît", "Garçon", 22, 1, 0, 2);
		ci.setUrl("benoit");
		assertEquals(Citizen.class, c.getActiveEntities().getAgent(0).getClass());
		if(Citizen.class==c.getActiveEntities().getAgent(0).getClass()) {
			Citizen co = (Citizen)c.getActiveEntities().getAgent(0);
			assertEquals(ci.getAge(), co.getAge());			
			assertEquals(ci.getLocation().getLocationX(), co.getLocation().getLocationX());
			assertEquals(ci.getLocation().getLocationY(), co.getLocation().getLocationY());			
			assertEquals(ci.getMoney(), co.getMoney());			
			assertEquals(ci.getName(), co.getName());			
			assertEquals(ci.getSurname(), co.getSurname());		
			assertEquals(ci.getSpeed(), co.getSpeed());			
		}
	}
	
	@Test
	public void testBadAgents() {
		c.loadCityFromFile("test/ragnar.txt");
		c.loadAgentsFromFile("test/TESTagents.txt");
		assertEquals(3, c.getActiveEntities().size());
	}

}

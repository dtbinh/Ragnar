package fr.isima.sma.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.isima.sma.gui.CityView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;


public class CityViewPerfTest {

	City c;
	
	@Before
	public void before() {
		c = new City();
		c.loadCityFromFile("ragnar.txt");
		Humanoid.setCity(c);
	}
	
	@After
	public void after() {
		c = null;
	}
	
	@Test
	public void testPerformanceConstructor() {
		long a = System.currentTimeMillis();
		CityView cityView = new CityView(c, Integer.valueOf(Properties.getInstance().getProperty("caseSize")));
		long b = System.currentTimeMillis();
		System.out.println("Temps de chargement de CityView : "+String.valueOf((b-a)/1000.)+"s");
		assertTrue((b-a)/1000 < 10);
		assertTrue((b-a)/1000 < 5);
		assertTrue((b-a)/1000 < 2);
		cityView.setVisible(true);
		cityView = null;
	}

}

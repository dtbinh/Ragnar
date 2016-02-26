package fr.isima.sma.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({PropertiesTest.class, ResourcesManagerTest.class, CityTest.class, VirtualClockTest.class, SimulationKernelTest.class})
public class AllTests {

}

package fr.isima.sma.test;

import org.junit.Assert;
import org.junit.Test;

import fr.isima.sma.simulator.VirtualClock;

public class VirtualClockTest {

	@Test
	public void testSecond() {
		VirtualClock v = new VirtualClock(100);
		long start = System.currentTimeMillis();
		long end = 0;
		for(int i = 0 ; i < 10 ; i++)
			while(!v.ticTac());
		end = System.currentTimeMillis();
		Assert.assertTrue(start*1.01 >= end-1000);
		Assert.assertTrue(start*0.99 <= end-1000);
	}

}

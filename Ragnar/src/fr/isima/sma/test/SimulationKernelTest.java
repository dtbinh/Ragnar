package fr.isima.sma.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;
import org.junit.Test;

import fr.isima.sma.simulator.SimulationKernel;

public class SimulationKernelTest {

	@Test
	public void testRandom() {
		SimulationKernel.setRand(new MersenneTwister(1));
		List<Integer> liste = new ArrayList<Integer>(100);
		for (int i = 0; i < 100; i++) {
			liste.add(SimulationKernel.getRand().nextInt());
		}
		SimulationKernel.setRand(new MersenneTwister(1));
		for (int i = 0; i < 100; i++) {
			assertEquals(liste.get(i).intValue(), SimulationKernel.getRand().nextInt());
		}
	}

}

package fr.isima.sma.simulator;

import java.util.Date;

public class VirtualClock {
	protected long step;
	protected long start;
	protected long last;
	
	
	public VirtualClock(long pStep) {
		start = System.currentTimeMillis();
		last = start;
		step = pStep;
	}
	
	public boolean ticTac() {		// peut-on executer un tour de simulation
		boolean timeToRun = false;
		long newTime = System.currentTimeMillis();
		if(newTime >= last + step) {
			timeToRun = true;
			last = System.currentTimeMillis();
		} else {
			try {
				Thread.sleep(last + step - System.currentTimeMillis());
			} catch (InterruptedException e) {
				System.err.println("Error: VirtualClock didn't wait!");
				e.printStackTrace();
			}
		}
		return timeToRun;
	}

}

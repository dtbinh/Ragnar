package fr.isima.sma.simulator;

public class VirtualClock {
	protected long step;	// Pas entre chaque tour de simulation
	protected long start;	// Date de debut de la simulation
	protected long last;	// Temps du dernier tour de simulation
	
	/**
	 * Constructor with parameters
	 * @param pStep simulation min step (calculations may lengthen it)
	 */
	public VirtualClock(long pStep) {
		start = System.currentTimeMillis();
		last = start;
		step = pStep;
	}
	
	/**
	 * Indicates if we can simulate using the virtual clock
	 * @return true if we can simulate, false if wa can't
	 */
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

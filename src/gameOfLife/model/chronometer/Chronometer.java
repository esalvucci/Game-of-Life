package gameOfLife.model.chronometer;

/**
 * Class that measures elapsed time from a starting moment.
 */
public class Chronometer {

	private boolean running;
	private long startTime;

    /**
     * Default constructor.
     */
	public Chronometer(){
		running = false;
	}

    /**
     * Starts the chronometer.
     */
	public void start(){
		running = true;
		startTime = System.currentTimeMillis();
	}

    /**
     * Stops the cronometer
     */
	public void stop(){
		startTime = getTime();
		running = false;
	}

    /**
     * Get the time elapsed from start to stop moment.
     * @return the time elapsed.
     */
	public long getTime(){
		if (running){
			return 	System.currentTimeMillis() - startTime;
		} else {
			return startTime;
		}
	}
}

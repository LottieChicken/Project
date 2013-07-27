package net.enteranamestudio.project.util;

public class Timer {

	protected int deltaStock;
	protected int duration;
	protected int eventTime;
	
	protected boolean stop = false;
	
	public Timer(int _duration) {
		deltaStock = 0;
		duration = _duration;
	}
	
	
	public Timer(int _duration, int _eventTime) {
		deltaStock = 0;
		duration = _duration;
		eventTime = _eventTime;
	}
	
	
	public void update(int delta) {
		if(!stop) {
			deltaStock += delta;
		
			if(deltaStock >= duration) deltaStock = duration;
		}
	}
	
	
	public void tick(int delta) {
		if (deltaStock < duration)
			deltaStock += delta;
	}
	
	
	public boolean isComplete() {
		return deltaStock >= duration;
	}
	
	
	public boolean isEventComplete() {
		return duration == eventTime;
	}
	
	
	public void setEventTime(int _eventTime) {
		eventTime = _eventTime;
	}
	
	
	public void reset() {
		deltaStock = 0;
	}
	
	
	public void stop(boolean _stop) {
		stop = _stop;
	}
	
	
	public void setDuration(int _duration) {
		duration = _duration;
	}
	
}

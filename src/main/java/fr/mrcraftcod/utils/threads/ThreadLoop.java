package fr.mrcraftcod.utils.threads;

public abstract class ThreadLoop extends Thread
{
	private boolean running = true;

	@Override
	public void run()
	{
		while(!this.isInterrupted() && this.running)
			this.loop();
	}

	public void close()
	{
		this.running = false;
		this.onClosed();
	}

	abstract void onClosed();

	abstract void loop();
}

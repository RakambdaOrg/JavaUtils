package fr.mrcraftcod.utils.threads;

import fr.mrcraftcod.utils.Log;
public abstract class ThreadLoop extends Thread
{
	private boolean running = true;

	@Override
	public void run()
	{
		while(!this.isInterrupted() && this.running)
			try
			{
				this.loop();
			}
			catch(Exception e)
			{
				Log.error("ThreadLoop unhandled exception in loop", e);
				break;
			}
	}

	public void close()
	{
		this.interrupt();
		this.running = false;
		this.onClosed();
	}

	public void onClosed(){};

	public abstract void loop() throws Exception;
}

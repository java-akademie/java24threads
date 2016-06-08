package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class TestKlasse
{
	synchronized public void doWait()
	{
		try
		{
			wait();
			System.out.println("wait von aussen beendet");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}



public class WaitNotify
{
	public static void main(String[] args) throws InterruptedException
	{
		final TestKlasse tc = new TestKlasse();

		Thread th = new Thread()
		{
			@Override
			public void run()
			{
				tc.doWait();
			}
		};

		th.start();

		MyTools.pause();

		th.interrupt();

		synchronized (tc)
		{
			tc.notify();
		}

		th.join();

		System.out.println("programm beendet");
	}
}

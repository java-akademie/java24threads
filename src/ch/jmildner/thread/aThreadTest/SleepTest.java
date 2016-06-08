package ch.jmildner.thread.aThreadTest;


public class SleepTest
{
	void sleepTest()
	{
		Thread th = new Thread()
		{
			@Override
			public void run()
			{
				System.out.println("Thread gestartet!");

				try
				{
					Thread.sleep(30000);
				}
				catch (InterruptedException e)
				{
					System.out.println("Thread interrupted!");
				}

				System.out.println("Thread fertig!");
			}
		};

		th.start();

		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			System.out.println("Thread interrupted!");
		}

		th.interrupt();
	}


}

package ch.jmildner.thread.threads;

import ch.jmb.tools.MyTools;

class ClassWithThread implements Runnable
{
	int tnr = 0;
	int ende = 0;


	ClassWithThread(int i)
	{
		tnr = i;
	}


	@Override
	public void run()
	{
		for (int i = 0; i < 1000000; i++)
		{
			System.out.print(tnr);
			if (ende == 1)
				break;
		}
	}
}



class SubThread extends Thread
{
	int tnr = 0;
	int ende = 0;


	SubThread(int i)
	{
		tnr = i;
	}


	@Override
	public void run()
	{
		for (int i = 0; i < 1000000; i++)
		{
			System.out.print(tnr);
			if (ende == 1)
			{
				break;
			}
		}
	}
}



public class Thread4
{
	public static void main(String[] args)
	{
		SubThread t1 = new SubThread(1);
		t1.start();
		ClassWithThread classWithThread = new ClassWithThread(2);
		Thread t2 = new Thread(classWithThread);
		t2.start();
		Thread t3 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				for (int i = 0; i < 100000; i++)
				{
					System.out.print(3);
				}
			}
		});
		t3.start();
		MyTools.pause();
		t1.ende = 1;
		MyTools.pause();
		classWithThread.ende = 1;
		// Kommunikation mit dem anonymen Thread ist nicht moeglich!
		MyTools.pause();
	}
}

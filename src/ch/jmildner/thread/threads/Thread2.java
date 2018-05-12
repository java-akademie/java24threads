package ch.jmildner.thread.threads;

import ch.jmildner.tools.MyTools;

class MyThread implements Runnable
{
	int tnr = 0;
	int ende = 0;


	MyThread(int i)
	{
		tnr = i;
	}


	@Override
	public void run()
	{
		@SuppressWarnings("unused")
		int w = 50000;
		for (int i = 0; i < 100000; i++)
		{
			System.out.println(tnr);
			if (tnr == 1)
			{
				// MyTools.sleep(w);
			}
			else
			{
				// MyTools.sleep(0);
			}
			w = 0;
		}
	}
}



public class Thread2
{
	public static void main(String[] args)
	{
		MyThread mt1 = new MyThread(1);
		Thread t1 = new Thread(mt1);
		MyThread mt2 = new MyThread(2);
		Thread t2 = new Thread(mt2);
		t1.setPriority(5);
		t2.setPriority(5);
		t1.start();
		t2.start();
		MyTools.pause();
		t1.interrupt();
		MyTools.pause();
	}
}

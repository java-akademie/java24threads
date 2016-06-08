package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class Data
{
	long z = 0;
	long x = 0;


	void add()
	{
		z++;
	}


	void check(long tnr, long i)
	{
		if (z != 0)
		{
			System.out.println("z nicht null " + tnr + " # " + ++x
					+ " " + i);
			z = 0;
		}
	}


	void sub()
	{
		z--;
	}
}



class MyRunnable implements Runnable
{
	Data d = new Data();
	int tnr = 0;
	int ende;


	MyRunnable(int i, Data d)
	{
		this.d = d;
		tnr = i;
	}


	// synchronized
	@Override
	public void run()
	{
		for (long i = 0;; i++)
		{
			synchronized (d)
			{
				// System.out.print(tnr);
				if (tnr == 0)
				{
					try
					{
						Thread.currentThread().wait();
					}
					catch (InterruptedException ie)
					{
						System.out.println("ie " + tnr);
					}
				}
				d.add();
				d.sub();
				d.check(tnr, i);
			}
		}
	}
}



public class Thread5
{
	public static void main(String[] args)
	{
		Data d = new Data();
		MyRunnable mr1 = new MyRunnable(1, d);
		MyRunnable mr2 = new MyRunnable(2, d);
		Thread t1 = new Thread(mr1);
		Thread t2 = new Thread(mr2);
		t1.start();
		t2.start();
		MyTools.pause();
		mr1.ende = 1;
		MyTools.pause();
		mr2.ende = 1;
	}
}

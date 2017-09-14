package ch.jmildner.thread.threads;

import java.util.Date;

import ch.jmb.tools.MyTools;

class CountThread implements Runnable
{
	int ende = 0;


	@Override
	public void run()
	{
		for (int i = 0; i < 1000000; i++)
		{
			System.out.println(i);
			MyTools.sleep(1000);
			if (ende == 1)
			{
				break;
			}
		}
	}
}



class DateThread implements Runnable
{
	int ende = 0;


	@Override
	public void run()
	{
		for (int i = 0; i < 1000000; i++)
		{
			System.out.println(new Date());
			MyTools.sleep(1000);
			if (ende == 1)
			{
				break;
			}
		}
	}
}



public class Thread1
{
	public static void main(String[] args)
	{
		DateThread t1 = new DateThread();
		Thread th1 = new Thread(t1);
		th1.start();
		CountThread t2 = new CountThread();
		Thread th2 = new Thread(t2);
		th2.start();
		MyTools.pause();
		th1.setPriority(6);
		th2.setPriority(4);
		MyTools.pause();
		th1.setPriority(4);
		th2.setPriority(6);
		int i1 = 0, i2 = 0;
		while (true)
		{
			int i = MyTools.getInteger("1,2 oder 9 eingeben");
			switch (i)
			{
				case 1:
					t1.ende = 1;
					break;
				case 2:
					t2.ende = 1;
					break;
				case 9:
					break;
				default:
			}
			if (i == 1)
				i1 = 1;
			if (i == 2)
				i2 = 1;
			if (i1 == 1 && i2 == 1)
			{
				if (i == 9)
				{
					break;
				}
			}
			System.out.println("ergebnis: " + i1 + " " + i2);
			@SuppressWarnings("unused")
			class a extends Thread
			{
				{
					start();
				}


				@Override
				public void run()
				{
					System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
				}
			}
		}
	}
}

package ch.jmildner.thread.threads;

class MyThread3 implements Runnable
{
	int tn;


	MyThread3(int n)
	{
		tn = n;
	}


	@Override
	public void run()
	{
		int i = 0;
		while (true)
		{
			System.out.println("start thread " + tn);
			while (true)
			{
				if ((i++) % 10000000 == 0)
				{
					System.out.println("tn: " + tn);
					Thread.yield();
					// Thread.currentThread().setPriority(9);
				}
			}
		}
	}
}



public class Thread3
{
	public static void main(String[] args)
	{
		MyThread3 mt1 = new MyThread3(1);
		MyThread3 mt2 = new MyThread3(2);
		MyThread3 mt3 = new MyThread3(3);
		Thread t1 = new Thread(mt1);
		Thread t2 = new Thread(mt2);
		Thread t3 = new Thread(mt3);
		t1.start();
		t2.start();
		t3.start();
		t1.setPriority(1);
		t2.setPriority(2);
		t3.setPriority(3);
	}
}

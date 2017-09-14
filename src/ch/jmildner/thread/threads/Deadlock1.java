package ch.jmildner.thread.threads;

import ch.jmb.tools.MyTools;

class Deadlock1 implements Runnable
{
	public static void main(String[] args)
	{
		Object a = "Resource A";
		Object b = "Resource B";
		/**
		 * wenn die Parameter bei einem der Aufrufe vertauscht werden,
		 * entsteht ein Deadlock
		 */
		Thread t1 = new Thread(new Deadlock1(a, b));
		Thread t2 = new Thread(new Deadlock1(b, a));
		t1.start();
		t2.start();
	}
	Object firstRessource;


	Object secondRessource;


	Deadlock1(Object first, Object second)
	{
		this.firstRessource = first;
		this.secondRessource = second;
	}


	@Override
	public void run()
	{
		for (;;)
		{
			System.out.println(Thread.currentThread().getName()
					+ " Looking for lock on " + firstRessource);
			synchronized (firstRessource)
			{
				System.out.println(Thread.currentThread().getName()
						+ " Obtained lock on " + firstRessource);
				System.out.println(Thread.currentThread().getName()
						+ " Looking for lock on " + secondRessource);
				synchronized (secondRessource)
				{
					System.out.println(Thread.currentThread().getName()
							+ " Obtained lock on " + secondRessource);
					System.out.println("-------------------");
					MyTools.sleep(1);
					System.out.println("-------------------");
				}
			}
		}
	}
}

package ch.jmildner.thread.threads;

import ch.jmb.tools.MyTools;

class ThreadusInterruptus1 extends Thread
{
	public static void main(String[] args)
	{
		ThreadusInterruptus1 t1 = new ThreadusInterruptus1(1);
		t1.start();
		ThreadusInterruptus1 t2 = new ThreadusInterruptus1(2);
		t2.start();
		MyTools.pause();
		t1.interrupt();
		MyTools.pause();
		t2.interrupt();
	}


	int x;


	ThreadusInterruptus1(int x)
	{
		this.x = x;
	}


	@Override
	public void run()
	{
		System.out.println("anfang");
		for (int i = 0; i < 100000; i++)
		{
			if (isInterrupted())
			{
				break;
			}
			System.out.println("hurra ich lebe noch " + x);
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				System.out.println("im schlaf ueberrascht");
				interrupt();
			}
		}
		System.out.println("jetzt ist es aus mit mir");
		if (interrupted()) // oder isInterrupted
		{
			System.out.println("wurde abgebrochen");
		}
		else
		{
			System.out.println("normales ende erreicht");
		}
	}
}

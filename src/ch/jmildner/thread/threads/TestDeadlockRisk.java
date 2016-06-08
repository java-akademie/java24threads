package ch.jmildner.thread.threads;

class DeadlockRisk
{
	private static class Resource
	{
		public int value;
	}

	private Resource resourceA = new Resource();
	private Resource resourceB = new Resource();


	int read()
	{
		synchronized (resourceB)
		{
			synchronized (resourceA)
			{
				return resourceA.value + resourceB.value;
			}
		}
	}


	void write(int a, int b)
	{
		synchronized (resourceA)
		{
			synchronized (resourceB)
			{
				resourceA.value = a;
				resourceB.value = b;
			}
		}
	}
}



class DeadlockRiskRead implements Runnable
{
	DeadlockRisk drr;


	DeadlockRiskRead(DeadlockRisk r)
	{
		drr = r;
	}


	@Override
	public void run()
	{
		for (int i = 1; i <= t.MAX; i++)
		{
			int x = drr.read();
			if (i % 100000 == 0)
			{
				System.out.println("gelesen " + x + " " + i);
			}
		}
	}
}



class DeadlockRiskWrite implements Runnable
{
	DeadlockRisk drw;


	DeadlockRiskWrite(DeadlockRisk w)
	{
		drw = w;
	}


	@Override
	public void run()
	{
		for (int i = 1; i <= t.MAX; i++)
		{
			drw.write(i + 1000, i + 2000);
			if (i % 100000 == 0)
			{
				System.out.println("geschrieben " + i);
			}
		}
	}
}



class t
{
	static final int MAX = 1000000000;
}



public class TestDeadlockRisk
{
	public static void main(String[] args)
	{
		DeadlockRisk dr = new DeadlockRisk();
		Runnable rr = new DeadlockRiskRead(dr);
		Runnable rw = new DeadlockRiskWrite(dr);
		Thread tr = new Thread(rr);
		Thread tw = new Thread(rw);
		tr.setName("vesna");
		tw.setName("hans");
		tw.start();
		tr.start();
	}
}

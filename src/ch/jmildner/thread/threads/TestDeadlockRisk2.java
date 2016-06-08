package ch.jmildner.thread.threads;

class DeadlockRiskR
{
	Resource resourceA;
	Resource resourceB;


	DeadlockRiskR(Resource a, Resource b)
	{
		resourceA = a;
		resourceB = b;
	}


	int read()
	{
		synchronized (resourceA)
		{
			synchronized (resourceB)
			{
				return resourceA.value + resourceB.value;
			}
		}
	}
}



class DeadlockRiskRead2 implements Runnable
{
	DeadlockRiskR drr;


	DeadlockRiskRead2(DeadlockRiskR r)
	{
		drr = r;
	}


	@Override
	public void run()
	{
		for (int i = 0; i < t2.MAX; i++)
		{
			int x = drr.read();
			if (i % t2.DIV2 == 0)
			{
				System.out.print(".");
			}
			if (i % t2.DIV1 == 0)
			{
				System.out.println("gelesen " + x + " " + i);
			}
		}
	}
}



class DeadlockRiskW
{
	Resource resourceA;
	Resource resourceB;


	DeadlockRiskW(Resource a, Resource b)
	{
		resourceA = a;
		resourceB = b;
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



class DeadlockRiskWrite2 implements Runnable
{
	DeadlockRiskW drw;


	DeadlockRiskWrite2(DeadlockRiskW w)
	{
		drw = w;
	}


	@Override
	public void run()
	{
		for (int i = 0; i < t2.MAX; i++)
		{
			drw.write(1, 1);
			if (i % t2.DIV2 == 0)
			{
				System.out.print(".");
			}
			if (i % t2.DIV1 == 0)
			{
				System.out.println("geschrieben " + i);
			}
		}
	}
}



class Resource
{
	public int value;
}



class t2
{
	static final int MAX = 1000 * 1000 * 1000;
	static final int DIV1 = 1000 * 1000 * 100;
	static final int DIV2 = 1000 * 1000 * 2;
}



public class TestDeadlockRisk2
{
	public static void main(String[] args)
	{
		Resource resourceA = new Resource();
		Resource resourceB = new Resource();
		DeadlockRiskR drr = new DeadlockRiskR(resourceA, resourceB);
		DeadlockRiskW drw = new DeadlockRiskW(resourceA, resourceB);
		Runnable rr = new DeadlockRiskRead2(drr);
		Runnable rw = new DeadlockRiskWrite2(drw);
		Thread tr = new Thread(rr);
		Thread tw = new Thread(rw);
		tr.setName("vesna");
		tw.setName("hans");
		tw.start();
		tr.start();
	}
}

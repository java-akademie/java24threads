package ch.jmildner.threadpool;

public class ThreadPool
{
	//private PoolRunner[] pool;
	private PoolRunner[] free;
	private int maxFree;



	public void dump()
	{
		final int MAX_THREADS = free.length;

		for (int i = 0; i < MAX_THREADS; i++)
		{
//				System.out.println(
//						pool[i].getName() + " " + pool[i].getState());
				System.out.println(
						free[i].getName() + " " + free[i].getState());
		}
	}


	public ThreadPool(final int MAX_THREADS, final String SERVICE_NAME)
	{
//		pool = new PoolRunner[MAX_THREADS];
		free = new PoolRunner[MAX_THREADS];

		for (int i = 0; i < MAX_THREADS; i++)
		{
			PoolRunner t = new PoolRunner();
			t.setName("PoolRunnder." + (i + 1) + " - " + SERVICE_NAME);
//			pool[i] = t;
			free[i] = t;
			t.setDaemon(true);
			t.start();
		}

		maxFree = MAX_THREADS - 1;
	}


	synchronized public void execute(Runnable action)
	{
		try
		{
			if (maxFree < 0)
			{
				System.out.println("vor wait in execute" + "  "
						+ Thread.currentThread().getName());
				wait();
			}
			System.out.println(
					"maxFree vor  execute in execute() " + maxFree);
			free[maxFree--].execute(action);
			System.out.println(
					"maxFree nach execute in execute() " + maxFree);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	synchronized public void noteCompletion(PoolRunner poolRunner)
	{
		System.out.println("vor free: " + maxFree + "  "
				+ Thread.currentThread().getName());
		free[++maxFree] = poolRunner;
		System.out.println("vor notify: " + +maxFree);
		notify();
	}

	private class PoolRunner extends Thread
	{
		Runnable action;


		public void execute(Runnable action)
		{
			synchronized (this)
			{
				this.action = action;
				notify();
			}
		}


		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					synchronized (this)
					{
						wait();
					}

					action.run();
					action = null;
					ThreadPool.this.noteCompletion(this);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

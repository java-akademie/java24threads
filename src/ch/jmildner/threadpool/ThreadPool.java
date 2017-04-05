package ch.jmildner.threadpool;

public class ThreadPool
{
	private PoolRunner[] pool;
	private PoolRunner[] free;
	private int maxFree;


	public ThreadPool(final int MAX_THREADS)
	{
		pool = new PoolRunner[MAX_THREADS];
		free = new PoolRunner[MAX_THREADS];

		for (int i = 0; i < MAX_THREADS; i++)
		{
			PoolRunner t = new PoolRunner();
			t.setName("poolRunner "+ i);
			pool[i] = t;
			free[i] = t;
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
				wait();
			}
			free[maxFree--].execute(action);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	synchronized public void noteCompletion(PoolRunner poolRunner)
	{
		free[++maxFree] = poolRunner;
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

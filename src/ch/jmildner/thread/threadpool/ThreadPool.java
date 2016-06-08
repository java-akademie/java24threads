package ch.jmildner.thread.threadpool;

class ThreadPool
{
	private class PoolRunner extends Thread
	{
		Runnable action;


		public void execute(Runnable action)
		{
			System.out.println("action:" + action);
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
				catch (InterruptedException ex)
				{
					// should not happen
					ex.printStackTrace(System.err);
				}
			}
		}
	}

	private PoolRunner pool[];
	private PoolRunner free[];
	private int maxFree;


	ThreadPool(int threads)
	{
		pool = new PoolRunner[threads];
		free = new PoolRunner[threads];
		for (int i = 0; i < threads; i++)
		{
			PoolRunner t = new PoolRunner();
			pool[i] = t;
			free[i] = t;
			t.start();
		}
		maxFree = threads - 1;
	}


	synchronized public void execute(Runnable action)
	{
		try
		{
			if (maxFree < 0)
				wait();
			free[maxFree--].execute(action);
			System.out.println(free[maxFree + 1]);
		}
		catch (InterruptedException ex)
		{
			// should not happen
			ex.printStackTrace(System.err);
		}
	}


	synchronized private void noteCompletion(PoolRunner pr)
	{
		free[++maxFree] = pr;
		notify();
	}
}

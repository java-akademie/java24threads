package ch.jmildner.thread.threads;

class Account1
{
	private int nBalance;


	public Account1()
	{
		nBalance = 0;
	}


	// synchronized
	public void deposit(int nAmount)
	{
		nBalance = nBalance + nAmount;
		// Thread.yield();
	}


	public int getBalance()
	{
		return nBalance;
	}


	// synchronized
	public void withdraw(int nAmount)
	{
		nBalance = nBalance - nAmount;
		// Thread.yield();
	}
}



public class SyncAccountDemo1 implements Runnable
{
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("Programm gestartet");

		SyncAccountDemo1 a1 = new SyncAccountDemo1();

		Thread t0 = new Thread(a1);
		System.out.println("starting " + t0);
		t0.start();

		Thread.yield();

		Thread t1 = new Thread(a1);
		System.out.println("starting " + t1);
		t1.start();

		t0.join();
		t1.join();

		System.out.println("Programm beendet");
	}


	private Account1 myAccount = new Account1();


	@Override
	public void run()
	{
		int beginBalance, endBalance;
		beginBalance = 0;
		// beginBalance = myAccount.getBalance();
		for (int i = 0; i < 100; i++)
		{
			// synchronized (myAccount)
			{
				// System.out.println("d:"+i+" " +
				// Thread.currentThread());
				myAccount.deposit(200);
				// System.out.println("w:"+i+" " +
				// Thread.currentThread());
				myAccount.withdraw(200);
				endBalance = myAccount.getBalance();
				if (endBalance != beginBalance)
				{
					System.out.println(Thread.currentThread()
							+ " iteration # " + i + " beginBalance = "
							+ beginBalance + " endBalance = "
							+ endBalance);
					beginBalance = myAccount.getBalance();
				}
			}
		}
	}
}

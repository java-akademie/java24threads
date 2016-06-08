package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class Account
{
	int balance = 100;


	int getBalance()
	{
		return balance;
	}


	void withdraw(int amount)
	{
		balance = balance - amount;
	}
}



class AccountDanger implements Runnable
{
	static String threadName()
	{
		return Thread.currentThread().getName();
	}


	Account account = new Account();


	void makeWithdrawal(int amount)
	{
		if (amount <= account.getBalance())
		{
			System.out.println(threadName() + " is going to withdraw");
			MyTools.sleep(500);
			account.withdraw(amount);
			System.out.println(threadName()
					+ " completes the withdrawal");
		}
		else
		{
			System.out.println(threadName() + " not enough account: "
					+ account.getBalance());
		}
	}


	@Override
	public void run()
	{
		for (int i = 1; i <= 7; i++)
		{
			synchronized (account)
			{
				makeWithdrawal(10);
			}
			if (account.getBalance() < 0)
			{
				System.out.println(threadName()
						+ " account is overdrawn: "
						+ account.getBalance());
			}
		}
	}
}



public class TestAccountDanger
{
	public static void main(String[] args) throws InterruptedException
	{
		Runnable r = new AccountDanger();
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		t1.setName("     heidi");
		t2.setName("urs                      ");
		t2.start();
		t1.start();
		t1.join();
		t2.join();
		System.out.println("programm beendet");
	}
}

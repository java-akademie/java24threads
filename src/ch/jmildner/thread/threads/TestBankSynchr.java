package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class Bank
{
	long buchungsNummer = 0;
	long soll = 0;
	long haben = 0;
	long saldo = 0;
	long check = 0;
	long fehler = 0;


	// synchronized
	void buchung(long tNr)
	{
		buchungsNummer++;
		long buchung = MyTools.getRandom(0, 10000);
		soll += buchung;
		haben += buchung;
		checkSaldo();
	}


	// synchronized
	void checkSaldo()
	{
		check++;
		saldo = soll - haben;
		if (saldo != 0)
		{
			fehler++;
			System.out.println(buchungsNummer + " saldo nicht nullx: "
					+ saldo + "  s=" + soll + " h=" + haben + "  "
					+ fehler);
			soll = haben = 0;
		}
		/*
		 * if (check % 100000 == 0) { System.out.println( buchungsNummer
		 * + " saldo nicht null: " + saldo + " s=" + soll + "
		 * h=" + haben + " " + check); }
		 */
	}
}



class BankThread implements Runnable
{
	long tNr = 0;
	long ende = 0;
	Bank b;


	BankThread(Bank b, long n)
	{
		this.b = b;
		this.tNr = n;
	}


	@Override
	public void run()
	{
		// synchronized (b)
		{
			for (long i = 0; i < 100000000; i++)
			{
				b.buchung(tNr);
				if (ende == 1)
					break;
			}
			System.out.println("Thread beendet: " + tNr);
		}
	}
}



class CheckBuchungenDaemon extends Thread
{
	Bank b;


	CheckBuchungenDaemon(Bank b)
	{
		this.b = b;
		setDaemon(true);
		setPriority(1);
	}


	@Override
	public void run()
	{
		long z = 0;
		while (true)
		{
			if (z++ % 100 == 0)
				System.out.println(b.buchungsNummer + " " + z);
			b.checkSaldo();
			// MyTools.sleep(10);
		}
	}
}



public class TestBankSynchr
{
	public static void main(String[] args)
	{
		Bank b = new Bank();
		BankThread t1 = new BankThread(b, 1);
		Thread th1 = new Thread(t1);
		th1.start();
		BankThread t2 = new BankThread(b, 2);
		Thread th2 = new Thread(t2);
		th2.start();
		 new CheckBuchungenDaemon(b).start();
		MyTools.pause();
		t1.ende = 1;
		MyTools.pause();
		t2.ende = 1;
	}
}

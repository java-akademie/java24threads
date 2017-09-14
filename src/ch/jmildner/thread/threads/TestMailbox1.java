package ch.jmildner.thread.threads;

import ch.jmb.tools.MyTools;

class Consumer implements Runnable
{
	boolean weiter = true;
	Mailbox mailbox;


	Consumer(Mailbox mb)
	{
		mailbox = mb;
	}


	@Override
	public void run()
	{
		while (weiter)
		{
			/**
			 * hier ist es egal ob man interrupted oder isInterrupted
			 * abf�gt Grund: nach der Abfrage ist der Thread sowieso
			 * beendet
			 */
			// if (Thread.currentThread().isInterrupted())
			if (Thread.interrupted())
			{
				weiter = false;
			}
			else
			{
				String m;
				m = mailbox.lesenMessage();
				System.out.println(m);
				MyTools.sleep(1000);
			}
		}
		System.out.println("lesen beendet ...");
	}
}



class Mailbox
{
	private boolean messageVorhanden;
	private String message;


	synchronized public String lesenMessage()
	{
		while (!messageVorhanden)
		{
			/**
			 * hier darf interrupted nicht abgefragt werden Grund:
			 * anschliessend ist das interrupted-flag gel�scht und bei
			 * der Abragae in der run-methode nicht mehr gesetzt
			 */
			// if (Thread.interrupted())
			if (Thread.currentThread().isInterrupted())
			{
				return null;
			}
			try
			{
				wait();
			}
			catch (InterruptedException ie)
			{
				System.out
						.println("lesen - von aussen zur Aufgabe gebeten");
				Thread.currentThread().interrupt();
			}
		}
		messageVorhanden = false;
		String m = message;
		message = null;
		notifyAll();
		return m;
	}


	synchronized public void schreibenMessage(String message)
	{
		while (messageVorhanden)
		{
			/**
			 * hier darf interrupted nicht abgefrage werden! Grund:
			 * anschliessend ist das interrupted-flag gel�scht und bei
			 * der Abfrage in der run-methode nicht mehr gesetzt
			 */
			// if (Thread.interrupted())
			if (Thread.currentThread().isInterrupted())
			{
				return;
			}
			try
			{
				System.out.println("wait");
				wait();
			}
			catch (InterruptedException ie)
			{
				System.out
						.println("schreiben - von aussen zur Aufgabe gebeten");
				Thread.currentThread().interrupt();
			}
		}
		this.message = message;
		messageVorhanden = true;
		notify();
	}
}



class Producer implements Runnable
{
	Mailbox mailbox;


	Producer(Mailbox mb)
	{
		mailbox = mb;
	}


	@Override
	public void run()
	{
		boolean weiter = true;
		int i = 1;
		while (weiter)
		{
			/**
			 * hier ist es egal ob man interrupted oder isInterrupted
			 * abfraegt Grund: nach der Abfrage ist der Thread sowieso
			 * beendet
			 */
			// if (Thread.currentThread().isInterrupted())
			if (Thread.interrupted())
			{
				weiter = false;
			}
			else
			{
				mailbox.schreibenMessage("message nummer " + i++);
				MyTools.sleep(500);
			}
		}
		System.out.println("schreiben beendet ...");
	}
}



class TestMailbox1
{
	public static void main(String[] args)
	{
		Mailbox mb = new Mailbox();
		Consumer c = new Consumer(mb);
		Producer p = new Producer(mb);
		Thread cThread = new Thread(c);
		Thread pThread = new Thread(p);
		cThread.start();
		pThread.start();
		MyTools.pause();
		cThread.interrupt();
		pThread.interrupt();
	}
}

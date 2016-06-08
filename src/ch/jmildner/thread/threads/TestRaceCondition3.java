package ch.jmildner.thread.threads;

import ch.java_akademie.tools.MyTools;

class Pipe3
{
	private String[] array = new String[8];
	private int index = 0;


	synchronized public String read(String tName)
	{
		String value;
		while (index == 0)
		{
			System.out.println("ReaderThread '" + tName
					+ "' muss warten");
			try
			{
				this.wait();
				System.out.println("ReaderThread '" + tName
						+ "' aufgewacht");
			}
			catch (InterruptedException e)
			{
				System.out.println("ReaderThread '" + tName
						+ "' interrupt");
				MyTools.pause();
			}
		}
		value = array[0];
		index--;
		System.out.print(tName + "   empfangen: " + value + ": ");
		show();
		for (int i = 0; i < index; i++)
		{
			array[i] = array[i + 1];
		}
		array[index] = "";
		if (index == array.length - 1)
		{
			System.out.println("ReaderThread '" + tName
					+ "' notify all");
			this.notifyAll();
			// es kann wieder geschrieben werden
		}
		return value;
	}


	void show()
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + "/");
		}
		System.out.println("");
	}


	synchronized public void write(String tName, String i)
	{
		while (index == array.length)
		{
			System.out.println("WriterThread '" + tName
					+ "' muss warten");
			try
			{
				this.wait();
				System.out.println("WriterThread '" + tName
						+ "' aufgewacht");
			}
			catch (InterruptedException e)
			{
				System.out.println("WriterThread '" + tName
						+ "' interrupt");
				// MyTools.halt();
			}
		}
		array[index] = i;
		index++;
		System.out.print(tName + " geschrieben: " + i + ": ");
		show();
		if (index == 1)
		{
			System.out.println("WriterThread '" + tName
					+ "' notify all");
			this.notifyAll(); // es kann wieder gelesen werden
		}
	}
}



class Reader3 extends Thread
{
	private String tName;
	private Pipe3 pipe;


	Reader3(Pipe3 p, String n)
	{
		pipe = p;
		tName = n;
	}


	@Override
	public void run()
	{
		String empfang;
		empfang = pipe.read(tName);
		while (empfang != "")
		{
			empfang = pipe.read(tName);
		}
	}
}



public class TestRaceCondition3
{
	public static void main(String[] args)
	{
		Pipe3 pipe = new Pipe3();
		Writer3 writerThread1 = new Writer3(pipe, "a");
		Writer3 writerThread2 = new Writer3(pipe, "b");
		Writer3 writerThread3 = new Writer3(pipe, "c");
		Writer3 writerThread4 = new Writer3(pipe, "d");
		Writer3 writerThread5 = new Writer3(pipe, "e");
		Reader3 readerThread1 = new Reader3(pipe, "a");
		Reader3 readerThread2 = new Reader3(pipe, "b");
		Reader3 readerThread3 = new Reader3(pipe, "c");
		Reader3 readerThread4 = new Reader3(pipe, "d");
		Reader3 readerThread5 = new Reader3(pipe, "e");
		// readerThread1.setPriority(10);
		writerThread1.start();
		writerThread2.start();
		writerThread3.start();
		writerThread4.start();
		writerThread5.start();
		readerThread1.start();
		readerThread2.start();
		readerThread3.start();
		readerThread4.start();
		readerThread5.start();
		/*
		 * for (int i=0; i <1000000; i++) { writerThread1.interrupt();
		 * readerThread1.interrupt(); writerThread2.interrupt();
		 * readerThread2.interrupt(); writerThread3.interrupt();
		 * readerThread3.interrupt(); writerThread4.interrupt();
		 * readerThread4.interrupt(); writerThread5.interrupt();
		 * readerThread5.interrupt(); }
		 */
		MyTools.pause();
		MyTools.pause();
	}
}



class Writer3 extends Thread
{
	private String tName;
	private Pipe3 pipe;


	Writer3(Pipe3 p, String n)
	{
		pipe = p;
		tName = n;
	}


	@Override
	public void run()
	{
		for (int i = 1; i < 1000; i++)
		{
			pipe.write(tName, tName + i);
		}
		pipe.write(tName, "");
	}
}

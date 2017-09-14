package ch.jmildner.threadpool;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import ch.jmb.tools.NetTools;

public class DayTimeService implements Runnable
{
	Socket socket;


	DayTimeService(Socket s)
	{
		socket = s;
	}


	@Override
	public void run()
	{
		PrintWriter pw;

		try
		{
			pw = NetTools.getPrintWriter(socket);
			System.out.println(Thread.currentThread().getName());
			pw.println(new Date().toString());
			pw.flush();
			pw.close();
			socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

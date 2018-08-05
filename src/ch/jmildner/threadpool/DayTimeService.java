package ch.jmildner.threadpool;

import ch.jmb.tools.NetTools;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;


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

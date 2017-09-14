package ch.jmildner.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

import ch.jmb.tools.MyTools;
import ch.jmb.tools.NetTools;

public class DayTimeTest
{
	public static void main(String[] args) throws Exception
	{
		// timeServer_time_nist_gov();
		timeServer_localhost_zeitmessung();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		// timeServer_localhost();
		 timeServer_localhost_beenden();

	}


	public static void timeServer_time_nist_gov() throws Exception
	{

		Socket socket = new Socket("time.nist.gov", 13);
		InputStream is = socket.getInputStream();

		int len;
		byte[] b = new byte[1000];

		while ((len = is.read(b)) != -1)
		{
			System.out.write(b, 0, len);
		}

		is.close();
		socket.close();
	}


	public static void timeServer_localhost() throws Exception
	{
		Socket socket = new Socket("localhost", 1300);

		BufferedReader br = new BufferedReader(
				NetTools.getBufferedReader(socket));

		String s = br.readLine();
		System.out.println("localhost: " + s);

		socket.close();
	}



	private static void timeServer_localhost_zeitmessung()
			throws Exception
	{
		final int AUFRUFE = 500;
		
		final long START = System.currentTimeMillis();
		
		for (int i = 1; i <= AUFRUFE; i++)
		{
			if (i % 10 == 0)
				MyTools.sleep(1);
			Socket socket = new Socket("localhost", 1300);

			socket.close();
		}
		
		final long STOPP = System.currentTimeMillis();
		
		System.out.printf("Dauer fuer %d Aufrufe  %d Millisekunden%n",
				AUFRUFE, (STOPP - START));
	}


	public static void timeServer_localhost_beenden() throws Exception
	{
		System.out.println("start DateTimeServer beenden aufrufen");

		try
		{
			Socket socket = new Socket("localhost", 1300, null, 1301);
			socket.close();
		}
		catch (ConnectException e)
		{
			System.out.println("Keine Server aktiv!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("stopp DateTimeServer beenden aufrufen");
	}
}

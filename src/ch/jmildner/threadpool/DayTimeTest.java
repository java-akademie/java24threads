package ch.jmildner.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

import ch.java_akademie.tools.MyTools;
import ch.java_akademie.tools.NetTools;

public class DayTimeTest
{
	public static void main(String[] args) throws Exception
	{
		// timeServer_time_nist_gov();
		timeServer_localhost_zeitmessung();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		timeServer_localhost();
//		 timeServer_localhost_beenden();

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
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 50000; i++) // 15000 dauert 4.5 sek.
		{
			if(i%200==0)MyTools.sleep(20);
			if(i%10000==0)MyTools.sleep(5000);
			Socket socket = new Socket("localhost", 1300);

			socket.close();
		}
		long stopp = System.currentTimeMillis();
		System.out.println((stopp - start) + " Millisecunden!");
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

package ch.jmildner.thread.threadpool;

import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
	public static void main(String[] arg)
	{
		String docRoot = ".";
		ThreadPool threadPool = null;
		boolean pooling = true;

		if (arg.length > 0) // htdocs
		{
			docRoot = arg[0];
		}

		if (arg.length > 1) // groesse des threadPools
		{
			threadPool = new ThreadPool(Integer.parseInt(arg[1]));
		}
		else
		{
			threadPool = new ThreadPool(10);
		}

		try
		{
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8081);
			RequestHandler requestHandler = null;

			System.out.println("http-Server gestartet");

			while (true)
			{
				Socket socket = serverSocket.accept();
				requestHandler = new RequestHandler(socket, docRoot);

				if (pooling)
				{
					threadPool.execute(requestHandler);
				}
				else
				{
					Thread handlerThread = new Thread(requestHandler);
					handlerThread.start();
				}
			}
		}
		catch (java.io.IOException ex)
		{
			System.err.println(ex);
		}
	}
}

package ch.jmildner.thread.threadpool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

class RequestHandler implements Runnable
{

	static private final int BUFLEN = 1024;
	static private final int OK = 200;
	static private final int BADREQUEST = 400;
	static private final int NOTFOUND = 404;
	static private final String notFoundString = "Not found";
	private Socket connection;
	private String documentRoot;
	private String request;
	private String urn;
	private int result = 0;
	private String text;


	RequestHandler(Socket connection, String documentRoot)
	{
		this.connection = connection;
		this.documentRoot = documentRoot;
	}


	private void parseRequest(char cbuf[])
	{
		String reqString = new String(cbuf);

		StringTokenizer tokenizer = new StringTokenizer(reqString);

		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();

			System.out.println("111: " + token);

			if (token.equals("GET"))
			{
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				request = "GET";

				if (!tokenizer.hasMoreTokens())
				{
					result = BADREQUEST;
				}
				else
				{
					urn = tokenizer.nextToken();

					System.out.println("222: " + urn);

					if (urn.equals("/"))
					{
						urn = "index.html";
					}

					result = OK;

					return; // we have all we want
				}
			}
		}

		result = BADREQUEST;
	}


	@Override
	public void run()
	{
		try
		{
			InputStream in = connection.getInputStream();
			InputStreamReader ir = new InputStreamReader(in);

			char cbuf[] = new char[BUFLEN];

			ir.read(cbuf, 0, BUFLEN);

			parseRequest(cbuf);

			System.out
					.println("Connection from "
							+ connection.getInetAddress() + " Result: "
							+ result + ", Request: " + request
							+ ", URN " + urn);

			if (result == OK)
			{
				try
				{
					File file = new File(documentRoot, urn);
					FileInputStream fi = new FileInputStream(file);

					int len = (int) file.length();

					byte[] textBuf = new byte[len];

					int off = 0;

					while (off < len)
					{
						off += fi.read(textBuf, off, len - off);
					}

					text = new String(textBuf);
					fi.close();
				}
				catch (FileNotFoundException ex)
				{
					result = NOTFOUND;
					text = notFoundString;
				}
			}

			System.out.println("Request: " + cbuf.toString());
			// OutputStream out = connection.getOutputStream();

			OutputStreamWriter sw = new OutputStreamWriter(
					connection.getOutputStream());
			StringBuffer resultBuf = new StringBuffer();

			resultBuf.append("HTTP/1.1");
			resultBuf.append(" " + result + " OK\r\n"); // need to add
			// result texts
			resultBuf.append("Content-Type: text/html\r\n");
			resultBuf.append("Content-Length: " + text.length()
					+ "\r\n\r\n");
			resultBuf.append(text); // ("Hallo Welt\r\n");
			resultBuf.append("\r\n");
			// System.out.println (resultBuf);
			sw.write(resultBuf.toString(), 0, resultBuf.length());
			sw.flush();
		}
		catch (java.io.IOException ex)
		{
			System.err.println(ex);
		}
	}
}

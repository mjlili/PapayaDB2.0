package fr.umlv.papayadb.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JavaHttpsExample {
	public static void main(String[] args) throws Exception {
		String httpsURL = "https://www.google.com/";
		URL myurl = new URL(httpsURL);
		HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
		InputStream ins = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(ins);
		BufferedReader in = new BufferedReader(isr);

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		}

		in.close();
	}
}

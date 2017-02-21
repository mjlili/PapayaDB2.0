package fr.umlv.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

	protected HttpURLConnection urlconnection;

	public void connect(URL url) throws Exception {
		try {
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setDoInput(true);
			urlconnection.setDoOutput(true);
			urlconnection.setRequestMethod("GET");
			urlconnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlconnection.connect();
		} catch (Exception e) {
			throw new Exception("Connection failed");
		}
	}

	public void disconnect() {
		urlconnection.disconnect();
	}

	public void displayResponse() throws Exception {
		String line;

		try {
			BufferedReader s = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
			line = s.readLine();
			while (line != null) {
				System.out.println(line);
				line = s.readLine();
			}
			s.close();
		} catch (Exception e) {
			throw new Exception("Unable to read input stream");
		}
	}

	public static void main(String argv[]) {

		try {
			Client c = new Client();
			c.connect(new URL("http://localhost/8080"));
			c.displayResponse();
			c.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

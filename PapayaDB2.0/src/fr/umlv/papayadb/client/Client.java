package fr.umlv.papayadb.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Client {

	public void insertJson(ObjectNode node) throws Exception {

		String url = "http://localhost:8080/add";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(node.toString());

		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());

	}

	public void findById(ObjectNode node) throws Exception {

		String url = "http://localhost:8080/getbyid";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(node.toString());

		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());

	}

	public void deleteDocument(ObjectNode node) throws Exception {
		String url = "http://localhost:8080/delete";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("DELETE");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoOutput(true);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(node.toString());

		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println(response.toString());
	}

	static List<ObjectNode> parseFile(String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<ObjectNode> documents = new LinkedList<ObjectNode>();

		try {
			JsonParser jsonParser = new JsonFactory().createParser(new File(file));
			MappingIterator<ObjectNode> jsonObject = mapper.readValues(jsonParser, ObjectNode.class);
			while (jsonObject.hasNext()) {
				ObjectNode node = jsonObject.next();
				node.putNull("_id");
				documents.add(node);
				/*
				 * JsonParser monJsonParser = node.traverse();
				 * monJsonParser.nextToken(); jsonParse(mapper, monJsonParser);
				 * System.out.println("***************");
				 */
			}
		} catch (

		JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return documents;
	}

	public static void main(String argv[]) {

		try {
			Client c = new Client();
			List<ObjectNode> documents = new LinkedList<ObjectNode>();
			documents = parseFile("person.json");
			for (ObjectNode objectNode : documents) {
				c.insertJson(objectNode);
			}
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode obj = mapper.createObjectNode();
			obj.put("_id", "0_99");
			c.findById(obj);
			// c.deleteDocument(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

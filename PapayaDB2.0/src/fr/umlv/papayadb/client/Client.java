package fr.umlv.papayadb.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.vertx.core.json.JsonObject;

public class Client {

	protected HttpURLConnection urlconnection;


	public void connect(String url) throws Exception {
		URL serverUrl = new URL(url);
		try {
			urlconnection = (HttpURLConnection) serverUrl.openConnection();
			urlconnection.setDoInput(true);
			urlconnection.setDoOutput(true);
			urlconnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlconnection.connect();
		} catch (Exception e) {
			e.printStackTrace();
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
	static void insertJson(ObjectNode node) throws Exception {
		
		String url = "http://localhost:8080/add";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoOutput(true);
		
		
		OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
		wr.write(node.toString());
		//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeByte(node.toString());
		wr.flush();
		wr.close();


		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	static void parseFile(String file) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonParser jsonParser = new JsonFactory().createParser(new File(file));
			MappingIterator<ObjectNode> jsonObject = mapper.readValues(jsonParser, ObjectNode.class);
			while (jsonObject.hasNext()) {
				ObjectNode node = jsonObject.next();
				node.putNull("_id");
				
				insertJson(node);
				
				/*JsonParser monJsonParser = node.traverse();
				monJsonParser.nextToken();
				jsonParse(mapper, monJsonParser);
				System.out.println("***************");
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
	}

	public static void jsonParse(ObjectMapper mapper, JsonParser monJsonParser)
			throws IOException, JsonParseException, JsonProcessingException {
		while (monJsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
			if (monJsonParser.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
				String fieldName = monJsonParser.getCurrentName();
				monJsonParser.nextToken();
				if (monJsonParser.getCurrentToken().equals(JsonToken.START_ARRAY)) {
					JsonNode array = Objects.requireNonNull(mapper.readTree(monJsonParser));
					System.out.println(fieldName + " : " + array);
				} else if (monJsonParser.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					System.out.println(fieldName + " : {");
					jsonParse(mapper, monJsonParser);
					System.out.println("} ");
				} else {
					String fieldValue = monJsonParser.getText();
					System.out.println(fieldName + " : " + fieldValue);
				}
			}
			monJsonParser.nextToken();
		}
	}
	public static void main(String argv[]) {

		try {
			Client c = new Client();
			c.connect("http://localhost:8080");
			c.displayResponse();
			parseFile("person.json");
			c.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

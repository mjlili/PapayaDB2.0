package fr.umlv.client;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

public class JacksonMapper {

	public static void main(String args[]) {

		/*
		 * System.out.println("saisir le chemin du fichier"); Scanner sc = new
		 * Scanner(System.in); String path = sc.nextLine();
		 * System.out.println("saisir le nom de la bd"); String collection =
		 * sc.nextLine();
		 */
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			/*FileInputStream data = new FileInputStream(new File("jsonTest.txt"));
			toJson(data);
			*/
			JsonParser jsonParser = new JsonFactory().createParser(new File("employee.txt"));
			MappingIterator<JsonNode> json = mapper.readValues(jsonParser, JsonNode.class);
			while(json.hasNext()) {
				JsonNode node = json.next();
				JsonParser monJsonParser = node.traverse();
				monJsonParser.nextToken();
				jsonParse(mapper, monJsonParser);
				System.out.println("***************");
			}
			//JsonNode jsonNode = mapper.readValue(new File("jsonTest.txt"), JsonNode.class);
			// jsonParse(mapper, jsonParser);
			

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
	/*
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * try {
	 * 
	 * //"Raw" Data Binding Example Map<String,Object> result =
	 * mapper.readValue(new File("jsonTest.txt"), Map.class);
	 * 
	 * System.out.println(result);
	 * 
	 * /*for (Object jsonNode : result) { /*Iterator<String> fields =
	 * jsonNode.fieldNames(); ObjectNode objet = mapper.createObjectNode();
	 * objet.putNull("_id"); String field = ""; while (fields.hasNext()) {
	 * 
	 * field = fields.next(); //System.out.println(field);
	 * //System.out.println(jsonNode.path(field));
	 * 
	 * String value = jsonNode.path(field).; objet.put(field, value);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // write data on json file //mapper.writeValue(new File("jsonTest.json"),
	 * result);
	 * 
	 * } catch (JsonGenerationException e) { e.printStackTrace(); } catch
	 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
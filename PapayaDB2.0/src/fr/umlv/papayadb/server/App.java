package fr.umlv.papayadb.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class App {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			byte[] data = Files.readAllBytes(Paths.get("employee.json"));
			List<JsonNode> jsonNodes = mapper.readValue(data, new TypeReference<List<JsonNode>>() {
			});
			System.out.println(jsonNodes.size());
			JsonNode jsonNode = mapper.readTree(new File("employee.json"));
			JsonParser monJsonParser = jsonNode.traverse();
			ObjectNode objectNode = mapper.createObjectNode();
			objectNode.putNull("_id");
			Iterator<String> fields = jsonNode.fieldNames();
			String fieldName;
			while (fields.hasNext()) {
				fieldName = fields.next();
				objectNode.put(fieldName, jsonNode.path(fieldName).asText());
			}
			System.out.println(objectNode);
			monJsonParser.nextToken();
			// Méthode de parcour d'un document json (txt, json ...)
			jsonParse(mapper, monJsonParser);
			System.out.println("***************");

			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			// POJO to JSON S�rialisation
			mapper.writeValue(new File("article.json"), createArticle());
			System.out.println("json created!");

			// JSON to POJO D�s�rialisation
			Article article = mapper.readValue(new File("article.json"), Article.class);
			System.out.println(article);

			// Raw" Data Binding Example
			@SuppressWarnings("unchecked")
			Map<String, Object> articleMap = mapper.readValue(new File("article.json"), Map.class);
			System.out.println(articleMap);

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
					// array.forEach(jn -> System.out.println("Test " +
					// jn.asText()));
					System.out.println(fieldName + " = " + array);
				} else if (monJsonParser.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					System.out.println(fieldName + " : {");
					jsonParse(mapper, monJsonParser);
					System.out.println("} ");
				} else {
					String fieldValue = monJsonParser.getText();
					System.out.println(fieldName + " = " + fieldValue);
				}
			}
			monJsonParser.nextToken();
		}
	}

	private static Article createArticle() {
		Article article = new Article();
		article.setTitle("Jackson - Java to JSON & JSON to Java");
		article.setUrl("http://hmkcode.com/jackson-java-json");
		article.addCategory("Java");
		article.addTag("Java");
		article.addTag("Jackson");
		article.addTag("JSON");
		return article;
	}
}

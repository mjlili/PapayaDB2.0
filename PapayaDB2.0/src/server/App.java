package server;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class App {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(new File("employee.json"));
			JsonParser monJsonParser = jsonNode.traverse();
			monJsonParser.nextToken();
			jsonParse(mapper, monJsonParser);
			System.out.println("***************");

			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			// POJO to JSON Sérialisation
			mapper.writeValue(new File("article.json"), createArticle());
			System.out.println("json created!");

			// JSON to POJO Désérialisation
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

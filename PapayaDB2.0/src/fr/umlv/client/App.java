package fr.umlv.client;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			// Data binding Collection<E>
			List<Article> articles = new LinkedList<Article>();

			articles.add(createArticle());
			articles.add(createArticle());

			mapper.writeValue(new File("articles.json"), articles);

			// ( 1 ) Collection<Map>
			List result = mapper.readValue(new File("articles.json"), List.class);
			System.out.println("Each Article is of type: " + result.get(0).getClass());
			System.out.println(result);

			// ( 2 ) Collection<Artilce>
			result = mapper.readValue(new File("articles.json"), new TypeReference<List<Article>>() {
			});
			System.out.println("Each Article is of type: " + result.get(0).getClass());
			System.out.println(result);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Article createArticle() {

		Article article = new Article();

		article.setTitle("Jackson - Java to JSON & JSON to Java");
		article.setUrl("http://hmkcode.com/jackson-java-json");
//		article.addCategory("Java");
//		article.addTag("Java");
//		article.addTag("Jackson");
//		article.addTag("JSON");

		return article;
	}
}
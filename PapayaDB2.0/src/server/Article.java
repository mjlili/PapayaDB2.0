package server;

import java.util.LinkedList;
import java.util.List;

public class Article {
	private String title;
	private String url;
	private List<String> categories;
	private List<String> tags;

	public Article() {
		this.categories = new LinkedList<String>();
		this.tags = new LinkedList<String>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void addCategory(String category) {
		this.categories.add(category);
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{title=").append(this.title).append(", url=").append(this.url).append(", categories=")
				.append(this.categories).append(", tags=").append(this.tags).append("}");
		return sb.toString();
	}
}

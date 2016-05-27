package services;

import java.util.Collections;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.Article;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ArticleService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	private ObjectMapper mp = new ObjectMapper();
	@RequestMapping(value="/articles", method=RequestMethod.GET)
	public String showArticles() throws JsonProcessingException{
		List<Article> articles = datastore.createQuery(Article.class).asList();	
		Collections.sort(articles);
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(articles);

	}
	@RequestMapping(value="/articles/{id}", method=RequestMethod.GET)
	public String getArticle(@PathVariable String id) throws JsonProcessingException{
		Query<Article> query = datastore.createQuery(Article.class);
		List<Article> articles=query.filter("_id ==", id).asList();
		if (articles.size()<1)
			return null;
		else{
			Article a=articles.get(0);
			return mp.writerWithDefaultPrettyPrinter().writeValueAsString(a);
		}
	}
}

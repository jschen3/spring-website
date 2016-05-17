package services;

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
import models.Teaser;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TeaserService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	ObjectMapper mp = new ObjectMapper();
	@RequestMapping(value="/teasers", method=RequestMethod.GET)
	public String showProjects() throws JsonProcessingException{
		List<Teaser> teasers = datastore.createQuery(Teaser.class).asList();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(teasers);
	}

	@RequestMapping(value="/teasers/{id}")
	public String getTeaser(@PathVariable String id) throws JsonProcessingException{
		Query<Teaser> query = datastore.createQuery(Teaser.class);
		List<Teaser> teasers=query.filter("_id ==", id).asList();
		ObjectMapper mp = new ObjectMapper();
		if (teasers.size()<1)
			return null;
		else{
			Teaser a=teasers.get(0);
			return mp.writerWithDefaultPrettyPrinter().writeValueAsString(a);
		}
	}
}

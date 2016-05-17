package services;

import java.util.List;


import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.Slide;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class SlideService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB,27017);
	private Morphia morphia = new  Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	ObjectMapper mp = new ObjectMapper();
	
	@RequestMapping(value="/slides", method=RequestMethod.GET)
	public String showSlides() throws JsonProcessingException{
		List<Slide> slides= datastore.createQuery(Slide.class).asList();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(slides);
	}
	
}

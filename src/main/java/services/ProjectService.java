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
import models.Project;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ProjectService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	@RequestMapping(value="/projects", method=RequestMethod.GET)
	public String showProjects() throws JsonProcessingException{
		List<Project> projects = datastore.createQuery(Project.class).asList();
		ObjectMapper mp = new ObjectMapper();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(projects);
	}
	
}

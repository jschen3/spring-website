package services;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.Comment;

@CrossOrigin(origins="http://localhost:8080")
@RestController
public class CommentService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	private ObjectMapper mp = new ObjectMapper();
	@RequestMapping(value="/comments/{id}", method=RequestMethod.GET)
	public String getComments(@PathVariable String id) throws JsonProcessingException{
		Query<Comment> query=datastore.createQuery(Comment.class);
		List<Comment> comments=query.filter("_elementId ==", id).asList();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(comments);
	}
	@RequestMapping(value="/comments/{id}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void addComment(@PathVariable String id, @RequestBody Comment newComment){
		newComment.insertIntoDbLocal();
		newComment.insertIntoDbRemote();
	}
	
}

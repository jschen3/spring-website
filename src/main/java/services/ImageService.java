package services;

import java.io.IOException;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import constants.WebsiteConstants;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ImageService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB, 27017);
	DB mongoDB = mongoClient.getDB("images");
	

	@RequestMapping(value="/images/{title}/{fileName}", method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> displayImage(@PathVariable String title, @PathVariable String fileName) throws IOException {
		GridFS imageStore = new GridFS(mongoDB, title);
		GridFSDBFile gridFile = imageStore.findOne(fileName);
		
		return ResponseEntity.ok().contentLength(gridFile.getLength()).contentType(MediaType.parseMediaType(gridFile.getContentType())).body(new InputStreamResource(gridFile.getInputStream()));
	}
}

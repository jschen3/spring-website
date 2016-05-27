package services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	

	@RequestMapping(value="/pictures/{title}/{fileName:.+}", method=RequestMethod.GET)
	public HttpEntity<byte[]> displayImage(@PathVariable String title, @PathVariable String fileName) throws IOException {
		System.out.println(title);
		System.out.println(fileName);
		GridFS imageStore = new GridFS(mongoDB, title);
		GridFSDBFile gridFile = imageStore.findOne(fileName);
		InputStream imageStream = gridFile.getInputStream();
		System.out.println(gridFile.getContentType());
		byte[] bytes = IOUtils.toByteArray(imageStream);
		HttpHeaders header = new HttpHeaders();
		if (gridFile.getContentType().equals("image/gif"))
			header.setContentType(MediaType.IMAGE_GIF);
		else if (gridFile.getContentType().equals("image/png"))
			header.setContentType(MediaType.IMAGE_PNG);
		else
			header.setContentType(MediaType.IMAGE_JPEG);
		header.setAccept(Arrays.asList(MediaType.IMAGE_GIF,MediaType.IMAGE_JPEG,MediaType.IMAGE_PNG));
		header.set("Content-Disposition", "inline; filename="+gridFile.getFilename());
		header.setContentLength(bytes.length);
		return new HttpEntity<byte[]>(bytes,header);
	}	


		
		
	
}

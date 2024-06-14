package ibf2023.csf.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ibf2023.csf.backend.models.Upload;

@Repository
public class PictureRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO Task 4.2
	// You may change the method signature by adding parameters and/or the  return type
	// You may throw any exception 
	/*
		db.travelpics.insertOne({date : <date>, title : <title>, comments: <comments>, url: <url>})
	 */
	public Upload save(Upload upload) {
		// IMPORTANT: Write the native mongo query in the comments above this method
		Upload response = mongoTemplate.save(upload, "travelpics");

		System.out.println(response.toString());

		return response;

	}

}

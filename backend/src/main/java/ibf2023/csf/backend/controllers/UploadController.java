package ibf2023.csf.backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.models.Upload;
import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {


	@Autowired 
	private PictureService pictureService;

	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart("title") String title,
	@RequestPart("comments") String comments,
	@RequestPart("datetime") String datetime,
	@RequestPart("picture") MultipartFile picture) throws IOException {

		try{
			Upload upload = new Upload(datetime, title, comments, "toBeSet");
			String response = pictureService.save(picture, upload);

			if(response.contains("The upload has exceeded your monthly upload quota")) {
				return ResponseEntity.status(413).body(Json.createObjectBuilder()
				.add("message", response)
				.build().toString());
			}

			return ResponseEntity.ok(
				Json.createObjectBuilder()
				.add("id", response)
				.build().toString()
			);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Json.createObjectBuilder()
				.add("message", e.getMessage())
				.build().toString());
		}
		
	}
}

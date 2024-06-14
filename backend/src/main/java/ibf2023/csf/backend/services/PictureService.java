package ibf2023.csf.backend.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.models.Upload;
import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;

@Service
public class PictureService {

	@Autowired
	ImageRepository imageRepo;

	@Autowired
	PictureRepository pictureRepo;

	@Value("${upload.size}")
	private String sizeRaw;

	// TODO Task 5.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public String save(MultipartFile picture, Upload upload) throws IOException {

		long sizeAllowed = Long.parseLong(sizeRaw) * 1024 * 1024; //convert to Bytes

		long allImagesSize = imageRepo.getSizeOfImagesInCurrentMonth();

		System.out.println(">>>> all Images Size: " + allImagesSize);

		//throw error
		if(sizeAllowed < picture.getSize() + allImagesSize ) {
			return "The upload has exceeded your monthly upload quota of " + sizeRaw + "MB";
		}

		String picUrl = imageRepo.save(picture);
		upload.setUrl(picUrl);
		upload = pictureRepo.save(upload);

		return upload.get_id();
	}

}

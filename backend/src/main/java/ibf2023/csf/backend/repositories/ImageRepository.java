package ibf2023.csf.backend.repositories;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Repository
public class ImageRepository {

	@Autowired
    private AmazonS3 s3;

    final String BUCKET_NAME = "csf-farm";

	// TODO Task 4.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public String save(MultipartFile file) throws IOException{

        Map<String, String> userData = new HashMap<>();
             
        userData.put("filename", file.getOriginalFilename());
        
        //metadata for the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0, 8);
        //take 4 parameter (bucketname, keyname | filename, inputstream, metadata)
        PutObjectRequest putReq = new PutObjectRequest(BUCKET_NAME, key, file.getInputStream(), metadata);

        // make object publically available (ppl without key also can access)
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // PutObjectResult result = s3.putObject(putReq);
        s3.putObject(putReq);

        
        
        return s3.getUrl(BUCKET_NAME, key).toString();
    }

    //return size in KB
    public long getSizeOfImagesInCurrentMonth() {
        //https://stackoverflow.com/questions/8027265/how-to-list-all-aws-s3-objects-in-a-bucket-using-java
        ObjectListing listing = s3.listObjects(BUCKET_NAME);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();
        long size = 0; //in Bytes

        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects (listing);
            summaries.addAll (listing.getObjectSummaries());
        }

        for(int i=0; i<summaries.size(); ++i) {
            if(!isCurrentMonth(summaries.get(i).getLastModified())) continue;
            size += summaries.get(i).getSize();

        }

        System.out.println(summaries);

        return size;
    }

    //https://stackoverflow.com/questions/26824020/java-check-if-a-given-date-is-within-current-month
    private boolean isCurrentMonth(Date date) {
        //Create 2 instances of Calendar
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        //set the given date in one of the instance and current date in the other
        cal1.setTime(date);
        cal2.setTime(new Date());

        //now compare the dates using methods on Calendar
        if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                return true;
            }
        }

        return false;
    }

}


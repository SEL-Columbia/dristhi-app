package org.ei.opensrp.service;

import org.ei.opensrp.Context;
import org.ei.opensrp.domain.ProfileImage;
import org.ei.opensrp.repository.ImageRepository;

import java.util.ArrayList;

/**
 * Created by Dimas Ciputra on 3/22/15.
 */
public class ImageUploadSyncService {


    private ImageRepository imageRepository;



    public ImageUploadSyncService(ImageRepository imageRepository) {

        this.imageRepository = imageRepository;
        ImageSync((ArrayList<ProfileImage>) imageRepository.allProfileImages(),imageRepository);
    }
    public void ImageSync(ArrayList<ProfileImage> profileimages,ImageRepository imagerepository){
        for(int i = 0;i<profileimages.size();i++){
            int response = Context.getInstance().getHttpAgent().httpImagePost(Context.getInstance().configuration().dristhiBaseURL()+"/multimedia-file",profileimages.get(i));
            int RESPONSE_OK = 200;
            int RESPONSE_OK_ = 201;

            if (response != RESPONSE_OK_ && response != RESPONSE_OK) {
            }else{
                imagerepository.close(profileimages.get(i).getImageid());
            }


        }
    }



}
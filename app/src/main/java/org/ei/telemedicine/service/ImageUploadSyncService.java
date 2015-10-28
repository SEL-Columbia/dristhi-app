package org.ei.telemedicine.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.domain.ProfileImage;
import org.ei.telemedicine.domain.Response;
import org.ei.telemedicine.dto.form.FormSubmissionDTO;
import org.ei.telemedicine.repository.ImageRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static org.ei.telemedicine.domain.FetchStatus.fetchedFailed;
import static org.ei.telemedicine.util.Log.logError;

/**
 * Created by Dimas Ciputra on 3/22/15.
 */
public class ImageUploadSyncService {


    private ImageRepository imageRepository;


    public ImageUploadSyncService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        ImageSync((ArrayList<ProfileImage>) imageRepository.allProfileImages(), imageRepository);
        getImages();
    }

    private void getImages() {
        String anmId = Context.getInstance().allSharedPreferences().fetchRegisteredANM();

        String imageGetURL = Context.getInstance().configuration().dristhiBaseURL() + "/multimedia-file?anm-id=" + anmId;
        Log.e("Image Getting url", imageGetURL);
        Response<String> response = Context.getInstance().getHttpAgent().fetch(imageGetURL);
        if (response.isFailure()) {
            logError(format("Fetching images url failed."));
        } else {
            try {
                JSONArray imagesArray = new JSONArray(response.payload());
                for (int i = 0; i < imagesArray.length(); i++) {
                    JSONObject imageJson = imagesArray.getJSONObject(i);
                    Context.getInstance().allEligibleCouples().updatePhotoPath(getDataFromJson(imageJson.toString(), "caseId"), Context.getInstance().configuration().dristhiBaseURL() + "/" + getDataFromJson(imageJson.toString(), "filePath"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String getDataFromJson(String jsonStr, String key) {
        if (jsonStr != null && !jsonStr.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.has(key) ? jsonObject.getString(key) : "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void ImageSync(ArrayList<ProfileImage> profileimages, ImageRepository imagerepository) {
        for (int i = 0; i < profileimages.size(); i++) {
            int response = Context.getInstance().getHttpAgent().httpImagePost(Context.getInstance().configuration().dristhiBaseURL() + "/multimedia-file", profileimages.get(i));
            int RESPONSE_OK = 200;
            int RESPONSE_OK_ = 201;

            if (response != RESPONSE_OK_ && response != RESPONSE_OK) {
            } else {
                imagerepository.close(profileimages.get(i).getImageid());
            }


        }
    }


}
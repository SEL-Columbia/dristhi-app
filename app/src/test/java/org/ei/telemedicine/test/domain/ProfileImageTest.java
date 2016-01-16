package org.ei.telemedicine.test.domain;

import org.ei.telemedicine.domain.ProfileImage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by naveen on 12/24/15.
 */
public class ProfileImageTest {
    ProfileImage  profileImage;

    @Before
    public void setUp(){
        profileImage = new ProfileImage("imageid1", "anmid1", "entityid1", "contenttype", "filepath", "status");

    }

    @Test
    public void anmTest(){
        profileImage.setAnmId("anm123");
       String anmID= profileImage.getAnmId();

        assertEquals("anm123",anmID);
    }

    @Test
    public void imageTest(){
        profileImage.setImageid("123");
        String imageid= profileImage.getImageid();

        assertEquals("123", imageid);
    }

    @Test
    public void profileImageTest(){
        profileImage.setEntityID("456");
        String entityID = profileImage.getEntityID();

        profileImage.setContenttype("sudheer");
        String contenttype = profileImage.getContenttype();

        profileImage.setFilepath("home");
        String filepath = profileImage.getFilepath();

        profileImage.setSyncStatus("staus");
        String syncstaus = profileImage.getSyncStatus();

        assertEquals("456", entityID);
        assertEquals("sudheer",contenttype);
        assertEquals("home",filepath);
        assertEquals("staus",syncstaus);
    }

}

package org.ei.telemedicine.test.domain;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.domain.ANCServiceType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ANCServiceTypeTest {

    @Mock
    Context context;

    @Mock
    private android.content.Context applicationContext;
    @Before
    public void setUp(){
        initMocks(this);
        when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);

    }
    @Test
    public void shouldParseStringToACNServiceType() throws Exception {
        assertEquals(ANCServiceType.ANC_1, ANCServiceType.tryParse("anc 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_2, ANCServiceType.tryParse("anc 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_3, ANCServiceType.tryParse("anc 3", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.ANC_4, ANCServiceType.tryParse("anc 4", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_1, ANCServiceType.tryParse("tt 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_2, ANCServiceType.tryParse("tt 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.TT_BOOSTER, ANCServiceType.tryParse("TT Booster", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 3", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.IFA, ANCServiceType.tryParse("IFA 1", ANCServiceType.EMPTY));

        assertEquals(ANCServiceType.HB_TEST, ANCServiceType.tryParse("HB Test 1", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.HB_TEST, ANCServiceType.tryParse("HB Test 2", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.DELIVERY_PLAN, ANCServiceType.tryParse("Delivery Plan", ANCServiceType.EMPTY));
        assertEquals(ANCServiceType.PNC, ANCServiceType.tryParse("PNC", ANCServiceType.EMPTY));

    }


    @Test
    public void shouldParseBlankStringAsDefaultANCServiceType() throws Exception {
        ANCServiceType defaultMethod = ANCServiceType.EMPTY;
        assertEquals(defaultMethod, ANCServiceType.tryParse("", defaultMethod));
    }

    @Test
    public void shouldParseInvalidStringAsDefaultANCServiceType() throws Exception {
        ANCServiceType defaultMethod = ANCServiceType.ANC_1;
        assertEquals(defaultMethod, ANCServiceType.tryParse("---", defaultMethod));
    }
    @Test
    public void shouldParseInvalidStringAsDefaultANCService() throws Exception {
        ANCServiceType x = ANCServiceType.ANC_2;
        assertEquals(x, ANCServiceType.tryParse("---", x));
    }

    @Test
    public void shouldParseInvalidStringAsDefaultANC3Service() throws Exception {
        ANCServiceType anc3 = ANCServiceType.ANC_3;
        assertEquals(anc3, ANCServiceType.tryParse("---", anc3));
    }
    @Test
    public void displayNameAnc1() throws  Exception{
        String anc1  =  "anc";
        assertEquals(anc1, ANCServiceType.valueOf("ANC_1").category());
    }
    @Test
    public void displayNameAnc2() throws  Exception{
        String anc2  =  "anc";
        assertEquals(anc2, ANCServiceType.valueOf("ANC_2").category());
    }
    @Test
    public void displayNameAnc3() throws  Exception{
        String anc3  =  "anc";
        assertEquals(anc3, ANCServiceType.valueOf("ANC_3").category());
    }


    @Test
    public void displayNameAnc4() throws  Exception{
        String anc4  =  "anc";
        assertEquals(anc4, ANCServiceType.valueOf("ANC_4").category());
    }
    @Test
    public void displayNameTT1() throws  Exception{
        String tt1  =  "tt";
        assertEquals(tt1, ANCServiceType.valueOf("TT_1").category());
    }

    @Test
    public void displayNameTT2() throws  Exception{
        String tt2  =  "tt";
        assertEquals(tt2, ANCServiceType.valueOf("TT_2").category());
    }
    @Test
    public void displayNameTTBooster() throws  Exception{
        String ttbooster  =  "tt";
        assertEquals(ttbooster, ANCServiceType.valueOf("TT_BOOSTER").category());
    }

    @Test
    public void displayNameifaTest() throws  Exception{
        String ifa  =  "ifa";
        assertEquals(ifa, ANCServiceType.valueOf("IFA").category());
    }


    @Test
    public void displayNamehbTest() throws  Exception{
        String hbtest  =  "hb";
        assertEquals(hbtest, ANCServiceType.valueOf("HB_TEST").category());
    }

    @Test
    public void displayNamehbdelivaryPlanTest() throws  Exception{
        String devliavary  =  "delivery_plan";
        assertEquals(devliavary, ANCServiceType.valueOf("DELIVERY_PLAN").category());
    }

    @Test
    public void displayNamehbdelivarypncTest() throws  Exception{
        String pnc  =  "pnc";
        assertEquals(pnc, ANCServiceType.valueOf("PNC").category());
    }


    @Test
    public void displayNamehbancserviceNameAnc1Test() throws  Exception{
        String anc1  =  "ANC 1";
        assertEquals(anc1, ANCServiceType.valueOf("ANC_1").serviceName());
    }
    @Test
    public void displayNamehbancserviceNameAnc2Test() throws  Exception{
        String anc2  =  "ANC 2";
        assertEquals(anc2, ANCServiceType.valueOf("ANC_2").serviceName());
    }
    @Test
    public void displayNamehbancserviceNameAnc3Test() throws  Exception{
        String anc3  =  "ANC 3";
        assertEquals(anc3, ANCServiceType.valueOf("ANC_3").serviceName());
    }

    @Test
    public void displayNamehbancserviceNameAnc4Test() throws  Exception{
        String anc4  =  "ANC 4";
        assertEquals(anc4, ANCServiceType.valueOf("ANC_4").serviceName());
    }

    @Test
    public void displayNamehbancserviceNameTT1Test() throws  Exception{
        String tt1  =  "TT 1";
        assertEquals(tt1, ANCServiceType.valueOf("TT_1").serviceName());
    }


    @Test
    public void displayNamehbancserviceNameTT2Test() throws  Exception{
        String tt2  =  "TT 2";
        assertEquals(tt2, ANCServiceType.valueOf("TT_2").serviceName());
    }
    @Test
    public void displayNamehbancserviceNameTTBoosterTest() throws  Exception{
        String ttbooster  =  "TT Booster";
        assertEquals(ttbooster, ANCServiceType.valueOf("TT_BOOSTER").serviceName());
    }
    @Test
    public void displayNamehbancserviceNameIfaTest() throws  Exception{
        String ifa  =  "IFA";
        assertEquals(ifa, ANCServiceType.valueOf("IFA").serviceName());
    }
    @Test
    public void displayNamehbancserviceNameHbTest() throws  Exception{
        String hb  =  "Hb";
        assertEquals(hb, ANCServiceType.valueOf("HB_TEST").serviceName());
    }
    @Test
    public void displayNamehbancserviceNamedelivaryPlanTest() throws  Exception {
        String dplan = "Delivery Plan";
        assertEquals(dplan, ANCServiceType.valueOf("DELIVERY_PLAN").serviceName());
    }
    @Test
    public void displayNamehbancserviceNamePncTest() throws  Exception {
        String pnc = "PNC";
        assertEquals(pnc, ANCServiceType.valueOf("PNC").serviceName());
    }
    @Test
    public void displayNamehbancserviceName() throws  Exception {
        String empty = "";
        assertEquals(empty, ANCServiceType.valueOf("EMPTY").serviceName());
    }


    @Test
    public void displayNamedisplayNameAnc1() throws  Exception {
        String danm1 = "ANC 1";
        assertEquals(danm1, ANCServiceType.valueOf("ANC_1").displayName());
    }
    @Test
    public void displayNamedisplayNameAnc2() throws  Exception {
        String danm2 = "ANC 2";
        assertEquals(danm2, ANCServiceType.valueOf("ANC_2").displayName());
    }
    @Test
    public void displayNamedisplayNameAnc3() throws  Exception {
        String danm3 = "ANC 3";
        assertEquals(danm3, ANCServiceType.valueOf("ANC_3").displayName());
    }
    @Test
    public void displayNamedisplayNameAnc4() throws  Exception {
        String danm4 = "ANC 4";
        assertEquals(danm4, ANCServiceType.valueOf("ANC_4").displayName());
    }
    @Test
    public void displayNamedisplayNamett1() throws  Exception {
        String dtt1 = "TT";
        assertEquals(dtt1, ANCServiceType.valueOf("TT_1").displayName());
    }
    @Test
    public void displayNamedisplayNamett2() throws  Exception {
        String dtt2 = "TT 2";
        assertEquals(dtt2, ANCServiceType.valueOf("TT_2").displayName());
    }
    @Test
    public void displayNamedisplayNamettbooster() throws  Exception {
        String dttbooster = "TT B";
        assertEquals(dttbooster, ANCServiceType.valueOf("TT_BOOSTER").displayName());
    }

    @Test
    public void displayNamedisplayNameIfa() throws  Exception {
        String difa = "IFA Tablets";
        assertEquals(difa, ANCServiceType.valueOf("IFA").displayName());
    }

    @Test
    public void displayNamedisplayNamehbTest() throws  Exception {
        String dhb = "Hb Test";
        assertEquals(dhb, ANCServiceType.valueOf("HB_TEST").displayName());
    }
    @Test
    public void displayNamedisplaydelivaryPlanTest() throws  Exception {
        String ddplan = "Delivery Plan";
        assertEquals(ddplan, ANCServiceType.valueOf("DELIVERY_PLAN").displayName());
    }
    @Test
    public void displayNamedisplayPncTest() throws  Exception {
        String dpnc = "PNC";
        assertEquals(dpnc, ANCServiceType.valueOf("PNC").displayName());
    }
/*    @Test
    public void displayNamedisplayemptyTest() throws  Exception {
        String dempty = Context.getInstance().applicationContext().getString(R.string.service_type_empty);
        assertEquals(dempty, ANCServiceType.valueOf("EMPTY").displayName());
    }*/
    @Test
    public void serviceDisplayNameTT1() throws  Exception {
        String sdtt1 = "TT 1";
        assertEquals(sdtt1, ANCServiceType.valueOf("TT_1").serviceDisplayName());
    }
    @Test
    public void serviceDisplayNameTT2() throws  Exception {
        String sdtt2 = "TT 2";
        assertEquals(sdtt2, ANCServiceType.valueOf("TT_2").serviceDisplayName());
    }
    @Test
    public void serviceDisplayNameTTBooster() throws  Exception {
        String sdttbooster = "TT B";
        assertEquals(sdttbooster, ANCServiceType.valueOf("TT_BOOSTER").serviceDisplayName());
    }
    @Test
    public void serviceDisplayNameemptys() throws  Exception {
        String sempty = "";
        assertEquals(sempty, ANCServiceType.valueOf("EMPTY").category());
    }

    @Test
    public void displayEmptyTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_empty)).thenReturn("");
        assertEquals("", ANCServiceType.valueOf("EMPTY").displayName());
    }



/*    @Test
    public void serviceDisplayName() throws  Exception {
        String s = "test";
        assertEquals(s, ANCServiceType.valueOf("serviceName").serviceDisplayName());
    }
    @Test
    public void ShortName() throws  Exception {
        String x = "test";
        assertEquals(x, ANCServiceType.valueOf("displayName").serviceDisplayName());
    }*/
}
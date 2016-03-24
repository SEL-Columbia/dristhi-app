package org.ei.telemedicine.test.domain;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.domain.ChildServiceType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ChildServiceTypeTest {

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
        //assertEquals("MEASLES", ChildServiceType.valueOf("MEASLES").displayName());
        //assertEquals(ChildServiceType.MEASLESBOOSTER, ChildServiceType.tryParse("anc 2", ChildServiceType.EMPTY));
        assertEquals(ChildServiceType.ILLNESS_VISIT, ChildServiceType.tryParse("Illness Visit", ChildServiceType.EMPTY));
        assertEquals(ChildServiceType.VITAMIN_A, ChildServiceType.tryParse("Vitamin A", ChildServiceType.EMPTY));
        assertEquals(ChildServiceType.OPV_BOOSTER, ChildServiceType.tryParse("opvbooster", ChildServiceType.EMPTY));
    }


    @Test
    public void shouldParseBlankStringAsDefaultChildServiceType() throws Exception {
        ChildServiceType defaultMethod = ChildServiceType.EMPTY;
        assertEquals(defaultMethod, ChildServiceType.tryParse("", defaultMethod));
    }

    @Test
    public void shouldParseInvalidStringAsDefaultChildServicesIllnesType() throws Exception {
        ChildServiceType defaultMethod = ChildServiceType.ILLNESS_VISIT;
        assertEquals(defaultMethod, ChildServiceType.tryParse("---", defaultMethod));
    }
    @Test
    public void shouldParseInvalidStringAsDefaultChildServiceVitaminType() throws Exception {
        ChildServiceType defaultMethod = ChildServiceType.VITAMIN_A;
        assertEquals(defaultMethod, ChildServiceType.tryParse("---", defaultMethod));
    }


    @Test
    public void shouldParseInvalidStringAsDefaultChildServiceOpvBoosterType() throws Exception {
        ChildServiceType defaultMethod = ChildServiceType.OPV_BOOSTER;
        assertEquals(defaultMethod, ChildServiceType.tryParse("---", defaultMethod));
    }

    @Test
    public void displayNameOpvs() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_booster)).thenReturn("OPV Boost");
        assertEquals("OPV Boost", ChildServiceType.valueOf("OPV_BOOSTER").displayName());
    }


    @Test
    public void displayNameDpopboost() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_1)).thenReturn("DPT Boost");
        assertEquals("DPT Boost", ChildServiceType.valueOf("DPTBOOSTER_1").displayName());

        when(context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_1_short)).thenReturn("DPT Boost");
        assertEquals("DPT Boost", ChildServiceType.valueOf("DPTBOOSTER_1").shortName());
    }


    @Test
    public void displayName() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_1)).thenReturn("OPV 1");
        assertEquals("OPV 1", ChildServiceType.valueOf("OPV_1").displayName());
    }

    @Test
    public void displayNameMasales() throws  Exception{
        String Mesles  =  "measles";
        assertEquals(Mesles, ChildServiceType.valueOf("MEASLES").category());
    }
    @Test
    public void displayNameMeasles() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_measles)).thenReturn("Measles");
        assertEquals("Measles", ChildServiceType.valueOf("MEASLES").displayName());
    }

    @Test
    public void displayNameMasalesBooster() throws  Exception{
        String MeaslesBosster  =  "measles";
        assertEquals(MeaslesBosster, ChildServiceType.valueOf("MEASLESBOOSTER").category());
    }

    @Test
    public void displayNameMeaslesBooster() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_measles_booster)).thenReturn("MeaslesBooster");
        assertEquals("MeaslesBooster", ChildServiceType.valueOf("MEASLESBOOSTER").displayName());
    }
    @Test
    public void displayNameMeaslesBoos() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_measles_booster_short)).thenReturn("Measles B");
        assertEquals("Measles B", ChildServiceType.valueOf("MEASLESBOOSTER").shortName());
    }
    @Test
    public void displayNameOpvBooster() throws  Exception{
        String Opv  =  "opvbooster";
        assertEquals(Opv, ChildServiceType.valueOf("OPV_BOOSTER").category());
    }

    @Test
    public void displayNameOpv() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_measles_booster_short)).thenReturn("Measles B");
        assertEquals("Measles B", ChildServiceType.valueOf("MEASLESBOOSTER").shortName());
    }
    @Test
    public void displayNameDtBooster1() throws  Exception{
        String DtBooster1  =  "dpt";
        assertEquals(DtBooster1, ChildServiceType.valueOf("DPTBOOSTER_1").category());
    }

    @Test
    public void displayNameDtBooster2() throws  Exception{
        String DtBooster2  =  "dpt";
        assertEquals(DtBooster2, ChildServiceType.valueOf("DPTBOOSTER_2").category());
    }

    @Test
    public void displayDpBoost() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_2_short)).thenReturn("Measles Bo");
        assertEquals("Measles Bo", ChildServiceType.valueOf("DPTBOOSTER_2").shortName());

        when(context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_2)).thenReturn("Measles Boso");
        assertEquals("Measles Boso", ChildServiceType.valueOf("DPTBOOSTER_2").displayName());
    }


    @Test
    public void displayNameOpv0() throws  Exception{
        String opv0  =  "opv";
        assertEquals(opv0, ChildServiceType.valueOf("OPV_0").category());
    }

    @Test
    public void displayNameOpv0Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_0)).thenReturn("OPV 0");
        assertEquals("OPV 0", ChildServiceType.valueOf("OPV_0").displayName());
    }

    @Test
    public void displayNameOpv1Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_1)).thenReturn("OPV 1");
        assertEquals("OPV 1", ChildServiceType.valueOf("OPV_1").displayName());
    }

    @Test
    public void displayNameOpv2Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_2)).thenReturn("OPV 2");
        assertEquals("OPV 2", ChildServiceType.valueOf("OPV_2").displayName());
    }

    @Test
    public void displayNameOpv3Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_3)).thenReturn("OPV 3");
        assertEquals("OPV 3", ChildServiceType.valueOf("OPV_3").displayName());
    }
    @Test
    public void displayNameOpv1() throws  Exception{
        String opv1  =  "opv";
        assertEquals(opv1, ChildServiceType.valueOf("OPV_1").category());
    }

    @Test
    public void displayNameOpv2() throws  Exception{
        String opv2  =  "opv";
        assertEquals(opv2, ChildServiceType.valueOf("OPV_2").category());
    }

    @Test
    public void displayNameOpv3() throws  Exception{
        String opv3  =  "opv";
        assertEquals(opv3, ChildServiceType.valueOf("OPV_3").category());
    }

    @Test
         public void displayNamepentavalent1() throws  Exception{
        String pentavalent1  =  "pentavalent";
        assertEquals(pentavalent1, ChildServiceType.valueOf("PENTAVALENT_1").category());
    }

    @Test
    public void displayNamePentavalentTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_1)).thenReturn("Pentavalent");
        assertEquals("Pentavalent", ChildServiceType.valueOf("PENTAVALENT_1").displayName());

        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_1_short)).thenReturn("Pentav");
        assertEquals("Pentav", ChildServiceType.valueOf("PENTAVALENT_1").shortName());
    }


    @Test
    public void displayNamepentavalent2() throws  Exception{
        String pentavalent2  =  "pentavalent";
        assertEquals(pentavalent2, ChildServiceType.valueOf("PENTAVALENT_2").category());
    }

    @Test
    public void displayNamePentavalent2Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_2)).thenReturn("Pentavalent2");
        assertEquals("Pentavalent2", ChildServiceType.valueOf("PENTAVALENT_2").displayName());

        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_2_short)).thenReturn("Pentav2");
        assertEquals("Pentav2", ChildServiceType.valueOf("PENTAVALENT_2").shortName());
    }


    @Test
    public void displayNamepentavalent3() throws  Exception{
        String pentavalent3  =  "pentavalent";
        assertEquals(pentavalent3, ChildServiceType.valueOf("PENTAVALENT_3").category());
    }

    @Test
    public void displayNamePentavalent3Test() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_3)).thenReturn("Pentavalent3");
        assertEquals("Pentavalent3", ChildServiceType.valueOf("PENTAVALENT_3").displayName());

        when(context.getInstance().applicationContext().getString(R.string.service_type_pentavalent_3_short)).thenReturn("Pentav3");
        assertEquals("Pentav3", ChildServiceType.valueOf("PENTAVALENT_3").shortName());
    }


    @Test
    public void displayNamebcg() throws  Exception{
        String bcg  =  "bcg";
        assertEquals(bcg, ChildServiceType.valueOf("BCG").category());
    }

    @Test
    public void displayNamebcgTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_bcg)).thenReturn("BCG");
        assertEquals("BCG", ChildServiceType.valueOf("BCG").displayName());
    }

    @Test
    public void displayNamepncTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_pnc)).thenReturn("Pnc");
        assertEquals("Pnc", ChildServiceType.valueOf("PNC").displayName());
    }

    @Test
    public void displayNameJeTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_je)).thenReturn("Je");
        assertEquals("Je", ChildServiceType.valueOf("JE").displayName());
    }

    @Test
    public void displayNameMmrTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_mmr)).thenReturn("Mmr");
        assertEquals("Mmr", ChildServiceType.valueOf("MMR").displayName());
    }


    @Test
    public void displayNameVitaminATest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_vitamin_a)).thenReturn("VitaminA");
        assertEquals("VitaminA", ChildServiceType.valueOf("VITAMIN_A").displayName());
    }


    @Test
    public void displayNameIllnusVisitTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_illness_visit)).thenReturn("IllnessVisit");
        assertEquals("IllnessVisit", ChildServiceType.valueOf("ILLNESS_VISIT").displayName());
    }

    @Test
    public void displayEmptyTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_empty)).thenReturn("");
        assertEquals("", ChildServiceType.valueOf("EMPTY").displayName());
    }


    @Test
    public void displayPncemptyTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_pnc)).thenReturn("");
        assertEquals("", ChildServiceType.valueOf("PNC").category());
    }

    @Test
    public void displayPncempTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_je)).thenReturn("");
        assertEquals("", ChildServiceType.valueOf("JE").category());
    }

    @Test
    public void displayPncempmmrTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_mmr)).thenReturn("");
        assertEquals("", ChildServiceType.valueOf("MMR").category());
    }

    @Test
    public void displayPncem() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_empty)).thenReturn("");
        assertEquals("", ChildServiceType.valueOf("EMPTY").category());
    }

    @Test
    public void displayNameHepbTest() throws  Exception{
     /*   when(context.applicationContext()).thenReturn(applicationContext);
        Context.setInstance(context);*/
        when(context.getInstance().applicationContext().getString(R.string.service_type_hepb_0)).thenReturn("hepb");
        assertEquals("hepb", ChildServiceType.valueOf("HEPB_0").displayName());
    }
    @Test
    public void displayNamehbo() throws  Exception{
        String hep  =  "hepb";
        assertEquals(hep, ChildServiceType.valueOf("HEPB_0").category());
    }
    @Test
    public void displayNameVitamin_A() throws  Exception{
        String vitamin  =  "vitamin_a";
        assertEquals(vitamin, ChildServiceType.valueOf("VITAMIN_A").category());
    }
    @Test
    public void displayNameChildillnuss() throws  Exception{
        String childillnuss  =  "child_illness";
        assertEquals(childillnuss, ChildServiceType.valueOf("ILLNESS_VISIT").category());
    }

    /*@Test
    public void displayNameChildss() throws  Exception{
        String xyz  =  Context.getInstance().applicationContext().getString(R.string.service_type_measles);
                assertEquals(xyz, ChildServiceType.valueOf("MEASLES").displayName());
    }*/
    /*    @Test
    public void displayName() throws  Exception{
//        String Booster  =  ChildServiceType.OPV_BOOSTER.displayName();
        when(context.getInstance().applicationContext().getString(R.string.service_type_opv_1)).thenReturn("OPV 1");
       // String s1  =  ChildServiceType.OPV_1.displayName();
        //String Booster = "OPV Boost";
        assertEquals("OPV 1", ChildServiceType.valueOf("OPV_1").displayName());
    }*/
/*    @Test
    public void shortName() throws  Exception{
        String shortname  =  ChildServiceType.DPTBOOSTER_1.shortName();
        String x = Context.getInstance().applicationContext().getString(R.string.service_type_dpt_booster_1_short);
        String  s = shortname.substring(Integer.parseInt(x));

        assertEquals(s, ChildServiceType.valueOf("service_type_dpt_booster_1_short"));
    }*/


}


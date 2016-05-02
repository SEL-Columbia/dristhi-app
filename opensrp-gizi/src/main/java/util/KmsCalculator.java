package util;

/**
 * Created by Iq on 02/05/16.
 */
public class KmsCalculator {
    public String cek2T(KmsPerson bayi){
        String currentDate = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
        int tahun = Integer.parseInt(currentDate.substring(0,4))-Integer.parseInt(bayi.getLastVisitDate().substring(0,4));
        int bulan = Integer.parseInt(currentDate.substring(5,7))-Integer.parseInt(bayi.getLastVisitDate().substring(5,7));

        bayi.Tidak2Kali = (tahun*12 + bulan) > 2;
        return " "+(bayi.Tidak2Kali ? "ya":"tidak");
        //System.out.println("Tidak naik timbangan 2 kali :"+(bayi.Tidak2Kali ? "ya":"tidak"));
    }

    public String cekWeightStatus(KmsPerson bayi){
        if(bayi.isMale())
            bayi.StatusBeratBadan = bayi.getPreviousWeight()==0.0 ? "timbang pertama" :
                    ((bayi.getWeight()-bayi.getPreviousWeight())*1000) > KmsConstants
                            .maleWeightUpIndicator[bayi.getAge() > 12 ? 12:bayi.getAge() ] ? "naik" : "tidak naik";
        else
            bayi.StatusBeratBadan = bayi.getPreviousWeight()==0.0 ? "timbang pertama" :
                    ((bayi.getWeight()-bayi.getPreviousWeight())*1000) > KmsConstants
                            .femaleWeightUpIndicator[bayi.getAge() > 11 ? 11:bayi.getAge() ] ? "naik" : "tidak naik";
        return  " "+bayi.StatusBeratBadan;
       // System.out.println("status :"+bayi.StatusBeratBadan);
    }

    public String cekBGM(KmsPerson bayi){
        bayi.BGM = bayi.isMale()
                ? KmsConstants.maleBGM[bayi.getAge()]>bayi.getWeight()
                : KmsConstants.femaleBGM[bayi.getAge()]>bayi.getWeight();
        return " "+(bayi.BGM ? "ya":"tidak");
        //system.out.println("BGM    :"+ (bayi.BGM ? "ya":"tidak"));
    }

    public String cekBawahKuning(KmsPerson bayi){
        bayi.GarisKuning = bayi.isMale()
                ? ((KmsConstants.maleGarisKuning[bayi.getAge()][0]<=bayi.getWeight())
                && (bayi.getWeight()<=KmsConstants.maleGarisKuning[bayi.getAge()][1]))
                : ((KmsConstants.femaleGarisKuning[bayi.getAge()][0]<=bayi.getWeight())
                && (bayi.getWeight()<=KmsConstants.femaleGarisKuning[bayi.getAge()][1]))
        ;
        return " "+(bayi.GarisKuning ? "ya":"tidak");
        //System.out.println("Dalam Garis Kuning : "+(bayi.GarisKuning ? "ya":"tidak"));
    }


}

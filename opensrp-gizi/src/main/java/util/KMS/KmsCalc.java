package util.KMS;

/**
 * Created by Iq on 27/05/16.
 */
public class KmsCalc {


    public int monthAges(String lastVisitDate,String currentDate){
        int tahun = Integer.parseInt(currentDate.substring(0,4))-Integer.parseInt(lastVisitDate.substring(0,4));
        int bulan = Integer.parseInt(currentDate.substring(5,7))-Integer.parseInt(lastVisitDate.substring(5,7));
        int hari = Integer.parseInt(currentDate.substring(8))-Integer.parseInt(lastVisitDate.substring(8));
        return(tahun*12 + bulan + (int)(hari/30));
    }

    public String cek2T(KmsPerson bayi){
        boolean status = true;
        System.out.println("check 2T");
        String measureDate[] = {new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()),bayi.getLastVisitDate()};
        double weight[] = {bayi.getWeight(),bayi.getPreviousWeight()};
        status = status && (!cekWeightStatus(bayi.isMale(), bayi.getDateOfBirth(), measureDate, weight).equalsIgnoreCase("naik"));
        String measureDate2[] = {bayi.getLastVisitDate(),bayi.getSecondLastVisitDate()};
        double weight2[] = {bayi.getPreviousWeight(),bayi.getSecondLastWeight()};
        status = status && (!cekWeightStatus(bayi.isMale(), bayi.getDateOfBirth(), measureDate2, weight2).equalsIgnoreCase("naik"));
        bayi.Tidak2Kali = status;
        return " "+(bayi.Tidak2Kali ? "Ya":"Tidak");
    }



    public String cekWeightStatus(KmsPerson bayi){
        System.out.println("check weight status");
        String measureDate[] = {new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date()),bayi.getLastVisitDate()};
        double weight[] = {bayi.getWeight(),bayi.getPreviousWeight()};
        bayi.StatusBeratBadan = cekWeightStatus(bayi.isMale(),bayi.getDateOfBirth(),measureDate,weight);

        return  " "+bayi.StatusBeratBadan;
    }

    public String cekWeightStatus(boolean isMale, String dateOfBirth, String measureDate[], double weight[]){
        int age = monthAges(dateOfBirth,measureDate[0]);
        String result="";
        if(isMale)
            result = monthAges(measureDate[1],measureDate[0])>1 ? "timbang pertama" :((weight[0]-weight[1]+0.000000000000004)*1000)
                    >= KmsConstants.maleWeightUpIndicator[age > 12 ? 12:age ]
                    ? "Naik" : "Tidak naik";
        else
            result = monthAges(measureDate[1],measureDate[0])>1 ? "timbang pertama" : ((weight[0]-weight[1]+0.000000000000004)*1000)
                    >= KmsConstants.femaleWeightUpIndicator[age > 11 ? 11:age ]
                    ? "Taik" : "Tidak naik";

        return result;
    }

    public String cekBGM(KmsPerson bayi){
        bayi.BGM = bayi.isMale()
                ? KmsConstants.maleBGM[bayi.getAge()]>bayi.getWeight()
                : KmsConstants.femaleBGM[bayi.getAge()]>bayi.getWeight();
        return " "+(bayi.BGM ? "Ya":"Tidak");
    }

    public String cekBawahKuning(KmsPerson bayi){
        bayi.GarisKuning = bayi.isMale()
                ? ((KmsConstants.maleGarisKuning[bayi.getAge()][0]<=bayi.getWeight())
                && (bayi.getWeight()<=KmsConstants.maleGarisKuning[bayi.getAge()][1]))
                : ((KmsConstants.femaleGarisKuning[bayi.getAge()][0]<=bayi.getWeight())
                && (bayi.getWeight()<=KmsConstants.femaleGarisKuning[bayi.getAge()][1]))
        ;
        return " "+(bayi.GarisKuning ? "Ta":"Tidak");
    }


}

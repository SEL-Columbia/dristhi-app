package util;

/**
 * Created by Iq on 02/05/16.
 */
public class KmsPerson {

  //  private String name;
    private boolean isMale;
    private int age;
    private double weight;
    private double previousWeight;
    private String lastVisitDate;

    //ditanya
    public String StatusBeratBadan;         // value : naik, tidak, ukur pertama
    public boolean BGM;
    public boolean GarisKuning;
    public boolean Tidak2Kali;

    //constructor
    public KmsPerson(  boolean isMale, int age, double weight,
                    double previousWeight,String lastVisitData){
        this.isMale=isMale;
        this.age=age;
        this.weight=weight;
        this.previousWeight=previousWeight;
        this.lastVisitDate=lastVisitData;
    }

    //mutators
    public boolean isMale(){return isMale;}
    public int getAge(){return age;}
    public double getWeight(){return weight;}
    public double getPreviousWeight(){return previousWeight;}
    public String getLastVisitDate(){return lastVisitDate;}
}

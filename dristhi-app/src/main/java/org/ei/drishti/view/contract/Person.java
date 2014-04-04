package org.ei.drishti.view.contract;


import java.util.Comparator;

public class Person {

    public static final Comparator<Person> NAME_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person person, Person person2) {
            return person.name.compareToIgnoreCase(person2.name);
        }
    };

    public static final Comparator<Person> AGE_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person person, Person person2) {
            return person.age == person2.age ? 0 : person.age < person2.age ? -1 : 1;
        }
    };

    public static final Comparator<Person> EC_NO_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person person, Person person2) {
            return person.ecNumber == person2.ecNumber ? 0 : person.ecNumber < person2.ecNumber ? -1 : 1;
        }
    };

    private static Person[] testData = {new Person(1, "laxmamma", "RameGowda", "Gowrikoppalu", 35, 5009),
            new Person(2, "Saakavva", "Beerappa", "Gowrikoppalu", 15, 5005),
            new Person(3, "Mallamma", "Siddesh", "KallushettyHalli", 35, 5007),
            new Person(4, "Sannamma", "Kallesh", "Gowrikoppalu", 32, 5004),
            new Person(5, "Huchhamma", "Tippesh", "Hakkihalli", 33, 5003),
            new Person(6, "Ratnamma", "Venkatesh", "Hullenahalli", 39, 5006),
            new Person(7, "Manjamma", "Thimmanna", "Hakkihalli", 35, 5004),
            new Person(8, "Malligemma", "Ramanna", "Hullenahalli", 33, 5008),
            new Person(9, "Annapoorna", "Huchhanna", "Hakkihalli", 34, 5001),
            new Person(11, "Mallige", "Shivanna", "Gonisomanahalli", 25, 5011),
            new Person(12, "Sampige", "Ninge Gowda", "Halebeedu", 32, 5021),
            new Person(13, "Pooja", "Thote", "Gowrikoppalu", 23, 5031),
            new Person(14, "Prutwi", "Murti", "Eeshwarahalli", 25, 5041),
            new Person(15, "Shashikala", "Manjegowda", "Maachenahalli", 26, 5051),
            new Person(16, "Naagamma", "Thammanna", "kuruballi", 26, 5061),
            new Person(17, "Namrutha", "Laxmana", "kuruballi", 27, 5071),
            new Person(18, "shruti", "Soori", "javagal", 28, 5081),
            new Person(19, "laavanya", "Ananda", "Javagal", 29, 5091),
            new Person(20, "sunita", "Madhu", "kuruballi", 30, 5101),
            new Person(21, "pavitra", "Kempa", "Bantenahalli", 40, 5201),
            new Person(22, "prema", "Kariya", "Bantenahalli", 34, 5301),
            new Person(23, "deepti", "Jayanna", "Sampige marada koppalu", 46, 5401),
            new Person(24, "divya", "Guru", "sampige marada koppalu", 45, 5501),
            new Person(25, "archana", "dileep", "adagooru", 24, 5601),
            new Person(26, "ashwin", "Manu", "adagooru", 29, 5701),
            new Person(27, "siri", "Sidda", "turuvanuru", 18, 5801),
            new Person(28, "sheetal", "Darshan", "turuvanuru", 19, 5922),
            new Person(29, "vinuta", "Sudeep", "turuvanuru", 20, 5023),
            new Person(30, "laxmamma1", "RameGowda1", "Kallushettyhalli", 35, 5024),
            new Person(31, "laxmamma2", "RameGowda2", "Gowrikoppalu2", 35, 5025),
            new Person(32, "laxmamma3", "RameGowda3", "Gowrikoppalu3", 35, 5026),
            new Person(33, "laxmamma4", "RameGowda4", "Gowrikoppalu4", 35, 5027),
            new Person(34, "laxmamma5", "RameGowda5", "Gowrikoppalu5", 35, 5028),
            new Person(35, "laxmamma6", "RameGowda6", "Gowrikoppalu6", 35, 5029),
            new Person(36, "laxmamma7", "RameGowda7", "Gowrikoppalu7", 35, 5032),
            new Person(37, "laxmamma8", "RameGowda8", "Gowrikoppalu8", 35, 5033),
            new Person(38, "laxmamma9", "RameGowda9", "Gowrikoppalu9", 35, 5034),
            new Person(39, "laxmamma10", "RameGowda10", "Gowrikoppalu10", 35, 5035),
            new Person(40, "laxmamma11", "RameGowda11", "Gowrikoppalu11", 35, 5036),
            new Person(41, "laxmamma12", "RameGowda12", "Gowrikoppalu12", 35, 5037),
            new Person(42, "laxmamma13", "RameGowda13", "Gowrikoppalu13", 35, 5038),
    };

    public static Person[] getCityPeople() {
        int n = 1000;
        Person[] largeDataset = new Person[n];
        for (int i = 0; i < n; i++) {
            largeDataset[i] = new Person(i, "name" + i, "myHusband" + i, "myVillage" + i, (i + 18) % 20, 500 + i);
        }
        return largeDataset;
    }

    public int id;
    public String name;
    public String husbandName;
    public String villageName;
    public int age;
    public int ecNumber;

    public Person(int id, String name, String husandName, String villageName, int age, int ecNumber) {
        this.id = id;
        this.name = name;
        this.husbandName = husandName;
        this.villageName = villageName;
        this.age = age;
        this.ecNumber = ecNumber;
    }

    public boolean willFilterMatches(String filter) {
        return name.toLowerCase().contains(filter)
                || husbandName.toLowerCase().contains(filter)
                || villageName.toLowerCase().contains(filter)
                || String.valueOf(age).contains(filter)
                || String.valueOf(ecNumber).contains(filter);
    }
}

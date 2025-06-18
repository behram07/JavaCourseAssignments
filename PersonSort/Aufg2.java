import java.util.Arrays;
import java.util.Comparator;

public class Aufg2 {
    public record Person(String name, String vorname, int id){}

    public static void main(String[] args){
        Person[] people = {
                new Person("Yilmaz", "Sikcadagilan", 2),
                new Person("Sedat", "Sakmaur", 3),
                new Person("Hayri", "Topugugüzel", 1),
                new Person("Süleyman", "Sahlananat", 4)
        };

        Arrays.sort(people, Comparator.comparingInt(Person::id));
        System.out.println("ID'ye göre siralama: ");
        for(Person p : people)
            System.out.println(p.name() + " " + p.vorname() + " - " + p.id());

        Arrays.sort(people, Comparator
                .comparing(Person::name)
                .thenComparing(Person::vorname)
                .thenComparingInt(Person::id));
        System.out.println("\nAd, soyad ve ID'ye göre siralama: ");
        for(Person p : people)
            System.out.println(p.name() + " " + p.vorname() + " - " + p.id());
    }
}

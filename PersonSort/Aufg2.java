import java.util.Arrays;
import java.util.Comparator;

public class Aufg2 {
    public record Person(String name, String firstname, int id){}

    public static void main(String[] args){
        Person[] people = {
                new Person("Moretti", "Luca", 2),
                new Person("Keller", "Sophie", 3),
                new Person("González", "Mateo", 1),
                new Person("Johansson", "Emma", 4)
        };

        Arrays.sort(people, Comparator.comparingInt(Person::id));
        System.out.println("Sorting by ID: ");
        for(Person p : people)
            System.out.println(p.name() + " " + p.firstname() + " - " + p.id());

        Arrays.sort(people, Comparator
                .comparing(Person::name)
                .thenComparing(Person::firstname)
                .thenComparingInt(Person::id));
        System.out.println("\nSort by name, first name and ID:Sort by ID: ");
        for(Person p : people)
            System.out.println(p.name() + " " + p.firstname() + " - " + p.id());
    }
}

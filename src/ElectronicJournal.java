import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ElectronicJournal {
    public static void main(String[] args) {
        Group group1 = new Group("ІП-з21");
        Group group2 = new Group("ІП-з22");
        Group group3 = new Group("ІП-з23");

        group1.addStudent(new Student("Іван Петренко"));
        group1.addStudent(new Student("Марія Коваль"));
        group1.addStudent(new Student("Олексій Сидоров"));

        group2.addStudent(new Student("Анна Шевченко"));
        group2.addStudent(new Student("Дмитро Бойко"));
        group2.addStudent(new Student("Софія Мельник"));

        group3.addStudent(new Student("Андрій Кравець"));
        group3.addStudent(new Student("Олена Лисенко"));
        group3.addStudent(new Student("Максим Попов"));

        List<Group> allGroups = Arrays.asList(group1, group2, group3);

        Teacher lecturer = new Teacher("Лектор Григоренко", allGroups);
        Teacher assistant1 = new Teacher("Асистент Іваненко", allGroups);
        Teacher assistant2 = new Teacher("Асистент Петров", allGroups);
        Teacher assistant3 = new Teacher("Асистент Сидоренко", allGroups);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println("=== ПОЧАТОК РОБОТИ ЕЛЕКТРОННОГО ЖУРНАЛУ ===\n");

        executor.submit(lecturer);
        executor.submit(assistant1);
        executor.submit(assistant2);
        executor.submit(assistant3);

        executor.shutdown();

        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Програма була перервана");
        }

        System.out.println("\n=== ПІДСУМКИ ===");

        for (Group group : allGroups) {
            System.out.println("\nГрупа: " + group.getName());
            for (Student student : group.getStudents()) {
                System.out.println("  " + student.getName() + ":");
                System.out.println("    Оцінки: " + student.getGrades());
                System.out.println("    Середній бал: " +
                        String.format("%.2f", student.getAverageGrade()));
            }
        }
    }
}
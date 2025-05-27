import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

class Teacher implements Runnable {
    private String name;
    private List<Group> groups;
    private Random random;

    public Teacher(String name, List<Group> groups) {
        this.name = name;
        this.groups = groups;
        this.random = new SecureRandom();
    }

    public void run() {
        for (int week = 1; week <= 3; week++) {
            System.out.println(name + " виставляє оцінки (тиждень " + week + ")...");

            for (Group group : groups) {
                for (Student student : group.getStudents()) {
                    int grade = 60 + random.nextInt(41);
                    student.addGrade(grade);
                    System.out.println(name + " поставив " + grade + " балів студенту "
                            + student.getName() + " з групи " + group.getName());
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

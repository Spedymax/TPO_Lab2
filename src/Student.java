import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private List<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void addGrade(int grade) {
        if (grade >= 0 && grade <= 100) {
            grades.add(grade);
        }
    }

    public synchronized List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    public synchronized double getAverageGrade() {
        if (grades.isEmpty()) return 0;
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
}
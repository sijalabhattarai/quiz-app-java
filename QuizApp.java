import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    private static final String CSV_FILE = "C:\\BCA 3rd Sem\\Java\\questions.csv";
    private static final int MAX_ATTEMPTS = 5;

    private List<Question> questions;
    private int score;
    private Scanner scanner;

    public QuizApp() {
        questions = new ArrayList<>();
        score = 0;
        scanner = new Scanner(System.in);
    }

    public void start() {
        loadQuestionsFromCSV();

        if (questions.isEmpty()) {
            System.out.println("No questions loaded. Exiting quiz.");
            return;
        }

        System.out.println("Welcome to the Quiz!\n");
        for (Question question : questions) {
            presentQuestion(question);
            if (score < 0) {
                score = 0;
            }
        }

        System.out.println("\nQuiz completed!");
        System.out.println("Your final score is: " + score + "/" + questions.size());
    }

    private void loadQuestionsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) { 
                    String question = data[0].trim();
                    String option1 = data[1].trim();
                    String option2 = data[2].trim();
                    String option3 = data[3].trim();
                    String option4 = data[4].trim();
                    String correctAnswer = data[5].trim();
                    String explanation = data[6].trim();
                    questions.add(new Question(question, option1, option2, option3, option4, correctAnswer, explanation));
                } else {
                    System.err.println("Skipping invalid question format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + e.getMessage());
        }
    }

    private void presentQuestion(Question question) {
        System.out.println(question.getQuestion());
        System.out.println("Options:");
        System.out.println("1. " + question.getOption1());
        System.out.println("2. " + question.getOption2());
        System.out.println("3. " + question.getOption3());
        System.out.println("4. " + question.getOption4());
        System.out.print("Enter your answer (1-4): ");

        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                attempts++;
                continue;
            }

            String userAnswer = "";
            switch (choice) {
                case 1:
                    userAnswer = question.getOption1();
                    break;
                case 2:
                    userAnswer = question.getOption2();
                    break;
                case 3:
                    userAnswer = question.getOption3();
                    break;
                case 4:
                    userAnswer = question.getOption4();
                    break;
            }

            if (userAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
                System.out.println("Correct answer! " + question.getExplanation());
                score++;
                break;
            } else {
                System.out.println("Incorrect answer. Try again.");
                attempts++;
                if (attempts == MAX_ATTEMPTS) {
                    System.out.println("Out of attempts. The correct answer was: " + question.getCorrectAnswer());
                    score--;
                }
            }
        }
        System.out.println("Current score: " + score + "/" + questions.size() + "\n");
    }

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.start();
    }
}

class Question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private String explanation;

    public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer, String explanation) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }
}

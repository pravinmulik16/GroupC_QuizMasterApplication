package com.database.app;

import com.database.dao.AdminDAO;
import com.database.dao.QuestionDAO;
import com.database.dao.ScoreDAO;
import com.database.dao.StudentDAO;
import com.database.model.Question;
import com.database.model.Score;
import com.database.model.Student;
import com.database.validation.InputValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class QuizMasterApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();
    private static final QuestionDAO questionDAO = new QuestionDAO();
    private static final AdminDAO adminDAO = new AdminDAO();
    private static final ScoreDAO scoreDAO = new ScoreDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("=========================================");
            System.out.println("   WELCOME TO QUIZMASTER APPLICATION     ");
            System.out.println("=========================================");
            System.out.println("1. Student Portal");
            System.out.println("2. Admin Portal");
            System.out.println("3. Exit Application");
            System.out.print("Select your path (1-3): ");

            int choice = readIntSafe();
            switch (choice) {
                case 1:
                    showStudentMenu();
                    break;
                case 2:
                    showAdminMenu();
                    break;
                case 3:
                    System.out.println("\nThank you for using QuizMaster! Goodbye.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please pick a number from 1 to 3.");
            }
        }
    }


    private static void showStudentMenu() {
        while (true) {
            System.out.println("--- Student Portal Menu ---");
            System.out.println("1. Register New Account");
            System.out.println("2. Login & Start Java Quiz");
            System.out.println("3. View My Historic Score");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = readIntSafe();
            try {
                switch (choice) {
                    case 1:
                        handleStudentRegistration();
                        break;
                    case 2:
                        handleStudentLoginAndQuiz();
                        break;
                    case 3:
                        handleViewStudentScore();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid selection.");
                }
            } catch (SQLException e) {
                System.err.println("Database Error occurred: " + e.getMessage());
            }

            if (!promptContinue("Student Portal")) return;
        }
    }

    private static void handleStudentRegistration() throws SQLException {
        System.out.println("=== Student Registration Form ===");
        System.out.print("Enter First Name: "); String fName = scanner.nextLine();
        System.out.print("Enter Last Name: "); String lName = scanner.nextLine();
        System.out.print("Enter Username: "); String username = scanner.nextLine();
        System.out.print("Enter Password (Min 6 chars): "); String password = scanner.nextLine();
        System.out.print("Enter City: "); String city = scanner.nextLine();
        System.out.print("Enter Email ID (abc@xyz.com): "); String email = scanner.nextLine();
        System.out.print("Enter Mobile Number (10 digits): "); String mobile = scanner.nextLine();


        if (InputValidator.isEmpty(fName) || InputValidator.isEmpty(lName) || InputValidator.isEmpty(username)
                || InputValidator.isEmpty(password) || InputValidator.isEmpty(city)
                || InputValidator.isEmpty(email) || InputValidator.isEmpty(mobile)) {
            System.out.println("Error: Fields cannot be left empty during registration!");
            return;
        }


        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Registration Failed: Email formatting layout is invalid!");
            return;
        }
        if (!InputValidator.isValidPassword(password)) {
            System.out.println("Registration Failed: Password must contain at least 6 characters!");
            return;
        }
        if (!InputValidator.isValidMobile(mobile)) {
            System.out.println("Registration Failed: Mobile must contain exactly 10 digital numbers!");
            return;
        }


        if (studentDAO.isUsernameDuplicate(username)) {
            System.out.println("Conflict Error: The username '" + username + "' is already taken!");
            return;
        }

        Student student = new Student(fName, lName, username, password, city, email, mobile);
        if (studentDAO.registerStudent(student)) {
            System.out.println("Registration completed successfully! You can login now.");
        }
    }

    private static void handleStudentLoginAndQuiz() throws SQLException {
        System.out.println("\n=== Student Authentication Portal ===");
        System.out.print("Enter Username: "); String username = scanner.nextLine();
        System.out.print("Enter Password: "); String password = scanner.nextLine();

        int studentId = studentDAO.loginStudent(username, password);
        if (studentId == -1) {
            System.out.println("Error: Invalid credentials verification matching parameters failed.");
            return;
        }

        System.out.println("\nAuthentication Success! Fetching questions data structures framework...");
        List<Question> questions = questionDAO.getRandomQuizQuestions();

        if (questions.isEmpty()) {
            System.out.println("Notice: The quiz bank repository contains zero active entries. Admin setup required.");
            return;
        }

        System.out.println("\n=== Java Quiz ===");
        System.out.println("You will be asked " + questions.size() + " multiple-choice questions.");
        System.out.println("Each question has 4 options. Enter your choice (1-4).");

        int correctAnswersCount = 0;
        int questionNumber = 1;

        for (Question q : questions) {
            System.out.println("\nQuestion " + questionNumber + ": " + q.getQuestionText());
            System.out.println("1. " + q.getOption1());
            System.out.println("2. " + q.getOption2());
            System.out.println("3. " + q.getOption3());
            System.out.println("4. " + q.getOption4());

            int studentResponse = 0;
            while (true) {
                System.out.print("Enter your answer (1-4): ");
                studentResponse = readIntSafe();
                if (studentResponse >= 1 && studentResponse <= 4) {
                    break;
                }
                System.out.println("Invalid selection. Choices are strictly limited to values between 1 and 4.");
            }

            if (studentResponse == q.getCorrectOption()) {
                correctAnswersCount++;
            }
            questionNumber++;
        }


        int wrongAnswersCount = questions.size() - correctAnswersCount;
        String finalGrade = scoreDAO.calculateGrade(correctAnswersCount);
        String feedback = scoreDAO.getFeedbackMessage(finalGrade);

        System.out.println("=== Display Quiz Summary ===");
        System.out.println("Total Questions: " + questions.size());
        System.out.println("Correct Answers: " + correctAnswersCount);
        System.out.println("Wrong Answers  : " + wrongAnswersCount);
        System.out.println("Your Final Score: " + correctAnswersCount + " / " + questions.size());
        System.out.println("Your Grade      : " + finalGrade + " (" + feedback + ")");

        Score scoreObj = new Score(studentId, correctAnswersCount, finalGrade);
        if (scoreDAO.saveStudentScore(scoreObj)) {
            System.out.println("Performance scorecard committed to data storage engine files successfully.");
        }
    }

    private static void handleViewStudentScore() throws SQLException {
        System.out.println("\n=== View Quiz Result ===");
        System.out.print("Enter Username: "); String username = scanner.nextLine();
        System.out.print("Enter Password: "); String password = scanner.nextLine();
        studentDAO.viewStudentScore(username, password);
    }


    private static void showAdminMenu() {
        while (true) {
            System.out.println("--- Admin Management Operations ---");
            System.out.println("1. Add New Quiz Question");
            System.out.println("2. Edit Existing Question Record");
            System.out.println("3. Delete Target Question Entry");
            System.out.println("4. View All Registered Student Scores");
            System.out.println("5. Search Student Score Profile by ID");
            System.out.println("6. Display Overall Top Scorer Results");
            System.out.println("7. Return to Root Setup Menu");
            System.out.print("Select administrative process choice: ");

            int choice = readIntSafe();
            try {
                switch (choice) {
                    case 1:
                        handleAdminAddQuestion();
                        break;
                    case 2:
                        handleAdminEditQuestion();
                        break;
                    case 3:
                        handleAdminDeleteQuestion();
                        break;
                    case 4:
                        adminDAO.viewAllStudentScores();
                        break;
                    case 5:
                        System.out.print("Enter target student lookup ID: ");
                        int lookupId = readIntSafe();
                        adminDAO.fetchScoreByStudentId(lookupId);
                        break;
                    case 6:
                        adminDAO.viewTopScorer();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid selection matching range filters.");
                }
            } catch (SQLException e) {
                System.err.println("Admin Database Transaction Aborted: " + e.getMessage());
            }
            if (!promptContinue("Admin Portal")) return;
        }
    }

    private static void handleAdminAddQuestion() throws SQLException {
        System.out.println("\n=== Add New Question ===");
        System.out.print("Enter Question Text: ");
        String text = scanner.nextLine();
        System.out.print("Enter Option 1: ");
        String o1 = scanner.nextLine();
        System.out.print("Enter Option 2: ");
        String o2 = scanner.nextLine();
        System.out.print("Enter Option 3: ");
        String o3 = scanner.nextLine();
        System.out.print("Enter Option 4: ");
        String o4 = scanner.nextLine();
        System.out.print("Enter Correct Option Number (1-4): ");
        int correctNum = readIntSafe();
        if (correctNum < 1 || correctNum > 4) {
            System.out.println("Failed: The valid option assignment configuration parameter mapping is limited to [1-4].");
            return;
        }
        Question question = new Question(text, o1, o2, o3, o4, correctNum);
        if (adminDAO.addQuestion(question)) {
            System.out.println("New problem index successfully assigned into schema parameters configuration lists.");
        }
    }

    private static void handleAdminEditQuestion() throws SQLException {
        System.out.print("\nEnter Question ID to Edit: ");
        int qId = readIntSafe();
        System.out.print("Enter New Question Text: ");
        String text = scanner.nextLine();
        System.out.print("Enter New Option 1: ");
        String o1 = scanner.nextLine();
        System.out.print("Enter New Option 2: ");
        String o2 = scanner.nextLine();
        System.out.print("Enter New Option 3: ");
        String o3 = scanner.nextLine();
        System.out.print("Enter New Option 4: ");
        String o4 = scanner.nextLine();
        System.out.print("Enter New Correct Option Number (1-4): ");
        int correctNum = readIntSafe();
        if (correctNum < 1 || correctNum > 4) {
            System.out.println("Failed: Correction aborted. Value index mapped out of range limits bounds.");
            return;
        }
        Question question = new Question(text, o1, o2, o3, o4, correctNum);
        question.setId(qId);
        if (adminDAO.editQuestion(question)) {
            System.out.println("Question modification committed safely into live storage array frames.");
        } else {
            System.out.println("Tracking index code error: Provided structural tracking key code ID was not found.");
        }
    }

    private static void handleAdminDeleteQuestion() throws SQLException {
        System.out.print("\nEnter Question ID to Delete: ");
        int qId = readIntSafe();
        if (adminDAO.deleteQuestion(qId)) {
            System.out.println("Target transaction executed cleanly. Row removed from system tracking files layout.");
        } else {
            System.out.println("Target lookup parameters array parsing failed: Question matching index ID could not be isolated.");
        }
    }


    private static int readIntSafe() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (Exception e) {
                System.out.print("Input error! Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }

    private static boolean promptContinue(String portalName) {

        System.out.print("\nDo you want to continue in " + portalName + "? (Y/N): ");
        String selection = scanner.nextLine().trim();
        return selection.equalsIgnoreCase("Y");
    }
}


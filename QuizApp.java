import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int timeLeft;

    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JLabel timerLabel;
    private Timer timer;

    public QuizApp() {
        super("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initializeQuestions();
        initializeComponents();

        currentQuestionIndex = 0;
        score = 0;
        showNextQuestion();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", 
                     new String[]{"London", "Berlin", "Paris", "Madrid"}, "Paris"));
        questions.add(new Question("Which planet is known as the Red Planet?", 
                     new String[]{"Mars", "Venus", "Jupiter", "Saturn"}, "Mars"));
        questions.add(new Question("What is 2 + 2?", 
                     new String[]{"3", "4", "5", "6"}, "4"));
    }

    private void initializeComponents() {
        questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(questionLabel);

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            int finalI = i;
            optionButtons[i].addActionListener(e -> checkAnswer(finalI));
            add(optionButtons[i]);
        }

        timerLabel = new JLabel();
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(timerLabel);

        timer = new Timer(1000, e -> updateTimer());
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText("<html><body style='width: 300px; text-align: center'>" + question.getQuestion() + "</body></html>");
            
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(question.getOptions()[i]);
            }

            timeLeft = 10;
            updateTimerLabel();
            timer.start();
        } else {
            showResult();
        }
    }

    private void updateTimer() {
        timeLeft--;
        updateTimerLabel();
        if (timeLeft == 0) {
            timer.stop();
            checkAnswer(-1);
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time left: " + timeLeft + " seconds");
    }

    private void checkAnswer(int selectedOption) {
        timer.stop();
        Question question = questions.get(currentQuestionIndex);
        if (selectedOption >= 0 && question.getOptions()[selectedOption].equals(question.getCorrectAnswer())) {
            score++;
        }
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, 
            "Quiz completed!\nYour score: " + score + "/" + questions.size(), 
            "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}
package hkcc.ccn3165.assignment.maths;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int firstNumber = 0, secondNumber = 0;
    String mOperator;
    public static TextView answer;
    public static byte questionNumber = 0;
    public static Intent[] questionIntent = new Intent[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.question);
        question.setText(question());

        Button submit = findViewById(R.id.submit);
        answer = findViewById(R.id.answer);

        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                long userAnswer;
                int correctAnswer = 0;
                if (input.getText().toString().equals("")) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Try to answer")
                            .setMessage("You haven't entered anything!\n" +
                                    "Try to answer this question!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                }
                            })
                            .show();
                    userAnswer = -1;
                } else {
                    userAnswer = Long.parseLong(input.getText().toString());
                }
                switch (mOperator) {
                    case "+":
                        correctAnswer = firstNumber + secondNumber;
                        break;

                    case "-":
                        correctAnswer = firstNumber - secondNumber;
                        break;

                    case "×":
                        correctAnswer = firstNumber * secondNumber;
                        break;

                    case "÷":
                        correctAnswer = firstNumber / secondNumber;
                        break;
                }

                if (userAnswer == correctAnswer) {
                    answer.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                questionIntent[questionNumber + 1] = new Intent(view.getContext(), MainActivity.class);
                questionNumber += 1;
                startActivityForResult(questionIntent[questionNumber], questionNumber);
            }
        });
    }

    private String question() {
        Random myRandom = new Random();
        String mQuestion = "Question: ", operator[] = {"+", "-", "×", "÷"};

        mOperator = operator[myRandom.nextInt(4)];
        switch (mOperator) {
            case "+":
                firstNumber = 1 + myRandom.nextInt(99);
                secondNumber = 1 + myRandom.nextInt(99);
                break;

            case "-":
                firstNumber = 1 + myRandom.nextInt(99);
                do {
                    secondNumber = myRandom.nextInt(100);
                } while (firstNumber < secondNumber);
                break;

            case "×":
                firstNumber = 1 + myRandom.nextInt(20);
                secondNumber = 1 + myRandom.nextInt(20);
                break;

            case "÷":
                firstNumber = 1 + myRandom.nextInt(99);
                do {
                    secondNumber = myRandom.nextInt(100);
                } while (secondNumber == 0 || firstNumber % secondNumber != 0);
                break;
        }
        mQuestion = mQuestion + firstNumber + mOperator + secondNumber + " = ?";
        return mQuestion;
    }
}

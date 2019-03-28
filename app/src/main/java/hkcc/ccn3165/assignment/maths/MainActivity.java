package hkcc.ccn3165.assignment.maths;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.question);
        question.setText(question());
    }

    private String question() {
        Random myRandom = new Random();
        String mQuestion = "Question: ", mOperator, operator[] = {"+", "-", "×", "÷"};
        int firstNumber = 0, secondNumber = 0;

        mOperator = operator[myRandom.nextInt(4)];
        switch (mOperator) {
            case "+":
                firstNumber = 1 + myRandom.nextInt(99);
                secondNumber = 1 + myRandom.nextInt(99);

            case "-":
                firstNumber = 1 + myRandom.nextInt(99);
        }
        mQuestion = mQuestion + firstNumber + mOperator + secondNumber;
        return mQuestion;
    }
}

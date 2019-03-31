package hkcc.ccn3165.assignment.maths;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int firstNumber = 0, secondNumber = 0;
    String mOperator;
    public static TextView answer;
    public static EditText input;
    boolean isAnswered = false;
    public static AlertDialog skip;
    public static TextToSpeech tts;
    public static byte questionIndex = 0, score = 0;
    public static Intent[] questionIntent = new Intent[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 建立 TTS
        createLanguageTTS();

        TextView question = findViewById(R.id.question);
        question.setText(question());

        Button submit = findViewById(R.id.submit);
        answer = findViewById(R.id.answer);
        input = findViewById(R.id.input);

        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                long userAnswer;
                int correctAnswer = 0;
                if (input.getText().toString().equals("")) {
                    String mNotEntered = "You haven't entered anything!", mTry = "Try to answer this question!";
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Try to answer")
                            .setMessage(mNotEntered + "\n" + mTry)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                }
                            })
                            .show();
                    //【英文】發音
                    // tts.speak(mNotEntered + mTry, TextToSpeech.QUEUE_FLUSH, null);
                    userAnswer = -1;
                } else {
                    userAnswer = Long.parseLong(input.getText().toString());
                    input.setEnabled(false);
                    isAnswered = true;
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

                String mCorrectness, mCorrectOutput = "Correct answer is " + correctAnswer, mCheer;
                if (userAnswer == correctAnswer) {
                    score++;
                    mCorrectness = "You are right!";
                    mCheer = "Great job!";
                    answer.setTextColor(getResources().getColor(R.color.colorPrimary));
                    answer.setText(mCorrectness + "\n" +
                            mCheer);
                    tts.speak(mCorrectness + mCheer, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    mCorrectness = "That’s not right.";
                    mCheer = "You can do better next question!";
                    answer.setTextColor(Color.RED);
                    answer.setText(mCorrectness + "\n" +
                            mCorrectOutput + "\n" +
                            mCheer);
                }
            }
        });

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (!isAnswered) {
                    String mNotAnswered = "You haven't answered this question!", mSkipQuestion = "Do you want to skip?";
                    AlertDialog.Builder skipQuestionBuilder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Try to answer")
                            .setMessage(mNotAnswered + "\n" +
                                    mSkipQuestion)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                }
                            })
                            .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    // input.setEnabled(false);
                                    nextQuestion(view);
                                }
                            });
                    skip = skipQuestionBuilder.create();
                    skip.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            skip.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                        }
                    });
                    skip.show();
                    // tts.speak(mNotAnswered + mSkipQuestion, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    nextQuestion(view);
                }
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

    private void nextQuestion(View view) {
        if (questionIndex < 9) {
            questionIntent[questionIndex + 1] = new Intent(view.getContext(), MainActivity.class);
            questionIndex += 1;
            startActivityForResult(questionIntent[questionIndex], questionIndex);
        } else {
            Intent mSummary = new Intent(view.getContext(), Summary.class);
            startActivityForResult(mSummary, ++questionIndex);
        }
    }

    // https://tomkuo139.blogspot.com/2016/03/android-tts-api-text-to-speech.html
    protected void onDestroy() {
        // 釋放 TTS
        if (tts != null) tts.shutdown();
        super.onDestroy();
    }

    // https://tomkuo139.blogspot.com/2016/03/android-tts-api-text-to-speech.html
    private void createLanguageTTS() {
        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int arg0) {
                    // TTS 初始化成功
                    if (arg0 == TextToSpeech.SUCCESS) {
                        // 目前指定的【語系+國家】TTS, 已下載離線語音檔, 可以離線發音
                        if (tts.isLanguageAvailable(Locale.UK) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                            tts.setLanguage(Locale.UK); // 不要用 Locale.ENGLISH, 會預設用英文(印度)
                        } else if (tts.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                            tts.setLanguage(Locale.US);
                        }
                    }
                }
            }
            );
        }
    }
}

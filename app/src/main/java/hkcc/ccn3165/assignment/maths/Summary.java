package hkcc.ccn3165.assignment.maths;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static hkcc.ccn3165.assignment.maths.MainActivity.tts;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView mScore = findViewById(R.id.score), mWrong = findViewById(R.id.wrong);
        String summaryText = "You have answered ", correct = " questions correctly.", incorrect = " questions wrongly.";
        mScore.setText(summaryText + MainActivity.score + correct);
        mWrong.setText(summaryText + (10 - MainActivity.score) + incorrect);
        tts.speak(summaryText + MainActivity.score + correct +
                summaryText + (10 - MainActivity.score) + incorrect, TextToSpeech.QUEUE_FLUSH, null);
    }
}

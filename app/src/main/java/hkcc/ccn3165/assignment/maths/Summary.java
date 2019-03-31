package hkcc.ccn3165.assignment.maths;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView mScore = findViewById(R.id.score);
        String summaryText = "You have answered ", correct = " questions correctly.", incorrect = " questions wrongly.";
        mScore.setText(summaryText + MainActivity.score + correct);
    }
}

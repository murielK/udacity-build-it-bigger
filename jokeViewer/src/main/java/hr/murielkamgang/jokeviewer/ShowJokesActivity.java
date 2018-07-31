package hr.murielkamgang.jokeviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class ShowJokesActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE_KEY = "EXTRA_JOKE_KEY";

    public static void show(Context context, String joke) {
        final Intent intent = new Intent(context, ShowJokesActivity.class);
        intent.putExtra(EXTRA_JOKE_KEY, joke);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_joke);

        if (getIntent().hasExtra(EXTRA_JOKE_KEY)) {
            final TextView textView = findViewById(R.id.textJoke);
            textView.setText(getIntent().getStringExtra(EXTRA_JOKE_KEY));
        } else {
            Toast.makeText(this, R.string.error_msg_joke_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}

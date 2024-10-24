package com.example.statemanagementextended;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private Button buttonIncrement;
    private Switch sSwitch;
    private EditText editText;
    private CheckBox checkBox;
    private TextView textViewCount;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean("isDarkTheme", false);

        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonIncrement = findViewById(R.id.buttonIncrement);
        sSwitch = findViewById(R.id.Switch);
        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.checkBox);
        textViewCount = findViewById(R.id.textViewCount);

        sSwitch.setChecked(isDarkTheme);

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count");
            String inputText = savedInstanceState.getString("inputText");
            boolean isChecked = savedInstanceState.getBoolean("isChecked");

            editText.setText(inputText);
            checkBox.setChecked(isChecked);
        }

        updateCountText();

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                updateCountText();
            }
        });

        sSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isDarkTheme", isChecked);
            editor.apply();

            recreate();
        });
    }

    private void updateCountText() {
        textViewCount.setText("Licznik: " + count);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
        outState.putString("inputText", editText.getText().toString());
        outState.putBoolean("isChecked", checkBox.isChecked());
    }
}
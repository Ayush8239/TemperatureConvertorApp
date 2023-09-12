package com.example.temperatureconvertor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editInput;
    private TextView textResult;
    private TextView textResultType;
    private TextView textType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editInput = findViewById(R.id.editInput);
        textResult = findViewById(R.id.textResult);
        textResultType = findViewById(R.id.textResultType);
        textType = findViewById(R.id.textType);

        // Add TextChangedListener to the input field
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Handle input text change
                calculateTemperature();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing here
            }
        });
        textType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTemperatureTypeDialog();
            }
        });
    }
    private void showTemperatureTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Temperature Type")
                .setItems(R.array.temperature_types, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selection (which is either 0 for Fahrenheit or 1 for Celsius)
                        if (which == 0) {
                            textType.setText(getString(R.string.fahrenheit));
                        } else {
                            textType.setText(getString(R.string.celsius));
                        }
                        calculateTemperature(); // Recalculate when the type changes
                    }
                });
        builder.create().show();
    }


    // Calculate and update the temperature conversion
    private void calculateTemperature() {
        // Get the input value
        String inputValue = editInput.getText().toString().trim();

        if (!inputValue.isEmpty()) {
            try {
                double inputTemperature = Double.parseDouble(inputValue);
                double resultTemperature;

                // Check which temperature type is selected (Fahrenheit or Celsius)
                if (textType.getText().toString().equalsIgnoreCase(getString(R.string.fahrenheit))) {
                    // Convert Fahrenheit to Celsius
                    resultTemperature = (inputTemperature - 32) * 5 / 9;
                    textResultType.setText(getString(R.string.celsius));
                } else {
                    // Convert Celsius to Fahrenheit
                    resultTemperature = (inputTemperature * 9 / 5) + 32;
                    textResultType.setText(getString(R.string.fahrenheit));
                }

                // Update the result TextView
                textResult.setText(String.format("%.2f", resultTemperature));

            } catch (NumberFormatException e) {
                // Handle invalid input
                textResult.setText(getString(R.string.invalid_input));
                textResultType.setText("");
            }
        } else {
            // Clear the result if input is empty
            textResult.setText("");
            textResultType.setText("");
        }
    }

}

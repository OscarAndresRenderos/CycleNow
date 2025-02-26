package com.example.cyclenow.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclenow.R;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText ageEditText; // For date of birth
    private Spinner genderSpinner; // For gender selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize fields
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        ageEditText = findViewById(R.id.ageEditText); // Date of birth field
        genderSpinner = findViewById(R.id.genderSpinner); // Gender dropdown
        Button registerButton = findViewById(R.id.registerButton);

        // Set up the gender spinner
        setupGenderSpinner();

        // Set up the date picker
        setupDatePicker();

        // Handle registration button click
        registerButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String dateOfBirth = ageEditText.getText().toString();
            String gender = genderSpinner.getSelectedItem().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Handle registration logic (e.g., send data to backend)
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                finish(); // Close the registration activity
            }
        });
    }

    private void setupGenderSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);
    }

    private void setupDatePicker() {
        // Make the EditText non-focusable and clickable
        ageEditText.setFocusable(false);
        ageEditText.setClickable(true);

        // Set an OnClickListener to show the DatePickerDialog
        ageEditText.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    // Set the selected date to the EditText using a resource string with placeholders
                    String date = getString(R.string.date_format, dayOfMonth, month1 + 1, year1);
                    ageEditText.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }
}
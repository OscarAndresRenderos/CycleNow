package com.example.cyclenow.ui.home;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclenow.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
        Calendar now = Calendar.getInstance();

        // Create and show the Material DatePickerDialog
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this, // Use `this` because the activity implements OnDateSetListener
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );

        // Set the minimum and maximum dates (optional)
        Calendar minDate = Calendar.getInstance();
        minDate.set(1900, Calendar.JANUARY, 1); // Minimum date: January 1, 1900
        dpd.setMinDate(minDate);

        Calendar maxDate = Calendar.getInstance();
        dpd.setMaxDate(maxDate); // Maximum date: today

        // Show the dialog
        dpd.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Set the selected date to the EditText using a resource string with placeholders
        String date = getString(R.string.date_format, dayOfMonth, monthOfYear + 1, year);
        ageEditText.setText(date);
    }
}
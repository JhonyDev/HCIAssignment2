package com.app.hciassignment2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText etDob;
    EditText etName;
    EditText etFName;
    EditText etAddress;
    EditText etPhoneNumber;
    EditText etMarks;

    DatePickerDialog datePickerDialog;
    Timer timer;

    int seconds = 0;
    int numberOfUsers = 0;
    int eachSeconds = 0;

    public boolean validEt(EditText etUserName, String strEtUserName) {
        if (strEtUserName.isEmpty()) {
            etUserName.setError("Field Empty");
            etUserName.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countTime();
            }
        }, 0, 1000);

        datePickerDialog = new DatePickerDialog(this);

        etDob = findViewById(R.id.et_dob);


        etName = findViewById(R.id.et_name);
        etFName = findViewById(R.id.et_f_name);
        etAddress = findViewById(R.id.et_address);
        etPhoneNumber = findViewById(R.id.et_phone);
        etMarks = findViewById(R.id.et_marks);

        etName.setOnFocusChangeListener((view, b) -> initTimer(b));
        etFName.setOnFocusChangeListener((view, b) -> initTimer(b));
        etAddress.setOnFocusChangeListener((view, b) -> initTimer(b));
        etPhoneNumber.setOnFocusChangeListener((view, b) -> initTimer(b));
        etMarks.setOnFocusChangeListener((view, b) -> initTimer(b));

        datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            String dob = i2 + " - " + i1 + " - " + i;
            etDob.setText(dob);
        });

    }

    private void initTimer(boolean isInFocus) {
        if (isInFocus) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    countTime();
                }
            }, 0, 1000);
        } else {
            timer.cancel();
            Toast.makeText(this, "Time Taken = " + eachSeconds + " seconds", Toast.LENGTH_SHORT).show();
            eachSeconds = 0;
        }

    }

    private void countTime() {
        seconds++;
        eachSeconds++;
    }

    public void showDatePicker(View view) {
        datePickerDialog.show();
    }

    public void submit(View view) {
        validate();
    }

    private void validate() {
        numberOfUsers++;
        int tasksFailed = 0;
        if (!validEt(etName, etName.getText().toString()))
            tasksFailed++;
        if (!validEt(etFName, etFName.getText().toString()))
            tasksFailed++;
        if (!validEt(etMarks, etMarks.getText().toString()))
            tasksFailed++;
        if (!validEt(etAddress, etAddress.getText().toString()))
            tasksFailed++;
        if (!validEt(etPhoneNumber, etPhoneNumber.getText().toString()))
            tasksFailed++;
        if (!validEt(etDob, etDob.getText().toString()))
            tasksFailed++;

        double efficiency = getEfficiency(tasksFailed);
        double effectiveness = getEffectiveness(tasksFailed);
        Toast.makeText(this, "Tasks Failed : " + tasksFailed, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Time Taken : " + seconds, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Efficiency : " + efficiency, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Effectiveness : " + effectiveness, Toast.LENGTH_SHORT).show();
        seconds = 0;
    }

    private double getEffectiveness(int totalFailed) {
        return ((double) totalFailed / 6) * 100;
    }

    private double getEfficiency(int totalFailed) {
        double numerator = 0.0;
        for (int i = 0; i < numberOfUsers; i++)
            numerator += (6 - (double) totalFailed / seconds);
        return numerator / 6 * numberOfUsers;
    }

}

package com.radjapov.springclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.radjapov.springclient.model.Employee;
import com.radjapov.springclient.retrofit.EmployeeApi;
import com.radjapov.springclient.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditTextName = findViewById(R.id.form_textFieldName);
        TextInputEditText inputEditTextBranch = findViewById(R.id.form_textFieldBranch);
        TextInputEditText inputEditTextLocation = findViewById(R.id.form_textFieldLocation);
        MaterialButton buttonSave = findViewById(R.id.form_buttonSave);

        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);

        buttonSave.setOnClickListener(view -> {
            String name = String.valueOf(inputEditTextName.getText());
            String branch = String.valueOf(inputEditTextBranch.getText());
            String location = String.valueOf(inputEditTextLocation.getText());

            Employee employee = new Employee();
            employee.setName(name);
            employee.setBranch(branch);
            employee.setLocation(location);

            employeeApi.save(employee)
                    .enqueue(new Callback<Employee>() {
                        @Override
                        public void onResponse(Call<Employee> call, Response<Employee> response) {
                            Toast.makeText(EmployeeForm.this, "Save successful!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Employee> call, Throwable t) {
                            Toast.makeText(EmployeeForm.this, "Save failed!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });

        });
    }
}
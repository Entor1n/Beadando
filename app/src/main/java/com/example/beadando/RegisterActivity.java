package com.example.beadando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordNameEditText;
    EditText passwordConfirmationNameEditText;
    EditText phoneEditText;
    Spinner spinner;
    EditText addressEditText;
    RadioGroup accountTypeGroup;

    private SharedPreferences preferences;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

       if (secret_key != 99){
           finish();
       }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordNameEditText = findViewById(R.id.passwordEditText);
        passwordConfirmationNameEditText = findViewById(R.id.passwordAgainEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        spinner = findViewById(R.id.phoneSpinner);
        addressEditText = findViewById(R.id.addressEditText);
        accountTypeGroup = findViewById(R.id.accountTypeGroup);
        accountTypeGroup.check(R.id.customerRadioButton);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String username = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(username);
        passwordNameEditText.setText(password);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.phone_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        myAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = passwordNameEditText.getText().toString();
        String passwordConfirmation = passwordConfirmationNameEditText.getText().toString();

        if (!password.equals(passwordConfirmation)) {
            Log.e(LOG_TAG,"passwords don't match");
        }

        String phoneNumber = phoneEditText.getText().toString();
        String phoneType = spinner.getSelectedItem().toString();
        String address = addressEditText.getText().toString();

        int checkedID = accountTypeGroup.getCheckedRadioButtonId();
        RadioButton radioButton = accountTypeGroup.findViewById(checkedID);
        String accountType = radioButton.getText().toString();

        Log.i(LOG_TAG, "Registered: " + userName + ", email: " + email);

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(LOG_TAG, "User created successfully");
                            startShopping();
                        } else{
                            Log.d(LOG_TAG, "User creation failed");
                            Toast.makeText(RegisterActivity.this,
                                    "User creation failed " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void cancel(View view) {
        finish();
    }

    private void startShopping(/* registered user data */){
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selectedItem);
        // TODO
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }
}
package com.example.evanlee.parkingbnb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;


public class signup_activity extends AppCompatActivity {
    private static final String TAG = "signupactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        final Button signupButton = (Button) findViewById(R.id.btn_signup);
        signupButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                signUp();
            }

        });
    }

    public void signUp(){
        Log.d(TAG, "Sign Up");

        // Validate stuff
        if(!validate()){
            failedSignUp();
            final Button signupButton = (Button) findViewById(R.id.btn_signup);
            signupButton.setEnabled(true);
            return;
        }

        final Button signupButton = (Button) findViewById(R.id.btn_signup);
        signupButton.setEnabled(false);

        final ProgressDialog signup_proDi = new ProgressDialog(this, R.style.AppTheme);
        signup_proDi.setIndeterminate(true);
        signup_proDi.setMessage("Creating Account.. ");
        signup_proDi.show();

        // TODO: implement Azure signup and send feedback

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        signup_proDi.dismiss();
                    }
                }, 3000);

        Intent menuIntent = new Intent(this, homepage.class);
        startActivity(menuIntent);

        }

        public void onSignupSuccess(){
            final Button signupButton = (Button) findViewById(R.id.btn_signup);
            signupButton.setEnabled(true);
            setResult(RESULT_OK, null);
            finish();
        }

        public void onSignupFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            final Button signupButton = (Button) findViewById(R.id.btn_signup);
            signupButton.setEnabled(true);
        }


    public void failedSignUp(){
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_LONG).show();
        final Button signupButton = (Button) findViewById(R.id.btn_signup);
        signupButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid = true;
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");

        final TextView _nameText = (TextView) findViewById(R.id.input_name);
        final TextView _emailText = (TextView) findViewById(R.id.input_email);
        final TextView _passwordText = (TextView) findViewById(R.id.input_password);


        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || p.matcher(password).find()) {
            _passwordText.setError("greater than 8 characters of alphanumeric");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}

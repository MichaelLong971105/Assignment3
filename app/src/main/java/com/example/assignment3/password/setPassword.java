package com.example.assignment3.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment3.R;

public class setPassword extends AppCompatActivity {

    EditText pass1, pass2, hints;
    Button confirmBtn;
    String correctPassword, hint;
    LockApplication lockApplication;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        lockApplication = (LockApplication) getApplication();
        pass1 = findViewById(R.id.set_NewPassword);
        pass2 = findViewById(R.id.confirm_SetNewPassword);
        hints = findViewById(R.id.set_hints);
        confirmBtn = findViewById(R.id.btnSetConfirm);
        sp = getSharedPreferences("Password", MODE_PRIVATE);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!pass1.getText().toString().equals("")) && (pass2.getText().toString().equals(pass1.getText().toString()))){
                    correctPassword = pass2.getText().toString();
                    if(!hints.getText().toString().equals("")){
                        hint = hints.getText().toString();
                    } else {
                        hint = "";
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", correctPassword);
                    editor.putString("hints", hint);
                    editor.putBoolean("havePassword", true);
                    editor.apply();
                    Intent goLock = new Intent(setPassword.this, LockActivity.class);
                    startActivity(goLock);
                } else {
                    Toast.makeText(getApplicationContext(),"something worong, try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

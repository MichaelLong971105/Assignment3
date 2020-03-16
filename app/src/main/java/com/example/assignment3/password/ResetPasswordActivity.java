package com.example.assignment3.password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment3.R;
import com.example.assignment3.main.MainActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    LockApplication lockApplication;
    EditText oldPass, newPass1, newPass2, hints;
    Button confirmBtn, backBtn;
    String oldPassword, newPassword, hint, test;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        lockApplication = (LockApplication) getApplication();
        oldPass = findViewById(R.id.old_Password);
        newPass1 = findViewById(R.id.reset_NewPassword);
        newPass2 = findViewById(R.id.confirm_ResetNewPassword);
        hints = findViewById(R.id.reset_hints);
        confirmBtn = findViewById(R.id.btnResetConfirm);
        backBtn = findViewById(R.id.btnBack);
        sp = getSharedPreferences("Password", MODE_PRIVATE);
        oldPassword = sp.getString("password", "");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(ResetPasswordActivity.this, MainActivity.class);
                startActivity(goHome);
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((oldPass.getText().toString().equals(oldPassword)) && (newPass2.getText().toString().equals(newPass1.getText().toString()))) {
                    newPassword = newPass1.getText().toString();
                    if (!hints.getText().toString().equals("")) {
                        hint = hints.getText().toString();
                    } else {
                        hint = "";
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", newPassword);
                    editor.putString("hints", hint);
                    editor.putBoolean("havePassword", true);
                    editor.apply();
                    test = sp.getString("password", "");
                    lockApplication.setLock(true);
                    Intent goHome = new Intent(ResetPasswordActivity.this, MainActivity.class);
                    startActivity(goHome);
                    Toast.makeText(getApplicationContext(), "Password changed successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (!oldPass.getText().toString().equals(oldPassword)) {
                    Toast.makeText(getApplicationContext(), "Incorrect old password.", Toast.LENGTH_SHORT).show();
                } else if (!newPass2.getText().toString().equals(newPass1.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter the same new password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(ResetPasswordActivity.this,"Press again to exit",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

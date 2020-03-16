package com.example.assignment3.password;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.main.MainActivity;
import com.example.assignment3.R;

public class LockActivity extends AppCompatActivity {

    LockApplication lockApplication;
    EditText passText;
    TextView hints;
    Button btnConfirm;
    String password, correctPassword, hint;
    SharedPreferences sp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        btnConfirm = findViewById(R.id.btnConfirm);
        passText = findViewById(R.id.enteredPassword);
        hints = findViewById(R.id.text_hint);

        sp = getSharedPreferences("Password", MODE_PRIVATE);
        correctPassword = sp.getString("password", "");
        hint = sp.getString("hints", "");
        hints.setText("Hint: "+hint);

        lockApplication = (LockApplication) getApplication();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = passText.getText().toString();
                if(!password.equals("")){
                    if(password.equals(correctPassword)){
                        Toast.makeText(getApplicationContext(),"password correct!",Toast.LENGTH_SHORT).show();
                        Intent goHome = new Intent(LockActivity.this, MainActivity.class);
                        lockApplication.setLock(false);
                        passText.setText("");
                        startActivity(goHome);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"password not correct, try again!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"password can't be empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_SHORT).show();
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

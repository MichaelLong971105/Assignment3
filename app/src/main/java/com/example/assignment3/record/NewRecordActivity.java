package com.example.assignment3.record;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import   java.text.SimpleDateFormat;

import com.example.assignment3.R;
import com.example.assignment3.database.MyDatabase;
import com.example.assignment3.main.MainActivity;

import java.util.Date;

public class NewRecordActivity extends AppCompatActivity {

    MyDatabase myDB;
    TextView time_text, title_text, content_text;
    Button btn_back, btn_save;
    String title, content, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        myDB = new MyDatabase(this);
        time_text = findViewById(R.id.edit_title_time);
        title_text = findViewById(R.id.edit_title);
        content_text = findViewById(R.id.edit_body);
        btn_back = findViewById(R.id.button_back);
        btn_save = findViewById(R.id.button_save);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        date = formatter.format(curDate);

        time_text.setText(date);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(NewRecordActivity.this, MainActivity.class);
                startActivity(goHome);
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_text.getText().toString();
                content = content_text.getText().toString();
                if(saveRecord(title,content,date)){
                    Intent goHome = new Intent(NewRecordActivity.this, MainActivity.class);
                    startActivity(goHome);
                    finish();
                }
            }
        });
    }

    public boolean saveRecord(String title, String content, String date){
        boolean checkValidFormat = true;
        if ("".equals(title)){
            Toast.makeText(this,"Title can't be empty!",Toast.LENGTH_SHORT).show();
            checkValidFormat = false;
        }
        if (title.length()>10){
            Toast.makeText(this,"Title too long!",Toast.LENGTH_SHORT).show();
            checkValidFormat = false;
        }

        if(checkValidFormat){
            SQLiteDatabase db;
            ContentValues values;
            db = myDB.getWritableDatabase();
            values = new ContentValues();
            values.put(MyDatabase.RECORD_TITLE,title);
            values.put(MyDatabase.RECORD_BODY,content);
            values.put(MyDatabase.RECORD_TIME,date);
            db.insert(MyDatabase.TABLE_NAME_RECORD,null,values);
            Toast.makeText(this,"Saved Successfully",Toast.LENGTH_SHORT).show();
            db.close();
        }
        return checkValidFormat;
    }

}

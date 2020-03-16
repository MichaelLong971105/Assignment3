package com.example.assignment3.record;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.R;
import com.example.assignment3.database.MyDatabase;
import com.example.assignment3.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleRecordActivity extends AppCompatActivity {

    Button back, save;
    TextView title, time;
    EditText content;
    Record record;
    MyDatabase myDB;
    String newContet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_record);

        back = findViewById(R.id.button_single_back);
        save = findViewById(R.id.button_single_save);
        title = findViewById(R.id.single_title);
        time = findViewById(R.id.single_time);
        content = findViewById(R.id.single_content);

        myDB = new MyDatabase(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(SingleRecordActivity.this, MainActivity.class);
                startActivity(goHome);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContet = content.getText().toString();
                updateContent(newContet);
            }
        });

        showSingle();
    }

    private void showSingle(){
        Intent fromHome = this.getIntent();
        if(fromHome != null){
            record = new Record();

            record.setId(Integer.valueOf(fromHome.getStringExtra(MyDatabase.RECORD_ID)));
            record.setTitleName(fromHome.getStringExtra(MyDatabase.RECORD_TITLE));
            record.setTextBody(fromHome.getStringExtra(MyDatabase.RECORD_BODY));
            record.setCreateTime(fromHome.getStringExtra(MyDatabase.RECORD_TIME));

            title.setText(record.getTitleName());
            time.setText(record.getCreateTime());
            content.setText(record.getTextBody());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent goHome = new Intent(SingleRecordActivity.this, MainActivity.class);
            startActivity(goHome);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateContent(String content){

        SQLiteDatabase db;
        ContentValues values;
        // update
        db = myDB.getWritableDatabase();
        values = new ContentValues();
        values.put(MyDatabase.RECORD_BODY,content);
        values.put(MyDatabase.RECORD_TIME,getNowTime());
        db.update(MyDatabase.TABLE_NAME_RECORD,values,MyDatabase.RECORD_ID +"=?",
                new String[]{record.getId().toString()});
        Toast.makeText(this,"Updated successfully!",Toast.LENGTH_SHORT).show();
        db.close();
    }

    private String getNowTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }
}

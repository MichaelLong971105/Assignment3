package com.example.assignment3.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import com.example.assignment3.R;
import com.example.assignment3.database.MyDatabase;
import com.example.assignment3.password.LockActivity;
import com.example.assignment3.password.LockApplication;
import com.example.assignment3.password.ResetPasswordActivity;
import com.example.assignment3.password.setPassword;
import com.example.assignment3.record.NewRecordActivity;
import com.example.assignment3.record.Record;
import com.example.assignment3.adapter.MyAdapter;
import com.example.assignment3.record.SingleRecordActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author: LONG, QINGSHENG
 * @ID: 16387388
 * This app is a     Secret Diary which needs the user to set up a password at the first run time,
 * and every time the user want to check the diaries, needs to enter the create password.
 * The main activity which will display a set up password activity if the user have not set up a password for it.
 * Once the user set up a password and enter the correct password, they can see a ListView which contains all
 * the diaries and they can click one to check the details and long press to delete it.
 * The user can create a new diary by clicking on the FloatingActionButton.
 */

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    LockApplication lockApplication;
    ArrayList<Record> recordList;
    MyDatabase myDB;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        mListView = findViewById(R.id.list_view);

        myDB = new MyDatabase(this);

        SharedPreferences sp = getSharedPreferences("Password", MODE_PRIVATE);
        boolean havePassword = sp.getBoolean("havePassword", false);
        lockApplication = (LockApplication) getApplication();
        setPassword(havePassword);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCreate = new Intent(MainActivity.this, NewRecordActivity.class);
                startActivity(goCreate);
                finish();
            }
        });

        showList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent resetPassword = new Intent(MainActivity.this, ResetPasswordActivity.class);
            startActivity(resetPassword);
            finish();
            return true;
        }

        if (id == R.id.action_logout) {
            lockApplication.setLock(true);
            Intent logout = new Intent(MainActivity.this, LockActivity.class);
            startActivity(logout);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPassword(boolean havePassword){
        if(!havePassword) {
            Intent goSetPassword = new Intent(MainActivity.this, setPassword.class);
            startActivity(goSetPassword);
            finish();
        }
        if((havePassword) && (lockApplication.getLock())) {
            Intent goLock = new Intent(MainActivity.this, LockActivity.class);
            startActivity(goLock);
            finish();
        }
    }

    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"Press again to exit",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showList(){
        recordList = new ArrayList<>();
        SQLiteDatabase db = myDB.getReadableDatabase();
        Cursor cursor = db.query(MyDatabase.TABLE_NAME_RECORD,null,
                null,null,null,
                null, MyDatabase.RECORD_TIME +" DESC");
        if(cursor.moveToFirst()){
            Record record;
            while (!cursor.isAfterLast()){
                record = new Record();
                record.setId(
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(MyDatabase.RECORD_ID))));
                record.setTitleName(
                        cursor.getString(cursor.getColumnIndex(MyDatabase.RECORD_TITLE))
                );
                record.setTextBody(
                        cursor.getString(cursor.getColumnIndex(MyDatabase.RECORD_BODY))
                );
                record.setCreateTime(
                        cursor.getString(cursor.getColumnIndex(MyDatabase.RECORD_TIME)));
                recordList.add(record);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        mAdapter = new MyAdapter(this, recordList, R.layout.record_item);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goSingle = new Intent(MainActivity.this, SingleRecordActivity.class);
                Record record = (Record) mListView.getItemAtPosition(position);
                goSingle.putExtra(MyDatabase.RECORD_TITLE,record.getTitleName().trim());
                goSingle.putExtra(MyDatabase.RECORD_BODY,record.getTextBody().trim());
                goSingle.putExtra(MyDatabase.RECORD_TIME,record.getCreateTime().trim());
                goSingle.putExtra(MyDatabase.RECORD_ID,record.getId().toString().trim());
                startActivity(goSingle);
                finish();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Record record = (Record) mListView.getItemAtPosition(position);
                delete(record,position);
                return true;
            }
        });
    }

    private void delete(final Record record,final int position){

        final AlertDialog.Builder dialog =
                new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Delete this diary?");
        String textTitle = record.getTitleName();
        dialog.setMessage(
                textTitle.length()>150?textTitle.substring(0,150)+"...":textTitle);
        dialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = myDB.getWritableDatabase();
                        db.delete(MyDatabase.TABLE_NAME_RECORD,
                                MyDatabase.RECORD_ID +"=?",
                                new String[]{String.valueOf(record.getId())});
                        db.close();
                        mAdapter.removeItem(position);
                        mListView.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
        dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.show();
    }
}

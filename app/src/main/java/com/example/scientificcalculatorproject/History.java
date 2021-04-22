package com.example.scientificcalculatorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.scientificcalculatorproject.MainActivity.Calculations;

public class History extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();
        switch (itemId){
            case R.id.backhistory:
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.clear:


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.history, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView=findViewById(R.id.listView);
        setTitle("History");


        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, Calculations);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(History.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete History!")
                        .setMessage("Delete this ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Calculations.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                //permanent data storage
                                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.scientificcalculatorproject", Context.MODE_PRIVATE);
                                HashSet<String> set= new HashSet<>(Calculations);
                                sharedPreferences.edit().putStringSet("Calculations", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }

        });


    }

}
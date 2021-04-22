package com.example.scientificcalculatorproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private EditText display;
    private TextView previousCalculations;
    static ArrayList<String> Calculations=new ArrayList<>();

    //database


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int options= item.getItemId();
        switch (options){
            case R.id.othercalc:
                Intent intent= new Intent(getApplicationContext(), Other_Calc.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.history:
                Intent intenthistory=new Intent(getApplicationContext(), History.class);
                startActivity(intenthistory);
                break;
            case R.id.scintificCalculator:
                Intent scientific= new Intent(getApplicationContext(), Scientific_Calculator.class);
                scientific.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(scientific);
                break;
            case R.id.exit:
                this.finishAffinity();
                break;
            case R.id.about:
                String mydetails="Hello there? My name is Patrick, \n" +
                        "A diligent software developer as well as computer programmer \n" +
                        " with a better record of delivering excellent services to clients. \n\n" +
                        "You can hire or make any query via \n" +
                        "patrickmuthusi77@gmail.com.\n" +
                        "Tel: +254706958261." +
                        "Location: Nairobi." +
                        "Kenya.\n\n" +
                        "Don't forget to rate the app on playstore.";
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("About Developer.");
                builder.setMessage(mydetails);
                builder.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        display=findViewById(R.id.display);
        display.setShowSoftInputOnFocus(false);
        setTitle("Calculator");

        //permanent data storage
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.scientificcalculatorproject", Context.MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("Calculations", null);
        if (set == null) {
            Toast.makeText(this, "History is empty", Toast.LENGTH_SHORT).show();
        } else{
            Calculations= new ArrayList(set);
        }


        previousCalculations=findViewById(R.id.previousCalculations);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            display.setShowSoftInputOnFocus(false);
        }
    }
    private void updatetext(String stringtoadd){

        String oldstring= display.getText().toString();
        int cursorposition=display.getSelectionStart();
        String leftstring=oldstring.substring(0, cursorposition);
        String rightstring=oldstring.substring(cursorposition);
        display.setText(String.format("%s%s%s",leftstring,stringtoadd,rightstring));
        display.setSelection(cursorposition+1);
    }

    public void zero(View view){
        updatetext("0");

    }
    public void one(View view){
        updatetext("1");

    }
    public void two(View view){
        updatetext("2");

    }
    public void three(View view){
        updatetext("3");

    }
    public void four(View view){
        updatetext("4");

    }
    public void five(View view){
        updatetext("5");

    }
    public void six(View view){
        updatetext("6");

    }
    public void seven(View view){
        updatetext("7");

    }
    public void eight(View view){
        updatetext("8");

    }
    public void nine(View view){
        updatetext("9");

    }


    public void brackets(View view){
        int cursorposition= display.getSelectionStart();
        int openPar=0;
        int closepar=0;
        int textlength=display.getText().length();
        for (int i=0; i<cursorposition; i++){
            if (display.getText().toString().substring(i, i+1).equals("(")){
                openPar +=1;
            }
            if (display.getText().toString().substring(i, i+1).equals(")")){
                closepar +=1;
            }
        }
        if (openPar==closepar || display.getText().toString().substring(textlength-1, textlength).equals("(")){
            updatetext("(");

        }
        else if (closepar < openPar || display.getText().toString().substring(textlength-1, textlength).equals("(")){
            updatetext(")");

        }
        display.setSelection(cursorposition +1);

    }


    public void add(View view){
        updatetext("+");

    }
    public void subtract(View view){
        updatetext("-");

    }
    public void divide(View view){
        updatetext("÷");

    }
    public void multiply(View view){
        updatetext("×");

    }


    public void point(View view){
        updatetext(".");

    }
    public void plusminus(View view){

    }
    public void cancle(View view){
        int cursorposition=display.getSelectionStart();
        int textlength=display.getText().length();

        if (cursorposition != 0 && textlength != 0){
            SpannableStringBuilder selection= (SpannableStringBuilder) display.getText();
            selection.replace(cursorposition-1, cursorposition, "");
            display.setText(selection);
            display.setSelection(cursorposition -1);
        }

    }
    public void clear(View view){
        display.setText("");
        previousCalculations.setText("");

    }
    public void exponent(View view){
        updatetext("^");

    }
    public void equals(View view){
        String userexpression=display.getText().toString();
        userexpression=userexpression.replaceAll("÷", "/");
        userexpression=userexpression.replaceAll("×", "*");

        Expression expression=new Expression(userexpression);

        String result=String.valueOf(expression.calculate());

        display.setText(result);
        display.setSelection(result.length());
        previousCalculations.setText(userexpression);

        Calculations.add(previousCalculations.getText().toString()+ "\n"+display.getText().toString());
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.scientificcalculatorproject", Context.MODE_PRIVATE);
        HashSet<String> set= new HashSet<>(MainActivity.Calculations);
        sharedPreferences.edit().putStringSet("Calculations", set).apply();




    }
}
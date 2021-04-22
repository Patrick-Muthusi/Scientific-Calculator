package com.example.scientificcalculatorproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.HashSet;

import static com.example.scientificcalculatorproject.MainActivity.Calculations;

public class Other_Calc extends AppCompatActivity {

    private TextView previousCalculations;
    private EditText display;



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option= item.getItemId();
        if (option== R.id.back){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other__calc);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Other Calculator");
        previousCalculations=findViewById(R.id.previousCalcupation);
        display=findViewById(R.id.textView);
        display.setShowSoftInputOnFocus(false);

    }

    private void updateText(String stradd){
        String oldstring=display.getText().toString();

        int cursorposition=display.getSelectionStart();
        String leftstring=oldstring.substring(0,cursorposition);
        String rightstring=oldstring.substring(cursorposition);


        display.setText(String.format("%s%s%s", leftstring,stradd, rightstring));
        display.setSelection(cursorposition+ stradd.length());


    }

    public void  zero(View view){
        updateText(getResources().getString(R.string.zero));
    }
    public void  one(View view){
        updateText(getResources().getString(R.string.one));
    }
    public void  two(View view){
        updateText(getResources().getString(R.string.two));
    }
    public void  three(View view){
        updateText(getResources().getString(R.string.three));
    }
    public void  four(View view){
        updateText(getResources().getString(R.string.four));
    }
    public void  five(View view){
        updateText(getResources().getString(R.string.five));
    }
    public void  six(View view){
        updateText(getResources().getString(R.string.six));
    }
    public void  seven(View view){
        updateText(getResources().getString(R.string.seven));
    }
    public void  eight(View view){
        updateText(getResources().getString(R.string.eight));
    }
    public void  nine(View view){
        updateText(getResources().getString(R.string.nine));
    }


    public void  openBracket(View view){
        updateText(getResources().getString(R.string.openParantesis));
    }
    public void  closeBrackets(View view){
        updateText(getResources().getString(R.string.closeParenticies));
    }
    public void  point(View view){
        updateText(getResources().getString(R.string.decimalText));
    }



    public void  add(View view){
        updateText(getResources().getString(R.string.addText));
    }
    public void  subtract(View view){
        updateText(getResources().getString(R.string.subtractText));
    }
    public void  multiply(View view){
        updateText(getResources().getString(R.string.multiplyText));
    }
    public void  divide(View view){
        updateText(getResources().getString(R.string.divideText));
    }


    public void  clear(View view){
        display.setText("");
        previousCalculations.setText("");
    }
    public void  back(View view){
        int cursorPosition=display.getSelectionStart();
        int textLength=display.getText().length();

        if (cursorPosition != 0 && textLength != 0){
            SpannableStringBuilder selection= (SpannableStringBuilder) display.getText();
            selection.replace(cursorPosition-1, cursorPosition, "");
            display.setText(selection);
            display.setSelection(cursorPosition-1);
        }
    }
    public void  equal(View view){
        String userExpression = display.getText().toString();

        previousCalculations.setText(userExpression);

        userExpression=userExpression.replaceAll(getResources().getString(R.string.divideText), "/");
        userExpression=userExpression.replaceAll(getResources().getString(R.string.multiplyText), "*");


        Expression expression=new Expression(userExpression);
        String result= String.valueOf(expression.calculate());

        display.setText(result);
        display.setSelection(result.length());

        //permanent data storage
        Calculations.add(previousCalculations.getText().toString()+ "\n"+ display.getText().toString());
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.scientificcalculatorproject", Context.MODE_PRIVATE);
        HashSet<String> set= new HashSet<>(Calculations);
        sharedPreferences.edit().putStringSet("Calculations", set).apply();


    }


    public void sin(View view){
        updateText("sin (");

    }
    public void cos(View view){
        updateText("cos (");

    }
    public void tan(View view){
        updateText("tan (");

    }
    public void sin2(View view){
        updateText("arcsin (");

    }
    public void cos2(View view){
        updateText("arccos (");

    }
    public void tan2(View view){
        updateText("arctan (");

    }
    public void log(View view){
        updateText("log (");

    }
    public void in(View view){
        updateText("In (");

    }
    public void squareRoot(View view){
        updateText("sqrt (");

    }
    public void e(View view){
        updateText("e (");

    }
    public void pie(View view){
        updateText("π");

    }
    public void xbrackets(View view){
// absolute value |x|
        updateText("abs(");
    }
    public void prime(View view){
        updateText("ispr(");


    }
    public void xsquared(View view){

        updateText("^(2)");

    }
    public void xysquared(View view){

        updateText("^(");
    }
}
package com.shenkar.shakedzrihen.mycalc;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import java.text.DecimalFormat;
import java.util.regex.Pattern;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String _result = "";
    private TextView _screen;
    private  String display ="";
    private String currentOperator = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _screen = findViewById(R.id.textView2);
        _screen.setText(display);
    }

    private void updateScreen(){
        _screen.setText(display);
    }

    public void onClickNumber(View v){
        if(!"".equals(_result))
        {
            clear();
            updateScreen();
        }
        Button number = (Button) v;
        display += number.getText();
        updateScreen();
    }

    public void onClickOperator(View v){
        if(!"".equals(_result))
        {
            if("Invalid operation".equals(_result)){
                display = "";
                _result = "";
            } else {
                display = _result;
                _result = "";
            }
        }
        if(!"".equals(currentOperator)){
            calculate("");
            updateScreen();
            _result = "";
        }
        Button operator = (Button) v;
        display += operator.getText();
        currentOperator = operator.getText().toString();
        updateScreen();
    }

    public void clear() {
        display = "";
        currentOperator = "";
        _result = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    public String answerToString(double answer){
        if ((int)answer == answer) {
            return new DecimalFormat("##").format(answer);
        } else {
            return new DecimalFormat("##.###").format(answer);
        }
    }

    public String calculate(String x, String y, String operator){
        _result = "";
        switch (operator){
            case "+": return answerToString(Double.valueOf(x) + Double.valueOf(y));
            case "-": return answerToString(Double.valueOf(x) - Double.valueOf(y));
            case ":":
                try {
                    if ("0".equals(y)){
                        throw new IllegalArgumentException("Argument 'divisor' is 0");
                    }
                    return answerToString(Double.valueOf(x) / Double.valueOf(y));
                } catch (Exception e) {
                    Log.d("calc", e.getMessage());
                    return  x;
                }
            case "x": return answerToString(Double.valueOf(x) * Double.valueOf(y));
            default: return "Invalid Operation";
        }
    }

    public void calculate(String op){
        if("".equals(currentOperator)){
            _screen.setText(display);
            return;
        }
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2){
            return;
        }
        String text;
        _result = calculate(operation[0], operation[1], currentOperator);
        if ("Invalid Operation".equals(_result)){
            text = _result;
        } else {
            text = display + "\n" + _result;
        }
        if("Eq".equals(op)){
            _screen.setText(text);
        } else {
            display = _result;
        }

    }

    public void onClickEqual(View v){
        calculate("Eq");
    }
}

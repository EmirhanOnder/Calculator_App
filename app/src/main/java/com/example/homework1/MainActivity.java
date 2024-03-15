package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView displayScreen;
    private String currentInput= "";
    private double result = 0 ;
    private char operator = ' ';
    private boolean functionPending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayScreen = findViewById(R.id.DisplayScreen);
    }
    public void ClearScreen(View view)
    {
        currentInput="";
        displayScreen.setText(currentInput);
        result = 0;
        operator = ' ';
    }
    public void onClick(View view)
    {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if(isFunction(buttonText)){
            functionPending = true;
            operator = buttonText.charAt(0);
        }
        else if(isOperator(buttonText)) {
            if(functionPending)
            {
                double value = Double.parseDouble(currentInput);
                result += performFunction(value, operator);
                displayScreen.setText(String.valueOf(result));
                currentInput = "" ;
                functionPending = false;
            }
            else if(operator == ' ' && !currentInput.isEmpty())
            {
                result = Double.parseDouble(currentInput);
                currentInput = "";
            }
            else if(operator == ' ' && currentInput.isEmpty())
            {
                result = 0;
                currentInput = "";
            }
            else if (operator != ' ' && !currentInput.isEmpty()) {
                double secondValue = Double.parseDouble(currentInput);
                result = performCalculation(result, secondValue, operator);
                displayScreen.setText(String.valueOf(result));
                currentInput = "";
            }
            operator = buttonText.charAt(0);
        }
        else
        {
            currentInput += buttonText;
            displayScreen.setText(currentInput);
        }
    }
    public void AddMinus(View view)
    {
        if(!currentInput.contains("-"))
        {
            currentInput = "-"+currentInput;
            displayScreen.setText(currentInput);
        }
        else
        {
            currentInput = currentInput.substring(1);
            displayScreen.setText(currentInput);
        }
    }
    public void DeleteOneNumber(View view)
    {
        if(!currentInput.isEmpty())
        {
            currentInput = currentInput.substring(0, currentInput.length()-1);
            displayScreen.setText(currentInput);
        }
        else
        {
            currentInput="";
            displayScreen.setText(currentInput);
        }
    }
    public void Calculate(View view)
    {
        if (operator != ' ') {
            if(functionPending)
            {
                double value = Double.parseDouble(currentInput);
                result += performFunction(value,operator);
            }
            else
            {
                double secondValue = Double.parseDouble(currentInput);
                result = performCalculation( result ,secondValue, operator);
            }

            if(Double.isNaN(result))
            {
                displayScreen.setText("NaN");
                currentInput="NaN";
            }
            else if(Double.toString(result).split("\\.")[1].equals("0"))
            {
                displayScreen.setText(String.valueOf(Double.toString(result).split("\\.")[0]));
                currentInput = String.valueOf(Double.toString(result).split("\\.")[0]);
            }
            else
            {
                displayScreen.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
            }
            operator = ' ';
            functionPending = false;
        }
    }
    private double performCalculation(double value1, double value2, char op)
    {
        switch (op) {
            case '+':
                return value1 + value2;
            case '-':
                return value1 - value2;
            case 'x':
                return value1 * value2;
            case '÷':
                if (value2 == 0.0) {
                    return Double.NaN;
                }
                return value1 / value2;
            case '%':
                return value1 % value2;

            default:
                return value2;
        }
    }

    private double performFunction(double value, char op) {
        switch (op) {
            case 'S':
                return Math.sin(Math.toRadians(value));
            case 'C':
                return Math.cos(Math.toRadians(value));
            case 'T':
                return Math.tan(Math.toRadians(value));
            case 'L':
                return Math.log10(value);
            case '√':
                return Math.sqrt(value);
            default:
                return value;
        }
    }
    private boolean isOperator(String text) {
        String operators = "+-x÷%";
        return operators.contains(text);
    }

    private boolean isFunction(String text) {
        String functions = "Sin Cos Log Tan √";
        return functions.contains(text);
    }
    public void showOtherOperations(View view) {
        LinearLayout mathFunctionsLayout = findViewById(R.id.mathFunctionsLayout);
        LinearLayout mathFunctionsLayout2 = findViewById(R.id.mathFunctionsLayout2);
        if (mathFunctionsLayout.getVisibility() == View.VISIBLE) {
            mathFunctionsLayout.setVisibility(View.GONE);
            mathFunctionsLayout2.setVisibility(View.GONE);
        } else {
            mathFunctionsLayout.setVisibility(View.VISIBLE);
            mathFunctionsLayout2.setVisibility(View.VISIBLE);
        }
    }
}
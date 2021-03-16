package edu.ranken.kyoung.calculatorspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    // Declare and initialize global constants
    final String NOINPUT = "No Number Was Provided";
    final String NOOPSEL = "No Operator Was Selected";
    final String DIVBYZERO = "Cannot Divide By Zero";

    // Declare global program variables
    double number1;
    double number2;
    double answer;
    String operator;
    String symbol;
    String outputStr;

    // Declare program widgets
    Spinner spOperators;
    EditText etNumber1;
    EditText etNumber2;
    Button btnCalculate;
    Button btnClear;
    TextView tvAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to program widgets
        spOperators = findViewById(R.id.spOperators);
        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvAnswer = findViewById(R.id.tvAnswer);

        if (spOperators != null) {
            spOperators.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operators,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        if (spOperators != null) {
            spOperators.setAdapter(adapter);
            spOperators.setOnItemSelectedListener(this);
        }

        // Initialize Global Variables
        number1 = 0.0;
        number2 = 0.0;
        answer = 0.0;
        operator = "";
        symbol = "";
        outputStr = "";

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upon clicking, this will reset all fields of the calculator
                etNumber1.setText("");
                etNumber2.setText("");
                tvAnswer.setText("");
                answer = 0.0;
                operator = "";
                symbol = "";
                etNumber1.requestFocus();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean keepGoing = validateNumber1();  // Calling the function
                // validateNumber1() to validate the
                // user's first input.

                if (keepGoing) {
                    keepGoing = validateNumber2();  // Calling the function
                    // validateNumber2() to validate the
                    // user's second input.
                } else {
                    return;
                }

                if (keepGoing) {
                    keepGoing = calculateAnswer();  // Calls the function calculateAnswer()
                    // to calculate the result of the two
                    // numbers inputted by the user.
                } else {
                    return;
                }
                if (keepGoing) {
                    // This is the format for how the answer will be displayed in the
                    // tvAnswer textview.
                    outputStr = String.format("%.2f", number1) + " ";
                    outputStr += symbol + " ";
                    outputStr += String.format("%.2f", number2) + " = ";
                    outputStr += String.format("%.2f", answer);
                    tvAnswer.setText(outputStr);
                }
            }
        });
    }

    public boolean validateNumber1()
    {
        // This function is called to validate the user's first entered number.

        try
        {
            number1 = Double.parseDouble(etNumber1.getText().toString());
            return true;
        }
        catch (NumberFormatException nfe)
        {
            // If no user input is detected, this toast will display stating that
            // the user did not enter a number.
            Toast.makeText(getApplicationContext(),
                    NOINPUT, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean validateNumber2()
    {
        // This function is called to validate the user's second entered number.

        try
        {
            number2 = Double.parseDouble(etNumber2.getText().toString());
            return true;
        }
        catch (NumberFormatException nfe)
        {
            // If no user input is detected, this toast will display stating that
            // the user did not enter a number.
            Toast.makeText(getApplicationContext(),
                    NOINPUT, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean calculateAnswer()
    {
        // This if statement checks to make sure the user did not try to divide or
        // modulo by zero and displays a toast saying "Cannot divide by zero" if they
        // did.
        if (((operator.equals("/") || (operator.equals("%"))) &&
                (number2 == 0)))
        {
            Toast.makeText(getApplicationContext(),
                    DIVBYZERO, Toast.LENGTH_LONG).show();
            return false;
        }
        // This switch statement calculates the user's equation based on the selected
        // mathematical symbol.
        else
        {
            switch (symbol)
            {
                case "+":
                    answer = number1 + number2;
                    break;

                case "-":
                    answer = number1 - number2;
                    break;

                case "*":
                    answer = number1 * number2;
                    break;

                case "/":
                    answer = number1 / number2;
                    break;

                case "%":
                    answer = number1 % number2;
                    break;
            }

            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        // This determines the value of the selected symbol
        if (text.equals("+"))
        {
            operator = "add";
            symbol = "+";
        }
        else if (text.equals("-"))
        {
            operator = "subtract";
            symbol = "-";
        }
        else if (text.equals("*"))
        {
            operator = "multiply";
            symbol = "*";
        }
        else if (text.equals("/"))
        {
            operator = "divide";
            symbol = "/";
        }
        else if (text.equals("%"))
        {
            operator = "modulus";
            symbol = "%";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),
                NOOPSEL, Toast.LENGTH_LONG).show();
    }

}
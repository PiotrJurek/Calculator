package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mariuszgromada.math.mxparser.Expression;

public class AdvancedActivity extends AppCompatActivity {

    private TextView calcTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        calcTextView = findViewById(R.id.calcTextView);
        calcTextView.setText(SavedValues.calculation);
    }

    @SuppressLint("SetTextI18n")
    public void numberButtonOnClick(View view) {
        Button btn = (Button) view;
        calcTextView.setText(calcTextView.getText().toString() + btn.getText());
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void clearButtonOnClick(View view) {
        calcTextView.setText("");
        SavedValues.calculation = calcTextView.getText().toString();
    }

    public void backspaceButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(!text.isEmpty()){
            calcTextView.setText(text.substring(0, text.length() - 1));
        }
        text = calcTextView.getText().toString();
        while(!text.isEmpty() && Character.isAlphabetic(text.charAt(text.length() - 1))
        && text.charAt(text.length() - 1) != 'e' && text.charAt(text.length() - 1) != 'π') {
            calcTextView.setText(text.substring(0, text.length() - 1));
            text = calcTextView.getText().toString();
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void divisionButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(!text.isEmpty()) {
            char last = text.charAt(text.length() - 1);
            if(Character.isDigit(last) || last == 'e' || last == 'π' || last == ')') {
                calcTextView.setText(text + '/');
            }
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void multiplicationButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(!text.isEmpty()) {
            char last = text.charAt(text.length() - 1);
            if(Character.isDigit(last) || last == 'e' || last == 'π' || last == ')') {
                calcTextView.setText(text + '*');
            }
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void minusButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(!text.isEmpty()) {
            char last = text.charAt(text.length() - 1);
            if(last != '-' && last != '.') {
                calcTextView.setText(text + '-');
            }
        }
        else {
            calcTextView.setText(text + '-');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void plusButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(!text.isEmpty()) {
            char last = text.charAt(text.length() - 1);
            if(Character.isDigit(last) || last == 'e' || last == 'π' || last == ')') {
                calcTextView.setText(text + '+');
            }
            else if(last == '-') {
                calcTextView.setText(text.substring(0, text.length() - 1));
            }
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    public void signButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty()){
            calcTextView.setText("-");
            SavedValues.calculation = calcTextView.getText().toString();
            return;
        }

        StringBuilder sb = new StringBuilder(text);
        int i = sb.length() - 1;
        while(i >= 0) {
            char c = text.charAt(i);
            if(!Character.isDigit(c) && c != '.' && c != 'e' && c != 'π') {
                if(c == '+') {
                    sb.setCharAt(i, '-');
                }
                else if(c == '-') {
                    if(i >= 1) {
                        char c2 = sb.charAt(i - 1);
                        if(!Character.isDigit(c2) && c2 != '.' && c2 != 'e' && c2 != 'π') {
                            sb.deleteCharAt(i);
                        }
                        else {
                            sb.setCharAt(i, '+');
                        }
                    }
                    else {
                        sb.deleteCharAt(i);
                    }
                }
                else {
                    sb.insert(i + 1, '-');
                }
                calcTextView.setText(sb.toString());
                SavedValues.calculation = calcTextView.getText().toString();
                return;
            }
            i--;
        }
        sb.insert(0, '-');
        calcTextView.setText(sb.toString());
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void dotButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        int counter = 0;
        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if(c == '.') {
                counter++;
            }
            else if (!Character.isDigit(c)) {
                break;
            }
        }
        if(!text.isEmpty() && Character.isDigit(text.charAt(text.length() - 1)) && counter == 0) {
            calcTextView.setText(text + '.');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void equalButtonOnClick(View view) {
        String text = calcTextView.getText().toString();

        Expression exp = new Expression(text);
        String result = String.valueOf(exp.calculate());

        if(!result.equals("NaN")) {
            calcTextView.setText(result);
        }
        else {
            calcTextView.setText("ERROR");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void startButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + '(');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void endButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + ')');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void piButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + 'π');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void eButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + 'e');
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void sinButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "sin(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void cosButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "cos(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void tanButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "tan(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void lnButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "ln(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void sqrtButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "sqrt(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void x2ButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "^2");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void xyButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "^(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }

    @SuppressLint("SetTextI18n")
    public void logButtonOnClick(View view) {
        String text = calcTextView.getText().toString();
        if(text.isEmpty() || text.charAt(text.length() - 1) != '.') {
            calcTextView.setText(text + "lg(");
        }
        SavedValues.calculation = calcTextView.getText().toString();
    }
}
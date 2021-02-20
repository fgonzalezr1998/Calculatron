package com.example.calculatron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Calculatron";

    public class ButtonHandler extends AppCompatActivity implements View.OnClickListener {

        private TextView resultsText, operationText;

        public ButtonHandler(TextView resultsText, TextView operationText) {
            Log.w(TAG, "Button Handler Generated!");
            this.resultsText = resultsText;
            this.operationText = operationText;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (this.isWriteable(id)) {
                this.writeOperation(this.getStrById(id));
            } else {
                switch (id) {
                    case R.id.reset_btn:
                        this.reset();
                        break;
                    case R.id.delete_btn:
                        this.deleteOperation();
                        break;
                }
            }
        }

        private void reset() {
            this.operationText.setText("0");
            this.resultsText.setText("");
        }

        private void deleteOperation() {
            String operation = (String) this.operationText.getText();
            if (!operation.equals("0")) {
                operation = operation.substring(0, operation.length() - 1);
                if (operation.equals(""))
                    operation = "0";
                this.operationText.setText(operation);
            }
        }

        private boolean isWriteable(int id) {
            return id == R.id.zero_btn || id == R.id.one_btn || id == R.id.two_btn ||
                    id == R.id.three_btn || id == R.id.four_btn || id == R.id.five_btn ||
                    id == R.id.six_btn || id == R.id.seven_btn || id == R.id.eight_btn ||
                    id == R.id.nine_btn || id == R.id.point_btn || id == R.id.divide_btn ||
                    id == R.id.product_btn || id == R.id.substract_btn || id == R.id.add_btn;
        }

        private void writeOperation(String n_str) {
            String operation = (String) this.operationText.getText();

            if (operation.equals("0"))
                operation = "";

            operation += n_str;
            this.operationText.setText(operation);
        }

        private String getStrById(int id) {
            String s;
            switch (id) {
                case R.id.zero_btn:
                    s = "0";
                    break;
                case R.id.one_btn:
                    s = "1";
                    break;
                case R.id.two_btn:
                    s = "2";
                    break;
                case R.id.three_btn:
                    s = "3";
                    break;
                case R.id.four_btn:
                    s = "4";
                    break;
                case R.id.five_btn:
                    s = "5";
                    break;
                case R.id.six_btn:
                    s = "6";
                    break;
                case R.id.seven_btn:
                    s = "7";
                    break;
                case R.id.eight_btn:
                    s = "8";
                    break;
                case R.id.nine_btn:
                    s = "9";
                    break;
                case R.id.point_btn:
                    s = ",";
                    break;
                case R.id.divide_btn:
                    s = "÷";
                    break;
                case R.id.product_btn:
                    s = "x";
                    break;
                case R.id.substract_btn:
                    s = "-";
                    break;
                case R.id.add_btn:
                    s = "+";
                    break;
                default:
                    s = "";
            }
            return s;
        }
    }

    LinearLayout linearLayout;
    ButtonHandler buttonHandler;
    private TextView resultsText, operationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido a Calculatron!", Toast.LENGTH_LONG);
        toast.show();

        linearLayout = (LinearLayout) findViewById(R.id.app_container);
        resultsText = (TextView) findViewById(R.id.results_text);
        operationText = (TextView) findViewById(R.id.operator_text);

        buttonHandler = new ButtonHandler(resultsText, operationText);
        this.setButtonsListener(this.linearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido a Calculatron!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setButtonsListener(ViewGroup parent_layout) {
        View element;
        Log.w(TAG, Integer.toString(parent_layout.getChildCount()));
        for (int i = 0; i < parent_layout.getChildCount(); i++) {
            element = parent_layout.getChildAt(i);
            if (element != null && element.isClickable()) {
                element.setOnClickListener(buttonHandler);
            }
            if (parent_layout.getChildAt(i) instanceof ViewGroup)
                this.setButtonsListener((ViewGroup) parent_layout.getChildAt(i));
        }
    }
}
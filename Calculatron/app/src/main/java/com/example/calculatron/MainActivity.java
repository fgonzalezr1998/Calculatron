package com.example.calculatron;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Calculatron";

    public class ButtonHandler implements View.OnClickListener {

        private TextView resultsText, operationText;
        private ArithmeticEvaluation arithmeticEval;
        private Context context;

        public ButtonHandler(TextView resultsText, TextView operationText, Context context) {
            // Log.w(TAG, "Button Handler Generated!");
            this.resultsText = resultsText;
            this.operationText = operationText;
            this.arithmeticEval = new ArithmeticEvaluation();
            this.context = context;
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
                    case R.id.equal_btn:
                        this.printResult();
                        break;
                    case R.id.percentage_btn:
                        this.printPercentage();
                        break;
                    case R.id.sqrt_btn:
                        this.printSqrt();
                        break;
                    case R.id.sin_btn:
                        this.printSin();
                        break;
                    case R.id.cos_btn:
                        this.printCos();
                        break;
                    case R.id.tan_btn:
                        this.printTan();
                        break;
                    case R.id.log_btn:
                        this.printLog();
                        break;
                    case R.id.ln_btn:
                        this.printLn();
                        break;
                    case R.id.fact_btn:
                        this.printFact();
                        break;
                    case R.id.pi_btn:
                        this.printPi();
                        break;
                }
            }
        }

        private int charAppearance(String str, char c) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c)
                    count++;
            }
            return count;
        }

        private boolean isWriteable(int id) {
            return id == R.id.zero_btn || id == R.id.one_btn || id == R.id.two_btn ||
                    id == R.id.three_btn || id == R.id.four_btn || id == R.id.five_btn ||
                    id == R.id.six_btn || id == R.id.seven_btn || id == R.id.eight_btn ||
                    id == R.id.nine_btn || id == R.id.point_btn || id == R.id.divide_btn ||
                    id == R.id.product_btn || id == R.id.substract_btn || id == R.id.add_btn ||
                    id == R.id.par_open_btn || id == R.id.par_closed_btn;
        }

        private boolean lastCharIsOk(String op) {
            String lastCh = op.substring(op.length() - 1);

            return !lastCh.equals("+") && !lastCh.equals("-") && !lastCh.equals("x") &&
                    !lastCh.equals("÷");
        }

        private boolean parenthesisOk(String op) {
            boolean ok;
            int i, j;
            ok = this.charAppearance(op, '(') == this.charAppearance(op, ')');
            if (!ok)
                return false;

            i = 0;
            while (i < op.length() - 1 && ok) {
                if (op.charAt(i) == '(') {
                    ok = op.charAt(i + 1) != ')';
                } else if (op.charAt(i) == ')' && i > 0) {
                    j = i - 1;
                    ok = op.charAt(j) != '+' && op.charAt(j) != '-' &&
                            op.charAt(j) != 'x' && op.charAt(j) != '÷';
                }
                i++;
            }

            return ok;
        }

        private boolean operatorsOk(String op) {
            for (int i = op.length() - 1; i >= 0; i--) {
                if (op.charAt(i) == ')')
                    continue;
                return this.lastCharIsOk(op.substring(0, i + 1));
            }
            return false;
        }

        private boolean operationOk(String op) {
            return this.lastCharIsOk(op) && this.parenthesisOk(op) && this.operatorsOk(op);
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

        private void printPi() {
            String operation;
            boolean ok = true;

            operation = (String) this.operationText.getText();

            if (operation.equals("0")) {
                operation = "";
            } else {
                ok = !this.operationOk(operation);
            }
            if (ok) {
                operation += "3.1416";
                this.operationText.setText(operation);
            }
        }

        private void printSqrt() {
            double res;

            String operation = (String) this.operationText.getText();
            if (!this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.sqrt(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printSin() {
            double res;

            String operation = (String) this.operationText.getText();
            if (! this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.sin(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printCos() {
            double res;

            String operation = (String) this.operationText.getText();
            if (! this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.cos(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printTan() {
            double res;

            String operation = (String) this.operationText.getText();
            if (!this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.tan(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printLog() {
            double res;

            String operation = (String) this.operationText.getText();
            if (!this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.log10(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printLn() {
            double res;

            String operation = (String) this.operationText.getText();
            if (!this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            res = Math.log(res);
            this.resultsText.setText(String.valueOf(res));
        }

        private void printFact() {
            double res;
            int roundedRes;
            int fact;

            String operation = (String) this.operationText.getText();
            if (!this.operationOk(operation)) {
                return;
            }

            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                res = expr.eval().doubleValue();
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            roundedRes = (int) res;
            fact = 1;
            while (roundedRes != 0) {
                fact = fact * roundedRes;
                roundedRes--;
            }
            this.resultsText.setText(String.valueOf(fact));
        }

        private void printPercentage() {
            double expr, res;

            try {
                expr = Double.parseDouble((String) this.operationText.getText());
            } catch (NumberFormatException e) {
                Toast toast = Toast.makeText(this.context, "Invalid Expression!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            res = expr / 100.0;
            res = Math.round(res * 1000.0) / 1000.0;
            this.resultsText.setText(String.valueOf(res));
        }

        private void printResult() {
            String operation = (String) this.operationText.getText();
            if (! this.operationOk(operation)) {
                return;
            }
            ArithmeticEvaluation.Expression expr = this.arithmeticEval.parse(operation);

            try {
                this.resultsText.setText(String.valueOf(expr.eval().doubleValue()));
            } catch (IllegalStateException e) {
                this.resultsText.setText("inf");
                Toast toast = Toast.makeText(this.context, "Division by Zero!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        private void writeOperation(String n_str) {
            String operation = (String) this.operationText.getText();

            if (operation.equals("0"))
                operation = "";

            if (n_str.equals(","))
                n_str = ".";
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
                case R.id.par_open_btn:
                    s = "(";
                    break;
                case R.id.par_closed_btn:
                    s = ")";
                    break;
                default:
                    s = "";
            }
            return s;
        }
    }

    private LinearLayout linearLayout;
    private ButtonHandler buttonHandler;
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

        buttonHandler = new ButtonHandler(resultsText, operationText, getApplicationContext());
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
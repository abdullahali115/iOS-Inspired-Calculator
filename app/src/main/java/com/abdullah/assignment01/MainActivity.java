package com.abdullah.assignment01;

import static android.widget.Toast.*;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton menuButton;
    ImageButton[] button2n3 = new ImageButton[2];
    LinearLayout sciLayout;
    LinearLayout extraSpace;
    boolean isBasic;
    boolean isScientific;
    LinearLayout landNormal;
    LinearLayout mainLayout;

    TextView mainText;
    TextView previewText;

    String op;

    List<View> buttons = new ArrayList<>();
    boolean justEvaluated = true;

    boolean hasTrig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        mainLayout = findViewById(R.id.mainLayout);
        menuButton = findViewById(R.id.menuButton);
        button2n3[0] = findViewById(R.id.menuButton2);
        button2n3[1] = findViewById(R.id.menuButton3);
        landNormal = findViewById(R.id.normalLayout);
        sciLayout = findViewById(R.id.sciLayout);
        isBasic = true;
        isScientific = false;
        mainText = findViewById(R.id.mainText);
        previewText = findViewById(R.id.previewText);
        getButtons(mainLayout);
        op = "";

        if (savedInstanceState != null) {
            mainText.setText(savedInstanceState.getString("mainText", ""));
            previewText.setText(savedInstanceState.getString("previewText", ""));
        }

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu obj = new PopupMenu(MainActivity.this, menuButton);
                obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                obj.show();

                obj.setOnMenuItemClickListener(items -> {
                    int id = items.getItemId();
                    if (id == R.id.basicOption && !isBasic) {
                        extraSpace = findViewById(R.id.extraSpace);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) extraSpace.getLayoutParams();
                        LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();


                        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 0.2f);
                        ValueAnimator animate = ValueAnimator.ofFloat(1.0f, 0.0f);
                        animator.setDuration(300);
                        animate.setDuration(300);
                        animator.addUpdateListener(animation -> {
                            params.weight = (Float) animator.getAnimatedValue();
                            extraSpace.setLayoutParams(params);
                        });

                        animate.addUpdateListener(animation -> {
                            sciParams.weight = (Float) animate.getAnimatedValue();
                            sciLayout.setLayoutParams(sciParams);
                        });
                        animator.start();
                        animate.start();
                        isBasic = true;
                        isScientific = false;
                    } else if (id == R.id.sciOption && !isScientific) {
                        extraSpace = findViewById(R.id.extraSpace);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) extraSpace.getLayoutParams();
                        LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();


                        ValueAnimator animator = ValueAnimator.ofFloat(0.2f, 0.0f);
                        ValueAnimator animate = ValueAnimator.ofFloat(0.0f, 1.0f);
                        animator.setDuration(300);
                        animate.setDuration(300);
                        animator.addUpdateListener(animation -> {
                            params.weight = (Float) animator.getAnimatedValue();
                            extraSpace.setLayoutParams(params);
                        });
                        animate.addUpdateListener(animation -> {
                            sciParams.weight = (Float) animate.getAnimatedValue();
                            sciLayout.setLayoutParams(sciParams);
                        });
                        animator.start();
                        animate.start();
                        isBasic = false;
                        isScientific = true;
                    }
                    return true;
                });
            }
        });

        for (View btn : buttons) {
            if (((Button) btn).getText().equals("AC")) {
                ((Button) btn).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mainText.setText("0");
                        previewText.setText("");
                        op = "";
                        justEvaluated = false;
                        return true;
                    }
                });
            }
            ((Button) btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMathError()) {
                        mainText.setText("0");
                        previewText.setText("");
                        op = "";
                        justEvaluated = false;
                    }
                    String text = ((Button) btn).getText().toString();
                    switch (text) {
                        case "=": {
                            try {
                                String expression = mainText.getText().toString();
                                expression = preprocessExpression(expression);
                                double result = evalExpression(expression);
                                mainText.setText(Double.toString(result));
                                previewText.setText(expression);
                                op = "";
                                justEvaluated = true;
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                        case "AC": {
                            if (justEvaluated) {
                                mainText.setText("0");
                                previewText.setText("");
                                op = "";
                            } else {
                                CharSequence current = mainText.getText();
                                if (current.length() > 0) {
                                    char lastChar = current.charAt(current.length() - 1);
                                    if (isOperator(lastChar)) {
                                        op = "";
                                    }
                                    mainText.setText(current.subSequence(0, current.length() - 1));
                                }
                                if (mainText.getText().length() == 0) {
                                    mainText.setText("0");
                                    previewText.setText("");
                                    op = "";
                                }
                            }
                            justEvaluated = false;
                            break;
                        }

                        case "+":
                        case "-":
                        case "%":
                        case "×":
                        case "÷": {
                            if (!isOperator(mainText.getText().charAt(mainText.length() - 1))) {
                                previewText.setText("");
                                mainText.setText(mainText.getText() + text);
                            } else {
                                mainText.setText(mainText.getText().subSequence(0, mainText.length() - 1) + text);
                            }
                            op = text;
                            break;
                        }
                        case "sin":
                        case "cos":
                        case "tan":
                        case "sinh":
                        case "cosh":
                        case "tanh":
                        case "ln":
                        case "log₁₀": {
                            if (!isInsideTrig()) {
                                if (!isOperator(mainText.getText().charAt(mainText.length() - 1))) {
                                    if (mainText.getText().equals("0"))
                                        mainText.setText(text + "(");
                                } else
                                    mainText.setText(mainText.getText() + text + "(");
                                hasTrig = true;
                            }
                            break;
                        }
                        case "\uD835\uDC52ˣ":
                            if (mainText.getText().equals("0"))
                                mainText.setText("e^");
                            else if (!mainText.getText().equals(""))
                                if (!isOperator(mainText.getText().charAt(mainText.length() - 1)) && mainText.getText().charAt(mainText.length() - 1) != '√' && mainText.getText().charAt(mainText.length() - 1) != '^' && mainText.getText().charAt(mainText.length() - 1) != '(')
                                    mainText.setText(mainText.getText() + "*e^");
                                else
                                    mainText.setText(mainText.getText() + "e^");
                            break;
                        case "\uD835\uDC52": {
                            if (mainText.getText().equals("0"))
                                mainText.setText("e^1");
                            else if (!mainText.getText().equals(""))
                                if (!isOperator(mainText.getText().charAt(mainText.length() - 1)) && mainText.getText().charAt(mainText.length() - 1) != '√' && mainText.getText().charAt(mainText.length() - 1) != '^' && mainText.getText().charAt(mainText.length() - 1) != '(')
                                    mainText.setText(mainText.getText() + "*e^1");
                                else
                                    mainText.setText(mainText.getText() + "e^1");
                            break;
                        }
                        case "π": {
                            if (mainText.getText().equals("0"))
                                mainText.setText("3.14");
                            else if (!mainText.getText().equals("0"))
                                if (!isOperator(mainText.getText().charAt(mainText.length() - 1)) && mainText.getText().charAt(mainText.length() - 1) != '√' && mainText.getText().charAt(mainText.length() - 1) != '^' && mainText.getText().charAt(mainText.length() - 1) != '(')
                                    mainText.setText(mainText.getText() + "*3.14");
                                else
                                    mainText.setText(mainText.getText() + "3.14");
                            break;
                        }
                        case "Rand":
                        case "Rad":
                        case "EE":
                        case "ʸ√x":
                        case "³√x":
                        case "¹⁄ₓ":
                        case "10ˣ":
                        case "2ⁿᵈ":
                        case "+/-":
                        case "mr":
                        case "m-":
                        case "m+":
                        case "mc":
                            break;
                        case "x²":
                            mainText.setText(mainText.getText() + "^2");
                            break;
                        case "x³":
                            mainText.setText(mainText.getText() + "^3");
                            break;
                        case "x!": {
                            mainText.setText(mainText.getText() + "!");
                            break;
                        }
                        case "xʸ": {
                            mainText.setText(mainText.getText() + "^");
                            break;
                        }
                        case "²√x": {
                            if (mainText.getText().equals("0"))
                                mainText.setText("√");
                            else if (!mainText.getText().equals(0))
                                if (!isOperator(mainText.getText().charAt(mainText.length() - 1)))
                                    mainText.setText(mainText.getText() + "*√");
                                else
                                    mainText.setText(mainText.getText() + "√");
                            break;
                        }
                        default: {
                            if (!previewText.getText().equals("")) {
                                mainText.setText("0");
                                previewText.setText((""));
                            }
                            if (mainText.getText().equals("0"))
                                mainText.setText(text);
                            else
                                mainText.setText(mainText.getText() + text);
                        }
                    }
                }
            });
        }

        for (ImageButton menuButton2 : button2n3) {
            menuButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu obj = new PopupMenu(MainActivity.this, menuButton2);
                    obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                    obj.show();

                    obj.setOnMenuItemClickListener(items -> {
                        int id = items.getItemId();
                        if (id == R.id.basicOption && !isBasic) {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) landNormal.getLayoutParams();
                            LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();


                            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
                            ValueAnimator animate = ValueAnimator.ofFloat(0.0f, 1.0f);
                            animator.setDuration(350);
                            animate.setDuration(350);
                            animator.addUpdateListener(animation -> {
                                sciParams.weight = (Float) animator.getAnimatedValue();
                                sciLayout.setLayoutParams(sciParams);
                            });

                            animate.addUpdateListener(animation -> {
                                params.weight = (Float) animate.getAnimatedValue();
                                landNormal.setLayoutParams(params);
                            });
                            animator.start();
                            animate.start();
                            isBasic = true;
                            isScientific = false;
                        } else if (id == R.id.sciOption && !isScientific) {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) landNormal.getLayoutParams();
                            LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();


                            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
                            ValueAnimator animate = ValueAnimator.ofFloat(1.0f, 0.0f);
                            animator.setDuration(300);
                            animate.setDuration(300);
                            animator.addUpdateListener(animation -> {
                                sciParams.weight = (Float) animator.getAnimatedValue();
                                sciLayout.setLayoutParams(sciParams);
                            });

                            animate.addUpdateListener(animation -> {
                                params.weight = (Float) animate.getAnimatedValue();
                                landNormal.setLayoutParams(params);
                            });
                            animator.start();
                            animate.start();
                            isBasic = false;
                            isScientific = true;
                        }
                        return true;
                    });
                }
            });
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mainText", mainText.getText().toString());
        outState.putString("previewText", previewText.getText().toString());
    }

    protected static boolean isOperator(char var) {
        return (var == '+' || var == '-' || var == '×' || var == '÷' || var == '%');
    }


    protected void getButtons(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Button) {
                buttons.add(child);
            } else if (child instanceof ViewGroup) {
                getButtons((ViewGroup) child);
            }
        }
    }

    private boolean isMathError() {
        return mainText.getText().toString().equals("Math Error!");
    }

    private boolean isInsideTrig() {
        String text = mainText.getText().toString();
        String[] trigFuncs = {"sin(", "cos(", "tan(", "sinh(", "cosh(", "tanh("};
        for (String func : trigFuncs) {
            int index = text.lastIndexOf(func);
            if (index != -1) {
                int closeIndex = text.indexOf(")", index);
                if (closeIndex == -1) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean hasOperator(String exp) {
        for (int i = 0; i < exp.length(); i++) {
            if (isOperator(exp.charAt(i)))
                return true;
        }
        return false;
    }

    private String preprocessExpression(String expr) {

        while (expr.contains("log₁₀(")) {
            int start = expr.indexOf("log₁₀(");
            int open = start + 5;
            int close = expr.indexOf(")", open);
            if (close == -1) throw new IllegalArgumentException("Invalid log₁₀ syntax");

            String inside = expr.substring(open + 1, close);
            double val = evalExpression(preprocessExpression(inside));
            if (val <= 0) throw new ArithmeticException("log₁₀ undefined for ≤ 0");
            double logResult = Math.log10(val);

            expr = expr.substring(0, start) + logResult + expr.substring(close + 1);
        }

        while (expr.contains("ln(")) {
            int start = expr.indexOf("ln(");
            int open = start + 2;
            int close = expr.indexOf(")", open);
            if (close == -1) throw new IllegalArgumentException("Invalid ln syntax");
            String inside = expr.substring(open + 1, close);
            double val = evalExpression(preprocessExpression(inside));
            if (val <= 0) throw new ArithmeticException("ln undefined for ≤ 0");
            double lnResult = Math.log(val);
            expr = expr.substring(0, start) + lnResult + expr.substring(close + 1);
        }

        while (expr.contains("e^")) {
            int i = expr.indexOf("e^");

            int start = i + 2;
            int end = start;
            while (end < expr.length() && (Character.isDigit(expr.charAt(end)) || expr.charAt(end) == '.'))
                end++;
            String expStr = expr.substring(start, end);
            double exponent = evalExpression(preprocessExpression(expStr));
            double result = Math.exp(exponent);

            expr = expr.substring(0, i) + result + expr.substring(end);
        }


        while (expr.contains("sin") || expr.contains("cos") || expr.contains("tan")) {
            int start = -1;
            String func = "";

            for (String f : new String[]{"sinh", "cosh", "tanh", "sin", "cos", "tan"}) {
                int idx = expr.indexOf(f);
                if (idx != -1 && (start == -1 || idx < start)) {
                    start = idx;
                    func = f;
                }
            }
            if (start == -1) break;
            int open = expr.indexOf("(", start);
            int close = expr.indexOf(")", open);
            if (open == -1 || close == -1)
                throw new IllegalArgumentException("Invalid trig syntax");

            String inside = expr.substring(open + 1, close);
            double val = evalExpression(inside);
            double radians = Math.toRadians(val);
            double trigResult;

            switch (func) {
                case "sin":
                    trigResult = Math.sin(radians);
                    break;
                case "cos":
                    trigResult = Math.cos(radians);
                    break;
                case "tan":
                    if ((val % 180) == 90) {
                        throw new ArithmeticException("Tan undefined at " + val);
                    }
                    trigResult = Math.tan(radians);
                    break;
                case "sinh":
                    trigResult = Math.sinh(radians);
                    break;
                case "cosh":
                    trigResult = Math.cosh(radians);
                    break;
                case "tanh":
                    trigResult = Math.tanh(radians);
                    break;
                default:
                    trigResult = 0;
            }
            if (Math.abs(trigResult) < 1e-10) trigResult = 0;
            expr = expr.substring(0, start) + trigResult + expr.substring(close + 1);
        }

        while (expr.contains("!")) {
            int idx = expr.indexOf("!");
            int start = idx - 1;
            while (start >= 0 && (Character.isDigit(expr.charAt(start)) || expr.charAt(start) == '.')) {
                start--;
            }
            start++;
            String numberStr = expr.substring(start, idx);
            double number = Double.parseDouble(numberStr);

            if (number < 0 || number != Math.floor(number)) {
                throw new ArithmeticException("Factorial only defined for non-negative integers");
            }

            double fact = factorial((int) number);
            expr = expr.substring(0, start) + fact + expr.substring(idx + 1);
        }

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '²' || c == '³') {
                int start = i - 1;
                while (start >= 0 && (Character.isDigit(expr.charAt(start)) || expr.charAt(start) == '.'))
                    start--;
                start++;
                String numberStr = expr.substring(start, i);
                double number = Double.parseDouble(numberStr);
                double power = (c == '²') ? Math.pow(number, 2) : Math.pow(number, 3);
                expr = expr.substring(0, start) + power + expr.substring(i + 1);
                i = start + String.valueOf(power).length() - 1;
            }
        }

        while (expr.contains("^")) {
            int i = expr.indexOf("^");

            int l = i - 1;
            while (l >= 0 && (Character.isDigit(expr.charAt(l)) || expr.charAt(l) == '.')) l--;
            double base = Double.parseDouble(expr.substring(l + 1, i));

            int r = i + 1;
            while (r < expr.length() && (Character.isDigit(expr.charAt(r)) || expr.charAt(r) == '.'))
                r++;
            double pow = Double.parseDouble(expr.substring(i + 1, r));

            double result = Math.pow(base, pow);
            expr = expr.substring(0, l + 1) + result + expr.substring(r);
        }

        while (expr.contains("√")) {
            int i = expr.indexOf("√");
            int r = i + 1;
            while (r < expr.length() && (Character.isDigit(expr.charAt(r)) || expr.charAt(r) == '.'))
                r++;
            double val = Double.parseDouble(expr.substring(i + 1, r));
            double result = Math.sqrt(val);
            expr = expr.substring(0, i) + result + expr.substring(r);
        }

        return expr;
    }

    private double factorial(int n) {
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private double evalExpression(String expr) {
        expr = expr.replace("÷", "/").replace("×", "*");
        expr = expr.replaceAll("--", "+");

        ArrayList<Double> nums = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();

        StringBuilder num = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                num.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                if (num.length() == 0 && c == '-') {
                    num.append('-');
                    continue;
                }
                nums.add(Double.parseDouble(num.toString()));
                num.setLength(0);
                ops.add(c);
            }
        }
        if (num.length() > 0)
            nums.add(Double.parseDouble(num.toString()));

        for (int i = 0; i < ops.size(); i++) {
            char op = ops.get(i);
            if (op == '*' || op == '/' || op == '%') {
                double a = nums.get(i);
                double b = nums.get(i + 1);
                double r = 0;
                switch (op) {
                    case '*':
                        r = a * b;
                        break;
                    case '/':
                        if (b == 0) throw new ArithmeticException("Div By 0");
                        r = a / b;
                        break;
                    case '%':
                        r = a % b;
                        break;
                }
                nums.set(i, r);
                nums.remove(i + 1);
                ops.remove(i--);
            }
        }

        double result = nums.get(0);
        for (int i = 0; i < ops.size(); i++) {
            char op = ops.get(i);
            double b = nums.get(i + 1);
            if (op == '+') result += b;
            else if (op == '-') result -= b;
        }

        return result;
    }

}
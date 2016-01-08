import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private final int CANCEL = 0;
    private final int NUMBER1 = 1;
    private final int NUMBER2 = 2;
    private final int OPERATOR = 3;
    private final int EQUAL = 4;

    private int state = CANCEL;

    private double firstNumber = 0;
    private String operator = "";

    private String[] buttonText = {"7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "1/x", "+",
        "C", "="};

    private String result = "0";

    private JTextField textfield = new JTextField(result);
    private JButton[] button = new JButton[buttonText.length];

    private JPanel aPanel = new JPanel(new GridLayout(4, 4));
    private JPanel bPanel = new JPanel(new GridLayout(2, 1));

    public Calculator() {
        setLayout(new BorderLayout());
        add(textfield, BorderLayout.NORTH);
        add(aPanel, BorderLayout.CENTER); //OR BorderLayout.WEST
        add(bPanel, BorderLayout.EAST);
        for (int i = 0; i < 16; i++) {
            button[i] = new JButton(buttonText[i]);
            aPanel.add(button[i]);
            button[i].addActionListener(this);
        }
        for (int i = 16; i < 18; i++) {
            button[i] = new JButton(buttonText[i]);
            bPanel.add(button[i]);
            button[i].addActionListener(this);
        }

        textfield.setHorizontalAlignment(JTextField.RIGHT);
        textfield.setEditable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        String sourceText = ((JButton) source).getText();
        System.out.println("ButtonPressed = " + sourceText);
        if (sourceText.compareTo("0") >= 0 && sourceText.compareTo("9") <= 0 && sourceText.length() == 1 || sourceText.equals(".")) {
            if (state == CANCEL || state == EQUAL) {
                result = sourceText;
                state = NUMBER1;
            } else if (state == OPERATOR) {
                result = sourceText;
                state = NUMBER2;
            } else if (state == NUMBER1 || state == NUMBER2) {
                if (!sourceText.equals(".") || (sourceText.equals(".") && !result.contains("."))) {
                    result = result + sourceText;
                }
            }
        } else if (sourceText.equals("C")) {
            result = "0";
            state = CANCEL;
        } else if (sourceText.equals("=")) {
            if (state != EQUAL && !operator.equals("")) {
                result = "" + calcuate(firstNumber, Double.parseDouble(result), operator);
            }
            state = EQUAL;
            firstNumber = 0;
            operator = "";
        } else if (sourceText.equals("1/x")) {
            state = EQUAL;
            double x = Double.parseDouble(result);
            x = 1 / x;
            result = "" + x;
        } else {

            if (state != OPERATOR) {
                result = "" + calcuate(firstNumber, Double.parseDouble(result), operator);
            }
            operator = sourceText;
            state = OPERATOR;
            firstNumber = Double.parseDouble(result);
        }
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length()-2);
        }
        textfield.setText(result);
    }

    public double calcuate(double firstNumber, double secondNumber, String operator) {
        double result = 0;
        if (operator.equals("+")) {
            result = firstNumber + secondNumber;
        } else if (operator.equals("-")) {
            result = firstNumber - secondNumber;
        } else if (operator.equals("*")) {
            result = firstNumber * secondNumber;
        } else if (operator.equals("/")) {
            result = firstNumber / secondNumber;
        } else {
            result = secondNumber;
        }
        return result;
    }
}

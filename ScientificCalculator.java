import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ScientificCalculator extends JFrame {
    private JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;

    public ScientificCalculator() {
        // Set up the frame
        setTitle("Scientific Calculator - New Theme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // New color scheme: Cool gradient theme
        Color backgroundColor = new Color(26, 188, 156);  // Teal
        Color buttonColor = new Color(41, 128, 185);      // Deep blue
        Color displayColor = new Color(240, 248, 255);    // Alice blue
        Color textColor = new Color(15, 50, 80);          // Dark teal

        // Create display
        display = new JTextField("0");
        display.setEditable(false);
        display.setBackground(displayColor);
        display.setForeground(textColor);
        display.setFont(new Font("Verdana", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(buttonColor, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        add(display, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5, 5, 5));
        buttonPanel.setBackground(backgroundColor);

        // Button labels and actions
        String[] buttons = {
            "C", "sin", "cos", "tan", "√",
            "7", "8", "9", "/", "^",
            "4", "5", "6", "*", "log",
            "1", "2", "3", "-", "ln",
            "0", ".", "=", "+", "(",
            ")", "", "", "", ""
        };

        // Add buttons
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Verdana", Font.PLAIN, 16));
            button.setBackground(buttonColor);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.addActionListener(new ButtonListener());
            buttonPanel.add(button);

            // Custom hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(buttonColor.brighter());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(buttonColor);
                }
            });
        }

        // Add panel to frame
        add(buttonPanel, BorderLayout.CENTER);

        // Set frame background
        getContentPane().setBackground(backgroundColor);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if (command.equals("C")) {
                start = true;
                result = 0;
                display.setText("0");
            } else if (command.equals("=")) {
                try {
                    calculate(Double.parseDouble(display.getText()));
                    lastCommand = "=";
                    start = true;
                } catch (NumberFormatException e) {
                    display.setText("Error");
                    start = true;
                }
            } else if ("+-*/^".contains(command)) {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            } else if (command.equals("√")) {
                calculate(Math.sqrt(Double.parseDouble(display.getText())));
                lastCommand = "=";
                start = true;
            } else if (command.equals("sin")) {
                calculate(Math.sin(Math.toRadians(Double.parseDouble(display.getText()))));
                lastCommand = "=";
                start = true;
            } else if (command.equals("cos")) {
                calculate(Math.cos(Math.toRadians(Double.parseDouble(display.getText()))));
                lastCommand = "=";
                start = true;
            } else if (command.equals("tan")) {
                calculate(Math.tan(Math.toRadians(Double.parseDouble(display.getText()))));
                lastCommand = "=";
                start = true;
            } else if (command.equals("log")) {
                calculate(Math.log10(Double.parseDouble(display.getText())));
                lastCommand = "=";
                start = true;
            } else if (command.equals("ln")) {
                calculate(Math.log(Double.parseDouble(display.getText())));
                lastCommand = "=";
                start = true;
            } else if (command.equals("(") || command.equals(")")) {
                if (start) {
                    display.setText(command);
                    start = false;
                } else {
                    display.setText(display.getText() + command);
                }
            } else {
                if (start) {
                    display.setText(command);
                    start = false;
                } else {
                    display.setText(display.getText() + command);
                }
            }
        }

        private void calculate(double x) {
            DecimalFormat df = new DecimalFormat("#.######");
            switch (lastCommand) {
                case "+":
                    result += x;
                    break;
                case "-":
                    result -= x;
                    break;
                case "*":
                    result *= x;
                    break;
                case "/":
                    if (x != 0) result /= x;
                    else display.setText("Error");
                    break;
                case "^":
                    result = Math.pow(result, x);
                    break;
                default:
                    result = x;
            }
            display.setText(df.format(result));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScientificCalculator calc = new ScientificCalculator();
            calc.setVisible(true);
        });
    }
}
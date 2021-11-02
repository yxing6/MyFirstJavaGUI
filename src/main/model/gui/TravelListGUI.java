package model.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TravelListGUI {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static JFrame frame;
    private static JPanel panel;

    private static JLabel userLabel;
    private static JLabel passwordLabel;
    private static JLabel successLabel;
    private static JTextField userText;
    private static JPasswordField passwordText;
    private static JButton button;

//    /**
//     * Helper to add control buttons.
//     */
//    private void addButtonPanel() {
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(4,2));
//        buttonPanel.add(new JButton(new AddCodeAction()));
//        buttonPanel.add(new JButton(new RemoveCodeAction()));
//        buttonPanel.add(createPrintCombo());
//
//        panel.add(buttonPanel, BorderLayout.WEST);
//    }


    public static void main(String[] args) {


            frame = new JFrame();
            panel = new JPanel();

            frame.setTitle("My travel list");
            frame.setSize(WIDTH, HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);

//        frame.pack();
//
//        frame.add(panel, BorderLayout.SOUTH);

//        panel.setLayout(new GridLayout(0, 1));
            panel.setLayout(null);

            userLabel = new JLabel("User");
            userLabel.setBounds(10, 20, 80, 25);
            panel.add(userLabel);

            userText = new JTextField(20);
            userText.setBounds(100, 20, 165,25);
            panel.add(userText);

            passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(10, 50, 80, 25);
            panel.add(passwordLabel);

            passwordText = new JPasswordField();
            passwordText.setBounds(100, 50, 165, 25);
            panel.add(passwordText);


            button = new JButton("login");
            button.setBounds(10, 80, 80, 25);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    {
                        String user = userText.getText();
                        String password = passwordText.getText();
                        System.out.println(user + ", " + password);

                        if (user.equals("Yun") && password.equals("123")) {
                            successLabel.setText("Login successful!");
                        }
                    }
                }
            });
            panel.add(button);


            successLabel = new JLabel("");
            successLabel.setBounds(10, 110, 300, 25);
            panel.add(successLabel);

            frame.setVisible(true);

//        button.addActionListener(this);
//
//
//
//
//        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
//
//        panel.add(button);
//        panel.add(label);
        }

}

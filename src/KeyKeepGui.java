import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.*;

public class KeyKeepGui {
    PasswordManager pManager = new PasswordManager();
    JFrame frame = new JFrame();
    JPanel panelScroll = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelScroll);

    public void gui() {
        JButton btnAdd = new JButton("ADD");
        btnAdd.setBounds(25, 25, 100, 50);
        btnAdd.setBackground(new Color(0x011525));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(e -> {
            JLabel lblPlatform = new JLabel("Platform: ");
            lblPlatform.setBounds(25, 25, 200, 25);
            JTextField txtFieldPlatform = new JTextField();
            txtFieldPlatform.setBounds(25, 50, 200, 25);
            
            JLabel lblEmail = new JLabel("Email: ");
            lblEmail.setBounds(25, 75, 200, 25);
            JTextField txtFieldEmail = new JTextField();
            txtFieldEmail.setBounds(25, 100, 200, 25);

            JLabel lblPassword = new JLabel("Password: ");
            lblPassword.setBounds(25, 125, 200, 25);
            JPasswordField txtFieldPassword = new JPasswordField();
            txtFieldPassword.setBounds(25, 150, 200, 25);
            JFrame frameAdd = new JFrame();

            JButton btnSubmitCred = new JButton("Submit");
            btnSubmitCred.setBounds(150, 185, 75, 25);
            btnSubmitCred.setBackground(new Color(0x011525));
            btnSubmitCred.setForeground(Color.WHITE);
            btnSubmitCred.addActionListener(f -> {
                String strPlat = txtFieldPlatform.getText();
                String strEmail = txtFieldEmail.getText();
                String strPass = new String(txtFieldPassword.getPassword());
                pManager.appendAccount(strPlat, strEmail, strPass);
                refreshFrame();
                frameAdd.dispose();
            });

            frameAdd.setSize(260, 275);
            frameAdd.setLayout(null);
            frameAdd.setResizable(false);

            frameAdd.setVisible(true);
            frameAdd.add(lblPlatform);
            frameAdd.add(txtFieldPlatform);
            frameAdd.add(lblEmail);
            frameAdd.add(txtFieldEmail);
            frameAdd.add(lblPassword);
            frameAdd.add(txtFieldPassword);
            frameAdd.add(btnSubmitCred);
        });
	
        JButton btnPassGen = new JButton("GENERATE");
        btnPassGen.setBounds(25, 95, 100, 50);
        btnPassGen.setBackground(new Color(0x011525));
        btnPassGen.setForeground(Color.WHITE);
        btnPassGen.setFocusable(false);
        btnPassGen.addActionListener(e -> {   
            JPanel pLeft = new JPanel();
            pLeft.setBounds(10, 10, 230, 230);
            pLeft.setBackground(new Color(0xee4d0e));
            pLeft.setLayout(null);

            JPanel pRight = new JPanel();
            pRight.setBounds(255, 10, 230, 230);
            pRight.setBackground(Color.GRAY);
            pRight.setLayout(null);
  
            JRadioButton radPassword = new JRadioButton("Password");
            radPassword.setBounds(25, 25, 100, 25);
            radPassword.setSelected(true);
            radPassword.setBackground(new Color(0, 0 ,0, 0));
            radPassword.setFocusable(false);
            radPassword.addItemListener(f -> {
                if (radPassword.isSelected()) {
                    pLeft.setBackground(new Color(0xee4d0e));                    
                }

                else {
                    pLeft.setBackground(Color.GRAY);
                }
            });

            JRadioButton radPassphrase = new JRadioButton("Passphrase");
            radPassphrase.setBounds(25, 25, 100, 25);
            radPassphrase.setBackground(new Color(0, 0, 0, 0));
            radPassphrase.setFocusable(false);
            radPassphrase.addItemListener(f -> {
                if (radPassphrase.isSelected()) {
                    pRight.setBackground(new Color(0xee4d0e));
                }

                else {
                    pRight.setBackground(Color.GRAY);
                }
            });

            ButtonGroup radGroup = new ButtonGroup();
            radGroup.add(radPassword);
            radGroup.add(radPassphrase);
            
            JLabel lblLen = new JLabel("Length");
            lblLen.setBounds(35, 50, 100, 25);

            String[] strInput = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
            JComboBox cbLen = new JComboBox<String>(strInput);
            cbLen.setBounds(155, 50, 50, 25);

            JCheckBox chkUpperCase = new JCheckBox("Uppercase");
            chkUpperCase.setBounds(25, 75, 150, 25);
            chkUpperCase.setBackground(new Color(0, 0, 0, 0));
            chkUpperCase.setFocusable(false);

            

            JCheckBox chkNum = new JCheckBox("Numbers");
            chkNum.setBounds(25, 125, 150, 25);
            chkNum.setBackground(new Color(0, 0, 0, 0));
            chkNum.setFocusable(false);

            JCheckBox chkPunc = new JCheckBox("Punctuations");
            chkPunc.setBounds(25, 150, 150, 25);
            chkPunc.setBackground(new Color(0, 0, 0, 0));
            chkPunc.setFocusable(false);

            JCheckBox chkLowerCase = new JCheckBox("Lowercase");
            chkLowerCase.setBounds(25, 100, 150, 25);
            chkLowerCase.setBackground(new Color(0, 0, 0, 0));
            chkLowerCase.setFocusable(false);
            chkLowerCase.setSelected(true);
            chkLowerCase.setEnabled(false);

            JLabel lblLenPhrase = new JLabel("Length");
            lblLenPhrase.setBounds(35, 50, 100, 25);

            String[] strInputPhrase = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
            JComboBox cbLenPhrase = new JComboBox<String>(strInputPhrase);
            cbLenPhrase.setBounds(155, 50, 50, 25);

            JFrame frameGen = new JFrame();
            frameGen.setSize(500, 360);
            frameGen.setResizable(false);
            frameGen.setLayout(null);
            frameGen.setVisible(true);
            
            JTextField txtPassGen = new JTextField();
            txtPassGen.setBounds(10, 255, 480, 25);
            txtPassGen.setEditable(false);  

            JButton btnGen = new JButton("GENERATE");
            btnGen.setBounds(340, 290, 150, 25);
            btnGen.setForeground(Color.WHITE);
            btnGen.setBackground(new Color(0x011525));
            btnGen.setFocusable(false);
            btnGen.addActionListener(f -> {
                if (radPassword.isSelected()) {
                    TraditionalPasswordGenerator tPGen = new TraditionalPasswordGenerator();

                    String strStrongPass = tPGen.mainGeneratePassword(Integer.parseInt(cbLen.getSelectedItem().toString()), chkUpperCase.isSelected(), chkLowerCase.isSelected(), chkNum.isSelected(), chkPunc.isSelected());
                    txtPassGen.setText(strStrongPass);
                }

                else {
                    PassphraseGeneratorV2 pPGen = new PassphraseGeneratorV2();
                    String strStrongPhrase = pPGen.mainPassphraseGenerator(Integer.parseInt(cbLenPhrase.getSelectedItem().toString()));
                    txtPassGen.setText(strStrongPhrase);
                }
            });

            pLeft.add(radPassword);
            pLeft.add(lblLen);
            pLeft.add(cbLen);
            pLeft.add(chkUpperCase);
            pLeft.add(chkLowerCase);
            pLeft.add(chkNum);
            pLeft.add(chkPunc);
            
            pRight.add(radPassphrase);
            pRight.add(lblLenPhrase);
            pRight.add(cbLenPhrase);

            frameGen.add(pLeft);
            frameGen.add(pRight);
            frameGen.add(txtPassGen);
            frameGen.add(btnGen);
        });

        JButton btnExit = new JButton("EXIT");
        btnExit.setBounds(25, 400, 100, 50);
        btnExit.setBackground(new Color(0x011525));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusable(false);
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        
        panelScroll.setLayout(new BoxLayout(panelScroll, BoxLayout.Y_AXIS));
        panelScroll.setBackground(new Color(0xFAF9F6));


        refreshFrame();
        

        scrollPane.setBounds(135, 25, 345, 425);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.setTitle("KeyKeep");    
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.add(btnAdd);
        frame.add(btnPassGen);
        frame.add(btnExit);
        frame.add(scrollPane);
    }

    private void refreshFrame() {
        panelScroll.removeAll();
        panelScroll.revalidate();
        panelScroll.repaint();

        String strCredentials = pManager.listAccounts();
        
        if (!strCredentials.equals("")) {
            String[] strButton = strCredentials.split("\\n");
            
            String  strPlatformTemp = "";
            String strEmailTmp = "";

            for (String strAcc: strButton) {
                if (strAcc.startsWith("Platform: ")) {
                    strPlatformTemp = strAcc.substring(10);
                    JLabel lblTmp = new JLabel(strPlatformTemp);
                    lblTmp.setForeground(new Color(0x011525));
                    panelScroll.add(Box.createHorizontalStrut(30));
                    panelScroll.add(Box.createVerticalStrut(10));
                    panelScroll.add(lblTmp);
                    continue;
                }

                else if (strAcc.startsWith("Email: ")) {
                    strEmailTmp = strAcc.substring(7);
                    continue;
                }

                else if (strAcc.startsWith("Password: ")) {
                    JButton btnAcc = new JButton(strEmailTmp);
                    String strInput = strPlatformTemp + "\n" + strEmailTmp + "\n" + strAcc.substring(10);
                    btnAcc.addActionListener(e -> {
                        String[] strArr = strInput.split("\\n");

                        JFrame frameAcc = new JFrame();

                        JLabel lblPlatform = new JLabel("Platform:");
                        lblPlatform.setBounds(25, 25, 75, 25);

                        JTextField txtPlatform = new JTextField(strArr[0]);
                        txtPlatform.setEditable(false);
                        txtPlatform.setBounds(100, 25, 175, 25);

                        JLabel lblEmail = new JLabel("Email:");
                        lblEmail.setBounds(25, 70, 75, 25);

                        JTextField txtEmail = new JTextField(strArr[1]);
                        txtEmail.setEditable(false);
                        txtEmail.setBounds(100, 70, 175, 25);

                        JLabel lblPass = new JLabel("Password:");
                        lblPass.setBounds(25, 120, 75, 25);

                        JTextField txtPass = new JTextField(strArr[2]);
                        txtPass.setBounds(100, 120, 175, 25);

                        JButton btnDel = new JButton("DELETE");
                        btnDel.setBackground(new Color(0x011525));
                        btnDel.setForeground(Color.WHITE);
                        btnDel.setBounds(25, 170, 110, 25);
                        btnDel.addActionListener(f-> {
                            pManager.updateAccount(strArr[0], strArr[1]);
                            refreshFrame();
                            frameAcc.dispose();
                        });                        

                        JButton btnUp = new JButton("UPDATE");
                        btnUp.setBackground(new Color(0xee4d0e));
                        btnUp.setForeground(Color.WHITE);
                        btnUp.setBounds(165, 170, 110, 25);
                        btnUp.addActionListener(f -> {
                            pManager.updateAccount(strArr[0], strArr[1], txtPass.getText());
                            refreshFrame();
                        });

                        frameAcc.setResizable(false);
                        frameAcc.setVisible(true);
                        frameAcc.setLayout(null);
                        frameAcc.setSize(300, 260);

                        frameAcc.add(lblPlatform);
                        frameAcc.add(txtPlatform);
                        frameAcc.add(lblEmail);
                        frameAcc.add(txtEmail);
                        frameAcc.add(lblPass);
                        frameAcc.add(txtPass);
                        frameAcc.add(btnDel);
                        frameAcc.add(btnUp);
                    });
                    btnAcc.setMinimumSize(new Dimension(295, 25));
                    btnAcc.setMaximumSize(new Dimension(295, 25));
                    btnAcc.setBackground(new Color(0xee4d0e));
                    btnAcc.setForeground(Color.WHITE);

                    panelScroll.add(Box.createHorizontalStrut(30));
                    panelScroll.add(Box.createVerticalStrut(5));
                    panelScroll.add(btnAcc);
                    
                }

                else {
                    panelScroll.add(new JLabel("ERROR in CREDENTIALS FILE"));
                }

            }
        }
    }
}

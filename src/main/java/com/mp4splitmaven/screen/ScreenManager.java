package com.mp4splitmaven.screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScreenManager extends ScreenVariables {

    private static ScreenManager instace = new ScreenManager();
    public static ScreenManager getInstace(){
        return instace;
    }

    private ScreenManager(){
        if(frame == null){
            createFram();
        }
    }

    private void createFram(){

        frame = new JFrame(UI_NAME);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(280,290);



        mainPanel = getMainPanel();
        logPanel = getLogPanel();
        menuBar = getMenuBar();

        cards.add(mainPanel,UI_BAR_ITEM_LOOKATMAINSCREEN);
        cards.add(logPanel,UI_BAR_ITEM_LOOKATLOG);

        frame.getContentPane().add(cards);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
    private JPanel getMainPanel(){
        JPanel jPanel = new JPanel(new GridBagLayout());

        JLabel details = new JLabel("Mp4Split Details:");
        JLabel nothing = new JLabel();

        JLabel numberOfTimestamps = new JLabel(UI_LABEL_TIMESTAMP);
        JLabel currently = new JLabel(UI_LABEL_CURRENTLY);




        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.NORTH;


        constraints.gridx = 0;
        constraints.gridy = 0;
        jPanel.add(details, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        jPanel.add(nothing, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        jPanel.add(numberOfTimestamps, constraints);

        constraints.gridx = 1;
        jPanel.add(numberOfTimestampsAns, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        jPanel.add(currently, constraints);

        constraints.gridx = 1;
        jPanel.add(currentlyAns, constraints);

        return jPanel;
    }

    private JPanel getLogPanel(){
        JPanel jPanel = new JPanel(new BorderLayout());

        logTextArea = getLogTextArea();

        JScrollPane editorScrollPane = new JScrollPane(logTextArea);
        new SmartScroller(editorScrollPane);
        EmptyBorder border = new EmptyBorder(10,10,10,10);

        jPanel.setBorder(border);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel.add(editorScrollPane);

        return jPanel;
    }
    private JTextArea getLogTextArea(){
        JTextArea jtextArea = new JTextArea();
        jtextArea.setEditable(false);
        jtextArea.setLineWrap(true);
        jtextArea.setWrapStyleWord(true);
        return jtextArea;
    }

    private JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();


        JMenu menu = new JMenu(UI_BAR_MENU);
            JMenuItem lookAtMainScreen = new JMenuItem(UI_BAR_ITEM_LOOKATMAINSCREEN);
            JMenuItem lookAtLog = new JMenuItem(UI_BAR_ITEM_LOOKATLOG);
            JMenuItem finalCut = new JMenuItem(UI_BAR_ITEM_FINALCUT);
            JMenuItem exit = new JMenuItem(UI_BAR_ITEM_EXIT);

        JMenu edit = new JMenu(UI_BAR_EDIT);
            JMenuItem clearLog = new JMenuItem(UI_BAR_ITEM_CLEARLOG);

        JMenu help = new JMenu(UI_BAR_HELP);
            JMenu needHelp = new JMenu(UI_BAR_ITEM_HELP);
                JMenuItem mainPage = new JMenuItem(UI_BAR_ITEM_MAINPAGE);
                JMenuItem instruction = new JMenuItem(UI_BAR_ITEM_INSTRUCTIONS);
                JMenuItem helpSettings = new JMenuItem(UI_BAR_ITEM_SETTINGS);
            JMenuItem about = new JMenuItem(UI_BAR_ITEM_ABOUT);


        clearLog.addActionListener(clearLogAction);

        exit.addActionListener(exitAction);

        mainPage.addActionListener(mainPageAction);

        instruction.addActionListener(instructionAction);

        helpSettings.addActionListener(settingsAction);

        about.addActionListener(aboutAction);

        lookAtLog.addActionListener(lookAtLogAction);

        lookAtMainScreen.addActionListener(lookAtMainScreenAction);

        finalCut.addActionListener(finalCutAction);


        menuBar.add(menu);
        menuBar.add(edit);
        menuBar.add(help);

        menu.add(lookAtMainScreen);
        menu.add(lookAtLog);
        menu.add(finalCut);
        menu.add(exit);

        edit.add(clearLog);

        help.add(needHelp);
            needHelp.add(mainPage);
            needHelp.add(instruction);
            needHelp.add(helpSettings);
        help.add(about);

        return menuBar;
    }

    public static void displayToLog(String text){
        logTextArea.append(text);
    }

    public static void displayNumberOfTimestamp(int number){
        numberOfTimestampsAns.setText(number+"");
    }
    public static void displayCurrently(int number){
        if(number == 1){
            currentlyAns.setText(UI_LABEL_CURRENTLY_CUTTING);
        }else {
            currentlyAns.setText(UI_LABEL_CURRENTLY_NOTCUTTING);
        }
    }

}

package com.mp4splitmaven.Screen;

import com.mp4splitmaven.LoggingHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ScreenManager extends ScreenConstans{
    private final String uiName = "Mp4Split";

    private final String uiBarMenu = "Menu";
    private final String uiBarEdit = "Edit";
    private final String uiBarHelp = "Help";

    private final String uiBarMenuHelp = "Help";
    private final String uiBarItemAbout = "About";
    private final String uiBarItemLogs = "Clear Log";
    private final String uiBarItemExit = "Exit";
    private final String uiBarItemWebPage = "Main Website";
    private final String uiBarItemInstruction = "Instructions";
    private final String uiBarItemSettings = "Settings";

    private final String uiWebPagepUrl = "https://github.com/Neoministein/Mp4Split";
    private final String uiInstructionUrl = "https://github.com/Neoministein/Mp4Split/blob/master/UserInstructions.md";
    private final String uiSettingsUrl = "https://github.com/Neoministein/Mp4Split/blob/master/SettingsReadme.md";
    private final String uiAboutMessage = "Version: 1.2.5 \n";

    private JFrame frame;
    private JPanel logPanel = new JPanel(new BorderLayout());
    private JMenuBar menuBar;
    private static JTextArea textArea;

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

        frame = new JFrame(uiName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,400);

        EmptyBorder border = new EmptyBorder(10,10,10,10);

        logPanel.setBorder(border);


        loadTextArea();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        new SmartScroller(editorScrollPane);

        logPanel.add(editorScrollPane);

        menuBar = getMenuBar();
        frame.setJMenuBar(menuBar);

        frame.add(logPanel);
        frame.setVisible(true);
    }

    private JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();


        JMenu menu = new JMenu(uiBarMenu);
            JMenuItem clearLog = new JMenuItem(uiBarItemLogs);
            JMenuItem exit = new JMenuItem(uiBarItemExit);

        JMenu help = new JMenu(uiBarHelp);
            JMenu needHelp = new JMenu(uiBarMenuHelp);
                JMenuItem mainPage = new JMenuItem(uiBarItemWebPage);
                JMenuItem instruction = new JMenuItem(uiBarItemInstruction);
                JMenuItem helpSettings = new JMenuItem(uiBarItemSettings);
            JMenuItem about = new JMenuItem(uiBarItemAbout);


        clearLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
                textArea.replaceSelection("");
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        mainPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite(uiWebPagepUrl);
            }
        });

        instruction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite(uiInstructionUrl);
            }
        });

        helpSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite(uiSettingsUrl);
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        uiAboutMessage,
                        uiBarItemAbout,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        menuBar.add(menu);
        menuBar.add(help);

        menu.add(clearLog);
        menu.add(exit);

        help.add(needHelp);
        needHelp.add(mainPage);
        needHelp.add(instruction);
        needHelp.add(helpSettings);
        help.add(about);

        return menuBar;
    }


    private void loadTextArea(){


        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }
    private void openWebsite(String link){
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (Exception ex) {
            LoggingHandler.println(LoggingHandler.ERROR,"Could not open default browser",ex);
        }
    }

    public static void disPlayOnScreen(String text){
        textArea.append(text);
    }
}

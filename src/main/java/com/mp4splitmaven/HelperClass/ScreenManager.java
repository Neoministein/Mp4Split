package com.mp4splitmaven.HelperClass;

import com.mp4splitmaven.LoggingHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ScreenManager {
    private final String uiName = "Mp4Split";

    private final String uiBarMenu = "Menu";
    private final String uiBarEdit = "Edit";
    private final String uiBarHelp = "Help";

    private final String uiBarItemHelp = "Help";
    private final String uiBarItemAbout = "About";
    private final String uiBarItemLogs = "Logs";
    private final String uiBarItemSettings = "Change Settings";
    private final String uiBarItemExit = "Exit";

    private final String uiNeedHelpUrl = "https://github.com/Neoministein/Mp4Split";
    private final String uiAboutMessage = "Version: 1.2.5 \n";

    public static void main(String[] args) {
        ScreenManager screenManager = new ScreenManager();

        screenManager.runGUI();
    }

    public void runGUI(){

        JFrame frame = new JFrame(uiName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        frame.setVisible(true);

        JMenuBar menuBar = getMenuBar();
        frame.setJMenuBar(menuBar);
    }

    private JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();


        JMenu menu = new JMenu(uiBarMenu);
        JMenu edit = new JMenu(uiBarEdit);
        JMenu help = new JMenu(uiBarHelp);

        JMenuItem needHelp = new JMenuItem(uiBarItemHelp);
        JMenuItem about = new JMenuItem(uiBarItemAbout);
        JMenuItem log = new JMenuItem(uiBarItemLogs);
        JMenuItem changeSettings = new JMenuItem(uiBarItemSettings);
        JMenuItem exit = new JMenuItem(uiBarItemExit);



        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        needHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL(uiNeedHelpUrl).toURI());
                } catch (Exception ex) {
                    LoggingHandler.println(LoggingHandler.ERROR,"Could not open default browser",ex);
                }
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),
                        uiAboutMessage,
                        uiBarItemAbout,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        menuBar.add(menu);
        menuBar.add(edit);
        menuBar.add(help);

        menu.add(log);
        menu.add(exit);
        edit.add(changeSettings);

        help.add(needHelp);
        help.add(about);

        return menuBar;
    }
}

package com.mp4splitmaven.screen;

import com.mp4splitmaven.FinalCut;
import com.mp4splitmaven.LoggingHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public abstract class ScreenVariables {

    protected static final String UI_NAME = "Mp4Split";
    protected static final String UI_BAR_MENU = "Menu";
    protected static final String UI_BAR_EDIT = "Edit";
    protected static final String UI_BAR_HELP = "Help";

    protected static final String UI_BAR_ITEM_HELP = "Help";
    protected static final String UI_BAR_ITEM_ABOUT = "About";
    protected static final String UI_BAR_ITEM_CLEARLOG = "Clear Log";
    protected static final String UI_BAR_ITEM_EXIT = "Exit";
    protected static final String UI_BAR_ITEM_MAINPAGE = "Main Website";
    protected static final String UI_BAR_ITEM_INSTRUCTIONS = "Instructions";
    protected static final String UI_BAR_ITEM_SETTINGS = "Settings";
    protected static final String UI_BAR_ITEM_LOOKATLOG = "Log Screen";
    protected static final String UI_BAR_ITEM_LOOKATMAINSCREEN = "Main Screen";
    protected static final String UI_BAR_ITEM_FINALCUT = "Execute FinalCut";

    protected static final String UI_URL_MAINPAGE = "https://github.com/Neoministein/Mp4Split";
    protected static final String UI_URL_INSTRUCTION = "https://github.com/Neoministein/Mp4Split/blob/master/UserInstructions.md";
    protected static final String UI_URL_SETTINGS = "https://github.com/Neoministein/Mp4Split/blob/master/SettingsReadme.md";

    protected static final String UI_ABOUTMESSAGE = "Version: 1.2.5 \n";

    protected static final String UI_LABEL_TIMESTAMP = "Active Timestamps: ";
    protected static final String UI_LABEL_CURRENTLY = "Mp4Split is currently: ";
    protected static final String UI_LABEL_NUMBEROFTIMESTAMPS = "0";
    protected static final String UI_LABEL_CURRENTLY_CUTTING = "Cutting";
    protected static final String UI_LABEL_CURRENTLY_NOTCUTTING = "not Cutting";

    protected static final String UI_STARTFINALCUT = "Do you really want to execute FinalCut?";

    public static final int IS_CUTTING = 1;
    public static final int IS_NOTCUTTING = 0;

    protected JFrame frame;
    protected JPanel cards = new JPanel(new CardLayout());
    protected JPanel mainPanel = new JPanel();
    protected JPanel logPanel;
    protected JMenuBar menuBar;

    protected static JTextArea logTextArea;
    protected static JLabel numberOfTimestampsAns = new JLabel(UI_LABEL_NUMBEROFTIMESTAMPS);
    protected static JLabel currentlyAns = new JLabel(UI_LABEL_CURRENTLY_NOTCUTTING);

    protected ActionListener clearLogAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            logTextArea.selectAll();
            logTextArea.replaceSelection("");
        }
    };

    protected ActionListener exitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    };

    protected ActionListener mainPageAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openWebsite(UI_URL_MAINPAGE);
        }
    };

    protected ActionListener instructionAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openWebsite(UI_URL_INSTRUCTION);
        }
    };

    protected ActionListener settingsAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openWebsite(UI_URL_SETTINGS);
        }
    };

    protected ActionListener aboutAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,
                    UI_ABOUTMESSAGE,
                    UI_BAR_ITEM_ABOUT,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    };
    protected ActionListener lookAtLogAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setSize(300,400);
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, UI_BAR_ITEM_LOOKATLOG);
        }
    };
    protected ActionListener lookAtMainScreenAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setSize(280,290);
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, UI_BAR_ITEM_LOOKATMAINSCREEN);
        }
    };
    protected ActionListener finalCutAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int input = JOptionPane.showConfirmDialog(frame, UI_STARTFINALCUT,UI_BAR_ITEM_FINALCUT,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(input == 0){
                FinalCut finalCut = new FinalCut();

                finalCut.finalCut();
            }
        }
    };


    private void openWebsite(String link){
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (Exception ex) {
            LoggingHandler.println(LoggingHandler.ERROR,"Could not open default browser",ex);
        }
    }
}

package com.mp4splitmaven.HelperClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenManager {

    public static void main(String[] args) {
        String frameName = "Mp4Split";

        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        frame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);


        JMenu menu = new JMenu("Menu");
        JMenu help = new JMenu("Help");
        menuBar.add(menu);
        menuBar.add(help);

        JMenuItem about = new JMenuItem("About");
        help.add(about);

        JMenuItem changeSettings = new JMenuItem("Change Settings");
        menu.add(changeSettings);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        menu.add(exit);



    }

    public void closeApp(){}
}

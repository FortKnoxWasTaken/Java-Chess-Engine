package com.chess.engine.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Table {
    private final JFrame gameFrame;
    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    public Table()
    {
        this.gameFrame = new JFrame("JChess");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar (tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }

    private void populateMenuBar(final JMenuBar tableMenuBar)
    {
        tableMenuBar.add(createFileMenu());
    }

    private JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PNG File");
        openPGN.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that PNG file!");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }


}

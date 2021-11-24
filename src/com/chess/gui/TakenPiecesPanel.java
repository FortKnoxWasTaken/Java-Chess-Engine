package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TakenPiecesPanel extends JPanel {


    private final JPanel northPanel;
    private final JPanel southPanel;
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);

    public TakenPiecesPanel()
    {
        super(new BorderLayout());
        this.setBackground(Color.decode("0xFDF5E6"));
        this.setBorder(PANEL_BORDER);
        this.northPanel= new JPanel(new GridLayout(8,2));
        this.southPanel = new JPanel(new GridLayout(8,2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel,BorderLayout.NORTH);
        add(this.southPanel,BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);


    }

    public void redo(final Table.MoveLog movelog)
    {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece>  whiteTakenPiece = new ArrayList<>();
        final List<Piece>  blackTakenPiece = new ArrayList<>();

        for(final Move move : movelog.getMoves() )
        {
            if(move.isAttack())
            {
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite())
                {
                    whiteTakenPiece.add(takenPiece);
                }
                else if(takenPiece.getPieceAlliance().isBlack())
                {
                    blackTakenPiece.add(takenPiece);
                }
                else
                {
                    throw new RuntimeException("should not reach here");
                }
            }
        }
        Collections.sort(whiteTakenPiece, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(),o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPiece, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(),o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : whiteTakenPiece)
        {
            try
            {
                final BufferedImage image = ImageIO.read(new File("art/pieces/plain" +takenPiece.getPieceAlliance().toString().substring(0,1)+""+takenPiece.toString()));
                final ImageIcon icon = new ImageIcon(image);
                final JPanel imageLabel = new JPanel();
                this.southPanel.add(imageLabel);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(final Piece takenPiece : blackTakenPiece)
        {
            try
            {
                final BufferedImage image = ImageIO.read(new File("art/pieces/plain" +takenPiece.getPieceAlliance().toString().substring(0,1)+""+takenPiece.toString()));
                final ImageIcon icon = new ImageIcon(image);
                final JPanel imageLabel = new JPanel();
                this.southPanel.add(imageLabel);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        validate();

    }



}

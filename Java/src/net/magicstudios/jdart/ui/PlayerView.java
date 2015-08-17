package net.magicstudios.jdart.ui;

import java.awt.*;

import javax.swing.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.ScoreListener;
import net.magicstudios.jdart.event.ScoreEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Nick Cramer (d3k199) Doug Love (d3m431)
 * @version 1.0
 */
public class PlayerView extends JPanel implements ScoreListener{
    private final int DISPLAY_NONE = -1;
    private final int DISPLAY_NAME = 0;
    private final int DISPLAY_NAME_AND_ICON = 1;

    private static final String FONT_NAME = "Eraser";
    private static final Color BACKGROUNDCOLOR = new Color(100, 136, 110);
    private static final Color FOREGROUNDCOLOR = Color.white;

    private Player m_Player;
    private int m_DisplayType;
    private boolean m_CurrentPlayer;
    private boolean m_bIsScrolling = false;

    public PlayerView(){
        m_Player = null;
        m_DisplayType = DISPLAY_NONE;
    }

    public PlayerView(Player player) {
        try {
            m_Player = player;
            m_Player.addScoreListener(this);
            m_DisplayType = (m_Player.getIcon() != null) ? DISPLAY_NAME_AND_ICON : DISPLAY_NAME;

            jbInit();
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param state boolean
     */
    public void setScrolling(boolean state) {
      m_bIsScrolling = state;
    }

    public boolean isScrolling() {
      return m_bIsScrolling;
    }

    /**
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                this_componentResized(e);
            }
        });

        this.setMinimumSize(new Dimension(50, 50));
        this.setPreferredSize(new Dimension(50, 50));
    }

    public void scoreChanged(ScoreEvent evt) {
        repaint();
    }

    public void paint(Graphics g) {
        // paint name or name with icon
        // paint divider line
        // paint 20, 19, 18, 17, 16, 15 , B
        // paint divider line
        // paint score

        int width = getWidth();
        int height = getHeight();

        g.setColor(BACKGROUNDCOLOR);
        g.fillRect(0, 0, width, height);
        g.setColor(FOREGROUNDCOLOR);

        if (width <= 0 || height <= 0) {
            // no width or height
            return;
        }

        int rowHeight = height / 9;
        int rowHeightInset = (int)((double)rowHeight * .9);
        int leftInset = (int)((double)width * .1);
        int rightInset = (int)((double)width * .9);
        int playerNameBaseLine = (int)((double)rowHeight * .95);
        int playerNameWidth;
        int playerNameHeight = rowHeightInset;
        int playerNameLeft;
        String playerName;


        switch (m_DisplayType) {

          case DISPLAY_NAME_AND_ICON:
            int iconLeft = (int) ( (double) width * .1);
            int iconHeight = rowHeightInset;
            int iconWidth = (int) ( (double) width * .2);

            // make icon square to the size of the smaller of iconWidth or iconHeight
            if (iconWidth > iconHeight) {
              iconWidth = iconHeight;
            }
            else {
              iconHeight = iconWidth;
            }

            playerNameWidth = width - (iconLeft * 3 + iconWidth);
            playerNameLeft = iconLeft * 2 + iconWidth;
            playerName = m_Player.getName();

            g.drawImage(m_Player.getIcon().getImage(), iconLeft, iconHeight, iconWidth, iconHeight, null);
            break;

          case DISPLAY_NAME:
            playerNameWidth = (int) ( (double) width * .8);
            playerNameLeft = (int) ( (double) width * .1);
            playerName = m_Player.getName();
            break;

          default:
            playerNameWidth = 0;
            playerNameLeft = 0;
            playerName = "";
        }

        setFontSizeForBox(playerName, playerNameWidth, playerNameHeight, g);

        FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();
        Rectangle2D rec = g.getFont().getStringBounds(playerName, frc);

        playerNameLeft = (int)((width - rec.getWidth()) / 2);

        if(m_CurrentPlayer == true){
            g.setColor(Color.yellow);
        }
        else {
            g.setColor(Color.white);
        }

        if (m_Player.isEliminated()) {
          g.setColor(BACKGROUNDCOLOR.darker());
        }

        g.drawString(playerName, playerNameLeft, playerNameBaseLine);

        if (m_DisplayType != DISPLAY_NONE) {

          if (isScrolling()) {

            // draw legend
            String [] arrThrowHist = m_Player.getThrowHistoryAsStrings();
            drawString(g, leftInset, rightInset, rowHeight * 1, rowHeight * 2, arrThrowHist[0]);
            drawString(g, leftInset, rightInset, rowHeight * 2, rowHeight * 3, arrThrowHist[1]);
            drawString(g, leftInset, rightInset, rowHeight * 3, rowHeight * 4, arrThrowHist[2]);
            drawString(g, leftInset, rightInset, rowHeight * 4, rowHeight * 5, arrThrowHist[3]);
            drawString(g, leftInset, rightInset, rowHeight * 5, rowHeight * 6, arrThrowHist[4]);
            drawString(g, leftInset, rightInset, rowHeight * 6, rowHeight * 7, arrThrowHist[5]);
            drawString(g, leftInset, rightInset, rowHeight * 7, rowHeight * 8, arrThrowHist[6]);
            drawLine(g, leftInset, rightInset, (8 * rowHeight));
            drawScore(g, leftInset, rightInset, rowHeight * 8, rowHeight * 9, m_Player.getScore());

          } else {
            // draw player
            drawLine(g, leftInset, rightInset, rowHeight);

            drawCount(g, leftInset, rightInset, rowHeight * 1, rowHeight * 2, m_Player.getZone(20));
            drawCount(g, leftInset, rightInset, rowHeight * 2, rowHeight * 3, m_Player.getZone(19));
            drawCount(g, leftInset, rightInset, rowHeight * 3, rowHeight * 4, m_Player.getZone(18));
            drawCount(g, leftInset, rightInset, rowHeight * 4, rowHeight * 5, m_Player.getZone(17));
            drawCount(g, leftInset, rightInset, rowHeight * 5, rowHeight * 6, m_Player.getZone(16));
            drawCount(g, leftInset, rightInset, rowHeight * 6, rowHeight * 7, m_Player.getZone(15));
            drawCount(g, leftInset, rightInset, rowHeight * 7, rowHeight * 8, m_Player.getZone(0));
            drawLine(g, leftInset, rightInset, (8 * rowHeight));
            drawScore(g, leftInset, rightInset, rowHeight * 8, rowHeight * 9, m_Player.getScore());
          }
        }
        else{
            // draw legend
            drawString(g, leftInset, rightInset, rowHeight * 1, rowHeight * 2, "20");
            drawString(g, leftInset, rightInset, rowHeight * 2, rowHeight * 3, "19");
            drawString(g, leftInset, rightInset, rowHeight * 3, rowHeight * 4, "18");
            drawString(g, leftInset, rightInset, rowHeight * 4, rowHeight * 5, "17");
            drawString(g, leftInset, rightInset, rowHeight * 5, rowHeight * 6, "16");
            drawString(g, leftInset, rightInset, rowHeight * 6, rowHeight * 7, "15");
            drawString(g, leftInset, rightInset, rowHeight * 7, rowHeight * 8, "BE");
        }
    }

    private void drawCount(Graphics g, int startX, int endX, int startY, int endY, int count){
        Rectangle2D rec;

        switch(count){
        case 0:
            break;
        case 1:
            rec = setFontSizeForBox("/", endX - startX, endY - startY, g);
            g.drawString("/", startX + (endX - startX) / 2 - (rec.getBounds().width / 2), startY + (endY - startY));
            break;
        case 2:
            rec = setFontSizeForBox("X", endX - startX, endY - startY, g);
            g.drawString("X", startX + (endX - startX) / 2 - (rec.getBounds().width / 2), startY + (endY - startY));
            break;
        case 3:
            rec = setFontSizeForBox("O", endX - startX, endY - startY, g);
            g.drawString("O", startX + (endX - startX) / 2 - (rec.getBounds().width / 2), startY + (endY - startY));
            int size = g.getFont().getSize() - 8;
            g.setFont(new Font(FONT_NAME, Font.PLAIN, size));
            g.drawString("X", startX + (endX - startX) / 2 + 4 - (rec.getBounds().width / 2), startY + (endY - startY));
            break;
        }
    }

    private void drawScore(Graphics g, int startX, int endX, int startY, int endY, int score){
        drawString(g, startX, endX, startY, endY, Integer.toString(score));
    }

    private void drawString(Graphics g, int startX, int endX, int startY, int endY, String string) {
      if (string != null) {
        Rectangle2D rec;
        rec = setFontSizeForBox(string, endX - startX, endY - startY - 4, g);
        g.drawString(string, startX + (endX - startX) / 2 - (rec.getBounds().width / 2), startY + (endY - startY) - (int) ( (double) (endY - startY) * .1));
      }
    }

    private void drawLine(Graphics g, int startX, int endX, int y){
        Graphics2D g2 = (Graphics2D)g;

        int fontSize = 24;
        int width = endX - startX;

        Font font = new Font(FONT_NAME, Font.PLAIN, fontSize);
        g2.setFont(font);

        int i = fontSize;

        while(((i / 2) - 1) < width){
            g2.rotate(Math.PI / 2, startX + (double)(i - fontSize)/2 - 1 , (double)y);
            g2.drawString("I", startX + (i - fontSize)/2 - 1 , y);
            g2.rotate(-Math.PI / 2, startX + (double)(i - fontSize)/2 - 1 , (double)y);

            i += fontSize;
        }
    }

    private Rectangle2D setFontSizeForBox(String msg, int width, int height, Graphics g){
        int startFontSize = 3;
        int previousSize = startFontSize;
        Rectangle2D rec;
        Font font = new Font(FONT_NAME, Font.PLAIN, startFontSize);
        FontRenderContext frc = ((Graphics2D)g).getFontRenderContext();

        while(true){
            rec = font.getStringBounds(msg, frc);

            if(rec.getBounds().getWidth() > width || rec.getBounds().getHeight() > height){
                break;
            }

            font = new Font(FONT_NAME, Font.PLAIN, ++startFontSize);
            g.setFont(font);
        }

        return rec;
    }

    public void this_componentResized(ComponentEvent e) {
        repaint();
    }

    public void setCurrentPlayerFlag(boolean flag){
        m_CurrentPlayer = flag;
        repaint();
    }
}



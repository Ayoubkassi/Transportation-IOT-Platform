package gui.component;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoundButton extends JLabel implements MouseListener{

    private Color color;
    private Color hoverColor;
    private Color paintColor;
    private int cornerRadius;

    public RoundButton(Rectangle bounds, int cornerRadius, String label, int textSize, Color color, Color hoverColor, Color textColor){
        super(label, JLabel.CENTER);
        setBounds(bounds);
        this.color = this.paintColor = color;
        this.hoverColor = hoverColor;
        this.cornerRadius = cornerRadius;
        setOpaque(true);
        setBackground(color);
        setForeground(textColor);
        setCursor( new Cursor(Cursor.HAND_CURSOR) );
        setFont( new Font("Helvetica", Font.PLAIN, textSize) );
        
        addMouseListener(this);
    }

    public void setColor(Color color){
        this.paintColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setClip( new RoundRectangle2D.Double(0, 0, getBounds().width+1, getBounds().height+1, cornerRadius, cornerRadius) );
        setBackground(paintColor);
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent me){}
    @Override
    public void mouseEntered(MouseEvent me){
        setColor(hoverColor);
    }
    @Override
    public void mouseExited(MouseEvent me){
        setColor(color);
    }
    @Override
    public void mousePressed(MouseEvent me){}
    @Override
    public void mouseReleased(MouseEvent me){}
    
}
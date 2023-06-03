package gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
//java.awt.Canvas paint seen not to support Transarency

public class Circle extends JComponent{
    private Color color;
    private int radius;

    public Circle(int x, int y, int radius, Color color){
        this.color = color;
        this.radius = radius;
        setBounds(x, y, radius * 2, radius * 2);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.addRenderingHints(SVGDrawer.getRenderingOptions());
        g2D.setColor(color);
        g2D.scale(0.001, 0.001);
        g2D.fillOval(0, 0, 2000*radius, 2000*radius);
        g2D.scale(1000, 1000);
    }

    public void setColor(Color color){
        this.color = color;
        repaint();
    }
}

package gui.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JComponent;

public class WinButton extends JComponent implements MouseListener{

    public static final String EXIT = "Close Window";
    public static final String MAXIMIZE = "Max window size";
    public static final String REDUCE = "Reduce Window";

    private Point lastWindowLocation;

    private WindowA window;
    private String action;
    private Rectangle rect;
    private Color activeColor;
    private Color color;
    private Color colorHover;
    private Color colorClick;

    private Double scale = 0.90;

    public WinButton(int x, int y, int radius, Color color, String action){
        super();
        
        this.rect = new Rectangle(x, y, radius, radius);
        setBounds(this.rect);
        this.color = this.activeColor = color;
        this.action = action;

        addMouseListener(this);
    }

    public void setWindow(WindowA window) {
        this.window = window;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        g2D.addRenderingHints(SVGDrawer.getRenderingOptions());
        g2D.setColor(activeColor);
        g2D.scale(scale, scale);
        g2D.fill(new Ellipse2D.Double(0, 0, rect.getWidth(), rect.getHeight()));
    }

    public void setPaintColor(Color color){
        this.activeColor = color;
        repaint();
    }

    public void setColors(Color colorHover, Color colorClick){
        this.colorHover = colorHover;
        this.colorClick = colorClick;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if( window!=null ){
            switch(action){
                case EXIT:
                    window.dispose(); break;
                case MAXIMIZE:{
                    if( !window.isFullscreen() ){
                        lastWindowLocation = window.getLocationOnScreen();
                        window.setFullscreen(true);
                    }
                    else{
                        window.setFullscreen(false);
                        setPaintColor(colorClick);
                        window.setLocation(lastWindowLocation);
                    }
                    break;
                }
                case REDUCE:
                    window.setState(JFrame.ICONIFIED); break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me){
        scale = 1.0;
        setPaintColor(colorHover);
    }

    @Override
    public void mouseExited(MouseEvent me){
        scale = 0.90;
        setPaintColor(color);
    }

    @Override
    public void mousePressed(MouseEvent me){}

    @Override
    public void mouseReleased(MouseEvent me){
        setPaintColor(color);
    }

}
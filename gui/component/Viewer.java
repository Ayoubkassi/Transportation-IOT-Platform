package gui.component;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class Viewer extends Canvas{

    BufferedImage buffer;
    int cornerRadius;

    public Viewer(int x, int y, int width, File image, int cornerRadius){
        super();
        this.cornerRadius = cornerRadius;
        try{
            this.buffer = ImageIO.read(image);
        }catch(IOException ioe){ System.out.println("Impossible de lire le fichier " + image.getAbsolutePath()); }
        setBounds(x, y, width, buffer.getHeight()*width / buffer.getWidth());
    }

    public Viewer(int x, int y, int width, String imagePath, int cornerRadius){
        super();
        this.cornerRadius = cornerRadius;
        try{
            this.buffer = ImageIO.read( new File(imagePath));
        }catch(IOException ioe){ System.out.println("Impossible de lire le fichier " + imagePath); }
        setBounds(x, y, width, buffer.getHeight()*width / buffer.getWidth());
    }

    public Viewer(int x, int y, int width, BufferedImage image, int cornerRadius){
        super();
        this.buffer = image;
        this.cornerRadius = cornerRadius;
        setBounds(x, y, width, buffer.getHeight()*width / buffer.getWidth());
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        Rectangle bounds = getBounds();
        int width = bounds.width;
        g2D.addRenderingHints(SVGDrawer.getRenderingOptions());
        g2D.setClip( new RoundRectangle2D.Float(0, 0, width, width, cornerRadius, cornerRadius));
        g2D.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
    }

    public BufferedImage getBuffer(){
        return this.buffer;
    }
}
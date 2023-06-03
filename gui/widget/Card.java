package gui.widget;

import dao.IOTNode;
import gui.component.Circle;
import gui.component.SVGDrawer;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;
import javax.swing.JComponent;

public class Card extends JComponent{
    public static final int CARD_WIDTH = 170;
    public static final int CARD_HEIGHT = 84;
    private static final int C_X = 7;
    private static final int C_R = 7;
    private static Color[] RED_C = {new Color(0xFF0000), new Color(0x7E0000)};
    private static Color[] GREEN_C = {new Color(0x00FF00), new Color(0x007700)};
    private IOTNode node;
    private JLabel title;
    private JLabel infos;

    private Circle redLightH, greenLightH, redLightV, greenLightV;

    public Card(int x, int y, IOTNode node){
        this.node = node;
        setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
        
        boolean testH = node.canPass(node.getTime(), IOTNode.U_X);
        this.redLightH = new Circle(C_X, 14, C_R, testH ? RED_C[0] : RED_C[1]);
        this.greenLightH = new Circle(C_X, 35, C_R, testH ? GREEN_C[1] : GREEN_C[0]);
        this.redLightV = new Circle(C_X + 140, 14, C_R, !testH ? RED_C[0] : RED_C[1]);
        this.greenLightV = new Circle(C_X + 140, 35, C_R, !testH ? GREEN_C[1] : GREEN_C[0]);

        add(redLightH); add(greenLightH); add(redLightV); add(greenLightV);
        initLabel();
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.addRenderingHints(SVGDrawer.getRenderingOptions());
        g2D.setColor(Color.WHITE);
        g2D.fill(new RoundRectangle2D.Float(0, 0, CARD_WIDTH - 1, CARD_HEIGHT - 1, 7, 7));
        g2D.setColor( new Color(0xF3F3F3) );
        g2D.fillRoundRect(4, 10, 21, 49, 7, 7);
        g2D.fillRoundRect(144, 10, 21, 49, 7, 7);
    }

    private void initLabel(){
        this.title = new JLabel(("Noeud " + this.node.getId()), JLabel.CENTER);
        this.title.setBounds(28, 7, 84, 28);
        this.title.setForeground(Color.BLUE);
        this.title.setFont(new Font("Helvetica", Font.PLAIN, 18));

        this.infos = new JLabel((this.node.getPassenger() + " passager(s)"), JLabel.CENTER);
        this.infos.setBounds(18, 35, 140, 28);
        this.infos.setForeground(Color.DARK_GRAY);
        this.infos.setFont( new Font("Helvetica", Font.PLAIN, 16));

        add(this.title); add(this.infos);
    }

    public void setTimeOffset(int timeOffset){
        this.node.setTime(timeOffset);
        boolean testH = node.canPass(timeOffset, IOTNode.U_X);
        this.redLightH.setColor(testH ? RED_C[0] : RED_C[1]);
        this.greenLightH.setColor(testH ? GREEN_C[1] : GREEN_C[0]);
        this.redLightV.setColor( !testH ? RED_C[0] : RED_C[1]);
        this.greenLightV.setColor( !testH ? GREEN_C[1] : GREEN_C[0]);
    }

    public void setNumClients(int numClients){
        this.node.setPassengers(numClients);
        this.infos.setText( numClients + " passager(s)" );
    }

    public void setNode(IOTNode node){
        this.node = node;
        this.infos.setText(node.getPassenger() + " passager(s)");
        setTimeOffset(node.getTime());
    }

}

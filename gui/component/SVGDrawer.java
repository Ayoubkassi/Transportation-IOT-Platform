package gui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.HashMap;

import javax.swing.JComponent;

/**
 * @author Adriano TIOTSOP FOGUE
 */
public class SVGDrawer extends JComponent{

    public static final Color BLUE0 = new Color(0x2486EB);
    public static final Color BLUE1 = new Color(0x003EFF);
    public static final Color SMOOTH = new Color(0xAAAAAA);

    public static final float DRAW_SCALE = 0.001f;
    public static final float DRAW_RESET_SCALE = 1000f;
    public static final int ICON_SIZE = 28;
    public static final char ALIGN_LEFT = 'L';
    public static final char ALIGN_RIGHT = 'R';

    public static final String CHECK = "CHECK_ICON";
    public static final String CROSS = "CROSS_ICON";
    public static final String LOOP = "LOOP_ICON";
    public static final String EYE = "EYE_ICON";
    public static final String MAX = "MAX_ICON";
    public static final String MIN = "MIN_ICON";
    public static final String PLUS = "PLUS_ICON";
    public static final String STAR = "STAR_ICON";

    //private HashMap<RenderingHints.Key, Object> renderingOptions = new HashMap<>(6);
    private Color color;
    private Color inColor;
    private String type;

    public SVGDrawer(JComponent attachedComponent, char position, String type, Color color, Color inColor){
        boolean test = (position == ALIGN_RIGHT);
        Rectangle bounds = attachedComponent.getBounds();
        setBounds(test ? (bounds.x + bounds.width) : (bounds.x - ICON_SIZE), bounds.y , ICON_SIZE, ICON_SIZE);
        this.type = type;
        this.color = color;
        this.inColor = inColor;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public SVGDrawer(int x, int y, String type, Color color, Color inColor){
        setBounds(x, y, ICON_SIZE, ICON_SIZE);
        this.type = type;
        this.color = color;
        this.inColor = inColor;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.addRenderingHints( getRenderingOptions() );
        
        g2D.scale(DRAW_SCALE, DRAW_SCALE);
        //Paints implementation for case
        draw(g2D);
        g2D.scale(DRAW_RESET_SCALE, DRAW_RESET_SCALE);
    }

    public void draw(Graphics2D g2D){
        switch (type){
            case CHECK :
                drawCheckIcon(g2D, color); break;
            case CROSS :
                drawCrossIcon(g2D, color); break;
            case LOOP :
                drawSearchIcon(g2D, color, inColor); break;
            case EYE :
                drawEyeIcon(g2D, color); break;
            case MAX :
                drawMaxIcon(g2D, color); break;
            case MIN :
                drawMinIcon(g2D, color); break;
            case STAR :
                drawStarIcon(g2D, color); break;
            case PLUS :
            default :
                drawPlusIcon(g2D, color);break;
        }
    }

    public void setActiveColor(Color color){
        this.color = color;
        repaint();
    }
    public void setColors(Color color, Color inColor){
        this.color = color;
        this.inColor = inColor;
        repaint();
    }

    //SVG Rendering Map options
    public static HashMap<RenderingHints.Key, Object> getRenderingOptions(){
        HashMap<RenderingHints.Key, Object> renderingOptions = new HashMap<>(6);
        
        renderingOptions.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingOptions.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderingOptions.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        renderingOptions.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        renderingOptions.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        renderingOptions.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        return renderingOptions;
    }

    private static final int[] CROSS_X = {7265, 7162, 6055, 6066, 12699, 12699, 6014, 6003, 7110, 7213, 13870, 14000, 20735, 20838, 21946, 21935, 15238, 15238, 21948, 21959, 20852, 20749, 14066, 13936};
    private static final int[] CROSS_Y = {21936, 21947, 20840, 20737, 14066, 13936, 7213, 7110, 6003, 6014, 12633, 12633, 5936, 5925, 7032, 7135, 13917, 14066, 20749, 20852, 21959, 21948, 15304, 15304};

    public static void drawCrossIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillPolygon(CROSS_X, CROSS_Y, CROSS_X.length);
    }

    private static void drawCheckIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.translate(-2000, -700);
        g2D.fillPolygon(PLUS_SX, PLUS_SY, PLUS_SX.length);
        g2D.translate(4000, 700);
        g2D.fillPolygon(PLUS_SX, PLUS_SY, PLUS_SX.length);
    }

    private static final int[] PLUS_SX = {12763, 12714, 7415, 7411, 8242, 8314, 12714, 12763, 12811, 20923, 20996, 21826, 21823, 12811};
    private static final int[] PLUS_SY = {19403, 19383, 14056, 13983, 13152, 13156, 17527, 17547, 17527, 9444, 9440, 10271, 10343, 19383};

    private static void drawPlusIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillPolygon(PLUS_SX, PLUS_SY, PLUS_SX.length);
    }

    private static final int[] LOOP_X = {17404, 18146, 24427, 24427, 23790, 23684};
    private static final int[] LOOP_Y = {18146, 17404, 23684, 23790, 24427, 24427};

    private static void drawSearchIcon(Graphics2D g2D, Color color, Color inColor){
        g2D.setColor(color);g2D.setColor(color);
        g2D.fillPolygon(LOOP_X, LOOP_Y, LOOP_X.length);
        g2D.fillOval(7035, 7035, 12600, 12600);
        g2D.setColor(inColor);
        g2D.fillOval(8085, 8085, 10500, 10500);
    }

    private static final int[] EYE_X = {16000, 16000, 15305, 14632, 14000, 13429, 12936, 12536, 12241, 12061, 12000, 12061, 12241, 12536, 12936, 13429, 14000, 14632, 15305, 16000, 16695, 17368, 18000, 18571, 19064, 19464, 19759, 19939, 20000, 19939, 19759, 19464, 19064, 18571, 18000, 17368, 16695, 16000, 16000, 17736, 19420, 21000, 22428, 23660, 24660, 25397, 25848, 26000, 25848, 25397, 24660, 23660, 22428, 21000, 19420, 17736, 16000, 14264, 12580, 11000, 9572, 8340, 7340, 6603, 6152, 6000, 6152, 6603, 7340, 8340, 9572, 11000, 12580, 14264};
    private static final int[] EYE_Y = {23000, 20000, 19939, 19759, 19464, 19064, 18571, 18000, 17368, 16695, 16000, 15305, 14632, 14000, 13429, 12936, 12536, 12241, 12061, 12000, 12061, 12241, 12536, 12936, 13429, 14000, 14632, 15305, 16000, 16695, 17368, 18000, 18571, 19064, 19464, 19759, 19939, 20000, 23000, 22894, 22578, 22062, 21362, 20500, 19500, 18394, 17216, 16000, 14784, 13606, 12500, 11500, 10638, 9938, 9422, 9106, 9000, 9106, 9422, 9938, 10638, 11500, 12500, 13606, 14784, 16000, 17216, 18394, 19500, 20500, 21362, 22062, 22578, 22894};
    
    private static void drawEyeIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillPolygon(EYE_X, EYE_Y, EYE_X.length);
        g2D.fillOval(1400, 1400, 400, 400);
    }

    private static final int[] MAX_XI = {14000, 14000, 8000, 8000, 10000, 10000};
    private static final int[] MAX_YI = {17000, 19000, 19000, 13000, 13000, 17000};
    private static final int[] MAX_XII= {14000, 18000, 18000, 20000, 20000, 14000};
    private static final int[] MAX_YII = {11000, 11000, 15000, 15000, 9000, 9000};

    private static void drawMaxIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillPolygon(MAX_XI, MAX_YI, MAX_XI.length);
        g2D.fillPolygon(MAX_XII, MAX_YII, MAX_XII.length);
    }
    private static void drawMinIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillRect(8000, 13000, 12000, 2000);
    }

    private static final int[] STAR_X = {8742, 8448, 8264, 8241, 9126, 9118, 8978, 8744, 4991, 4833, 4903, 5182, 10368, 10756, 11123, 11371, 13690, 13887, 14113, 14310, 16629, 16877, 17244, 17632, 22818, 23097, 23167, 23009, 19257, 19022, 18882, 18874, 19760, 19736, 19552, 19258, 14620, 14227, 13773, 13380};
    private static final int[] STAR_Y = {21764, 21835, 21702, 21400, 16235, 15825, 15393, 15056, 11398, 11141, 10925, 10809, 10056, 9921, 9655, 9327, 4628, 4398, 4398, 4628, 9327, 9655, 9921, 10056, 10809, 10925, 11141, 11398, 15056, 15393, 15825, 16235, 21400, 21702, 21835, 21764, 19326, 19207, 19207, 19326};

    private static void drawStarIcon(Graphics2D g2D, Color color){
        g2D.setColor(color);
        g2D.fillPolygon(STAR_X, STAR_Y, STAR_X.length);
    }

}
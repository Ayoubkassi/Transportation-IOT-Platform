package gui.component;

import java.awt.Point;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.geom.Rectangle2D;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Arrays;
import java.util.stream.Stream;
/**
 * @author Adriano TIOTSOP FOGUE
 */
public class WindowB extends JFrame implements MouseListener, MouseMotionListener, Runnable{

    private boolean coverred;
    private boolean draggable;
    private boolean fullScreen;

    private JComponent btnClose;
    private JComponent btnMaxSize;
    private JComponent btnReduce;

    private ThreadGroup winGroup;

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GraphicsEnvironment graphicsEnvironment;
    public Point lastLocation;

    private int x, y, mx, my;

    public WindowB(int width, int height, Color background, String title, boolean resizable){
        super(title);
        
        setLayout(null);
        setUndecorated(true);
        setSize(width, height);
        setResizable(resizable);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setShape(new Rectangle2D.Double(0, 0, width, height));
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(screenSize.width, screenSize.height));

        this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point screenCenter = graphicsEnvironment.getCenterPoint();
        GraphicsDevice defaultGraphicDevice = graphicsEnvironment.getDefaultScreenDevice();
        GraphicsDevice graphicsDevices[] = graphicsEnvironment.getScreenDevices();
        //setLocationRelativeTo(null);
        setLocation(70, 14);

        System.out.println(screenSize + "("+screenCenter.x+","+screenCenter.y+")");
        System.out.println("Our screen have " + graphicsDevices.length + " screen(s)");
        System.out.println("\tDefault screen " + defaultGraphicDevice);
        //Arrays.asList(graphicsDevices).stream().forEach(System.out::println);
        Arrays.asList(graphicsDevices).stream().forEach(device -> System.out.println(device + " " + device.getDefaultConfiguration().getBounds() ) );
        

        winGroup = new ThreadGroup("My window thread group");

        getContentPane().setBackground(background);

        drawButtons();
        add(btnClose); add(btnReduce);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void drawButtons(){
        int width = getBounds().width;
        btnClose = new SVGDrawer(width - SVGDrawer.ICON_SIZE, 0, SVGDrawer.CROSS, SVGDrawer.BLUE0, SVGDrawer.SMOOTH);
        btnClose.addMouseListener(this);

        if( isResizable() ){
            btnMaxSize = new SVGDrawer(width - 2*SVGDrawer.ICON_SIZE, 0, SVGDrawer.MAX, Color.DARK_GRAY, SVGDrawer.SMOOTH);
            btnMaxSize.addMouseListener(this);
            add(btnMaxSize);
            btnReduce = new SVGDrawer(width - 3*SVGDrawer.ICON_SIZE, 0, SVGDrawer.MIN, Color.LIGHT_GRAY, SVGDrawer.SMOOTH);
        }else{
            btnReduce = new SVGDrawer(width - 2*SVGDrawer.ICON_SIZE, 0, SVGDrawer.MIN, Color.LIGHT_GRAY, SVGDrawer.SMOOTH);
        }
        btnReduce.addMouseListener(this);
    }

    public void placeButtons(){  
        int width = getBounds().width;   
        if( isResizable() ){
            btnMaxSize.setLocation(width - SVGDrawer.ICON_SIZE * 2, 0);
            btnReduce.setLocation(width - SVGDrawer.ICON_SIZE * 3, 0);
        }else{
            btnReduce.setLocation(width - width - SVGDrawer.ICON_SIZE * 2, 0);
        }
        btnClose.setLocation(width - SVGDrawer.ICON_SIZE, 0);
    }

    public boolean isCovered(){
        return this.coverred;
    }

    public boolean isDraggable(){
        return this.draggable;
    }

    public boolean isFullscreen(){
        return this.fullScreen;
    }

    public void setCoverred(boolean value){
        this.coverred = value;
    }

    public void setDraggable(boolean value){
        this.draggable = value;
    }

    public void setFullscreen(boolean value){
        this.fullScreen = value;
        if( value == true ){
            lastLocation = getLocation();
            setLocation(1, 1);
            setSize(screenSize.width, screenSize.height);
            setShape(new Rectangle2D.Double(0, 0, screenSize.width, screenSize.height));
        }else{
            setSize(getPreferredSize());
            setLocation(lastLocation);
            setShape(new Rectangle2D.Double(0, 0, getPreferredSize().width, getPreferredSize().height));
        }
        placeButtons();
    }

    @Override
    public void run(){
        setLocation(getLocation().x + mx - x, getLocation().y + my - y);
        if( !isDraggable() )
            return;
    }

    @Override
    public void mouseDragged(MouseEvent me){
        if( isCovered() ){
            try{
                mx = getMousePosition(true).x;
                my = getMousePosition(true).y;
            }catch(Exception e){}
            new Thread(winGroup, this).start();
        }
            
    }

    @Override
    public void mouseMoved(MouseEvent me){
        if( isCovered() ){
            try{
                x = getMousePosition(true).x;
                y = getMousePosition(true).y;
            }catch(Exception e){}
        }
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if( me.getComponent().equals(btnClose))
            this.dispose();
        if( me.getComponent().equals(btnReduce))
            this.setState(JFrame.ICONIFIED);
        if( btnMaxSize != null && me.getComponent().equals(btnMaxSize))
            this.setFullscreen( isFullscreen() ? false : true);
    }

    @Override
    public void mouseEntered(MouseEvent me){
        setCoverred(true);
    }

    @Override
    public void mouseExited(MouseEvent me){
        setDraggable(false);
        setCoverred(false);
    }

    @Override
    public void mousePressed(MouseEvent me){
        if(me.getButton() == MouseEvent.BUTTON1){
            setDraggable(true);
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
    }

    @Override
    public void mouseReleased(MouseEvent me){
        setDraggable(false);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}

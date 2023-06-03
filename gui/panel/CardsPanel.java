package gui.panel;

import dao.FinalNode;
import dao.IOTNode;
import gui.component.RoundButton;
import gui.component.SVGDrawer;
import gui.component.TitledInput;
import gui.widget.Card;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class CardsPanel extends JComponent{
    public static final int WIDTH = 550;
    public static final int HEIGHT = 630;
    private static final int N = 15;
    private static final int SPACE = 10, INPUT_WIDTH = 28, INPUT_HEIGHT = 28;

    private Card[] cards = new Card[15];
    private JTextField[] clientInputs = new JTextField[N];
    private TitledInput inputs[] = new TitledInput[N];
    private TitledInput valuesSetters[] = new TitledInput[3];
    private JLabel clientsLabel;
    private RoundButton update;
    private RoundButton start;

    /**
     * 
     * @param x x position in principal window
     * @param y y position in principal window
     * @param timeOffset time offset for algorithm
     * @param maxRandom max of possible values for client waiting on stations
     * @param pass number of seconds during which horizontal light blink to drive and for vertical to wait
     * @param wait number of seconds during which horizontal light blink to wait and for vertical to drive
     */
    public CardsPanel(int x, int y, int timeOffset, int maxRandom, int pass, int wait){
        FinalNode.fillNodes(maxRandom, timeOffset, pass, wait);;
        setBounds(x, y, WIDTH, HEIGHT);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 3; j++){
                int position = 3 * i + j;
                int x_pos = SPACE + j * ( SPACE + Card.CARD_WIDTH);
                int y_pos = SPACE + i * ( SPACE + Card.CARD_HEIGHT);
                this.cards[position] = new Card(x_pos, y_pos, FinalNode.getNodes()[position]);
                add(this.cards[position]);
            }
        }

        initClientsInput();
        initSetters(timeOffset, pass, wait);
        FinalNode.setNodes(getNodes());

        this.start = new RoundButton(new Rectangle(420, 560, 120, 42), 0, "Start", 22, Color.BLUE, Color.RED, Color.WHITE);
        this.update = new RoundButton(new Rectangle(280, 560, 120, 42), 0, "Update", 22, Color.BLUE, Color.RED, Color.WHITE);
        add(this.start);
        add(this.update);

    }
    //javax.swing.JComponent.paint(Graphics g) seems to Override existing objects
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.addRenderingHints(SVGDrawer.getRenderingOptions());
        g2D.setColor(new Color(0xF7F7F7));
        g2D.fillRoundRect(0, 0, WIDTH - 1, HEIGHT - 1, 7, 7);
    }

    public JComponent getStartButton(){ return this.start; }
    public JComponent getUpdateButton(){ return this.update; }

    public FinalNode getEndPoint(){
        return FinalNode.getNodes().length == 15 ? FinalNode.getEndPoint() : null;
    }

    public IOTNode[] getNodes(){
        return FinalNode.getNodes();
    }
    private void initSetters(int timeOffset, int pass, int wait){
        valuesSetters[0] = new TitledInput(new Rectangle(14, 539, 70, 70), "T Offset", 14, Color.BLUE, Color.BLACK, "(\\d){0,3}");
        valuesSetters[0].setValue(String.valueOf(timeOffset));
        valuesSetters[1] = new TitledInput(new Rectangle(14 + 80, 539, 70, 70), "W duration", 14, Color.BLUE, Color.BLACK, "(\\d){0,3}");
        valuesSetters[1].setValue(String.valueOf(pass));
        valuesSetters[2] = new TitledInput(new Rectangle(14 + 80 + 80, 539, 70, 70), "P duration", 14, Color.BLUE, Color.BLACK, "(\\d){0,3}");
        valuesSetters[2].setValue(String.valueOf(wait));
        for(TitledInput input : valuesSetters)
            add(input);
    }

    private void initClientsInput() {
        this.clientsLabel = new JLabel("Clients", JLabel.CENTER);
        this.clientsLabel.setBounds(0, 504, 65, INPUT_HEIGHT);
        this.clientsLabel.setForeground(Color.BLUE);
        this.clientsLabel.setFont( new Font("Helvetica", Font.PLAIN, 16));
        add(this.clientsLabel);

        for(int c = 0; c < this.clientInputs.length; c++){
            int x_padding = 65 + 3 + c * (32); 
            this.inputs[c] = new TitledInput(new Rectangle(x_padding, 480, INPUT_WIDTH, INPUT_HEIGHT * 2), "N" + c, 14, Color.DARK_GRAY, Color.BLACK, "([0-2]{0,1})([0-9])");
            this.inputs[c].setValue( String.valueOf(FinalNode.getNodes()[c].getPassengers()) );
            add(this.inputs[c]);
        }
        this.inputs[10].setEnabled(false);
    }

    public void update() {
        int[] values = new int[N];
        for(int i = 0; i < N; i++)
            values[i] = Integer.valueOf( inputs[i].getValue() );
        System.out.println(java.util.Arrays.toString(values));
        int timeOffset = Integer.valueOf(valuesSetters[0].getValue());
        int pass = Integer.valueOf(valuesSetters[2].getValue());
        int wait = Integer.valueOf(valuesSetters[1].getValue());
        FinalNode.fillNodes(values, timeOffset, pass, wait);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 3; j++){
                int position = 3 * i + j;
                this.cards[position].setNode(FinalNode.getNodes()[position]);;
            }
        }
    }
}

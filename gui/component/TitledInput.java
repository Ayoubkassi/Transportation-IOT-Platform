package gui.component;

import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TitledInput extends JComponent implements KeyListener{

    private JLabel header;
    private JTextField input;
    private String regex;
    private Pattern pattern;

    public TitledInput(Rectangle bounds, String label, int textSize, Color headerColor, Color inputColor, String regex){
        setBounds(bounds);
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
        this.header = new JLabel(label, JLabel.CENTER);
        this.header.setBounds(0,0, bounds.width, bounds.height/2);
        this.header.setOpaque(false);
        this.header.setForeground(headerColor);
        this.header.setFont( new Font("Helvetica", Font.PLAIN, textSize));

        this.input = new JTextField();
        this.input.setBounds(0,bounds.height/2, bounds.width, bounds.height/2);
        this.input.setForeground(inputColor);
        this.input.setFont( new Font("Helvetica", Font.PLAIN, textSize));
        this.input.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        this.input.addKeyListener(this);
        add(header); add(input);
    }

    public void setValue(String value){
        if( evaluate(value))
            this.input.setText(value);
    }

    public String getValue(){
        return input.getText();
    }

    public boolean evaluate(String value){
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {
        if( !evaluate(this.input.getText()) ) 
            this.input.setText("");
    }
    
}

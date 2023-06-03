import gui.component.Viewer;
import gui.component.WindowB;
import gui.panel.CardsPanel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;

import controller.Solver;
import controller.UpdateHandler;

import java.io.File;


// Command Linux rm *.class */*.class */*/*.class; javac Launch.java; java Launch;
// Command Window rm *.class *\*.class *\*\*.class; javac Launch.java; java Launch;
public class Launch{
    public static final char SEP = File.separatorChar;

    private static String imagePath = "ressources" + SEP + "map.png";
    private static String image1 = "ressources" + SEP + "sub_map0.png";
    private static String image2 = "ressources" + SEP + "sub_map1.png";
    private static String icon = "ressources" + SEP + "app.png";

    private static int WINDOW_WIDTH = 1200;
    public static void main(String[] args) {
        WindowB window = new WindowB(WINDOW_WIDTH, 700, Color.WHITE, "IOT Project", false);
        window.setIconImage( java.awt.Toolkit.getDefaultToolkit().getImage(icon) );
        System.out.println(imagePath);

        JLabel title = new JLabel("GROUPE 11 : RECHERCHE DU CHEMIN LE PLUS COURT", JLabel.CENTER);
        title.setOpaque(false);
        title.setForeground(Color.BLUE);
        title.setBounds(7, 28, 600, 28);
        title.setFont(new Font("Helvetica", Font.PLAIN, 22));

        CardsPanel cardsPanel = new CardsPanel(WINDOW_WIDTH - CardsPanel.WIDTH, 28, 880, 14, 300, 360);
        Viewer image = new Viewer(7, 63, WINDOW_WIDTH - CardsPanel.WIDTH - 14, imagePath, 0);
        Viewer view1 = new Viewer(7, 427, 250, image1, 2);
        Viewer view2 = new Viewer(400 - 7, 427, 250, image2, 2);

        String result = "Meilleur chemin : " + cardsPanel.getEndPoint().bestPath(880);
        System.out.println(result);
        JLabel show = new JLabel(result, JLabel.CENTER);
        show.setOpaque(false);
        show.setForeground(Color.RED);
        show.setBounds(7, 658, 1000, 28);
        show.setFont(new Font("Helvetica", Font.PLAIN, 16));

        cardsPanel.getStartButton().addMouseListener(new Solver(show, cardsPanel));
        cardsPanel.getUpdateButton().addMouseListener(new UpdateHandler(cardsPanel));

        window.add(title);
        window.add(image);
        window.add(view1); window.add(view2);
        window.add(show);

        window.add(cardsPanel);
        window.setVisible(true);
    }
}
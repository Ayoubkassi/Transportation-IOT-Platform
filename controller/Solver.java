package controller;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import gui.panel.CardsPanel;

import java.awt.event.MouseAdapter;

public class Solver extends MouseAdapter{

    private JLabel shower;
    private CardsPanel cardsPanel;

    public Solver(JLabel shower, CardsPanel panel){
        this.shower = shower;
        this.cardsPanel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me){
        solve();
    }

    public void solve(){
        int timeOffset = cardsPanel.getEndPoint().getTime();
        String result = "Meilleur chemin : " + cardsPanel.getEndPoint().bestPath(timeOffset);
        System.out.println(result);
        shower.setText(result);
    }
    
}

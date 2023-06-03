package controller;

import java.awt.event.MouseEvent;

import gui.panel.CardsPanel;

import java.awt.event.MouseAdapter;

public class UpdateHandler extends MouseAdapter{
    private CardsPanel cardsPanel;
    public UpdateHandler(CardsPanel panel){
        this.cardsPanel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me){
        update();
    }

    public void update(){
        System.out.println("Update values");
        this.cardsPanel.update();
    }
}

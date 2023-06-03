package dao;

public class Neighbor {
    private IOTNode next;
    private double distance;
    private char direction;

    public Neighbor(IOTNode node, double distance, char direction){
        this.next = node;
        this.distance = distance;
        this.direction = direction;
    }

    public IOTNode getDestination(){
        return this.next;
    }
    public double getDistance(){
        return this.distance;
    }
    public char getDirection(){
        return this.direction;
    }
    
}

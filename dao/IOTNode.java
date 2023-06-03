package dao;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Groupe 11 ENSA Kenitra project IOT 2021-2022 Supervised by Professor Abderahim BAJIT.
 * @since 2022
 */
public class IOTNode {
    public static final char U_X = 'H';
    public static final char V_Y = 'V';
    private static int PASS, WAIT;

    public static void setOption(int pass, int wait){
        PASS = Math.abs(pass);
        WAIT = Math.abs(wait);
    }
    public static int getPass() { return PASS; }
    public static int getWait() { return WAIT; }

    private int id;
    private int passengers;
    private int time;

    private List<Neighbor> neighbors = new ArrayList<>(4);

    public IOTNode(int id, int passengers, int startOffset){
        this.id = id;
        this.passengers = passengers;
        this.time = startOffset;
    }

    public IOTNode(IOTNode node){
        this.id = node.id;
        this.passengers = node.passengers;
        this.time = node.time;
        this.neighbors = node.neighbors;
    }

    //Getters
    public int getId(){
        return this.id;
    }
    public int getTime() {
        return time;
    }
    public int getPassengers() {
        return passengers;
    }
    public int getPassenger() {
        if(passengers > 0)
            passengers -= 1;
        return passengers;
    }

    public List<Neighbor> getNeighbors(){
        return neighbors;
    }

    public double getDistance(IOTNode node){
        for( Neighbor neighbor : neighbors )
            if( neighbor.getDestination().getId() == node.getId() )
                return neighbor.getDistance();
        return 0;
    }

    public char getDirection(IOTNode node){
        for( Neighbor neighbor : neighbors )
            if( neighbor.getDestination().getId() == node.getId() )
                return neighbor.getDirection();
        return ' ';
    }

    public void addNeighbor(IOTNode node, double distance, char direction) {
        Neighbor neighbor = new Neighbor(node, distance, direction);
        if( ! neighbors.contains(neighbor) )
            neighbors.add(neighbor);
    }

    //Setters
    public void setTime(int time) {
        this.time = time;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public boolean canPass(int time, char direction){
        if(direction == IOTNode.U_X)
            return time%(WAIT + PASS) > WAIT;
        return time%(WAIT + PASS) <= WAIT;
    }
    public int waiting(int time, char direction){
        if(direction == IOTNode.U_X){
            if(time % (WAIT + PASS) > WAIT)
                return 0;
            return ( WAIT - time % (WAIT+PASS) );
        }else{
            if(time % (WAIT + PASS) <= WAIT)
                return 0;
            return ( WAIT+PASS - time % (WAIT+PASS) );
        }
    }

    @Override
    public String toString(){
        String stats[] = {"Node " + getId() + "{ passenger(s) : " + getPassengers() + ", time : " + getTime()};
        stats[0] += " Neighbor(s) : " + getNeighbors().size() + " [";
        getNeighbors().forEach( node -> stats[0] += node.getDestination().getInfos() );
        return stats[0] + "...}\n";
    }

    public String getInfos(){
        return "Node " + getId() + "{passenger(s) : " + getPassengers() + ", time : " + getTime() +"}, ";
    }
}

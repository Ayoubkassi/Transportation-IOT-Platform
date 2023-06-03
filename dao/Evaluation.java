package dao;

public class Evaluation {
    private int time;
    private int distance;
    private int points;
    private int passengers;
    private String path;

    /**
     * 
     * @param time offset time in second for start or real time start point for evaluation
     * @param distance distance in kilometer between current node and next node
     * @param position for point evaluation
     * @param passengers passenger on node at current time
     * @param path path for evaluation
     */
    public Evaluation(int time, int distance, int positionFactor, int passengers, String path){
        this.time = time;
        this.distance = distance;
        this.points = positionFactor * passengers;
        this.passengers = passengers;
        this.path = path;
    }

    public Evaluation(Evaluation evaluation){
        this.time = evaluation.time;
        this.distance = evaluation.distance;
        this.points = evaluation.points;
        this.passengers = evaluation.passengers;
        this.path = evaluation.path;
    }

    public static String getCSVHeader(){
        return "chemin,time(s),time(min),distance(m),passager(s),point(s)\n";
    }

    public void addDistance(double distance){
        this.distance += distance;
    }

    public void addPassengers(int passengers, int position){
        this.time += 7;
        this.passengers += passengers;
        this.points += passengers * position;
    }

    public void addPath(int nextNodeId){
        this.path += "-" + String.valueOf(nextNodeId);
    }

    public void addPoints(int points){
        this.points += points;
    }

    public void addTime(int timesup){
        this.time += timesup;
    }

    public int getDistance(){
        return this.distance;
    }

    public int getTime(){
        return this.time;
    }

    public int getPassengers(){
        return this.passengers;
    }

    public String getPath(){
        return this.path;
    }

    public int getPoints(){
        return this.points;
    }

    public Evaluation compare(Evaluation evaluation){
        if(this.time < evaluation.getTime())
            return this;
        else if( this.time == evaluation.getTime() ){
            if(this.distance < evaluation.distance)
                return this;
            else if( this.distance == evaluation.getDistance()){
                    if(this.passengers > evaluation.getPassengers())
                        return this;
                    else if(this.passengers == evaluation.getPassengers()){
                        if(this.points > evaluation.points)
                            return this;
                        else
                            return evaluation;
                    }
                    else
                        return evaluation;
            }else
                return evaluation;
        }
        else 
            return evaluation;
    }

    public static String second2min(int timeInSeconds){
        int min = timeInSeconds / 60;
        int second = timeInSeconds % 60;
        return min + "min" + second + "s";
    }

    public String getCSVLine(){
        return getPath() + "," + getTime() + "," + second2min(time) + "," + getDistance() + "," + getPassengers() + "," + getPoints() + "\n";
    }

    @Override
    public String toString(){
        String stats =  "Path[" + path + "] time = " + time + "s | " + second2min(time) + ", distance = " + distance + "m";
        stats += " Passager(s) = " + passengers + ", points = " + points;  
        return stats;
    }
}

package dao;

import controller.FileManager;

public class FinalNode extends IOTNode{

    //14 PATHS
    public static final int[][] PATHS = { {0,1,2,5,8,11}, {0,1,4,5,8,11}, {0,1,4,7,8,11}, {0,1,4,7,10,11}, {0,1,4,7,10,13,14,11}, {0,3,4,5,8,11}, {0,3,4,7,10,11}, {0,3,4,7,10,13,14,11}, {0,3,6,7,8,11}, {0,3,6,7,10,11}, {0,3,6,7,10,13,14,11}, {0,3,6,9,10,11}, {0,3,6,9,10,13,14,11}, {0,3,6,9,12,10,11}, {0,3,6,9,12,10,13,14,11}};

    private static int capacity;
    private static double speed;
    private static int CAPACITY = 30;
    private static double SPEED = 35/9;  // ---- 14km/h --- 14000m/3600s  
    private static IOTNode nodes[] = new IOTNode[15];
    private static FinalNode endPoint;
    /**
     * 
     * @param id final node ID
     * @param c car capacity
     * @param velocity speed in m/s
     */
    public FinalNode(int id, int c, double velocity, int startOffset){
        super(id, 0, startOffset);
        capacity = c;
        speed = velocity;
    }

    public static void fillNodes(int maxRandom, int startOffset, int pass, int wait){
        IOTNode.setOption(pass, wait);
        for(int n = 0; n <= 10; n++)
            nodes[n] = new IOTNode(n, (int) (maxRandom * Math.random()), startOffset);
        endPoint = new FinalNode(11, CAPACITY, SPEED, startOffset);
        nodes[11] = endPoint;
        for(int n = 12; n < 15; n++)
        nodes[n] = new IOTNode(n, (int) (maxRandom * Math.random()), startOffset);
    }

    public static void fillNodes(int values[], int startOffset, int pass, int wait){
        IOTNode.setOption(pass, wait);
        for(int n = 0; n <= 10; n++)
            nodes[n] = new IOTNode(n, values[n], startOffset);
        endPoint = new FinalNode(11, CAPACITY, SPEED, startOffset);
        nodes[11] = endPoint;
        for(int n = 12; n < 15; n++)
            nodes[n] = new IOTNode(n, values[n], startOffset);
        initNodes(nodes);
    }

    public static IOTNode[] getNodes() { return nodes;}
    public static void setNodes(IOTNode[] tabNodes){
        nodes = tabNodes;
        initNodes(nodes);
    }
    public static FinalNode getEndPoint(){
        return endPoint;
    }
    //Distance in meters
    public static void initNodes(IOTNode[] nodes){
        if(nodes.length == 15){
            nodes[0].addNeighbor(nodes[1], 1708, IOTNode.U_X); nodes[0].addNeighbor(nodes[3], 1263, IOTNode.V_Y);
            nodes[1].addNeighbor(nodes[2], 1251, IOTNode.U_X); nodes[1].addNeighbor(nodes[4], 1616, IOTNode.V_Y);
            nodes[2].addNeighbor(nodes[5], 1203, IOTNode.V_Y);
            nodes[3].addNeighbor(nodes[4], 1501, IOTNode.U_X); nodes[3].addNeighbor(nodes[6], 670, IOTNode.V_Y);
            nodes[4].addNeighbor(nodes[5], 1612, IOTNode.U_X); nodes[4].addNeighbor(nodes[7], 685, IOTNode.V_Y);
            nodes[5].addNeighbor(nodes[8], 826, IOTNode.V_Y);
            nodes[6].addNeighbor(nodes[7], 1602, IOTNode.U_X); nodes[6].addNeighbor(nodes[9], 1132, IOTNode.V_Y);
            nodes[7].addNeighbor(nodes[8], 1329, IOTNode.U_X); nodes[7].addNeighbor(nodes[10], 1295, IOTNode.V_Y);
            nodes[8].addNeighbor(nodes[11], 1305, IOTNode.V_Y);
            nodes[9].addNeighbor(nodes[10], 1285, IOTNode.U_X); nodes[9].addNeighbor(nodes[12], 1463, IOTNode.V_Y);
            nodes[10].addNeighbor(nodes[11], 1402, IOTNode.U_X); nodes[10].addNeighbor(nodes[13], 1297, IOTNode.V_Y);
            nodes[12].addNeighbor(nodes[13], 1267, IOTNode.U_X);
            nodes[13].addNeighbor(nodes[10], 1297, IOTNode.V_Y); nodes[13].addNeighbor(nodes[14], 1252, IOTNode.U_X);
            nodes[14].addNeighbor(nodes[11], 1323, IOTNode.V_Y);
        }
    }

    public Evaluation algorithm(int[] path, int timeOffset){
        Evaluation evaluation = new Evaluation(timeOffset, 0, 0, 0, String.valueOf(path[0]));
        //System.out.println( "\nPATH : " + Arrays.toString(path) );
        IOTNode node = nodes[0];
        //System.out.println(node);
        int initialCapacity = capacity;
        for(int p = 0; p < path.length - 1; p++){
            node = nodes[ path[p] ];
            int initialPassengers = node.getPassengers();
            while( node.getPassengers() > 0 && capacity > 0 ){
                capacity--;
                node.getPassenger();
                evaluation.addPassengers(1, path.length - p);
                //System.out.println("Actuellement : " + evaluation.getPassengers() + "et " + node.getPassengers());
            }
            
            node.setPassengers(initialPassengers);
            //System.out.println("Apres : " + node.getPassengers());
            IOTNode next = nodes[ path[p+1] ];
            //System.out.println(next);
            char direction = node.getDirection(next);
            if( !canPass(evaluation.getTime(), direction) )
                evaluation.addTime( waiting(evaluation.getTime(), direction) );
            evaluation.addDistance( node.getDistance(next) );
            evaluation.addTime( (int) (node.getDistance(next) / speed) );
            evaluation.addPath(next.getId());
        }
        capacity = initialCapacity;
        FileManager.write(FileManager.FILE, evaluation.getCSVLine());
        //System.out.println(evaluation.toString());
        return evaluation;
    }

    public Evaluation evaluate(Evaluation evaluations[]){
        Evaluation evaluation = evaluations[0];
        for (int e = 1; e < evaluations.length; e++ ) 
            evaluation = evaluation.compare(evaluations[e]);
        return evaluation;
    }

    public String bestPath(int timeOffset){
        FileManager.delete(FileManager.FILE);
        FileManager.write(FileManager.FILE, Evaluation.getCSVHeader());
        Evaluation evaluations[] = new Evaluation[PATHS.length];
        for(int p = 0; p < PATHS.length; p++)
            evaluations[p] = new Evaluation( algorithm(PATHS[p], timeOffset) );
        return evaluate(evaluations).toString() + " (Time offset : " + timeOffset + "s | " + Evaluation.second2min(timeOffset) + ")";
    }
}

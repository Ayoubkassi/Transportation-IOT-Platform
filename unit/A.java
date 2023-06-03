package unit;

import java.io.PrintStream;

public class A {
    public static void main(String[] args) {
        PrintStream console = new PrintStream(System.out);

        console.println("value of " + (145%140 > 40));
        int path[] = {45, 25, 4, 43, 12, 15, 0, 4, 7, 9};
        for(int i : path)
            console.println("Values : " + i);

        while(true){
            console.println(Math.floor(250*Math.random()));
            try{
                Thread.sleep(300);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            console.println(Math.round(45*Math.random()));
        }
    }
}

package utils;

/**
 * Created by dmitri on 23.10.15.
 */

public class TimeHelper {

    public static final int MILLIS = 5000;

    public static void sleep(int period){
        try{
            Thread.sleep(period);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void sleep(){
        try{
            Thread.sleep(MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thomasbotsford3draycastingengine;

/**
 *
 * @author tom
 */
public class Framerate {

    public static int fps;
    public static double time;
    
    private static long frameStartTime = System.nanoTime(); //exact time when frame starts
    private static long frameLastTime = System.nanoTime();
    private static int framesCount; //number of frames counted per second, incremented each update 
    
    
    public static void update() {
        long currentFrameTime = System.nanoTime();
        if (currentFrameTime - frameStartTime >= 1000000000) { //after a second has passed
            fps = framesCount; //fps equals counted frames
            framesCount = 0;  //reset frame count
            frameStartTime = currentFrameTime; //start tracking a new second
        } 
        else {
            framesCount++; //increment frame count if a second has not passed
        }
        time = currentFrameTime - frameLastTime;
        frameLastTime = currentFrameTime;
    }
    
}
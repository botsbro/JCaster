/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thomasbotsford3draycastingengine;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author tom
 */
public class WorldObject{
    
    private int xCoordinate;
    private int yCoordinate;
    private BufferedImage sprite;
    private double distance;
    private int colour;
    private boolean drawState;
    //private boolean drawn;

//    WorldObject(Point2D.Double xy) {
//        this.coordinates = xy;    
//    }
    
    WorldObject(int x, int y, BufferedImage s, int c, double d, boolean drawn){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.colour = c;
        this.distance = d;
        this.sprite = s;
        this.drawState = drawn;
    }
    
    
    public void setCoordinates(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }
    
    public int getXCoordinate(){
        return this.xCoordinate;
    }
    
    public int getYCoordinate(){
        return this.yCoordinate;
    }
    
    public double getDistance(){
        return this.distance;
    }
    
    public void setDistance(double d){
        this.distance = d;
    }
    
    public boolean getDrawState(){
        return this.drawState;
    }
    
    public void setDrawState(boolean drawn){
        this.drawState = drawn;
    }
    
    public void flipDrawState(){
        if(this.drawState == true){
            this.drawState = false;
        }
        else if(this.drawState == false){
            this.drawState = false;
        }
    }
    
//    public void setDrawn(boolean d){
//        this.drawn = d;
//    }
//    
//    public boolean getDrawn(){
//        return this.drawn;
//    }
    
}

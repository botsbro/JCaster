/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thomasbotsford3draycastingengine;


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

//    WorldObject(Point2D.Double xy) {
//        this.coordinates = xy;    
//    }
    
    WorldObject(int x, int y, BufferedImage s, int c, double d){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.colour = c;
        this.distance = d;
        this.sprite = s;
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
    
    public BufferedImage getSprite(){
        return this.sprite;
    }
    
    public void setDistance(double d){
        this.distance = d;
    }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thomasbotsford3draycastingengine;

//importing relevant libraries 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;  
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 *
 * @author tom
 */
public class Window extends javax.swing.JFrame {
    
    private BufferedImage backBuffer; //back buffer that each following frame will be drawn to
    private BufferedImage map; //map PNG that will be used for the wall positions
    private BufferedImage plant; //sprite for test world object
    private BufferedImage floor; //image for bottom half of background
    private BufferedImage sky; //image for top half of background
    private BufferedImage wallTexture; //image that is split into columns used for wall slices
        
    public BufferedImage[] sliceTextures = new BufferedImage[16]; //array for columns used for wall slices
    public ArrayList<WorldObject> worldObjects = new ArrayList<>();
        
    private Point.Double cameraCoordinates = new Point.Double(50, 240); //camera starting coordinates
    private double cameraAngle = Math.toRadians(180); //starting camera direction
    private double cameraFOV = Math.toRadians(53.5); // fov degrees to radians, 53.5 recommended for 480x685
    
    //note that an image will be drawn on a projection plane between the camera and and object...
    //...this drawing is used as the 3D image output
    private int projectionPlaneWidth = 480; //width of 3D projection output
    private int projectionPlaneHeight = 685;
    private double projectionPlaneDistance; // distance between camera and projection plane
    private int black;
    private int white;
    private int red;
    private int blue;
    
    private int sliceIndex = 0;
    private int objectIndex = 0;
    
    //final JFileChooser fc = new JFileChooser();
    
    //width of wall slices in pixels, higher gives more jagged edges on walls but better performance
    private int sliceWidth = 6; // 1 is minimum, 2 gives best balance, recommended no higher than 6

    /**
     * Creates new form Window
     */
    public Window() {
        initComponents();
        setTitle("3D Raycasting Demo (April 2020)");
        addKeyListener(new KeyHandler());
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMovementHandler());
        setLocationRelativeTo(null);
   
        // create back buffer to create proper frame transitions
        backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        // load map image
        try {
            map = ImageIO.read(getClass().getResourceAsStream("map5.png"));
        } 
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            plant = ImageIO.read(getClass().getResourceAsStream("plant.png"));
        } 
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            floor = ImageIO.read(getClass().getResourceAsStream("floor.png"));
        } 
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sky = ImageIO.read(getClass().getResourceAsStream("sky.png"));
        } 
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }     
        try {
            wallTexture = ImageIO.read(getClass().getResourceAsStream("Bricks.png"));
        } 
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }  
        Texture.createSliceTextures(wallTexture);

        createSliceTextureArray();
        
//        fc.showOpenDialog(this);
//        File selFile = fc.getSelectedFile();
//        try {
//            wallTexture = ImageIO.read(getClass().getResourceAsStream(selFile.getCanonicalPath()));
//        } 
//        catch (IOException ex) {
//            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
//        }  
//       

        //distance from projection plane scales so that perspective will remain the same in case of projection plane or FOV adjustment
        projectionPlaneDistance = (projectionPlaneWidth / 2) / Math.tan(cameraFOV / 2);  
        black = -16777216;
        white = -1;
        red = -1237980;
        blue = -16776961;
        
        System.out.println(cameraFOV); //fov in degrees converted to radians
        System.out.println(Math.tan(cameraFOV / 2)); //tan using half of radians
        System.out.println(projectionPlaneDistance);    //distance to projection plane
        System.out.println("Black integer value = " + black);
        System.out.println("White integer value = " + white);
        System.out.println("Red integer value = " + red);
        System.out.println("Blue integer value = " + blue);
        new Thread(new MainLoop()).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
                
            }
        });
    }
    
    //create window frame and run the class
    public static void createFrame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Window().setVisible(true);
                    
                }
            });    
    }
    
    private class MainLoop implements Runnable {
        @Override
        public void run() {
            while (true) {
                updateMovement();
                Thread.yield();
            }
        }
    }
    
    public void createSliceTextureArray(){
        sliceTextures[0] = Texture.slice1;
        sliceTextures[1] = Texture.slice2;
        sliceTextures[2] = Texture.slice3;
        sliceTextures[3] = Texture.slice4;
        sliceTextures[4] = Texture.slice5;
        sliceTextures[5] = Texture.slice6;
        sliceTextures[6] = Texture.slice7;
        sliceTextures[7] = Texture.slice8;
        sliceTextures[8] = Texture.slice9;
        sliceTextures[9] = Texture.slice10;
        sliceTextures[10] = Texture.slice11;
        sliceTextures[11] = Texture.slice12;
        sliceTextures[12] = Texture.slice13;
        sliceTextures[13] = Texture.slice14;
        sliceTextures[14] = Texture.slice15;
        sliceTextures[15] = Texture.slice16;
    }
    
    public void cleanDupeWorldObjects(){;
    if (worldObjects.size() == 1 || worldObjects.size() == 0){
        
    }
    else{
        WorldObject[] temp = new WorldObject[worldObjects.size()];  
        int j = 0;  
        for (int i=0; i<worldObjects.size()-1; i++){  
            if (worldObjects.get(i) != worldObjects.get(i+1)){  
                temp[j++] = worldObjects.get(i);  
            }  
         }  
        temp[j++] = worldObjects.get(worldObjects.size()-1);     
        
        // Changing original array  
        for (int i=0; i<j; i++){  
            worldObjects.set(i, temp[i]);  
            worldObjects.remove(i++);
            System.out.println(worldObjects.size());
        }  
    }
    }
    
    private double castRay(double cameraPositionX, double cameraPositionY, double cameraDirectionAngle, Graphics g, Color rayColor) { //cast a single ray
        double sin = Math.sin(cameraDirectionAngle);// sin variable for ray angle
        double cos = Math.cos(cameraDirectionAngle);// cos variable for ray angle
        int px;
        int py;
        double wallDistance = 0; //distance of ray from camera
        double objectDistance = 0;
        int currentColour = 0; //colour intersecting with ray
        do {
            wallDistance += 0.1; //extend ray
             px = (int) (cameraPositionX + wallDistance * sin);//draw ray in direction of angle along the x axis
             py = (int) (cameraPositionY + wallDistance * cos);//draw ray in direction of angle along the y axis
            currentColour = map.getRGB(px, py);
        } while (currentColour == white);//until something that isn't white is hit


        
            if(currentColour == black){ //if ray hits black
                g.setColor(rayColor);//draw blue ray
                g.drawLine((int) cameraPositionX, (int) cameraPositionY, (int) (cameraPositionX + wallDistance * sin), (int) (cameraPositionY + wallDistance * cos)); //draw ray line on 2D map 
                return wallDistance; //return distance ray travelled when wall is hit
            }
            
            else if(currentColour == blue){ //if ray hits black
                do { //continue drawing ray past red pixel    
                    wallDistance += 0.1; //extend ray
                     px = (int) (cameraPositionX + wallDistance * sin);//draw ray in direction of angle along the x axis
                     py = (int) (cameraPositionY + wallDistance * cos);//draw ray in direction of angle along the y axis
                    currentColour = map.getRGB(px, py);
                } while (currentColour != black);//until a black wall is hit, -16777216 is the integer value of black
            }
            else if(currentColour == red){ //if ray hits red
                
//                if(worldObjects.isEmpty()){ // if no world objects
//                    worldObjects.add(new WorldObject(px,py,plant,red,wallDistance)); //add first world object
//                    System.out.println("Created object at " + px + "," + py + " " + "in " + "worldObjects(" + (worldObjects.size()-1) + ")");
//                }
//                
//                else if (worldObjects.isEmpty() == false){ // if there is at least one viewed world object
//                    boolean foundMatch = false;
//                    for(int i = 0; i < worldObjects.size(); i++){        
//
//                        if(worldObjects.get(i).getXCoordinate() == px && worldObjects.get(i).getYCoordinate() == py){ //if coordinates of red pixel match
//                            foundMatch = true; //found matching pixel coordinates
//                            System.out.println("Skipped object. " + "Matched coordinates of " + "worldObjects(" + i + ")"); //do not add 
//                            System.out.println("worldObjects size is " + worldObjects.size());
//                        }
//                        else{
//                           
//                        }
//                    }
//                    
//                    if(foundMatch == false){ // if coordinates of red pixel do not match
//                        worldObjects.add(new WorldObject(px,py,plant,red,wallDistance)); //add new world object
//                        System.out.println("Created object at " + px + ", " + py);
//                    }
//                    else{
//                        
//                    }
//                                         
//                }
                
                do { //continue drawing ray past red pixel    
                    wallDistance += 0.1; //extend ray
                     px = (int) (cameraPositionX + wallDistance * sin);//draw ray in direction of angle along the x axis
                     py = (int) (cameraPositionY + wallDistance * cos);//draw ray in direction of angle along the y axis
                    currentColour = map.getRGB(px, py);
                } while (currentColour != black);//until a black wall is hit, -16777216 is the integer value of black

            }
        
        //draw ray intersecting with black wall after passing through red pixel
        g.setColor(rayColor);//draw blue ray
        g.drawLine((int) cameraPositionX, (int) cameraPositionY, (int) (cameraPositionX + wallDistance * sin), (int) (cameraPositionY + wallDistance * cos)); //draw ray line on 2D map 
        return wallDistance; //return distance ray travelled when wall is hit
        
    }
    
    public int getColour(double cameraPositionX, double cameraPositionY, double cameraDirectionAngle){
        double sin = Math.sin(cameraDirectionAngle);// sin variable for ray angle
        double cos = Math.cos(cameraDirectionAngle);// cos variable for ray angle
        double objectDistance = 0; //distance of ray from camera
        int currentColour = 0; //colour intersecting with ray
        int px = (int) (cameraPositionX + objectDistance * sin);//draw ray in direction of angle along the x axis
        int py = (int) (cameraPositionY + objectDistance * cos);
        do {
            objectDistance += 0.1; //extend ray
            px = (int) (cameraPositionX + objectDistance * sin);//draw ray in direction of angle along the x axis
            py = (int) (cameraPositionY + objectDistance * cos);//draw ray in direction of angle along the y axis
            currentColour = map.getRGB(px, py);
        } while (currentColour == white);//until something that isn't white is hit
        
        if(currentColour == black){ //if ray hits black
            return black;
        }
        
        else if(currentColour == red){ //if ray hits red

            
            if(worldObjects.isEmpty()){ // if no world objects
                    worldObjects.add(new WorldObject(px,py,plant,red,objectDistance,false)); //add first world object
                    System.out.println("Created object at " + px + "," + py + " " + "in " + "worldObjects(" + (worldObjects.size()-1) + ")");
                    objectIndex = 0;
                    return red; //ray captures object
                }
                
                else if (worldObjects.isEmpty() == false){ // if there is at least one viewed world object
                    boolean foundMatch = false;
                    for(int i = 0; i < worldObjects.size(); i++){        

                        if(worldObjects.get(i).getXCoordinate() == px && worldObjects.get(i).getYCoordinate() == py){ //if coordinates of red pixel match
   
                                foundMatch = true; //found matching pixel coordinates
                                System.out.println("Skipped object. " + "Matched coordinates of " + "worldObjects(" + i + ")"); //do not add 
                                System.out.println("worldObjects size is " + worldObjects.size());
                            
                        }
                        else{
                           foundMatch = false;
                        }
                    }
                    
                    if(foundMatch == false){ // if coordinates of red pixel do not match
                        worldObjects.add(new WorldObject(px,py,plant,red,objectDistance,false)); //add new world object
                        System.out.println("Created object at " + px + ", " + py);
//                        drawObjects = true;
//                        worldObjects.clear();
//                        worldObjects.trimToSize();
                        foundMatch = true;
                        objectIndex = worldObjects.size()-1;
                        return red; //ray captures object
                    }
                    else{
                        return black;
                    }
                                         
                }

            
            else{
                
            }
                //return red;
        }
        
        return black;
    }
    
    public void draw2D(Graphics2D g) {
        g.setBackground(Color.PINK);
        g.clearRect(0, 0, getWidth(), getHeight());
        g.translate(0, 31);
        g.drawImage(map, 0, 0, null); //draw map
        g.setColor(Color.RED);
        g.fillRect((int) (cameraCoordinates.x - 3), (int) (cameraCoordinates.y - 3), 6, 6); //draw camera on 2d map
//        g.setColor(Color.WHITE);
//        g.fillRect(315, 0, 955, 720);
        g.drawImage(sky, 315, 0, null); 
        g.drawImage(floor, 315, 360, null); 
        
        // draw walls
        for (int x=-(projectionPlaneWidth); x<(projectionPlaneWidth); x+=sliceWidth) { //for pixel along the projection plane's width  in the FOV
           // if (x % 2 == 0){
                double a = Math.atan(x / projectionPlaneDistance);
                //cast a single ray for a single angle, repeated for each degree in the FOV
                double cameraWallDistance = castRay(cameraCoordinates.x, cameraCoordinates.y, cameraAngle - a, g, Color.BLUE); 
                double z = cameraWallDistance * Math.cos(a);
                int colour = getColour(cameraCoordinates.x, cameraCoordinates.y, cameraAngle - a);
 
                    if(sliceIndex < sliceTextures.length -1){ //if sliceIndex is under its maximum
                        sliceIndex++;
                        drawWall3D(g, z, x + 790, colour);
                    }
                    else{
                        sliceIndex = 0;
                        drawWall3D(g, z, x + 790, colour);
                    }
                
                
           // }
            //else{
                
           // }

        }
        // draw camera direction
        //cast ray in straight line in front of camera, will always be in middle of FOV
        castRay(cameraCoordinates.x, cameraCoordinates.y, cameraAngle, g, Color.GREEN); 
        //Framerate.update();
        showFramerate(g);
    }
    
    private void drawObject(int x, double distance, double z, int size, Graphics g){
        //int y = (projectionPlaneHeight / 2) + (size / 2);
        int y = map.getHeight() + ((int)(projectionPlaneDistance*20/z)/2);
        y = y - (int)(((plant.getHeight())*(projectionPlaneDistance/(z*2)))/2);
        y = y < (projectionPlaneHeight / 2) - (int)(((plant.getHeight())*(projectionPlaneDistance/(z*2)))/2) ? (projectionPlaneHeight / 2) - (int)(((plant.getHeight())*(projectionPlaneDistance/(z*2)))/2) : y;
        x = x-(int)((((plant.getWidth())*(projectionPlaneDistance/(z*2)))));
        //int y = (int) (projectionPlaneDistance * 11 / z)*2; 
        //g.drawImage(scale(plant, projectionPlaneHeight / 2 - wallHeight, projectionPlaneHeight / 2 - wallHeight) , x, y + 300 , null); //draw plant
        g.drawImage(scale(plant,(int)((plant.getWidth())*(projectionPlaneDistance/(z*2))),(int)((plant.getHeight())*(projectionPlaneDistance/(z*2)))),x, y , null); //draw plant

    }
    
    private void drawWall3D(Graphics g, double z, int x, int c) {
         if (c == red){
            double p = z / 300;
            p = p > 1 ? 1 : p;
            p = p < 0 ? 0 : p;
            int shade = (int) (p * 255);
            int wallHeight = (int) (projectionPlaneDistance * 20 / z);
            wallHeight = wallHeight > projectionPlaneHeight * 3 ? projectionPlaneHeight * 3 : wallHeight;
            g.setColor(new Color(shade, shade, shade));
            //g.drawLine(x, projectionPlaneHeight / 2 - (wallHeight/2), x, projectionPlaneHeight / 2 + (wallHeight/2));  
            g.drawImage(scale(sliceTextures[sliceIndex], sliceWidth, wallHeight), x, projectionPlaneHeight / 2 - (wallHeight/2), null);
            drawObject(x, 0, worldObjects.get(objectIndex).getDistance(), wallHeight, g);

        }
         else if (c == black){
//            double p = z / 300;
//            p = p > 1 ? 1 : p;
//            p = p < 0 ? 0 : p;
//            int shade = (int) (p * 255);
            int wallHeight = (int) (projectionPlaneDistance * 20 / z);
            wallHeight = wallHeight > projectionPlaneHeight * 3 ? projectionPlaneHeight * 3 : wallHeight;
            //g.setColor(new Color(shade, shade, shade));
            //g.drawLine(x, projectionPlaneHeight / 2 - wallHeight, x, projectionPlaneHeight / 2 + wallHeight);//draw line from (x,y to x,y)(CANNOT BE USED WITH BELOW FILL RECTANGLE OR DRAW IMAGE'S)
            //g.fillRect(x, projectionPlaneHeight / 2 - wallHeight, sliceWidth, wallHeight * 2); //draw rectangle (CANNOT BE USED WITH ABOVE DRAW LINE OR BELOW DRAW IMAGE'S)              
            //g.drawImage(scale(sliceTextures[sliceIndex], sliceWidth, wallHeight * 2), x, projectionPlaneHeight / 2 - wallHeight, null);           
            g.drawImage(scale(sliceTextures[sliceIndex], sliceWidth, wallHeight), x, projectionPlaneHeight / 2 - (wallHeight/2), null);
//            if(c == red){ // find correct world object in arrayList using coordinates, and draw it with correct distance 
//                
//
//                                         
//                
//                
//            }
            //g.drawImage(scale(recolour(sliceTextures[sliceIndex],z), sliceWidth, wallHeight), x, projectionPlaneHeight / 2 - (wallHeight/2), null);
         
         }
        
        
    }
    
    public void enableDrawObjects(){ //resets the worldObject arrayList so that world objects can be drawn again on the next frame                   
        worldObjects.clear();                    
        worldObjects.trimToSize();        
    }
    
    
    //display drawn graphics drawn in backbuffer
    @Override
    public void paint(Graphics d) {
        draw2D((Graphics2D) backBuffer.getGraphics());
        d.drawImage(backBuffer, 0, 0, null);
        enableDrawObjects();
        Framerate.update();
    }
    
    public BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    public BufferedImage recolour(BufferedImage imageToColour, double z){
        int w = imageToColour.getWidth(); 
        int h = imageToColour.getHeight(); 
        double p = z / 300;
            p = p > 1 ? 1 : p;
            p = p < 0 ? 0 : p;
            int shade = (int) (p * 255); 
        for (int y = 0; y < h; y++) 
            { 
                for (int x = 0; x < w; x++) 
                { 
                    

                    
                  
                } 
            }  
        
        return imageToColour;
    }
    
    //method called in case of key event, updates coordinates and rotation angle
    private void moveCamera(double distance, double angle) {
        double newAngle = cameraAngle + angle;
        double sin = Math.sin(newAngle);
        double cos = Math.cos(newAngle);
        cameraCoordinates.x += distance * sin;
        cameraCoordinates.y += distance * cos;
    }
    
    private void wallCollision(int direction){ //method to detect and handle wall collision
        double sin = Math.sin(cameraAngle);// sin variable for camera angle
        double cos = Math.cos(cameraAngle);// cos variable for camera angle
        int px = (int) (cameraCoordinates.x + 0.1 * sin);
        int py = (int) (cameraCoordinates.y + 0.1 * cos);
        int currentColour = map.getRGB(px, py); 
        if(currentColour != white){ //if player coodinates are over black wall...
            switch(direction){
                case 0://if moving forward
                    moveCamera(-1d/10, 0); //move backwards an equal amount
                break;    
                case 1: //if moving backward
                    moveCamera(1d/10, 0); //move forwards an equal amount
                break;
                case 2: //if moving left
                    moveCamera(-1d/10, Math.toRadians(90)); //move right an equal amount
                break;
                case 3: //if moving right
                   moveCamera(1d/10, Math.toRadians(90)); //move left an equal amount
                break;
            }    
        }
    }

    public void updateMovement() {
        
        if (Keyboard.keyDown[37] || Keyboard.keyDown[65]) { // left
            moveCamera(1d/30000, Math.toRadians(90));
            wallCollision(2);
        }
        else if (Keyboard.keyDown[39] || Keyboard.keyDown[68]) { // right
            moveCamera(1d/30000, Math.toRadians(-90));
            wallCollision(3);
        }

        if (Keyboard.keyDown[38] || Keyboard.keyDown[87]) { // up 
            moveCamera(1d/30000, 0);
            wallCollision(0);
        }
        else if (Keyboard.keyDown[40] || Keyboard.keyDown[83]) { // down
            moveCamera(-1d/30000, 0);
            wallCollision(1);
        }        
        
        if (Keyboard.keyDown[90]  || Keyboard.keyDown[81]) { // z
            cameraAngle += 0.0000017;
//            if (sliceIndex > 0){    
//                sliceIndex--;
//            }
//            else{
//                
//            }
        }
        else if (Keyboard.keyDown[88] || Keyboard.keyDown[69]) { // x
            cameraAngle -= 0.0000017;
//            if (sliceIndex > 0){    
//                sliceIndex--;
//            }
//            else{
//                
//            }
        }
        repaint();
    }
    
    private static class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            Keyboard.setKeyDown(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Keyboard.setKeyDown(e.getKeyCode(), false);
        }
        
    }
    
    private int mouseClickX;
    private double angleOriginal;
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent m) {
            //System.out.println("Clicked " + m.getX());
            mouseClickX = m.getX();
            angleOriginal = cameraAngle;
        }

        @Override
        public void mouseReleased(MouseEvent m) { 
            
            //System.out.println("Released " + m.getX());

        }
        
    }
    
    private class MouseMovementHandler extends MouseMotionAdapter {
        
        @Override
        public void mouseDragged(MouseEvent m) {
            int difX = m.getX() - mouseClickX;
            //System.out.println("Dragged " + m.getX());
            double difAngle = Math.toRadians(difX / 12d);
            cameraAngle = angleOriginal - difAngle;
        }
        
    }
    
    //display FPS in top left
    private static void showFramerate(Graphics2D f) {
        f.setColor(Color.BLACK); 
        f.fillRect(0, 0, 100, 30); //draw black rectangle for FPS counter in top left
        f.setColor(Color.WHITE); 
        f.drawString("FPS: " + Framerate.fps, 20, 20); //draw white text inside box    

    }   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

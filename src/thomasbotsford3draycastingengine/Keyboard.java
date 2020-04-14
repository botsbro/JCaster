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
public class Keyboard {
    
    public static boolean[] keyDown = new boolean[223];
    
    public static void setKeyDown(int keyCode, boolean down) {
        if (keyCode > keyDown.length - 1) {
            return;
        }
        keyDown[keyCode] = down;
    }
}

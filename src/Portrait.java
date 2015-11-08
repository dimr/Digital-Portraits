import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by dimitris on 11/7/15.
 */
public abstract class Portrait {

    private PApplet pa;
    private PImage img;
    private int width;
    private int height;


    public Portrait(PApplet pa, PImage img, int width, int height) {
        this.pa = pa;
        this.img = img;
        this.width = width;
        this.height = height;
    }


    @Override
    public abstract PGraphics generatePortrtait(PImage theFace)
}

import gab.opencv.OpenCV;
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
    private OpenCV cv;
    private boolean useColor;
    private float ratio;

    public Portrait(PApplet pa, PImage img,boolean useColor) {
        this.pa = pa;
        this.img=img;

        this.width = width;
        this.height = height;
        this.setUseColor(useColor);
        this.setImage(this.img);
        setRatio();

    }

    public Portrait() {
    }


    public Portrait(PApplet pa, OpenCV cv, boolean useColor) {
        this.pa = pa;
        this.useColor = useColor;
        this.cv = new OpenCV(this.pa, cv.getOutput(), useColor);

    }

    public Portrait(PApplet pa) {
        this.pa = pa;
    }

    public PApplet pa(){
        return this.pa;
    }

    public void setUseColor(boolean useColor){
        this.useColor=useColor;
    }

    public boolean getUseColor(){
        return this.useColor;
    }


    public void setImage(PImage img) {
        this.cv=new OpenCV(this.pa,img,this.getUseColor());
        this.img = this.cv.getOutput();
    }

    public PImage getPImage() {
        return this.img;
    }



    public int getWidth() {
        return this.img.width;
    }


    public int getHeight() {
        return this.img.height;
    }

    private void setRatio() {
        this.ratio = (float) this.getWidth() / this.getHeight();
    }

    private float getRatio() {
        return this.ratio;
    }

    public int getDimensions(){
        return getWidth()*getHeight();
    }



    @Override
    public String toString() {
        return "Portrait{" +
                "width=" + this.getWidth() +
                ", height=" + this.getHeight() +
                ", useColor=" + this.useColor +
                ", ratio=" + this.getRatio() +
                ", dimensions=" + this.getDimensions()+
                '}';
    }

    public abstract PGraphics generatePortrtait();
}

import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import toxi.geom.Vec2D;

/**
 * Created by dimitris on 11/8/15.
 */
public class RectPortrait extends Portrait {


    private PGraphics result;
    public RectPortrait(PApplet pa,PImage img,boolean useColor){
        super(pa,img,useColor);
        System.out.println("RECT");
        this.setResult(pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height));
    }


    public RectPortrait(PApplet pa, OpenCV cv,boolean useColor){
        super(pa,cv,useColor);
    }

    @Override
    public PGraphics getResult() {
        return this.result;
    }

    @Override
    public void setResult(PGraphics result) {
        this.result=result;
    }


    @Override
    public PGraphics generatePortrtait() {
        assert(this.getPImage()!=null):"OK PIMAGE";
        PImage face = this.getPImage();
        PApplet pa=this.pa();
        face.loadPixels();
        Vec2D pos = new Vec2D();
//        this.setResult(pa.createGraphics(this.getPImage().width * 5, 5 * this.getPImage().height));
        PImage image = pa.createImage(this.getPImage().width * 5, 5 * this.getPImage().height, pa.RGB);
        //System.out.println(this.getResult().width + "---" + this.getResult().height);
        this.getResult().beginDraw();
        this.getResult().background(100);
        this.getResult().noStroke();
        float tileWidth=this.getResult().width/(float)face.width;
        float tileHeight=this.getResult().height/(float)face.height;
        for (int x = 0; x < face.width; x++) {
            for (int y = 0; y < face.height; y++) {
                float posX = tileWidth * x;
                float posY = tileHeight * y;
                pos.set(posX,posY);
                int c = face.pixels[y*face.width+x];
                int greyscale = pa.round((float) (pa.red(c) * 0.222 + pa.green(c) * 0.707 + pa.blue(c) * 0.071));
                float t = pa.map(greyscale, 0, 255, 0, 4);
                Vec2D rectSize = new Vec2D(t,t).jitter(pa.random(4), pa.random(4));
                this.getResult().fill(greyscale);
                this.getResult().rect(pos.x(), pos.y(), rectSize.x(), rectSize.y());
            }

        }
        //result.background(255,0,0);
        this.getResult().endDraw();
        
        return this.getResult();
    }
}

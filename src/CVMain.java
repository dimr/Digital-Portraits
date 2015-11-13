import controlP5.ControlP5;
import gab.opencv.OpenCV;
import org.gicentre.utils.move.Ease;
import processing.core.*;
import processing.video.Capture;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dimitris on 11/7/15.
 */
public class CVMain extends PApplet {

    Capture video;
    OpenCV opencv;

    /*
    use one of these
    face: this is the rectangle that draws the detected face
    out: black & white,use with opencv.getOutput()
     */
    PImage face;
    PImage out;

    //Main Pimage that gets passed tou Portraits instances
    OpenCV snapshot;
    PVector finalSnapshotPosition = new PVector();

    //Take Snapshot Button and clean(will be replaced)
    ControlP5 snapShotButton, clearButton;


    //Opecv, array that hold the face identified
    Rectangle[] faces;

    //whether recognizes a face or not
    boolean seesFace;

    //message
    PGraphics faceMessage;

    Portrait rectPortrait, vertexPortrait;
    Portrait textPortait, AgentPortrait;
    PVector rectFinalPosition,vertexFinalPosition,textFinalPosition;

    PGraphics result;
    float t = 0;
    float tInc = (float) .5;

    //render all portraits
    ArrayList<Portrait> allPortraits;
    int offset = 10;
    int accumulator = 0;
    float portraitWidth;

    //will be removed
    boolean bigScreen = 1 == 1;
    float animationFactor = 0;


    long faceEnters;
    boolean theFaceIsVisible = true;
    int cou = 0;
    int faceCameIn, runningTime;
    boolean haveTimeValue = true;
    boolean takeSnapshot = true;
    boolean showTimeGraphics = true;

    public void setup() {
        //small screen
        //big screen
        if (bigScreen)
            size(1920, 1080 - 50, P3D);
        else
            size(1366, 768, P3D);


        video = new Capture(this, 640 / 2, 480 / 2, 15);
        opencv = new OpenCV(this, 640 / 2, 480 / 2);
        opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);
        video.start();

        //tell opencv to get Colored snapshot
        opencv.useColor();


        //Snapshot Button
        snapShotButton = new ControlP5(this);
        snapShotButton.addButton("snapShotButton").setPosition(10, 30).setSize(60, 10).setVisible(false);
        clearButton = new ControlP5(this);
        clearButton.addButton("clearButton").setPosition(10, 50).setSize(60, 10).setVisible(false);

        //no Face Messsage
        faceMessage = createGraphics(width, 200);

        portraitWidth = (width - (3 * offset)) / 3;
        // cc.s;
        rectFinalPosition =new PVector();
        vertexFinalPosition=new PVector();
        textFinalPosition=new PVector();
    }


    public void draw() {
        background(0);
        opencv.loadImage(video);
        faces = opencv.detect();
        //cale(2);
        seesFace = faces.length == 0 ? false : true;
        showMessage();


//        for (int i = 1; i < allPortraits.size(); i++) {
//            int offstet=10;
//            image(allPortraits.get(i),offstet*i+allPortraits.get(i-1).width+,10);
//        }

        if (allPortraits != null) {
            animationFactor = ((frameCount * 4) % 360);
            float scaleLevel = 0;
            //animationFactor = (int)map(sin((frameCount*2)),-1,1,0,360);
//            image(allPortraits.get(0), 10, 10);
//            image(allPortraits.get(1), allPortraits.get(0).width + 20, 10);
            assert (allPortraits.size() != 0) : "ZERO LENGTH";
            assert (allPortraits.get(0).getResult() != null) : "EEEERRROORRR";

//            for (int i = 1; i < allPortraits.size() + 1; i++) {
//                accumulator = offset * i + allPortraits.get(i - 1).getResult().width * (i - 1);
//                if (allPortraits.get(i-1) instanceof VertexPortrait) {
//                    int t=i-1;
//                    image(((VertexPortrait) allPortraits.get(t)).generatePortrtait(animationFactor), accumulator, 10);
//                } else {
//                    image(allPortraits.get(i - 1).generatePortrtait(), accumulator, 10);
//                    //    accumulator = offset * i + allPortraits.get(i - 1).getWidth() * (i - 1);
//                }
//            }
//        } else {
//            animationFactor = 0;
            if (t < 0) {
                tInc = abs(tInc);
            } else if (t >= 1) {
                tInc = -abs(tInc);
            }
            t += tInc;

            rectFinalPosition.lerp(new PVector(portraitWidth, height), Ease.cubicBoth((float) .4));




            image(allPortraits.get(0).generatePortrtait(), offset, 10, rectFinalPosition.x, rectFinalPosition.y);
            if (rectFinalPosition.x>portraitWidth-10) {
                vertexFinalPosition.lerp(new PVector(portraitWidth, height),Ease.cubicBoth((float) .4));
                image(((VertexPortrait) allPortraits.get(1)).generatePortrtait(animationFactor), portraitWidth + 2 * offset, 10, portraitWidth, vertexFinalPosition.y);
            }
            if (vertexFinalPosition.y>height-10){
                textFinalPosition.lerp(new PVector(portraitWidth-10,height),(float).4);

            image(allPortraits.get(2).generatePortrtait(), portraitWidth * 2 + 3 * offset, 10, textFinalPosition.x, height);
            }


        }

        if (seesFace && theFaceIsVisible) {
//            System.out.println(cou+++" IF CLAUSE"+" "+theFaceIsVisible+" "+seesFace);
            faceCameIn = millis();
            theFaceIsVisible = false;
            System.out.println("FACE IN");
            haveTimeValue = true;

        } else if (!seesFace) {
            theFaceIsVisible = true;
            // System.out.println(cou+" ELSE IF "+" "+theFaceIsVisible+" "+seesFace);
            rectPortrait = null;
            result = null;
            allPortraits = null;
            animationFactor = 0;
            runningTime = frameCount;
            //System.out.println(runningTime);

        }

        if (seesFace && haveTimeValue && showTimeGraphics) {
            //System.out.println(faceCameIn + " " + millis());
            float t = (millis() - faceCameIn) / 1000;
            // System.out.println(t);;
            textSize(40);
            int countdown = (int) (3 - t) >= 0 ? (int) (3 - t) : 0;
            String message = "TAKING SNAPSHOT IN " + countdown + " seconds";
            faceMessage = createGraphics(width, 200);
            faceMessage.beginDraw();
            faceMessage.background(0);
            faceMessage.fill(191, 61, 39);
            faceMessage.textSize(80);
            faceMessage.text(message, 0, faceMessage.height / 2);
            //faceMessage.ellipse(faceMessage.width/2,faceMessage.height/2,200, 200);
            faceMessage.endDraw();
            image(faceMessage, width / 2 - faceMessage.width / 2, height / 2 - faceMessage.height / 2);
            if (t > 3 && takeSnapshot) {
                System.out.println("PASSED");
                //initPortraits();
                snapShotButton();
                takeSnapshot = false;
                showTimeGraphics = false;
            }
            //  haveTimeValue=false;
        } else if (!seesFace) {
            haveTimeValue = false;
            takeSnapshot = true;
            showTimeGraphics = true;
            clearButton();
            snapshot = null;
            rectFinalPosition.set(0,0);
            vertexFinalPosition.set(0, 0);
            textFinalPosition.set(0,0);
        }

        if (snapshot != null) {
            //with linear interpolation to the top right corner
            //finalSnapshotPosition.lerp(new PVector(width - face.width - 10, (int) (10 + 10 / ((float) height - face.height))), Ease.cubicBoth((float) .4));
            //image(face, finalSnapshotPosition.x, finalSnapshotPosition.y);


            finalSnapshotPosition.lerp(new PVector(width - face.width - 10,height-face.height),Ease.cubicBoth((float) .4));
            finalSnapshotPosition.lerp(new PVector(width - face.width - 10, (int) (10 +  ((float) height - face.height))), Ease.cubicBoth((float) .4));
            image(face, finalSnapshotPosition.x, finalSnapshotPosition.y);
            //original - no lerping
//            image(face, width - face.width - 10, (int) (10 + 10 / ((float) height - face.height)));
        }


        pushMatrix();
        //scale((float).5);
        translate(width - video.width, height - video.height);
        if (allPortraits == null)
            //scale((float).5);
            image(video, 0, 0);
        popMatrix();
    }


    void initPortraits() {
        snapshot = new OpenCV(this, opencv.getSnapshot(), true);
        face = snapshot.getOutput().get(faces[0].x, faces[0].y - 50, faces[0].width, faces[0].height + 70);
        //finalSnapshotPosition.set(faces[0].x,faces[0].y);
        boolean isColored = snapshot.getColorSpace() == 0 ? false : true;
        System.out.println(!isColored ? "Grey Pic" : "Colored Pic");
        allPortraits = new ArrayList<Portrait>();
        allPortraits.add(new RectPortrait(this, face, true));
        allPortraits.add(new VertexPortrait(this, face, true));
        allPortraits.add(new TextPortrait(this, face, true));
    }

    public void snapShotButton() {
        if (!seesFace) {
            System.out.println("NO FACE");
            return;
        }

        snapshot = new OpenCV(this, opencv.getSnapshot(), true);
        // snapshot.useColor();
        // snapshot.brightness(-100);
        face = snapshot.getOutput().get(faces[0].x, faces[0].y - 50, faces[0].width, faces[0].height + 70);
        //  width - video.width, height - video.height
        finalSnapshotPosition.set(faces[0].x + width - video.width, faces[0].y + height - video.height);
//        System.out.println("CLICK");
//        System.out.println(face.width+" "+face.height+" ratio:"+ (float)face.width/face.height);

        //tells whether pic is Grey==0 or Colored==1
        boolean isColored = snapshot.getColorSpace() == 0 ? false : true;
        System.out.println(!isColored ? "Grey Pic" : "Colored Pic");
        allPortraits = new ArrayList<Portrait>();
        allPortraits.add(new RectPortrait(this, face, true));
        allPortraits.add(new VertexPortrait(this, face, true));
        allPortraits.add(new TextPortrait(this, face, true));
//        System.out.println((allPortraits.get(1).getClass().getCanonicalName()));
        //allPortraits.add(new AgentPortrait(this,face,true).generatePortrtait());


        //        rectPortrait = new RectPortrait(this, face, true);
//        PGraphics sult = rectPortrait.generatePortrtait();
//        System.out.println(rectPortrait.toString());
//
//
//        textPortait = new TextPortrait(this, face, true);
//        System.out.println(textPortait.toString());
//        result = textPortait.generatePortrtait();
//
//        vertexPortrait = new VertexPortrait(this, face, true);
//
//        result = vertexPortrait.generatePortrtait();
        System.out.println("GENERATED");

    }

    public void clearButton() {
        if (face == null) {
            System.out.println("NOTHING TO CLEAR");
            return;
        }
        rectPortrait = null;
        result = null;
        allPortraits = null;
        animationFactor = 0;
        finalSnapshotPosition.set(0, 0);

    }

    public void showMessage() {
        if (!seesFace) {
            faceMessage.beginDraw();
            faceMessage.background(0);//(120, 0, 0);
            faceMessage.textSize(57);
            faceMessage.text("NO FACE", 0, faceMessage.height / 2);
            faceMessage.endDraw();
            image(faceMessage, width / 2 - faceMessage.width / 2, height / 2 - faceMessage.height / 2);
        }
    }

    void captureEvent(Capture c) {
        c.read();
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{CVMain.class.getName()});
    }
}


import controlP5.ControlP5;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
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
    PGraphics result;

    //render all portraits
    ArrayList<Portrait> allPortraits;
    int offset = 10;
    int accumulator = 0;


    //will be removed
    boolean bigScreen = true;
    float animationFactor=0;

    public void setup() {
        //small screen
        //big screen
        if (bigScreen)
            size(1920, 1080 - 20, P3D);
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
        snapShotButton.addButton("snapShotButton").setPosition(10, 30).setSize(60, 10);
        clearButton = new ControlP5(this);
        clearButton.addButton("clearButton").setPosition(10, 50).setSize(60, 10);

        //no Face Messsage
        faceMessage = createGraphics(200, 200);


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
            animationFactor=((frameCount*2)%360);
            //animationFactor = (int)map(sin((frameCount*2)),-1,1,0,360);
//            image(allPortraits.get(0), 10, 10);
//            image(allPortraits.get(1), allPortraits.get(0).width + 20, 10);
            for (int i = 1; i < allPortraits.size() + 1; i++) {
                accumulator = offset * i + allPortraits.get(i - 1).getWidth() * (i - 1);
                image(((VertexPortrait)allPortraits.get(i - 1)).generatePortrtait(animationFactor), accumulator, 10);
            }
        }else {
            animationFactor = 0;
        }
        if (snapshot != null) {
            image(face, width - face.width, 0);
        }
//        if (result != null) {
//            image(result, face.width, 0);
//        }

        //this is the camera

        pushMatrix();
        //scale((float).5);
        translate(width - video.width, height - video.height);
        if (allPortraits == null)
            //scale((float).5);
            image(video, 0, 0);
        popMatrix();
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
//        System.out.println("CLICK");
//        System.out.println(face.width+" "+face.height+" ratio:"+ (float)face.width/face.height);

        //tells whether pic is Grey==0 or Colored==1
        boolean isColored = snapshot.getColorSpace() == 0 ? false : true;
        System.out.println(!isColored ? "Grey Pic" : "Colored Pic");
        allPortraits = new ArrayList<Portrait>();
        //allPortraits.add(new RectPortrait(this, face, true));
        allPortraits.add(new VertexPortrait(this, face, true));
        //allPortraits.add(new TextPortrait(this, face, true));
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
        animationFactor=0;

    }

    public void showMessage() {
        if (!seesFace) {
            faceMessage.beginDraw();
            faceMessage.background(120, 0, 0);
            faceMessage.textSize(17);
            faceMessage.text("NO FACE", faceMessage.width / 2, faceMessage.height / 2);
            faceMessage.endDraw();
            image(faceMessage, 0, height / 2);
        }
    }

    void captureEvent(Capture c) {
        c.read();
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{CVMain.class.getName()});
    }
}


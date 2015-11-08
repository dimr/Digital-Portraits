import controlP5.ControlP5;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;

import java.awt.*;

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
    PImage snapshot;

    //Take Snapshot Button and clean(will be replaced)
    ControlP5 snapShotButton, clearButton;


    //Opecv, array that hold the face identified
    Rectangle[] faces;

    //whether recognizes a face or not
    boolean seesFace;

    //message
    PGraphics faceMessage;

    public void setup() {
        size(930, 600, P3D);
        video = new Capture(this, 640 / 2, 480 / 2, 15);
        opencv = new OpenCV(this, 640 / 2, 480 / 2);
        opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);
        video.start();

        //tell opencv to get Colored snapshot
        // opencv.useColor();


        //Snapshot Button
        snapShotButton = new ControlP5(this);
        snapShotButton.addButton("snapShotButton").setPosition(10, 30).setSize(60, 10);
        clearButton = new ControlP5(this);
        clearButton.addButton("clearButton").setPosition(10, 50).setSize(60, 10);

        //no Face Messsage
        faceMessage = createGraphics(200, 200);
    }


    public void draw() {
        background(100);
        opencv.loadImage(video);
        faces = opencv.detect();
        //cale(2);
        image(video, 100, 0);
        seesFace = faces.length == 0 ? false : true;
        showMessage();
        if (face != null) {
            image(face, width / 2, 0);
        }
    }


    public void snapShotButton() {
        if (!seesFace) {
            System.out.println("NO FACE");
            return;
        }

        snapshot = opencv.getSnapshot();
        face = snapshot.get(faces[0].x, faces[0].y - 50, faces[0].width, faces[0].height + 70);
        System.out.println("CLICK");
        System.out.println(face.width+" "+face.height);


    }

    public void clearButton() {
        if (face == null) {
            System.out.println("NOTHING TO CLEAR");
            return;
        }
        face = null;
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


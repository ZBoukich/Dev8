package opdracht3;

import processing.core.PApplet;

import java.util.*;

/**
 * Created by Zahey Boukich on 30-5-2016.
 */
public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    private static final String HOOGTEBESTAND = "hoogtebestand.txt";
    private static final int screenWidth = 500;
    private static final int screenHeight = 500;

    private String[] headers;

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    float xo;
    float yo;

    boolean play;
    boolean pause;

    private int runner;
    private int zoom;

    private List<Map<String, Float>> dataList = new ArrayList<>();

    public void settings() {
        size(screenWidth, screenHeight);
    }

    public void setup() {
        this.dataList = loadData(HOOGTEBESTAND);
        runner = 0;
        zoom = 500;
        saveMinAndMaxValue();
    }

    public void draw() {
        background(50);
        translate(xo, yo);

        prepareData();
    }

    // extraxt x,y,z map each value
    private void prepareData() {
        for (int i = 0; i < dataList.size(); i++) {
            float xValue = dataList.get(i).get(headers[0]);
            float yValue = dataList.get(i).get(headers[1]);
            float zValue = dataList.get(i).get(headers[2]);

            float xMapValue = map(xValue, minX, maxX, 0, zoom);
            float yMapValue = map(yValue, minY, maxY, 0, zoom);

            prepareAnimation(zValue, runner);
            rect(xMapValue, yMapValue, 1, 1);
        }
        if (pause) {
            runner += 0;
            writeText("Pause", 10, 30);
        }
        if (!pause) {
            runner += 1;
        }
    }


    private void writeText(String text, int x, int y) {
        textSize(32);
        text(text, x, y);
        fill(0, 0, 0);
    }


    private void prepareAnimation(float z, int loop) {
        if (play) {
            checkHeight(z, loop);
        } else {
            checkHeight(z, 0);
        }

    }

    // According to the ranges of the z values height give a color to simulate flote
    // lowest z value is like -1.889  highest is like 32  so i created ranges of 2 to simulate effect

    private void checkHeight(float z, int loop) {

        if(z > -2 && z <= 0)
        {
            if(loop >= 1)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(0,0,50);
            }
        }
        else if(z > 0 && z <= 2 )
        {
            if(loop >= 2)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(0,0,150);
            }
        }
        else if(z > 2 && z <= 4 )
        {
            if(loop >= 3)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(0,0,200);
            }
        }
        else if(z > 4 && z <= 6 )
        {
            stroke(0,0,255);
        }
        else if(z > 6 && z <= 8 )
        {
            if(loop >= 4)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(0,255,50);
            }
        }
        else if(z > 8 && z <= 10 )
        {
            if(loop >= 5)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(0,255,0);
            }
        }
        else if(z > 10 && z <= 12 )
        {
            if(loop >= 6)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(50,255,0);
            }
        }
        else if(z > 12 && z <= 14 )
        {
            if(loop >= 7)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(100,255,0);
            }
        }
        else if(z > 14 && z <= 16 )
        {
            if(loop >= 8)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(150,255,0);
            }
        }
        else if(z > 16 && z <= 18 )
        {
            if(loop >= 9)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(200,255,0);
            }
        }
        else if(z > 18 && z <= 20 )
        {
            if(loop >= 10)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,255,0);
            }
        }
        else if(z > 20 && z <= 22 )
        {
            if(loop >= 11)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,200,0);
            }
        }
        else if(z > 22 && z <= 24 )
        {
            if(loop >= 12)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,150,0);
            }
        }
        else if(z > 24 && z <= 26 )
        {
            if(loop >= 13)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,100,0);
            }
        }
        else if(z > 26 && z <= 28 )
        {
            if(loop >= 14)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,50,0);
            }
        }
        else if(z > 28 && z <= 30 )
        {
            if(loop >= 15)
            {
                stroke(0,0,255);
            }
            else
            {
                stroke(255,0,0);
            }
        }

    }


    // Map all the data as key-value pair
    // save all the maps in one list
    public List<Map<String, Float>> loadData(String file) {
        List<Map<String, Float>> dataList = new ArrayList<>();
        String[] tokens;
        String[] lines = loadStrings(file);
        String header = lines[0];
        headers = splitTokens(header, ",");
        System.out.println(headers);

        for (int i = 1; i < lines.length; i++) {
            Map<String, Float> hoogteMap = new HashMap<>();
            tokens = splitTokens(lines[i], ",");
            for (int j = 0; j < headers.length; j++) {
                hoogteMap.put(headers[j], parseFloat(tokens[j].replace(",", ".")));
            }
            dataList.add(hoogteMap);
        }
        return dataList;
    }


    // Save the min and max value of x datatype and y datatype
    // we need them later in several places
    public void saveMinAndMaxValue() {
        ArrayList<Float> listX = new ArrayList<Float>();
        ArrayList<Float> listY = new ArrayList<Float>();

        for (Map<String, Float> map : dataList) {
            listX.add(map.get(headers[0]));
            listY.add(map.get(headers[1]));}
        minX = Collections.min(listX);
        maxX = Collections.max(listX);
        minY = Collections.min(listY);
        maxY = Collections.max(listY);
    }

    // make dragging the animation with mouse possible
    public void mouseDragged() {
        xo = xo + (mouseX - pmouseX);
        yo = yo + (mouseY - pmouseY);
    }

    // According to which key pressed change the value of pause or play
    public void keyPressed() {

        if (key == CODED) {

            switch (keyCode) {
                case UP:
                    zoom = 1000;
                    break;
                case DOWN:
                    zoom = 500;
                    break;
                case ALT:
                    pause = true;
                    break;
                case CONTROL:
                    pause = false;
                    break;
                case LEFT:
                    if (!pause) {
                        runner = 0;
                        play = true;
                    }
                    break;
                case RIGHT:
                    if (!pause) {
                        runner = 0;
                        play = false;
                    }
            }

            // save the file if s pressed
        } else if (keyPressed) {
            if (key == 's') {
                save("overstroming.jpg");
            }
        }
    }

}

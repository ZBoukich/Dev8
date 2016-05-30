package opdracht1;

import opdracht1.model.Aardbeving;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * Created by Zahey Boukich on 15-5-2016.
 */
public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    //Initiale stuff

    private static final String DATA = "ijsland-metingen.json";
    private static final String IMAGE = "Kaart.png";
    PImage image;
    JSONObject jsonFile;

    JSONObject aardBeving;
    JSONArray aardBevingen;

    private String timestamp;
    private float latitude;
    private float longitude;
    private float depth;
    private float size;
    private float quality;
    private String humanReadableLocation;

    Aardbeving[] aardbevings = new Aardbeving[38];


    public void settings() {
        size(770, 334);
    }

    public void setup() {
        noLoop();
        image = loadImage(IMAGE, "png");
        jsonFile = loadJSONObject(DATA);
        aardBevingen = jsonFile.getJSONArray("results");

        for (int i = 0; i < aardBevingen.size(); i++) {
            aardBeving = aardBevingen.getJSONObject(i);
            result(aardBeving, i);
        }
        viewRiskRed();
    }
    // firroaga skype

    public void draw() {
        background(0);
        image.resize(550, 334);
        image(image, 0, 0);

        for (int i = 0; i < aardbevings.length; i++) {
            fill(aardbevings[i].getColor());
            ellipse(aardbevings[i].getLongitude(), aardbevings[i].getLatitude(), 15, 15);
            printValuesOnScreen();
        }
    }
    // Change the color red if the magnitude(size) of an earthquake is bigger than 5
    private void viewRiskRed() {
        for (int i = 0; i < aardbevings.length; i++) {
            if (aardbevings[i].getSize() > 5) {
                aardbevings[i].setColor(color(220, 20, 60));
            }
        }
    }


    private void result(JSONObject aardBeving, int i) {
        timestamp = aardBeving.getString("timestamp");
        depth = aardBeving.getFloat("depth");
        longitude = map(aardBeving.getFloat("longitude"), -22, -16, 133, 358);
        latitude = map(aardBeving.getFloat("latitude"), 66, 64, 106, 252);
        size = aardBeving.getFloat("size");
        quality = aardBeving.getFloat("quality");
        humanReadableLocation = aardBeving.getString("humanReadableLocation");

        // Based on the depth of an earthquake the point gets colored
        if (depth < 1f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(120, 0, 255));
        } else if (depth < 2f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(200, 40, 255));
        } else if (depth < 3f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(0, 120, 255));
        } else if (depth < 4f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(120, 0, 255));
        } else if (depth < 5f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(200, 40, 255));
        } else if (depth < 6f) {
            aardbevings[i] = aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(60, 200, 255));
        } else if (depth < 7f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(100, 100, 100));
        } else if (depth < 8f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(0, 206, 209));
        } else if (depth < 9f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(80, 80, 80));
        } else if (depth < 10f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(60, 60, 60));
        } else if (depth < 11f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(40, 40, 40));
        } else if (depth < 12f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(20, 20, 20));
        } else if (depth < 13f) {
            aardbevings[i] = new Aardbeving(timestamp,latitude, longitude, depth, size, quality, humanReadableLocation, color(0, 255, 0));
        }
    }

    // Show info about an earthquake if hovered on the map
    private void printValuesOnScreen() {
        for (int i = 0; i < aardBevingen.size(); i++) {
            float x = aardbevings[i].getLongitude();
            float y = aardbevings[i].getLatitude();
            if (dist(mouseX, mouseY, x, y) < 5) {
                fill(255);
                String timestamp = "Timestamp :" + aardbevings[i].getTimestamp();
                String lattitude = "Latitude : " + str(aardbevings[i].getLatitude());
                String longitude = "Longitude : " + str(aardbevings[i].getLongitude());
                String depth = "Depth : " + str(aardbevings[i].getDepth());
                String size = "Size : " + str(aardbevings[i].getSize());
                String quality = "Quality : " + str(aardbevings[i].getQuality());
                String humanreadableLocation = "Location : " + aardbevings[i].getHumanReadableLocation();
                text(timestamp, width - 220,10);
                text(lattitude, width - 220, 25);
                text(longitude, width - 220, 40);
                text(depth, width - 220, 55);
                text(size, width - 220, 70);
                text(quality, width, -220, 85);
                text(humanreadableLocation, width - 220, 100);
            }
        }
    }


    public void mouseClicked() {
        println(mouseX);
        println(mouseY);
    }
}

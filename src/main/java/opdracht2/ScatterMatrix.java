package opdracht2;

import processing.core.PApplet;

import java.util.*;

/**
 * Created by Zahey Boukich on 28-5-2016.
 */
public class ScatterMatrix extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{ScatterMatrix.class.getName()});
    }

    private static final String STUDENTDATA = "studentdata.txt";
    // Set here the Screendimensions
    private static final int nColumns = 6;
    private static final int screenWidth = 600;
    private static final int screenHeight = 600;

    private List<Map<String, Float>> dataList = new ArrayList<Map<String, Float>>();

    // Store these min and max values global, we'll need to remember this during the loop in creating the scattermatrix
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    private String[] headers;

    public void settings() {
        size(screenWidth, screenHeight);
    }

    public void setup() {
        loadData(STUDENTDATA);
    }

    public void draw() {
        background(255);
        prepareScatterMatrix();
    }

    //Prepare scatter matrix
    // loop through the map
    // compare each time two datatypes, if they are the same draw Text
    // else create a scattermatrix and add rangeinfo
    public void prepareScatterMatrix() {
        for (int y = 0; y < headers.length; y++) {
            for (int x = 0; x < headers.length; x++) {
                if (x == y) {
                    noFill();
                    writeText(x);
                } else if (x != y) {
                    saveMinAndMaxValue(x, y);
                    noFill();
                    for (int i = 0; i < dataList.size(); i++) {
                        createScatterMatrix(x, y, i);
                    }
                }
                drawMatrixRangeInfo(x, y);
            }

        }
    }

    private void createScatterMatrix(int x, int y, int i) {
        int x_ = x + 1;
        int y_ = y + 1;

        rect(x_ * screenWidth / nColumns, y_ * screenWidth / nColumns,
                screenWidth / nColumns, screenWidth / nColumns);

        float firstMapValue = dataList.get(i).get(headers[x]);
        float secondMapValue = dataList.get(i).get(headers[y]);

        float mapXValue = map(firstMapValue, minX, maxX, (x_ * screenWidth / nColumns) - screenWidth / nColumns, x_ * screenWidth / nColumns);
        float mapYValue = map(secondMapValue, minY, maxY, (y_ * screenWidth / nColumns) - screenWidth / nColumns, y_ * screenWidth / nColumns);

        //fill ellipse light blue
        // another nice rgb fill(0,0,255) is more dark blue
        fill(135, 206, 250);

        ellipse(mapXValue, mapYValue, 5, 5);

        surface.setTitle("Matrix Plot of Students");
    }

    // Map all the data as key-value pair
    // save all the maps in one list
    public void loadData(String file) {
        String[] tokens;
        String[] lines = loadStrings(file);
        String header = lines[0];
        headers = splitTokens(header);

        for (int i = 1; i < lines.length; i++) {
            Map<String, Float> studentMap = new HashMap<String, Float>();
            tokens = splitTokens(lines[i]);
            for (int j = 0; j < headers.length; j++) {
                studentMap.put(headers[j], parseFloat(tokens[j].replace(",", ".")));
            }
            dataList.add(studentMap);
        }
    }

    // Save the min and max value of x datatype and y datatype
    // we need them later in several places
    public void saveMinAndMaxValue(int x, int y) {
        ArrayList<Float> listX = new ArrayList<Float>();
        ArrayList<Float> listY = new ArrayList<Float>();

        for (Map<String, Float> map : dataList) {
            listX.add(map.get(headers[x]));
            listY.add(map.get(headers[y]));
        }
        minX = Collections.min(listX);
        maxX = Collections.max(listX);
        minY = Collections.min(listY);
        maxY = Collections.max(listY);

    }


    private void writeText(int x) {
        rect(x * screenWidth / nColumns, x * screenHeight / nColumns, screenWidth / nColumns, screenHeight / nColumns);
        textSize(10);
        textAlign(CENTER);

        switch (x) {
            case 0:
                text("Studentnummer", (screenWidth / nColumns) / 2, (screenHeight / nColumns) / 2);
            case 1:
                text("Leeftijd", ((screenWidth / nColumns) / 2) * 3, ((screenHeight / nColumns) / 2) * 3);
            case 2:
                text("ANA", ((screenWidth / nColumns) / 2) * 5, ((screenHeight / nColumns) / 2) * 5);
            case 3:
                text("DEV", ((screenWidth / nColumns) / 2) * 7, ((screenHeight / nColumns) / 2) * 7);
            case 4:
                text("PRJ", ((screenWidth / nColumns) / 2) * 9, ((screenHeight / nColumns) / 2) * 9);
            case 5:
                text("SKL", ((screenWidth / nColumns) / 2) * 11, ((screenHeight / nColumns) / 2) * 11);
        }
    }

    private void drawMatrixRangeInfo(int x, int y) {
        if (x == 0 && y == 0) {
            fill(176, 23, 31);
            textSize(7);
            text("min x:" + Math.round(minX), 25, 10);
            text("min y:" + Math.round(minY), 25, (screenHeight / nColumns) - 5);
            text("max x:" + Math.round(maxX), 25, 22);
            text("max y:" + Math.round(maxY), (screenWidth / nColumns) - 30, 12);
        } else if (x == 0) {
            y = y + 1;
            fill(176, 23, 31);
            textSize(7);
            text("min y:" + Math.round(minY), 20, y * (screenHeight / nColumns - 5));
            text("max y:" + Math.round(maxY), (screenHeight / nColumns) - ((screenHeight / nColumns) - 25), (y * screenHeight / nColumns) - (screenHeight / nColumns) - 10);
        } else if (y == 0) {
            x = x + 1;
            fill(176, 23, 31);
            textSize(7);
            text("min x:" + Math.round(minX), x * (screenHeight / nColumns) - ((screenWidth / nColumns) - 25), 10);
            text("max x:" + Math.round(maxX), x * (screenWidth / nColumns) - 30, 10);
        }
    }

}

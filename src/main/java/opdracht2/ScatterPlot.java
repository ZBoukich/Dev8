package opdracht2;

import processing.core.PApplet;

import java.util.*;

/**
 * Created by Zahey Boukich on 29-5-2016.
 */
public class ScatterPlot extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{ScatterPlot.class.getName()});
    }

    private static final String DATA = "data.txt";

    // Set here the Screendimensions
    private static final int screenWidth = 400;
    private static final int screenHeight = 400;

    List<Map<String, Float>> dataList = new ArrayList<Map<String, Float>>();

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    // Remember the categorie of each row, we need this later when we want to give a color to each different categorie
    private float cat;
    private String[] headers;

    public void settings() {
        size(screenWidth, screenHeight);
    }

    public void setup() {
        loadData(DATA);
    }

    public void draw() {
        noLoop();
        createScatterPlot();
    }

    private void createScatterPlot() {
        saveMinAndMaxValue(1, 2);
        noFill();
        rect(0, 0, 400, 400);

        for (int i = 0; i < dataList.size(); i++) {
            float tempCat = dataList.get(i).get(headers[0]);
            float eig1 = dataList.get(i).get(headers[1]);
            float eig2 = dataList.get(i).get(headers[2]);


            float mapXValue = map(eig1, minX, maxX, 0, 400);
            float mapYValue = map(eig2, minY, maxY, 0, 400);

            int color = color(41,36,33);
            if(this.cat != 0 && this.cat != tempCat){
                color = getAnotherColor();
            }
            this.cat = tempCat;
            fill(color);
            ellipse(mapXValue, mapYValue, 5, 5);
        }
    }

    public int getAnotherColor() {
        return color(random(0, 255), random(0, 255), random(0, 255));
    }

    // Methods below LoadData  and SaveMinAndMaxValue will move to a utility class in the future

    void loadData(String file) {
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
}
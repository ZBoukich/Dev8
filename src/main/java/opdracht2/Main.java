package opdracht2;

import opdracht2.model.DataModel;
import opdracht2.model.Student;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Zahey Boukich on 28-5-2016.
 */
public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    private static final String STUDENTDATA = "studentdata.txt";
    private static final String DATA = "data.txt";

    private ArrayList<Student> studentList = new ArrayList<Student>();
    private ArrayList<DataModel> dataList = new ArrayList<DataModel>();

    private ArrayList<Float> studentNummerList = new ArrayList<Float>();
    private ArrayList<Float> leeftijdList = new ArrayList<Float>();
    private ArrayList<Float> anaList = new ArrayList<Float>();
    private ArrayList<Float> devList = new ArrayList<Float>();
    private ArrayList<Float> prjList = new ArrayList<Float>();
    private ArrayList<Float> sklList = new ArrayList<Float>();

    private ArrayList<ArrayList<Float>> arrayLists = new ArrayList<ArrayList<Float>>();

    private ArrayList<Float> catList = new ArrayList<Float>();
    float cat;
    private ArrayList<Float> eig1List = new ArrayList<Float>();
    private ArrayList<Float> eig2List = new ArrayList<Float>();
    private ArrayList<Integer> colorList = new ArrayList<Integer>();


    private float minX;
    private float maxX;
    private float minY;
    private float maxY;


    //czet size 400,400 voor opdracht 2c  scattermatrix 600,600
    public void settings() {
        size(600, 600);
    }

    //
    public void setup() {
        // Bepaal de file generiek opgezet
        loadData(STUDENTDATA);
        prepareListValues(studentList);
    }


    public void draw() {
        background(255);
        // uncomment deze method voor een demo opdracht 2c en comment de plotScatterMatrix() dan wel uit;

        //createScatterPlot();

        plotScatterMatrix();

    }


    public void plotScatterMatrix() {
        for (int y = 0; y < arrayLists.size(); y++) {
            for (int x = 0; x < arrayLists.size(); x++) {
                if (x == y) {
                    noFill();
                    writeText(x);
                } else if (x != y) {
                    saveMinAndMaxValue(x, y);
                    noFill();
                    for (int i = 0; i < studentList.size(); i++) {
                        createScatterMatrix(x, y, i);
                    }
                }

            }
        }

    }

    private void saveMinAndMaxValue(int listX, int listY) {
        ArrayList<Float> x = arrayLists.get(listX);
        ArrayList<Float> y = arrayLists.get(listY);

        minX = Collections.min(x);
        maxX = Collections.max(x);
        minY = Collections.min(y);
        maxY = Collections.max(y);
    }

    private void createScatterMatrix(int x, int y, int i) {

        int x_ = x + 1;
        int y_ = y + 1;

        rect(x_ * 100, y_ * 100, 100, 100);

        float firstValue = arrayLists.get(x).get(i);
        float secondValue = arrayLists.get(y).get(i);


        float mapXValue = map(firstValue, minX, maxX, (x_ * 100) - 100, x_ * 100);
        float mapYValue = map(secondValue, minY, maxY, (y_ * 100) - 100, y_ * 100);

        fill(0, 0, 255);
        ellipse(mapXValue, mapYValue, 5, 5);
    }

    private void createScatterPlot() {
        saveMinAndMaxValue(0, 1);
        noFill();

        rect(400, 400, 0, 400);

        for (int i = 0; i < dataList.size(); i++) {
            float firstValue = arrayLists.get(0).get(i);
            float secondValue = arrayLists.get(1).get(i);
            int color = colorList.get(i);

            float mapXValue = map(firstValue, minX, maxX, 0, 400);
            float mapYValue = map(secondValue, minY, maxY, 0, 400);

            fill(color);
            ellipse(mapXValue, mapYValue, 5, 5);
        }

    }


    private void writeText(int x) {
        rect(x * 100, x * 100, 100, 100);
        textSize(10);
        textAlign(CENTER);

        switch (x) {
            case 0:
                text("Studentnummer", 50, 50);
            case 1:
                text("Leeftijd", 150, 150);
            case 2:
                text("ANA", 250, 250);
            case 3:
                text("DEV", 350, 350);
            case 4:
                text("PRJ", 450, 450);
            case 5:
                text("SKL", 550, 550);
        }
    }

    public void loadData(String file) {
        String[] lines = loadStrings(file);


        for (int i = 1; i < lines.length; i++) {
            if (file.equals(STUDENTDATA)) {
                addStudent(lines[i]);
            } else {
                addData(lines[i]);
            }
        }
    }

    private void addData(String lineToParse) {

        String[] tokens = splitTokens(lineToParse);

        float temp_cat = parseFloat(tokens[0]);
        float eig1 = parseFloat(tokens[1]);
        float eig2 = parseFloat(tokens[2].replace(",", "."));
        int color = color(120, 0, 255);
        if (this.cat != temp_cat) {
            color = getAnotherColor();
            //  System.out.println( "color " + color);
        }
        this.cat = temp_cat;
        DataModel nextDataModel = new DataModel(temp_cat, eig1, eig2, color);

        dataList.add(nextDataModel);
    }

    public int getAnotherColor() {
        return color(random(0, 255), random(0, 255), random(0, 255));
    }


    private void addStudent(String lineToParse) {
        String[] tokens = splitTokens(lineToParse);
        float studentnr = parseFloat(tokens[0]);
        float leeftijd = parseFloat(tokens[1]);
        float ana = parseFloat(tokens[2].replace(",", "."));
        float dev = parseFloat(tokens[3].replace(",", "."));
        float prj = parseFloat(tokens[4].replace(",", "."));
        float skl = parseFloat(tokens[5].replace(",", "."));

        Student nextStudent = new Student(studentnr, leeftijd, ana, dev, prj, skl);
        studentList.add(nextStudent);
    }


    public void prepareListValues(ArrayList list) {
        if (list == studentList) {
            for (Student student : studentList) {
                studentNummerList.add(student.getStudentNummer());
                leeftijdList.add(student.getLeeftijd());
                anaList.add(student.getAna());
                devList.add(student.getDev());
                prjList.add(student.getPrj());
                sklList.add(student.getSkl());
            }
            arrayLists.add(studentNummerList);
            arrayLists.add(leeftijdList);
            arrayLists.add(anaList);
            arrayLists.add(devList);
            arrayLists.add(prjList);
            arrayLists.add(sklList);
        } else if (list == dataList) {
            for (DataModel dataModel : dataList) {
                eig1List.add(dataModel.getEig1());
                eig2List.add(dataModel.getEig2());
                catList.add(dataModel.getCat());
                colorList.add(dataModel.getColor());
            }

            arrayLists.add(eig1List);
            arrayLists.add(eig2List);
            arrayLists.add(catList);


        }

    }


}

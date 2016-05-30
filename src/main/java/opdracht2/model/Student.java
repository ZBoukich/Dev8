package opdracht2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Zahey Boukich on 28-5-2016.
 */
@AllArgsConstructor
@Data
public class Student {
    private float studentNummer;
    private float leeftijd;
    private float ana;
    private float dev;
    private float prj;
    private float skl;


    @Override
    public String toString() {
        return "Studentnummer :" + studentNummer;
    }
}

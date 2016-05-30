package opdracht1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by Zahey Boukich on 15-5-2016.
 */
@AllArgsConstructor
public @Data class Aardbeving {

    private String timestamp;
    private float latitude;
    private float longitude;
    private float depth;
    private float size;
    private float quality;
    private String humanReadableLocation;
    private int color;



}

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import processing.data.JSONObject;
import org.junit.Assert.*;

/**
 * Created by Godfather on 15-5-2016.
 */
public class TestJunit {

    JSONObject jsonFile;

    @Test
    public void testPrintMessage() {
        String str = "Junit is working fine";
        Assert.assertEquals("Junit is working fine", str);
    }

    @Test
    public void testNumbers(){
        String url = "http://www.test.nl";
        String confirming = url.split("://")[0];
        Assert.assertEquals("http",confirming);

    }

}

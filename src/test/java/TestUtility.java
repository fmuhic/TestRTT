import org.junit.jupiter.api.Test;
import util.Utility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtility {
    @Test
    public void testConvertStreamToString() {
        InputStream istream = new ByteArrayInputStream("foo bar".getBytes());
        Utility u = new Utility();
        String result = u.convertStreamToString(istream);

        assertEquals("foo bar", result);
    }
}

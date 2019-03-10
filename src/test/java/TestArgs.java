import org.junit.jupiter.api.Test;
import util.Args;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestArgs {

    @Test
    public void testPitcherArgs() {

        String[] argStrings = {"-p", "--port", "8008", "--hostname", "foo.bar", "--mps", "256", "--size", "125"};
        Args args = new Args();
        args.parse(argStrings);

        assertEquals(true, args.pitcher);
        assertEquals(8008, args.port);
        assertEquals("foo.bar", args.hostname);
        assertEquals(256, args.mps);
        assertEquals(125, args.msgSize);
    }

    @Test
    public void testFetcherArgs() {

        String[] argStrings = {"-f", "--port", "8008", "--bind", "foo.bar"};
        Args args = new Args();
        args.parse(argStrings);

        assertEquals(true, args.fetcher);
        assertEquals(8008, args.port);
        assertEquals("foo.bar", args.bind);
    }

    @Test
    public void testDefaultArgs() {

        String[] argStrings = {};
        Args args = new Args();
        args.parse(argStrings);

        assertEquals(false, args.fetcher);
        assertEquals(false, args.pitcher);
        assertEquals(8000, args.port);
        assertEquals("127.0.0.1", args.bind);
        assertEquals("127.0.0.1", args.hostname);
        assertEquals(50, args.mps);
        assertEquals(200, args.msgSize);
        assertEquals(false, args.helpRequested);
    }
}

import org.junit.jupiter.api.Test;
import pitcher.Pitcher;

import static org.mockito.Mockito.*;

public class TestPitcher {
    @Test
    public void testStart() {
        Thread runner = mock(Thread.class);
        Pitcher pitcher = new Pitcher(runner);
        pitcher.start();

        verify(runner, times(1)).run();
    }
}

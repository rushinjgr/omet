import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// You could also do this to make this a little cleaner.
// import static org.mockito.Mockito.*;
import org.mockito.*;

public class OMETConnectionTest{
    public void testOMETConnection() {
        OMETConnection connection = new OMETConnection("jgr10","6803");
    }
}
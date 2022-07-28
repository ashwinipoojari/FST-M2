import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

    private static List list;

    @BeforeAll
    public static void setUp(){
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest()
    {
        assertEquals(2,list.size());
        list.add("gama");
        assertEquals(3,list.size());
        assertEquals("alpha",list.get(0));
        assertEquals("beta",list.get(1));
        assertEquals("gama",list.get(2));
    }

    @Test
    public void replaceTest()
    {
        assertEquals(2,list.size());
        list.add("delta");
        assertEquals(3,list.size());
        list.set(1,"theta") ;
        assertEquals("alpha",list.get(0));
        assertEquals("theta",list.get(1));
        assertEquals("delta",list.get(2));
    }
}

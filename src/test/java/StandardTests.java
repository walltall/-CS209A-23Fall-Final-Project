import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // check the difference for variable b by uncommenting this annotation
public class StandardTests {
    @org.junit.Test
    public void testLod(){
        Logger logger=Logger.getLogger(Test.class);
        logger.debug("debug级别捏");
//        logger.error("error级别捏");

    }
}
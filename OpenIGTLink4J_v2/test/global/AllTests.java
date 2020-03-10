import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import msg.AllMessageTests;
import network.QueryMechanismTest;
import network.StreamingMechanismTest;

@RunWith(Suite.class)
@SuiteClasses({
	
	AllMessageTests.class,
	QueryMechanismTest.class,
	StreamingMechanismTest.class
	
})
public class AllTests {

}

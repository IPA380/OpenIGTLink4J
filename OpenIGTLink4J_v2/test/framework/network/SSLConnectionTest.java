package network;

import static org.junit.Assert.*;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLConnectionTest {

	static final int SERVER_PORT = 30020;

	private static final boolean USE_TLS = true;

	static Server server;
	Client client;
	TestMessageHandler clientMessageHandler;

	private Logger log;
	static TestMessageHandler serverMessageHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		if (USE_TLS) {
			ServerThread.DEFAULT_SOCKET_FACTORY = SSLServerSocketFactory.getDefault();
			ClientThread.DEFAULT_SOCKET_FACTORY = SSLSocketFactory.getDefault();
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		log = LoggerFactory.getLogger(this.getClass());
		serverMessageHandler = new TestMessageHandler();
		server = new Server(SERVER_PORT, serverMessageHandler, null);
		clientMessageHandler = new TestMessageHandler();
		client = new Client("127.0.0.1", SERVER_PORT, clientMessageHandler, 
				false, null);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@After
	public void tearDown() throws Exception {		
		client.stop();
		client = null;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {e.printStackTrace();}
		server.stop();
	}

	@Test
	public void testClientConnect() {	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			/* TODO Automatisch generierter Erfassungsblock */
			e.printStackTrace();
		}
		
		assertTrue(client.isConnected());
	}

	@Test
	public void testClientStop() {
		
		server.stop();	
		
		client.stop();
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* TODO Automatisch generierter Erfassungsblock */
			e.printStackTrace();
		}
		
		assertEquals("Test active threads", 2, Thread.activeCount());
		log.info("done");
	}

}

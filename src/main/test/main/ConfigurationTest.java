package main;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class ConfigurationTest {

    public static final int port = 8080;
    private Configuration configuration = new Configuration("localhost", port);

    @Test
    public void testGetHost() throws Exception {
        assertEquals("localhost", configuration.getHost());
    }

    @Test
    public void testGetPort() throws Exception {
        assertEquals(port, configuration.getPort());
    }
}
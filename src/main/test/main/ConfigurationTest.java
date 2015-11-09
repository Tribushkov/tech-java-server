package main;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class ConfigurationTest {

    public static final int PORT = 8080;
    private Configuration configuration = new Configuration("localhost", PORT);

    @Test
    public void testGetHost() throws Exception {
        assertEquals("localhost", configuration.getHost());
    }

    @Test
    public void testGetPort() throws Exception {
        assertEquals(PORT, configuration.getPort());
    }
}
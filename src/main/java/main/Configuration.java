package main;

import java.io.Serializable;

/**
 * Created by dmitri on 27.10.15.
 */

public class Configuration implements Serializable {

    private String host;
    private int port;

    public Configuration() {
        this.host = "localhost:";
    }

    public Configuration(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}

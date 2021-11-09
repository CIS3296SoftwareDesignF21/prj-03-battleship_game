package com.battleship.utils;

import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IpChecker {

    /**
     * Get the public IP for the server to share
     *
     * @return a String containing the public IP
     * @throws Exception the connection has failed or the site is unreachable
     */
    public static String getIp() throws Exception {
        return InetAddress.getLocalHost().getHostAddress();
    }
}

package com.battleship.utils;

import java.net.InetAddress;

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

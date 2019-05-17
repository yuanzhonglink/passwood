package com.pass.util.inetwork;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yuanzhonglin
 * @date 2019/5/17
 * @Description: 获取本机ip
 */
@Slf4j
public class InetWorkHost {

    private static final Pattern IP_PATTERN = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");

    public static String getLocalAddress() throws Exception {
        String ip = "127.0.0.1";
        List<InetAddress> list = new ArrayList<>();

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {

            NetworkInterface anInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = anInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {

                InetAddress address = addresses.nextElement();
                if (address != null && address instanceof Inet4Address) {
                    System.out.println(address.getHostName() + "---" + address.getHostAddress());
                    list.add(address);
                }
            }
        }

        Iterator<InetAddress> iterator = list.iterator();

        InetAddress address;
        do {
            if (!iterator.hasNext()) {
                break;
            }
            address = iterator.next();
            ip = address.getHostAddress();
        } while (address == null || !(address instanceof Inet4Address) || !isValidAddress(address));

        return ip;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String ip = address.getHostAddress();
            if (ip == null) {
                return false;
            } else {
                return !"0.0.0.0".equals(ip) && !"127.0.0.1".equals(ip) && isValidIPv4(ip);
            }
        } else {
            return false;
        }
    }

    private static boolean isValidIPv4(String ip) {
        return ip == null ? false : IP_PATTERN.matcher(ip).matches();
    }

    public static void main(String[] args) throws Exception{
        String localAddress = getLocalAddress();
        System.out.println(localAddress);
    }
}

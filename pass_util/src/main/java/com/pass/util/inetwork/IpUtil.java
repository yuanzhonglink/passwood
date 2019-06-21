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
public class IpUtil {

    public static final String IP_MATCHER = "([0-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    public static final String PORT_MATCHER = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";


    private static final Pattern IP_PATTERN = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");

    public static InetAddress getLocalAddress() throws Exception {

        // 一、直接获取本机ip
        InetAddress inetAddress = InetAddress.getLocalHost();
        if (isValidAddress(inetAddress)) {
            return inetAddress;
        }

        // 二、直接获取本机ip失败
        List<InetAddress> list = new ArrayList<>();

        // 获取所有网卡信息
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

        // 遍历获取符合要求的ipv4地址
        Iterator<InetAddress> iterator = list.iterator();
        InetAddress address = null;
        do {
            if (!iterator.hasNext()) {
                break;
            }
            address = iterator.next();
        } while (address == null || !(address instanceof Inet4Address) || !isValidAddress(address));

        return address;
    }

    // 校验是否为ipv4地址
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

    // 校验ip格式是否正确
    private static boolean isValidIPv4(String ip) {
        return ip == null ? false : IP_PATTERN.matcher(ip).matches();
    }

    public static void main(String[] args) throws Exception{
        InetAddress localAddress = getLocalAddress();
        System.out.println(localAddress == null ? "127.0.0.1" : localAddress.getHostAddress());
    }
}

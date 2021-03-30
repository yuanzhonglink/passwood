package com.pass.util.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2021/3/30
 */
public class WeightSort {

    public static void main(String[] args) {
        List<Server> servers = new ArrayList<>();
        Server server1 = new Server("192.168.11.11:1234", 5);
        Server server2 = new Server("192.168.11.12:5678", 1);
        Server server3 = new Server("192.168.11.13:1234", 1);

        servers.add(server1);
        servers.add(server2);
        servers.add(server3);

        for (int i = 0; i < 7 ; i ++) {
            Server bestServer = getBestServer(servers);
            System.out.println(bestServer.getKey());
        }
    }



    public static Server getBestServer(List<Server> servers) {
        Server server;
        Server best = null;
        int total = 0;
        int size = servers.size();

        for (int i = 0; i < size; i++) {
            server = servers.get(i);

            server.setCurrentWeight(server.getCurrentWeight() + server.getWeight());
            total += server.getWeight();

            if (best == null || server.getCurrentWeight() > best.getCurrentWeight()) {
                best = server;
            }
        }

        if (best == null) {
            return null;
        }

        best.setCurrentWeight(best.getCurrentWeight() - total);
        return best;
    }
}

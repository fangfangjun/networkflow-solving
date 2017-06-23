package com.cacheserverdeploy.deploy;

public class Node {
    int to, flow, cost, next;
    Node(int to, int flow, int cost, int next) {
        this.to = to;
        this.flow = flow;
        this.cost = cost;
        this.next = next;
    }
}

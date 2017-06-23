package com.cacheserverdeploy.deploy;

import java.util.ArrayList;
import java.util.Arrays;

public class MinCostFlow {
    int n, m, p;
    int cost;
    final int INF = 0x7fffffff;
    Node[] w;
    int[] h;
    int cnt = 1;
    int S, T;
    int ans;
    int[] r;
    int totFlow;
    int curFlow;
    boolean[] consumer;
    int[] toNum;
    int[][] adj;
    int[] d;
    boolean[] vst;
    ArrayList<String> list;
    int serNum;
    int[] serFlow;
    int[] serCost;
    int[] posCost;
    int[] preFlow;
    int[] pV;
    void add(int x, int y, int flow, int cost) {
        w[++cnt] = new Node(y, flow, cost, h[x]);
        h[x] = cnt;
        w[++cnt] = new Node(x, 0, -cost, h[y]);
        h[y] = cnt;
    }
    int[] rec;
    int print(int u, int f, int k) {
        int ret = 0;
        rec[k] = u;
        if (u == T) {
            StringBuilder s = new StringBuilder();
            for (int i = 1; i < k; i++) {
                System.out.print(rec[i] + " ");
                s.append(rec[i] + " ");
            }
            System.out.println(r[rec[k-1]] + " " + f + " " + pV[rec[1]]);
            s.append(r[rec[k-1]] + " " + f + " " + pV[rec[1]]);
            list.add(s.toString());
            return f;
        }
        for (int v = h[u]; v != 0; v = w[v].next) {
            if ((v & 1) == 1) continue;
            int j;
            j = w[v].to;
            if (w[v^1].flow > 0) {
                int t;
                t = print(j, Math.min(f, w[v^1].flow), k+1);
                w[v^1].flow -= t;
                w[v].flow += t;
                f -= t;
                ret += t;
                if (f == 0)
                    return ret;
            }
        }
        return ret;
    }
    MinCostFlow(String[] s) {
        w = new Node[100001];
        h = new int[2001];
        d = new int[2001];
        vst = new boolean[2001];
        r = new int[2001];
        rec = new int[2001];
        consumer = new boolean[2001];
        toNum = new int[2001];
        adj = new int[2001][];
        list = new ArrayList<String>();
        serFlow = new int[2001];
        serCost = new int[2001];
        posCost = new int[2001];
        preFlow = new int[2001];
        pV = new int[2001];
        init(s);
    }
    void init(String[] s) {
        int[][] b = new int[20001][];

        int[] tmp;
        int i;
        tmp = TypeUtil.stringToInt(s[0]);

        n = tmp[0];  //节点
        m = tmp[1];  //链路
        p = tmp[2];  //消费节点

        S = n;
        T = n + 1;
        totFlow = 0;
        cnt = 1;
        Arrays.fill(h, 0);
        for (serNum = 2; s[serNum].length() > 0; serNum++) {
            int x, f, c;
            tmp = TypeUtil.stringToInt(s[serNum]);
            x = tmp[0];      //档次
            f = tmp[1];      //输出能力
            c = tmp[2];      //成本
            serFlow[x] = f;
            serCost[x] = c;
        }
        //tmp = TypeUtil.stringToInt(s[2]);
        //cost = tmp[0];
        for (i = 0; i < n; i++)
            add(S, i, 0, 0);
        for (i = serNum+1; i < n+serNum+1; i++) {
            int x, c;
            tmp = TypeUtil.stringToInt(s[i]);
            x = tmp[0];     //节点ID
            c = tmp[1];     //部署成本
            posCost[x] = c;
            //System.out.printf("%d %d\n", x, c);
        }
        for (i = n+serNum+2; i < m+n+serNum+2; i++) {
            tmp = TypeUtil.stringToInt(s[i]);
            b[i-(n+serNum+2)] = tmp;

            toNum[tmp[0]]++;
            toNum[tmp[1]]++;
        }

        for (i = 0; i < n; i++) {
            adj[i] = new int[toNum[i]+1];
        }

        for (i = 0; i < m; i++) {
            int x, y, f, c;

            tmp = b[i];
            x = tmp[0];    //起始节点ID
            y = tmp[1];    //终止ID

            f = tmp[2];    //总带宽
            c = tmp[3];    //租用费

            add(x, y, f, c);
            add(y, x, f, c);

            adj[x][++adj[x][0]] = y;
            adj[y][++adj[y][0]] = x;
            //System.out.printf("%d %d %d %d\n", x, y, f, c);
        }

        for (i = m+n+serNum+3; i < p+m+n+serNum+3; i++) {
            int x, y, z;
            tmp = TypeUtil.stringToInt(s[i]);
            x = tmp[0];    //消费节点ID
            y = tmp[1];    //网络节点ID

            z = tmp[2];    //消耗需求
            add(y, T, z, 0);
            r[y] = x;
            consumer[y] = true;
            totFlow += z;
            //System.out.printf("%d %d %d\n", x, y, z);
        }

        serNum -= 2;

        System.out.printf("%d %d %d\n", n, m, p);
        System.out.printf("%d\n", serNum);
        System.out.printf("%d %d\n", S, T);
        System.out.printf("%d %d\n", totFlow, cnt);
        //for (i = 0; i < serNum; i++) {
            //System.out.printf("%d %d\n", serFlow[i], serCost[i]);
        //}
    }
    int calc(boolean[] vst) {
        return 0;
    }
    int calc(int[] vst) {
        ans = 0;
        for (int i = 2; i <= cnt; i+=2) {
            w[i].flow += w[i^1].flow;
            w[i^1].flow = 0;
        }
        for (int v = h[S]; v != 0; v = w[v].next) {
            int j;
            j = w[v].to;
            if (vst[j] >= 0) {
                w[v].flow = serFlow[vst[j]];
                ans += serCost[vst[j]];
                ans += posCost[j];
            }
            else {
                w[v].flow = 0;
            }
        }
        curFlow = 0;

        zkw();

        for (int i = 0; i < n; i++) {
            preFlow[i] = 0;
        }

        for (int v = h[S]; v != 0; v = w[v].next) {
            int j;
            j = w[v].to;
            if (vst[j] >= 0) {
                preFlow[j] = w[v^1].flow;
            }
        }

        for (int i = 0; i < n; i++) {
            pV[i] = vst[i];
        }

        if (curFlow != totFlow) return INF;
        return ans;
    }

    int aug(int u, int f) {
        int j, v, t, ret = 0;
        if (u == T) {
            ans += f * d[S];
            curFlow += f;
            return f;
        }
        vst[u] = true;
        for (v = h[u]; v != 0; v = w[v].next) {
            j = w[v].to;
            if (w[v].flow > 0 && !vst[j] && d[j] + w[v].cost == d[u]) {
                t = aug(j, Math.min(f, w[v].flow));
                w[v].flow -= t;
                w[v ^ 1].flow += t;
                f -= t;
                ret += t;
                if (f == 0) return ret;
            }
        }
        return ret;
    }
    boolean modlabel() {
        int i, j, v;
        int minn = INF;
        for (i = 0; i <= T; i++)
            if (vst[i]) {
                for (v = h[i]; v != 0; v = w[v].next) {
                    j = w[v].to;
                    if (w[v].flow > 0 && !vst[j]) {
                        minn = Math.min(minn, d[j] + w[v].cost - d[i]);
                    }
                }
            }
        if (minn == INF) return false;
        for (i = 0; i <= T; i++)
            if (vst[i]) d[i] += minn;
        return minn < INF;
    }
    void zkw() {
        Arrays.fill(d, 0);
        do {
            do {
                Arrays.fill(vst, false);
            } while (aug(S, INF) > 0);
        } while (modlabel());
    }

    
}

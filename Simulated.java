package com.cacheserverdeploy.deploy;
import java.util.ArrayList;

public class Simulated {
    static int LEN = 85000;

    int n;
    double SUB;
    double GE;
    double T;
    double T_min;
    double r;
    int[] scheme;
    int[] step;
    int[] solution;

    int[] buf;

    int scheme_cost;
    int step_cost;
    int solution_cost;
    MinCostFlow cf;
    long start;
    Simulated(String[] s) {
        start = System.currentTimeMillis();
        cf = new MinCostFlow(s);
        n = cf.n;
        scheme = new int[2001];
        step = new int[2001];
        solution = new int[2001];
        buf = new int[2001];

        int ans_tot = 0;
        SUB = 0.1;
        GE = 0.3;

        //memcpy(scheme, cf.consumer);
        for (int i = 0; i < n; i++) {
            scheme[i] = cf.serNum - 1;
        }
        serLev(scheme, 1);
        scheme_cost = cf.calc(scheme);
        memcpy(solution, scheme);
        solution_cost = scheme_cost;



        int tot = 0;
        /*for (int i = 1; i <= 500; i++) {
            int dE;
            towards(scheme, step);
            step_cost = cf.calc(step);
            dE = scheme_cost - step_cost;
            if (dE > 0) {
                memcpy(scheme, step);
                scheme_cost = step_cost;
                if (scheme_cost < solution_cost) {
                    memcpy(solution, scheme);
                    solution_cost = scheme_cost;
                }
            }
            System.out.printf("%d %d %d\n", i, scheme_cost, solution_cost);
            if (System.currentTimeMillis() - start > LEN) {
                break;
            }
        }*/
		for (int i = 1; i <= 5; i++) {
			T = 80;
			T_min = 6;
			r = 0.90;
			SUB = 0.5;
			GE = 0.5;
			memcpy(scheme, solution);
            scheme_cost = solution_cost;
			while (T > T_min) {
				int dE;
				towards(scheme, step);
				serLev(step, 0);
				step_cost = cf.calc(step);
				dE = scheme_cost - step_cost;
				if (dE > 0) {
					memcpy(scheme, step);
					scheme_cost = step_cost;
					if (scheme_cost < solution_cost) {
						memcpy(solution, scheme);
						solution_cost = scheme_cost;
						ans_tot = 0;
					}
				}
				else {
					if (Math.exp(dE / T) > Math.random()) {
						memcpy(scheme, step);
						scheme_cost = step_cost;
						T = r * T;
					}
				}
				tot++;
				ans_tot++;
				System.out.printf("%d %d %d\n", tot, scheme_cost, solution_cost);
				if (ans_tot > 10000) break;
				if (System.currentTimeMillis() - start > LEN) {
					break;
				}
			}
		}
    }
    void serLev(int a[], int b) {
        int minn, t;
        minn = cf.INF;

        for (int i = 0; i < cf.serNum; i++) {
            
            for (int j = 0; j < n; j++) {
                if (a[j] >= 0)
                    a[j] = i;
            }
            t = cf.calc(a);

            for (int j = 0; j < n; j++) {
                if (a[j] < 0) continue;
                for (int l = cf.serNum-1; l >= 0; l--) {
                    if (cf.preFlow[j] > cf.serFlow[l])
                        break;
                    a[j] = l;
                }
            }

            t = cf.calc(a);
            if (t < minn) {
                minn = t;

                memcpy(buf, a);
            }
        }

        if (minn < cf.INF) {

            memcpy(a, buf);
            if (b == 1) {
                t = cf.calc(a);
                for (int i = 0; i < n; i++) {
                    if (cf.preFlow[i] == 0)
                        a[i] = -1;
                }
            }
        }
    }
    int picSer(int[] a) {
        double ret = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] >= 0)
                ret += 1;
        }
        ret = Math.random() * ret;
        for (int i = 0; i < n; i++) {
            if (a[i] < 0) continue;
            if (ret < 1 + 1e-8) return i;
            ret -= 1;
        }
        return 0;
    }
    int picSerAnti(int[] a) {
        double ret = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] < 0)
                ret += 1;
        }
        ret = Math.random() * ret;
        for (int i = 0; i < n; i++) {
            if (a[i] >= 0) continue;
            if (ret < 1 + 1e-8) return i;
            ret -= 1;
        }
        return 0;
    }
    int pick(int[] adj) {
        int l;
        l = adj[0];
        double ret;
        ret = Math.random() * l;
        for (int i = 1; i <= l; i++) {
            if (ret < 1 + 1e-8) return adj[i];
            ret -= 1;
        }
        return adj[1];
    }
    void memcpy(int[] a, int[] b) {
        for (int i = 0; i < n; i++)
            a[i] = b[i];
    }
    void towards(int[] a, int[] b) {
        if (Math.random() < GE) {
            memcpy(b, a);
            for (int i = 0; i < n; i++) {
                if (Math.random() < 8.0 / n) {
                    if (b[i] >= 0) {
                        b[i] = -1;
                    }
                }
            }
            /*int u = picSer(a);
            int v = picSerAnti(a);
            b[v] = b[u];
            if (Math.random() < SUB)
                b[u] = -1;*/
        }
        else {
            memcpy(b, a);
            int u = picSer(a);
            int v = pick(cf.adj[u]);
            b[v] = b[u];
            if (Math.random() < SUB)
                b[u] = -1;
        }
    }
    
}
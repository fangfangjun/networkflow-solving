package com.cacheserverdeploy.deploy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TypeUtil {
    public static int[] stringToInt(final String s) {
        List<Integer> a = new ArrayList<Integer>();
        int i, len, t = 0;
        boolean vst = false;
        len = s.length();
        for (i = 0; i < len; i++) {
            char ch;
            ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                t = 10 * t + ch - '0';
                vst = true;
            }
            else {
                if (vst)
                    a.add(t);
                t = 0;
            }
        }
        if (vst)
            a.add(t);
        int[] ret;
        ret = new int[a.size()];
        i = 0;
        for (Iterator<Integer> it = a.iterator(); it.hasNext(); ) {
            ret[i++] = it.next();
        }
        return ret;
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BridgesGenerator.java

package com.maddox.il2.tools;

import com.maddox.TexImage.TexImage;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.tools:
//            Bridge

public class BridgesGenerator
{

    public BridgesGenerator()
    {
    }

    private static int WintI(int i, int j)
    {
        return W.intI(i * 4 + 2, j * 4 + 2);
    }

    private static int WintL(int i, int j, int k, int l)
    {
        byte byte0 = ((byte)(k <= i ? -1 : 1));
        if(k == i)
            byte0 = 0;
        byte byte1 = ((byte)(l <= j ? -1 : 1));
        if(l == j)
            byte1 = 0;
        int i1 = 255;
        i = i * 4 + 2;
        j = j * 4 + 2;
        k = k * 4 + 2;
        for(l = l * 4 + 2; j != l || i != k; i += byte0)
        {
            if(i1 > W.intI(i, j))
                i1 = W.intI(i, j);
            j += byte1;
        }

        return i1;
    }

    private static int getlen(int i, int j, int k)
    {
        int l;
        for(l = 0; l < 50;)
        {
            if(i < 0 || j < 0 || i >= T.sx || j >= T.sy || (T.I(i, j) & k) == 0)
                break;
            T.I(i, j, T.I(i, j) & ~k);
            l++;
            i += dx;
            j += dy;
        }

        i -= dx;
        j -= dy;
        if(l > 0 && l < 8)
        {
            if(prn)
                java.lang.System.out.print("" + k + ", " + i + "," + j + ",");
            if(num < coords.length)
            {
                coords[num++] = i;
                coords[num++] = j;
            }
        }
        return l;
    }

    private static void conn8(int i, int j, int k)
    {
        int l = len = 0;
        if((T.I(i - 1, j - 1) & k) != 0)
            l |= 1;
        if((T.I(i, j - 1) & k) != 0)
            l |= 2;
        if((T.I(i + 1, j - 1) & k) != 0)
            l |= 4;
        if((T.I(i + 1, j) & k) != 0)
            l |= 8;
        if((T.I(i + 1, j + 1) & k) != 0)
            l |= 0x10;
        if((T.I(i, j + 1) & k) != 0)
            l |= 0x20;
        if((T.I(i - 1, j + 1) & k) != 0)
            l |= 0x40;
        if((T.I(i - 1, j) & k) != 0)
            l |= 0x80;
        switch(l)
        {
        default:
            return;

        case 1: // '\001'
            dx = -1;
            dy = -1;
            break;

        case 2: // '\002'
            dx = 0;
            dy = -1;
            break;

        case 4: // '\004'
            dx = 1;
            dy = -1;
            break;

        case 8: // '\b'
            dx = 1;
            dy = 0;
            break;

        case 16: // '\020'
            dx = 1;
            dy = 1;
            break;

        case 32: // ' '
            dx = 0;
            dy = 1;
            break;

        case 64: // '@'
            dx = -1;
            dy = 1;
            break;

        case 128: 
            dx = -1;
            dy = 0;
            break;
        }
        len = com.maddox.il2.tools.BridgesGenerator.getlen(i, j, k);
    }

    private static void comp(int i, int j, int k)
    {
        if((T.I(i, j) & k) == 0)
            return;
        com.maddox.il2.tools.BridgesGenerator.conn8(i, j, k);
        if(len > 0 && len < 8)
        {
            if(prn)
                java.lang.System.out.println(" " + i + "," + j);
            type[num / 4] = (byte)k;
            if(num < coords.length)
            {
                coords[num++] = i;
                coords[num++] = j;
            }
        }
    }

    private static void printList(com.maddox.TexImage.TexImage teximage)
    {
        num = 0;
        if(T != teximage)
            T.set(teximage);
        for(int k = 0; k < T.sy; k++)
        {
            for(int i = 0; i < T.sx; i++)
                if((T.intI(i, k) & 0xe0) == 0 || (T.intI(i, k) & 0x1c) != 28)
                    T.I(i, k, 0);
                else
                    T.I(i, k, T.intI(i, k) & 0xe0);

        }

        for(int l = 1; l < T.sy - 1; l++)
        {
            for(int j = 1; j < T.sx - 1; j++)
            {
                if((T.I(j, l) & 0x80) != 0)
                    com.maddox.il2.tools.BridgesGenerator.comp(j, l, 128);
                if((T.I(j, l) & 0x40) != 0)
                    com.maddox.il2.tools.BridgesGenerator.comp(j, l, 64);
                if((T.I(j, l) & 0x20) != 0)
                    com.maddox.il2.tools.BridgesGenerator.comp(j, l, 32);
            }

        }

    }

    public static com.maddox.il2.tools.Bridge[] getBridgesArray(com.maddox.TexImage.TexImage teximage)
    {
        prn = false;
        com.maddox.il2.tools.BridgesGenerator.printList(teximage);
        prn = true;
        int i = num / 4;
        com.maddox.il2.tools.Bridge abridge[] = new com.maddox.il2.tools.Bridge[i];
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.tools.Bridge bridge = abridge[j] = new Bridge();
            int k = j * 4;
            bridge.x1 = coords[k];
            bridge.y1 = coords[k + 1];
            bridge.x2 = coords[k + 2];
            bridge.y2 = coords[k + 3];
            bridge.type = type[j] & 0xff;
        }

        return abridge;
    }

    public static void shorting(com.maddox.il2.tools.Bridge bridge)
    {
        int i = bridge.x1;
        int j = bridge.x2;
        int k = bridge.y1;
        int l = bridge.y2;
        int i1 = j - i;
        int j1 = l - k;
        if(i1 > 0)
            i1 = 1;
        else
        if(i1 < 0)
            i1 = -1;
        if(j1 > 0)
            j1 = 1;
        else
        if(j1 < 0)
            j1 = -1;
        int l1;
        do
        {
            l1 = 0;
            int k1 = java.lang.Math.max(java.lang.Math.abs(i - j), java.lang.Math.abs(k - l));
            if(k1 <= 1)
                break;
            byte byte0 = T.I(i, k);
            if((byte0 & 0xe0) != 0 && (byte0 & 0x1c) == 28 && com.maddox.il2.tools.BridgesGenerator.WintL(i, k, i + i1, k + j1) >= 250)
            {
                T.I(i, k, T.I(i, k) & 0xffffffe3);
                i += i1;
                k += j1;
                l1++;
            }
            k1 = java.lang.Math.max(java.lang.Math.abs(i - j), java.lang.Math.abs(k - l));
            if(k1 <= 1)
                break;
            byte0 = T.I(j, l);
            if((byte0 & 0xe0) != 0 && (byte0 & 0x1c) == 28 && com.maddox.il2.tools.BridgesGenerator.WintL(j, l, j - i1, l - j1) >= 250)
            {
                T.I(j, l, T.I(j, l) & 0xffffffe3);
                j -= i1;
                l -= j1;
                l1++;
            }
        } while(l1 != 0);
        bridge.x1 = i;
        bridge.x2 = j;
        bridge.y1 = k;
        bridge.y2 = l;
    }

    public static void longing(com.maddox.il2.tools.Bridge bridge)
    {
        int i = bridge.x1;
        int j = bridge.x2;
        int k = bridge.y1;
        int l = bridge.y2;
        int i1 = j - i;
        int j1 = l - k;
        if(i1 > 0)
            i1 = 1;
        else
        if(i1 < 0)
            i1 = -1;
        if(j1 > 0)
            j1 = 1;
        else
        if(j1 < 0)
            j1 = -1;
        if(com.maddox.il2.tools.BridgesGenerator.WintI(i, k) < 130)
        {
            i -= i1;
            k -= j1;
            if((T.I(i, k) & 0xe0) != 0)
                T.I(i, k, T.I(i, k) | 0x1c);
        }
        if(com.maddox.il2.tools.BridgesGenerator.WintI(j, l) < 130)
        {
            j += i1;
            l += j1;
            if((T.I(j, l) & 0xe0) != 0)
                T.I(j, l, T.I(j, l) | 0x1c);
        }
        bridge.x1 = i;
        bridge.x2 = j;
        bridge.y1 = k;
        bridge.y2 = l;
    }

    public static void deleting(com.maddox.il2.tools.Bridge bridge)
    {
        int i = bridge.x1;
        int j = bridge.x2;
        int k = bridge.y1;
        int l = bridge.y2;
        int i1 = j - i;
        int j1 = l - k;
        if(i1 > 0)
            i1 = 1;
        else
        if(i1 < 0)
            i1 = -1;
        if(j1 > 0)
            j1 = 1;
        else
        if(j1 < 0)
            j1 = -1;
        for(; k != l || i != j; i += i1)
        {
            T.I(i, k, T.I(i, k) & 0xffffffe3);
            k += j1;
        }

    }

    public static boolean exists(com.maddox.il2.tools.Bridge bridge)
    {
        return com.maddox.il2.tools.BridgesGenerator.WintL(bridge.x1, bridge.y1, bridge.x2, bridge.y2) < 180;
    }

    public static void main(java.lang.String args[])
        throws java.lang.Exception
    {
        if(args.length != 1)
        {
            java.lang.System.out.println("Usage: Bridges filename.tga");
            return;
        }
        T.LoadTGA(args[0]);
        com.maddox.il2.tools.Bridge abridge[] = com.maddox.il2.tools.BridgesGenerator.getBridgesArray(T);
        T.LoadTGA(args[0]);
        W.LoadTGA(com.maddox.il2.tools.BridgesGenerator.ch_name(args[0], "WaterBig.tga"));
        for(int i = 0; i < abridge.length; i++)
        {
            com.maddox.il2.tools.BridgesGenerator.shorting(abridge[i]);
            com.maddox.il2.tools.BridgesGenerator.longing(abridge[i]);
            java.lang.System.out.println(abridge[i]);
        }

        T.SaveTGA(args[0] + "~");
    }

    private static java.lang.String ch_name(java.lang.String s, java.lang.String s1)
    {
        int i = s.lastIndexOf('\\');
        java.lang.System.out.println(s.substring(0, i + 1) + s1);
        return s.substring(0, i + 1) + s1;
    }

    private static void wipeBridge(com.maddox.il2.tools.Bridge bridge)
    {
        int i = bridge.x1;
        int j = bridge.x2;
        int k = bridge.y1;
        int l = bridge.y2;
        int i1 = j - i;
        int j1 = l - k;
        if(i1 > 0)
            i1 = 1;
        else
        if(i1 < 0)
            i1 = -1;
        if(j1 > 0)
            j1 = 1;
        else
        if(j1 < 0)
            j1 = -1;
        i *= 4;
        k *= 4;
        j *= 4;
        for(l *= 4; k != l || i != j; i += i1)
        {
            W.I(i, k, 0);
            k += j1;
        }

    }

    public static final int HIGHWAY = 128;
    public static final int RAIL = 64;
    public static final int ROAD = 32;
    public static final int CH_RATIO = 4;
    public static final int CH2 = 2;
    public static final int WATERLEV = 250;
    private static com.maddox.TexImage.TexImage T = new TexImage();
    private static final int MXL = 8;
    private static int dx;
    private static int dy;
    private static int len;
    private static boolean prn = true;
    private static final int MAXBRIDGE = 1000;
    private static int num;
    private static int coords[] = new int[4000];
    private static byte type[] = new byte[1000];
    static com.maddox.TexImage.TexImage W = new TexImage();

}

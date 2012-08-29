// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PathFind.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.TexImage.TexImage;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.tools.Bridge;

// Referenced classes of package com.maddox.il2.builder:
//            PNodes

public class PathFind
{

    public static void unloadMap()
    {
        if(curType >= 0)
            com.maddox.il2.builder.PathFind.UnloadMap();
        b = null;
        tShip = null;
        tNoShip = null;
        curType = -1;
    }

    private static void setMap(int i)
    {
        boolean flag = false;
        if(i == 2)
            flag = curType != 2;
        else
        if(i == 3)
            flag = curType < 0;
        else
            flag = curType == 2 || curType < 0;
        if(flag)
        {
            com.maddox.TexImage.TexImage teximage = tNoShip;
            if(i == 2)
                teximage = tShip;
            if(curType >= 0)
                com.maddox.il2.builder.PathFind.UnloadMap();
            com.maddox.il2.builder.PathFind.setMap(teximage.image, teximage.sx, teximage.sy, b);
            curType = i;
        }
    }

    private static void setMap(byte abyte0[], int i, int j, com.maddox.il2.tools.Bridge abridge[])
    {
        startPoint[0] = startPoint[1] = null;
        int k = com.maddox.il2.engine.Engine.land().config.outsideMapCell;
        if(abridge == null || abridge.length == 0)
        {
            com.maddox.il2.builder.PathFind.SetMap(abyte0, k, i, j, 200F, 20F, null, 0);
        } else
        {
            int ai[] = new int[abridge.length * 5];
            int l = 0;
            for(int i1 = 0; i1 < abridge.length; i1++)
            {
                ai[l + 0] = abridge[i1].x1;
                ai[l + 1] = abridge[i1].y1;
                ai[l + 2] = abridge[i1].x2;
                ai[l + 3] = abridge[i1].y2;
                ai[l + 4] = abridge[i1].type;
                l += 5;
            }

            com.maddox.il2.builder.PathFind.SetMap(abyte0, k, i, j, 200F, 20F, ai, abridge.length);
        }
    }

    public static boolean setMoverType(int i)
    {
        com.maddox.il2.builder.PathFind.setMap(i);
        return com.maddox.il2.builder.PathFind.SetMoverType(i);
    }

    public static boolean setStartPoint(int i, com.maddox.il2.builder.PNodes pnodes)
    {
        com.maddox.JGP.Point3d point3d = pnodes.pos.getAbsPoint();
        if(pnodes == startPoint[i] && point3d.x == startPosX[i] && point3d.y == startPosY[i])
            return true;
        if(com.maddox.il2.builder.PathFind.SetStartPoint(i, (float)point3d.x, (float)point3d.y))
        {
            startPosX[i] = point3d.x;
            startPosY[i] = point3d.y;
            startPoint[i] = pnodes;
            return true;
        } else
        {
            startPoint[i] = null;
            return false;
        }
    }

    public static boolean isPointReacheable(int i, com.maddox.il2.builder.PNodes pnodes)
    {
        com.maddox.JGP.Point3d point3d = pnodes.pos.getAbsPoint();
        return com.maddox.il2.builder.PathFind.IsPointReacheable(i, (float)point3d.x, (float)point3d.y);
    }

    public static float[] buildPath(int i, com.maddox.il2.builder.PNodes pnodes)
    {
        com.maddox.JGP.Point3d point3d = pnodes.pos.getAbsPoint();
        float af[] = com.maddox.il2.builder.PathFind.BuildPath(i, (float)point3d.x, (float)point3d.y);
        if(af == null)
            return null;
        for(int j = 0; j < af.length / 4; j++)
            af[j * 4 + 2] = com.maddox.il2.engine.Engine.land().HQ(af[j * 4 + 0], af[j * 4 + 1]);

        return af;
    }

    private static native void SetMap(byte abyte0[], int i, int j, int k, float f, float f1, int ai[], int l);

    private static native void UnloadMap();

    private static native boolean SetMoverType(int i);

    private static native boolean SetStartPoint(int i, float f, float f1);

    private static native boolean IsPointReacheable(int i, float f, float f1);

    private static native float[] BuildPath(int i, float f, float f1);

    public static native int codeAtPos(float f, float f1);

    public static native int getV();

    public static native int getI();

    private PathFind()
    {
    }

    public static final void loadNative()
    {
        if(!libLoaded)
        {
            java.lang.System.loadLibrary("pathfind");
            libLoaded = true;
        }
    }

    public static final float ROAD_WIDTH = 20F;
    public static final int SIZE = 4;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int B = 3;
    public static final int MOVER_VEHICLE = 0;
    public static final int MOVER_TROOPER = 1;
    public static final int MOVER_SHIP = 2;
    public static final int MOVER_TRAIN = 3;
    public static final int MOVER_BUILDING = 4;
    private static com.maddox.il2.builder.PNodes startPoint[] = new com.maddox.il2.builder.PNodes[2];
    private static double startPosX[] = new double[2];
    private static double startPosY[] = new double[2];
    protected static com.maddox.il2.tools.Bridge b[];
    protected static com.maddox.TexImage.TexImage tShip;
    protected static com.maddox.TexImage.TexImage tNoShip;
    protected static int curType = -1;
    private static boolean libLoaded = false;

    static 
    {
        com.maddox.il2.builder.PathFind.loadNative();
    }
}

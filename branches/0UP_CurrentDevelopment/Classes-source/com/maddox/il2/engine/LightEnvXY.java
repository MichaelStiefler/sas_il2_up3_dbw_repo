// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightEnvXY.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16Hash;
import java.util.Map;

// Referenced classes of package com.maddox.il2.engine:
//            LightEnv, LightPoint, Sun

public class LightEnvXY extends com.maddox.il2.engine.LightEnv
{

    public int prepareForRender(com.maddox.JGP.Point3d point3d, float f)
    {
        if(f <= 64F)
        {
            int i = (int)((com.maddox.JGP.Tuple3d) (point3d)).x / 512;
            int k = (int)((com.maddox.JGP.Tuple3d) (point3d)).y / 512;
            com.maddox.util.HashMapExt hashmapext = mapXY.get(k, i);
            if(hashmapext == null)
                return 0;
            com.maddox.il2.engine.LightPoint.curStamp++;
            countLights = 0;
            pointsStamped(hashmapext);
        } else
        {
            int j = (int)(((com.maddox.JGP.Tuple3d) (point3d)).x - (double)f) / 512;
            int l = (int)(((com.maddox.JGP.Tuple3d) (point3d)).x + (double)f) / 512;
            int i1 = (int)(((com.maddox.JGP.Tuple3d) (point3d)).y - (double)f) / 512;
            int j1 = (int)(((com.maddox.JGP.Tuple3d) (point3d)).y + (double)f) / 512;
            com.maddox.il2.engine.LightPoint.curStamp++;
            countLights = 0;
            for(int k1 = i1; k1 <= j1; k1++)
            {
                for(int l1 = j; l1 <= l; l1++)
                {
                    com.maddox.util.HashMapExt hashmapext1 = mapXY.get(k1, l1);
                    if(hashmapext1 != null)
                        pointsStamped(hashmapext1);
                }

            }

        }
        return countLights;
    }

    private void pointsStamped(com.maddox.util.HashMapExt hashmapext)
    {
        for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
        {
            com.maddox.il2.engine.LightPoint lightpoint = (com.maddox.il2.engine.LightPoint)entry.getKey();
            if(lightpoint.stamp != com.maddox.il2.engine.LightPoint.curStamp)
            {
                lightpoint.stamp = com.maddox.il2.engine.LightPoint.curStamp;
                lightpoint.addToRender();
                countLights++;
            }
        }

    }

    protected void changedPos(com.maddox.il2.engine.LightPoint lightpoint, double d, double d1, double d2)
    {
        if(lightpoint.I <= 0.0F || lightpoint.R <= 0.0F)
        {
            return;
        } else
        {
            remove(lightpoint);
            add(lightpoint, (int)d, (int)d1, lightpoint.I, lightpoint.R);
            return;
        }
    }

    protected void changedEmit(com.maddox.il2.engine.LightPoint lightpoint, float f, float f1)
    {
        remove(lightpoint);
        if(f <= 0.0F || f1 <= 0.0F)
        {
            return;
        } else
        {
            add(lightpoint, lightpoint.IX, lightpoint.IY, f, f1);
            return;
        }
    }

    protected void add(com.maddox.il2.engine.LightPoint lightpoint)
    {
        if(lightpoint.I <= 0.0F || lightpoint.R <= 0.0F)
        {
            return;
        } else
        {
            add(lightpoint, lightpoint.IX, lightpoint.IY, lightpoint.I, lightpoint.R);
            return;
        }
    }

    protected void remove(com.maddox.il2.engine.LightPoint lightpoint)
    {
        if(lightpoint.I > 0.0F && lightpoint.R > 0.0F)
        {
            int i = (int)lightpoint.R;
            int j = lightpoint.IX;
            int k = lightpoint.IY;
            int l = (j - i) / 512;
            int i1 = (j + i) / 512;
            int j1 = (k - i) / 512;
            int k1 = (k + i) / 512;
            for(int l1 = j1; l1 <= k1; l1++)
            {
                for(int i2 = l; i2 <= i1; i2++)
                    mapXY.remove(l1, i2, lightpoint);

            }

        }
    }

    private void add(com.maddox.il2.engine.LightPoint lightpoint, int i, int j, float f, float f1)
    {
        int k = (int)f1;
        int l = (i - k) / 512;
        int i1 = (i + k) / 512;
        int j1 = (j - k) / 512;
        int k1 = (j + k) / 512;
        i /= 512;
        j /= 512;
        for(int l1 = j1; l1 <= k1; l1++)
        {
            for(int i2 = l; i2 <= i1; i2++)
                mapXY.put(l1, i2, lightpoint, i2 == i && l1 == j ? flgCenter : null);

        }

    }

    public void clear()
    {
        mapXY.clear();
    }

    public LightEnvXY()
    {
        mapXY = new HashMapXY16Hash(7);
    }

    public LightEnvXY(com.maddox.il2.engine.Sun sun)
    {
        super(sun);
        mapXY = new HashMapXY16Hash(7);
    }

    public static final int STEP = 512;
    public static final int SMALL_SIZE = 32;
    private int countLights;
    private com.maddox.util.HashMapXY16Hash mapXY;
    private static java.lang.Object flgCenter = new Object();

}

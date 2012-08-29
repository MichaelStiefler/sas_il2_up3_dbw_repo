// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RandomVector.java

package com.maddox.il2.ai;

import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.rts.Time;
import java.util.Random;

public class RandomVector
{

    public RandomVector()
    {
    }

    public static void getTimed(com.maddox.JGP.Vector3f vector3f, int i)
    {
        long l = com.maddox.rts.Time.current();
        long l1 = l / 500L;
        long l2 = l - l1 * 500L;
        float f = (float)l2 * 0.002F;
        i = (int)((long)i + l1) & 0xff;
        int j = i + 1 & 0xff;
        vector3f.interpolate(vectors[i], vectors[j], f);
    }

    public static void getTimed(com.maddox.JGP.Vector3d vector3d, int i)
    {
        long l = com.maddox.rts.Time.current();
        long l1 = l / 1000L;
        long l2 = l - l1 * 1000L;
        float f = (float)l2 * 0.001F;
        i = (int)((long)i + l1) & 0xff;
        int j = i + 1 & 0xff;
        tmp.interpolate(vectors[i], vectors[j], f);
        vector3d.set(tmp.x, tmp.y, tmp.z);
    }

    public static void getTimed(long l, com.maddox.JGP.Vector3d vector3d, int i)
    {
        long l1 = l / 1000L;
        long l2 = l - l1 * 1000L;
        float f = (float)l2 * 0.001F;
        i = (int)((long)i + l1) & 0xff;
        int j = i + 1 & 0xff;
        tmp.interpolate(vectors[i], vectors[j], f);
        vector3d.set(tmp.x, tmp.y, tmp.z);
    }

    public static void getTimedStepped(int i, long l, com.maddox.JGP.Vector3d vector3d, int j)
    {
        long l1 = l / (long)(i * 1000);
        long l2 = l - l1 * 1000L * (long)i;
        float f = (float)l2 * (1.0F / ((float)i * 1000F));
        j = (int)((long)j + l1) & 256 / i - 1;
        int k = j + i & 0xff;
        tmp.interpolate(vectors[j], vectors[k], f);
        vector3d.set(tmp.x, tmp.y, tmp.z);
    }

    private static final int MASK_ELEMS = 255;
    private static final int N_ELEMS = 256;
    private static com.maddox.JGP.Vector3f vectors[];
    private static com.maddox.JGP.Vector3f tmp = new Vector3f();

    static 
    {
        java.util.Random random = new Random(12345L);
        vectors = new com.maddox.JGP.Vector3f[256];
        vectors[0] = new Vector3f(0.0F, 0.0F, 0.0F);
        for(int i = 1; i < 256; i++)
        {
            vectors[i] = new Vector3f(random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F);
            vectors[i].scale(0.2F);
            vectors[i].add(vectors[i - 1]);
            if(vectors[i].length() >= 1.0F)
                vectors[i].normalize();
        }

        vectors[254].interpolate(vectors[253], vectors[0], 0.33F);
        vectors[255].interpolate(vectors[253], vectors[0], 0.67F);
    }
}

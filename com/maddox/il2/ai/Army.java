// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Army.java

package com.maddox.il2.ai;


public final class Army
{

    public Army()
    {
    }

    public static int amountSingle()
    {
        return 3;
    }

    public static int amountNet()
    {
        return 17;
    }

    public static int color(int i)
    {
        return color[i];
    }

    public static java.lang.String name(int i)
    {
        return name[i];
    }

    private static int color[] = {
        -1, 0xff0000c0, 0xffc00000, 0xff00c000, 0xff00c0c0, 0xffc000c0, 0xffc0c000, 0xff000080, 0xff800000, 0xff004000, 
        0xff0c8059, 0xff800080, 0xff808000, 0xff1a65e7, 0xff9ea400, 0xff36668c, 0xff408040
    };
    private static java.lang.String name[] = {
        "None", "Red", "Blue", "Green", "Gold", "Purple", "Aqua", "Maroon", "Navy", "Emerald", 
        "Olive", "Magenta", "Teal", "Orange", "Turquoise", "Brown", "Salad"
    };

}

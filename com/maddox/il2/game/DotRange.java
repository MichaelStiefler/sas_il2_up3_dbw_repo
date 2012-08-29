// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DotRange.java

package com.maddox.il2.game;

import com.maddox.il2.ai.Army;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import java.io.IOException;

public class DotRange
{

    public void setDefault()
    {
        set(14000D, 6000D, 6000D, 6000D, 6000D, 6000D);
    }

    public double dot()
    {
        return dot;
    }

    public double dot(double d)
    {
        return dot * d;
    }

    public double icon()
    {
        return icon;
    }

    public double color()
    {
        return color;
    }

    public double type()
    {
        return type;
    }

    public double name()
    {
        return name;
    }

    public double id()
    {
        return id;
    }

    public double range()
    {
        return range;
    }

    public int colorDot(double d, int i, int j)
    {
        int k = 0;
        if(d < color)
            k = com.maddox.il2.ai.Army.color(i) & 0xffffff;
        int l = ((int)(alphaDot(d) * (double)j) & 0xff) << 24;
        return k | l;
    }

    public int colorIcon(double d, int i, int j)
    {
        int k = 0;
        if(d < color)
            k = com.maddox.il2.ai.Army.color(i) & 0xffffff;
        int l = ((int)(alphaIcon(d) * (double)j) & 0xff) << 24;
        return k | l;
    }

    public int alphaColorDot(double d)
    {
        return (int)(alphaDot(d) * 255D) << 24;
    }

    public int alphaColorIcon(double d)
    {
        return (int)(alphaIcon(d) * 255D) << 24;
    }

    public double alphaDot(double d)
    {
        double d1 = dot / 2D;
        if(d <= d1)
            return 1.0D;
        if(d >= dot)
            return 0.0D;
        else
            return 1.0D - (d - d1) / d1;
    }

    public double alphaDot(double d, double d1)
    {
        double d2 = dot * d1;
        if(d >= d2)
            return 0.0D;
        else
            return 1.0D - d / d2;
    }

    public double alphaIcon(double d)
    {
        double d1 = icon / 2D;
        if(d <= d1)
            return 1.0D;
        if(d >= icon)
            return 0.0D;
        else
            return 1.0D - (d - d1) / d1;
    }

    private void validate()
    {
        if(dot < 5D)
            dot = 5D;
        if(dot > 25000D)
            dot = 25000D;
        if(color < 5D)
            color = 5D;
        if(color > dot)
            color = dot;
        if(type < 5D)
            type = 5D;
        if(type > dot)
            type = dot;
        if(name < 5D)
            name = 5D;
        if(name > dot)
            name = dot;
        if(id < 5D)
            id = 5D;
        if(id > dot)
            id = dot;
        if(range < 5D)
            range = 5D;
        if(range > dot)
            range = dot;
        icon = color;
        if(icon < type)
            icon = type;
        if(icon < name)
            icon = name;
        if(icon < id)
            icon = id;
        if(icon < range)
            icon = range;
    }

    public void set(double d, double d1, double d2, double d3, double d4, double d5)
    {
        if(d > 0.0D)
            dot = d;
        if(d1 > 0.0D)
            color = d1;
        if(d2 > 0.0D)
            type = d2;
        if(d3 > 0.0D)
            name = d3;
        if(d4 > 0.0D)
            id = d4;
        if(d5 > 0.0D)
            range = d5;
        validate();
    }

    public void netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        set(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
    }

    public void netOutput(com.maddox.rts.NetMsgOutput netmsgoutput)
        throws java.io.IOException
    {
        netmsgoutput.writeFloat((float)dot);
        netmsgoutput.writeFloat((float)color);
        netmsgoutput.writeFloat((float)type);
        netmsgoutput.writeFloat((float)name);
        netmsgoutput.writeFloat((float)id);
        netmsgoutput.writeFloat((float)range);
    }

    public DotRange()
    {
        dot = 10000D;
        icon = 3000D;
        color = 3000D;
        type = 3000D;
        name = 3000D;
        id = 3000D;
        range = 3000D;
        setDefault();
    }

    public static final double MAX_DOT = 25000D;
    public static final double MIN_DOT = 5D;
    protected double dot;
    protected double icon;
    protected double color;
    protected double type;
    protected double name;
    protected double id;
    protected double range;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitInPackedFormGeneric.java

package com.maddox.il2.ai.ground;


public class UnitInPackedFormGeneric
{

    public int CodeName()
    {
        return codeName;
    }

    public int CodeType()
    {
        return codeType;
    }

    public int State()
    {
        return state;
    }

    public UnitInPackedFormGeneric(int i, int j, int k)
    {
        codeName = i;
        codeType = j;
        state = k;
    }

    private int codeName;
    private int codeType;
    private int state;
}

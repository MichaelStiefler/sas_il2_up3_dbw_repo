// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitInPackedForm.java

package com.maddox.il2.ai.ground;


// Referenced classes of package com.maddox.il2.ai.ground:
//            UnitInPackedFormGeneric

public class UnitInPackedForm extends com.maddox.il2.ai.ground.UnitInPackedFormGeneric
{

    public UnitInPackedForm(int i, int j, int k, float f, float f1, int l, int i1)
    {
        super(i, j, k);
        SPEED_AVERAGE = f;
        BEST_SPACE = f1;
        WEAPONS_MASK = l;
        HITBY_MASK = i1;
    }

    public UnitInPackedForm(int i, int j, int k)
    {
        super(i, j, k);
        SPEED_AVERAGE = 0.0F;
        BEST_SPACE = 0.0F;
        WEAPONS_MASK = 0;
        HITBY_MASK = 0;
    }

    public float SPEED_AVERAGE;
    public float BEST_SPACE;
    public int WEAPONS_MASK;
    public int HITBY_MASK;
}

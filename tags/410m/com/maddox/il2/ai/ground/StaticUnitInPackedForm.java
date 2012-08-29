// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StaticUnitInPackedForm.java

package com.maddox.il2.ai.ground;


// Referenced classes of package com.maddox.il2.ai.ground:
//            UnitInPackedFormGeneric

public class StaticUnitInPackedForm extends com.maddox.il2.ai.ground.UnitInPackedFormGeneric
{

    public float X()
    {
        return x;
    }

    public float Y()
    {
        return y;
    }

    public float Yaw()
    {
        return yaw;
    }

    public StaticUnitInPackedForm(int i, int j, int k, float f, float f1, float f2)
    {
        super(i, j, k);
        x = f;
        y = f1;
        yaw = f2;
    }

    private float x;
    private float y;
    private float yaw;
}

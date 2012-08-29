// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Scheme5.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            AircraftLH

public abstract class Scheme5 extends com.maddox.il2.objects.air.AircraftLH
{

    public Scheme5()
    {
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = -70F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -40F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -40F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -40F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -40F * f, 0.0F);
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SPITFIRE9.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE

public abstract class SPITFIRE9 extends com.maddox.il2.objects.air.SPITFIRE
{

    public SPITFIRE9()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        super.update(f);
        hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
    }

    private float kangle;
}

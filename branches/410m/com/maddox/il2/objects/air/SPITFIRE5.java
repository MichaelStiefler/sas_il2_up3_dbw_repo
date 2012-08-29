// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SPITFIRE5.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE

public class SPITFIRE5 extends com.maddox.il2.objects.air.SPITFIRE
{

    public SPITFIRE5()
    {
    }

    public void update(float f)
    {
        super.update(f);
        if(FM.isPlayers())
        {
            com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)FM;
            if(realflightmodel.RealMode)
                FM.producedAM.z -= 25F * FM.EI.engines[0].getControlRadiator() * realflightmodel.indSpeed;
        }
    }
}

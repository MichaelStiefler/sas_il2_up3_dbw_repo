// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RotatingYGBeacon.java

package com.maddox.il2.objects.vehicles.radios;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.vehicles.radios:
//            BeaconGeneric

public class RotatingYGBeacon extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(isAlive())
            {
                float f = com.maddox.il2.objects.vehicles.radios.BeaconGeneric.cvt((float)com.maddox.rts.Time.current() % 30000F, 0.0F, 30000F, 0.0F, 360F);
                hierMesh().chunkSetAngles("Head_D0", -f - pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
            }
            return true;
        }

        Move()
        {
        }
    }


    public RotatingYGBeacon()
    {
        startRotate();
    }

    public void startRotate()
    {
        interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LorenzBlindLandingBeacon.java

package com.maddox.il2.objects.vehicles.radios;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;

// Referenced classes of package com.maddox.il2.objects.vehicles.radios:
//            BeaconGeneric, BlindLandingData

public class LorenzBlindLandingBeacon extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
{

    public LorenzBlindLandingBeacon()
    {
        p1 = new Point3d();
        p2 = new Point3d();
        tempPoint1 = new Point3d();
        tempPoint2 = new Point3d();
        emittingPoint = new Point3d();
        outerMarkerDist = 3000F;
        innerMarkerDist = 300F;
        com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon _tmp = this;
        innerMarkerDist = constr_arg1.innerMarkerDist;
        com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon _tmp1 = this;
        outerMarkerDist = constr_arg1.outerMarkerDist;
    }

    public void rideBeam(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.vehicles.radios.BlindLandingData blindlandingdata)
    {
        aircraft.pos.getAbs(p2);
        pos.getAbs(p1);
        p1.sub(p2);
        double d = java.lang.Math.sqrt(p1.x * p1.x + p1.y * p1.y);
        double d1 = (double)aircraft.FM.getAltitude() - pos.getAbsPoint().z;
        float f = getConeOfSilence(d, d1);
        if(d > 35000D)
        {
            blindlandingdata.addSignal(0.0F, 0.0F, 35000F, false, 0.0F, 0.0F, 0.0F);
            return;
        }
        float f1 = f;
        if(com.maddox.il2.engine.Landscape.rayHitHQ(emittingPoint, aircraft.FM.Loc, tempPoint1))
        {
            com.maddox.il2.engine.Landscape.rayHitHQ(aircraft.FM.Loc, emittingPoint, tempPoint2);
            tempPoint1.sub(tempPoint2);
            double d2 = java.lang.Math.sqrt(tempPoint1.x * tempPoint1.x + tempPoint1.y * tempPoint1.y);
            f1 = com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon.cvt((float)d2, 0.0F, 5000F, 1.0F, 0.1F);
        }
        f1 *= com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon.cvt((float)d, 0.0F, 35000F, 1.0F, 0.0F);
        float f2 = 57.32484F * (float)java.lang.Math.atan2(p1.x, p1.y);
        f2 = pos.getAbsOrient().getYaw() + f2;
        for(f2 = (f2 + 180F) % 360F; f2 < 0.0F; f2 += 360F);
        for(; f2 >= 360F; f2 -= 360F);
        float f3 = f2 - 90F;
        boolean flag = true;
        if(f3 > 90F)
        {
            f3 = -(f3 - 180F);
            flag = false;
        }
        float f4 = 0.0F;
        if(!flag)
            f4 = f3 * -18F;
        else
            f4 = f3 * 18F;
        blindlandingdata.addSignal(f3, f4 * f, (float)d, flag, f1, outerMarkerDist, innerMarkerDist);
    }

    private float getConeOfSilence(double d, double d1)
    {
        float f = 57.32484F * (float)java.lang.Math.atan2(d, d1);
        return com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon.cvt(f, 20F, 40F, 0.0F, 1.0F);
    }

    public void missionStarting()
    {
        super.missionStarting();
        hierMesh().chunkVisible("GuideArrows", false);
        emittingPoint.x = pos.getAbsPoint().x;
        emittingPoint.y = pos.getAbsPoint().y;
        emittingPoint.z = pos.getAbsPoint().z + 20D;
    }

    public void align()
    {
        super.align();
        emittingPoint.x = pos.getAbsPoint().x;
        emittingPoint.y = pos.getAbsPoint().y;
        emittingPoint.z = pos.getAbsPoint().z + 20D;
    }

    com.maddox.JGP.Point3d p1;
    com.maddox.JGP.Point3d p2;
    private com.maddox.JGP.Point3d tempPoint1;
    private com.maddox.JGP.Point3d tempPoint2;
    private com.maddox.JGP.Point3d emittingPoint;
    public float outerMarkerDist;
    public float innerMarkerDist;
}

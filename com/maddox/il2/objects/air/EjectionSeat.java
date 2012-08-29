// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EjectionSeat.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Message;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft, Paratrooper

public class EjectionSeat extends com.maddox.il2.engine.ActorHMesh
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            float f = com.maddox.rts.Time.tickLenFs();
            v.z -= 9.8100000000000005D * (double)f * (double)f;
            v.x *= 0.99000000953674316D;
            v.y *= 0.99000000953674316D;
            l.add(v);
            pos.setAbs(l);
            com.maddox.il2.ai.World.cur();
            double d = com.maddox.il2.ai.World.land().HQ_Air(l.getPoint().x, l.getPoint().y);
            if(l.getPoint().z < d)
                com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current(), actor);
            if(bPilotAttached && (l.getPoint().z < d || com.maddox.rts.Time.current() > timeStart + 3000L))
                if(!ownerAircraft.isNet() || ownerAircraft.isNetMaster())
                {
                    com.maddox.JGP.Vector3d vector3d = new Vector3d(v);
                    vector3d.scale(1.0F / com.maddox.rts.Time.tickLenFs());
                    if(com.maddox.il2.engine.Actor.isValid(ownerAircraft))
                    {
                        com.maddox.il2.objects.air.Paratrooper paratrooper = new Paratrooper(ownerAircraft, ownerAircraft.getArmy(), 0, l, vector3d);
                        doRemovePilot();
                        bPilotAttached = false;
                        ownerAircraft.FM.AS.astateBailoutStep = 12;
                        com.maddox.il2.ai.EventLog.onBailedOut(ownerAircraft, 0);
                        ownerAircraft.FM.AS.setPilotState(ownerAircraft, 0, 100, false);
                    }
                } else
                {
                    doRemovePilot();
                    bPilotAttached = false;
                }
            return true;
        }

        Interpolater()
        {
        }
    }


    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private void doRemovePilot()
    {
        hierMesh().chunkVisible("Pilot1_D0", false);
        hierMesh().chunkVisible("Head1_D0", false);
        hierMesh().chunkVisible("HMask1_D0", false);
    }

    public EjectionSeat(int i, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        v = new Vector3d();
        l = new Loc();
        bPilotAttached = true;
        switch(i)
        {
        case 1: // '\001'
        default:
            setMesh("3DO/Plane/He-162-ESeat/hier.him");
            drawing(true);
            break;

        case 2: // '\002'
            setMesh("3DO/Plane/Do-335A-0-ESeat/hier.him");
            drawing(true);
            break;

        case 3: // '\003'
            setMesh("3DO/Plane/Ar-234-ESeat/hier.him");
            drawing(true);
            break;
        }
        l.set(loc);
        v.set(vector3d);
        v.scale(com.maddox.rts.Time.tickConstLenFs());
        pos.setAbs(l);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
        ownerAircraft = aircraft;
        timeStart = com.maddox.rts.Time.current();
    }

    private com.maddox.JGP.Vector3d v;
    private com.maddox.il2.engine.Loc l;
    private boolean bPilotAttached;
    private com.maddox.il2.objects.air.Aircraft ownerAircraft;
    private long timeStart;







}

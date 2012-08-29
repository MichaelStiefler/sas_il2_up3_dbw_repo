// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_262HGII.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeFMPar06, NetAircraft, Aircraft, 
//            EjectionSeat

public class ME_262HGII extends com.maddox.il2.objects.air.ME_262
{

    public ME_262HGII()
    {
    }

    protected void moveRudder(float f)
    {
        updateControlsVisuals();
        if(FM.CT.getGear() > 0.75F)
            hierMesh().chunkSetAngles("GearC21_D0", 0.0F, 40F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        updateControlsVisuals();
    }

    private final void updateControlsVisuals()
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -21.25F * FM.CT.getElevator() - 21.25F * FM.CT.getRudder(), 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -21.25F * FM.CT.getElevator() + 21.25F * FM.CT.getRudder(), 0.0F);
    }

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)obj;
                if(!com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    return;
                } else
                {
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_ExternalSeat01");
                    aircraft.pos.getAbs(loc1);
                    hooknamed.computePos(aircraft, loc1, loc);
                    loc.transform(vector3d);
                    vector3d.x += aircraft.FM.Vwld.x;
                    vector3d.y += aircraft.FM.Vwld.y;
                    vector3d.z += aircraft.FM.Vwld.z;
                    new EjectionSeat(2, loc, vector3d, aircraft);
                    return;
                }
            }

        }
;
        hierMesh().chunkVisible("Seat_D0", false);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-262HG-II/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1958.2F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-262HG-II.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitME_262HGII.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74615F);
        com.maddox.il2.objects.air.ME_262HGII.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.ME_262HGII.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", 
            "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24"
        });
        com.maddox.il2.objects.air.ME_262HGII.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.ME_262HGII.weaponsRegister(class1, "24r4m", new java.lang.String[] {
            "MGunMK108k 100", "MGunMK108k 100", "MGunMK108k 80", "MGunMK108k 80", "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1"
        });
        com.maddox.il2.objects.air.ME_262HGII.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class N1KS extends N1K
    implements TypeSailPlane
{

    public N1KS()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    public void moveSteering(float f)
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        super.hitBone(s, shot, point3d);
        if(s.startsWith("xgearc"))
            hitChunk("GearC2", shot);
        if(s.startsWith("xgearl"))
            hitChunk("GearL2", shot);
        if(s.startsWith("xgearr"))
            hitChunk("GearR2", shot);
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(FM.Gears.clpGearEff[i][j] != null)
                {
                    tmpp.set(FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
                    tmpp.z = 0.01D;
                    FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
                    FM.Gears.clpGearEff[i][j].pos.reset();
                }

        }

    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.1F, 0.99F, 0.0F, 0.61F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("GearC11_D0", 0.0F, -30F * f, 0.0F);
    }

    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = N1KS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "N1K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/N1K/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/N1Ks.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitN1K2JA.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15s 525", "MGunMG15s 525", "MGunHo5k 60", "MGunHo5k 60"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

public class SeaHurricaneMkIb extends Hurricane
{

    public SeaHurricaneMkIb()
    {
        arrestor = 0.0F;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((!isNet() || !isNetMirror()) && !s.startsWith("Hook"))
            super.msgCollision(actor, s, s1);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57F * f, 0.0F);
        resetYPRmodifier();
        Aircraft.xyz[2] = 0.1385F * f;
        arrestor = f;
    }

    public void update(float f)
    {
        super.update(f);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() > 0.2F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle != 0.0F)
            {
                float f1 = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle, -50F, 7F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-33F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVSink) / 57F;
                if(f2 < 0.0F && super.FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    private float arrestor;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = SeaHurricaneMkIb.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SeaHurricaneMkIb(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SeaHurricaneMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSEAHURRICANE1.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[8]);
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 308", "MGunBrowning303k 334"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}

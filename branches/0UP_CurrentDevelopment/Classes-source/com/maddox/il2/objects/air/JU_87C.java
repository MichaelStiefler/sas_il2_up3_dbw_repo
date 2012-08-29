// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_87C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_87, PaintSchemeBMPar02, Aircraft, Cockpit, 
//            NetAircraft

public class JU_87C extends com.maddox.il2.objects.air.JU_87
{

    public JU_87C()
    {
        bDynamoOperational = true;
        dynamoOrient = 0.0F;
        arrestor2 = 0.0F;
        bDynamoRotary = false;
        bGearJettisoned = false;
        bGearInitialized = false;
        bOldStatusAI = false;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(i == 36 || i == 37 || i == 10)
        {
            hierMesh().chunkVisible("GearR3_D0", false);
            hierMesh().chunkVisible("GearR3Rot_D0", false);
            bDynamoOperational = false;
        }
        return super.cutFM(i, j, actor);
    }

    private boolean isAI()
    {
        return (this != com.maddox.il2.ai.World.getPlayerAircraft() || !((com.maddox.il2.fm.RealFlightModel)super.FM).isRealMode()) && (super.FM instanceof com.maddox.il2.ai.air.Pilot);
    }

    private void updateGearStatus()
    {
        if(!bGearInitialized)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 1.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(1.0F);
            bGearInitialized = true;
        }
        if(isAI())
        {
            if(bGearJettisoned)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(0.0F);
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 1.0F;
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(1.0F);
            }
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasGearControl = false;
            bOldStatusAI = true;
        } else
        {
            if(!bGearJettisoned)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasGearControl = true;
                if(bOldStatusAI)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 1.0F;
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(1.0F);
                }
            }
            bOldStatusAI = false;
        }
    }

    protected void moveGear(float f)
    {
        if(isAI())
            return;
        if(bGearJettisoned)
        {
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(0.0F);
            return;
        }
        if(!bGearJettisoned && f < 0.95F)
        {
            bGearJettisoned = true;
            cutFM(9, 0, this);
            cutFM(10, 0, this);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.GearControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.setGear(0.0F);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.setOperable(false);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasGearControl = false;
            super.FM.setGCenter(-0.5F);
            super.FM.GearCX = 0.0F;
            if(this == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log("Gear Jettisoned");
            return;
        } else
        {
            return;
        }
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLFold", 100F * f, 22F * f, -90F * f);
        hiermesh.chunkSetAngles("WingRFold", -100F * f, -22F * f, -90F * f);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 1.0F, 0.0F, -0.6F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -45F * f, 0.0F);
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((!isNet() || !isNetMirror()) && !s.startsWith("Hook"))
            super.msgCollision(actor, s, s1);
    }

    protected void moveFan(float f)
    {
        if(bDynamoOperational)
        {
            pk = java.lang.Math.abs((int)(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld.length() / 14D));
            if(pk >= 1)
                pk = 1;
        }
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            hierMesh().chunkVisible("GearR3_D0", !bDynamoRotary);
            hierMesh().chunkVisible("GearR3Rot_D0", bDynamoRotary);
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("GearR3_D0", 0.0F, dynamoOrient, 0.0F);
        super.moveFan(f);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80F * f, 0.0F);
    }

    public void update(float f)
    {
        updateGearStatus();
        for(int i = 1; i < 9; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -15F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() > 0.9F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle != 0.0F)
            {
                arrestor2 = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle, -65F, 3F, 45F, -23F);
                hierMesh().chunkSetAngles("Hook_D0", 0.0F, arrestor2, 0.0F);
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.getClass();
            } else
            {
                float f1 = -41F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVSink;
                if(f1 < 0.0F && super.FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f1 > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() < 0.9F)
                    f1 = 0.0F;
                if(f1 > 6.2F)
                    f1 = 6.2F;
                arrestor2 += f1;
                if(arrestor2 < -23F)
                    arrestor2 = -23F;
                else
                if(arrestor2 > 45F)
                    arrestor2 = 45F;
                hierMesh().chunkSetAngles("Hook_D0", 0.0F, arrestor2, 0.0F);
            }
    }

    private boolean bDynamoOperational;
    private float dynamoOrient;
    private float arrestor2;
    private boolean bDynamoRotary;
    private int pk;
    private boolean bGearJettisoned;
    private boolean bGearInitialized;
    private boolean bOldStatusAI;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87C.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87C/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        try
        {
            com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
                java.lang.Class.forName("com.maddox.il2.objects.air.CockpitJU_87B2"), java.lang.Class.forName("com.maddox.il2.objects.air.CockpitJU_87B2_Gunner")
            });
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            com.maddox.il2.ai.EventLog.type("Exception in F-86J Cockpit init, " + classnotfoundexception.getMessage());
        }
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1939.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSD500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSD500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunAB250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}

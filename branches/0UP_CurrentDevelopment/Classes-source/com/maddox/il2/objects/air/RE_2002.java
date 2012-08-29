// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RE_2002.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            RE_2002xyz, PaintSchemeBMPar09, PaintSchemeFMPar02, TypeDiveBomber, 
//            Aircraft, Cockpit, NetAircraft

public class RE_2002 extends com.maddox.il2.objects.air.RE_2002xyz
    implements com.maddox.il2.objects.air.TypeDiveBomber
{

    public RE_2002()
    {
        canopyF = 0.0F;
        tiltCanopyOpened = false;
        slideCanopyOpened = false;
        blisterRemoved = false;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(tiltCanopyOpened && !blisterRemoved && FM.getSpeed() > 75F)
            doRemoveBlister1();
    }

    private final void doRemoveBlister1()
    {
        blisterRemoved = true;
        if(hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            hierMesh().hideSubTrees("Blister1_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
        }
    }

    public void moveCockpitDoor(float f)
    {
        if(f > canopyF)
        {
            if(FM.Gears.onGround() && FM.getSpeed() < 5F || tiltCanopyOpened)
            {
                tiltCanopyOpened = true;
                hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            } else
            {
                slideCanopyOpened = true;
                resetYPRmodifier();
                com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.01F;
                com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.3F);
                hierMesh().chunkSetLocate("Blister4L_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
                com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.01F;
                hierMesh().chunkSetLocate("Blister4R_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            }
        } else
        if(FM.Gears.onGround() && FM.getSpeed() < 5F && !slideCanopyOpened || tiltCanopyOpened)
        {
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            if(FM.getSpeed() > 50F && f < 0.6F && !blisterRemoved)
                doRemoveBlister1();
            if(f == 0.0F)
                tiltCanopyOpened = false;
        } else
        {
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.3F);
            hierMesh().chunkSetLocate("Blister4L_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            hierMesh().chunkSetLocate("Blister4R_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            if(f == 0.0F)
                slideCanopyOpened = false;
        }
        canopyF = f;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    protected void mydebug(java.lang.String s)
    {
        java.lang.System.out.println(s);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public float canopyF;
    private boolean tiltCanopyOpened;
    private boolean slideCanopyOpened;
    private boolean blisterRemoved;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "RE.2002");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/RE-2002(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/RE-2002(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitRE_2000.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/RE-2000.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9119F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 3, 3, 3, 3, 3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalTorp01", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100kg", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100kg+1x240lTank", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null, null, "FuelTankGun_Tank240 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x250kg+2x100kg", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", "BombGunIT_100_M 1", "BombGunIT_100_M 1", "BombGunIT_250_T 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x250kg", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, "BombGunIT_250_T 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500kg", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, "BombGunIT_500_T 1", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x630kg", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, "BombGunIT_630 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xTorpedo", new java.lang.String[] {
            "MGunBredaSAFAT127re 390", "MGunBredaSAFAT127re 450", "MGunBredaSAFAT77ki 640", "MGunBredaSAFAT77ki 640", null, null, null, null, "BombGunTorp650 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}

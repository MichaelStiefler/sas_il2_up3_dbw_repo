// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Squares.java

package com.maddox.il2.fm;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunNull;
import com.maddox.il2.objects.weapons.BombGunSC50;
import com.maddox.il2.objects.weapons.BombGunSC70;
import com.maddox.il2.objects.weapons.FuelTankGun;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.PylonHS129BK37;
import com.maddox.il2.objects.weapons.PylonHS129BK75;
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FR;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WR;
import com.maddox.il2.objects.weapons.PylonP38RAIL5;
import com.maddox.il2.objects.weapons.PylonP38RAILS;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21Dual;
import com.maddox.il2.objects.weapons.RocketBombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGunR4M;
import com.maddox.rts.SectFile;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.fm:
//            Controls

public class Squares
{

    public Squares()
    {
        dragParasiteCx = 0.0F;
        dragAirbrakeCx = 0.0F;
        dragFuselageCx = 0.0F;
        dragProducedCx = 0.0F;
        toughness = new float[44];
        eAbsorber = new float[44];
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s1 = "Zero Square processed from " + ((java.lang.Object) (sectfile)).toString();
        java.lang.String s = "Squares";
        float f = sectfile.get(s, "Wing", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        squareWing = f;
        f = sectfile.get(s, "Aileron", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        squareAilerons = f;
        f = sectfile.get(s, "Flap", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        squareFlaps = f;
        f = sectfile.get(s, "Stabilizer", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        liftStab = f;
        f = sectfile.get(s, "Elevator", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        squareElevators = f;
        f = sectfile.get(s, "Keel", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        liftKeel = f;
        f = sectfile.get(s, "Rudder", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        squareRudders = f;
        f = sectfile.get(s, "Wing_In", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        liftWingLIn = liftWingRIn = f;
        f = sectfile.get(s, "Wing_Mid", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        liftWingLMid = liftWingRMid = f;
        f = sectfile.get(s, "Wing_Out", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        liftWingLOut = liftWingROut = f;
        f = sectfile.get(s, "AirbrakeCxS", -1F);
        if(f == -1F)
            throw new RuntimeException(s1);
        dragAirbrakeCx = f;
        f = sectfile.get("Params", "SpinCxLoss", -1F);
        if(f == -1F)
            throw new RuntimeException(s1);
        spinCxloss = f;
        f = sectfile.get("Params", "SpinCyLoss", -1F);
        if(f == -1F)
            throw new RuntimeException(s1);
        spinCyloss = f;
        for(int i = 0; i < 8; i++)
            dragEngineCx[i] = 0.0F;

        s = "Toughness";
        for(int j = 0; j < 44; j++)
            toughness[j] = (float)sectfile.get(s, com.maddox.il2.objects.air.Aircraft.partNames()[j], 100) * 0.0001F;

        toughness[43] = 3.402823E+038F;
        float f1 = (2.0F * (liftWingLIn + liftWingLMid + liftWingLOut)) / (squareWing + 0.01F);
        if(f1 < 0.9F || f1 > 1.1F)
        {
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Error in flightmodel " + ((java.lang.Object) (sectfile)).toString() + ": (wing square) != (sum of squares*2)");
            if(f1 > 1.0F)
                squareWing = 2.0F * (liftWingLIn + liftWingLMid + liftWingLOut);
            else
                liftWingLIn = liftWingLMid = liftWingLOut = liftWingRIn = liftWingRMid = liftWingROut = 0.166667F * squareWing;
        }
    }

    public float getToughness(int i)
    {
        return toughness[i];
    }

    public void computeParasiteDrag(com.maddox.il2.fm.Controls controls, com.maddox.il2.ai.BulletEmitter abulletemitter[][])
    {
        dragParasiteCx = 0.0F;
        for(int i = 0; i < abulletemitter.length; i++)
        {
            if(abulletemitter[i] == null || abulletemitter[i].length <= 0)
                continue;
            for(int j = 0; j < abulletemitter[i].length; j++)
            {
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketGun)
                {
                    java.lang.Class theBulletClass = ((com.maddox.il2.objects.weapons.RocketGun)abulletemitter[i][j]).bulletClass();
                    if((com.maddox.il2.objects.weapons.Missile.class).isAssignableFrom(theBulletClass))
                        continue;
                }
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGunNull)
                    continue;
                if(((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGun) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketBombGun)) && abulletemitter[i][j].haveBullets() && abulletemitter[i][j].getHookName().startsWith("_External") && dragParasiteCx < 0.704F)
                    if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGunSC50) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGunSC70) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.FuelTankGun))
                        dragParasiteCx += 0.02F;
                    else
                        dragParasiteCx += 0.06F;
                if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketGun) && abulletemitter[i][j].haveBullets() && !(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketGunR4M))
                    dragParasiteCx += 0.02F;
                if(!(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.Pylon) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_1) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_3) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB100) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB250) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3FL) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3FR) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3WL) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3WR) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL5) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAILS) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120Internal))
                    continue;
                dragParasiteCx += 0.035F;
                if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129BK75) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129BK37))
                    dragParasiteCx += 0.45F;
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_WfrGr21)
                    dragParasiteCx += 0.015F;
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_WfrGr21Dual)
                    dragParasiteCx += 0.02F;
            }

        }

        dragParasiteCx += 0.02F * controls.getCockpitDoor();
    }

    public float squareWing;
    public float squareAilerons;
    public float squareElevators;
    public float squareRudders;
    public float squareFlaps;
    public float liftWingLIn;
    public float liftWingLMid;
    public float liftWingLOut;
    public float liftWingRIn;
    public float liftWingRMid;
    public float liftWingROut;
    public float liftStab;
    public float liftKeel;
    public float dragEngineCx[] = {
        0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F
    };
    public float dragParasiteCx;
    public float dragAirbrakeCx;
    public float dragFuselageCx;
    public float dragProducedCx;
    float spinCxloss;
    float spinCyloss;
    public float toughness[];
    public float eAbsorber[];
    public final float dragSmallHole = 0.06F;
    public final float dragBigHole = 0.12F;
    public final float wingSmallHole = 0.4F;
    public final float wingBigHole = 0.8F;
}

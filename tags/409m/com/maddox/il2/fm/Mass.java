// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mass.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonMG15120x2;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.fm:
//            FlightModelMain, Controls

public class Mass
{

    public Mass()
    {
        bFuelTanksLoaded = false;
        nitro = 0.0F;
        maxNitro = 1.0F;
    }

    public void load(com.maddox.rts.SectFile sectfile, com.maddox.il2.fm.FlightModelMain flightmodelmain)
    {
        java.lang.String s = "Mass";
        java.lang.String s1 = "Critical Mass in " + sectfile.toString();
        FM = flightmodelmain;
        float f = sectfile.get(s, "Empty", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        massEmpty = f;
        f = sectfile.get(s, "Oil", -1F);
        if(f == -1F)
            throw new RuntimeException(s1);
        massEmpty += f;
        f = sectfile.get("Aircraft", "Crew", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        massEmpty += f * 90F;
        f = sectfile.get(s, "TakeOff", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        maxWeight = f;
        f = sectfile.get(s, "Fuel", 0.0F);
        if(f == 0.0F)
        {
            throw new RuntimeException(s1);
        } else
        {
            maxFuel = f;
            mass = massEmpty;
            fuel = maxFuel;
            f = sectfile.get(s, "Nitro", 0.0F);
            maxNitro = nitro = f;
            return;
        }
    }

    public void onFuelTanksChanged()
    {
        bFuelTanksLoaded = true;
        fuelTanks = new com.maddox.il2.objects.weapons.FuelTank[0];
    }

    public boolean requestFuel(float f)
    {
        mass = massEmpty + fuel + nitro + parasiteMass;
        if(!bFuelTanksLoaded)
        {
            fuelTanks = FM.CT.getFuelTanks();
            bFuelTanksLoaded = true;
        }
        if(fuelTanks.length != 0)
        {
            float f1 = 0.0F;
            for(int i = 0; i < fuelTanks.length; i++)
                f1 += fuelTanks[i].getFuel(f / (float)fuelTanks.length);

            if(f1 > 0.0F)
                return true;
        }
        fuel -= f;
        if(fuel < 0.0F)
        {
            fuel = 0.0F;
            return false;
        } else
        {
            return true;
        }
    }

    public boolean requestNitro(float f)
    {
        mass = massEmpty + fuel + nitro + parasiteMass;
        nitro -= f;
        if(nitro < 0.0F)
        {
            nitro = 0.0F;
            return false;
        } else
        {
            return true;
        }
    }

    public float getFullMass()
    {
        return mass;
    }

    public void computeParasiteMass(com.maddox.il2.ai.BulletEmitter abulletemitter[][])
    {
        parasiteMass = 0.0F;
        parasiteJx = 0.0F;
        for(int i = 0; i < abulletemitter.length; i++)
            if(abulletemitter[i] != null && abulletemitter[i].length > 0)
            {
                for(int j = 0; j < abulletemitter[i].length; j++)
                {
                    if(abulletemitter[i][j] instanceof com.maddox.il2.engine.GunGeneric)
                    {
                        int k = abulletemitter[i][j].countBullets();
                        float f = abulletemitter[i][j].bulletMassa() * (float)(k < 0 ? 50 : k) * 3F;
                        float f3 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().z;
                        float f6 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().y;
                        parasiteJx += (f3 * f3 + f6 * f6) * f;
                        parasiteMass += f;
                    }
                    if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGun) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketGun))
                    {
                        int l = abulletemitter[i][j].countBullets();
                        float f1 = abulletemitter[i][j].bulletMassa() * (float)(l < 0 ? 1 : l);
                        float f4 = 0.0F;
                        float f7 = 2.0F;
                        parasiteJx += (f4 * f4 + f7 * f7) * f1;
                        parasiteMass += f1;
                    }
                    if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.Pylon)
                    {
                        float f2;
                        if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_1) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_3) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB100) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB250) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120Internal))
                            f2 = 0.0F;
                        else
                        if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120x2)
                            f2 = 450F;
                        else
                            f2 = 15F;
                        float f5 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().z;
                        float f8 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().y;
                        parasiteJx += (f5 * f5 + f8 * f8) * f2;
                        parasiteMass += f2;
                    }
                }

            }

    }

    public void computeFullJ(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1)
    {
        vector3d.scale(massEmpty, vector3d1);
        vector3d.x += parasiteJx;
    }

    private com.maddox.il2.fm.FlightModelMain FM;
    public float massEmpty;
    public float mass;
    public float maxWeight;
    private float parasiteMass;
    private float parasiteJx;
    public float fuel;
    public float maxFuel;
    private com.maddox.il2.objects.weapons.FuelTank fuelTanks[];
    private boolean bFuelTanksLoaded;
    public float nitro;
    public float maxNitro;
}

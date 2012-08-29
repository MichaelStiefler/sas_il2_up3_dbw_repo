// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Engines.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.HUD;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            FMMath, FlightModel, Pitot, Atmosphere, 
//            AircraftState, Controls, FmSounds

public class Engines extends com.maddox.il2.fm.FMMath
{

    public Engines()
    {
        Kh0 = 0.0F;
        HofVmax = 100F;
        RadiatorOK = true;
        bInline = false;
        bAutonomous = false;
        bRan = false;
        magneto = 0;
        stage = 0;
        oldStage = 0;
        timer = 0L;
        given = 0x3fffffffffffffffL;
        boostFactor = 1.0F;
        fmdreference = null;
        TOilIn = 0.0F;
        TOilOut = 0.0F;
        TWaterOut = 0.0F;
        isnd = null;
    }

    public void set(com.maddox.il2.fm.Engines engines)
    {
        Arm = new float[engines.Arm.length];
        Power = new float[engines.Arm.length];
        for(int i = 0; i < engines.Arm.length; i++)
        {
            Arm[i] = engines.Arm[i];
            Power[i] = engines.Power[i];
        }

        Power100 = engines.Power100;
        PowerSET = engines.PowerSET;
        Nominal = engines.Nominal;
        Reductor = engines.Reductor;
        W0 = engines.W0;
        Mp = engines.Mp;
        Ix = engines.Ix;
        PropDiam = engines.PropDiam;
        PropDir = engines.PropDir;
        Wx = engines.Wx;
        Kh0 = engines.Kh0;
        kh = engines.kh;
        HofVmax = engines.HofVmax;
        khAlt = engines.khAlt;
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        java.lang.String s = "Engine";
        if(sectfile.get(s, "Jet", 0) == 1)
            JET = true;
        Power = new float[Arm.length];
        Power100 = sectfile.get(s, JET ? "THRUST" : "POWER", 120F);
        if(JET)
            Power100 *= 9.81F;
        Nominal = sectfile.get(s, "NOMINAL", 0.0F);
        for(int i = 0; i < Arm.length; i++)
            Power[i] = 1.0F;

        if(!JET)
        {
            if(sectfile.get(s, "Inline", 0) == 1)
                bInline = true;
            if(sectfile.get(s, "Autonomous", 0) == 1)
                bAutonomous = true;
            Reductor = sectfile.get(s, "REDUCTOR", 1.0F);
            PropDiam = sectfile.get(s, "PROPELLER_DIAM", 3F);
            java.lang.String s1 = sectfile.get(s, "PROPELLER_DIR");
            if(s1.endsWith("LEFT"))
                PropDir = 1;
            else
            if(s1.endsWith("RIGHT"))
                PropDir = 2;
            else
                PropDir = 0;
            W0 = (6.283185F * Nominal * Reductor) / 60F;
            Mp = PropDiam * PropDiam * PropDiam;
            Ix = Mp * PropDiam * PropDiam;
        }
        PowerSET = 0.0F;
        boostFactor = sectfile.get(s, "BOOSTFACTOR", 1.0F);
        tChangeSpeed = sectfile.get(s, "TESPEED", 0.01F);
        tWaterMaxRPM = sectfile.get(s, "TWATERMAXRPM", 95F);
        tOilInMaxRPM = sectfile.get(s, "TOILINMAXRPM", 70F);
        tOilOutMaxRPM = sectfile.get(s, "TOILOUTMAXRPM", 107F);
        timeOverheat = sectfile.get(s, "MAXRPMTIME", 120F);
        timeUnderheat = sectfile.get(s, "MINRPMTIME", 999F);
        tWaterCritMax = sectfile.get(s, "TWATERMAX", 115F);
        tWaterCritMin = sectfile.get(s, "TWATERMIN", 60F);
        tOilCritMax = sectfile.get(s, "TOILMAX", 132F);
        tOilCritMin = sectfile.get(s, "TOILMIN", 40F);
        timeCounter = 0.0F;
        s = "Params";
        int j = 1;
        float f = sectfile.get(s, "Vmax", 0.0F) * 0.2777778F;
        int l = 2;
        do
        {
            if(sectfile.get(s, "VmaxH" + l, -1F) == -1F)
            {
                j = l - 1;
                break;
            }
            l++;
        } while(true);
        if(j == 1)
        {
            kh = new float[1];
            khAlt = new float[1];
            khAlt[0] = sectfile.get(s, "HofVmax", 100F);
            kh[0] = getKforH(f, sectfile.get(s, "VmaxH", 0.0F) * 0.2777778F, khAlt[0]);
        } else
        {
            kh = new float[j];
            khAlt = new float[j];
            for(int i1 = 0; i1 < kh.length; i1++)
            {
                khAlt[i1] = sectfile.get(s, "HofVmax" + (i1 + 1), 100F);
                kh[i1] = getKforH(f, sectfile.get(s, "VmaxH" + (i1 + 1), 0.0F) * 0.2777778F, khAlt[i1]);
            }

        }
    }

    public void setP(float f)
    {
        PowerSET = stage != 6 ? f : f;
    }

    public void setPowerReadyness(int i, float f)
    {
        Power[i] = f;
    }

    public float getPower()
    {
        float f = 0.0F;
        if(Power.length < 1)
            return 0.0F;
        for(int i = 0; i < Power.length; i++)
            f += Power[i];

        return ((stage != 6 ? 0.0F : PowerSET) * f) / (float)Power.length;
    }

    public float getRPM(int i)
    {
        if(JET)
            if(stage == 6)
            {
                float f = PowerSET;
                if(f < 0.1F)
                    f = 0.1F;
                return f * Nominal;
            } else
            {
                return 0.0F;
            }
        float f1 = (Wx / Reductor) * 9.55F;
        if(stage == 6 && f1 < 200F)
            f1 = 200F;
        if(Power[i] > 0.0F)
            return f1;
        else
            return 0.0F;
    }

    public float getWx(int i)
    {
        if(JET)
            return getRPM(i);
        float f = Wx;
        if(stage == 6 && f < 20F)
            f = 20F;
        if(Power[i] > 0.0F)
            return f;
        else
            return 0.0F;
    }

    public void update(float f, com.maddox.il2.fm.FlightModel flightmodel)
    {
        float f5 = com.maddox.il2.fm.Pitot.Indicator((float)flightmodel.Loc.z, flightmodel.getSpeedKMH());
        boolean flag = true;
        float f1 = com.maddox.il2.fm.Atmosphere.temperature((float)flightmodel.Loc.z) - 273.15F;
        if(stage == 6)
        {
            float f2 = 1.05F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(getPower() <= 0.2F ? 0.2F : getPower() + (float)flightmodel.AS.astateOilStates[0] * 0.33F)) * (float)java.lang.Math.sqrt(Wx / W0 <= 0.75F ? 0.75D : Wx / W0) * tOilOutMaxRPM * (flag ? 0.9F : 1.0F) * (1.0F - f5 * 0.0002F) + 22F;
            if(getPower() > 1.0F)
                f2 *= getPower();
            TOilOut += (f2 - TOilOut) * f * tChangeSpeed;
        } else
        {
            float f3 = (Wx / W0) * tOilOutMaxRPM * (flag ? 0.8F : 1.0F) + f1;
            TOilOut += (f3 - TOilOut) * f * tChangeSpeed * (bInline ? 0.42F : 1.07F);
        }
        float f4;
        if(flag)
            f4 = TOilOut * (0.75F - f5 * 0.0005F) + f1 * (0.25F + f5 * 0.0005F);
        else
            f4 = TOilOut * (0.8F - f5 * 0.0005F) + f1 * (0.2F + f5 * 0.0005F);
        TOilIn += (f4 - TOilIn) * f * tChangeSpeed * 0.5F;
        f4 = 1.05F * (float)java.lang.Math.sqrt(getPower()) * (1.0F - f5 * 0.0002F) * tWaterMaxRPM + f1;
        TWaterOut += (f4 - TWaterOut) * f * tChangeSpeed * (TWaterOut >= 50F ? 1.0F : 0.4F) * (1.0F - f5 * 0.0006F);
        if(TOilOut < f1)
            TOilOut = f1;
        if(TOilIn < f1)
            TOilIn = f1;
        if(TWaterOut < f1)
            TWaterOut = f1;
        if(com.maddox.il2.ai.World.cur().diffCur.Engine_Overheat && (TWaterOut > tWaterCritMax || TOilOut > tOilCritMax))
        {
            if(heatStringID == -1)
                heatStringID = com.maddox.il2.game.HUD.makeIdLog();
            com.maddox.il2.game.HUD.log(heatStringID, "EngineOverheat");
            timeCounter += f;
            if(timeCounter > timeOverheat)
            {
                for(int i = 0; i < Power.length; i++)
                    if(Power[i] > 0.33F)
                    {
                        Power[i] -= 0.00666F * f;
                        tOilCritMax -= 0.00666F * f * (tOilCritMax - tOilOutMaxRPM);
                    } else
                    {
                        Power[i] = 0.33F;
                        stage = 7;
                    }

            }
        } else
        if(timeCounter > 0.0F)
        {
            timeCounter = 0.0F;
            if(heatStringID == -1)
                heatStringID = com.maddox.il2.game.HUD.makeIdLog();
            com.maddox.il2.game.HUD.log(heatStringID, "EngineRestored");
        }
    }

    public void update(float f)
    {
        if(stage == 6)
            return;
        bTFirst = false;
        float f1 = 20F;
        long l = com.maddox.rts.Time.current() - timer;
        if(stage > 0 && stage < 6 && l > given)
        {
            stage++;
            if(fmdreference.isPlayers() && stage == 1)
                com.maddox.il2.game.HUD.log("Starting_Engine");
            timer = com.maddox.rts.Time.current();
        }
        if(oldStage != stage)
        {
            bTFirst = true;
            oldStage = stage;
        }
        if(stage > 0 && stage < 6 && fmdreference != null)
            fmdreference.CT.PowerControl = 0.2F;
        switch(stage)
        {
        case 0: // '\0'
            if(bTFirst)
            {
                given = 0x3fffffffffffffffL;
                timer = com.maddox.rts.Time.current();
            }
            setP(0.0F);
            break;

        case 1: // '\001'
            if(bTFirst)
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
            magneto = 2;
            Wx = 0.1047F * ((20F * (float)l) / (float)given);
            setP(0.0F);
            break;

        case 2: // '\002'
            if(bTFirst)
            {
                given = (long)(4000F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(bRan)
                {
                    given = (long)(100F + ((tOilOutMaxRPM - TOilOut) / (tOilOutMaxRPM - f1)) * 7900F * com.maddox.il2.ai.World.Rnd().nextFloat(2.0F, 4.2F));
                    if(given > 9000L)
                        given = com.maddox.il2.ai.World.Rnd().nextLong(7800L, 9600L);
                    if(fmdreference.AS.isMaster() && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        stage = 0;
                        fmdreference.AS.setEngineStops(0);
                    }
                }
            }
            Wx = 0.1047F * (20F + (15F * (float)l) / (float)given);
            setP(0.0F);
            break;

        case 3: // '\003'
            if(bTFirst)
            {
                given = (long)(50F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(fmdreference.AS.isMaster() && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F && (tOilOutMaxRPM - TOilOut) / (tOilOutMaxRPM - f1) < 0.75F)
                    fmdreference.AS.setEngineStops(0);
            }
            Wx = 0.1047F * (60F + (60F * (float)l) / (float)given);
            setP(0.0F);
            if(fmdreference == null || (tOilOutMaxRPM - TOilOut) / (tOilOutMaxRPM - f1) <= 0.75F)
                break;
            for(int i = 1; i < 4; i++)
            {
                for(int i1 = 1; i1 < 64; i1++)
                    try
                    {
                        com.maddox.il2.engine.Hook hook = fmdreference.actor.findHook("_Engine" + i + "EF_" + (i1 >= 10 ? "" + i1 : "0" + i1));
                        if(hook != null)
                            com.maddox.il2.engine.Eff3DActor.New(fmdreference.actor, hook, null, 1.0F, "3DO/Effects/Aircraft/EngineStart" + com.maddox.il2.ai.World.Rnd().nextInt(1, 3) + ".eff", -1F);
                    }
                    catch(java.lang.Exception exception) { }

            }

            break;

        case 4: // '\004'
            if(bTFirst)
            {
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(fmdreference.AS.isMaster() && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    setPowerReadyness(0, getEngineDamageFactor(0) - 0.1F);
            }
            Wx = 12.564F;
            setP(0.0F);
            break;

        case 5: // '\005'
            if(bTFirst)
            {
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(bRan)
                {
                    if((tOilOutMaxRPM - TOilOut) / (tOilOutMaxRPM - f1) > 0.75F)
                        if(bInline)
                        {
                            if(fmdreference.AS.isMaster() && getEngineDamageFactor(0) > 0.75F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                                setPowerReadyness(0, getEngineDamageFactor(0) - 0.05F);
                        } else
                        if(fmdreference.AS.isMaster() && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            fmdreference.AS.setEngineDies(fmdreference.actor, 0);
                    if(fmdreference.AS.isMaster() && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        fmdreference.AS.setEngineStops(0);
                }
                bRan = true;
            }
            Wx = 0.1047F * (120F + (120F * (float)l) / (float)given);
            setP(0.2F);
            break;

        case 6: // '\006'
            if(bTFirst)
            {
                given = -1L;
                fmdreference.AS.setEngineRunning(0);
            }
            break;

        case 7: // '\007'
            if(bTFirst)
                given = -1L;
            if(Power.length > 0)
            {
                for(int j = 0; j < Power.length; j++)
                    setPowerReadyness(j, 0.0F);

            }
            magneto = 0;
            break;

        default:
            return;
        }
    }

    public void torque(float f, com.maddox.JGP.Vector3f vector3f, com.maddox.JGP.Vector3f vector3f1, float f1, float f2)
    {
        if(JET)
        {
            vector3f1.set(0.0F, 0.0F, 0.0F);
            return;
        }
        float f4;
        float f3 = f4 = 0.0F;
        if(Wx < 0.0F)
            Wx = 0.0F;
        float f5 = Wx;
        for(int i = 0; i < Power.length; i++)
            f4 = java.lang.Math.max(f4, Power[i]);

        for(int j = 0; j < Power.length; j++)
            f3 += Power[j];

        if(f1 < 20F)
            f1 = 0.0F;
        else
            f1 -= 20F;
        if(f2 < -1F)
        {
            calcPropAOA(f1, f2);
            if(stage == 6 || stage == 7)
                Wx = (W0 * (float)java.lang.Math.sqrt(PowerSET * f4) + f1 * 0.2F) * (1.0F - 0.35F * propAOA);
            else
            if(stage == 0)
                Wx = 0.0F;
            Wx = f5 + (Wx - f5) * (Wx >= f5 ? 0.2658F / Reductor / Reductor : 0.5F) * f * 0.333F;
            if(f4 < 0.01F)
                Wx *= 0.99F;
            vector3f1.set(0.0F, 0.0F, 0.0F);
            return;
        }
        calcPropAOA(f1, f2);
        if(stage == 6 || stage == 7)
            Wx = (W0 * (float)java.lang.Math.sqrt(PowerSET * f4) + f1 * 0.2F) * (1.0F - 0.35F * propAOA);
        else
        if(stage == 0)
            Wx = 0.0F;
        Wx = f5 + (Wx - f5) * (Wx >= f5 ? 0.2658F / Reductor / Reductor : 0.5F) * f;
        if(f4 < 0.01F)
            Wx *= 0.99F;
        if(com.maddox.il2.ai.World.cur().diffCur.Torque_N_Gyro_Effects)
        {
            L.x = Ix * Wx * f3 * 0.15F;
            vector3f1.cross(vector3f, L);
            float f6 = ((Ix * (Wx - f5)) / f) * 0.8F;
            vector3f1.x += f3 <= 0.0F ? -f6 : f6;
            vector3f1.x *= PropDir != 1 ? -1F : 1.0F;
        } else
        {
            vector3f1.set(0.0F, 0.0F, 0.0F);
        }
    }

    private void calcPropAOA(float f, float f1)
    {
        float f2 = (float)java.lang.Math.atan2(f, 0.375F * PropDiam * (Wx + 1.0F));
        float f3;
        if(f1 < 0.0F)
            f3 = f2 + 0.2F;
        else
            f3 = 0.35F + f1 * 0.65F;
        if(f3 < 0.35F)
            f3 = 0.35F;
        else
        if(f3 > 1.0F)
            f3 = 1.0F;
        propAOA = f3 - f2;
    }

    public void propSpeed(float f)
    {
        float f1 = Wx;
        float f2 = 0.0F;
        for(int i = 0; i < Power.length; i++)
            f2 = java.lang.Math.max(f2, Power[i]);

        if(stage == 6)
            Wx = W0 * (float)java.lang.Math.sqrt(PowerSET * f2);
        if(stage == 0)
        {
            Wx = 0.0F;
            Wx = f1 + (Wx - f1) * (Wx >= f1 ? 0.01F : 0.03F);
            if(f2 < 0.01F)
                Wx *= 0.99F;
        }
    }

    public float forcePropAOA(float f, float f1)
    {
        Wx = W0;
        calcPropAOA(f, -2F);
        Wx = 0.0F;
        if(propAOA > 0.2F)
            propAOA = 0.2F + (propAOA - 0.2F) * 2.0F;
        return force(f, f1);
    }

    private float forceJet(float f, float f1)
    {
        float f2 = 0.0F;
        for(int i = 0; i < Power.length; i++)
            f2 += Power[i];

        f2 *= PowerSET * Power100;
        float f3 = f1 / khAlt[0];
        if(f3 < 1.0F)
            f3 = com.maddox.il2.fm.FMMath.interpolate(1.0F, kh[0], f3);
        else
            f3 = kh[0] / f3;
        return f2 * f3;
    }

    private float kV(float f)
    {
        return 1.0F - 0.0032F * f;
    }

    public float force(float f, float f1)
    {
        if(JET)
            return forceJet(f, f1);
        if(propAOA < 0.0F)
            return 0.0F;
        Pow = 0.0F;
        for(int i = 0; i < Power.length; i++)
            Pow += Power[i];

        Pow *= PowerSET * Power100 * 9.6F;
        if(PowerSET > 1.0F)
            Pow *= boostFactor;
        k = 0.0F;
        for(int j = 0; j < kh.length; j++)
        {
            ktmp = com.maddox.il2.fm.FMMath.interpolate(khAlt[0] / khAlt[j], kh[j], f1 / khAlt[j]);
            if(f1 > khAlt[j])
                ktmp = kh[j] * (com.maddox.il2.fm.Atmosphere.density(f1) / com.maddox.il2.fm.Atmosphere.density(khAlt[j]));
            if(ktmp > k)
                k = ktmp;
        }

        P = Pow * k * kV(f);
        if(propAOA <= 0.2F)
            return P * (propAOA * 5F);
        else
            return P * (0.2F / propAOA);
    }

    void setAltSpeed(float f, float f1, float f2)
    {
        HofVmax = f2;
        float f3 = (com.maddox.il2.fm.Atmosphere.density(HofVmax) * (f1 * f1)) / (com.maddox.il2.fm.Atmosphere.density(0.0F) * (f * f));
        if(JET)
            Kh0 = f3;
        else
            Kh0 = (f3 * kV(f)) / kV(f1);
    }

    protected float getKforH(float f, float f1, float f2)
    {
        float f3 = (com.maddox.il2.fm.Atmosphere.density(f2) * (f1 * f1)) / (com.maddox.il2.fm.Atmosphere.density(0.0F) * (f * f));
        if(!JET)
            f3 = (f3 * kV(f)) / kV(f1);
        return f3;
    }

    public void doStartEngine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        fmdreference = flightmodel;
        if(com.maddox.il2.ai.Airport.distToNearestAirport(fmdreference.Loc) < 1000D && fmdreference.isStationedOnGround())
        {
            stage = 1;
            bRan = false;
            timer = com.maddox.rts.Time.current();
            flightmodel.AS.setEngineStarts(0);
            return;
        }
        if(stage == 0)
            if(bAutonomous)
            {
                stage = 1;
                timer = com.maddox.rts.Time.current();
                flightmodel.AS.setEngineStarts(0);
            } else
            {
                doStopEngine(flightmodel);
                magneto = 0;
            }
    }

    public void doStopEngine(com.maddox.il2.fm.FlightModel flightmodel)
    {
        fmdreference = flightmodel;
        if(stage != 0)
        {
            stage = 0;
            magneto = 0;
            timer = com.maddox.rts.Time.current();
            flightmodel.AS.setEngineStops(0);
        }
    }

    public void doKillEngine(com.maddox.il2.fm.FlightModel flightmodel, int i)
    {
        fmdreference = flightmodel;
        flightmodel.setCapableOfTaxiing(false);
        flightmodel.setCapableOfACM(false);
        if(stage != 7)
        {
            setPowerReadyness(i, 0.0F);
            if(PowerSET != 0.0F && getPower() == 0.0F)
            {
                stage = 7;
                if(com.maddox.il2.ai.World.getPlayerAircraft() == flightmodel.actor)
                    com.maddox.il2.game.HUD.log("FailedEngine");
                magneto = 0;
            }
            timer = com.maddox.rts.Time.current();
        }
    }

    public void doKillCompressor(com.maddox.il2.fm.FlightModel flightmodel, int i)
    {
        fmdreference = flightmodel;
        if(kh.length > 1 && khAlt[0] != 55F)
        {
            kh = (new float[] {
                1.0F
            });
            khAlt = (new float[] {
                55F
            });
            if(com.maddox.il2.ai.World.getPlayerAircraft() == flightmodel.actor)
                com.maddox.il2.game.HUD.log("FailedCompressor");
        }
    }

    public void toggleMagnetos(com.maddox.il2.fm.FlightModel flightmodel)
    {
        fmdreference = flightmodel;
        if(stage == 0)
        {
            if((double)flightmodel.getAltitude() - com.maddox.il2.engine.Engine.land().HQ(flightmodel.Loc.x, flightmodel.Loc.y) > 25D)
            {
                if(bRan)
                {
                    magneto = 2;
                    doStartEngine(flightmodel);
                } else
                {
                    setMagnetos(flightmodel, (byte)2);
                }
                return;
            }
            magneto = 2;
            doStartEngine(flightmodel);
        } else
        {
            doStopEngine(flightmodel);
        }
    }

    public void setMagnetos(com.maddox.il2.fm.FlightModel flightmodel, byte byte0)
    {
        fmdreference = flightmodel;
        switch(byte0)
        {
        case 2: // '\002'
            magneto = 2;
            stage = 6;
            bRan = true;
            timer = com.maddox.rts.Time.current();
            flightmodel.AS.setEngineRunning(0);
            break;

        default:
            return;
        }
    }

    public byte getMagnetos()
    {
        return magneto;
    }

    public int getEngineNumber()
    {
        return Power.length;
    }

    public float getReductor()
    {
        return Reductor;
    }

    public float getFanSpeed(int i)
    {
        return Wx * 0.15915F * Power[i];
    }

    public float getEngineDamageFactor(int i)
    {
        return Power[i];
    }

    public float getPropellerMoment(int i)
    {
        return PowerSET * Power[i];
    }

    public int getStage(int i)
    {
        return stage;
    }

    float Arm[];
    float Power[];
    float Power100;
    float PowerSET;
    float Nominal;
    float Reductor;
    public float W0;
    public float Wx;
    public float propAOA;
    float Mp;
    float Ix;
    float Kh0;
    float kh[];
    float HofVmax;
    float khAlt[];
    public float PropDiam;
    public int PropDir;
    public boolean RadiatorOK;
    public boolean JET;
    public boolean bInline;
    public boolean bAutonomous;
    public boolean bRan;
    public byte magneto;
    private byte stage;
    private byte oldStage;
    private long timer;
    private long given;
    private static boolean bTFirst;
    private float boostFactor;
    private com.maddox.il2.fm.FlightModel fmdreference;
    public float TOilIn;
    public float TOilOut;
    public float TWaterOut;
    private float tWaterCritMin;
    private float tWaterCritMax;
    private float tOilCritMin;
    private float tOilCritMax;
    private float tWaterMaxRPM;
    public float tOilOutMaxRPM;
    private float tOilInMaxRPM;
    private float tChangeSpeed;
    private float timeOverheat;
    private float timeUnderheat;
    private float timeCounter;
    private static int heatStringID = -1;
    public com.maddox.il2.fm.FmSounds isnd;
    public static final int PROP_LEFT = 1;
    public static final int PROP_RIGHT = 2;
    public static final int PROP_AUTO = 0;
    private static com.maddox.JGP.Vector3f L = new Vector3f(0.0F, 0.0F, 0.0F);
    private static float k;
    private static float ktmp;
    private static float P;
    private static float Pow;

}

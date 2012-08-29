// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Motor.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BI_1;
import com.maddox.il2.objects.air.BI_6;
import com.maddox.il2.objects.air.GLADIATOR;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.P_38;
import com.maddox.il2.objects.air.P_51;
import com.maddox.il2.objects.air.P_63C;
import com.maddox.il2.objects.air.SM79;
import com.maddox.il2.objects.air.SPITFIRE5B;
import com.maddox.il2.objects.air.SPITFIRE8;
import com.maddox.il2.objects.air.SPITFIRE8CLP;
import com.maddox.il2.objects.air.SPITFIRE9;
import com.maddox.il2.objects.air.YAK_3;
import com.maddox.il2.objects.air.YAK_3P;
import com.maddox.il2.objects.air.YAK_9M;
import com.maddox.il2.objects.air.YAK_9U;
import com.maddox.il2.objects.air.YAK_9UT;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.fm:
//            FMMath, RealFlightModel, Atmosphere, FlightModelMain, 
//            FlightModel, Controls, AircraftState, EnginesInterface, 
//            FmSounds, Mass, Pitot

public class Motor extends com.maddox.il2.fm.FMMath
{

    public Motor()
    {
        isnd = null;
        reference = null;
        soundName = null;
        startStopName = null;
        propName = null;
        number = 0;
        type = 0;
        cylinders = 12;
        engineMass = 900F;
        wMin = 20F;
        wNom = 180F;
        wMax = 200F;
        wWEP = 220F;
        wMaxAllowed = 250F;
        wNetPrev = 0;
        engineMoment = 0.0F;
        engineMomentMax = 0.0F;
        engineBoostFactor = 1.0F;
        engineAfterburnerBoostFactor = 1.0F;
        engineDistAM = 0.0F;
        engineDistBM = 0.0F;
        engineDistCM = 0.0F;
        bRan = false;
        enginePos = new Point3f();
        engineVector = new Vector3f();
        engineForce = new Vector3f();
        engineTorque = new Vector3f();
        engineDamageAccum = 0.0F;
        _1_wMaxAllowed = 1.0F / wMaxAllowed;
        _1_wMax = 1.0F / wMax;
        RPMMin = 200F;
        RPMNom = 2000F;
        RPMMax = 2200F;
        Vopt = 90F;
        momForFuel = 0.0D;
        addVflow = 0.0D;
        addVside = 0.0D;
        propPos = new Point3f();
        propReductor = 1.0F;
        propAngleDeviceType = 0;
        propAngleDeviceMinParam = 0.0F;
        propAngleDeviceMaxParam = 0.0F;
        propAngleDeviceAfterburnerParam = -999.9F;
        propDirection = 0;
        propDiameter = 3F;
        propMass = 30F;
        propI = 1.0F;
        propIW = new Vector3d();
        propSEquivalent = 1.0F;
        propr = 1.125F;
        propPhiMin = (float)java.lang.Math.toRadians(10D);
        propPhiMax = (float)java.lang.Math.toRadians(29D);
        propPhi = (float)java.lang.Math.toRadians(11D);
        propAoA0 = (float)java.lang.Math.toRadians(11D);
        propAoACrit = (float)java.lang.Math.toRadians(16D);
        propAngleChangeSpeed = 0.1F;
        propForce = 0.0F;
        propMoment = 0.0F;
        propTarget = 0.0F;
        mixerType = 0;
        mixerLowPressureBar = 0.0F;
        horsePowers = 1200F;
        thrustMax = 10.7F;
        cylindersOperable = 12;
        engineI = 1.0F;
        engineAcceleration = 1.0F;
        bIsAutonomous = true;
        bIsMaster = true;
        bIsStuck = false;
        bIsInoperable = false;
        bIsAngleDeviceOperational = true;
        isPropAngleDeviceHydroOperable = true;
        engineCarburetorType = 0;
        FuelConsumptionP0 = 0.4F;
        FuelConsumptionP05 = 0.24F;
        FuelConsumptionP1 = 0.28F;
        FuelConsumptionPMAX = 0.3F;
        compressorType = 0;
        compressorMaxStep = 0;
        compressorPMax = 1.0F;
        compressorManifoldPressure = 1.0F;
        compressorAltitudes = null;
        compressorPressure = null;
        compressorAltMultipliers = null;
        compressorRPMtoP0 = 1500F;
        compressorRPMtoCurvature = -30F;
        compressorRPMtoPMax = 2600F;
        compressorRPMtoWMaxATA = 1.45F;
        compressorSpeedManifold = 0.2F;
        compressorRPM = new float[16];
        compressorATA = new float[16];
        nOfCompPoints = 0;
        compressorStepFound = false;
        compressorManifoldThreshold = 1.0F;
        afterburnerCompressorFactor = 1.0F;
        _1_P0 = 1.0F / com.maddox.il2.fm.Atmosphere.P0();
        compressor1stThrottle = 1.0F;
        compressor2ndThrottle = 1.0F;
        compressorPAt0 = 0.3F;
        afterburnerType = 0;
        afterburnerChangeW = false;
        stage = 0;
        oldStage = 0;
        timer = 0L;
        given = 0x3fffffffffffffffL;
        rpm = 0.0F;
        w = 0.0F;
        aw = 0.0F;
        oldW = 0.0F;
        readyness = 1.0F;
        oldReadyness = 1.0F;
        radiatorReadyness = 1.0F;
        tOilIn = 0.0F;
        tOilOut = 0.0F;
        tWaterOut = 0.0F;
        tCylinders = 0.0F;
        oilMass = 90F;
        waterMass = 90F;
        bHasThrottleControl = true;
        bHasAfterburnerControl = true;
        bHasPropControl = true;
        bHasRadiatorControl = true;
        bHasMixControl = true;
        bHasMagnetoControl = true;
        bHasExtinguisherControl = false;
        bHasCompressorControl = false;
        bHasFeatherControl = false;
        extinguishers = 0;
        controlThrottle = 0.0F;
        controlRadiator = 0.0F;
        controlAfterburner = false;
        controlProp = 1.0F;
        bControlPropAuto = true;
        controlMix = 1.0F;
        controlMagneto = 0;
        controlCompressor = 0;
        controlFeather = 0;
        neg_G_Counter = 0.0F;
        bFullT = false;
        bFloodCarb = false;
        fastATA = false;
        old_engineForce = new Vector3f();
        old_engineTorque = new Vector3f();
        updateStep = 0.12F;
        updateLast = 0.0F;
        fricCoeffT = 1.0F;
        engineNoFuelHUDLogId = -1;
    }

    public void load(com.maddox.il2.fm.FlightModel flightmodel, java.lang.String s, java.lang.String s1, int i)
    {
        reference = flightmodel;
        number = i;
        com.maddox.rts.SectFile sectfile = com.maddox.il2.fm.FlightModelMain.sectFile(s);
        resolveFromFile(sectfile, "Generic");
        resolveFromFile(sectfile, s1);
        calcAfterburnerCompressorFactor();
        if(type == 0 || type == 1 || type == 7)
            initializeInline(flightmodel.Vmax);
        if(type == 2)
            initializeJet(flightmodel.Vmax);
    }

    private void resolveFromFile(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        soundName = sectfile.get(s, "SoundName", soundName);
        propName = sectfile.get(s, "PropName", propName);
        startStopName = sectfile.get(s, "StartStopName", startStopName);
        com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Resolving submodel " + s + " from file '" + sectfile.toString() + "'....");
        java.lang.String s1 = sectfile.get(s, "Type");
        if(s1 != null)
            if(s1.endsWith("Inline"))
                type = 0;
            else
            if(s1.endsWith("Radial"))
                type = 1;
            else
            if(s1.endsWith("Jet"))
                type = 2;
            else
            if(s1.endsWith("RocketBoost"))
                type = 4;
            else
            if(s1.endsWith("Rocket"))
                type = 3;
            else
            if(s1.endsWith("Tow"))
                type = 5;
            else
            if(s1.endsWith("PVRD"))
                type = 6;
            else
            if(s1.endsWith("Unknown"))
                type = 8;
            else
            if(s1.endsWith("Azure"))
                type = 8;
            else
            if(s1.endsWith("HeloI"))
                type = 7;
        if(type == 0 || type == 1 || type == 7)
        {
            int i = sectfile.get(s, "Cylinders", 0xfffe7961);
            if(i != 0xfffe7961)
            {
                cylinders = i;
                cylindersOperable = cylinders;
            }
        }
        s1 = sectfile.get(s, "Direction");
        if(s1 != null)
            if(s1.endsWith("Left"))
                propDirection = 0;
            else
            if(s1.endsWith("Right"))
                propDirection = 1;
        float f = sectfile.get(s, "RPMMin", -99999F);
        if(f != -99999F)
        {
            RPMMin = f;
            wMin = toRadianPerSecond(RPMMin);
        }
        f = sectfile.get(s, "RPMNom", -99999F);
        if(f != -99999F)
        {
            RPMNom = f;
            wNom = toRadianPerSecond(RPMNom);
        }
        f = sectfile.get(s, "RPMMax", -99999F);
        if(f != -99999F)
        {
            RPMMax = f;
            wMax = toRadianPerSecond(RPMMax);
            _1_wMax = 1.0F / wMax;
        }
        f = sectfile.get(s, "RPMMaxAllowed", -99999F);
        if(f != -99999F)
        {
            wMaxAllowed = toRadianPerSecond(f);
            _1_wMaxAllowed = 1.0F / wMaxAllowed;
        }
        f = sectfile.get(s, "Reductor", -99999F);
        if(f != -99999F)
            propReductor = f;
        if(type == 0 || type == 1 || type == 7)
        {
            f = sectfile.get(s, "HorsePowers", -99999F);
            if(f != -99999F)
                horsePowers = f;
            int j = sectfile.get(s, "Carburetor", 0xfffe7961);
            if(j != 0xfffe7961)
                engineCarburetorType = j;
            f = sectfile.get(s, "Mass", -99999F);
            if(f != -99999F)
                engineMass = f;
            else
                engineMass = horsePowers * 0.6F;
        } else
        {
            f = sectfile.get(s, "Thrust", -99999F);
            if(f != -99999F)
                thrustMax = f * 9.81F;
        }
        f = sectfile.get(s, "BoostFactor", -99999F);
        if(f != -99999F)
            engineBoostFactor = f;
        f = sectfile.get(s, "WEPBoostFactor", -99999F);
        if(f != -99999F)
            engineAfterburnerBoostFactor = f;
        if(type == 2)
        {
            FuelConsumptionP0 = 0.075F;
            FuelConsumptionP05 = 0.075F;
            FuelConsumptionP1 = 0.1F;
            FuelConsumptionPMAX = 0.11F;
        }
        if(type == 6)
        {
            FuelConsumptionP0 = 0.835F;
            FuelConsumptionP05 = 0.835F;
            FuelConsumptionP1 = 0.835F;
            FuelConsumptionPMAX = 0.835F;
        }
        f = sectfile.get(s, "FuelConsumptionP0", -99999F);
        if(f != -99999F)
            FuelConsumptionP0 = f;
        f = sectfile.get(s, "FuelConsumptionP05", -99999F);
        if(f != -99999F)
            FuelConsumptionP05 = f;
        f = sectfile.get(s, "FuelConsumptionP1", -99999F);
        if(f != -99999F)
            FuelConsumptionP1 = f;
        f = sectfile.get(s, "FuelConsumptionPMAX", -99999F);
        if(f != -99999F)
            FuelConsumptionPMAX = f;
        int k = sectfile.get(s, "Autonomous", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bIsAutonomous = false;
            else
            if(k == 1)
                bIsAutonomous = true;
        k = sectfile.get(s, "cThrottle", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasThrottleControl = false;
            else
            if(k == 1)
                bHasThrottleControl = true;
        k = sectfile.get(s, "cAfterburner", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasAfterburnerControl = false;
            else
            if(k == 1)
                bHasAfterburnerControl = true;
        k = sectfile.get(s, "cProp", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasPropControl = false;
            else
            if(k == 1)
                bHasPropControl = true;
        k = sectfile.get(s, "cMix", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasMixControl = false;
            else
            if(k == 1)
                bHasMixControl = true;
        k = sectfile.get(s, "cMagneto", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasMagnetoControl = false;
            else
            if(k == 1)
                bHasMagnetoControl = true;
        k = sectfile.get(s, "cCompressor", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasCompressorControl = false;
            else
            if(k == 1)
                bHasCompressorControl = true;
        k = sectfile.get(s, "cFeather", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasFeatherControl = false;
            else
            if(k == 1)
                bHasFeatherControl = true;
        k = sectfile.get(s, "cRadiator", 0xfffe7961);
        if(k != 0xfffe7961)
            if(k == 0)
                bHasRadiatorControl = false;
            else
            if(k == 1)
                bHasRadiatorControl = true;
        k = sectfile.get(s, "Extinguishers", 0xfffe7961);
        if(k != 0xfffe7961)
        {
            extinguishers = k;
            if(k != 0)
                bHasExtinguisherControl = true;
            else
                bHasExtinguisherControl = false;
        }
        f = sectfile.get(s, "PropDiameter", -99999F);
        if(f != -99999F)
            propDiameter = f;
        propr = 0.5F * propDiameter * 0.75F;
        f = sectfile.get(s, "PropMass", -99999F);
        if(f != -99999F)
            propMass = f;
        propI = propMass * propDiameter * propDiameter * 0.083F;
        bWepRpmInLowGear = false;
        k = sectfile.get(s, "PropAnglerType", 0xfffe7961);
        if(k != 0xfffe7961)
        {
            if(k > 255)
            {
                bWepRpmInLowGear = (k & 0x100) > 1;
                k -= 256;
            }
            propAngleDeviceType = k;
        }
        f = sectfile.get(s, "PropAnglerSpeed", -99999F);
        if(f != -99999F)
            propAngleChangeSpeed = f;
        f = sectfile.get(s, "PropAnglerMinParam", -99999F);
        if(f != -99999F)
        {
            propAngleDeviceMinParam = f;
            if(propAngleDeviceType == 6 || propAngleDeviceType == 5)
                propAngleDeviceMinParam = (float)java.lang.Math.toRadians(propAngleDeviceMinParam);
            if(propAngleDeviceType == 1 || propAngleDeviceType == 2 || propAngleDeviceType == 7 || propAngleDeviceType == 8 || propAngleDeviceType == 9)
                propAngleDeviceMinParam = toRadianPerSecond(propAngleDeviceMinParam);
        }
        f = sectfile.get(s, "PropAnglerMaxParam", -99999F);
        if(f != -99999F)
        {
            propAngleDeviceMaxParam = f;
            if(propAngleDeviceType == 6 || propAngleDeviceType == 5)
                propAngleDeviceMaxParam = (float)java.lang.Math.toRadians(propAngleDeviceMaxParam);
            if(propAngleDeviceType == 1 || propAngleDeviceType == 2 || propAngleDeviceType == 7 || propAngleDeviceType == 8 || propAngleDeviceType == 9)
                propAngleDeviceMaxParam = toRadianPerSecond(propAngleDeviceMaxParam);
            if(propAngleDeviceAfterburnerParam == -999.9F)
                propAngleDeviceAfterburnerParam = propAngleDeviceMaxParam;
        }
        f = sectfile.get(s, "PropAnglerAfterburnerParam", -99999F);
        if(f != -99999F)
        {
            propAngleDeviceAfterburnerParam = f;
            wWEP = toRadianPerSecond(propAngleDeviceAfterburnerParam);
            if(wWEP != wMax)
                afterburnerChangeW = true;
            if(propAngleDeviceType == 6 || propAngleDeviceType == 5)
                propAngleDeviceAfterburnerParam = (float)java.lang.Math.toRadians(propAngleDeviceAfterburnerParam);
            if(propAngleDeviceType == 1 || propAngleDeviceType == 2 || propAngleDeviceType == 7 || propAngleDeviceType == 8 || propAngleDeviceType == 9)
                propAngleDeviceAfterburnerParam = toRadianPerSecond(propAngleDeviceAfterburnerParam);
        } else
        {
            wWEP = wMax;
        }
        f = sectfile.get(s, "PropPhiMin", -99999F);
        if(f != -99999F)
        {
            propPhiMin = (float)java.lang.Math.toRadians(f);
            if(propPhi < propPhiMin)
                propPhi = propPhiMin;
            if(propTarget < propPhiMin)
                propTarget = propPhiMin;
        }
        f = sectfile.get(s, "PropPhiMax", -99999F);
        if(f != -99999F)
        {
            propPhiMax = (float)java.lang.Math.toRadians(f);
            if(propPhi > propPhiMax)
                propPhi = propPhiMax;
            if(propTarget > propPhiMax)
                propTarget = propPhiMax;
        }
        f = sectfile.get(s, "PropAoA0", -99999F);
        if(f != -99999F)
            propAoA0 = (float)java.lang.Math.toRadians(f);
        k = sectfile.get(s, "CompressorType", 0xfffe7961);
        if(k != 0xfffe7961)
            compressorType = k;
        f = sectfile.get(s, "CompressorPMax", -99999F);
        if(f != -99999F)
            compressorPMax = f;
        k = sectfile.get(s, "CompressorSteps", 0xfffe7961);
        if(k != 0xfffe7961)
        {
            compressorMaxStep = k - 1;
            if(compressorMaxStep < 0)
                compressorMaxStep = 0;
        }
        if(compressorAltitudes != null)
            if(compressorAltitudes.length == compressorMaxStep + 1);
        compressorAltitudes = new float[compressorMaxStep + 1];
        compressorPressure = new float[compressorMaxStep + 1];
        compressorAltMultipliers = new float[compressorMaxStep + 1];
        if(compressorAltitudes.length > 0)
        {
            for(int l = 0; l < compressorAltitudes.length; l++)
            {
                f = sectfile.get(s, "CompressorAltitude" + l, -99999F);
                if(f != -99999F)
                {
                    compressorAltitudes[l] = f;
                    compressorPressure[l] = com.maddox.il2.fm.Atmosphere.pressure(compressorAltitudes[l]) * _1_P0;
                }
                f = sectfile.get(s, "CompressorMultiplier" + l, -99999F);
                if(f != -99999F)
                    compressorAltMultipliers[l] = f;
            }

        }
        f = sectfile.get(s, "CompressorRPMP0", -99999F);
        if(f != -99999F)
        {
            compressorRPMtoP0 = f;
            insetrPoiInCompressorPoly(compressorRPMtoP0, 1.0F);
        }
        f = sectfile.get(s, "CompressorRPMCurvature", -99999F);
        if(f != -99999F)
            compressorRPMtoCurvature = f;
        f = sectfile.get(s, "CompressorMaxATARPM", -99999F);
        if(f != -99999F)
        {
            compressorRPMtoWMaxATA = f;
            insetrPoiInCompressorPoly(RPMMax, compressorRPMtoWMaxATA);
        }
        f = sectfile.get(s, "CompressorRPMPMax", -99999F);
        if(f != -99999F)
        {
            compressorRPMtoPMax = f;
            insetrPoiInCompressorPoly(compressorRPMtoPMax, compressorPMax);
        }
        f = sectfile.get(s, "CompressorSpeedManifold", -99999F);
        if(f != -99999F)
            compressorSpeedManifold = f;
        f = sectfile.get(s, "CompressorPAt0", -99999F);
        if(f != -99999F)
            compressorPAt0 = f;
        f = sectfile.get(s, "Voptimal", -99999F);
        if(f != -99999F)
            Vopt = f * 0.277778F;
        boolean flag = true;
        float f1 = 2000F;
        float f2 = 1.0F;
        int i1 = 0;
        do
        {
            if(!flag)
                break;
            f = sectfile.get(s, "CompressorRPM" + i1, -99999F);
            if(f != -99999F)
                f1 = f;
            else
                flag = false;
            f = sectfile.get(s, "CompressorATA" + i1, -99999F);
            if(f != -99999F)
                f2 = f;
            else
                flag = false;
            if(flag)
                insetrPoiInCompressorPoly(f1, f2);
            i1++;
            if(nOfCompPoints > 15 || i1 > 15)
                flag = false;
        } while(true);
        k = sectfile.get(s, "AfterburnerType", 0xfffe7961);
        if(k != 0xfffe7961)
            afterburnerType = k;
        k = sectfile.get(s, "MixerType", 0xfffe7961);
        if(k != 0xfffe7961)
            mixerType = k;
        f = sectfile.get(s, "MixerAltitude", -99999F);
        if(f != -99999F)
            mixerLowPressureBar = com.maddox.il2.fm.Atmosphere.pressure(f) / com.maddox.il2.fm.Atmosphere.P0();
        f = sectfile.get(s, "EngineI", -99999F);
        if(f != -99999F)
            engineI = f;
        f = sectfile.get(s, "EngineAcceleration", -99999F);
        if(f != -99999F)
            engineAcceleration = f;
        f = sectfile.get(s, "DisP0x", -99999F);
        if(f != -99999F)
        {
            float f3 = sectfile.get(s, "DisP0x", -99999F);
            f3 = toRadianPerSecond(f3);
            float f4 = sectfile.get(s, "DisP0y", -99999F);
            f4 *= 0.01F;
            float f5 = sectfile.get(s, "DisP1x", -99999F);
            f5 = toRadianPerSecond(f5);
            float f6 = sectfile.get(s, "DisP1y", -99999F);
            f6 *= 0.01F;
            float f7 = f3;
            float f8 = f4;
            float f9 = (f5 - f3) * (f5 - f3);
            float f10 = f6 - f4;
            engineDistAM = f10 / f9;
            engineDistBM = (-2F * f10 * f7) / f9;
            engineDistCM = f8 + (f10 * f7 * f7) / f9;
        }
        timeCounter = 0.0F;
        f = sectfile.get(s, "TESPEED", -99999F);
        if(f != -99999F)
            tChangeSpeed = f;
        f = sectfile.get(s, "TWATERMAXRPM", -99999F);
        if(f != -99999F)
            tWaterMaxRPM = f;
        f = sectfile.get(s, "TOILINMAXRPM", -99999F);
        if(f != -99999F)
            tOilInMaxRPM = f;
        f = sectfile.get(s, "TOILOUTMAXRPM", -99999F);
        if(f != -99999F)
            tOilOutMaxRPM = f;
        f = sectfile.get(s, "MAXRPMTIME", -99999F);
        if(f != -99999F)
            timeOverheat = f;
        f = sectfile.get(s, "MINRPMTIME", -99999F);
        if(f != -99999F)
            timeUnderheat = f;
        f = sectfile.get(s, "TWATERMAX", -99999F);
        if(f != -99999F)
            tWaterCritMax = f;
        f = sectfile.get(s, "TWATERMIN", -99999F);
        if(f != -99999F)
            tWaterCritMin = f;
        f = sectfile.get(s, "TOILMAX", -99999F);
        if(f != -99999F)
            tOilCritMax = f;
        f = sectfile.get(s, "TOILMIN", -99999F);
        if(f != -99999F)
            tOilCritMin = f;
        coolMult = 1.0F;
    }

    private void initializeInline(float f)
    {
        propSEquivalent = 0.26F * propr * propr;
        engineMomentMax = (horsePowers * 746F * 1.2F) / wMax;
    }

    private void initializeJet(float f)
    {
        propSEquivalent = ((float)(cylinders * cylinders) * (2.0F * thrustMax)) / (getFanCy(propAoA0) * com.maddox.il2.fm.Atmosphere.ro0() * wMax * wMax * propr * propr);
        computePropForces(wMax, 0.0F, 0.0F, propAoA0, 0.0F);
        engineMomentMax = propMoment;
    }

    public void initializeTowString(float f)
    {
        propForce = f;
    }

    public void setMaster(boolean flag)
    {
        bIsMaster = flag;
    }

    private void insetrPoiInCompressorPoly(float f, float f1)
    {
        int i = 0;
        do
        {
            if(i >= nOfCompPoints)
                break;
            if(compressorRPM[i] >= f)
            {
                if(compressorRPM[i] == f)
                    return;
                break;
            }
            i++;
        } while(true);
        for(int j = nOfCompPoints - 1; j >= i; j--)
        {
            compressorRPM[j + 1] = compressorRPM[j];
            compressorATA[j + 1] = compressorATA[j];
        }

        nOfCompPoints++;
        compressorRPM[i] = f;
        compressorATA[i] = f1;
    }

    private void calcAfterburnerCompressorFactor()
    {
        if(afterburnerType == 1 || afterburnerType == 7 || afterburnerType == 8 || afterburnerType == 10 || afterburnerType == 11 || afterburnerType == 6 || afterburnerType == 5 || afterburnerType == 9 || afterburnerType == 4)
        {
            float f = compressorRPM[nOfCompPoints - 1];
            float f1 = compressorATA[nOfCompPoints - 1];
            nOfCompPoints--;
            int i = 0;
            int j = 1;
            float f2 = 1.0F;
            float f3 = f;
            if(nOfCompPoints < 2)
            {
                afterburnerCompressorFactor = 1.0F;
                return;
            }
            if((double)f3 < 0.10000000000000001D)
                f2 = com.maddox.il2.fm.Atmosphere.pressure((float)reference.Loc.z) * _1_P0;
            else
            if(f3 >= compressorRPM[nOfCompPoints - 1])
            {
                f2 = compressorATA[nOfCompPoints - 1];
            } else
            {
                if(f3 < compressorRPM[0])
                {
                    i = 0;
                    j = 1;
                } else
                {
                    int k = 0;
                    do
                    {
                        if(k >= nOfCompPoints - 1)
                            break;
                        if(compressorRPM[k] <= f3 && f3 < compressorRPM[k + 1])
                        {
                            i = k;
                            j = k + 1;
                            break;
                        }
                        k++;
                    } while(true);
                }
                float f4 = compressorRPM[j] - compressorRPM[i];
                if(f4 < 0.001F)
                    f4 = 0.001F;
                f2 = compressorATA[i] + ((f3 - compressorRPM[i]) * (compressorATA[j] - compressorATA[i])) / f4;
            }
            afterburnerCompressorFactor = f1 / f2;
        } else
        {
            afterburnerCompressorFactor = 1.0F;
        }
    }

    public float getATA(float f)
    {
        int i = 0;
        int j = 1;
        float f1 = 1.0F;
        if(nOfCompPoints < 2)
            return 1.0F;
        if((double)f < 0.10000000000000001D)
            f1 = com.maddox.il2.fm.Atmosphere.pressure((float)reference.Loc.z) * _1_P0;
        else
        if(f >= compressorRPM[nOfCompPoints - 1])
        {
            f1 = compressorATA[nOfCompPoints - 1];
        } else
        {
            if(f < compressorRPM[0])
            {
                i = 0;
                j = 1;
            } else
            {
                int k = 0;
                do
                {
                    if(k >= nOfCompPoints - 1)
                        break;
                    if(compressorRPM[k] <= f && f < compressorRPM[k + 1])
                    {
                        i = k;
                        j = k + 1;
                        break;
                    }
                    k++;
                } while(true);
            }
            float f2 = compressorRPM[j] - compressorRPM[i];
            if(f2 < 0.001F)
                f2 = 0.001F;
            f1 = compressorATA[i] + ((f - compressorRPM[i]) * (compressorATA[j] - compressorATA[i])) / f2;
        }
        return f1;
    }

    public void update(float f)
    {
        if(!(reference instanceof com.maddox.il2.fm.RealFlightModel) && com.maddox.rts.Time.tickCounter() > 200)
        {
            updateLast += f;
            if(updateLast >= updateStep)
            {
                f = updateStep;
            } else
            {
                engineForce.set(old_engineForce);
                engineTorque.set(old_engineTorque);
                return;
            }
        }
        producedDistabilisation = 0.0F;
        pressureExtBar = com.maddox.il2.fm.Atmosphere.pressure(reference.getAltitude()) + compressorSpeedManifold * 0.5F * com.maddox.il2.fm.Atmosphere.density(reference.getAltitude()) * reference.getSpeed() * reference.getSpeed();
        pressureExtBar /= com.maddox.il2.fm.Atmosphere.P0();
        if(controlThrottle > 1.0F && engineBoostFactor == 1.0F)
        {
            reference.CT.setPowerControl(1.0F);
            if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "Power", new java.lang.Object[] {
                    new Integer(100)
                });
        }
        computeForces(f);
        computeStage(f);
        if(stage > 0 && stage < 6)
            engineForce.set(0.0F, 0.0F, 0.0F);
        else
        if(stage == 8)
            rpm = w = 0.0F;
        if(reference.isPlayers())
        {
            if(bIsMaster && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
            {
                computeTemperature(f);
                if(com.maddox.il2.ai.World.cur().diffCur.Reliability)
                    computeReliability(f);
            }
            if(com.maddox.il2.ai.World.cur().diffCur.Limited_Fuel)
                computeFuel(f);
        } else
        {
            computeFuel(f);
        }
        old_engineForce.set(engineForce);
        old_engineTorque.set(engineTorque);
        updateLast = 0.0F;
        float f1 = 0.5F / (java.lang.Math.abs(aw) + 1.0F) - 0.1F;
        if(f1 < 0.025F)
            f1 = 0.025F;
        if(f1 > 0.4F)
            f1 = 0.4F;
        if(f1 < updateStep)
            updateStep = 0.9F * updateStep + 0.1F * f1;
        else
            updateStep = 0.99F * updateStep + 0.01F * f1;
    }

    public void netupdate(float f, boolean flag)
    {
        computeStage(f);
        if((double)java.lang.Math.abs(w) < 1.0000000000000001E-005D)
            propPhiW = 1.570796F;
        else
            propPhiW = (float)java.lang.Math.atan(reference.Vflow.x / (double)(w * propReductor * propr));
        propAoA = propPhi - propPhiW;
        computePropForces(w * propReductor, (float)reference.Vflow.x, propPhi, propAoA, reference.getAltitude());
        float f1 = w;
        float f2 = propPhi;
        float f3 = compressorManifoldPressure;
        computeForces(f);
        if(flag)
            compressorManifoldPressure = f3;
        w = f1;
        propPhi = f2;
        rpm = toRPM(w);
    }

    public void setReadyness(com.maddox.il2.engine.Actor actor, float f)
    {
        if(f > 1.0F)
            f = 1.0F;
        if(f < 0.0F)
            f = 0.0F;
        if(!com.maddox.il2.engine.Actor.isAlive(actor))
            return;
        if(bIsMaster)
        {
            if(readyness > 0.0F && f == 0.0F)
            {
                readyness = 0.0F;
                setEngineDies(actor);
                return;
            }
            doSetReadyness(f);
        }
        if(java.lang.Math.abs(oldReadyness - readyness) > 0.1F)
        {
            reference.AS.setEngineReadyness(actor, number, (int)(f * 100F));
            oldReadyness = readyness;
        }
    }

    private void setReadyness(float f)
    {
        setReadyness(reference.actor, f);
    }

    public void doSetReadyness(float f)
    {
        readyness = f;
    }

    public void setStage(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(actor))
            return;
        if(bIsMaster)
            doSetStage(i);
        reference.AS.setEngineStage(actor, number, i);
    }

    public void doSetStage(int i)
    {
        stage = i;
    }

    public void setEngineStarts(com.maddox.il2.engine.Actor actor)
    {
        if(!bIsMaster || !com.maddox.il2.engine.Actor.isAlive(actor))
            return;
        if(isHasControlMagnetos() && getMagnetoMultiplier() < 0.1F)
        {
            return;
        } else
        {
            reference.AS.setEngineStarts(number);
            return;
        }
    }

    public void doSetEngineStarts()
    {
        if(com.maddox.il2.ai.Airport.distToNearestAirport(reference.Loc) < 1200D && reference.isStationedOnGround())
        {
            reference.CT.setMagnetoControl(3);
            setControlMagneto(3);
            stage = 1;
            bRan = false;
            timer = com.maddox.rts.Time.current();
            return;
        }
        if(stage == 0)
        {
            reference.CT.setMagnetoControl(3);
            setControlMagneto(3);
            stage = 1;
            timer = com.maddox.rts.Time.current();
        }
    }

    public void setEngineStops(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(actor))
            return;
        if(stage < 1 || stage > 6)
        {
            return;
        } else
        {
            reference.AS.setEngineStops(number);
            return;
        }
    }

    public void doSetEngineStops()
    {
        if(stage != 0)
        {
            stage = 0;
            setControlMagneto(0);
            timer = com.maddox.rts.Time.current();
        }
    }

    public void setEngineDies(com.maddox.il2.engine.Actor actor)
    {
        if(stage > 6)
        {
            return;
        } else
        {
            reference.AS.setEngineDies(reference.actor, number);
            return;
        }
    }

    public void doSetEngineDies()
    {
        if(stage < 7)
        {
            bIsInoperable = true;
            reference.setCapableOfTaxiing(false);
            reference.setCapableOfACM(false);
            doSetReadyness(0.0F);
            float f = 0.0F;
            int i = reference.EI.getNum();
            if(i != 0)
            {
                for(int j = 0; j < i; j++)
                    f += reference.EI.engines[j].getReadyness() / (float)i;

                if(f < 0.7F)
                    reference.setReadyToReturn(true);
                if(f < 0.3F)
                    reference.setReadyToDie(true);
            }
            stage = 7;
            if(reference.isPlayers())
                com.maddox.il2.game.HUD.log("FailedEngine");
            timer = com.maddox.rts.Time.current();
        }
    }

    public void setEngineRunning(com.maddox.il2.engine.Actor actor)
    {
        if(!bIsMaster || !com.maddox.il2.engine.Actor.isAlive(actor))
        {
            return;
        } else
        {
            reference.AS.setEngineRunning(number);
            return;
        }
    }

    public void doSetEngineRunning()
    {
        if(stage >= 6)
            return;
        stage = 6;
        reference.CT.setMagnetoControl(3);
        setControlMagneto(3);
        if(reference.isPlayers())
            com.maddox.il2.game.HUD.log("EngineI1");
        w = wMax * 0.75F;
        tWaterOut = 0.5F * (tWaterCritMin + tWaterMaxRPM);
        tOilOut = 0.5F * (tOilCritMin + tOilOutMaxRPM);
        tOilIn = 0.5F * (tOilCritMin + tOilInMaxRPM);
        propPhi = 0.5F * (propPhiMin + propPhiMax);
        propTarget = propPhi;
        if(isnd != null)
            isnd.onEngineState(stage);
    }

    public void setKillCompressor(com.maddox.il2.engine.Actor actor)
    {
        reference.AS.setEngineSpecificDamage(actor, number, 0);
    }

    public void doSetKillCompressor()
    {
        switch(compressorType)
        {
        default:
            break;

        case 2: // '\002'
            compressorAltitudes[0] = 50F;
            compressorAltMultipliers[0] = 1.0F;
            break;

        case 1: // '\001'
            for(int i = 0; i < compressorMaxStep; i++)
            {
                compressorAltitudes[i] = 50F;
                compressorAltMultipliers[i] = 1.0F;
            }

            break;
        }
    }

    public void setKillPropAngleDevice(com.maddox.il2.engine.Actor actor)
    {
        reference.AS.setEngineSpecificDamage(actor, number, 3);
    }

    public void doSetKillPropAngleDevice()
    {
        bIsAngleDeviceOperational = false;
    }

    public void setKillPropAngleDeviceSpeeds(com.maddox.il2.engine.Actor actor)
    {
        reference.AS.setEngineSpecificDamage(actor, number, 4);
    }

    public void doSetKillPropAngleDeviceSpeeds()
    {
        isPropAngleDeviceHydroOperable = false;
    }

    public void setCyliderKnockOut(com.maddox.il2.engine.Actor actor, int i)
    {
        reference.AS.setEngineCylinderKnockOut(actor, number, i);
    }

    public void doSetCyliderKnockOut(int i)
    {
        cylindersOperable -= i;
        if(cylindersOperable < 0)
            cylindersOperable = 0;
        if(bIsMaster)
            if(getCylindersRatio() < 0.12F)
                setEngineDies(reference.actor);
            else
            if(getCylindersRatio() < getReadyness())
                setReadyness(reference.actor, getCylindersRatio());
    }

    public void setMagnetoKnockOut(com.maddox.il2.engine.Actor actor, int i)
    {
        reference.AS.setEngineMagnetoKnockOut(reference.actor, number, i);
    }

    public void doSetMagnetoKnockOut(int i)
    {
        bMagnetos[i] = false;
        if(i == controlMagneto)
            setEngineStops(reference.actor);
    }

    public void setEngineStuck(com.maddox.il2.engine.Actor actor)
    {
        reference.AS.setEngineStuck(actor, number);
    }

    public void doSetEngineStuck()
    {
        bIsInoperable = true;
        reference.setCapableOfTaxiing(false);
        reference.setCapableOfACM(false);
        if(stage != 8)
        {
            setReadyness(0.0F);
            if(reference.isPlayers() && stage != 7)
                com.maddox.il2.game.HUD.log("FailedEngine");
            stage = 8;
            timer = com.maddox.rts.Time.current();
        }
    }

    public void setw(float f)
    {
        w = f;
        rpm = toRPM(w);
    }

    public void setPropPhi(float f)
    {
        propPhi = f;
    }

    public void setEngineMomentMax(float f)
    {
        engineMomentMax = f;
    }

    public void setPos(com.maddox.JGP.Point3d point3d)
    {
        enginePos.set(point3d);
    }

    public void setPropPos(com.maddox.JGP.Point3d point3d)
    {
        propPos.set(point3d);
    }

    public void setVector(com.maddox.JGP.Vector3f vector3f)
    {
        engineVector.set(vector3f);
        engineVector.normalize();
    }

    public void setControlThrottle(float f)
    {
        if(bHasThrottleControl)
        {
            if(afterburnerType == 4)
            {
                if(f > 1.0F && controlThrottle <= 1.0F && reference.M.requestNitro(0.0001F))
                {
                    reference.CT.setAfterburnerControl(true);
                    setControlAfterburner(true);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);
                        com.maddox.il2.game.HUD.logRightBottom("BoostWepTP4");
                    }
                }
                if(f < 1.0F && controlThrottle >= 1.0F)
                {
                    reference.CT.setAfterburnerControl(false);
                    setControlAfterburner(false);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);
                        com.maddox.il2.game.HUD.logRightBottom(null);
                    }
                }
            } else
            if(afterburnerType == 8)
            {
                if(f > 1.0F && controlThrottle <= 1.0F)
                {
                    reference.CT.setAfterburnerControl(true);
                    setControlAfterburner(true);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);
                        com.maddox.il2.game.HUD.logRightBottom("BoostWepTP7");
                    }
                }
                if(f < 1.0F && controlThrottle >= 1.0F)
                {
                    reference.CT.setAfterburnerControl(false);
                    setControlAfterburner(false);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);
                        com.maddox.il2.game.HUD.logRightBottom(null);
                    }
                }
            } else
            if(afterburnerType == 10)
            {
                if(f > 1.0F && controlThrottle <= 1.0F)
                {
                    reference.CT.setAfterburnerControl(true);
                    setControlAfterburner(true);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);
                        com.maddox.il2.game.HUD.logRightBottom("BoostWepTP0");
                    }
                }
                if(f < 1.0F && controlThrottle >= 1.0F)
                {
                    reference.CT.setAfterburnerControl(false);
                    setControlAfterburner(false);
                    if(reference.isPlayers())
                    {
                        com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);
                        com.maddox.il2.game.HUD.logRightBottom(null);
                    }
                }
            }
            controlThrottle = f;
        }
    }

    public void setControlAfterburner(boolean flag)
    {
        if(bHasAfterburnerControl)
        {
            if(afterburnerType == 1 && !controlAfterburner && flag && controlThrottle > 1.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
                setCyliderKnockOut(reference.actor, com.maddox.il2.ai.World.Rnd().nextInt(0, 3));
            controlAfterburner = flag;
        }
        if(afterburnerType == 4 || afterburnerType == 8 || afterburnerType == 10)
            controlAfterburner = flag;
    }

    public void doSetKillControlThrottle()
    {
        bHasThrottleControl = false;
    }

    public void setControlPropDelta(int i)
    {
        controlPropDirection = i;
    }

    public int getControlPropDelta()
    {
        return controlPropDirection;
    }

    public void doSetKillControlAfterburner()
    {
        bHasAfterburnerControl = false;
    }

    public void setControlProp(float f)
    {
        if(bHasPropControl)
            controlProp = f;
    }

    public void setControlPropAuto(boolean flag)
    {
        if(bHasPropControl)
            bControlPropAuto = flag && isAllowsAutoProp();
    }

    public void doSetKillControlProp()
    {
        bHasPropControl = false;
    }

    public void setControlMix(float f)
    {
        if(bHasMixControl)
            switch(mixerType)
            {
            case 0: // '\0'
                controlMix = f;
                break;

            case 1: // '\001'
                controlMix = f;
                if(controlMix < 1.0F)
                    controlMix = 1.0F;
                break;

            default:
                controlMix = f;
                break;
            }
    }

    public void doSetKillControlMix()
    {
        bHasMixControl = false;
    }

    public void setControlMagneto(int i)
    {
        if(bHasMagnetoControl)
        {
            controlMagneto = i;
            if(i == 0)
                setEngineStops(reference.actor);
        }
    }

    public void setControlCompressor(int i)
    {
        if(bHasCompressorControl)
            controlCompressor = i;
    }

    public void setControlFeather(int i)
    {
        if(bHasFeatherControl)
        {
            controlFeather = i;
            if(reference.isPlayers())
                com.maddox.il2.game.HUD.log("EngineFeather" + controlFeather);
        }
    }

    public void setControlRadiator(float f)
    {
        if(bHasRadiatorControl)
            controlRadiator = f;
    }

    public void setExtinguisherFire()
    {
        if(!bIsMaster)
            return;
        if(bHasExtinguisherControl)
        {
            reference.AS.setEngineSpecificDamage(reference.actor, number, 5);
            if(reference.AS.astateEngineStates[number] > 2)
                reference.AS.setEngineState(reference.actor, number, com.maddox.il2.ai.World.Rnd().nextInt(1, 2));
            else
            if(reference.AS.astateEngineStates[number] > 0)
                reference.AS.setEngineState(reference.actor, number, 0);
        }
    }

    public void doSetExtinguisherFire()
    {
        if(!bHasExtinguisherControl)
            return;
        extinguishers--;
        if(extinguishers == 0)
            bHasExtinguisherControl = false;
        reference.AS.doSetEngineExtinguisherVisuals(number);
        if(bIsMaster)
        {
            if(reference.AS.astateEngineStates[number] > 1 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.56F)
                reference.AS.repairEngine(number);
            if(reference.AS.astateEngineStates[number] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.21F)
            {
                reference.AS.repairEngine(number);
                reference.AS.repairEngine(number);
            }
            tWaterOut -= 4F;
            tOilIn -= 4F;
            tOilOut -= 4F;
        }
        if(reference.isPlayers())
            com.maddox.il2.game.HUD.log("ExtinguishersFired");
    }

    private void computeStage(float f)
    {
        if(stage == 6)
            return;
        bTFirst = false;
        float f1 = 20F;
        long l = com.maddox.rts.Time.current() - timer;
        if(stage > 0 && stage < 6 && l > given)
        {
            stage++;
            timer = com.maddox.rts.Time.current();
            l = 0L;
        }
        if(oldStage != stage)
        {
            bTFirst = true;
            oldStage = stage;
        }
        if(stage > 0 && stage < 6)
            setControlThrottle(0.2F);
label0:
        switch(stage)
        {
        case 0: // '\0'
            if(bTFirst)
            {
                given = 0x3fffffffffffffffL;
                timer = com.maddox.rts.Time.current();
            }
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        case 1: // '\001'
            if(bTFirst)
            {
                if(bIsStuck)
                {
                    stage = 8;
                    return;
                }
                if(type == 3 || type == 4 || type == 6)
                {
                    stage = 5;
                    if(reference.isPlayers())
                        com.maddox.il2.game.HUD.log("Starting_Engine");
                    return;
                }
                if(type == 0 || type == 1 || type == 7)
                {
                    if(w > wMin)
                    {
                        stage = 3;
                        if(reference.isPlayers())
                            com.maddox.il2.game.HUD.log("Starting_Engine");
                        return;
                    }
                    if(!bIsAutonomous)
                    {
                        if(com.maddox.il2.ai.Airport.distToNearestAirport(reference.Loc) < 1200D && reference.isStationedOnGround())
                        {
                            setControlMagneto(3);
                            if(reference.isPlayers())
                                com.maddox.il2.game.HUD.log("Starting_Engine");
                        } else
                        {
                            doSetEngineStops();
                            if(reference.isPlayers())
                                com.maddox.il2.game.HUD.log("EngineI0");
                            return;
                        }
                    } else
                    if(reference.isPlayers())
                        com.maddox.il2.game.HUD.log("Starting_Engine");
                } else
                if(!bIsAutonomous)
                {
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(reference.Loc) < 1200D && reference.isStationedOnGround())
                    {
                        setControlMagneto(3);
                        if(reference.isPlayers())
                            com.maddox.il2.game.HUD.log("Starting_Engine");
                    } else
                    {
                        if(reference.getSpeedKMH() < 350F)
                        {
                            doSetEngineStops();
                            if(reference.isPlayers())
                                com.maddox.il2.game.HUD.log("EngineI0");
                            return;
                        }
                        if(reference.isPlayers())
                            com.maddox.il2.game.HUD.log("Starting_Engine");
                    }
                } else
                if(reference.isPlayers())
                    com.maddox.il2.game.HUD.log("Starting_Engine");
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
            }
            if(isnd != null)
                isnd.onEngineState(stage);
            reference.CT.setMagnetoControl(3);
            setControlMagneto(3);
            w = 0.1047F * ((20F * (float)l) / (float)given);
            setControlThrottle(0.0F);
            break;

        case 2: // '\002'
            if(bTFirst)
            {
                given = (long)(4000F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(bRan)
                {
                    given = (long)(100F + ((tOilOutMaxRPM - tOilOut) / (tOilOutMaxRPM - f1)) * 7900F * com.maddox.il2.ai.World.Rnd().nextFloat(2.0F, 4.2F));
                    if(given > 9000L)
                        given = com.maddox.il2.ai.World.Rnd().nextLong(7800L, 9600L);
                    if(bIsMaster && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        stage = 0;
                        reference.AS.setEngineStops(number);
                    }
                }
            }
            w = 0.1047F * (20F + (15F * (float)l) / (float)given);
            setControlThrottle(0.0F);
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        case 3: // '\003'
            if(bTFirst)
            {
                if(isnd != null)
                    isnd.onEngineState(stage);
                if(bIsInoperable)
                {
                    stage = 0;
                    doSetEngineDies();
                    return;
                }
                given = (long)(50F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(bIsMaster && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F && (tOilOutMaxRPM - tOilOut) / (tOilOutMaxRPM - f1) < 0.75F)
                    reference.AS.setEngineStops(number);
            }
            w = 0.1047F * (60F + (60F * (float)l) / (float)given);
            setControlThrottle(0.0F);
            if(reference == null || type == 2 || type == 3 || type == 4 || type == 6 || type == 5)
                break;
            int i = 1;
            do
            {
                if(i >= 32)
                    break label0;
                try
                {
                    com.maddox.il2.engine.Hook hook = reference.actor.findHook("_Engine" + (number + 1) + "EF_" + (i >= 10 ? "" + i : "0" + i));
                    if(hook != null)
                        com.maddox.il2.engine.Eff3DActor.New(reference.actor, hook, null, 1.0F, "3DO/Effects/Aircraft/EngineStart" + com.maddox.il2.ai.World.Rnd().nextInt(1, 3) + ".eff", -1F);
                }
                catch(java.lang.Exception exception) { }
                i++;
            } while(true);

        case 4: // '\004'
            if(bTFirst)
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
            w = 12.564F;
            setControlThrottle(0.0F);
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        case 5: // '\005'
            if(bTFirst)
            {
                given = (long)(500F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F));
                if(bRan && (type == 0 || type == 1 || type == 7))
                {
                    if((tOilOutMaxRPM - tOilOut) / (tOilOutMaxRPM - f1) > 0.75F)
                        if(type == 0 || type == 7)
                        {
                            if(bIsMaster && getReadyness() > 0.75F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                                setReadyness(getReadyness() - 0.05F);
                        } else
                        if(type == 1 && bIsMaster && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            reference.AS.setEngineDies(reference.actor, number);
                    if(bIsMaster && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        reference.AS.setEngineStops(number);
                }
                bRan = true;
            }
            w = 0.1047F * (120F + (120F * (float)l) / (float)given);
            setControlThrottle(0.2F);
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        case 6: // '\006'
            if(bTFirst)
            {
                given = -1L;
                reference.AS.setEngineRunning(number);
            }
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        case 7: // '\007'
        case 8: // '\b'
            if(bTFirst)
                given = -1L;
            setReadyness(0.0F);
            setControlMagneto(0);
            if(isnd != null)
                isnd.onEngineState(stage);
            break;

        default:
            return;
        }
    }

    private void computeFuel(float f)
    {
        tmpF = 0.0F;
        float f2 = w * _1_wMax;
        if(stage == 6)
        {
            float f1;
            double d;
            switch(type)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 7: // '\007'
                d = momForFuel * (double)w * 0.0010499999999999999D;
                f1 = (float)d / horsePowers;
                if(d < (double)horsePowers * 0.050000000000000003D)
                    d = (double)horsePowers * 0.050000000000000003D;
                break;

            default:
                d = thrustMax * (f1 = getPowerOutput());
                if(d < (double)thrustMax * 0.050000000000000003D)
                    d = (double)thrustMax * 0.050000000000000003D;
                break;
            }
            if(f1 < 0.0F)
                f1 = 0.0F;
            double d1;
            if(f1 <= 0.5F)
                d1 = (double)FuelConsumptionP0 + (double)(FuelConsumptionP05 - FuelConsumptionP0) * (2D * (double)f1);
            else
            if((double)f1 <= 1.0D)
            {
                d1 = (double)FuelConsumptionP05 + (double)(FuelConsumptionP1 - FuelConsumptionP05) * (2D * ((double)f1 - 0.5D));
            } else
            {
                float f3 = f1 - 1.0F;
                if(f3 > 0.1F)
                    f3 = 0.1F;
                f3 *= 10F;
                d1 = FuelConsumptionP1 + (FuelConsumptionPMAX - FuelConsumptionP1) * f3;
            }
            d1 /= 3600D;
            switch(type)
            {
            case 5: // '\005'
            default:
                break;

            case 0: // '\0'
            case 1: // '\001'
            case 7: // '\007'
                float f4 = (float)(d1 * d);
                tmpF = f4 * f;
                double d2 = f4 * 4.4E+007F;
                double d3 = f4 * 15.7F;
                double d4 = 1010D * d3 * 700D;
                d *= 746D;
                Ptermo = (float)(d2 - d - d4);
                break;

            case 2: // '\002'
                tmpF = (float)(d1 * d * (double)f);
                break;

            case 3: // '\003'
                if((reference.actor instanceof com.maddox.il2.objects.air.BI_1) || (reference.actor instanceof com.maddox.il2.objects.air.BI_6))
                {
                    tmpF = 1.8F * getPowerOutput() * f;
                    break;
                }
                if(reference.actor instanceof com.maddox.il2.objects.air.MXY_7)
                    tmpF = 0.5F * getPowerOutput() * f;
                else
                    tmpF = 2.5777F * getPowerOutput() * f;
                break;

            case 4: // '\004'
                tmpF = 1.432056F * getPowerOutput() * f;
                tmpB = reference.M.requestNitro(tmpF);
                tmpF = 0.0F;
                if(tmpB || !bIsMaster)
                    break;
                setEngineStops(reference.actor);
                if(reference.isPlayers() && engineNoFuelHUDLogId == -1)
                {
                    engineNoFuelHUDLogId = com.maddox.il2.game.HUD.makeIdLog();
                    com.maddox.il2.game.HUD.log(engineNoFuelHUDLogId, "EngineNoFuel");
                }
                return;

            case 6: // '\006'
                tmpF = (float)(d1 * d * (double)f);
                tmpB = reference.M.requestNitro(tmpF);
                tmpF = 0.0F;
                if(tmpB || !bIsMaster)
                    break;
                setEngineStops(reference.actor);
                if(reference.isPlayers() && engineNoFuelHUDLogId == -1)
                {
                    engineNoFuelHUDLogId = com.maddox.il2.game.HUD.makeIdLog();
                    com.maddox.il2.game.HUD.log(engineNoFuelHUDLogId, "EngineNoFuel");
                }
                return;
            }
        }
        tmpB = reference.M.requestFuel(tmpF);
        if(!tmpB && bIsMaster)
        {
            setEngineStops(reference.actor);
            reference.setCapableOfACM(false);
            reference.setCapableOfTaxiing(false);
            if(reference.isPlayers() && engineNoFuelHUDLogId == -1)
            {
                engineNoFuelHUDLogId = com.maddox.il2.game.HUD.makeIdLog();
                com.maddox.il2.game.HUD.log(engineNoFuelHUDLogId, "EngineNoFuel");
            }
        }
        if(controlAfterburner)
            switch(afterburnerType)
            {
            case 3: // '\003'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
            default:
                break;

            case 1: // '\001'
                if(controlThrottle > 1.0F && !reference.M.requestNitro(0.044872F * f) && reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && com.maddox.il2.ai.World.cur().diffCur.Vulnerability)
                    setReadyness(reference.actor, getReadyness() - 0.01F * f);
                break;

            case 2: // '\002'
                if(reference.M.requestNitro(0.044872F * f));
                break;

            case 5: // '\005'
                if(reference.M.requestNitro(0.044872F * f));
                break;

            case 9: // '\t'
                if(reference.M.requestNitro(0.044872F * f));
                break;

            case 4: // '\004'
                if(reference.M.requestNitro(0.044872F * f))
                    break;
                reference.CT.setAfterburnerControl(false);
                if(reference.isPlayers())
                {
                    com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);
                    com.maddox.il2.game.HUD.logRightBottom(null);
                }
                break;
            }
    }

    private void computeReliability(float f)
    {
        if(stage != 6)
            return;
        float f1 = controlThrottle;
        if(engineBoostFactor > 1.0F)
            f1 *= 0.9090909F;
        switch(type)
        {
        default:
            zatizeni = f1;
            zatizeni = zatizeni * zatizeni;
            zatizeni = zatizeni * zatizeni;
            zatizeni *= (double)f * 6.1984262178699901E-005D;
            if(zatizeni > com.maddox.il2.ai.World.Rnd().nextDouble(0.0D, 1.0D))
            {
                int i = com.maddox.il2.ai.World.Rnd().nextInt(0, 9);
                if(i < 2)
                {
                    reference.AS.hitEngine(reference.actor, number, 3);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - smoke");
                } else
                {
                    setCyliderKnockOut(reference.actor, com.maddox.il2.ai.World.Rnd().nextInt(0, 3));
                    com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - power loss");
                }
            }
            break;

        case 0: // '\0'
        case 1: // '\001'
        case 7: // '\007'
            zatizeni = coolMult * f1;
            zatizeni *= w / wWEP;
            zatizeni = zatizeni * zatizeni;
            zatizeni = zatizeni * zatizeni;
            double d = zatizeni * (double)f * 1.4248134284734321E-005D;
            if(d <= com.maddox.il2.ai.World.Rnd().nextDouble(0.0D, 1.0D))
                break;
            int j = com.maddox.il2.ai.World.Rnd().nextInt(0, 19);
            if(j < 10)
            {
                reference.AS.setEngineCylinderKnockOut(reference.actor, number, 1);
                com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - cylinder");
                break;
            }
            if(j < 12)
            {
                if(j < 11)
                {
                    reference.AS.setEngineMagnetoKnockOut(reference.actor, number, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - mag1");
                } else
                {
                    reference.AS.setEngineMagnetoKnockOut(reference.actor, number, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - mag2");
                }
                break;
            }
            if(j < 14)
            {
                reference.AS.setEngineDies(reference.actor, number);
                com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - dead");
                break;
            }
            if(j < 15)
            {
                reference.AS.setEngineStuck(reference.actor, number);
                com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - stuck");
                break;
            }
            if(j < 17)
            {
                setKillPropAngleDevice(reference.actor);
                com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - propAngler");
            } else
            {
                reference.AS.hitOil(reference.actor, number);
                com.maddox.il2.objects.air.Aircraft.debugprintln(reference.actor, "Malfunction #" + number + " - oil");
            }
            break;
        }
    }

    private void computeTemperature(float f)
    {
        float f5 = com.maddox.il2.fm.Pitot.Indicator((float)reference.Loc.z, reference.getSpeedKMH());
        float f1 = com.maddox.il2.fm.Atmosphere.temperature((float)reference.Loc.z) - 273.15F;
        if(stage == 6)
        {
            float f2 = 1.05F * (float)java.lang.Math.sqrt(java.lang.Math.sqrt(getPowerOutput() <= 0.2F ? 0.2F : getPowerOutput() + (float)reference.AS.astateOilStates[number] * 0.33F)) * (float)java.lang.Math.sqrt(w / wMax <= 0.75F ? 0.75D : w / wMax) * tOilOutMaxRPM * (1.0F - 0.11F * controlRadiator) * (1.0F - f5 * 0.0002F) + 22F;
            if(getPowerOutput() > 1.0F)
                f2 *= getPowerOutput();
            tOilOut += (f2 - tOilOut) * f * tChangeSpeed;
        } else
        {
            float f3 = (w / wMax) * tOilOutMaxRPM * (1.0F - 0.2F * controlRadiator) + f1;
            tOilOut += (f3 - tOilOut) * f * tChangeSpeed * (type != 0 ? 1.07F : 0.42F);
        }
        float f6 = 0.8F - 0.05F * controlRadiator;
        float f4 = tOilOut * (f6 - f5 * 0.0005F) + f1 * ((1.0F - f6) + f5 * 0.0005F);
        tOilIn += (f4 - tOilIn) * f * tChangeSpeed * 0.5F;
        f4 = 1.05F * (float)java.lang.Math.sqrt(getPowerOutput()) * (1.0F - f5 * 0.0002F) * tWaterMaxRPM * (controlAfterburner ? 1.1F : 1.0F) + f1;
        tWaterOut += (f4 - tWaterOut) * f * tChangeSpeed * (tWaterOut >= 50F ? 1.0F : 0.4F) * (1.0F - f5 * 0.0006F);
        if(tOilOut < f1)
            tOilOut = f1;
        if(tOilIn < f1)
            tOilIn = f1;
        if(tWaterOut < f1)
            tWaterOut = f1;
        if(com.maddox.il2.ai.World.cur().diffCur.Engine_Overheat && (tWaterOut > tWaterCritMax || tOilOut > tOilCritMax))
        {
            if(heatStringID == -1)
                heatStringID = com.maddox.il2.game.HUD.makeIdLog();
            if(reference.isPlayers())
                com.maddox.il2.game.HUD.log(heatStringID, "EngineOverheat");
            timeCounter += f;
            if(timeCounter > timeOverheat)
                if(readyness > 0.32F)
                {
                    setReadyness(readyness - 0.00666F * f);
                    tOilCritMax -= 0.00666F * f * (tOilCritMax - tOilOutMaxRPM);
                } else
                {
                    setEngineDies(reference.actor);
                }
        } else
        if(timeCounter > 0.0F)
        {
            timeCounter = 0.0F;
            if(heatStringID == -1)
                heatStringID = com.maddox.il2.game.HUD.makeIdLog();
            if(reference.isPlayers())
                com.maddox.il2.game.HUD.log(heatStringID, "EngineRestored");
        }
    }

    public void updateRadiator(float f)
    {
        if(reference.actor instanceof com.maddox.il2.objects.air.GLADIATOR)
        {
            controlRadiator = 0.0F;
            return;
        }
        if((reference.actor instanceof com.maddox.il2.objects.air.P_51) || (reference.actor instanceof com.maddox.il2.objects.air.P_38) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_3) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_3P) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9M) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9U) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9UT) || (reference.actor instanceof com.maddox.il2.objects.air.P_63C))
        {
            if(tOilOut > tOilOutMaxRPM)
            {
                controlRadiator += 0.1F * f;
                if(controlRadiator > 1.0F)
                    controlRadiator = 1.0F;
            } else
            {
                controlRadiator = 1.0F - reference.getSpeed() / reference.VmaxH;
                if(controlRadiator < 0.0F)
                    controlRadiator = 0.0F;
            }
            return;
        }
        if((reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE9) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8CLP))
        {
            float f1 = 0.0F;
            if(tOilOut > tOilCritMin)
            {
                float f2 = tOilCritMax - tOilCritMin;
                f1 = (1.4F * (tOilOut - tOilCritMin)) / f2;
                if(f1 > 1.4F)
                    f1 = 1.4F;
            }
            float f3 = 0.0F;
            if(tWaterOut > tWaterCritMin)
            {
                float f4 = tWaterCritMax - tWaterCritMin;
                f3 = (1.4F * (tWaterOut - tWaterCritMin)) / f4;
                if(f3 > 1.4F)
                    f3 = 1.4F;
            }
            float f5 = java.lang.Math.max(f1, f3);
            float f6 = 1.0F;
            float f7 = reference.getSpeed();
            if(f7 > reference.Vmin * 1.5F)
            {
                float f8 = reference.Vmax - reference.Vmin * 1.5F;
                f6 = 1.0F - (1.65F * (f7 - reference.Vmin * 1.5F)) / f8;
                if(f6 < -1F)
                    f6 = -1F;
            }
            controlRadiator = 0.5F * (f5 + f6);
            if(tWaterOut > tWaterCritMax || tOilOut > tOilCritMax)
                controlRadiator += 0.05F * timeCounter;
            if(controlRadiator < 0.0F)
                controlRadiator = 0.0F;
            if(controlRadiator > 1.0F)
                controlRadiator = 1.0F;
            return;
        }
        if(reference.actor instanceof com.maddox.il2.objects.air.GLADIATOR)
        {
            controlRadiator = 0.0F;
            return;
        }
        switch(propAngleDeviceType)
        {
        case 3: // '\003'
        case 4: // '\004'
        default:
            controlRadiator = 1.0F - getPowerOutput();
            break;

        case 5: // '\005'
        case 6: // '\006'
            controlRadiator = 1.0F - reference.getSpeed() / reference.VmaxH;
            if(controlRadiator < 0.0F)
                controlRadiator = 0.0F;
            break;

        case 1: // '\001'
        case 2: // '\002'
            if(controlRadiator > 1.0F - getPowerOutput())
            {
                controlRadiator -= 0.15F * f;
                if(controlRadiator < 0.0F)
                    controlRadiator = 0.0F;
            } else
            {
                controlRadiator += 0.15F * f;
            }
            break;

        case 8: // '\b'
            if(type == 0)
            {
                if(tOilOut > tOilOutMaxRPM)
                {
                    controlRadiator += 0.1F * f;
                    if(controlRadiator > 1.0F)
                        controlRadiator = 1.0F;
                    break;
                }
                if(tOilOut >= tOilOutMaxRPM - 10F)
                    break;
                controlRadiator -= 0.1F * f;
                if(controlRadiator < 0.0F)
                    controlRadiator = 0.0F;
                break;
            }
            if(controlRadiator > 1.0F - getPowerOutput())
            {
                controlRadiator -= 0.15F * f;
                if(controlRadiator < 0.0F)
                    controlRadiator = 0.0F;
            } else
            {
                controlRadiator += 0.15F * f;
            }
            break;

        case 7: // '\007'
            if(tOilOut > tOilOutMaxRPM)
            {
                controlRadiator += 0.1F * f;
                if(controlRadiator > 1.0F)
                    controlRadiator = 1.0F;
                break;
            }
            controlRadiator = 1.0F - reference.getSpeed() / reference.VmaxH;
            if(controlRadiator < 0.0F)
                controlRadiator = 0.0F;
            break;
        }
    }

    private void computeForces(float f)
    {
        switch(type)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 7: // '\007'
            if(java.lang.Math.abs(w) < 1E-005F)
                propPhiW = 1.570796F;
            else
            if(type == 7)
                propPhiW = (float)java.lang.Math.atan(java.lang.Math.abs(reference.Vflow.x) / (double)(w * propReductor * propr));
            else
                propPhiW = (float)java.lang.Math.atan(reference.Vflow.x / (double)(w * propReductor * propr));
            propAoA = propPhi - propPhiW;
            if(type == 7)
                computePropForces(w * propReductor, (float)java.lang.Math.abs(reference.Vflow.x), propPhi, propAoA, reference.getAltitude());
            else
                computePropForces(w * propReductor, (float)reference.Vflow.x, propPhi, propAoA, reference.getAltitude());
            switch(propAngleDeviceType)
            {
            case 3: // '\003'
            case 4: // '\004'
                float f9 = controlThrottle;
                if(f9 > 1.0F)
                    f9 = 1.0F;
                compressorManifoldThreshold = 0.5F + (compressorRPMtoWMaxATA - 0.5F) * f9;
                if(isPropAngleDeviceOperational())
                {
                    if(bControlPropAuto)
                        propTarget = propPhiW + propAoA0;
                    else
                        propTarget = propPhiMax - controlProp * (propPhiMax - propPhiMin);
                } else
                if(propAngleDeviceType == 3)
                    propTarget = 0.0F;
                else
                    propTarget = 3.141593F;
                break;

            case 9: // '\t'
                if(bControlPropAuto)
                {
                    float f16 = propAngleDeviceMaxParam;
                    if(controlAfterburner)
                        f16 = propAngleDeviceAfterburnerParam;
                    controlProp += ((float)controlPropDirection * f) / 5F;
                    if(controlProp > 1.0F)
                        controlProp = 1.0F;
                    else
                    if(controlProp < 0.0F)
                        controlProp = 0.0F;
                    float f22 = propAngleDeviceMinParam + (f16 - propAngleDeviceMinParam) * controlProp;
                    float f26 = controlThrottle;
                    if(f26 > 1.0F)
                        f26 = 1.0F;
                    compressorManifoldThreshold = getATA(toRPM(propAngleDeviceMinParam + (propAngleDeviceMaxParam - propAngleDeviceMinParam) * f26));
                    if(isPropAngleDeviceOperational())
                    {
                        if(w < f22)
                        {
                            f22 = java.lang.Math.min(1.0F, 0.01F * (f22 - w) - 0.012F * aw);
                            propTarget -= f22 * getPropAngleDeviceSpeed() * f;
                        } else
                        {
                            f22 = java.lang.Math.min(1.0F, 0.01F * (w - f22) + 0.012F * aw);
                            propTarget += f22 * getPropAngleDeviceSpeed() * f;
                        }
                        if(stage == 6 && propTarget < propPhiW - 0.12F)
                        {
                            propTarget = propPhiW - 0.12F;
                            if(propPhi < propTarget)
                                propPhi += 0.2F * f;
                        }
                    } else
                    {
                        propTarget = propPhi;
                    }
                } else
                {
                    compressorManifoldThreshold = 0.5F + (compressorRPMtoWMaxATA - 0.5F) * (controlThrottle <= 1.0F ? controlThrottle : 1.0F);
                    propTarget = propPhi;
                    if(isPropAngleDeviceOperational())
                        if(controlPropDirection > 0)
                            propTarget = propPhiMin;
                        else
                        if(controlPropDirection < 0)
                            propTarget = propPhiMax;
                }
                break;

            case 1: // '\001'
            case 2: // '\002'
                if(bControlPropAuto)
                    if(engineBoostFactor > 1.0F)
                        controlProp = 0.75F + 0.227272F * controlThrottle;
                    else
                        controlProp = 0.75F + 0.25F * controlThrottle;
                float f17 = propAngleDeviceMaxParam;
                if(controlAfterburner && (!bWepRpmInLowGear || controlCompressor != compressorMaxStep))
                    f17 = propAngleDeviceAfterburnerParam;
                float f1 = propAngleDeviceMinParam + (f17 - propAngleDeviceMinParam) * controlProp;
                float f10 = controlThrottle;
                if(f10 > 1.0F)
                    f10 = 1.0F;
                compressorManifoldThreshold = getATA(toRPM(propAngleDeviceMinParam + (propAngleDeviceMaxParam - propAngleDeviceMinParam) * f10));
                if(isPropAngleDeviceOperational())
                {
                    if(w < f1)
                    {
                        f1 = java.lang.Math.min(1.0F, 0.01F * (f1 - w) - 0.012F * aw);
                        propTarget -= f1 * getPropAngleDeviceSpeed() * f;
                    } else
                    {
                        f1 = java.lang.Math.min(1.0F, 0.01F * (w - f1) + 0.012F * aw);
                        propTarget += f1 * getPropAngleDeviceSpeed() * f;
                    }
                    if(stage == 6 && propTarget < propPhiW - 0.12F)
                    {
                        propTarget = propPhiW - 0.12F;
                        if(propPhi < propTarget)
                            propPhi += 0.2F * f;
                    }
                } else
                if(propAngleDeviceType == 1)
                    propTarget = 0.0F;
                else
                    propTarget = 1.5708F;
                break;

            case 7: // '\007'
                float f23 = controlThrottle;
                if(engineBoostFactor > 1.0F)
                    f23 = 0.9090909F * controlThrottle;
                float f18 = propAngleDeviceMaxParam;
                if(controlAfterburner)
                    if(afterburnerType == 1)
                    {
                        if(controlThrottle > 1.0F)
                            f18 = propAngleDeviceMaxParam + 10F * (controlThrottle - 1.0F) * (propAngleDeviceAfterburnerParam - propAngleDeviceMaxParam);
                    } else
                    {
                        f18 = propAngleDeviceAfterburnerParam;
                    }
                float f2 = propAngleDeviceMinParam + (f18 - propAngleDeviceMinParam) * f23;
                float f11 = controlThrottle;
                if(f11 > 1.0F)
                    f11 = 1.0F;
                compressorManifoldThreshold = getATA(toRPM(propAngleDeviceMinParam + (f18 - propAngleDeviceMinParam) * f11));
                if(isPropAngleDeviceOperational())
                    if(bControlPropAuto)
                    {
                        if(w < f2)
                        {
                            f2 = java.lang.Math.min(1.0F, 0.01F * (f2 - w) - 0.012F * aw);
                            propTarget -= f2 * getPropAngleDeviceSpeed() * f;
                        } else
                        {
                            f2 = java.lang.Math.min(1.0F, 0.01F * (w - f2) + 0.012F * aw);
                            propTarget += f2 * getPropAngleDeviceSpeed() * f;
                        }
                        if(stage == 6 && propTarget < propPhiW - 0.12F)
                        {
                            propTarget = propPhiW - 0.12F;
                            if(propPhi < propTarget)
                                propPhi += 0.2F * f;
                        }
                        if(propTarget < propPhiMin + (float)java.lang.Math.toRadians(3D))
                            propTarget = propPhiMin + (float)java.lang.Math.toRadians(3D);
                    } else
                    {
                        propTarget = (1.0F - f * 0.1F) * propTarget + f * 0.1F * (propPhiMax - controlProp * (propPhiMax - propPhiMin));
                        if(w > 1.02F * wMax)
                            wMaxAllowed = (1.0F - 4E-007F * (w - 1.02F * wMax)) * wMaxAllowed;
                        if(w > wMax)
                        {
                            float f27 = w - wMax;
                            f27 *= f27;
                            float f28 = 1.0F - 0.001F * f27;
                            if(f28 < 0.0F)
                                f28 = 0.0F;
                            propForce *= f28;
                        }
                    }
                break;

            case 8: // '\b'
                float f24 = controlThrottle;
                if(engineBoostFactor > 1.0F)
                    f24 = 0.9090909F * controlThrottle;
                float f19 = propAngleDeviceMaxParam;
                if(controlAfterburner)
                    if(afterburnerType == 1)
                    {
                        if(controlThrottle > 1.0F)
                            f19 = propAngleDeviceMaxParam + 10F * (controlThrottle - 1.0F) * (propAngleDeviceAfterburnerParam - propAngleDeviceMaxParam);
                    } else
                    {
                        f19 = propAngleDeviceAfterburnerParam;
                    }
                float f3 = propAngleDeviceMinParam + (f19 - propAngleDeviceMinParam) * f24 + (bControlPropAuto ? 0.0F : -25F + 50F * controlProp);
                float f12 = controlThrottle;
                if(f12 > 1.0F)
                    f12 = 1.0F;
                compressorManifoldThreshold = getATA(toRPM(propAngleDeviceMinParam + (f19 - propAngleDeviceMinParam) * f12));
                if(isPropAngleDeviceOperational())
                {
                    if(w < f3)
                    {
                        f3 = java.lang.Math.min(1.0F, 0.01F * (f3 - w) - 0.012F * aw);
                        propTarget -= f3 * getPropAngleDeviceSpeed() * f;
                    } else
                    {
                        f3 = java.lang.Math.min(1.0F, 0.01F * (w - f3) + 0.012F * aw);
                        propTarget += f3 * getPropAngleDeviceSpeed() * f;
                    }
                    if(stage == 6 && propTarget < propPhiW - 0.12F)
                    {
                        propTarget = propPhiW - 0.12F;
                        if(propPhi < propTarget)
                            propPhi += 0.2F * f;
                    }
                    if(propTarget < propPhiMin + (float)java.lang.Math.toRadians(3D))
                        propTarget = propPhiMin + (float)java.lang.Math.toRadians(3D);
                }
                break;

            case 6: // '\006'
                float f13 = controlThrottle;
                if(f13 > 1.0F)
                    f13 = 1.0F;
                compressorManifoldThreshold = 0.5F + (compressorRPMtoWMaxATA - 0.5F) * f13;
                if(isPropAngleDeviceOperational())
                    if(bControlPropAuto)
                    {
                        float f4 = 25F + (wMax - 25F) * (0.25F + 0.75F * controlThrottle);
                        if(w < f4)
                        {
                            f4 = java.lang.Math.min(1.0F, 0.01F * (f4 - w) - 0.012F * aw);
                            propTarget -= f4 * getPropAngleDeviceSpeed() * f;
                        } else
                        {
                            f4 = java.lang.Math.min(1.0F, 0.01F * (w - f4) + 0.012F * aw);
                            propTarget += f4 * getPropAngleDeviceSpeed() * f;
                        }
                        if(stage == 6 && propTarget < propPhiW - 0.12F)
                        {
                            propTarget = propPhiW - 0.12F;
                            if(propPhi < propTarget)
                                propPhi += 0.2F * f;
                        }
                        controlProp = (propAngleDeviceMaxParam - propTarget) / (propAngleDeviceMaxParam - propAngleDeviceMinParam);
                        if(controlProp < 0.0F)
                            controlProp = 0.0F;
                        if(controlProp > 1.0F)
                            controlProp = 1.0F;
                    } else
                    {
                        propTarget = propAngleDeviceMaxParam - controlProp * (propAngleDeviceMaxParam - propAngleDeviceMinParam);
                    }
                break;

            case 5: // '\005'
                float f14 = controlThrottle;
                if(f14 > 1.0F)
                    f14 = 1.0F;
                compressorManifoldThreshold = 0.5F + (compressorRPMtoWMaxATA - 0.5F) * f14;
                if(bControlPropAuto)
                    if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
                    {
                        if(com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
                            controlProp = -controlThrottle;
                        else
                            controlProp = -com.maddox.il2.objects.air.Aircraft.cvt(reference.getSpeed(), reference.Vmin, reference.Vmax, 0.0F, 1.0F);
                    } else
                    {
                        controlProp = -com.maddox.il2.objects.air.Aircraft.cvt(reference.getSpeed(), reference.Vmin, reference.Vmax, 0.0F, 1.0F);
                    }
                propTarget = propAngleDeviceMaxParam - controlProp * (propAngleDeviceMaxParam - propAngleDeviceMinParam);
                propPhi = propTarget;
                break;
            }
            if(controlFeather == 1 && bHasFeatherControl && isPropAngleDeviceOperational())
                propTarget = 1.55F;
            if(propPhi > propTarget)
            {
                float f5 = java.lang.Math.min(1.0F, 157.2958F * (propPhi - propTarget));
                propPhi -= f5 * getPropAngleDeviceSpeed() * f;
            } else
            if(propPhi < propTarget)
            {
                float f6 = java.lang.Math.min(1.0F, 157.2958F * (propTarget - propPhi));
                propPhi += f6 * getPropAngleDeviceSpeed() * f;
            }
            if(propTarget > propPhiMax)
                propTarget = propPhiMax;
            else
            if(propTarget < propPhiMin)
                propTarget = propPhiMin;
            if(propPhi > propPhiMax && controlFeather == 0)
                propPhi = propPhiMax;
            else
            if(propPhi < propPhiMin)
                propPhi = propPhiMin;
            engineMoment = getN();
            float f15 = getCompressorMultiplier(f);
            engineMoment *= f15;
            momForFuel = engineMoment;
            engineMoment *= getReadyness();
            engineMoment *= getMagnetoMultiplier();
            engineMoment *= getMixMultiplier();
            engineMoment *= getStageMultiplier();
            engineMoment *= getDistabilisationMultiplier();
            engineMoment += getFrictionMoment(f);
            float f20 = engineMoment - propMoment;
            aw = f20 / (propI + engineI);
            if(aw > 0.0F)
                aw *= engineAcceleration;
            oldW = w;
            w += aw * f;
            if(w < 0.0F)
                w = 0.0F;
            if(w > wMaxAllowed + wMaxAllowed)
                w = wMaxAllowed + wMaxAllowed;
            if(oldW == 0.0F)
            {
                if(w < 10F * fricCoeffT)
                    w = 0.0F;
            } else
            if(w < 2.0F * fricCoeffT)
                w = 0.0F;
            if(reference.isPlayers() && com.maddox.il2.ai.World.cur().diffCur.Torque_N_Gyro_Effects && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
            {
                propIW.set(propI * w * propReductor, 0.0D, 0.0D);
                if(propDirection == 1)
                    propIW.x = -propIW.x;
                engineTorque.set(0.0F, 0.0F, 0.0F);
                float f25 = propI * aw * propReductor;
                if(propDirection == 0)
                {
                    engineTorque.x += propMoment;
                    engineTorque.x += f25;
                } else
                {
                    engineTorque.x -= propMoment;
                    engineTorque.x -= f25;
                }
            } else
            {
                engineTorque.set(0.0F, 0.0F, 0.0F);
            }
            engineForce.set(engineVector);
            engineForce.scale(propForce);
            tmpV3f.cross(propPos, engineForce);
            engineTorque.add(tmpV3f);
            rearRush = 0.0F;
            rpm = toRPM(w);
            double d = reference.Vflow.x + addVflow;
            if(d < 1.0D)
                d = 1.0D;
            double d1 = 1.0D / ((double)(com.maddox.il2.fm.Atmosphere.density(reference.getAltitude()) * 6F) * d);
            addVflow = 0.94999999999999996D * addVflow + 0.050000000000000003D * (double)propForce * d1;
            addVside = 0.94999999999999996D * addVside + 0.050000000000000003D * (double)(propMoment / propr) * d1;
            if(addVside < 0.0D)
                addVside = 0.0D;
            break;

        case 2: // '\002'
            float f29 = pressureExtBar;
            engineMoment = propAngleDeviceMinParam + getControlThrottle() * (propAngleDeviceMaxParam - propAngleDeviceMinParam);
            engineMoment /= propAngleDeviceMaxParam;
            engineMoment *= engineMomentMax;
            engineMoment *= getReadyness();
            engineMoment *= getDistabilisationMultiplier();
            engineMoment *= getStageMultiplier();
            engineMoment += getJetFrictionMoment(f);
            computePropForces(w, 0.0F, 0.0F, propAoA0, 0.0F);
            float f30 = w * _1_wMax;
            float f31 = f30 * pressureExtBar;
            float f32 = f30 * f30;
            float f33 = 1.0F - 0.006F * (com.maddox.il2.fm.Atmosphere.temperature((float)reference.Loc.z) - 290F);
            float f34 = 1.0F - 0.0011F * reference.getSpeed();
            propForce = thrustMax * f31 * f32 * f33 * f34 * getStageMultiplier();
            float f21 = engineMoment - propMoment;
            aw = (f21 / (propI + engineI)) * 1.0F;
            if(aw > 0.0F)
                aw *= engineAcceleration;
            w += aw * f;
            if(w < -wMaxAllowed)
                w = -wMaxAllowed;
            if(w > wMaxAllowed + wMaxAllowed)
                w = wMaxAllowed + wMaxAllowed;
            engineForce.set(engineVector);
            engineForce.scale(propForce);
            engineTorque.cross(enginePos, engineForce);
            rpm = toRPM(w);
            break;

        case 3: // '\003'
        case 4: // '\004'
            w = wMin + (wMax - wMin) * controlThrottle;
            if(w < wMin || w < 0.0F || reference.M.fuel == 0.0F || stage != 6)
                w = 0.0F;
            propForce = (w / wMax) * thrustMax;
            propForce *= getStageMultiplier();
            float f7 = (float)reference.Vwld.length();
            if(f7 > 208.333F)
                if(f7 > 291.666F)
                    propForce = 0.0F;
                else
                    propForce *= (float)java.lang.Math.sqrt((291.666F - f7) / 83.33299F);
            engineForce.set(engineVector);
            engineForce.scale(propForce);
            engineTorque.cross(enginePos, engineForce);
            rpm = toRPM(w);
            break;

        case 6: // '\006'
            w = wMin + (wMax - wMin) * controlThrottle;
            if(w < wMin || w < 0.0F || stage != 6)
                w = 0.0F;
            float f35 = reference.getSpeed() / 94F;
            if(f35 < 1.0F)
                w = 0.0F;
            else
                f35 = (float)java.lang.Math.sqrt(f35);
            propForce = (w / wMax) * thrustMax * f35;
            propForce *= getStageMultiplier();
            float f8 = (float)reference.Vwld.length();
            if(f8 > 208.333F)
                if(f8 > 291.666F)
                    propForce = 0.0F;
                else
                    propForce *= (float)java.lang.Math.sqrt((291.666F - f8) / 83.33299F);
            engineForce.set(engineVector);
            engineForce.scale(propForce);
            engineTorque.cross(enginePos, engineForce);
            rpm = toRPM(w);
            if(!(reference instanceof com.maddox.il2.fm.RealFlightModel))
                break;
            com.maddox.il2.fm.RealFlightModel realflightmodel = (com.maddox.il2.fm.RealFlightModel)reference;
            f8 = com.maddox.il2.objects.air.Aircraft.cvt(propForce, 0.0F, thrustMax, 0.0F, 0.21F);
            if(realflightmodel.producedShakeLevel < f8)
                realflightmodel.producedShakeLevel = f8;
            break;

        case 5: // '\005'
            engineForce.set(engineVector);
            engineForce.scale(propForce);
            engineTorque.cross(enginePos, engineForce);
            break;

        default:
            return;
        }
    }

    private void computePropForces(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = f * propr;
        float f6 = f1 * f1 + f5 * f5;
        float f7 = (float)java.lang.Math.sqrt(f6);
        float f8 = 0.5F * getFanCy((float)java.lang.Math.toDegrees(f3)) * com.maddox.il2.fm.Atmosphere.density(f4) * f6 * propSEquivalent;
        float f9 = 0.5F * getFanCx((float)java.lang.Math.toDegrees(f3)) * com.maddox.il2.fm.Atmosphere.density(f4) * f6 * propSEquivalent;
        if(f7 > 300F)
        {
            float f10 = 1.0F + 0.02F * (f7 - 300F);
            if(f10 > 2.0F)
                f10 = 2.0F;
            f9 *= f10;
        }
        if(f7 < 0.001F)
            f7 = 0.001F;
        float f11 = 1.0F / f7;
        float f12 = f1 * f11;
        float f13 = f5 * f11;
        float f14 = 1.0F;
        if(f1 < Vopt)
        {
            float f15 = Vopt - f1;
            f14 = 1.0F - 5E-005F * f15 * f15;
        }
        propForce = f14 * (f8 * f13 - f9 * f12);
        propMoment = (f9 * f13 + f8 * f12) * propr;
    }

    public void toggle()
    {
        if(stage == 0)
        {
            setEngineStarts(reference.actor);
            return;
        }
        if(stage < 7)
        {
            setEngineStops(reference.actor);
            if(reference.isPlayers())
                com.maddox.il2.game.HUD.log("EngineI0");
            return;
        } else
        {
            return;
        }
    }

    public float getPowerOutput()
    {
        if(stage == 0 || stage > 6)
            return 0.0F;
        else
            return controlThrottle * readyness;
    }

    public float getThrustOutput()
    {
        if(stage == 0 || stage > 6)
            return 0.0F;
        float f = w * _1_wMax * readyness;
        if(f > 1.1F)
            f = 1.1F;
        return f;
    }

    public float getReadyness()
    {
        return readyness;
    }

    public float getPropPhi()
    {
        return propPhi;
    }

    private float getPropAngleDeviceSpeed()
    {
        if(isPropAngleDeviceHydroOperable)
            return propAngleChangeSpeed;
        else
            return propAngleChangeSpeed * 10F;
    }

    public int getPropDir()
    {
        return propDirection;
    }

    public float getPropAoA()
    {
        return propAoA;
    }

    public com.maddox.JGP.Vector3f getForce()
    {
        return engineForce;
    }

    public float getRearRush()
    {
        return rearRush;
    }

    public float getw()
    {
        return w;
    }

    public float getRPM()
    {
        return rpm;
    }

    public float getPropw()
    {
        return w * propReductor;
    }

    public float getPropRPM()
    {
        return rpm * propReductor;
    }

    public int getType()
    {
        return type;
    }

    public float getControlThrottle()
    {
        return controlThrottle;
    }

    public boolean getControlAfterburner()
    {
        return controlAfterburner;
    }

    public boolean isHasControlThrottle()
    {
        return bHasThrottleControl;
    }

    public boolean isHasControlAfterburner()
    {
        return bHasAfterburnerControl;
    }

    public float getControlProp()
    {
        return controlProp;
    }

    public float getElPropPos()
    {
        float f;
        if(bControlPropAuto)
            f = controlProp;
        else
            f = (propPhiMax - propPhi) / (propPhiMax - propPhiMin);
        if(f < 0.1F)
            return 0.0F;
        if(f > 0.9F)
            return 1.0F;
        else
            return f;
    }

    public boolean getControlPropAuto()
    {
        return bControlPropAuto;
    }

    public boolean isHasControlProp()
    {
        return bHasPropControl;
    }

    public boolean isAllowsAutoProp()
    {
        if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
            if(com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
                switch(propAngleDeviceType)
                {
                case 0: // '\0'
                    return false;

                case 5: // '\005'
                    return true;

                case 6: // '\006'
                    return false;

                case 3: // '\003'
                case 4: // '\004'
                    return false;

                case 1: // '\001'
                case 2: // '\002'
                    return (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE9) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8CLP);

                case 7: // '\007'
                case 8: // '\b'
                    return true;
                }
            else
                return bHasPropControl;
        return true;
    }

    public float getControlMix()
    {
        return controlMix;
    }

    public boolean isHasControlMix()
    {
        return bHasMixControl;
    }

    public int getControlMagnetos()
    {
        return controlMagneto;
    }

    public int getControlCompressor()
    {
        return controlCompressor;
    }

    public boolean isHasControlMagnetos()
    {
        return bHasMagnetoControl;
    }

    public boolean isHasControlCompressor()
    {
        return bHasCompressorControl;
    }

    public int getControlFeather()
    {
        return controlFeather;
    }

    public boolean isHasControlFeather()
    {
        return bHasFeatherControl;
    }

    public boolean isAllowsAutoRadiator()
    {
        if(com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
        {
            if((reference.actor instanceof com.maddox.il2.objects.air.P_51) || (reference.actor instanceof com.maddox.il2.objects.air.P_38) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_3) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_3P) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9M) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9U) || (reference.actor instanceof com.maddox.il2.objects.air.YAK_9UT) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE8CLP) || (reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE9) || (reference.actor instanceof com.maddox.il2.objects.air.P_63C))
                return true;
            switch(propAngleDeviceType)
            {
            case 7: // '\007'
                return true;

            case 8: // '\b'
                return type == 0;
            }
            return false;
        } else
        {
            return true;
        }
    }

    public boolean isHasControlRadiator()
    {
        return bHasRadiatorControl;
    }

    public float getControlRadiator()
    {
        return controlRadiator;
    }

    public int getExtinguishers()
    {
        return extinguishers;
    }

    private float getFanCy(float f)
    {
        if(f > 34F)
            f = 34F;
        if(f < -8F)
            f = -8F;
        if(f < 16F)
            return -0.004688F * f * f + 0.15F * f + 0.4F;
        float f1 = 0.0F;
        if(f > 22F)
        {
            f1 = 0.01F * (f - 22F);
            f = 22F;
        }
        return (0.00097222F * f * f - 0.070833F * f) + 2.4844F + f1;
    }

    private float getFanCx(float f)
    {
        if(f < -4F)
            f = -8F - f;
        if(f > 34F)
            f = 34F;
        if((double)f < 16D)
            return 0.00035F * f * f + 0.0028F * f + 0.0256F;
        float f1 = 0.0F;
        if(f > 22F)
        {
            f1 = 0.04F * (f - 22F);
            f = 22F;
        }
        return ((-0.00555F * f * f + 0.24444F * f) - 2.32888F) + f1;
    }

    public int getCylinders()
    {
        return cylinders;
    }

    public int getCylindersOperable()
    {
        return cylindersOperable;
    }

    public float getCylindersRatio()
    {
        return (float)cylindersOperable / (float)cylinders;
    }

    public int getStage()
    {
        return stage;
    }

    public float getBoostFactor()
    {
        return engineBoostFactor;
    }

    public float getManifoldPressure()
    {
        return compressorManifoldPressure;
    }

    public void setManifoldPressure(float f)
    {
        compressorManifoldPressure = f;
    }

    public boolean getSootState()
    {
        return false;
    }

    public com.maddox.JGP.Point3f getEnginePos()
    {
        return enginePos;
    }

    public com.maddox.JGP.Point3f getPropPos()
    {
        return propPos;
    }

    public com.maddox.JGP.Vector3f getEngineVector()
    {
        return engineVector;
    }

    public float forcePropAOA(float f, float f1, float f2, boolean flag)
    {
        switch(type)
        {
        default:
            return -1F;

        case 0: // '\0'
        case 1: // '\001'
        case 7: // '\007'
            float f3;
            boolean flag1;
            int i;
            float f4;
            boolean flag3;
label0:
            {
                f3 = controlThrottle;
                flag1 = controlAfterburner;
                i = stage;
                safeLoc.set(reference.Loc);
                safeVwld.set(reference.Vwld);
                safeVflow.set(reference.Vflow);
                if(flag)
                    w = wWEP;
                else
                    w = wMax;
                controlThrottle = f2;
                if((double)engineBoostFactor <= 1.0D && controlThrottle > 1.0F)
                    controlThrottle = 1.0F;
                if(afterburnerType > 0 && flag)
                    controlAfterburner = true;
                stage = 6;
                fastATA = true;
                reference.Loc.set(0.0D, 0.0D, f1);
                reference.Vwld.set(f, 0.0D, 0.0D);
                reference.Vflow.set(f, 0.0D, 0.0D);
                pressureExtBar = com.maddox.il2.fm.Atmosphere.pressure(reference.getAltitude()) + compressorSpeedManifold * 0.5F * com.maddox.il2.fm.Atmosphere.density(reference.getAltitude()) * f * f;
                pressureExtBar /= com.maddox.il2.fm.Atmosphere.P0();
                f4 = getCompressorMultiplier(0.033F);
                f4 *= getN();
                if(flag && bWepRpmInLowGear && controlCompressor == compressorMaxStep)
                {
                    w = wMax;
                    float f5 = getCompressorMultiplier(0.033F);
                    f5 *= getN();
                    f4 = f5;
                }
                boolean flag2 = false;
                float f6 = propPhiMin;
                float f7 = -1E+008F;
                flag3 = false;
                if((com.maddox.il2.objects.air.Aircraft)(com.maddox.il2.objects.air.Aircraft)reference.actor instanceof com.maddox.il2.objects.air.SM79)
                    flag3 = true;
                do
                {
                    if(propAngleDeviceType != 0 && !flag3)
                        break label0;
                    float f8 = 2.0F;
                    int j = 0;
                    float f10 = 0.1F;
                    float f12 = 0.5F;
                    do
                    {
                        if(flag)
                            w = wWEP * f12;
                        else
                            w = wMax * f12;
                        float f15 = (float)java.lang.Math.sqrt(f * f + w * propr * propReductor * w * propr * propReductor);
                        float f16 = f6 - (float)java.lang.Math.asin(f / f15);
                        computePropForces(w * propReductor, f, 0.0F, f16, f1);
                        f4 = getN() * getCompressorMultiplier(0.033F);
                        if(j > 32 || (double)f10 <= 1.0000000000000001E-005D)
                            break;
                        if(propMoment < f4)
                        {
                            if(f8 == 1.0F)
                                f10 /= 2.0F;
                            f12 *= 1.0F + f10;
                            f8 = 0.0F;
                        } else
                        {
                            if(f8 == 0.0F)
                                f10 /= 2.0F;
                            f12 /= 1.0F + f10;
                            f8 = 1.0F;
                        }
                        j++;
                    } while(true);
                    if(!flag3)
                        break label0;
                    if(f6 != propPhiMin)
                        break;
                    f7 = propForce;
                    f6 = propPhiMax;
                } while(true);
                if(f7 > propForce)
                    propForce = f7;
            }
            controlThrottle = f3;
            controlAfterburner = flag1;
            stage = i;
            reference.Loc.set(safeLoc);
            reference.Vwld.set(safeVwld);
            reference.Vflow.set(safeVflow);
            fastATA = false;
            w = 0.0F;
            if(flag3 || propAngleDeviceType == 0)
                return propForce;
            float f9 = 1.5F;
            float f11 = -0.06F;
            float f13 = 0.5F * (f9 + f11);
            int k = 0;
            do
            {
                float f14 = 0.5F * (f9 + f11);
                if(flag && (!bWepRpmInLowGear || controlCompressor != compressorMaxStep))
                    computePropForces(wWEP * propReductor, f, 0.0F, f14, f1);
                else
                    computePropForces(wMax * propReductor, f, 0.0F, f14, f1);
                if((propForce <= 0.0F || java.lang.Math.abs(propMoment - f4) >= 1E-005F) && k <= 32)
                {
                    if(propForce > 0.0F && propMoment > f4)
                        f9 = f14;
                    else
                        f11 = f14;
                    k++;
                } else
                {
                    return propForce;
                }
            } while(true);

        case 2: // '\002'
            pressureExtBar = com.maddox.il2.fm.Atmosphere.pressure(f1) + compressorSpeedManifold * 0.5F * com.maddox.il2.fm.Atmosphere.density(f1) * f * f;
            pressureExtBar /= com.maddox.il2.fm.Atmosphere.P0();
            float f17 = pressureExtBar;
            float f18 = 1.0F - 0.006F * (com.maddox.il2.fm.Atmosphere.temperature(f1) - 290F);
            float f19 = 1.0F - 0.0011F * f;
            propForce = thrustMax * f17 * f18 * f19;
            return propForce;

        case 3: // '\003'
        case 4: // '\004'
        case 6: // '\006'
            propForce = thrustMax;
            if(f > 208.333F)
                if(f > 291.666F)
                    propForce = 0.0F;
                else
                    propForce *= (float)java.lang.Math.sqrt((291.666F - f) / 83.33299F);
            return propForce;

        case 5: // '\005'
            return thrustMax;

        case 8: // '\b'
            return -1F;
        }
    }

    public float getEngineLoad()
    {
        float f = 0.1F + getControlThrottle() * 0.8181818F;
        float f1 = getw() / wMax;
        return f1 / f;
    }

    private void overrevving()
    {
        if((reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement && com.maddox.il2.ai.World.cur().diffCur.Engine_Overheat && w > wMaxAllowed && bIsMaster)
        {
            wMaxAllowed = 0.999965F * wMaxAllowed;
            _1_wMaxAllowed = 1.0F / wMaxAllowed;
            tmpF *= 1.0F - (wMaxAllowed - w) * 0.01F;
            engineDamageAccum += 0.01F + 0.05F * (w - wMaxAllowed) * _1_wMaxAllowed;
            if(engineDamageAccum > 1.0F)
            {
                if(heatStringID == -1)
                    heatStringID = com.maddox.il2.game.HUD.makeIdLog();
                if(reference.isPlayers())
                    com.maddox.il2.game.HUD.log(heatStringID, "EngineOverheat");
                setReadyness(getReadyness() - (engineDamageAccum - 1.0F) * 0.005F);
            }
            if(getReadyness() < 0.2F)
                setEngineDies(reference.actor);
        }
    }

    public float getN()
    {
        if(stage == 6)
        {
            switch(engineCarburetorType)
            {
            case 0: // '\0'
                float f = 0.05F + 0.95F * getControlThrottle();
                float f4 = w / wMax;
                tmpF = engineMomentMax * ((-1F / f) * f4 * f4 + 2.0F * f4);
                if(getControlThrottle() > 1.0F)
                    tmpF *= engineBoostFactor;
                overrevving();
                break;

            case 3: // '\003'
                float f1 = 0.1F + 0.9F * getControlThrottle();
                float f5 = w / wNom;
                tmpF = engineMomentMax * ((-1F / f1) * f5 * f5 + 2.0F * f5);
                if(getControlThrottle() > 1.0F)
                    tmpF *= engineBoostFactor;
                float f10 = getControlThrottle() - neg_G_Counter * 0.1F;
                if(f10 <= 0.3F)
                    f10 = 0.3F;
                if(reference.getOverload() < 0.0F && neg_G_Counter >= 0.0F)
                {
                    neg_G_Counter += 0.03F;
                    producedDistabilisation += 10F + 5F * neg_G_Counter;
                    tmpF *= f10;
                    if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && bIsMaster && neg_G_Counter > com.maddox.il2.ai.World.Rnd().nextFloat(5F, 8F))
                        setEngineStops(reference.actor);
                } else
                if(reference.getOverload() >= 0.0F && neg_G_Counter > 0.0F)
                {
                    neg_G_Counter -= 0.015F;
                    producedDistabilisation += 10F + 5F * neg_G_Counter;
                    tmpF *= f10;
                    bFloodCarb = true;
                } else
                {
                    bFloodCarb = false;
                    neg_G_Counter = 0.0F;
                }
                overrevving();
                break;

            case 1: // '\001'
            case 2: // '\002'
                float f2 = 0.1F + 0.9F * getControlThrottle();
                if(f2 > 1.0F)
                    f2 = 1.0F;
                float f8 = engineMomentMax * (-0.5F * f2 * f2 + 1.0F * f2 + 0.5F);
                float f6;
                if(controlAfterburner)
                    f6 = w / (wWEP * f2);
                else
                    f6 = w / (wNom * f2);
                tmpF = f8 * (2.0F * f6 - 1.0F * f6 * f6);
                if(getControlThrottle() > 1.0F)
                    tmpF *= 1.0F + (getControlThrottle() - 1.0F) * 10F * (engineBoostFactor - 1.0F);
                overrevving();
                break;

            case 4: // '\004'
                float f3 = 0.1F + 0.9F * getControlThrottle();
                if(f3 > 1.0F)
                    f3 = 1.0F;
                float f9 = engineMomentMax * (-0.5F * f3 * f3 + 1.0F * f3 + 0.5F);
                float f7;
                if(controlAfterburner)
                {
                    f7 = w / (wWEP * f3);
                    if(f3 >= 0.95F)
                        bFullT = true;
                    else
                        bFullT = false;
                } else
                {
                    f7 = w / (wNom * f3);
                    bFullT = false;
                    if((reference.actor instanceof com.maddox.il2.objects.air.SPITFIRE5B) && f3 >= 0.95F)
                        bFullT = true;
                }
                tmpF = f9 * (2.0F * f7 - 1.0F * f7 * f7);
                if(getControlThrottle() > 1.0F)
                    tmpF *= 1.0F + (getControlThrottle() - 1.0F) * 10F * (engineBoostFactor - 1.0F);
                float f11 = getControlThrottle() - neg_G_Counter * 0.2F;
                if(f11 <= 0.0F)
                    f11 = 0.1F;
                if(reference.getOverload() < 0.0F && neg_G_Counter >= 0.0F)
                {
                    neg_G_Counter += 0.03F;
                    if(bFullT && neg_G_Counter < 0.5F)
                    {
                        producedDistabilisation += 15F + 5F * neg_G_Counter;
                        tmpF *= 0.52F - neg_G_Counter;
                    } else
                    if(bFullT && neg_G_Counter >= 0.5F && neg_G_Counter <= 0.8F)
                    {
                        neg_G_Counter = 0.51F;
                        bFloodCarb = false;
                    } else
                    if(bFullT && neg_G_Counter > 0.8F)
                    {
                        neg_G_Counter -= 0.045F;
                        producedDistabilisation += 10F + 5F * neg_G_Counter;
                        tmpF *= f11;
                        bFloodCarb = true;
                    } else
                    {
                        producedDistabilisation += 10F + 5F * neg_G_Counter;
                        tmpF *= f11;
                        if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && bIsMaster && neg_G_Counter > com.maddox.il2.ai.World.Rnd().nextFloat(7.5F, 9.5F))
                            setEngineStops(reference.actor);
                    }
                } else
                if(reference.getOverload() >= 0.0F && neg_G_Counter > 0.0F)
                {
                    neg_G_Counter -= 0.03F;
                    if(!bFullT)
                    {
                        producedDistabilisation += 10F + 5F * neg_G_Counter;
                        tmpF *= f11;
                    }
                    bFloodCarb = true;
                } else
                {
                    neg_G_Counter = 0.0F;
                    bFloodCarb = false;
                }
                overrevving();
                break;
            }
            if(controlAfterburner)
                if(afterburnerType == 1)
                {
                    if(controlThrottle > 1.0F && reference.M.nitro > 0.0F)
                        tmpF *= engineAfterburnerBoostFactor;
                } else
                if(afterburnerType == 8 || afterburnerType == 7)
                {
                    if(controlCompressor < compressorMaxStep)
                        tmpF *= engineAfterburnerBoostFactor;
                } else
                {
                    tmpF *= engineAfterburnerBoostFactor;
                }
            if(engineDamageAccum > 0.0F)
                engineDamageAccum -= 0.01F;
            if(engineDamageAccum < 0.0F)
                engineDamageAccum = 0.0F;
            if(tmpF < 0.0F)
                tmpF = java.lang.Math.max(tmpF, -0.8F * w * _1_wMax * engineMomentMax);
            return tmpF;
        }
        tmpF = -1500F * w * _1_wMax * engineMomentMax;
        if(stage == 8)
            w = 0.0F;
        return tmpF;
    }

    private float getDistabilisationMultiplier()
    {
        if(engineMoment < 0.0F)
            return 1.0F;
        float f = 1.0F + com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 0.1F) * getDistabilisationAmplitude();
        if(f < 0.0F && w < 0.5F * (wMax + wMin))
            return 0.0F;
        else
            return f;
    }

    public float getDistabilisationAmplitude()
    {
        if(getCylindersOperable() > 2)
        {
            float f = 1.0F - getCylindersRatio();
            return engineDistAM * w * w + engineDistBM * w + engineDistCM + 9.25F * f * f + producedDistabilisation;
        } else
        {
            return 11.25F;
        }
    }

    private float getCompressorMultiplier(float f)
    {
        float f20 = controlThrottle;
        if(f20 > 1.0F)
            f20 = 1.0F;
        float f15;
        switch(propAngleDeviceType)
        {
        case 1: // '\001'
        case 2: // '\002'
        case 7: // '\007'
        case 8: // '\b'
            f15 = getATA(toRPM(propAngleDeviceMinParam + (propAngleDeviceMaxParam - propAngleDeviceMinParam) * f20));
            break;

        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        default:
            f15 = compressorRPMtoWMaxATA * (0.55F + 0.45F * f20);
            break;
        }
        coolMult = 1.0F;
        compressorManifoldThreshold = f15;
        switch(compressorType)
        {
        case 0: // '\0'
            float f1 = com.maddox.il2.fm.Atmosphere.pressure(reference.getAltitude()) + 0.5F * com.maddox.il2.fm.Atmosphere.density(reference.getAltitude()) * reference.getSpeed() * reference.getSpeed();
            float f21 = f1 / com.maddox.il2.fm.Atmosphere.P0();
            coolMult = f21;
            return f21;

        case 1: // '\001'
            float f2 = pressureExtBar;
            if((!bHasCompressorControl || !reference.isPlayers() || !(reference instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() || !com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement || fastATA) && (reference.isTick(128, 0) || fastATA))
            {
                compressorStepFound = false;
                controlCompressor = 0;
            }
            float f25 = -1F;
            float f26 = -1F;
            int i = -1;
            float f22;
            if(fastATA)
            {
                for(controlCompressor = 0; controlCompressor <= compressorMaxStep; controlCompressor++)
                {
                    compressorManifoldThreshold = f15;
                    float f5 = compressorPressure[controlCompressor];
                    float f10 = compressorRPMtoWMaxATA / f5;
                    float f28 = 1.0F;
                    float f33 = 1.0F;
                    if(f2 > f5)
                    {
                        float f37 = 1.0F - f5;
                        if(f37 < 0.0001F)
                            f37 = 0.0001F;
                        float f42 = 1.0F - f2;
                        if(f42 < 0.0F)
                            f42 = 0.0F;
                        float f46 = 1.0F;
                        for(int j = 1; j <= controlCompressor; j++)
                            if(compressorAltMultipliers[controlCompressor] >= 1.0F)
                                f46 *= 0.8F;
                            else
                                f46 *= 0.8F * compressorAltMultipliers[controlCompressor];

                        f28 = f46 + (f42 / f37) * (compressorAltMultipliers[controlCompressor] - f46);
                    } else
                    {
                        f28 = compressorAltMultipliers[controlCompressor];
                    }
                    compressorManifoldPressure = (compressorPAt0 + (1.0F - compressorPAt0) * w * _1_wMax) * f2 * f10;
                    float f16 = compressorRPMtoWMaxATA / compressorManifoldPressure;
                    if(controlAfterburner && (afterburnerType != 8 && afterburnerType != 7 || controlCompressor != compressorMaxStep) && (afterburnerType != 1 || controlThrottle <= 1.0F || reference.M.nitro > 0.0F))
                    {
                        f16 *= afterburnerCompressorFactor;
                        compressorManifoldThreshold *= afterburnerCompressorFactor;
                    }
                    compressor2ndThrottle = f16;
                    if(compressor2ndThrottle > 1.0F)
                        compressor2ndThrottle = 1.0F;
                    compressorManifoldPressure *= compressor2ndThrottle;
                    compressor1stThrottle = f15 / compressorRPMtoWMaxATA;
                    if(compressor1stThrottle > 1.0F)
                        compressor1stThrottle = 1.0F;
                    compressorManifoldPressure *= compressor1stThrottle;
                    f33 = (f28 * compressorManifoldPressure) / compressorManifoldThreshold;
                    if(controlAfterburner && (afterburnerType == 8 || afterburnerType == 7) && controlCompressor == compressorMaxStep)
                    {
                        if(f33 / engineAfterburnerBoostFactor > f25)
                        {
                            f25 = f33;
                            i = controlCompressor;
                        }
                        continue;
                    }
                    if(f33 > f25)
                    {
                        f25 = f33;
                        i = controlCompressor;
                    }
                }

                f22 = f25;
                controlCompressor = i;
            } else
            {
                float f29 = f15;
                if(controlAfterburner)
                    f29 *= afterburnerCompressorFactor;
                do
                {
                    float f6 = compressorPressure[controlCompressor];
                    float f11 = compressorRPMtoWMaxATA / f6;
                    float f34 = 1.0F;
                    float f38 = 1.0F;
                    if(f2 > f6)
                    {
                        float f43 = 1.0F - f6;
                        if(f43 < 0.0001F)
                            f43 = 0.0001F;
                        float f47 = 1.0F - f2;
                        if(f47 < 0.0F)
                            f47 = 0.0F;
                        float f49 = 1.0F;
                        for(int k = 1; k <= controlCompressor; k++)
                            if(compressorAltMultipliers[controlCompressor] >= 1.0F)
                                f49 *= 0.8F;
                            else
                                f49 *= 0.8F * compressorAltMultipliers[controlCompressor];

                        f34 = f49 + (f47 / f43) * (compressorAltMultipliers[controlCompressor] - f49);
                        f38 = f34;
                    } else
                    {
                        f34 = compressorAltMultipliers[controlCompressor];
                        f38 = (f34 * f2 * f11) / f29;
                    }
                    if(f38 > f25)
                    {
                        f25 = f38;
                        f26 = f34;
                        i = controlCompressor;
                    }
                    if(!compressorStepFound)
                    {
                        controlCompressor++;
                        if(controlCompressor == compressorMaxStep + 1)
                            compressorStepFound = true;
                    }
                } while(!compressorStepFound);
                if(i < 0)
                    i = 0;
                controlCompressor = i;
                float f7 = compressorPressure[controlCompressor];
                float f12 = compressorRPMtoWMaxATA / f7;
                compressorManifoldPressure = (compressorPAt0 + (1.0F - compressorPAt0) * w * _1_wMax) * f2 * f12;
                float f17 = compressorRPMtoWMaxATA / compressorManifoldPressure;
                if(controlAfterburner && (afterburnerType != 8 && afterburnerType != 7 || controlCompressor != compressorMaxStep) && (afterburnerType != 1 || controlThrottle <= 1.0F || reference.M.nitro > 0.0F))
                {
                    f17 *= afterburnerCompressorFactor;
                    compressorManifoldThreshold *= afterburnerCompressorFactor;
                }
                if(fastATA)
                    compressor2ndThrottle = f17;
                else
                    compressor2ndThrottle -= 3F * f * (compressor2ndThrottle - f17);
                if(compressor2ndThrottle > 1.0F)
                    compressor2ndThrottle = 1.0F;
                compressorManifoldPressure *= compressor2ndThrottle;
                compressor1stThrottle = f15 / compressorRPMtoWMaxATA;
                if(compressor1stThrottle > 1.0F)
                    compressor1stThrottle = 1.0F;
                compressorManifoldPressure *= compressor1stThrottle;
                f22 = compressorManifoldPressure / compressorManifoldThreshold;
                coolMult = f22;
                f22 *= f26;
            }
            if(w <= 20F && w < 150F)
                compressorManifoldPressure = java.lang.Math.min(compressorManifoldPressure, f2 * (0.4F + (w - 20F) * 0.04F));
            if(w < 20F)
                compressorManifoldPressure = f2 * (1.0F - w * 0.03F);
            if(mixerType == 1 && stage == 6)
                compressorManifoldPressure *= getMixMultiplier();
            return f22;

        case 2: // '\002'
            float f3 = pressureExtBar;
            float f8 = compressorPressure[controlCompressor];
            float f13 = compressorRPMtoWMaxATA / f8;
            compressorManifoldPressure = (compressorPAt0 + (1.0F - compressorPAt0) * w * _1_wMax) * f3 * f13;
            float f18 = compressorRPMtoWMaxATA / compressorManifoldPressure;
            if(controlAfterburner && (afterburnerType != 1 || controlThrottle <= 1.0F || reference.M.nitro > 0.0F))
            {
                f18 *= afterburnerCompressorFactor;
                compressorManifoldThreshold *= afterburnerCompressorFactor;
            }
            if(fastATA)
                compressor2ndThrottle = f18;
            else
                compressor2ndThrottle -= 0.1F * (compressor2ndThrottle - f18);
            if(compressor2ndThrottle > 1.0F)
                compressor2ndThrottle = 1.0F;
            compressorManifoldPressure *= compressor2ndThrottle;
            compressor1stThrottle = f15 / compressorRPMtoWMaxATA;
            if(compressor1stThrottle > 1.0F)
                compressor1stThrottle = 1.0F;
            compressorManifoldPressure *= compressor1stThrottle;
            float f23;
            if(controlAfterburner && afterburnerType == 2 && reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode() && reference.M.nitro > 0.0F)
            {
                float f30 = compressorManifoldPressure + 0.2F;
                if(f30 > compressorRPMtoWMaxATA + 0.199F && !fastATA && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.001F)
                {
                    readyness = 0.0F;
                    setEngineDies(reference.actor);
                }
                if(f30 > compressorManifoldThreshold)
                    f30 = compressorManifoldThreshold;
                f23 = f30 / compressorManifoldThreshold;
            } else
            {
                f23 = compressorManifoldPressure / compressorManifoldThreshold;
            }
            if(w <= 20F && w < 150F)
                compressorManifoldPressure = java.lang.Math.min(compressorManifoldPressure, f3 * (0.4F + (w - 20F) * 0.04F));
            if(w < 20F)
                compressorManifoldPressure = f3 * (1.0F - w * 0.03F);
            if(f3 > f8)
            {
                float f31 = 1.0F - f8;
                if(f31 < 0.0001F)
                    f31 = 0.0001F;
                float f35 = 1.0F - f3;
                if(f35 < 0.0F)
                    f35 = 0.0F;
                float f39 = 1.0F;
                float f44 = f39 + (f35 / f31) * (compressorAltMultipliers[controlCompressor] - f39);
                f23 *= f44;
            } else
            {
                f23 *= compressorAltMultipliers[controlCompressor];
            }
            coolMult = compressorManifoldPressure / compressorManifoldThreshold;
            return f23;

        case 3: // '\003'
            float f4 = pressureExtBar;
            controlCompressor = 0;
            float f27 = -1F;
            float f9 = compressorPressure[controlCompressor];
            float f14 = compressorRPMtoWMaxATA / f9;
            float f32 = 1.0F;
            float f36 = 1.0F;
            if(f4 > f9)
            {
                float f40 = 1.0F - f9;
                if(f40 < 0.0001F)
                    f40 = 0.0001F;
                float f45 = 1.0F - f4;
                if(f45 < 0.0F)
                    f45 = 0.0F;
                float f48 = 1.0F;
                f32 = f48 + (f45 / f40) * (compressorAltMultipliers[controlCompressor] - f48);
            } else
            {
                f32 = compressorAltMultipliers[controlCompressor];
            }
            f27 = f32;
            f14 = compressorRPMtoWMaxATA / f9;
            if(f4 < f9)
                f4 = 0.1F * f4 + 0.9F * f9;
            float f41 = f4 * f14;
            compressorManifoldPressure = (compressorPAt0 + (1.0F - compressorPAt0) * w * _1_wMax) * f41;
            float f19 = compressorRPMtoWMaxATA / compressorManifoldPressure;
            if(fastATA)
                compressor2ndThrottle = f19;
            else
                compressor2ndThrottle -= 3F * f * (compressor2ndThrottle - f19);
            if(compressor2ndThrottle > 1.0F)
                compressor2ndThrottle = 1.0F;
            compressorManifoldPressure *= compressor2ndThrottle;
            compressor1stThrottle = f15 / compressorRPMtoWMaxATA;
            if(compressor1stThrottle > 1.0F)
                compressor1stThrottle = 1.0F;
            compressorManifoldPressure *= compressor1stThrottle;
            float f24 = compressorManifoldPressure / compressorManifoldThreshold;
            f24 *= f27;
            if(w <= 20F && w < 150F)
                compressorManifoldPressure = java.lang.Math.min(compressorManifoldPressure, f4 * (0.4F + (w - 20F) * 0.04F));
            if(w < 20F)
                compressorManifoldPressure = f4 * (1.0F - w * 0.03F);
            return f24;
        }
        return 1.0F;
    }

    private float getMagnetoMultiplier()
    {
        switch(controlMagneto)
        {
        case 0: // '\0'
            return 0.0F;

        case 1: // '\001'
            return bMagnetos[0] ? 0.87F : 0.0F;

        case 2: // '\002'
            return bMagnetos[1] ? 0.87F : 0.0F;

        case 3: // '\003'
            float f = 0.0F;
            f += bMagnetos[0] ? 0.87F : 0.0F;
            f += bMagnetos[1] ? 0.87F : 0.0F;
            if(f > 1.0F)
                f = 1.0F;
            return f;
        }
        return 1.0F;
    }

    private float getMixMultiplier()
    {
        float f4 = 0.0F;
        switch(mixerType)
        {
        case 0: // '\0'
            return 1.0F;

        case 1: // '\001'
            if(controlMix == 1.0F)
            {
                if(bFloodCarb)
                    reference.AS.setSootState(reference.actor, number, 1);
                else
                    reference.AS.setSootState(reference.actor, number, 0);
                return 1.0F;
            }
            // fall through

        case 2: // '\002'
            if(reference.isPlayers() && (reference instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)reference).isRealMode())
            {
                if(!com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement)
                    return 1.0F;
                float f = mixerLowPressureBar * controlMix;
                if(f < pressureExtBar - f4)
                {
                    if(f < 0.03F)
                        setEngineStops(reference.actor);
                    if(bFloodCarb)
                        reference.AS.setSootState(reference.actor, number, 1);
                    else
                        reference.AS.setSootState(reference.actor, number, 0);
                    if(f > (pressureExtBar - f4) * 0.25F)
                    {
                        return 1.0F;
                    } else
                    {
                        float f2 = f / ((pressureExtBar - f4) * 0.25F);
                        return f2;
                    }
                }
                if(f > pressureExtBar)
                {
                    producedDistabilisation += 0.0F + 35F * (1.0F - (pressureExtBar + f4) / (f + 0.0001F));
                    reference.AS.setSootState(reference.actor, number, 1);
                    float f3 = (pressureExtBar + f4) / (f + 0.0001F);
                    return f3;
                }
                if(bFloodCarb)
                    reference.AS.setSootState(reference.actor, number, 1);
                else
                    reference.AS.setSootState(reference.actor, number, 0);
                return 1.0F;
            }
            float f1 = mixerLowPressureBar * controlMix;
            if(f1 < pressureExtBar - f4 && f1 < 0.03F)
                setEngineStops(reference.actor);
            return 1.0F;

        default:
            return 1.0F;
        }
    }

    private float getStageMultiplier()
    {
        return stage != 6 ? 0.0F : 1.0F;
    }

    public void setFricCoeffT(float f)
    {
        fricCoeffT = f;
    }

    private float getFrictionMoment(float f)
    {
        float f1 = 0.0F;
        if(bIsInoperable || stage == 0 || controlMagneto == 0)
        {
            fricCoeffT += 0.1F * f;
            if(fricCoeffT > 1.0F)
                fricCoeffT = 1.0F;
            float f2 = w * _1_wMax;
            f1 = (-fricCoeffT * (6F + 3.8F * f2) * (propI + engineI)) / f;
            float f3 = (-0.99F * w * (propI + engineI)) / f;
            if(f1 < f3)
                f1 = f3;
        } else
        {
            fricCoeffT = 0.0F;
        }
        return f1;
    }

    private float getJetFrictionMoment(float f)
    {
        float f1 = 0.0F;
        if(bIsInoperable || stage == 0)
            f1 = (-0.002F * w * (propI + engineI)) / f;
        return f1;
    }

    public com.maddox.JGP.Vector3f getEngineForce()
    {
        return engineForce;
    }

    public com.maddox.JGP.Vector3f getEngineTorque()
    {
        return engineTorque;
    }

    public com.maddox.JGP.Vector3d getEngineGyro()
    {
        tmpV3d1.cross(reference.getW(), propIW);
        return tmpV3d1;
    }

    public float getEngineMomentRatio()
    {
        return engineMoment / engineMomentMax;
    }

    public boolean isPropAngleDeviceOperational()
    {
        return bIsAngleDeviceOperational;
    }

    public float getCriticalW()
    {
        return wMaxAllowed;
    }

    public float getPropPhiMin()
    {
        return propPhiMin;
    }

    public int getAfterburnerType()
    {
        return afterburnerType;
    }

    private float toRadianPerSecond(float f)
    {
        return (f * 3.141593F * 2.0F) / 60F;
    }

    private float toRPM(float f)
    {
        return (f * 60F) / 2.0F / 3.141593F;
    }

    private float getKforH(float f, float f1, float f2)
    {
        float f3 = (com.maddox.il2.fm.Atmosphere.density(f2) * (f1 * f1)) / (com.maddox.il2.fm.Atmosphere.density(0.0F) * (f * f));
        if(type != 2)
            f3 = (f3 * kV(f)) / kV(f1);
        return f3;
    }

    private float kV(float f)
    {
        return 1.0F - 0.0032F * f;
    }

    public final void setAfterburnerType(int i)
    {
        afterburnerType = i;
    }

    public final void setPropReductorValue(float f)
    {
        propReductor = f;
    }

    public void replicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte(controlMagneto | stage << 4);
        netmsgguaranted.writeByte(cylinders);
        netmsgguaranted.writeByte(cylindersOperable);
        netmsgguaranted.writeByte((int)(255F * readyness));
        netmsgguaranted.writeByte((int)(255F * ((propPhi - propPhiMin) / (propPhiMax - propPhiMin))));
        netmsgguaranted.writeFloat(w);
    }

    public void replicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        stage = (i & 0xf0) >> 4;
        controlMagneto = i & 0xf;
        cylinders = netmsginput.readUnsignedByte();
        cylindersOperable = netmsginput.readUnsignedByte();
        readyness = (float)netmsginput.readUnsignedByte() / 255F;
        propPhi = ((float)netmsginput.readUnsignedByte() / 255F) * (propPhiMax - propPhiMin) + propPhiMin;
        w = netmsginput.readFloat();
    }

    private static final boolean ___debug___ = false;
    public static final int _E_TYPE_INLINE = 0;
    public static final int _E_TYPE_RADIAL = 1;
    public static final int _E_TYPE_JET = 2;
    public static final int _E_TYPE_ROCKET = 3;
    public static final int _E_TYPE_ROCKETBOOST = 4;
    public static final int _E_TYPE_TOW = 5;
    public static final int _E_TYPE_PVRD = 6;
    public static final int _E_TYPE_HELO_INLINE = 7;
    public static final int _E_TYPE_UNIDENTIFIED = 8;
    public static final int _E_PROP_DIR_LEFT = 0;
    public static final int _E_PROP_DIR_RIGHT = 1;
    public static final int _E_STAGE_NULL = 0;
    public static final int _E_STAGE_WAKE_UP = 1;
    public static final int _E_STAGE_STARTER_ROLL = 2;
    public static final int _E_STAGE_CATCH_UP = 3;
    public static final int _E_STAGE_CATCH_ROLL = 4;
    public static final int _E_STAGE_CATCH_FIRE = 5;
    public static final int _E_STAGE_NOMINAL = 6;
    public static final int _E_STAGE_DEAD = 7;
    public static final int _E_STAGE_STUCK = 8;
    public static final int _E_PROP_FIXED = 0;
    public static final int _E_PROP_RETAIN_RPM_1 = 1;
    public static final int _E_PROP_RETAIN_RPM_2 = 2;
    public static final int _E_PROP_RETAIN_AOA_1 = 3;
    public static final int _E_PROP_RETAIN_AOA_2 = 4;
    public static final int _E_PROP_FRICTION = 5;
    public static final int _E_PROP_MANUALDRIVEN = 6;
    public static final int _E_PROP_WM_KOMANDGERAT = 7;
    public static final int _E_PROP_FW_KOMANDGERAT = 8;
    public static final int _E_PROP_CSP_EL = 9;
    public static final int _E_CARB_SUCTION = 0;
    public static final int _E_CARB_CARBURETOR = 1;
    public static final int _E_CARB_INJECTOR = 2;
    public static final int _E_CARB_FLOAT = 3;
    public static final int _E_CARB_SHILLING = 4;
    public static final int _E_COMPRESSOR_NONE = 0;
    public static final int _E_COMPRESSOR_MANUALSTEP = 1;
    public static final int _E_COMPRESSOR_WM_KOMANDGERAT = 2;
    public static final int _E_COMPRESSOR_TURBO = 3;
    public static final int _E_MIXER_GENERIC = 0;
    public static final int _E_MIXER_BRIT_FULLAUTO = 1;
    public static final int _E_MIXER_LIMITED_PRESSURE = 2;
    public static final int _E_AFTERBURNER_GENERIC = 0;
    public static final int _E_AFTERBURNER_MW50 = 1;
    public static final int _E_AFTERBURNER_GM1 = 2;
    public static final int _E_AFTERBURNER_FIRECHAMBER = 3;
    public static final int _E_AFTERBURNER_WATER = 4;
    public static final int _E_AFTERBURNER_NO2 = 5;
    public static final int _E_AFTERBURNER_FUEL_INJECTION = 6;
    public static final int _E_AFTERBURNER_FUEL_ILA5 = 7;
    public static final int _E_AFTERBURNER_FUEL_ILA5AUTO = 8;
    public static final int _E_AFTERBURNER_WATERMETHANOL = 9;
    public static final int _E_AFTERBURNER_P51 = 10;
    public static final int _E_AFTERBURNER_SPIT = 11;
    private static int heatStringID = -1;
    public com.maddox.il2.fm.FmSounds isnd;
    private com.maddox.il2.fm.FlightModel reference;
    private static boolean bTFirst;
    public java.lang.String soundName;
    public java.lang.String startStopName;
    public java.lang.String propName;
    private int number;
    private int type;
    private int cylinders;
    private float engineMass;
    private float wMin;
    private float wNom;
    private float wMax;
    private float wWEP;
    private float wMaxAllowed;
    public int wNetPrev;
    public float engineMoment;
    private float engineMomentMax;
    private float engineBoostFactor;
    private float engineAfterburnerBoostFactor;
    private float engineDistAM;
    private float engineDistBM;
    private float engineDistCM;
    private float producedDistabilisation;
    private boolean bRan;
    private com.maddox.JGP.Point3f enginePos;
    private com.maddox.JGP.Vector3f engineVector;
    private com.maddox.JGP.Vector3f engineForce;
    private com.maddox.JGP.Vector3f engineTorque;
    private float engineDamageAccum;
    private float _1_wMaxAllowed;
    private float _1_wMax;
    private float RPMMin;
    private float RPMNom;
    private float RPMMax;
    private float Vopt;
    private float pressureExtBar;
    private double momForFuel;
    public double addVflow;
    public double addVside;
    private com.maddox.JGP.Point3f propPos;
    private float propReductor;
    private int propAngleDeviceType;
    private float propAngleDeviceMinParam;
    private float propAngleDeviceMaxParam;
    private float propAngleDeviceAfterburnerParam;
    private int propDirection;
    private float propDiameter;
    private float propMass;
    private float propI;
    public com.maddox.JGP.Vector3d propIW;
    private float propSEquivalent;
    private float propr;
    private float propPhiMin;
    private float propPhiMax;
    private float propPhi;
    private float propPhiW;
    private float propAoA;
    private float propAoA0;
    private float propAoACrit;
    private float propAngleChangeSpeed;
    private float propForce;
    public float propMoment;
    private float propTarget;
    private int mixerType;
    private float mixerLowPressureBar;
    private float horsePowers;
    private float thrustMax;
    private int cylindersOperable;
    private float engineI;
    private float engineAcceleration;
    private boolean bMagnetos[] = {
        true, true
    };
    private boolean bIsAutonomous;
    private boolean bIsMaster;
    private boolean bIsStuck;
    private boolean bIsInoperable;
    private boolean bIsAngleDeviceOperational;
    private boolean isPropAngleDeviceHydroOperable;
    private int engineCarburetorType;
    private float FuelConsumptionP0;
    private float FuelConsumptionP05;
    private float FuelConsumptionP1;
    private float FuelConsumptionPMAX;
    private int compressorType;
    public int compressorMaxStep;
    private float compressorPMax;
    private float compressorManifoldPressure;
    public float compressorAltitudes[];
    private float compressorPressure[];
    private float compressorAltMultipliers[];
    private float compressorRPMtoP0;
    private float compressorRPMtoCurvature;
    private float compressorRPMtoPMax;
    private float compressorRPMtoWMaxATA;
    private float compressorSpeedManifold;
    private float compressorRPM[];
    private float compressorATA[];
    private int nOfCompPoints;
    private boolean compressorStepFound;
    private float compressorManifoldThreshold;
    private float afterburnerCompressorFactor;
    private float _1_P0;
    private float compressor1stThrottle;
    private float compressor2ndThrottle;
    private float compressorPAt0;
    private int afterburnerType;
    private boolean afterburnerChangeW;
    private int stage;
    private int oldStage;
    private long timer;
    private long given;
    private float rpm;
    public float w;
    private float aw;
    private float oldW;
    private float readyness;
    private float oldReadyness;
    private float radiatorReadyness;
    private float rearRush;
    public float tOilIn;
    public float tOilOut;
    public float tWaterOut;
    public float tCylinders;
    private float tWaterCritMin;
    public float tWaterCritMax;
    private float tOilCritMin;
    public float tOilCritMax;
    private float tWaterMaxRPM;
    public float tOilOutMaxRPM;
    private float tOilInMaxRPM;
    private float tChangeSpeed;
    private float timeOverheat;
    private float timeUnderheat;
    private float timeCounter;
    private float oilMass;
    private float waterMass;
    private float Ptermo;
    private float R_air;
    private float R_oil;
    private float R_water;
    private float R_cyl_oil;
    private float R_cyl_water;
    private float C_eng;
    private float C_oil;
    private float C_water;
    private boolean bHasThrottleControl;
    private boolean bHasAfterburnerControl;
    private boolean bHasPropControl;
    private boolean bHasRadiatorControl;
    private boolean bHasMixControl;
    private boolean bHasMagnetoControl;
    private boolean bHasExtinguisherControl;
    private boolean bHasCompressorControl;
    private boolean bHasFeatherControl;
    private int extinguishers;
    private float controlThrottle;
    public float controlRadiator;
    private boolean controlAfterburner;
    private float controlProp;
    private boolean bControlPropAuto;
    private float controlMix;
    private int controlMagneto;
    private int controlCompressor;
    private int controlFeather;
    public double zatizeni;
    public float coolMult;
    private int controlPropDirection;
    private float neg_G_Counter;
    private boolean bFullT;
    private boolean bFloodCarb;
    private boolean bWepRpmInLowGear;
    public boolean fastATA;
    private com.maddox.JGP.Vector3f old_engineForce;
    private com.maddox.JGP.Vector3f old_engineTorque;
    private float updateStep;
    private float updateLast;
    private float fricCoeffT;
    private static com.maddox.JGP.Vector3f tmpV3f = new Vector3f();
    private static com.maddox.JGP.Vector3d tmpV3d1 = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV3d2 = new Vector3d();
    private static com.maddox.JGP.Point3f safeloc = new Point3f();
    private static com.maddox.JGP.Point3d safeLoc = new Point3d();
    private static com.maddox.JGP.Vector3f safeVwld = new Vector3f();
    private static com.maddox.JGP.Vector3f safeVflow = new Vector3f();
    private static boolean tmpB;
    private static float tmpF;
    private int engineNoFuelHUDLogId;

}

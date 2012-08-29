// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MissilePhysics.java

package com.maddox.il2.objects.weapons;


public class MissilePhysics
{

    private MissilePhysics()
    {
    }

    public static float getAirPressure(float theAltitude)
    {
        float fBase = 1.0F - (0.0065F * theAltitude) / 288.15F;
        float fExponent = 5.255781F;
        return 101325F * (float)java.lang.Math.pow(fBase, fExponent);
    }

    public static float getAirPressureFactor(float theAltitude)
    {
        return com.maddox.il2.objects.weapons.MissilePhysics.getAirPressure(theAltitude) / 101325F;
    }

    public static float getAirDensity(float theAltitude)
    {
        return (com.maddox.il2.objects.weapons.MissilePhysics.getAirPressure(theAltitude) * 0.0289644F) / (8.31447F * (288.15F - 0.0065F * theAltitude));
    }

    public static float getAirDensityFactor(float theAltitude)
    {
        return com.maddox.il2.objects.weapons.MissilePhysics.getAirDensity(theAltitude) / 1.225F;
    }

    public static float getCwFactorForMach(float theMachValue)
    {
        int i = 0;
        for(i = 0; i < fMachCwX.length && fMachCwX[i] <= theMachValue; i++);
        if(i == 0)
        {
            return fMachCwY[0];
        } else
        {
            float baseFactor = fMachCwY[i - 1];
            float spanFactor = fMachCwY[i] - baseFactor;
            float baseMach = fMachCwX[i - 1];
            float spanMach = fMachCwX[i] - baseMach;
            float spanMult = (theMachValue - baseMach) / spanMach;
            return baseFactor + spanFactor * spanMult;
        }
    }

    public static float getMachForAlt(float theAltValue)
    {
        theAltValue /= 1000F;
        int i = 0;
        for(i = 0; i < fMachAltX.length && fMachAltX[i] <= theAltValue; i++);
        if(i == 0)
        {
            return fMachAltY[0];
        } else
        {
            float baseMach = fMachAltY[i - 1];
            float spanMach = fMachAltY[i] - baseMach;
            float baseAlt = fMachAltX[i - 1];
            float spanAlt = fMachAltX[i] - baseAlt;
            float spanMult = (theAltValue - baseAlt) / spanAlt;
            return baseMach + spanMach * spanMult;
        }
    }

    public static float getMpsFromKmh(float kilometersPerHour)
    {
        return kilometersPerHour / 3.6F;
    }

    public static float getDragForce(float theFrontSqM, float theDragCoefficient, float theAltitudeM, float theSpeedMps)
    {
        float altSpeedMach = theSpeedMps / com.maddox.il2.objects.weapons.MissilePhysics.getMpsFromKmh(com.maddox.il2.objects.weapons.MissilePhysics.getMachForAlt(theAltitudeM));
        float altSpeedCw = theDragCoefficient * com.maddox.il2.objects.weapons.MissilePhysics.getCwFactorForMach(altSpeedMach);
        float altDensity = com.maddox.il2.objects.weapons.MissilePhysics.getAirDensity(theAltitudeM);
        float dragForce = (theFrontSqM * altSpeedCw * altDensity * theSpeedMps * theSpeedMps) / 2.0F;
        return dragForce;
    }

    public static float getDragInGravity(float theFrontSqM, float theDragCoefficient, float theAltitudeM, float theSpeedMps, float theTangageDeg, float theMass)
    {
        float fDragForce = com.maddox.il2.objects.weapons.MissilePhysics.getDragForce(theFrontSqM, theDragCoefficient, theAltitudeM, theSpeedMps);
        double alpha = java.lang.Math.toRadians(theTangageDeg);
        fDragForce = (float)((double)fDragForce + java.lang.Math.sin(alpha) * 9.8066501617431641D * (double)theMass);
        return fDragForce;
    }

    public static float getForceInGravity(float theTangageDeg, float theForceN, float theMass)
    {
        float g = 9.80665F * theMass;
        double alpha = java.lang.Math.toRadians(90F + java.lang.Math.abs(theTangageDeg));
        float resForce = 0.0F;
        if(theTangageDeg < 0.0F)
        {
            if(theForceN == 0.0F)
                return g;
            if(theForceN < g)
                return 0.0F;
            double beta = java.lang.Math.asin((java.lang.Math.sin(alpha) * (double)g) / (double)theForceN);
            double gamma = 3.1415926535897931D - alpha - beta;
            resForce = g * (float)(java.lang.Math.sin(gamma) / java.lang.Math.sin(beta));
        } else
        {
            resForce = (float)java.lang.Math.sqrt((double)(g * g + theForceN * theForceN) + (double)(2.0F * g * theForceN) * java.lang.Math.cos(alpha));
        }
        return resForce;
    }

    public static float getDegPerSec(float fSpeed, float fMaxG)
    {
        return (fMaxG * 9.80665F * 180F) / (fSpeed * 3.141593F);
    }

    public static float getGForce(float fSpeed, float fDegPerSec)
    {
        return (fDegPerSec * fSpeed * 3.141593F) / 1765.197F;
    }

    public static final float G_CONST = 9.80665F;
    private static final float p0 = 101325F;
    private static final float T0 = 288.15F;
    private static final float L = 0.0065F;
    private static final float R = 8.31447F;
    private static final float M = 0.0289644F;
    private static final float Rho0 = 1.225F;
    private static float fMachCwX[] = {
        0.0F, 0.8F, 0.9F, 1.0F, 1.1F, 1.2F, 1.3F, 1.6F, 2.0F, 3F, 
        5F, 3.402823E+038F
    };
    private static float fMachCwY[] = {
        1.0F, 1.1F, 2.0F, 2.3F, 2.4F, 2.4F, 2.2F, 1.8F, 1.5F, 1.2F, 
        1.1F, 1.0F
    };
    private static float fMachAltX[] = {
        0.0F, 11F, 20.1F, 47.4F, 51.4F, 86F, 90F, 500F, 3.402823E+038F
    };
    private static float fMachAltY[] = {
        1225F, 1062.3F, 1062.3F, 1187.3F, 1187.3F, 986.5F, 986.5F, 1108.8F, 1108.8F
    };

}

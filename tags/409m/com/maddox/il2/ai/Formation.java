// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Formation.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.A6M;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.B5N;
import com.maddox.il2.objects.air.BF_109;
import com.maddox.il2.objects.air.BF_110;
import com.maddox.il2.objects.air.B_17;
import com.maddox.il2.objects.air.B_25;
import com.maddox.il2.objects.air.B_29;
import com.maddox.il2.objects.air.D3A;
import com.maddox.il2.objects.air.FW_190;
import com.maddox.il2.objects.air.G4M;
import com.maddox.il2.objects.air.H8K;
import com.maddox.il2.objects.air.HE_111H2;
import com.maddox.il2.objects.air.IL_2;
import com.maddox.il2.objects.air.JU_88;
import com.maddox.il2.objects.air.KI_43;
import com.maddox.il2.objects.air.KI_46;
import com.maddox.il2.objects.air.KI_61;
import com.maddox.il2.objects.air.KI_84;
import com.maddox.il2.objects.air.ME_323;
import com.maddox.il2.objects.air.N1K;
import com.maddox.il2.objects.air.PE_2;
import com.maddox.il2.objects.air.PE_8;
import com.maddox.il2.objects.air.SBD;
import com.maddox.il2.objects.air.SPITFIRE;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TA_152H1;
import com.maddox.il2.objects.air.TBF;
import com.maddox.il2.objects.air.TB_3;
import com.maddox.il2.objects.air.TU_2;
import com.maddox.il2.objects.air.YAK;

// Referenced classes of package com.maddox.il2.ai:
//            Wing

public class Formation
{

    public Formation()
    {
    }

    public static final void generate(com.maddox.il2.objects.air.Aircraft aaircraft[])
    {
        com.maddox.il2.ai.Formation.gen(aaircraft, WR);
    }

    private static final float scaleCoeff(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft instanceof com.maddox.il2.objects.air.ME_323)
            return 5F;
        if((aircraft instanceof com.maddox.il2.objects.air.PE_8) || (aircraft instanceof com.maddox.il2.objects.air.TB_3) || (aircraft instanceof com.maddox.il2.objects.air.B_17) || (aircraft instanceof com.maddox.il2.objects.air.B_29))
            return 3.5F;
        if(aircraft instanceof com.maddox.il2.objects.air.TA_152H1)
            return 2.0F;
        if(aircraft instanceof com.maddox.il2.objects.air.SBD)
            return 1.8F;
        if(aircraft instanceof com.maddox.il2.objects.air.TBF)
            return 1.9F;
        if(aircraft instanceof com.maddox.il2.objects.air.FW_190)
            return 1.4F;
        return (aircraft instanceof com.maddox.il2.objects.air.Scheme1) ? 1.2F : 2.2F;
    }

    public static final void gather(com.maddox.il2.fm.FlightModel flightmodel, byte byte0)
    {
        com.maddox.il2.ai.Formation.gather(flightmodel, byte0, flightmodel.Offset);
    }

    public static final void gather(com.maddox.il2.fm.FlightModel flightmodel, byte byte0, com.maddox.JGP.Vector3d vector3d)
    {
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)flightmodel.actor;
        com.maddox.il2.fm.FlightModel flightmodel1 = flightmodel;
        int i = ((com.maddox.il2.ai.air.Maneuver)flightmodel).Group.numInGroup(aircraft);
        float f = flightmodel.formationScale;
        switch(byte0)
        {
        default:
            break;

        case 0: // '\0'
            if((aircraft instanceof com.maddox.il2.objects.air.BF_109) || (aircraft instanceof com.maddox.il2.objects.air.BF_110) || (aircraft instanceof com.maddox.il2.objects.air.FW_190) || (aircraft instanceof com.maddox.il2.objects.air.SPITFIRE))
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)7, vector3d);
                return;
            }
            if((aircraft instanceof com.maddox.il2.objects.air.IL_2) || (aircraft instanceof com.maddox.il2.objects.air.YAK) || (aircraft instanceof com.maddox.il2.objects.air.PE_2) || (aircraft instanceof com.maddox.il2.objects.air.TU_2))
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)6, vector3d);
                return;
            }
            if((aircraft instanceof com.maddox.il2.objects.air.A6M) || (aircraft instanceof com.maddox.il2.objects.air.B5N) || (aircraft instanceof com.maddox.il2.objects.air.D3A) || (aircraft instanceof com.maddox.il2.objects.air.G4M) || (aircraft instanceof com.maddox.il2.objects.air.KI_43) || (aircraft instanceof com.maddox.il2.objects.air.KI_46) || (aircraft instanceof com.maddox.il2.objects.air.KI_84) || (aircraft instanceof com.maddox.il2.objects.air.N1K) || (aircraft instanceof com.maddox.il2.objects.air.H8K) || (aircraft instanceof com.maddox.il2.objects.air.KI_61))
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)6, vector3d);
                return;
            }
            if((aircraft instanceof com.maddox.il2.objects.air.JU_88) || (aircraft instanceof com.maddox.il2.objects.air.HE_111H2))
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)8, vector3d);
                return;
            }
            if(aircraft instanceof com.maddox.il2.objects.air.ME_323)
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)8, vector3d);
                return;
            }
            if((aircraft instanceof com.maddox.il2.objects.air.PE_8) || (aircraft instanceof com.maddox.il2.objects.air.B_17) || (aircraft instanceof com.maddox.il2.objects.air.B_25) || (aircraft instanceof com.maddox.il2.objects.air.B_29))
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)6, vector3d);
                return;
            } else
            {
                com.maddox.il2.ai.Formation.gather(flightmodel, (byte)2, vector3d);
                return;
            }

        case 1: // '\001'
            com.maddox.il2.ai.Formation.gather(flightmodel, flightmodel.formationType, vector3d);
            return;

        case 2: // '\002'
            flightmodel.formationType = byte0;
            vector3d.set(25D, 25D, 0.0D);
            break;

        case 3: // '\003'
            flightmodel.formationType = byte0;
            vector3d.set(25D, -25D, 0.0D);
            break;

        case 4: // '\004'
            flightmodel.formationType = byte0;
            if(i == 0)
                vector3d.set(25D, 75D, 0.0D);
            else
                vector3d.set(1.0D, 33D, 0.0D);
            break;

        case 5: // '\005'
            flightmodel.formationType = byte0;
            if(i == 0)
                vector3d.set(120D, 0.0D, 15D);
            else
                vector3d.set(80D, 0.0D, 10D);
            vector3d.scale(f);
            return;

        case 6: // '\006'
            flightmodel.formationType = byte0;
            switch(i)
            {
            case 0: // '\0'
                vector3d.set(55D, 55D, 0.0D);
                break;

            case 1: // '\001'
                vector3d.set(25D, 25D, 0.0D);
                break;

            case 2: // '\002'
                vector3d.set(0.0D, -50D, 0.0D);
                break;

            case 3: // '\003'
                vector3d.set(25D, -25D, 0.0D);
                break;
            }
            break;

        case 7: // '\007'
            flightmodel.formationType = byte0;
            switch(i)
            {
            case 0: // '\0'
                vector3d.set(25D, 25D, 0.0D);
                break;

            case 1: // '\001'
                vector3d.set(15D, 30D, 0.0D);
                break;

            case 2: // '\002'
                vector3d.set(25D, -60D, 0.0D);
                break;

            case 3: // '\003'
                vector3d.set(15D, -20D, 0.0D);
                break;
            }
            break;

        case 8: // '\b'
            flightmodel.formationType = byte0;
            switch(i)
            {
            case 0: // '\0'
                vector3d.set(75D, 30D, 0.0D);
                break;

            case 1: // '\001'
                vector3d.set(25D, 25D, 0.0D);
                break;

            case 2: // '\002'
                vector3d.set(0.0D, -50D, 0.0D);
                break;

            case 3: // '\003'
                vector3d.set(25D, 25D, 0.0D);
                break;
            }
            break;
        }
        vector3d.scale(f * com.maddox.il2.ai.Formation.scaleCoeff(aircraft));
    }

    public static final void leaderOffset(com.maddox.il2.fm.FlightModel flightmodel, byte byte0, com.maddox.JGP.Vector3d vector3d)
    {
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)flightmodel.actor;
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)flightmodel.actor.getOwner();
        int i;
        if(wing != null)
            i = wing.indexInSquadron();
        else
            i = 0;
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            vector3d.set(300D, -150D, 0.0D);
            break;

        case 1: // '\001'
            if(byte0 != 2)
                vector3d.set(100D, 100D, 0.0D);
            else
                vector3d.set(200D, 200D, 0.0D);
            break;

        case 2: // '\002'
            if(byte0 != 3 && byte0 != 6)
                vector3d.set(150D, -150D, 0.0D);
            else
                vector3d.set(210D, -210D, 0.0D);
            break;

        case 3: // '\003'
            if(byte0 != 5)
                vector3d.set(150D, 0.0D, 0.0D);
            else
                vector3d.set(300D, 0.0D, 0.0D);
            break;
        }
        vector3d.scale(0.69999999999999996D * (double)com.maddox.il2.ai.Formation.scaleCoeff(aircraft));
    }

    private static final void gen(com.maddox.il2.objects.air.Aircraft aaircraft[], com.maddox.JGP.Vector3f vector3f)
    {
        dd.set(vector3f);
        aaircraft[0].pos.getAbsOrient().transform(dd);
        int j = 0;
        for(int i = 1; i < aaircraft.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(aaircraft[i]))
            {
                aaircraft[i].FM.Offset.set(vector3f);
                aaircraft[i].FM.Leader = aaircraft[j].FM;
                aaircraft[j].FM.Wingman = aaircraft[i].FM;
                aaircraft[j].pos.getAbs(Pd);
                Pd.sub(dd);
                aaircraft[i].pos.setAbs(Pd);
                j = i;
            }

    }

    public static final byte F_DEFAULT = 0;
    public static final byte F_PREVIOUS = 1;
    public static final byte F_ECHELONRIGHT = 2;
    public static final byte F_ECHELONLEFT = 3;
    public static final byte F_LINEABREAST = 4;
    public static final byte F_LINEASTERN = 5;
    public static final byte F_VIC = 6;
    public static final byte F_FINGERFOUR = 7;
    public static final byte F_DIAMOND = 8;
    private static final com.maddox.JGP.Vector3f WR = new Vector3f(100F, 100F, 0.0F);
    private static final com.maddox.JGP.Vector3d dd = new Vector3d();
    private static final com.maddox.JGP.Point3d Pd = new Point3d();

}

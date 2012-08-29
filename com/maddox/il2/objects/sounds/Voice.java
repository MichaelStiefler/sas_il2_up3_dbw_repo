// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Voice.java

package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.GameTrack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.JU_88MSTL;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.trains.Train;
import com.maddox.il2.objects.trains.Wagon;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.cars.CarGeneric;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.sounds:
//            VoiceBase, VoiceFX

public class Voice extends com.maddox.il2.objects.sounds.VoiceBase
{

    public Voice()
    {
        SpeakAttackByRockets = new int[2][4];
        SpeakAttackByBombs = new int[2][4];
        SpeakTargetDestroyed = new int[2][4];
        SpeakDanger = new int[2][4];
        SpeakHelpNeeded = new int[2][4];
        SpeakClearTail = new int[2][4];
        SpeakCoverMe = new int[2][4];
        SpeakCoverProvided = new int[2][4];
        SpeakHelpFromAir = new int[2][4];
        SpeakNiceKill = new int[2][4];
        SpeakEndOfAmmo = new int[2][4];
        SpeakMayday = new int[2][4];
        SpeakCheckFire = new int[2][4];
        SpeakHint = new int[2][4];
        SpeakToReturn = new int[2][4];
        SpeakBailOut = new int[2][4];
        SpeakAttackGround = new int[2][4];
        SpeakAttackMyTarget = new int[2][4];
        SpeakAttackBombers = new int[2][4];
        SpeakTargetAll = new int[2][4];
        SpeakDropTanks = new int[2][4];
        SpeakBreak = new int[2][4];
        SpeakRejoin = new int[2][4];
        SpeakTightFormation = new int[2][4];
        SpeakLoosenFormation = new int[2][4];
        SpeakOk = new int[2][4];
        SpeakLandingPermited = new int[2][4];
        SpeakBombing = new int[2];
        SpeakEndBombing = new int[2];
        SpeakEndGattack = new int[2];
        SpeakDeviateSmall = new int[2];
        SpeakDeviateBig = new int[2];
        SpeakHeading = new int[2];
        SpeakAltitude = new int[2];
        SpeakNearBombers = new int[2];
        Speak1minute = new int[2];
        Speak5minutes = new int[2];
        SpeakBombersUnderAttack = new int[2];
        SpeakBombersUnderAttack1 = new int[2];
        SpeakBombersUnderAttack2 = new int[2];
        SpeakEnemyDetected = new int[2];
    }

    public static com.maddox.il2.objects.sounds.Voice cur()
    {
        return com.maddox.il2.ai.World.cur().voicebase;
    }

    public static boolean isEnableVoices()
    {
        return com.maddox.il2.objects.sounds.VoiceFX.isEnabled();
    }

    public static void setEnableVoices(boolean flag)
    {
        com.maddox.il2.objects.sounds.VoiceFX.setEnabled(flag);
    }

    static boolean frequency()
    {
        return com.maddox.il2.game.Main3D.cur3D().ordersTree.frequency().booleanValue();
    }

    public static void setSyncMode(int i)
    {
        syncMode = i;
    }

    public static void reset()
    {
        for(int i = 0; i < str.length; i++)
            str[i] = 0;

        for(int j = 0; j < str2.length; j++)
        {
            if(str2[j] == null)
                continue;
            for(int k = 0; k < str2[j].length; k++)
                str2[j][k] = 0;

        }

    }

    public static void endSession()
    {
        com.maddox.il2.objects.sounds.VoiceFX.end();
    }

    private static boolean isPunctChar(char c)
    {
        return c == '!' || c == '?' || c == '.' || c == ',' || c == '@';
    }

    public static int actorVoice(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(aircraft == null || !com.maddox.il2.engine.Actor.isValid(aircraft) || (aircraft instanceof com.maddox.il2.objects.air.JU_88MSTL))
            return 0;
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Maneuver)
        {
            if(((com.maddox.il2.ai.air.Maneuver)aircraft.FM).silence)
                return 0;
            if(((com.maddox.il2.ai.air.Maneuver)aircraft.FM).kamikaze)
                return 0;
        }
        com.maddox.il2.ai.Squadron squadron = aircraft.getSquadron();
        if(squadron == null)
            return 0;
        com.maddox.il2.ai.Wing wing = null;
        Object obj = null;
        if(i == 3 || i == 0)
        {
            if(aircraft.isNet() && !aircraft.isNetMaster())
                return 0;
            float f = 0.0F;
            for(int k = 0; k < squadron.wing.length; k++)
                if(squadron.wing[k] != null)
                    f += squadron.wing[k].aircReady();

            if(f < 2.0F)
                return 0;
        }
        switch(i)
        {
        case 3: // '\003'
            int j = 0;
            do
            {
                if(j >= squadron.wing.length)
                    break;
                if(squadron.wing[j] != null)
                {
                    wing = squadron.wing[j];
                    break;
                }
                j++;
            } while(true);
            if(wing == null)
                return 0;
            com.maddox.il2.objects.air.Aircraft aircraft1;
            if(wing.airc.length > 0 && wing.airc[0] != null)
                aircraft1 = wing.airc[0];
            else
                return 0;
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft1))
                return 0;
            com.maddox.il2.ai.World.cur();
            if(aircraft1 == com.maddox.il2.ai.World.getPlayerAircraft())
                return 0;
            return aircraft1.FM.AS.astatePilotStates[0] <= 50 ? 1 : 0;

        case 1: // '\001'
            com.maddox.il2.objects.air.Aircraft aircraft2 = com.maddox.il2.ai.War.getNearestFriend(aircraft);
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft2))
                return 0;
            if(aircraft2 == com.maddox.il2.ai.World.getPlayerAircraft())
                return 0;
            if(com.maddox.il2.game.Mission.isDogfight())
                return 0;
            else
                return pilotVoice[aircraft2.getWing().indexInSquadron() * 4 + aircraft2.aircIndex()];

        case 4: // '\004'
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
                return 0;
            return aircraft != com.maddox.il2.ai.World.getPlayerAircraft() || !aircraft.FM.turret[0].bIsAIControlled ? 0 : 5;

        case 0: // '\0'
            if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
                return 0;
            if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
                return 0;
            if(aircraft.FM.AS.astatePilotStates[0] > 50)
                return 0;
            else
                return pilotVoice[aircraft.getWing().indexInSquadron() * 4 + aircraft.aircIndex()];

        case 6: // '\006'
            return 2;

        case 5: // '\005'
            return 4;

        case 2: // '\002'
        default:
            return 0;
        }
    }

    public static void new_speak(int i, int j, java.lang.String s, int ai[], int k)
    {
        com.maddox.il2.objects.sounds.Voice.speak(i, j, s, ai, k, true, true);
    }

    public static void speak(int i, int j, java.lang.String s, int ai[], int k)
    {
        com.maddox.il2.objects.sounds.Voice.speak(i, j, s, ai, k, true, false);
    }

    public static void speak(int i, int j, java.lang.String s, int ai[], int k, boolean flag, boolean flag1)
    {
        int l = syncMode;
        syncMode = 0;
        if(i == 0)
            return;
        if(flag && i != 5)
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).speekVoice(syncMode, i, j, s, ai, k, flag1);
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        int i1 = com.maddox.il2.ai.World.getPlayerArmy();
        if(com.maddox.il2.game.Main3D.cur3D().ordersTree.frequency().booleanValue())
        {
            if(j != i1)
                return;
        } else
        if(j == i1)
            return;
        com.maddox.il2.objects.sounds.Voice.play(l, i, j, s, ai, flag1);
        if(com.maddox.il2.ai.World.cur().isDebugFM())
        {
            java.lang.String s1 = "";
            for(int k1 = 0; k1 < ai.length && ai[k1] != 0; k1++)
                s1 = s1 + " " + vbStr[ai[k1]];

            java.lang.System.out.print("AN: ");
            java.lang.System.out.print(i);
            java.lang.System.out.print("  Army: ");
            java.lang.System.out.print(j);
            java.lang.System.out.println("  : " + s1);
        }
        for(int j1 = 0; j1 < ai.length && ai[j1] != 0; j1++)
            ai[j1] = 0;

    }

    public static void play(int i, int j, int k, java.lang.String s, int ai[], boolean flag)
    {
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackPlay() != null)
            return;
        if(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord() != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(4);
                netmsgguaranted.writeInt(i);
                netmsgguaranted.writeInt(j);
                netmsgguaranted.writeInt(k);
                netmsgguaranted.writeBoolean(flag);
                int l = ai.length;
                netmsgguaranted.writeInt(l);
                for(int i1 = 0; i1 < l; i1++)
                    netmsgguaranted.writeInt(ai[i1]);

                if(s != null)
                    netmsgguaranted.write255(s);
                com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().postTo(com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().channel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
        com.maddox.il2.game.HUD.message(ai, j, k, flag);
        com.maddox.il2.objects.sounds.VoiceFX.play(i, j, k, s, ai);
    }

    public static boolean netInputPlay(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readInt();
        int j = netmsginput.readInt();
        int k = netmsginput.readInt();
        boolean flag = netmsginput.readBoolean();
        int l = netmsginput.readInt();
        int ai[] = new int[l];
        for(int i1 = 0; i1 < l; i1++)
            ai[i1] = netmsginput.readInt();

        java.lang.String s = null;
        if(netmsginput.available() > 0)
            s = netmsginput.read255();
        com.maddox.il2.game.HUD.message(ai, j, k, flag);
        com.maddox.il2.objects.sounds.VoiceFX.play(i, j, k, s, ai);
        return true;
    }

    public static void speak(int i, int j, java.lang.String s, int k, int l)
    {
        str[0] = k;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.speak(i, j, s, str, l);
    }

    public static void new_speak(int i, int j, java.lang.String s, int k, int l)
    {
        str[0] = k;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.new_speak(i, j, s, str, l);
    }

    public static void speakRandom(int i, int j, java.lang.String s, int ai[], int k)
    {
        int l;
        for(l = 0; l < ai.length && ai[l] != 0; l++);
        if(l < 1)
        {
            return;
        } else
        {
            int i1 = rnd.nextInt(0, l - 1);
            com.maddox.il2.objects.sounds.Voice.speak(i, j, s, ai[i1], k);
            return;
        }
    }

    public static void speakNewRandom(int i, int j, java.lang.String s, int ai[], int k)
    {
        int l;
        for(l = 0; l < ai.length && ai[l] != 0; l++);
        if(l < 1)
        {
            return;
        } else
        {
            int i1 = rnd.nextInt(0, l - 1);
            com.maddox.il2.objects.sounds.Voice.new_speak(i, j, s, ai[i1], k);
            return;
        }
    }

    public static void airSpeaksArray(com.maddox.il2.objects.air.Aircraft aircraft, int i, int ai[], int j)
    {
        int k = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, i);
        if(k == 0)
        {
            return;
        } else
        {
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.speak(k, l, s, ai, j);
            return;
        }
    }

    public static void airSpeaksNewArray(com.maddox.il2.objects.air.Aircraft aircraft, int i, int ai[], int j)
    {
        int k = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, i);
        if(k == 0)
        {
            return;
        } else
        {
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.new_speak(k, l, s, ai, j);
            return;
        }
    }

    public static void airSpeaks(com.maddox.il2.objects.air.Aircraft aircraft, int i, int j, int k)
    {
        str[0] = j;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, i, str, k);
    }

    public static void airSpeaks(com.maddox.il2.objects.air.Aircraft aircraft, int i, int j)
    {
        str[0] = i;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 0, str, j);
    }

    public static void airSpeaksNew(com.maddox.il2.objects.air.Aircraft aircraft, int i, int j)
    {
        str[0] = i;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 0, str, j);
    }

    public static void speakRandomArray(com.maddox.il2.objects.air.Aircraft aircraft, int ai[][], int i)
    {
        for(int j = 0; j < ai.length && ai[j] != null; j++)
        {
            if(ai[j][0] == 0)
                continue;
            if(ai[j][1] == 0)
            {
                com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, ai[j][0], i);
                continue;
            }
            byte byte0;
            if(ai[j][2] == 0)
                byte0 = 2;
            else
                byte0 = 3;
            com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, ai[j][rnd.nextInt(0, byte0 - 1)], i);
        }

        com.maddox.il2.objects.sounds.Voice.reset();
    }

    public static void speakRandom(com.maddox.il2.objects.air.Aircraft aircraft, int ai[], int i)
    {
        int j;
        for(j = 0; j < ai.length && ai[j] != 0; j++);
        if(j < 1)
        {
            return;
        } else
        {
            int k = rnd.nextInt(0, j - 1);
            com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, ai[k], i);
            return;
        }
    }

    public static void speakNewRandom(com.maddox.il2.objects.air.Aircraft aircraft, int ai[], int i)
    {
        int j;
        for(j = 0; j < ai.length && ai[j] != 0; j++);
        if(j < 1)
        {
            return;
        } else
        {
            int k = rnd.nextInt(0, j - 1);
            com.maddox.il2.objects.sounds.Voice.airSpeaksNew(aircraft, ai[k], i);
            return;
        }
    }

    public static void speakAltitude(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int j = (int)(com.maddox.rts.Time.current() / 1000L);
        int k = aircraft.getArmy() - 1 & 1;
        if(j < com.maddox.il2.objects.sounds.Voice.cur().SpeakAltitude[k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakAltitude[k] = j + 20;
        str[0] = 118;
        if(i > 10000)
            i = 10000;
        if(i < 1)
            i = 1;
        str[1] = altitudes[i / 500];
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 3);
    }

    public static void speakHeading(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.JGP.Vector3f vector3f)
    {
        float f = 57.32484F * (float)java.lang.Math.atan2(vector3f.x, vector3f.y);
        int i = (int)f;
        i = (i + 180) % 360;
        com.maddox.il2.objects.sounds.Voice.speakHeading(aircraft, i);
    }

    public static void speakHeading(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.JGP.Vector3d vector3d = aircraft.FM.Vwld;
        float f = 57.32484F * (float)java.lang.Math.atan2(vector3d.x, vector3d.y);
        int i = (int)f;
        i = (i + 180) % 360;
        com.maddox.il2.objects.sounds.Voice.speakHeading(aircraft, i);
    }

    public static void speakHeadingToHome(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.JGP.Point3d point3d)
    {
        float f = 57.32484F * (float)java.lang.Math.atan2(point3d.x, point3d.y);
        int i = (int)f;
        for(i = (i + 180) % 360; i < 0; i += 360);
        for(; i >= 360; i -= 360);
        i /= 30;
        int j = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 5);
        int k = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.new_speak(j, k, s, 235, 2);
        str[0] = 165;
        str[1] = headings[i];
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 5, str, 2);
        com.maddox.il2.objects.sounds.Voice.speak(j, k, s, 252, 2);
    }

    public static void speakHeadingToTarget(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.JGP.Point3d point3d)
    {
        float f = 57.32484F * (float)java.lang.Math.atan2(point3d.x, point3d.y);
        int i = (int)f;
        for(i = (i + 180) % 360; i < 0; i += 360);
        for(; i >= 360; i -= 360);
        i /= 30;
        int j = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 5);
        int k = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        str[0] = 165;
        str[1] = headings[i];
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 5, str, 2);
        com.maddox.il2.objects.sounds.Voice.speak(j, k, s, 252, 2);
    }

    public static void speakHeading(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int j = (int)(com.maddox.rts.Time.current() / 1000L);
        int k = aircraft.getArmy() - 1 & 1;
        if(j < com.maddox.il2.objects.sounds.Voice.cur().SpeakHeading[k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakHeading[k] = j + 20;
        for(; i < 0; i += 360);
        for(; i >= 360; i -= 360);
        i /= 30;
        str[0] = 165;
        str[1] = headings[i];
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 3);
    }

    public static void speak5minutes(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().Speak5minutes[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().Speak5minutes[j] = i + 40;
        str[0] = 81;
        if(rnd.nextFloat() < 0.5F)
            str[1] = 49;
        else
            str[1] = 74;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 1);
    }

    public static void speak1minute(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().Speak1minute[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().Speak1minute[j] = i + 40;
        str[0] = 81;
        if(rnd.nextFloat() < 0.5F)
            str[1] = 22;
        else
            str[1] = 73;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 1);
    }

    public static void speakBeginGattack(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        if(aircraft.aircNumber() < 2)
            return;
        int i = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 3);
        if(i == 0)
            return;
        int j = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.new_speak(i, j, s, 81, 1);
        com.maddox.il2.objects.sounds.Voice.speak(i, j, s, 169, 1);
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot)
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
            if(com.maddox.il2.engine.Actor.isValid(pilot.target_ground))
            {
                com.maddox.il2.engine.Actor actor = pilot.target_ground;
                if(actor instanceof com.maddox.il2.objects.vehicles.cars.CarGeneric)
                    str[0] = 162;
                else
                if(actor instanceof com.maddox.il2.objects.vehicles.tanks.TankGeneric)
                    str[0] = 152;
                else
                if(actor instanceof com.maddox.il2.objects.vehicles.artillery.AAA)
                    str[0] = 111;
                else
                if(actor instanceof com.maddox.il2.objects.trains.Wagon)
                    str[0] = 161;
                else
                if(actor instanceof com.maddox.il2.objects.trains.Train)
                    str[0] = 161;
                else
                if(actor instanceof com.maddox.il2.ai.ground.TgtShip)
                    str[0] = 99;
                str[1] = 0;
                com.maddox.il2.objects.sounds.Voice.speak(i, j, s, str[0], 1);
                com.maddox.il2.objects.sounds.Voice.speak(i, j, s, 75, 1);
            }
            com.maddox.il2.ai.Squadron squadron = aircraft.getSquadron();
            com.maddox.il2.ai.Wing wing = aircraft.getWing();
            com.maddox.il2.ai.Wing wing1 = null;
            int k = 0;
            do
            {
                if(k >= squadron.wing.length)
                    break;
                if(squadron.wing[k] != null)
                {
                    wing1 = squadron.wing[k];
                    break;
                }
                k++;
            } while(true);
            if(wing.airc.length > 0)
            {
                for(int l = 0; l < wing.airc.length; l++)
                    if((wing1 != wing || l != 0) && wing.airc[l] != null)
                        com.maddox.il2.objects.sounds.Voice.speakAttackGround(wing.airc[l]);

            }
        }
    }

    public static void speakLeaderEndGattack()
    {
        if(!com.maddox.il2.engine.Actor.isValid(internalAir))
        {
            return;
        } else
        {
            str[0] = 81;
            str[1] = 153;
            str[2] = 136;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(internalAir, 3, str, 1);
            return;
        }
    }

    public static void speakLeaderRepeatGattack()
    {
        if(!com.maddox.il2.engine.Actor.isValid(internalAir))
        {
            return;
        } else
        {
            str[0] = 81;
            str[1] = 138;
            str[2] = 136;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(internalAir, 3, str, 1);
            return;
        }
    }

    public static void speakEndGattack(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        if(aircraft.aircIndex() > 0)
            return;
        if(aircraft instanceof com.maddox.il2.objects.air.TypeBomber)
            return;
        if(aircraft.aircNumber() < 2)
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakEndGattack[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakEndGattack[j] = i + 60;
        if(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot)
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)aircraft.FM;
            internalAir = aircraft;
            if(com.maddox.il2.engine.Actor.isValid(pilot.target_ground))
                new com.maddox.rts.MsgAction(10D) {

                    public void doAction()
                    {
                        com.maddox.il2.objects.sounds.Voice.speakLeaderRepeatGattack();
                    }

                }
;
            else
                new com.maddox.rts.MsgAction(10D) {

                    public void doAction()
                    {
                        com.maddox.il2.objects.sounds.Voice.speakLeaderEndGattack();
                    }

                }
;
        }
    }

    public static void speakAttackByRockets(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackByRockets[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackByRockets[j][k] = i + 60;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 108;
            str[1] = 79;
            str[2] = 141;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakAttackByBombs(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackByBombs[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackByBombs[j][k] = i + 60;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 85;
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 0, str, 1);
            return;
        }
    }

    public static void speakNearBombers(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakNearBombers[j])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakNearBombers[j] = i + 300;
            int k = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            str[0] = 219;
            str[1] = 220;
            str[2] = 221;
            str[3] = 136;
            str[4] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 6, str, 3);
            return;
        }
    }

    public static void speakCheckFire(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakCheckFire[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakCheckFire[j][k] = i + 15;
        int l = aircraft1.getWing().indexInSquadron() * 4 + aircraft1.aircIndex();
        if(l > 15)
        {
            return;
        } else
        {
            str[0] = aNumber[l];
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 0, str, 1);
            str[0] = 87;
            str[1] = 166;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakBombersUnderAttack(com.maddox.il2.objects.air.Aircraft aircraft, boolean flag)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
            return;
        boolean flag1 = aircraft.FM.AP.way.curr().Action == 3 && aircraft.FM.AP.getWayPointDistance() < 20000F;
        int j = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        int k = j - 1 & 1;
        if(!flag)
        {
            if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack[k])
                return;
            com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack[k] = i + 25;
            if(flag1)
                com.maddox.il2.objects.sounds.Voice.new_speak(2, j, s, 225, 2);
            else
                com.maddox.il2.objects.sounds.Voice.new_speak(2, j, s, 226, 2);
        } else
        {
            if(com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 1) == 0)
                return;
            if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack1[k])
            {
                if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack2[k])
                {
                    com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack2[k] = i + 5;
                    str[0] = 119;
                    str[1] = 215;
                    str[2] = 213;
                    str[3] = 216;
                    str[4] = 0;
                    com.maddox.il2.objects.sounds.Voice.speakNewRandom(2, j, s, str, 2);
                    str[0] = 226;
                    str[1] = 0;
                    com.maddox.il2.objects.sounds.Voice.speak(2, j, s, str, 2);
                }
                return;
            }
            com.maddox.il2.objects.sounds.Voice.cur().SpeakBombersUnderAttack1[k] = i + 25;
            if(flag1)
                com.maddox.il2.objects.sounds.Voice.new_speak(2, j, s, 226, 2);
            else
                com.maddox.il2.objects.sounds.Voice.new_speak(2, j, s, 228, 2);
        }
    }

    public static void speakDanger(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int j = (int)(com.maddox.rts.Time.current() / 1000L);
        int k = aircraft.getArmy() - 1 & 1;
        int l = aircraft.aircIndex();
        if(j < com.maddox.il2.objects.sounds.Voice.cur().SpeakDanger[k][l])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakDanger[k][l] = j + 27;
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        if(i == 4)
        {
            int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 4);
            if(i1 == 0)
                return;
            int j1 = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            str[0] = 260;
            str[1] = 255;
            str[2] = 254;
            com.maddox.il2.objects.sounds.Voice.speakNewRandom(i1, j1, s, str, 1);
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakClearTail(aircraft);
        }
    }

    public static void speakClearTail(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.aircIndex();
        int k = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakClearTail[k][j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakClearTail[k][j] = i + 37;
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        float f = rnd.nextFloat();
        if(f < 0.33F)
            str[0] = 90;
        else
        if(f < 0.66F)
            str[0] = 218;
        else
            str[0] = 146;
        f = rnd.nextFloat();
        if(f < 0.5F)
            str[1] = 115;
        else
            str[1] = 89;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 0, str, 1);
    }

    public static void speakBombing(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBombing[j])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakBombing[j] = i + 1;
            com.maddox.il2.objects.sounds.Voice.reset();
            str2[0][0] = 81;
            str2[1][0] = 232;
            str2[1][1] = 231;
            str2[1][2] = 85;
            com.maddox.il2.objects.sounds.Voice.speakRandomArray(aircraft, str2, 1);
            return;
        }
    }

    public static void speakEndBombing(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakEndBombing[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakEndBombing[j] = i + 300;
        int k = aircraft.getRegiment().diedBombers;
        str[0] = 107;
        if(k > 1)
            str[1] = 222;
        else
        if(k == 1)
            str[1] = 223;
        else
            str[1] = 224;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 6, str, 2);
    }

    public static void speakDeviateSmall(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakDeviateSmall[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakDeviateSmall[j] = i + 4;
        str[0] = 170;
        str[1] = 150;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 2);
        com.maddox.il2.ai.Wing wing = aircraft.getWing();
        if(wing.airc.length > 0)
        {
            com.maddox.il2.objects.sounds.Voice.speakHeading(wing.airc[0]);
            com.maddox.il2.objects.sounds.Voice.speakAltitude(aircraft, (int)wing.airc[0].FM.Loc.z);
            str[0] = 136;
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 3, str, 2);
        }
    }

    public static void speakDeviateBig(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakDeviateBig[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakDeviateBig[j] = i + 10;
        if(rnd.nextFloat() < 0.5F)
        {
            if(rnd.nextFloat() < 0.5F)
                str[0] = 90;
            else
                str[0] = 214;
        } else
        if(rnd.nextFloat() < 0.5F)
            str[0] = 170;
        else
            str[0] = 217;
        str[1] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 2);
        str[0] = 171;
        str[1] = 149;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 3, str, 2);
        com.maddox.il2.ai.Wing wing = aircraft.getWing();
        if(wing.airc.length > 0)
        {
            com.maddox.il2.objects.sounds.Voice.speakHeading(wing.airc[0]);
            com.maddox.il2.objects.sounds.Voice.speakAltitude(aircraft, (int)wing.airc[0].FM.Loc.z);
        }
    }

    public static void speakEnemyDetected(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakEnemyDetected[j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakEnemyDetected[j] = i + 40;
        if(!(aircraft.FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        if(aircraft.aircNumber() < 2)
            return;
        com.maddox.il2.objects.air.Aircraft aircraft2 = aircraft1;
        if(aircraft2 == null)
            return;
        str[0] = 81;
        if(aircraft2 instanceof com.maddox.il2.objects.air.TypeFighter)
            str[1] = 107;
        else
        if(aircraft2 instanceof com.maddox.il2.objects.air.TypeBomber)
            str[1] = 84;
        else
        if(aircraft2 instanceof com.maddox.il2.objects.air.TypeDiveBomber)
            str[1] = 84;
        else
            str[1] = 83;
        str[2] = com.maddox.il2.objects.sounds.Voice.speakTarget_O_Clock(aircraft, aircraft2);
        str[3] = 0;
        int k = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 3);
        if(k == 0)
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 1);
            return;
        }
    }

    private static int speakTarget_O_Clock(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.engine.Actor actor)
    {
        int i = aircraft.target_O_Clock(actor);
        if(i < 1 || i > 36)
            return 0;
        else
            return clkstr[i];
    }

    public static void speakCheckYour6(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakHint[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakHint[j][k] = i + 2;
        if(aircraft.aircNumber() < 2)
            return;
        int l = aircraft.getWing().indexInSquadron() * 4 + aircraft.aircIndex();
        if(l > 15)
        {
            return;
        } else
        {
            str[0] = aNumber[l];
            str[1] = 88;
            str[2] = your_o_clock[aircraft.target_O_Clock(aircraft1)];
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 1, str, 1);
            return;
        }
    }

    public static void speakToReturn(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakToReturn[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakToReturn[j][k] = i + 5;
        if(aircraft.aircNumber() < 2)
            return;
        int l = aircraft.getWing().indexInSquadron() * 4 + aircraft.aircIndex();
        if(l > 15)
        {
            return;
        } else
        {
            str[0] = aNumber[l];
            str[1] = 140;
            str[2] = 136;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 3, str, 1);
            return;
        }
    }

    public static void speakBailOut(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBailOut[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakBailOut[j][k] = i + 1;
        if(aircraft.aircNumber() < 2)
            return;
        int l = aircraft.getWing().indexInSquadron() * 4 + aircraft.aircIndex();
        if(l > 15)
            return;
        int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 1);
        com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.War.getNearestFriend(aircraft);
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        if(aircraft1 != null && (rnd.nextFloat() > 0.5F || aircraft == com.maddox.il2.ai.World.getPlayerAircraft()))
        {
            com.maddox.il2.objects.sounds.Voice.airSpeaksNew(aircraft1, aNumber[l], 1);
            str[0] = 82;
            str[1] = 116;
            str[2] = 120;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft1, str, 1);
        } else
        {
            if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
                return;
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 121;
            str[1] = 123;
            str[2] = 125;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
        }
    }

    public static void speakMayday(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i;
        int j;
        int k;
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        i = (int)(com.maddox.rts.Time.current() / 60000L);
        j = aircraft.getArmy() - 1 & 1;
        k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakMayday[j][k])
            return;
        try
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakMayday[j][k] = i + 1;
        }
        catch(java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) { }
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        str[0] = 122;
        str[1] = 91;
        str[2] = 126;
        str[3] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
        return;
    }

    public static void speakMissionAccomplished(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 3);
        if(i == 0)
            return;
        int j = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.new_speak(i, j, s, 81, 1);
        if(aircraft.getRegiment().diedAircrafts == 0)
            com.maddox.il2.objects.sounds.Voice.speak(i, j, s, 128, 1);
        else
            com.maddox.il2.objects.sounds.Voice.speak(i, j, s, 127, 1);
        str[0] = 139;
        str[1] = 105;
        str[2] = 168;
        str[3] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(i, j, s, str, 1);
        com.maddox.il2.objects.sounds.Voice.speakHeading(aircraft);
        com.maddox.il2.objects.sounds.Voice.speakAltitude(aircraft, (int)aircraft.FM.Loc.z);
        if(!(aircraft instanceof com.maddox.il2.objects.air.TypeFighter))
        {
            com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.War.getNearestFriendlyFighter(aircraft, 50000F);
            if(aircraft1 != null)
                com.maddox.il2.objects.sounds.Voice.speakEndBombing(aircraft);
        }
    }

    public static void speakThisIs(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == null)
            return;
        int i = aircraft.getWing().indexInSquadron();
        int j = aircraft.aircIndex();
        int k = aircraft.aircNumber();
        int l = i * 4 + j;
        if(l > 15)
        {
            return;
        } else
        {
            str[0] = thisIsNumber[l];
            str[1] = 0;
            int i1 = syncMode;
            com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 0, str, 1);
            com.maddox.il2.objects.sounds.Voice.setSyncMode(i1);
            return;
        }
    }

    public static void speakIAm(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == null)
            return;
        int i = aircraft.getWing().indexInSquadron();
        int j = aircraft.aircIndex();
        int k = aircraft.aircNumber();
        int l = i * 4 + j;
        if(l > 15)
            return;
        str[0] = thisIsNumber[l];
        int i1 = aircraft.getArmy() - 1 & 1;
        if(i1 == 0)
        {
            if(j == 0)
                if(k == 2)
                    str[0] = thisIsPara[i];
                else
                if(k > 1)
                    str[0] = thisIsWing[i];
        } else
        if(j == 0)
            if(k == 2)
                str[0] = thisIsRotte[i];
            else
            if(k > 1)
                str[0] = thisIsSwarm[i];
        str[1] = 0;
        int j1 = syncMode;
        com.maddox.il2.objects.sounds.Voice.airSpeaksNewArray(aircraft, 0, str, 1);
        com.maddox.il2.objects.sounds.Voice.setSyncMode(j1);
    }

    public static void speakNumber_same_str(int i, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == null)
            return;
        int j = aircraft.getWing().indexInSquadron();
        int k = aircraft.aircIndex();
        int l = j * 4 + k;
        if(l > 15)
        {
            return;
        } else
        {
            int i1 = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int j1 = syncMode;
            com.maddox.il2.objects.sounds.Voice.speak(i, i1, s, aNumber[l], 2);
            com.maddox.il2.objects.sounds.Voice.setSyncMode(j1);
            return;
        }
    }

    public static void speakNumber(int i, com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(aircraft == null)
            return;
        int j = aircraft.getWing().indexInSquadron();
        int k = aircraft.aircIndex();
        int l = j * 4 + k;
        if(l > 15)
        {
            return;
        } else
        {
            int i1 = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int j1 = syncMode;
            com.maddox.il2.objects.sounds.Voice.new_speak(i, i1, s, aNumber[l], 2);
            com.maddox.il2.objects.sounds.Voice.setSyncMode(j1);
            return;
        }
    }

    public static void speakCoverMe(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverMe[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverMe[j][k] = i + 15;
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 310;
            str[1] = 309;
            str[2] = 268;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(i1, l, s, str, 1);
            return;
        }
    }

    public static void speakYouAreClear(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverMe[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverMe[j][k] = i + 15;
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 341;
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.airSpeaksArray(aircraft, 0, str, 1);
            return;
        }
    }

    public static void speakTargetAll(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetAll[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetAll[j][k] = i + 1;
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 324;
            str[1] = 320;
            str[2] = 277;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(i1, l, s, str, 1);
            return;
        }
    }

    public static void speakAttackFighters(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetAll[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetAll[j][k] = i + 30;
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 323;
            str[1] = 306;
            str[2] = 267;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(i1, l, s, str, 1);
            return;
        }
    }

    public static void speakAttackBombers(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackBombers[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakAttackBombers[j][k] = i + 30;
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 307;
            str[1] = 264;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(i1, l, s, str, 1);
            return;
        }
    }

    public static void speakAttackMyTarget(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str2[0][0] = 278;
            str2[0][1] = 264;
            com.maddox.il2.objects.sounds.Voice.speakRandomArray(aircraft, str2, 1);
            return;
        }
    }

    public static void speakAttackGround(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str2[0][0] = 262;
            str2[0][1] = 264;
            com.maddox.il2.objects.sounds.Voice.speakRandomArray(aircraft, str2, 1);
            return;
        }
    }

    public static void speakDropTanks(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str2[0][0] = 322;
            str2[0][1] = 275;
            com.maddox.il2.objects.sounds.Voice.speakRandomArray(aircraft, str2, 1);
            return;
        }
    }

    public static void speakHelpNeeded(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int j = (int)(com.maddox.rts.Time.current() / 1000L);
        int k = aircraft.getArmy() - 1 & 1;
        int l = aircraft.aircIndex();
        if(j < com.maddox.il2.objects.sounds.Voice.cur().SpeakHelpNeeded[k][l])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakHelpNeeded[k][l] = j + 30;
            return;
        }
    }

    public static void speakCoverProvided(com.maddox.il2.objects.air.Aircraft aircraft, com.maddox.il2.objects.air.Aircraft aircraft1)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft) || !com.maddox.il2.engine.Actor.isAlive(aircraft1))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.aircIndex();
        int k = aircraft.getArmy() - 1 & 1;
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverProvided[k][j])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverProvided[k][j] = i + 30;
        int l = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        int i1 = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        if(com.maddox.il2.ai.World.Rnd().nextBoolean())
            com.maddox.il2.objects.sounds.Voice.speakNumber_same_str(i1, aircraft1);
        str[0] = 310;
        str[1] = 309;
        str[2] = 268;
        str[3] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(i1, l, s, str, 1);
    }

    public static void speakHelpNeededFromBase(com.maddox.il2.objects.air.Aircraft aircraft, boolean flag)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = aircraft.getArmy() - 1 & 1;
        int j = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        int k = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 5);
        com.maddox.il2.objects.sounds.Voice.new_speak(k, j, s, 235, 1);
        if(flag)
        {
            str[0] = 237;
            str[1] = 239;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(k, j, s, str, 1);
        } else
        {
            str[0] = 234;
            str[1] = 233;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(k, j, s, str, 1);
        }
    }

    public static void speakHelpFromAir(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int j = (int)(com.maddox.rts.Time.current() / 1000L);
        int k = aircraft.getArmy() - 1 & 1;
        int l = aircraft.aircIndex();
        if(j < com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverProvided[k][l])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakCoverProvided[k][l] = j + 45;
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        switch(i)
        {
        case 1: // '\001'
            str[0] = 291;
            str[1] = 294;
            str[2] = 291;
            break;

        case 2: // '\002'
            str[0] = 341;
            str[1] = 342;
            str[2] = 342;
            break;

        default:
            str[0] = 295;
            str[1] = 339;
            str[2] = 340;
            break;
        }
        str[3] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
    }

    public static void speakRearGunKill()
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakRearGunKill)
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakRearGunKill = i + 20;
            int j = com.maddox.il2.ai.World.getPlayerArmy();
            java.lang.String s = com.maddox.il2.ai.World.getPlayerAircraft().getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.new_speak(5, j, s, 258, 1);
            return;
        }
    }

    public static void speakRearGunShake()
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakRearGunShake)
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakRearGunShake = i + 20;
        int j = com.maddox.il2.ai.World.getPlayerArmy();
        java.lang.String s = com.maddox.il2.ai.World.getPlayerAircraft().getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        if(rnd.nextFloat() < 0.5F)
            com.maddox.il2.objects.sounds.Voice.new_speak(5, j, s, 256, 1);
        else
            com.maddox.il2.objects.sounds.Voice.new_speak(5, j, s, 259, 1);
    }

    public static void speakNiceKill(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakNiceKill[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakNiceKill[j][k] = i + 5;
        if(aircraft == null)
            return;
        float f = rnd.nextFloat();
        int l = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 1);
        com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
        if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft())
            f = 0.0F;
        else
        if(l == 0)
            f = 1.0F;
        if(f > 0.5F)
        {
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 293;
            str[1] = 290;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 2);
        } else
        {
            int i1 = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.speakNumber(l, aircraft);
            str[0] = 289;
            str[1] = 288;
            str[2] = 296;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(l, i1, s, str, 1);
        }
    }

    public static void speakRearGunTargetDestroyed()
    {
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            int i = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            str[0] = 153;
            str[1] = 93;
            str[2] = 154;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakNewRandom(5, i, s, str, 2);
            str[0] = 257;
            str[1] = 261;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(5, i, s, str, 2);
            return;
        }
    }

    public static void speakTargetDestroyed(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetDestroyed[j][k])
            return;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakTargetDestroyed[j][k] = i + 60;
        if(aircraft == com.maddox.il2.ai.World.getPlayerAircraft() && aircraft.FM.turret.length > 0 && aircraft.FM.AS.astatePilotStates[aircraft.FM.turret.length] < 90 && aircraft.FM.turret[0].bIsAIControlled)
            new com.maddox.rts.MsgAction(5.5D) {

                public void doAction()
                {
                    com.maddox.il2.objects.sounds.Voice.speakRearGunTargetDestroyed();
                }

            }
;
        if(com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 153;
            str[1] = 93;
            str[2] = 154;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
        }
    }

    public static void speakEndOfAmmo(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        if((aircraft instanceof com.maddox.il2.objects.air.TypeBomber) || (aircraft instanceof com.maddox.il2.objects.air.TypeTransport))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakEndOfAmmo[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakEndOfAmmo[j][k] = i + 5;
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
            str[0] = 292;
            str[1] = 124;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 2);
            return;
        }
    }

    public static void speakBreak(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakBreak[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakBreak[j][k] = i + 1;
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 302;
            str[1] = 298;
            str[2] = 266;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 2);
            return;
        }
    }

    public static void speakRejoin(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 318;
            str[1] = 317;
            str[2] = 274;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 2);
            return;
        }
    }

    public static void speakTightFormation(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 300;
            str[1] = 301;
            str[2] = 279;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 2);
            return;
        }
    }

    public static void speakLoosenFormation(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            str[0] = 299;
            str[1] = 299;
            str[2] = 265;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakNewRandom(aircraft, str, 2);
            return;
        }
    }

    public static void speakOk(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 298, 1);
            return;
        }
    }

    public static void speakUnable(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 339;
            str[1] = 340;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakNextWayPoint(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 314;
            str[1] = 271;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakPrevWayPoint(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 316;
            str[1] = 319;
            str[2] = 272;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakReturnToBase(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 325;
            str[1] = 305;
            str[2] = 276;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakHangOn(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
            str[0] = 308;
            str[1] = 269;
            str[2] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(aircraft, str, 1);
            return;
        }
    }

    public static void speakEchelonRight(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 304, 2);
    }

    public static void speakEchelonLeft(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 303, 2);
    }

    public static void speakLineAbreast(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 312, 2);
    }

    public static void speakLineAstern(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 313, 1);
    }

    public static void speakVic(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakIAm(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 321, 1);
    }

    public static void speakPullUp(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = (int)(com.maddox.rts.Time.current() / 1000L);
        if(i < com.maddox.il2.objects.sounds.Voice.cur().SpeakPullUp)
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakPullUp = i + 30;
            int j = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 1);
            int k = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.setSyncMode(2);
            com.maddox.il2.objects.sounds.Voice.speakNumber(j, aircraft);
            str[0] = 137;
            str[1] = 172;
            str[2] = 167;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(j, k, s, str, 3);
            return;
        }
    }

    public static void speakLandingPermited(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
            return;
        int i = (int)(com.maddox.rts.Time.current() / 60000L);
        int j = aircraft.getArmy() - 1 & 1;
        int k = aircraft.aircIndex();
        if(aircraft != com.maddox.il2.ai.World.getPlayerAircraft() && i < com.maddox.il2.objects.sounds.Voice.cur().SpeakLandingPermited[j][k])
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.Voice.cur().SpeakLandingPermited[j][k] = i + 60;
            int l = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.speakNumber(4, aircraft);
            str[0] = 240;
            str[1] = 243;
            str[2] = 248;
            str[3] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(4, l, s, str, 2);
            return;
        }
    }

    public static void speakLandingDenied(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.speakNumber(4, aircraft);
        str[0] = 250;
        str[1] = 236;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(4, i, s, str, 2);
    }

    public static void speakWaveOff(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        com.maddox.il2.objects.sounds.Voice.speakNumber(4, aircraft);
        str[0] = 236;
        str[1] = 238;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(4, i, s, str, 2);
    }

    public static void speakLanding(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 134, 1);
    }

    public static void speakGoAround(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        int j = com.maddox.il2.objects.sounds.Voice.actorVoice(aircraft, 0);
        com.maddox.il2.objects.sounds.Voice.speakThisIs(aircraft);
        str[0] = 135;
        str[1] = 117;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(j, i, s, str, 1);
    }

    public static void speakGoingIn(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        com.maddox.il2.objects.sounds.Voice.airSpeaks(aircraft, 130, 1);
    }

    public static void testTargDestr(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(com.maddox.il2.engine.Actor.isValid(actor1) && (!actor1.isNet() || actor1.isNetMaster()))
            try
            {
                if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                {
                    if(!(actor instanceof com.maddox.il2.objects.air.TypeFighter))
                    {
                        ((com.maddox.il2.ai.Wing)actor.getOwner()).regiment().diedBombers++;
                        if(actor1 instanceof com.maddox.il2.objects.air.TypeFighter)
                            com.maddox.il2.objects.sounds.Voice.speakBombersUnderAttack((com.maddox.il2.objects.air.Aircraft)actor, true);
                    }
                    ((com.maddox.il2.ai.Wing)actor.getOwner()).regiment().diedAircrafts++;
                    if((actor1 instanceof com.maddox.il2.objects.air.Aircraft) && actor.getArmy() != actor1.getArmy() && !((com.maddox.il2.objects.air.Aircraft)actor).buried)
                        com.maddox.il2.objects.sounds.Voice.speakNiceKill((com.maddox.il2.objects.air.Aircraft)actor1);
                } else
                if(actor1 instanceof com.maddox.il2.objects.air.Aircraft)
                {
                    int i = actor.getArmy();
                    if(i == 0)
                    {
                        actor.pos.getAbs(P3d);
                        i = com.maddox.il2.ai.Front.army(P3d.x, P3d.x);
                    }
                    if(i != actor1.getArmy())
                        com.maddox.il2.objects.sounds.Voice.speakTargetDestroyed((com.maddox.il2.objects.air.Aircraft)actor1);
                }
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
    }

    public static void speakTakeoffPermited(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            int i = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.speakNumber(4, aircraft);
            str[0] = 241;
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(4, i, s, str, 1);
            return;
        }
    }

    public static void speakTakeoffDenied(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(aircraft))
        {
            return;
        } else
        {
            int i = aircraft.getArmy();
            java.lang.String s = aircraft.getRegiment().speech();
            com.maddox.il2.objects.sounds.Voice.speakNumber(4, aircraft);
            str[0] = 253;
            str[1] = 0;
            com.maddox.il2.objects.sounds.Voice.speakRandom(4, i, s, str, 1);
            return;
        }
    }

    public static void speakNegative(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        str[0] = 295;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(6, i, s, str, 1);
    }

    public static void speakRooger(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        int i = aircraft.getArmy();
        java.lang.String s = aircraft.getRegiment().speech();
        str[0] = 298;
        str[2] = 0;
        com.maddox.il2.objects.sounds.Voice.speakRandom(6, i, s, str, 1);
    }

    private int SpeakRearGunKill;
    private int SpeakPullUp;
    private int SpeakRearGunShake;
    private int SpeakAttackByRockets[][];
    private int SpeakAttackByBombs[][];
    private int SpeakTargetDestroyed[][];
    private int SpeakDanger[][];
    private int SpeakHelpNeeded[][];
    private int SpeakClearTail[][];
    private int SpeakCoverMe[][];
    private int SpeakCoverProvided[][];
    private int SpeakHelpFromAir[][];
    private int SpeakNiceKill[][];
    private int SpeakEndOfAmmo[][];
    private int SpeakMayday[][];
    private int SpeakCheckFire[][];
    private int SpeakHint[][];
    private int SpeakToReturn[][];
    public int SpeakBailOut[][];
    private int SpeakAttackGround[][];
    private int SpeakAttackMyTarget[][];
    private int SpeakAttackBombers[][];
    private int SpeakTargetAll[][];
    private int SpeakDropTanks[][];
    private int SpeakBreak[][];
    private int SpeakRejoin[][];
    private int SpeakTightFormation[][];
    private int SpeakLoosenFormation[][];
    private int SpeakOk[][];
    private int SpeakLandingPermited[][];
    private int SpeakBombing[];
    private int SpeakEndBombing[];
    private int SpeakEndGattack[];
    private int SpeakDeviateSmall[];
    private int SpeakDeviateBig[];
    private int SpeakHeading[];
    private int SpeakAltitude[];
    public int SpeakNearBombers[];
    private int Speak1minute[];
    private int Speak5minutes[];
    public int SpeakBombersUnderAttack[];
    public int SpeakBombersUnderAttack1[];
    public int SpeakBombersUnderAttack2[];
    private int SpeakEnemyDetected[];
    private static com.maddox.il2.objects.air.Aircraft internalAir = null;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    public static int str[] = new int[8];
    public static int str2[][] = new int[4][8];
    public static final int afPILOT = 0;
    public static final int afNEARFRIEND = 1;
    public static final int afWINGMAN = 2;
    public static final int afLEADER = 3;
    public static final int afREARGUN = 4;
    public static final int afLAND = 5;
    public static final int afBOMBERS = 6;
    public static final int anNONE = 0;
    public static final int anLEADER = 1;
    public static final int anBOMBER1 = 2;
    public static final int anBOMBER2 = 3;
    public static final int anLAND = 4;
    public static final int anREARGUN = 5;
    public static final int anACTOR6 = 6;
    public static final int anACTOR7 = 7;
    public static final int anACTOR8 = 8;
    public static final int anACTOR9 = 9;
    private static int headings[] = {
        1, 3, 5, 6, 15, 20, 21, 27, 28, 30, 
        35, 36
    };
    private static int altitudes[] = {
        2, 4, 7, 19, 26, 29, 34, 37, 41, 42, 
        46, 47, 53, 54, 58, 59, 63, 64, 68, 69, 
        8
    };
    private static final int clkstr[] = {
        16, 23, 31, 38, 43, 50, 55, 60, 65, 70, 
        9, 12, 16, 24, 32, 39, 44, 51, 56, 61, 
        66, 71, 10, 13, 17, 25, 33, 40, 45, 52, 
        57, 62, 67, 72, 11, 14, 18
    };
    private static final int aNumber[] = {
        285, 326, 331, 287, 284, 329, 327, 281, 297, 330, 
        282, 338, 332, 286, 283, 328
    };
    private static int your_o_clock[] = {
        206, 173, 182, 185, 188, 191, 194, 197, 200, 203, 
        176, 179, 206, 174, 183, 186, 189, 192, 195, 198, 
        201, 204, 177, 180, 207, 175, 184, 187, 190, 193, 
        196, 199, 202, 205, 178, 181, 208
    };
    private static final int thisIsNumber[] = {
        158, 360, 354, 346, 345, 357, 351, 333, 350, 358, 
        343, 337, 359, 349, 344, 336
    };
    private static final int thisIsPara[] = {
        334, 347, 352, 355
    };
    private static final int thisIsWing[] = {
        335, 348, 353, 356
    };
    private static final int thisIsRotte[] = {
        361, 362, 363, 364
    };
    private static final int thisIsSwarm[] = {
        365, 366, 367, 368
    };
    private static final int pilotVoice[] = {
        6, 9, 8, 7, 7, 9, 8, 6, 8, 9, 
        7, 6, 9, 8, 7, 6
    };
    public static final int M_SYNC = 0;
    public static final int M_DELAY = 1;
    public static final int M_IMMED = 2;
    protected static int syncMode = 0;
    private static com.maddox.JGP.Point3d P3d = new Point3d();

    static 
    {
        com.maddox.il2.objects.sounds.Voice.reset();
    }
}

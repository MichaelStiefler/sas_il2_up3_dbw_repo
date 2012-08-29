// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AirportGround.java

package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai:
//            AirportStatic, World, RangeRandom

public class AirportGround extends com.maddox.il2.ai.AirportStatic
{
    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                int i = netmsginput.readUnsignedByte();
                if(i == 80)
                {
                    boolean flag = netmsginput.readBoolean();
                    turnOnLights(flag);
                }
                return true;
            } else
            {
                return true;
            }
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return true;
            if(netmsginput.readUnsignedByte() == 81)
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                acThatRequestedLights = netobj != null ? (com.maddox.il2.objects.air.Aircraft)(com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                turnOnLights(acThatRequestedLights);
                return true;
            } else
            {
                return false;
            }
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }


    public AirportGround()
    {
        runwayLights = null;
        lightsOn = false;
        acThatRequestedLights = null;
        aircraftIsTakingOff = false;
        canTurnOffLights = false;
        canTurnOnLights = false;
        randomDelay = 0;
        timeOfLightsOn = 0L;
        net = null;
        if(com.maddox.il2.game.Mission.cur() != null && (!com.maddox.il2.net.NetMissionTrack.isPlaying() || com.maddox.il2.net.NetMissionTrack.playingOriginalVersion() > 102))
        {
            int i = com.maddox.il2.game.Mission.cur().getUnitNetIdRemote(this);
            com.maddox.rts.NetChannel netchannel = com.maddox.il2.game.Mission.cur().getNetMasterChannel();
            if(netchannel == null)
                net = new Master(this);
            else
            if(i != 0)
                net = new Mirror(this, (com.maddox.rts.NetChannel)netchannel, i);
        }
    }

    protected void update()
    {
        super.update();
        if(com.maddox.il2.game.Mission.cur() == null)
            return;
        if(com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.net.NetMissionTrack.playingOriginalVersion() <= 102)
            return;
        if(net.isMirror())
            return;
        if(lightsOn)
        {
            if(canTurnOffLights)
            {
                if(randomDelay < 0)
                    turnOnLights(false);
                else
                    randomDelay--;
            } else
            if(acThatRequestedLights != null && aircraftIsTakingOff)
            {
                if(!acThatRequestedLights.FM.Gears.onGround() || !acThatRequestedLights.isAlive())
                {
                    randomDelay = 300 + com.maddox.il2.ai.World.Rnd().nextInt(800);
                    canTurnOffLights = true;
                } else
                if(com.maddox.rts.Time.current() > timeOfLightsOn + 0x15f90L)
                {
                    canTurnOffLights = true;
                    turnOnLights(false);
                }
            } else
            if(acThatRequestedLights != null && !aircraftIsTakingOff)
            {
                if(acThatRequestedLights.FM.Gears.onGround() || !acThatRequestedLights.isAlive())
                {
                    randomDelay = 300 + (int)(java.lang.Math.random() * 800D);
                    canTurnOffLights = true;
                } else
                if(com.maddox.rts.Time.current() > timeOfLightsOn + 0x15f90L)
                {
                    canTurnOffLights = true;
                    turnOnLights(false);
                }
            } else
            if(com.maddox.rts.Time.current() > timeOfLightsOn + 0x15f90L)
            {
                canTurnOffLights = true;
                turnOnLights(false);
            }
        } else
        if(canTurnOnLights)
            if(randomDelay < 0)
            {
                canTurnOnLights = false;
                turnOnLights(true);
                timeOfLightsOn = com.maddox.rts.Time.current();
            } else
            {
                randomDelay--;
            }
    }

    public boolean hasLights()
    {
        return runwayLights != null && runwayLights.size() > 0;
    }

    public void addLights(com.maddox.il2.objects.vehicles.stationary.SmokeGeneric smokegeneric)
    {
        if(runwayLights == null)
            runwayLights = new ArrayList();
        runwayLights.add(smokegeneric);
        smokegeneric.setArmy(0);
    }

    private void turnOnLights(boolean flag)
    {
        lightsOn = flag;
        if(!net.isMirror())
            master_sendLights(lightsOn);
        if(runwayLights != null)
        {
            for(int i = 0; i < runwayLights.size(); i++)
            {
                com.maddox.il2.objects.vehicles.stationary.SmokeGeneric smokegeneric = (com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)runwayLights.get(i);
                smokegeneric.setVisible(flag);
            }

        }
    }

    public void turnOnLights(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(net.isMirror())
        {
            mirror_sendLights(aircraft);
            return;
        }
        canTurnOnLights = true;
        canTurnOffLights = false;
        acThatRequestedLights = aircraft;
        if(acThatRequestedLights != null && acThatRequestedLights.FM.Gears.onGround())
            aircraftIsTakingOff = true;
        else
            aircraftIsTakingOff = false;
        randomDelay = 200 + com.maddox.il2.ai.World.Rnd().nextInt(200);
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(80);
            netmsgguaranted.writeBoolean(lightsOn);
            net.postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Airport lights: NetFirstUpdate failed");
        }
    }

    private boolean master_sendLights(boolean flag)
    {
        if(net.isMirror())
            return false;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(80);
        netmsgguaranted.writeBoolean(flag);
        net.post(netmsgguaranted);
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    private boolean mirror_sendLights(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!net.isMirror() || (net.masterChannel() instanceof com.maddox.rts.NetChannelInStream))
            return false;
        com.maddox.rts.NetMsgFiltered netmsgfiltered = null;
        netmsgfiltered = new NetMsgFiltered();
        netmsgfiltered.writeByte(81);
        netmsgfiltered.writeNetObj(aircraft != null ? ((com.maddox.rts.NetObj) (aircraft.net)) : null);
        netmsgfiltered.setIncludeTime(false);
        net.postTo(com.maddox.il2.net.NetServerParams.getServerTime(), net.masterChannel(), netmsgfiltered);
        return true;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    private java.util.ArrayList runwayLights;
    private boolean lightsOn;
    private com.maddox.il2.objects.air.Aircraft acThatRequestedLights;
    private boolean aircraftIsTakingOff;
    private boolean canTurnOffLights;
    private boolean canTurnOnLights;
    private int randomDelay;
    private long timeOfLightsOn;
    private final int maxLightsOnTimeMs = 0x15f90;



}

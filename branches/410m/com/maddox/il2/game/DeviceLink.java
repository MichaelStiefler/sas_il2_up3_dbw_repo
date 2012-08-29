// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DeviceLink.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdMove;
import com.maddox.rts.HotKeyCmdTrackIRAngles;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            Mission, Main3D, AircraftHotKeys

public class DeviceLink
{
    private class Listener extends java.lang.Thread
    {

        public void run()
        {
_L2:
            if(com.maddox.rts.RTSConf.cur == null || com.maddox.rts.RTSConf.isRequestExitApp())
                break; /* Loop/switch isn't completed */
            java.net.DatagramPacket datagrampacket;
            datagrampacket = new DatagramPacket(new byte[com.maddox.il2.game.DeviceLink.PACKET_SIZE], com.maddox.il2.game.DeviceLink.PACKET_SIZE);
            serverSocket.receive(datagrampacket);
            if(datagrampacket.getLength() < 1)
                continue; /* Loop/switch isn't completed */
            if(enableClientAdr.size() > 0)
            {
                java.lang.String s = datagrampacket.getAddress().getHostAddress();
                int i = enableClientAdr.size();
                boolean flag = false;
                int j = 0;
                do
                {
                    if(j >= i)
                        break;
                    if(s.equals(enableClientAdr.get(j)))
                    {
                        flag = true;
                        break;
                    }
                    j++;
                } while(true);
                if(!flag)
                    continue; /* Loop/switch isn't completed */
            }
            try
            {
                synchronized(inputAction)
                {
                    inputPackets.add(datagrampacket);
                    inputAction.activate();
                }
            }
            catch(java.lang.Throwable throwable) { }
            if(true) goto _L2; else goto _L1
_L1:
        }

        private Listener()
        {
        }

    }

    private class ActionReceivedPacket extends com.maddox.rts.MsgAction
    {

        public void doAction()
        {
            while(inputPackets.size() > 0) 
            {
                java.net.DatagramPacket datagrampacket = (java.net.DatagramPacket)inputPackets.get(0);
                inputPackets.remove(0);
                try
                {
                    serverReceivePacket(datagrampacket);
                }
                catch(java.lang.Throwable throwable) { }
            }
        }

        public void activate()
        {
            if(!busy())
                post(64, this, 0.0D);
        }

        private ActionReceivedPacket()
        {
        }

    }

    private class Parameter
    {

        public void set(java.util.List list)
        {
        }

        public void get(java.util.List list)
        {
        }

        public boolean get_accessible()
        {
            return true;
        }

        public boolean set_accessible()
        {
            return true;
        }

        protected boolean isMissinValid()
        {
            return com.maddox.il2.game.Mission.isPlaying();
        }

        protected boolean isAircraftValid()
        {
            if(!isMissinValid())
                return false;
            else
                return com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft());
        }

        protected boolean isFMValid()
        {
            return isAircraftValid() && !com.maddox.il2.ai.World.isPlayerGunner() && !com.maddox.il2.ai.World.isPlayerParatrooper() && !com.maddox.il2.ai.World.isPlayerDead();
        }

        protected boolean isNetAccessible()
        {
            return com.maddox.il2.game.Mission.isSingle() || com.maddox.il2.net.NetMissionTrack.isPlaying();
        }

        protected int engineNum(java.util.List list)
        {
            if(list == null || list.size() == 0)
                return 0;
            return java.lang.Integer.parseInt((java.lang.String)list.get(0));
            java.lang.Exception exception;
            exception;
            return -1;
        }

        protected com.maddox.il2.engine.Actor actorForName(java.util.List list)
        {
            if(list == null || list.size() == 0)
            {
                return null;
            } else
            {
                java.lang.String s = (java.lang.String)list.get(0);
                return com.maddox.il2.engine.Actor.getByName(s);
            }
        }

        protected java.lang.String fmt(float f)
        {
            boolean flag = f < 0.0F;
            if(flag)
                f = -f;
            float f1 = (f + 0.005F) - (float)(int)f;
            if(f1 >= 0.1F)
                return (flag ? "-" : "") + (int)f + "." + (int)(f1 * 100F);
            else
                return (flag ? "-" : "") + (int)f + ".0" + (int)(f1 * 100F);
        }

        public void answer(java.lang.String s)
        {
            do_answer(id, s);
        }

        public void answer(java.lang.String as[])
        {
            do_answer(id, as);
        }

        public void answer(java.util.List list)
        {
            do_answer(id, list);
        }

        public int id;

        public Parameter(int i)
        {
            i *= 2;
            paramMap.put(i, this);
            id = i;
        }
    }

    private class ParameterMisc extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return false;
        }

        protected boolean isFMValid()
        {
            return isAircraftValid() && !com.maddox.il2.ai.World.isPlayerParatrooper() && !com.maddox.il2.ai.World.isPlayerDead();
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            return com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name());
        }

        public void set(java.util.List list)
        {
            cmd._exec(true);
            cmd._exec(false);
        }

        com.maddox.rts.HotKeyCmd cmd;

        public ParameterMisc(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }

    private class ParameterTrackIR extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return false;
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            return com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name());
        }

        public void set(java.util.List list)
        {
            float f;
            float f1;
            float f2;
            try
            {
                f = java.lang.Float.parseFloat((java.lang.String)list.get(0));
                f1 = java.lang.Float.parseFloat((java.lang.String)list.get(1));
                f2 = java.lang.Float.parseFloat((java.lang.String)list.get(2));
            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            cmd._exec(f, f1, f2);
        }

        com.maddox.rts.HotKeyCmdTrackIRAngles cmd;

        public ParameterTrackIR(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = (com.maddox.rts.HotKeyCmdTrackIRAngles)com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }

    private class ParameterView extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return false;
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            return com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name());
        }

        public void set(java.util.List list)
        {
            cmd._exec(true);
            cmd._exec(false);
        }

        com.maddox.rts.HotKeyCmd cmd;

        public ParameterView(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }

    private class ParameterPilot extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return isFMValid();
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            if(!com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name()))
                return false;
            else
                return isFMValid() && ((com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM()).isRealMode() && !cmd.isActive();
        }

        protected boolean set_accessible0()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            if(!com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name()))
                return false;
            else
                return isFMValid() && ((com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM()).isRealMode();
        }

        public void set(java.util.List list)
        {
            cmd._exec(true);
            cmd._exec(false);
        }

        com.maddox.rts.HotKeyCmd cmd;

        public ParameterPilot(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }

    private class ParameterGunner extends com.maddox.il2.game.ParameterPilot
    {

        protected boolean isFMValid()
        {
            return isAircraftValid() && !com.maddox.il2.ai.World.isPlayerParatrooper() && !com.maddox.il2.ai.World.isPlayerDead();
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            if(!com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name()))
                return false;
            else
                return isFMValid() && !cmd.isActive();
        }

        public ParameterGunner(int i, java.lang.String s, java.lang.String s1)
        {
            super(i, s, s1);
        }
    }

    private class ParameterMove extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return isFMValid();
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            if(!com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name()))
                return false;
            else
                return isFMValid() && ((com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM()).isRealMode() && !cmd.isActive();
        }

        public void set(java.util.List list)
        {
            float f = 0.0F;
            try
            {
                f = java.lang.Float.parseFloat((java.lang.String)list.get(0));
            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            if(f < -1F)
                f = -1F;
            if(f > 1.0F)
                f = 1.0F;
            cmd._exec(java.lang.Math.round(f * 125F));
        }

        com.maddox.rts.HotKeyCmdMove cmd;

        public ParameterMove(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = (com.maddox.rts.HotKeyCmdMove)com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }

    private class ParameterMouseMove extends com.maddox.il2.game.Parameter
    {

        public boolean get_accessible()
        {
            return false;
        }

        public boolean set_accessible()
        {
            if(!cmd.isEnabled())
                return false;
            com.maddox.rts.HotKeyCmdEnv hotkeycmdenv = cmd.hotKeyCmdEnv();
            if(!hotkeycmdenv.isEnabled())
                return false;
            return com.maddox.rts.HotKeyEnv.isEnabled(hotkeycmdenv.name());
        }

        public void set(java.util.List list)
        {
            int i;
            int j;
            int k;
            try
            {
                i = java.lang.Integer.parseInt((java.lang.String)list.get(0));
                j = java.lang.Integer.parseInt((java.lang.String)list.get(1));
                k = java.lang.Integer.parseInt((java.lang.String)list.get(2));
            }
            catch(java.lang.Exception exception)
            {
                return;
            }
            cmd._exec(i, j, k);
        }

        com.maddox.rts.HotKeyCmdMouseMove cmd;

        public ParameterMouseMove(int i, java.lang.String s, java.lang.String s1)
        {
            super(i);
            cmd = (com.maddox.rts.HotKeyCmdMouseMove)com.maddox.rts.HotKeyCmdEnv.env(s).get(s1);
        }
    }


    private void registerParams()
    {
        new com.maddox.il2.game.Parameter(1) {

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("1.00");
            }

        }
;
        new com.maddox.il2.game.Parameter(2) {

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = 0;
                com.maddox.il2.game.Parameter parameter = null;
                try
                {
                    i = java.lang.Integer.parseInt((java.lang.String)list.get(0)) & -2;
                    parameter = (com.maddox.il2.game.Parameter)paramMap.get(i);
                }
                catch(java.lang.Exception exception)
                {
                    return;
                }
                if(parameter != null)
                    answer(new java.lang.String[] {
                        "" + i, parameter.get_accessible() ? "1" : "0"
                    });
            }

        }
;
        new com.maddox.il2.game.Parameter(3) {

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = 0;
                com.maddox.il2.game.Parameter parameter = null;
                try
                {
                    i = java.lang.Integer.parseInt((java.lang.String)list.get(0)) & -2;
                    parameter = (com.maddox.il2.game.Parameter)paramMap.get(i);
                }
                catch(java.lang.Exception exception)
                {
                    return;
                }
                if(parameter != null)
                    answer(new java.lang.String[] {
                        "" + (i + 1), parameter.set_accessible() ? "1" : "0"
                    });
            }

        }
;
        new com.maddox.il2.game.Parameter(10) {

            public boolean get_accessible()
            {
                return isMissinValid();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getTimeofDay());
            }

        }
;
        new com.maddox.il2.game.Parameter(11) {

            public boolean get_accessible()
            {
                return isAircraftValid();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.rts.Property.stringValue(com.maddox.il2.ai.World.getPlayerAircraft().getClass(), "keyName", ""));
            }

        }
;
        new com.maddox.il2.game.Parameter(12) {

            public boolean get_accessible()
            {
                return isAircraftValid();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.game.Main3D.cur3D().cockpits.length);
            }

        }
;
        new com.maddox.il2.game.Parameter(13) {

            public boolean get_accessible()
            {
                return isAircraftValid();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx();
                if(com.maddox.il2.game.Main3D.cur3D().isViewOutside())
                    i = -1;
                answer("" + i);
            }

        }
;
        new com.maddox.il2.game.Parameter(14) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().EI.getNum());
            }

        }
;
        new com.maddox.il2.game.Parameter(15) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.fm.Pitot.Indicator((float)com.maddox.il2.ai.World.getPlayerFM().Loc.z, com.maddox.il2.ai.World.getPlayerFM().getSpeedKMH())));
            }

        }
;
        new com.maddox.il2.game.Parameter(16) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt((float)com.maddox.il2.ai.World.getPlayerFM().Vwld.z));
            }

        }
;
        new com.maddox.il2.game.Parameter(17) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                float f = com.maddox.il2.ai.World.getPlayerFM().getSpeedKMH() <= 10F ? 0.0F : com.maddox.il2.ai.World.getPlayerFM().getAOS();
                if(f < -45F)
                    f = -45F;
                if(f > 45F)
                    f = 45F;
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(18) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.JGP.Vector3f vector3f = new Vector3f();
                vector3f.set(com.maddox.il2.ai.World.getPlayerFM().getW());
                com.maddox.il2.ai.World.getPlayerFM().Or.transform(vector3f);
                float f = vector3f.z / 6F;
                if(f < -1F)
                    f = -1F;
                if(f > 1.0F)
                    f = 1.0F;
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(19) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.JGP.Vector3f vector3f = new Vector3f();
                vector3f.set(com.maddox.il2.ai.World.getPlayerFM().getW());
                com.maddox.il2.ai.World.getPlayerFM().Or.transform(vector3f);
                float f = vector3f.z;
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(20) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt((float)com.maddox.il2.ai.World.getPlayerFM().Loc.z));
            }

        }
;
        new com.maddox.il2.game.Parameter(21) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.il2.engine.Orientation orientation = com.maddox.il2.ai.World.getPlayerFM().Or;
                o.set(orientation.azimut(), orientation.tangage(), orientation.kren());
                o.wrap();
                float f;
                for(f = 90F - o.getYaw(); f < 0.0F; f += 360F);
                for(; f > 360F; f -= 360F);
                answer("" + fmt(f));
            }

            com.maddox.il2.engine.Orient o;

            
            {
                o = new Orient();
            }
        }
;
        new com.maddox.il2.game.Parameter(22) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                com.maddox.il2.ai.WayPoint waypoint = com.maddox.il2.ai.World.getPlayerFM().AP.way.curr();
                float f = 0.0F;
                if(waypoint != null)
                {
                    waypoint.getP(point3d);
                    vector3d.sub(point3d, com.maddox.il2.ai.World.getPlayerFM().Loc);
                    f = (float)(57.295779513082323D * java.lang.Math.atan2(vector3d.y, vector3d.x));
                }
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(23) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.il2.engine.Orientation orientation = com.maddox.il2.ai.World.getPlayerFM().Or;
                o.set(orientation.azimut(), orientation.tangage(), orientation.kren());
                o.wrap();
                answer("" + fmt(-o.getKren()));
            }

            com.maddox.il2.engine.Orient o;

            
            {
                o = new Orient();
            }
        }
;
        new com.maddox.il2.game.Parameter(24) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                com.maddox.il2.engine.Orientation orientation = com.maddox.il2.ai.World.getPlayerFM().Or;
                o.set(orientation.azimut(), orientation.tangage(), orientation.kren());
                o.wrap();
                answer("" + fmt(o.getTangage()));
            }

            com.maddox.il2.engine.Orient o;

            
            {
                o = new Orient();
            }
        }
;
        new com.maddox.il2.game.Parameter(25) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().M.fuel));
            }

        }
;
        new com.maddox.il2.game.Parameter(26) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().getOverload()));
            }

        }
;
        new com.maddox.il2.game.Parameter(27) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(((com.maddox.il2.fm.RealFlightModel)com.maddox.il2.ai.World.getPlayerFM()).shakeLevel));
            }

        }
;
        new com.maddox.il2.game.Parameter(28) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                float f = 0.0F;
                if(com.maddox.il2.ai.World.getPlayerFM().Gears.lgear)
                    f = com.maddox.il2.ai.World.getPlayerFM().CT.getGear();
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(29) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                float f = 0.0F;
                if(com.maddox.il2.ai.World.getPlayerFM().Gears.rgear)
                    f = com.maddox.il2.ai.World.getPlayerFM().CT.getGear();
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(30) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                float f = com.maddox.il2.ai.World.getPlayerFM().CT.getGear();
                answer("" + fmt(f));
            }

        }
;
        new com.maddox.il2.game.Parameter(31) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].getControlMagnetos()
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(32) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].getRPM())
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(33) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].getManifoldPressure())
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(34) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].tOilIn)
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(35) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].tOilOut)
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(36) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].tWaterOut)
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.Parameter(37) {

            public boolean get_accessible()
            {
                return isFMValid() && isNetAccessible();
            }

            public boolean set_accessible()
            {
                return false;
            }

            public void get(java.util.List list)
            {
                int i = engineNum(list);
                if(i < 0)
                {
                    return;
                } else
                {
                    answer(new java.lang.String[] {
                        "" + i, "" + fmt(com.maddox.il2.ai.World.getPlayerFM().EI.engines[i].tWaterOut)
                    });
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(50, "pilot", "Stabilizer") {

            public boolean set_accessible()
            {
                return super.set_accessible() && (com.maddox.il2.ai.World.getPlayerAircraft() instanceof com.maddox.il2.objects.air.TypeBomber);
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.StabilizerControl ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(51, "pilot", "AIRCRAFT_TOGGLE_ENGINE") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(52, "pilot", "Boost") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlAfterburner();
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.isAfterburner() ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(53, "pilot", "MagnetoPlus") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlMagnetos() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected().getControlMagnetos() < 3;
            }

            public boolean get_accessible()
            {
                return super.get_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected().getControlMagnetos());
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(54, "pilot", "MagnetoMinus") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlMagnetos() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected().getControlMagnetos() > 0;
            }

            public boolean get_accessible()
            {
                return super.get_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected().getControlMagnetos());
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(55, "pilot", "CompressorPlus") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlCompressor() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().CT.getCompressorControl());
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(56, "pilot", "CompressorMinus") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlCompressor() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().CT.getCompressorControl());
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(57, "pilot", "EngineSelectAll") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(58, "pilot", "EngineSelectNone") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(59, "pilot", "EngineSelectLeft") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(60, "pilot", "EngineSelectRight") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(61, "pilot", "EngineSelect1") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(62, "pilot", "EngineSelect2") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(63, "pilot", "EngineSelect3") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(64, "pilot", "EngineSelect4") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(65, "pilot", "EngineSelect5") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(66, "pilot", "EngineSelect6") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(67, "pilot", "EngineSelect7") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(68, "pilot", "EngineSelect8") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(69, "pilot", "EngineToggleAll") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(70, "pilot", "EngineToggleLeft") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(71, "pilot", "EngineToggleRight") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(72, "pilot", "EngineToggle1") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(73, "pilot", "EngineToggle2") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(74, "pilot", "EngineToggle3") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(75, "pilot", "EngineToggle4") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(76, "pilot", "EngineToggle5") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(77, "pilot", "EngineToggle6") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(78, "pilot", "EngineToggle7") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(79, "pilot", "EngineToggle8") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(80, "pilot", "EngineExtinguisher") {

            public boolean get_accessible()
            {
                return false;
            }

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlExtinguisher();
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(81, "pilot", "EngineFeather") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlFeather() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement;
            }

            public boolean get_accessible()
            {
                return super.get_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlFeather() && com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected() != null;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.ai.World.getPlayerFM().EI.getFirstSelected().getControlFeather());
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(82, "pilot", "Gear") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasGearControl && !com.maddox.il2.ai.World.getPlayerFM().Gears.onGround() && com.maddox.il2.ai.World.getPlayerFM().Gears.isHydroOperable();
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.GearControl));
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(83, "pilot", "AIRCRAFT_GEAR_UP_MANUAL") {

            public boolean get_accessible()
            {
                return false;
            }

            public boolean set_accessible()
            {
                return super.set_accessible() && !com.maddox.il2.ai.World.getPlayerFM().Gears.onGround() && com.maddox.il2.ai.World.getPlayerFM().CT.GearControl > 0.0F && com.maddox.il2.ai.World.getPlayerFM().Gears.isOperable() && !com.maddox.il2.ai.World.getPlayerFM().Gears.isHydroOperable();
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(84, "pilot", "AIRCRAFT_GEAR_DOWN_MANUAL") {

            public boolean get_accessible()
            {
                return false;
            }

            public boolean set_accessible()
            {
                return super.set_accessible() && !com.maddox.il2.ai.World.getPlayerFM().Gears.onGround() && com.maddox.il2.ai.World.getPlayerFM().CT.GearControl < 1.0F && com.maddox.il2.ai.World.getPlayerFM().Gears.isOperable() && !com.maddox.il2.ai.World.getPlayerFM().Gears.isHydroOperable();
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(85, "pilot", "Radiator") {

            public boolean get_accessible()
            {
                return false;
            }

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlRadiator();
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(86, "pilot", "AIRCRAFT_TOGGLE_AIRBRAKE") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasAirBrakeControl;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.AirBrakeControl != 0.0F ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(87, "pilot", "AIRCRAFT_TAILWHEELLOCK") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasLockGearControl;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().Gears.bTailwheelLocked ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(88, "pilot", "AIRCRAFT_DROP_TANKS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(89, "pilot", "AIRCRAFT_DOCK_UNDOCK") {

            public boolean get_accessible()
            {
                return false;
            }

            public boolean set_accessible()
            {
                return super.set_accessible() && (com.maddox.il2.ai.World.getPlayerFM().actor instanceof com.maddox.il2.objects.air.TypeDockable);
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(90, "pilot", "Weapon0") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[0] ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(91, "pilot", "Weapon1") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[1] ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(92, "pilot", "Weapon2") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[2] ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(93, "pilot", "Weapon3") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[3] ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(94, "pilot", "Weapon01") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(!com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[0] || !com.maddox.il2.ai.World.getPlayerFM().CT.WeaponControl[1] ? "0" : "1");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(95, "pilot", "GunPods") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerAircraft().isGunPodsExist();
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerAircraft().isGunPodsOn() ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(96, "pilot", "SIGHT_AUTO_ONOFF") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(97, "pilot", "SIGHT_DIST_PLUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(98, "pilot", "SIGHT_DIST_MINUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(99, "pilot", "SIGHT_SIDE_RIGHT") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(100, "pilot", "SIGHT_SIDE_LEFT") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(101, "pilot", "SIGHT_ALT_PLUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(102, "pilot", "SIGHT_ALT_MINUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(103, "pilot", "SIGHT_SPD_PLUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(104, "pilot", "SIGHT_SPD_MINUS") {

            public boolean get_accessible()
            {
                return false;
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(95, "pilot", "WINGFOLD") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasWingControl;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.getWing() <= 0.99F ? "0" : "1");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(106, "pilot", "COCKPITDOOR") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasCockpitDoorControl;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.getCockpitDoor() <= 0.99F ? "0" : "1");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(107, "pilot", "AIRCRAFT_CARRIERHOOK") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasArrestorControl;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().CT.arrestorControl <= 0.5F ? "0" : "1");
            }

        }
;
        new com.maddox.il2.game.ParameterPilot(108, "pilot", "AIRCRAFT_BRAKESHOE") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().canChangeBrakeShoe;
            }

            public void get(java.util.List list)
            {
                answer(com.maddox.il2.ai.World.getPlayerFM().brakeShoe ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterGunner(110, "gunner", "Fire") {

            public boolean set_accessible()
            {
                return set_accessible0();
            }

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

            public void get(java.util.List list)
            {
                answer(cmd.isActive() ? "1" : "0");
            }

        }
;
        new com.maddox.il2.game.ParameterMouseMove(111, "gunner", "Mouse") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isAircraftValid() && !com.maddox.il2.ai.World.isPlayerParatrooper() && !com.maddox.il2.ai.World.isPlayerDead();
            }

        }
;
        new com.maddox.il2.game.ParameterMove(40, "move", "power") {

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.PowerControl / 0.55F - 1.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(41, "move", "flaps") {

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.FlapsControl / 0.5F - 1.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(42, "move", "aileron") {

            public boolean set_accessible()
            {
                return super.set_accessible() && !com.maddox.il2.ai.World.getPlayerFM().CT.StabilizerControl;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.AileronControl));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(43, "move", "elevator") {

            public boolean set_accessible()
            {
                return super.set_accessible() && !com.maddox.il2.ai.World.getPlayerFM().CT.StabilizerControl;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.ElevatorControl));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(44, "move", "rudder") {

            public boolean set_accessible()
            {
                return super.set_accessible() && !com.maddox.il2.ai.World.getPlayerFM().CT.StabilizerControl;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.RudderControl));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(45, "move", "brakes") {

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.BrakeControl / 0.5F - 1.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(46, "move", "pitch") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.cur().diffCur.ComplexEManagement && com.maddox.il2.ai.World.getPlayerFM().EI.isSelectionHasControlProp();
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.getStepControl() / 0.5F - 1.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(47, "move", "trimaileron") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasAileronTrim;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.getTrimAileronControl() * 2.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(48, "move", "trimelevator") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasElevatorTrim;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.getTrimElevatorControl() * 2.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterMove(49, "move", "trimrudder") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.il2.ai.World.getPlayerFM().CT.bHasRudderTrim;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.ai.World.getPlayerFM().CT.getTrimRudderControl() * 2.0F));
            }

        }
;
        new com.maddox.il2.game.ParameterView(150, "aircraftView", "changeCockpit") {

            public boolean get_accessible()
            {
                return com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx() >= 0;
            }

            public void get(java.util.List list)
            {
                answer("" + com.maddox.il2.game.Main3D.cur3D().cockpitCurIndx());
            }

        }
;
        new ParameterView(151, "aircraftView", "cockpitView0");
        new ParameterView(152, "aircraftView", "cockpitView1");
        new ParameterView(153, "aircraftView", "cockpitView2");
        new ParameterView(154, "aircraftView", "cockpitView3");
        new ParameterView(155, "aircraftView", "cockpitView4");
        new ParameterView(156, "aircraftView", "cockpitView5");
        new ParameterView(157, "aircraftView", "cockpitView6");
        new ParameterView(158, "aircraftView", "cockpitView7");
        new ParameterView(159, "aircraftView", "cockpitView8");
        new ParameterView(160, "aircraftView", "cockpitView9");
        new ParameterView(161, "aircraftView", "fov90");
        new ParameterView(162, "aircraftView", "fov85");
        new ParameterView(163, "aircraftView", "fov80");
        new ParameterView(164, "aircraftView", "fov75");
        new ParameterView(165, "aircraftView", "fov70");
        new ParameterView(166, "aircraftView", "fov65");
        new ParameterView(167, "aircraftView", "fov60");
        new ParameterView(168, "aircraftView", "fov55");
        new ParameterView(168, "aircraftView", "fov50");
        new ParameterView(170, "aircraftView", "fov45");
        new ParameterView(171, "aircraftView", "fov40");
        new ParameterView(172, "aircraftView", "fov35");
        new ParameterView(173, "aircraftView", "fov30");
        new com.maddox.il2.game.ParameterView(174, "aircraftView", "fovSwitch") {

            public boolean get_accessible()
            {
                return true;
            }

            public void get(java.util.List list)
            {
                answer("" + fmt(com.maddox.il2.game.Main3D.FOVX));
            }

        }
;
        new ParameterView(175, "aircraftView", "fovInc");
        new ParameterView(176, "aircraftView", "fovDec");
        new ParameterView(177, "aircraftView", "CockpitView");
        new ParameterView(178, "aircraftView", "CockpitShow");
        new ParameterView(179, "aircraftView", "OutsideView");
        new ParameterView(180, "aircraftView", "NextView");
        new ParameterView(181, "aircraftView", "NextViewEnemy");
        new ParameterView(182, "aircraftView", "OutsideViewFly");
        new ParameterView(183, "aircraftView", "PadlockView");
        new ParameterView(184, "aircraftView", "PadlockViewFriend");
        new ParameterView(185, "aircraftView", "PadlockViewGround");
        new ParameterView(186, "aircraftView", "PadlockViewFriendGround");
        new ParameterView(187, "aircraftView", "PadlockViewNext");
        new ParameterView(188, "aircraftView", "PadlockViewPrev");
        new com.maddox.il2.game.ParameterView(189, "aircraftView", "PadlockViewForward") {

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

        }
;
        new ParameterView(190, "aircraftView", "ViewEnemyAir");
        new ParameterView(191, "aircraftView", "ViewFriendAir");
        new ParameterView(192, "aircraftView", "ViewEnemyDirectAir");
        new ParameterView(193, "aircraftView", "ViewEnemyGround");
        new ParameterView(194, "aircraftView", "ViewFriendGround");
        new ParameterView(195, "aircraftView", "ViewEnemyDirectGround");
        new ParameterView(196, "aircraftView", "OutsideViewFollow");
        new ParameterView(197, "aircraftView", "NextViewFollow");
        new ParameterView(198, "aircraftView", "NextViewEnemyFollow");
        new ParameterView(199, "aircraftView", "cockpitAim");
        new ParameterTrackIR(5, "PanView", "TrackIR");
        new com.maddox.il2.game.ParameterMisc(200, "misc", "autopilot") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(203, "misc", "cockpitDim") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid() && !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(204, "misc", "cockpitLight") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid() && !com.maddox.il2.game.Main3D.cur3D().isViewOutside();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(205, "misc", "toggleNavLights") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(206, "misc", "toggleLandingLight") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(207, "misc", "toggleSmokes") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(201, "misc", "autopilotAuto") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(202, "misc", "ejectPilot") {

            public boolean set_accessible()
            {
                return super.set_accessible() && isFMValid() && !com.maddox.il2.ai.World.isPlayerGunner();
            }

        }
;
        new ParameterMisc(208, "misc", "pad");
        new ParameterMisc(209, "misc", "chat");
        new ParameterMisc(211, "misc", "showPositionHint");
        new ParameterMisc(212, "misc", "iconTypes");
        new ParameterMisc(213, "misc", "showMirror");
        new ParameterMisc(214, "$$$misc", "quickSaveNetTrack");
        new ParameterMisc(216, "$$$misc", "radioChannelSwitch");
        new com.maddox.il2.game.ParameterMisc(210, "misc", "onlineRating") {

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(215, "$$$misc", "radioMuteKey") {

            public void set(java.util.List list)
            {
                if(list == null || list.size() != 1)
                {
                    return;
                } else
                {
                    boolean flag = "1".equals(list.get(0));
                    com.maddox.rts.HotKeyCmd.exec(flag, cmd.hotKeyCmdEnv().name(), cmd.name());
                    return;
                }
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(217, "timeCompression", "timeSpeedUp") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.rts.Time.isEnableChangeSpeed();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(218, "timeCompression", "timeSpeedNormal") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.rts.Time.isEnableChangeSpeed();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(219, "timeCompression", "timeSpeedDown") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.rts.Time.isEnableChangeSpeed();
            }

        }
;
        new com.maddox.il2.game.ParameterMisc(220, "hotkeys", "pause") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.rts.Time.isEnableChangeSpeed();
            }

        }
;
        new ParameterMisc(221, "misc", "onlineRatingPage");
        new ParameterMisc(222, "misc", "soundMuteKey");
        new ParameterView(223, "aircraftView", "cockpitUp");
        new com.maddox.il2.game.ParameterMisc(224, "timeCompression", "timeSkip") {

            public boolean set_accessible()
            {
                return super.set_accessible() && com.maddox.rts.Time.isEnableChangeSpeed();
            }

        }
;
    }

    private void receiveParam(int i, java.util.List list)
    {
        try
        {
            com.maddox.il2.game.Parameter parameter = (com.maddox.il2.game.Parameter)paramMap.get(i & -2);
            if(parameter != null)
                if((i & 1) == 0)
                {
                    if(parameter.get_accessible())
                        parameter.get(list);
                } else
                if(parameter.set_accessible())
                    parameter.set(list);
        }
        catch(java.lang.Throwable throwable)
        {
            java.lang.System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    private void serverReceivePacket(java.net.DatagramPacket datagrampacket)
        throws java.io.IOException
    {
        if(datagrampacket.getLength() < 1)
            return;
        inputMsg.setData(null, false, datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
        int i = inputMsg.readUnsignedByte();
        if(i != 82)
            return;
        inputMsg.fixed();
        outList.clear();
        do
        {
            int j = receiveKey();
            if(j == 0)
                break;
            inArg.clear();
            do
            {
                java.lang.String s = receiveArg();
                if(s == null)
                    break;
                inArg.add(s);
            } while(true);
            receiveParam(j, inArg);
        } while(true);
        for(; outList.size() > 0; serverSocket.send(outPacket))
        {
            int k = PACKET_SIZE - 1;
            byte abyte0[] = outPacket.getData();
            int l = 0;
            abyte0[l++] = 65;
            do
            {
                if(outList.size() <= 0)
                    break;
                java.lang.String s1 = (java.lang.String)outList.get(0);
                int i1 = s1.length();
                if(i1 > k)
                    break;
                outList.remove(0);
                int j1 = 0;
                while(j1 < i1) 
                {
                    abyte0[l++] = (byte)(s1.charAt(j1) & 0x7f);
                    j1++;
                }
            } while(true);
            outPacket.setAddress(datagrampacket.getAddress());
            outPacket.setPort(datagrampacket.getPort());
            outPacket.setLength(l);
        }

    }

    private void do_answer(int i, java.lang.String s)
    {
        if(inOutBuf.length() > 0)
            inOutBuf.delete(0, inOutBuf.length());
        inOutBuf.append("/" + i);
        if(s != null)
        {
            inOutBuf.append('\\');
            int j = s.length();
            for(int k = 0; k < j; k++)
            {
                char c = s.charAt(k);
                if(c == '\\' || c == '/')
                    inOutBuf.append('\\');
                inOutBuf.append(c);
            }

        }
        outList.add(inOutBuf.toString());
    }

    private void do_answer(int i, java.lang.String as[])
    {
        if(inOutBuf.length() > 0)
            inOutBuf.delete(0, inOutBuf.length());
        inOutBuf.append("/" + i);
        if(as != null)
        {
            int j = as.length;
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = as[k];
                inOutBuf.append('\\');
                int l = s.length();
                for(int i1 = 0; i1 < l; i1++)
                {
                    char c = s.charAt(i1);
                    if(c == '\\' || c == '/')
                        inOutBuf.append('\\');
                    inOutBuf.append(c);
                }

            }

        }
        outList.add(inOutBuf.toString());
    }

    private void do_answer(int i, java.util.List list)
    {
        if(inOutBuf.length() > 0)
            inOutBuf.delete(0, inOutBuf.length());
        inOutBuf.append("/" + i);
        if(list != null)
        {
            int j = list.size();
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = (java.lang.String)list.get(k);
                inOutBuf.append('\\');
                int l = s.length();
                for(int i1 = 0; i1 < l; i1++)
                {
                    char c = s.charAt(i1);
                    if(c == '\\' || c == '/')
                        inOutBuf.append('\\');
                    inOutBuf.append(c);
                }

            }

        }
        outList.add(inOutBuf.toString());
    }

    private int receiveKey()
        throws java.io.IOException
    {
        if(receiveChar() != '/')
            return 0;
        int i;
        char c;
        for(i = 0; inputMsg.available() > 0; i = i * 10 + (c - 48))
        {
            c = receiveChar();
            if(c == '/' || c == '\\')
            {
                inputMsg.reset();
                return i;
            }
            if(c < '0' || c > '9')
                return 0;
        }

        return i;
    }

    private java.lang.String receiveArg()
        throws java.io.IOException
    {
        char c = receiveChar();
        if(c == '/')
        {
            inputMsg.reset();
            return null;
        }
        if(c != '\\')
        {
            inputMsg.reset();
            return null;
        }
        if(inOutBuf.length() > 0)
            inOutBuf.delete(0, inOutBuf.length());
        do
        {
            char c1 = receiveChar(false);
            if(c1 == 0)
            {
                inputMsg.fixed();
                break;
            }
            if(c1 == '/')
            {
                inputMsg.reset();
                break;
            }
            if(c1 == '\\')
            {
                if(inputMsg.available() > 0)
                {
                    char c2 = receiveChar(false);
                    if(c2 == '\\' || c2 == '/')
                    {
                        inputMsg.fixed();
                        inOutBuf.append(c2);
                        continue;
                    }
                    inputMsg.reset();
                } else
                {
                    inputMsg.reset();
                }
                break;
            }
            inputMsg.fixed();
            inOutBuf.append(c1);
        } while(true);
        if(inOutBuf.length() > 0)
            return inOutBuf.toString();
        else
            return null;
    }

    private char receiveChar()
        throws java.io.IOException
    {
        return receiveChar(true);
    }

    private char receiveChar(boolean flag)
        throws java.io.IOException
    {
        while(inputMsg.available() > 0) 
        {
            if(flag)
                inputMsg.fixed();
            int i = inputMsg.readUnsignedByte();
            if(i >= 33 && i <= 126)
                return (char)i;
        }
        return '\0';
    }

    private DeviceLink(int i, java.util.ArrayList arraylist)
    {
        paramMap = new HashMapInt();
        outPacket = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
        inputMsg = new NetMsgInput();
        inArg = new ArrayList();
        inOutBuf = new StringBuffer();
        outList = new LinkedList();
        enableClientAdr = arraylist;
        try
        {
            java.net.InetAddress inetaddress = null;
            java.lang.String s = com.maddox.il2.engine.Config.cur.ini.get("DeviceLink", "host", (java.lang.String)null);
            if(s != null && s.length() > 0)
                inetaddress = java.net.InetAddress.getByName(s);
            else
                inetaddress = java.net.InetAddress.getLocalHost();
            serverSocket = new DatagramSocket(i, inetaddress);
            serverSocket.setSoTimeout(0);
            serverSocket.setSendBufferSize(PACKET_SIZE);
            serverSocket.setReceiveBufferSize(PACKET_SIZE);
            inputPackets = new LinkedList();
            inputAction = new ActionReceivedPacket();
            registerParams();
            listener = new Listener();
            listener.setPriority(java.lang.Thread.currentThread().getPriority() + 1);
            listener.start();
        }
        catch(java.lang.Throwable throwable)
        {
            java.lang.System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    public static void start()
    {
        if(deviceLink != null)
            return;
        int i = com.maddox.il2.engine.Config.cur.ini.get("DeviceLink", "port", 0, 0, 65000);
        if(i == 0)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        java.lang.String s = com.maddox.il2.engine.Config.cur.ini.get("DeviceLink", "IPS", (java.lang.String)null);
        if(s != null)
        {
            java.lang.String s1;
            for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s); stringtokenizer.hasMoreTokens(); arraylist.add(s1))
                s1 = stringtokenizer.nextToken();

        }
        deviceLink = new DeviceLink(i, arraylist);
    }

    private static int PACKET_SIZE = 2048;
    private static final int VERSION = 1;
    private static final int GET_ACCESSIBLE = 2;
    private static final int SET_ACCESSIBLE = 3;
    private static final int TRACKIR = 5;
    private static final int TIME_OF_DAY = 10;
    private static final int PLANE = 11;
    private static final int COCKPITS = 12;
    private static final int COCKPIT_CUR = 13;
    private static final int ENGINES = 14;
    private static final int SPEEDOMETER = 15;
    private static final int VARIOMETER = 16;
    private static final int SLIP = 17;
    private static final int TURN = 18;
    private static final int ANGULAR_SPEED = 19;
    private static final int ALTIMETER = 20;
    private static final int AZIMUT = 21;
    private static final int BEACON_AZIMUT = 22;
    private static final int ROLL = 23;
    private static final int PITCH = 24;
    private static final int FUEL = 25;
    private static final int OVERLOAD = 26;
    private static final int SHAKE_LEVEL = 27;
    private static final int GEAR_POS_L = 28;
    private static final int GEAR_POS_R = 29;
    private static final int GEAR_POS_C = 30;
    private static final int MAGNETO = 31;
    private static final int RPM = 32;
    private static final int MANIFOLD = 33;
    private static final int TEMP_OILIN = 34;
    private static final int TEMP_OILOUT = 35;
    private static final int TEMP_WATER = 36;
    private static final int TEMP_CYLINDERS = 37;
    private static final int M_POWER = 40;
    private static final int M_FLAPS = 41;
    private static final int M_AILERON = 42;
    private static final int M_ELEVATOR = 43;
    private static final int M_RUDDER = 44;
    private static final int M_BRAKES = 45;
    private static final int M_PITCH = 46;
    private static final int M_TRIMAILERON = 47;
    private static final int M_TRIMELEVATOR = 48;
    private static final int M_TRIMRUDDER = 49;
    private static final int STABILIZER = 50;
    private static final int TOGGLE_ENGINE = 51;
    private static final int BOOST = 52;
    private static final int MAGNETO_PLUS = 53;
    private static final int MAGNETO_MINUS = 54;
    private static final int COMPRESSOR_PLUS = 55;
    private static final int COMPRESSOR_MINUS = 56;
    private static final int ENGINE_SELECT_ALL = 57;
    private static final int ENGINE_SELECT_NONE = 58;
    private static final int ENGINE_SELECT_LEFT = 59;
    private static final int ENGINE_SELECT_RIGHT = 60;
    private static final int ENGINE_SELECT_1 = 61;
    private static final int ENGINE_SELECT_2 = 62;
    private static final int ENGINE_SELECT_3 = 63;
    private static final int ENGINE_SELECT_4 = 64;
    private static final int ENGINE_SELECT_5 = 65;
    private static final int ENGINE_SELECT_6 = 66;
    private static final int ENGINE_SELECT_7 = 67;
    private static final int ENGINE_SELECT_8 = 68;
    private static final int ENGINE_TOGGLE_ALL = 69;
    private static final int ENGINE_TOGGLE_LEFT = 70;
    private static final int ENGINE_TOGGLE_RIGHT = 71;
    private static final int ENGINE_TOGGLE_1 = 72;
    private static final int ENGINE_TOGGLE_2 = 73;
    private static final int ENGINE_TOGGLE_3 = 74;
    private static final int ENGINE_TOGGLE_4 = 75;
    private static final int ENGINE_TOGGLE_5 = 76;
    private static final int ENGINE_TOGGLE_6 = 77;
    private static final int ENGINE_TOGGLE_7 = 78;
    private static final int ENGINE_TOGGLE_8 = 79;
    private static final int ENGINE_EXTINGUISHER = 80;
    private static final int ENGINE_FEATHER = 81;
    private static final int GEAR = 82;
    private static final int GEAR_UP_MANUAL = 83;
    private static final int GEAR_DOWN_MANUAL = 84;
    private static final int RADIATOR = 85;
    private static final int TOGGLE_AIRBRAKE = 86;
    private static final int TAILWHEELLOCK = 87;
    private static final int DROP_TANKS = 88;
    private static final int DOCK_UNDOCK = 89;
    private static final int WEAPON0 = 90;
    private static final int WEAPON1 = 91;
    private static final int WEAPON2 = 92;
    private static final int WEAPON3 = 93;
    private static final int WEAPON01 = 94;
    private static final int GUNPODS = 95;
    private static final int SIGHT_AUTO_ONOFF = 96;
    private static final int SIGHT_DIST_PLUS = 97;
    private static final int SIGHT_DIST_MINUS = 98;
    private static final int SIGHT_SIDE_RIGHT = 99;
    private static final int SIGHT_SIDE_LEFT = 100;
    private static final int SIGHT_ALT_PLUS = 101;
    private static final int SIGHT_ALT_MINUS = 102;
    private static final int SIGHT_SPD_PLUS = 103;
    private static final int SIGHT_SPD_MINUS = 104;
    private static final int WINGFOLD = 105;
    private static final int COCKPITDOOR = 106;
    private static final int CARRIERHOOK = 107;
    private static final int BRAKESHOE = 108;
    private static final int GUNNER_FIRE = 110;
    private static final int GUNNER_MOVE = 111;
    private static final int VchangeCockpit = 150;
    private static final int VcockpitView0 = 151;
    private static final int VcockpitView1 = 152;
    private static final int VcockpitView2 = 153;
    private static final int VcockpitView3 = 154;
    private static final int VcockpitView4 = 155;
    private static final int VcockpitView5 = 156;
    private static final int VcockpitView6 = 157;
    private static final int VcockpitView7 = 158;
    private static final int VcockpitView8 = 159;
    private static final int VcockpitView9 = 160;
    private static final int Vfov90 = 161;
    private static final int Vfov85 = 162;
    private static final int Vfov80 = 163;
    private static final int Vfov75 = 164;
    private static final int Vfov70 = 165;
    private static final int Vfov65 = 166;
    private static final int Vfov60 = 167;
    private static final int Vfov55 = 168;
    private static final int Vfov50 = 169;
    private static final int Vfov45 = 170;
    private static final int Vfov40 = 171;
    private static final int Vfov35 = 172;
    private static final int Vfov30 = 173;
    private static final int VfovSwitch = 174;
    private static final int VfovInc = 175;
    private static final int VfovDec = 176;
    private static final int VCockpitView = 177;
    private static final int VCockpitShow = 178;
    private static final int VOutsideView = 179;
    private static final int VNextView = 180;
    private static final int VNextViewEnemy = 181;
    private static final int VOutsideViewFly = 182;
    private static final int VPadlockView = 183;
    private static final int VPadlockViewFriend = 184;
    private static final int VPadlockViewGround = 185;
    private static final int VPadlockViewFriendGround = 186;
    private static final int VPadlockViewNext = 187;
    private static final int VPadlockViewPrev = 188;
    private static final int VPadlockViewForward = 189;
    private static final int VViewEnemyAir = 190;
    private static final int VViewFriendAir = 191;
    private static final int VViewEnemyDirectAir = 192;
    private static final int VViewEnemyGround = 193;
    private static final int VViewFriendGround = 194;
    private static final int VViewEnemyDirectGround = 195;
    private static final int VOutsideViewFollow = 196;
    private static final int VNextViewFollow = 197;
    private static final int VNextViewEnemyFollow = 198;
    private static final int VcockpitAim = 199;
    private static final int AUTOPILOT = 200;
    private static final int AUTOPILOT_AUTO = 201;
    private static final int EJECTPILOT = 202;
    private static final int COCKPIT_DIM = 203;
    private static final int COCKPIT_LIGHT = 204;
    private static final int TORGLE_NAV_LIGHTS = 205;
    private static final int TORGLE_LANDING_LIGHTS = 206;
    private static final int TORGLE_SMOKES = 207;
    private static final int PAD = 208;
    private static final int CHAT = 209;
    private static final int ONLINE_RATING = 210;
    private static final int SHOW_POSITION_HINT = 211;
    private static final int ICON_TYPES = 212;
    private static final int SHOW_MIRROR = 213;
    private static final int QUICK_SAVE_NET_TRACK = 214;
    private static final int RADIO_MUTE_KEY = 215;
    private static final int RADIO_CHANNEL_SWITCH = 216;
    private static final int TIME_SPEED_UP = 217;
    private static final int TIME_SPEED_NORMAL = 218;
    private static final int TIME_SPEED_DOWN = 219;
    private static final int TIME_SPEED_PAUSE = 220;
    private static final int ONLINE_RATING_PAGE = 221;
    private static final int SOUND_MUTE_KEY = 222;
    private static final int COCKPIT_UP = 223;
    private static final int TIME_SKIP = 224;
    private static final int POSITION = 300;
    private static final int ORIENTATION = 301;
    private static final int SPEED = 302;
    private static final int ACCEL = 303;
    private static final int COWL_FLAPS_POS = 310;
    private static final int FLAPS_POS = 311;
    private static final int BRAKES_POS = 312;
    private static final int MACHINE_GUNS = 320;
    private static final int FUEL_TANKS = 321;
    private static final int ENGINE_STATUS_FIRE = 322;
    private static final int ENGINE_STATUS_TEMPERATURE = 323;
    private static final int ENGINE_STATUS_IGNITION = 324;
    private static final int ENGINE_STATUS_SUPERCHARGER = 325;
    private static final int ENGINE_STATUS_FUEL = 326;
    private static final int CONTROLS_STATUS = 327;
    private static final int COWL_FLAPS = 330;
    private static final int CANOPY = 331;
    private static final int ENGINE_MIX = 332;
    private static final int MAGNETO_SET = 333;
    private com.maddox.util.HashMapInt paramMap;
    private java.net.DatagramSocket serverSocket;
    private java.util.ArrayList enableClientAdr;
    private com.maddox.il2.game.Listener listener;
    private java.util.List inputPackets;
    private java.net.DatagramPacket outPacket;
    private com.maddox.il2.game.ActionReceivedPacket inputAction;
    private com.maddox.rts.NetMsgInput inputMsg;
    private java.util.ArrayList inArg;
    private java.lang.StringBuffer inOutBuf;
    private java.util.LinkedList outList;
    private static com.maddox.il2.game.DeviceLink deviceLink;











}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TimeSkip.java

package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.game:
//            Main3D, HUD, Main, Mission, 
//            AircraftHotKeys

public class TimeSkip
    implements com.maddox.rts.MsgTimeOutListener
{
    class SkipRender extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
        }

        public void render()
        {
            long l = com.maddox.rts.Time.current();
            int i = (int)((l / 1000L) % 60L);
            int j = (int)(l / 1000L / 60L);
            java.lang.String s = null;
            if(i > 9)
                s = "" + j + ":" + i;
            else
                s = "" + j + ":0" + i;
            font.output(-1, getViewPortWidth() - font.height() * 4, 5F, 0.0F, s);
        }

        public boolean isShow()
        {
            return _isDo();
        }

        private SkipRender(float f)
        {
            super(f);
            useClearDepth(false);
            useClearColor(true);
        }

    }


    private void checkStop()
    {
        if(com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
            if(wayPoint != com.maddox.il2.ai.World.getPlayerFM().AP.way.curr())
                com.maddox.il2.game.HUD.log("WaypointReached");
            else
            if(!com.maddox.il2.ai.World.getPlayerFM().isOk())
                com.maddox.il2.game.HUD.log("AicraftDamaged");
            else
            if(airAction != 0)
                switch(airAction)
                {
                case 1: // '\001'
                    com.maddox.il2.game.HUD.log("AircraftCollided");
                    break;

                case 2: // '\002'
                case 3: // '\003'
                    com.maddox.il2.game.HUD.log("EnemiesAreNearby");
                    break;
                }
            else
            if((double)(com.maddox.il2.ai.World.getPlayerFM().M.fuel / com.maddox.il2.ai.World.getPlayerFM().M.maxFuel) < 0.050000000000000003D)
                com.maddox.il2.game.HUD.log("FuelLow");
            else
            if(isExistEnemy())
                com.maddox.il2.game.HUD.log("EnemiesAreNearby");
            else
                return;
        stop();
    }

    public static void airAction(int i)
    {
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D))
        {
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().timeSkip._airAction(i);
            return;
        }
    }

    private void _airAction(int i)
    {
        airAction = i;
    }

    private boolean checkStart()
    {
        wayPoint = com.maddox.il2.ai.World.getPlayerFM().AP.way.curr();
        if(wayPoint.Action != 0)
        {
            if(wayPoint.Action == 3)
                com.maddox.il2.game.HUD.log("STargetIsNearby");
            else
                com.maddox.il2.game.HUD.log("SNowhereToSkip");
        } else
        if(!com.maddox.il2.ai.World.getPlayerFM().isOk())
            com.maddox.il2.game.HUD.log("SAicraftDamaged");
        else
        if((double)(com.maddox.il2.ai.World.getPlayerFM().M.fuel / com.maddox.il2.ai.World.getPlayerFM().M.maxFuel) < 0.050000000000000003D)
            com.maddox.il2.game.HUD.log("SFuelLow");
        else
        if(isExistEnemy())
        {
            com.maddox.il2.game.HUD.log("SEnemiesAreNearby");
        } else
        {
            airAction = 0;
            return true;
        }
        wayPoint = null;
        return false;
    }

    private boolean isExistEnemy()
    {
        double d = 16000000D;
        com.maddox.JGP.Point3d point3d = com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsPoint();
        int i = com.maddox.il2.ai.World.getPlayerAircraft().getArmy();
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if(actor.getArmy() != i)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y);
                if(d1 <= d)
                    return true;
            }
        }

        return false;
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        if(com.maddox.il2.game.TimeSkip.isDo())
        {
            if(!ticker.busy())
                ticker.post();
        } else
        {
            return;
        }
        checkStop();
    }

    public void start()
    {
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D))
            return;
        if(!com.maddox.il2.game.Mission.isSingle())
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(com.maddox.rts.Time.isPaused())
            return;
        if(com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            return;
        if(com.maddox.il2.ai.World.isPlayerDead())
            return;
        if(!checkStart())
            return;
        com.maddox.rts.Time.bShowDiag = false;
        bDo = true;
        activateHotKeys(false);
        bAutopilot = !com.maddox.il2.game.AircraftHotKeys.isCockpitRealMode(0);
        if(!bAutopilot)
            com.maddox.il2.game.AircraftHotKeys.setCockpitRealMode(0, false);
        if(com.maddox.il2.net.NetMissionTrack.isRecording())
            com.maddox.il2.net.NetMissionTrack.stopRecording();
        if(com.maddox.il2.net.NetMissionTrack.countRecorded == 0)
            com.maddox.il2.net.NetMissionTrack.countRecorded++;
        com.maddox.il2.game.Main3D.cur3D().keyRecord.stopRecording(false);
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        com.maddox.il2.game.Main3D.cur3D().keyRecord.clearListExcludeCmdEnv();
        com.maddox.il2.engine.RendersMain.setRenderFocus(render);
        com.maddox.il2.engine.Engine.rendersMain().setMaxFps(10F);
        com.maddox.sound.AudioDevice.soundsOff();
        com.maddox.rts.Time.setSpeed(256F);
        if(!ticker.busy())
            ticker.post();
    }

    public void stop()
    {
        if(!_isDo())
            return;
        com.maddox.rts.Time.bShowDiag = true;
        bDo = false;
        com.maddox.rts.Time.setSpeed(1.0F);
        activateHotKeys(true);
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
            com.maddox.il2.game.AircraftHotKeys.setCockpitRealMode(0, !bAutopilot);
        com.maddox.il2.engine.RendersMain.setRenderFocus(null);
        com.maddox.il2.engine.Engine.rendersMain().setMaxFps(-1F);
        com.maddox.sound.AudioDevice.soundsOn();
        wayPoint = null;
    }

    public boolean _isDo()
    {
        return bDo;
    }

    public static boolean isDo()
    {
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D))
            return false;
        else
            return com.maddox.il2.game.Main3D.cur3D().timeSkip._isDo();
    }

    private void activateHotKeys(boolean flag)
    {
        if(!flag)
        {
            java.util.List list = com.maddox.rts.HotKeyEnv.allEnv();
            int j = list.size();
            for(int l = 0; l < j; l++)
            {
                com.maddox.rts.HotKeyEnv hotkeyenv1 = (com.maddox.rts.HotKeyEnv)list.get(l);
                if(hotkeyenv1.isEnabled() && !"timeCompression".equals(hotkeyenv1.name()))
                {
                    hotkeyenv1.enable(false);
                    pausedEnv.add(hotkeyenv1);
                }
            }

        } else
        {
            int i = pausedEnv.size();
            for(int k = 0; k < i; k++)
            {
                com.maddox.rts.HotKeyEnv hotkeyenv = (com.maddox.rts.HotKeyEnv)pausedEnv.get(k);
                hotkeyenv.enable(true);
            }

            pausedEnv.clear();
        }
    }

    protected TimeSkip(float f)
    {
        bDo = false;
        bAutopilot = false;
        wayPoint = null;
        airAction = 0;
        pausedEnv = new ArrayList();
        font = com.maddox.il2.engine.TTFont.font[1];
        render = new SkipRender(f);
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = new CameraOrtho2D();
        cameraortho2d.set(0.0F, render.getViewPortWidth(), 0.0F, render.getViewPortHeight());
        render.setCamera(cameraortho2d);
        render.setName("renderTimeSkip");
        ticker = new MsgTimeOut(null);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(8);
        ticker.setListener(this);
    }

    public static final int COLLISION = 1;
    public static final int SHOT = 2;
    public static final int EXPLOSION = 3;
    private static final boolean DEBUG = false;
    private boolean bDo;
    private boolean bAutopilot;
    private com.maddox.il2.ai.WayPoint wayPoint;
    private int airAction;
    private com.maddox.rts.MsgTimeOut ticker;
    private java.util.ArrayList pausedEnv;
    private com.maddox.il2.engine.TTFont font;
    private com.maddox.il2.game.SkipRender render;

}

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

public class TimeSkip
  implements MsgTimeOutListener
{
  public static final int COLLISION = 1;
  public static final int SHOT = 2;
  public static final int EXPLOSION = 3;
  private static final boolean DEBUG = false;
  private boolean bDo = false;
  private boolean bAutopilot = false;
  private WayPoint wayPoint = null;
  private int airAction = 0;
  private MsgTimeOut ticker;
  private ArrayList pausedEnv = new ArrayList();

  private TTFont font = TTFont.font[1];
  private SkipRender render;

  private void checkStop()
  {
    if (Actor.isAlive(World.getPlayerAircraft()))
    {
      if (this.wayPoint != World.getPlayerFM().jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr())
      {
        HUD.log("WaypointReached");
      }
      else if (!World.getPlayerFM().isOk())
      {
        HUD.log("AicraftDamaged");
      }
      else if (this.airAction != 0)
      {
        switch (this.airAction) { case 1:
          HUD.log("AircraftCollided"); break;
        case 2:
        case 3:
          HUD.log("EnemiesAreNearby");
        }

      }
      else if (World.getPlayerFM().jdField_M_of_type_ComMaddoxIl2FmMass.fuel / World.getPlayerFM().jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel < 0.05D)
      {
        HUD.log("FuelLow");
      }
      else if (isExistEnemy())
      {
        HUD.log("EnemiesAreNearby");
      }
      else
      {
        return;
      }
    }
    stop();
  }

  public static void airAction(int paramInt) {
    if (!(Main.cur() instanceof Main3D))
      return;
    Main3D.cur3D().timeSkip._airAction(paramInt);
  }

  private void _airAction(int paramInt) {
    this.airAction = paramInt;
  }

  private boolean checkStart()
  {
    this.wayPoint = World.getPlayerFM().jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr();
    if (this.wayPoint.Action != 0)
    {
      if (this.wayPoint.Action == 3)
        HUD.log("STargetIsNearby");
      else {
        HUD.log("SNowhereToSkip");
      }
    }
    else if (!World.getPlayerFM().isOk())
    {
      HUD.log("SAicraftDamaged");
    }
    else if (World.getPlayerFM().jdField_M_of_type_ComMaddoxIl2FmMass.fuel / World.getPlayerFM().jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel < 0.05D)
    {
      HUD.log("SFuelLow");
    }
    else if (isExistEnemy())
    {
      HUD.log("SEnemiesAreNearby");
    }
    else
    {
      this.airAction = 0;
      return true;
    }
    this.wayPoint = null;
    return false;
  }

  private boolean isExistEnemy() {
    double d1 = 16000000.0D;
    Point3d localPoint3d1 = World.getPlayerAircraft().pos.getAbsPoint();
    int i = World.getPlayerAircraft().getArmy();
    List localList = Engine.targets();
    int j = localList.size();
    for (int k = 0; k < j; k++) {
      Actor localActor = (Actor)localList.get(k);
      if (localActor.getArmy() == i)
        continue;
      Point3d localPoint3d2 = localActor.pos.getAbsPoint();
      double d2 = (localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) * (localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) + (localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double) * (localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double);

      if (d2 <= d1) {
        return true;
      }
    }
    return false;
  }

  public void msgTimeOut(Object paramObject) {
    if (isDo()) {
      if (!this.ticker.busy()) this.ticker.post(); 
    }
    else {
      return;
    }
    checkStop();
  }

  public void start() {
    if (!(Main.cur() instanceof Main3D)) return;
    if (!Mission.isSingle()) return;
    if (!Mission.isPlaying()) return;
    if (Time.isPaused()) return;
    if (Main3D.cur3D().isDemoPlaying()) return;
    if (!Actor.isValid(World.getPlayerAircraft())) return;
    if (World.isPlayerDead()) return;

    if (!checkStart()) return;

    Time.bShowDiag = false;
    this.bDo = true;

    activateHotKeys(false);

    this.bAutopilot = (!AircraftHotKeys.isCockpitRealMode(0));
    if (!this.bAutopilot) {
      AircraftHotKeys.setCockpitRealMode(0, false);
    }
    if (NetMissionTrack.isRecording()) {
      NetMissionTrack.stopRecording();
    }
    if (NetMissionTrack.countRecorded == 0) {
      NetMissionTrack.countRecorded += 1;
    }
    Main3D.cur3D().keyRecord.stopRecording(false);
    Main3D.cur3D().keyRecord.clearRecorded();
    Main3D.cur3D().keyRecord.clearListExcludeCmdEnv();

    RendersMain.setRenderFocus(this.render);
    Engine.rendersMain().setMaxFps(10.0F);

    AudioDevice.soundsOff();

    Time.setSpeed(256.0F);

    if (!this.ticker.busy())
      this.ticker.post(); 
  }

  public void stop() {
    if (!_isDo()) return;

    Time.bShowDiag = true;
    this.bDo = false;
    Time.setSpeed(1.0F);

    activateHotKeys(true);
    if (Actor.isValid(World.getPlayerAircraft())) {
      AircraftHotKeys.setCockpitRealMode(0, !this.bAutopilot);
    }
    RendersMain.setRenderFocus(null);
    Engine.rendersMain().setMaxFps(-1.0F);

    AudioDevice.soundsOn();

    this.wayPoint = null;
  }

  public boolean _isDo()
  {
    return this.bDo;
  }

  public static boolean isDo() {
    if (!(Main.cur() instanceof Main3D))
      return false;
    return Main3D.cur3D().timeSkip._isDo();
  }

  private void activateHotKeys(boolean paramBoolean)
  {
    int j;
    if (!paramBoolean) {
      List localList = HotKeyEnv.allEnv();
      j = localList.size();
      for (int k = 0; k < j; k++) {
        HotKeyEnv localHotKeyEnv2 = (HotKeyEnv)localList.get(k);
        if ((localHotKeyEnv2.isEnabled()) && (!"timeCompression".equals(localHotKeyEnv2.name()))) {
          localHotKeyEnv2.enable(false);
          this.pausedEnv.add(localHotKeyEnv2);
        }
      }
    } else {
      int i = this.pausedEnv.size();
      for (j = 0; j < i; j++) {
        HotKeyEnv localHotKeyEnv1 = (HotKeyEnv)this.pausedEnv.get(j);
        localHotKeyEnv1.enable(true);
      }
      this.pausedEnv.clear();
    }
  }

  protected TimeSkip(float paramFloat)
  {
    this.render = new SkipRender(paramFloat, null);
    CameraOrtho2D localCameraOrtho2D = new CameraOrtho2D();
    localCameraOrtho2D.set(0.0F, this.render.getViewPortWidth(), 0.0F, this.render.getViewPortHeight());
    this.render.setCamera(localCameraOrtho2D);
    this.render.setName("renderTimeSkip");
    this.ticker = new MsgTimeOut(null);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(8);
    this.ticker.setListener(this);
  }

  class SkipRender extends Render
  {
    private final TimeSkip this$0;

    public void preRender()
    {
    }

    public void render()
    {
      long l = Time.current();
      int i = (int)(l / 1000L % 60L);
      int j = (int)(l / 1000L / 60L);
      String str = null;
      if (i > 9) str = "" + j + ":" + i; else
        str = "" + j + ":0" + i;
      this.this$0.font.output(-1, getViewPortWidth() - this.this$0.font.height() * 4, 5.0F, 0.0F, str);
    }
    public boolean isShow() {
      return this.this$0._isDo();
    }
    private SkipRender(float arg2) {
      super();

      this.this$0 = this$1;

      useClearDepth(false);
      useClearColor(true);
    }

    SkipRender(float param1, TimeSkip.1 arg3)
    {
      this(param1);
    }
  }
}
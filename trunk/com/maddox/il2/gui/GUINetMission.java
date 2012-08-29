package com.maddox.il2.gui;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.game.ZutiTimer_Refly;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.NetEnv;

public class GUINetMission extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bVideo;
  public GUIButton b3d;
  public GUIButton bSound;
  public GUIButton bControls;
  public GUIButton bNet;
  public GUIButton bReFly;
  public GUIButton bExit;
  public GUIButton bBack;
  public GUIButton bTrack;
  protected boolean bEnableReFly;
  protected static ZutiTimer_Refly reflyTimer = null;
  private static Paratrooper playerParatrooper = null;

  public static void setReflyTimer(ZutiTimer_Refly paramZutiTimer_Refly)
  {
    reflyTimer = paramZutiTimer_Refly;
  }

  public static void setPlayerParatrooper(Paratrooper paramParatrooper)
  {
    playerParatrooper = paramParatrooper;
  }

  private static boolean isStationaryOnCarrierDeck()
  {
    if ((World.getPlayerFM() != null) && (World.getPlayerFM().Gears.isUnderDeck()))
    {
      if ((World.getPlayerFM().brakeShoeLastCarrier != null) && ((World.getPlayerFM().brakeShoeLastCarrier instanceof BigshipGeneric)))
      {
        BigshipGeneric localBigshipGeneric = (BigshipGeneric)World.getPlayerFM().brakeShoeLastCarrier;
        Vector3d localVector3d1 = new Vector3d();
        localBigshipGeneric.getSpeed(localVector3d1);
        Vector3d localVector3d2 = new Vector3d();
        World.getPlayerFM().getSpeed(localVector3d2);
        if (Math.abs(localVector3d2.length() - localVector3d1.length()) < 0.5D)
          return true;
      }
    }
    return false;
  }

  private static boolean isOkToReFly()
  {
    Aircraft localAircraft = World.getPlayerAircraft();
    if ((World.isPlayerParatrooper()) && (!World.isPlayerDead()) && (playerParatrooper != null) && (playerParatrooper.isChuteSafelyOpened()))
    {
      return true;
    }
    if ((localAircraft != null) && (!World.isPlayerParatrooper()) && (!World.isPlayerDead()))
    {
      if ((World.getPlayerFM().brakeShoe) || (isStationaryOnCarrierDeck()))
        return true;
      if ((World.getPlayerFM().isCapableOfBMP()) && (World.getPlayerFM().isCapableOfACM()) && (World.getPlayerFM().Gears.onGround()) && (localAircraft.getSpeed(null) < 1.0D))
      {
        return true;
      }
    }
    return false;
  }

  public static boolean isEnableReFly()
  {
    if (NetAircraft.ZUTI_REFLY_OWERRIDE)
      return true;
    if (Main.cur().mission.zutiMisc_DisableReflyForMissionDuration)
      return false;
    if (Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied)
    {
      if (ZutiSupportMethods.ZUTI_KIA_COUNTER > Main.cur().mission.zutiMisc_MaxAllowedKIA)
        return false;
      if ((ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED) || (isOkToReFly()))
      {
        reflyTimer = null;
        return true;
      }

      return false;
    }

    if (Main.cur().netServerParams.isCoop())
      return false;
    if (!Actor.isValid(World.getPlayerAircraft()))
    {
      return true;
    }
    if (!World.getPlayerAircraft().isAlive())
    {
      return true;
    }
    if (!World.cur().diffCur.Takeoff_N_Landing)
    {
      return true;
    }
    if ((World.getPlayerFM().Gears.onGround()) && (World.getPlayerAircraft().getSpeed(null) < 1.0D))
    {
      return true;
    }
    if (World.isPlayerDead())
    {
      return true;
    }
    if (World.isPlayerParatrooper())
    {
      return true;
    }

    Aircraft localAircraft = World.getPlayerAircraft();

    if ((localAircraft != null) && ((localAircraft.FM.brakeShoe) || (isStationaryOnCarrierDeck()))) {
      return true;
    }

    return (localAircraft != null) && (localAircraft.FM.Gears.isAnyDamaged()) && (localAircraft.FM.Gears.onGround());
  }

  public void _leave()
  {
    this.client.hideWindow();
  }
  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 59) {
      this.client.hideWindow();
      GUI.unActivate();
    } else {
      this.client.activateWindow();
    }
  }

  protected void checkCaptured() {
    if (World.isPlayerDead()) return;
    Object localObject;
    if (World.isPlayerParatrooper()) {
      if (Actor.isAlive(Actor.getByName("_paraplayer_"))) {
        localObject = (Paratrooper)Actor.getByName("_paraplayer_");
        if (!((Paratrooper)localObject).isChecksCaptured())
          ((Paratrooper)localObject).checkCaptured();
      }
    } else if (Actor.isAlive(World.getPlayerAircraft())) {
      localObject = World.getPlayerAircraft();
      if ((!((Aircraft)localObject).FM.isOk()) && (Front.isCaptured((Actor)localObject))) {
        World.setPlayerCaptured();
        if (Config.isUSE_RENDER()) HUD.log("PlayerCAPT");
        Chat.sendLog(1, "gore_captured", (NetUser)NetEnv.host(), null);
      }
    }
  }

  protected void destroyPlayerActor() {
    if ((Actor.isValid(World.getPlayerAircraft())) && (!World.isPlayerGunner()))
    {
      if (World.getPlayerAircraft().isAlive()) {
        Aircraft localAircraft = World.getPlayerAircraft();
        Object localObject = World.getPlayerAircraft();
        FlightModel localFlightModel = World.getPlayerAircraft().FM;
        boolean bool = false;

        if (localFlightModel.isWasAirborne())
        {
          if (localFlightModel.isStationedOnGround()) {
            if (localFlightModel.isNearAirdrome()) {
              if (localFlightModel.isTakenMortalDamage())
                Chat.sendLogRnd(2, "gore_crashland", localAircraft, null);
              else
                Chat.sendLogRnd(2, "gore_lands", localAircraft, null);
            }
            else {
              localObject = localAircraft.getDamager();
              bool = true;
              if (!World.isPlayerParatrooper()) {
                if (Engine.cur.land.isWater(localFlightModel.Loc.x, localFlightModel.Loc.y))
                  Chat.sendLogRnd(2, "gore_swim", localAircraft, null);
                else {
                  Chat.sendLogRnd(2, "gore_ditch", localAircraft, null);
                }
              }
            }

          }
          else if ((localFlightModel.isTakenMortalDamage()) || (!localFlightModel.isCapableOfBMP())) {
            localObject = localAircraft.getDamager();
            bool = true;
          }

        }

        if (!Actor.isValid((Actor)localObject))
          localObject = World.getPlayerAircraft();
        World.onActorDied(World.getPlayerAircraft(), (Actor)localObject, bool);
      }
      Actor.destroy(World.getPlayerAircraft());
    }
    if (Actor.isAlive(Actor.getByName("_paraplayer_")))
      Actor.getByName("_paraplayer_").setName(null);
  }

  public void doQuitMission() {
    GUI.activate();
    this.client.activateWindow();
    this.bEnableReFly = isEnableReFly();
    if (this.bEnableReFly) this.bReFly.showWindow(); else
      this.bReFly.hideWindow();
  }

  protected void doReFly()
  {
  }

  protected void doExit()
  {
  }

  protected void clientRender()
  {
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = "";

    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bVideo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.b3d = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSound = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bControls = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNet = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bReFly = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bTrack = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUINetMission(int paramInt) {
    super(paramInt);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUINetMission.this.bVideo) {
        Main.stateStack().push(12);
        return true;
      }
      if (paramGWindow == GUINetMission.this.b3d) {
        Main.stateStack().push(11);
        return true;
      }
      if (paramGWindow == GUINetMission.this.bSound) {
        Main.stateStack().push(13);
        return true;
      }
      if (paramGWindow == GUINetMission.this.bControls) {
        Main.stateStack().push(20);
        return true;
      }
      if (paramGWindow == GUINetMission.this.bNet) {
        Main.stateStack().push(52);
        return true;
      }
      if (paramGWindow == GUINetMission.this.bReFly) {
        GUINetMission.this.doReFly();
        return true;
      }
      if (paramGWindow == GUINetMission.this.bExit) {
        GUINetMission.this.doExit();
        return true;
      }
      if (paramGWindow == GUINetMission.this.bBack) {
        GUINetMission.this.client.hideWindow();
        GUI.unActivate();
        return true;
      }
      if (paramGWindow == GUINetMission.this.bTrack) {
        if (NetMissionTrack.isRecording()) {
          NetMissionTrack.stopRecording();
          GUINetMission.this.client.hideWindow();
          GUI.unActivate();
        } else {
          Main.stateStack().push(59);
        }
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(368.0F), x1024(288.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(544.0F), x1024(288.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.VideoModes"));
      draw(x1024(96.0F), y1024(96.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.VideoOptions"));
      draw(x1024(96.0F), y1024(160.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.SoundSetup"));
      draw(x1024(96.0F), y1024(224.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.Controls"));
      draw(x1024(96.0F), y1024(288.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.Network"));

      draw(x1024(96.0F), y1024(576.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.Return2Miss"));
      if (NetMissionTrack.isRecording())
        draw(x1024(96.0F), y1024(640.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.StopRecording"));
      else
        draw(x1024(96.0F), y1024(640.0F), x1024(224.0F), y1024(48.0F), 0, GUINetMission.this.i18n("miss.StartRecording"));
      GUINetMission.this.clientRender();
    }

    public void setPosSize()
    {
      set1024PosSize(352.0F, 32.0F, 352.0F, 720.0F);

      GUINetMission.this.bVideo.setPosC(x1024(56.0F), y1024(56.0F));
      GUINetMission.this.b3d.setPosC(x1024(56.0F), y1024(120.0F));
      GUINetMission.this.bSound.setPosC(x1024(56.0F), y1024(184.0F));
      GUINetMission.this.bControls.setPosC(x1024(56.0F), y1024(248.0F));
      GUINetMission.this.bNet.setPosC(x1024(56.0F), y1024(312.0F));
      GUINetMission.this.bReFly.setPosC(x1024(56.0F), y1024(424.0F));
      GUINetMission.this.bExit.setPosC(x1024(56.0F), y1024(488.0F));
      GUINetMission.this.bBack.setPosC(x1024(56.0F), y1024(600.0F));
      GUINetMission.this.bTrack.setPosC(x1024(56.0F), y1024(666.0F));
    }
  }
}
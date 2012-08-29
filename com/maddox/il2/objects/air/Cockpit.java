package com.maddox.il2.objects.air;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon;
import com.maddox.il2.objects.vehicles.radios.BeaconGeneric;
import com.maddox.il2.objects.vehicles.radios.BlindLandingData;
import com.maddox.il2.objects.vehicles.radios.TypeHasHayRake;
import com.maddox.il2.objects.vehicles.radios.TypeHasYGBeacon;
import com.maddox.opengl.gl;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CmdMusic;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import com.maddox.sound.VGroup;
import java.io.PrintStream;
import java.util.ArrayList;

public abstract class Cockpit extends Actor
{
  public FlightModel fm = null;
  public boolean cockpitDimControl = false;
  public boolean cockpitLightControl = false;
  protected String[] cockpitNightMats = null;

  protected static float[] ypr = { 0.0F, 0.0F, 0.0F };
  protected static float[] xyz = { 0.0F, 0.0F, 0.0F };

  protected int _astatePilotIndx = -1;
  public static final int SNDCLK_NULL = 0;
  public static final int SNDCLK_BUTTONDEPRESSED1 = 1;
  public static final int SNDCLK_BUTTONDEPRESSED2 = 2;
  public static final int SNDCLK_BUTTONDEPRESSED3 = 3;
  public static final int SNDCLK_TUMBLERFLIPPED1 = 4;
  public static final int SNDCLK_TUMBLERFLIPPED2 = 5;
  public static final int SNDCLK_TUMBLERFLIPPED3 = 6;
  public static final int SNDINF_RUSTYWHEELTURNING1 = 7;
  public static final int SNDINF_OILEDMETALWHEELWITHCHAIN1 = 8;
  public static final int SNDCLK_SMALLLEVERSLIDES1 = 9;
  public static final int SNDCLK_SMALLLEVERSLIDES2 = 10;
  public static final int SNDINF_LEVERSLIDES1 = 11;
  public static final int SNDINF_LEVERSLIDES2 = 12;
  public static final int SNDCLK_RUSTYLEVERSLIDES1 = 13;
  public static final int SNDCLK_SMALLVALVE1 = 14;
  public static final int SNDINF_SMALLVALVEWITHGASLEAK1 = 15;
  public static final int SNDINF_WORMGEARTURNS1 = 16;
  public static final int SNDINF_BUZZER_109 = 17;
  public static final int SND_COUNT = 18;
  protected static SoundPreset sfxPreset = null;

  protected SoundFX[] sounds = null;
  protected Point3d sfxPos = new Point3d(1.0D, 0.0D, 0.0D);

  private float prevWaypointF = 0.0F;
  private boolean skip = false;
  private Vector3d distanceV = new Vector3d();
  private float ndBeaconRange = 1.0F;
  private float ndBeaconDirection = 0.0F;
  private BlindLandingData blData = new BlindLandingData();
  private Point3d acPoint = new Point3d();
  private Point3d tempPoint1 = new Point3d();
  private Point3d tempPoint2 = new Point3d();
  private static final String ygCode = "DWRKANUGMLFS";
  private static final int beaconAntennaHeight = 20;
  private static final int YEYGAntennaHeight = 40;
  private static float atmosphereInterference = 0.0F;
  private static Point3d lightningPoint = new Point3d();
  private static boolean lightningStriked = false;
  public static final float radioCompassUpdateF = 0.02F;
  private static float terrainAndNightError = 0.0F;
  private static final double glideScopeInRads = Math.toRadians(3.0D);
  private int morseCharsPlayed = 0;
  private boolean clearToPlay = true;
  private static final float[] volumeLogScale = { 0.0F, 0.301F, 0.477F, 0.602F, 0.698F, 0.778F, 0.845F, 0.903F, 0.954F, 1.0F };

  private boolean bNullShow = false;

  private boolean bEnableRendering = true;

  private boolean bEnableRenderingBall = true;

  private boolean bFocus = false;

  private int[] world_renderwin = new int[4];
  private int[] cockpit_renderwin = new int[4];
  public HierMesh mesh;
  private HierMesh nullMesh;
  private Mesh nullMeshIL2;
  private Draw drawMesh = new Draw();
  private NullDraw drawNullMesh = new NullDraw();
  private boolean bExistMirror = false;
  private Mat mirrorMat;
  private Loc __l = new Loc();
  private Point3d __p = new Point3d();
  private Orient __o = new Orient();

  public static Aircraft _newAircraft = null;

  private static Point3d nullP = new Point3d();

  protected static Point3d P1 = new Point3d();
  protected static Vector3d V = new Vector3d();

  protected void resetYPRmodifier()
  {
    float tmp33_32 = (ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F); ypr[1] = tmp33_32; ypr[0] = tmp33_32;
  }

  public int astatePilotIndx()
  {
    if (this._astatePilotIndx == -1)
      this._astatePilotIndx = Property.intValue(getClass(), "astatePilotIndx", 0);
    return this._astatePilotIndx;
  }

  public boolean isEnableHotKeysOnOutsideView() {
    return false; } 
  public String[] getHotKeyEnvs() { return null;
  }

  protected void initSounds()
  {
    if (sfxPreset == null) sfxPreset = new SoundPreset("aircraft.cockpit");
    this.sounds = new SoundFX[18];
  }

  protected void sfxClick(int paramInt)
  {
    sfxStart(paramInt);
  }

  protected void sfxStart(int paramInt)
  {
    if ((this.sounds != null) && (this.sounds.length > paramInt)) {
      SoundFX localSoundFX = this.sounds[paramInt];
      if (localSoundFX == null) {
        localSoundFX = aircraft().newSound(sfxPreset, false, false);
        if (localSoundFX == null) return;
        localSoundFX.setParent(aircraft().getRootFX());
        this.sounds[paramInt] = localSoundFX;
        localSoundFX.setUsrFlag(paramInt);
      }
      localSoundFX.play(this.sfxPos);
    }
  }

  protected void sfxStop(int paramInt)
  {
    if ((this.sounds != null) && (this.sounds.length > paramInt) && 
      (this.sounds[paramInt] != null)) this.sounds[paramInt].stop();
  }

  protected void sfxSetAcoustics(Acoustics paramAcoustics)
  {
    for (int i = 0; i < this.sounds.length; i++) {
      if (this.sounds[i] == null) continue; this.sounds[i].setAcoustics(paramAcoustics);
    }
  }

  protected void loadBuzzerFX()
  {
    if (this.sounds != null) {
      SoundFX localSoundFX = this.sounds[17];
      if (localSoundFX == null) {
        localSoundFX = aircraft().newSound(sfxPreset, false, false);
        if (localSoundFX != null) {
          localSoundFX.setParent(aircraft().getRootFX());
          this.sounds[17] = localSoundFX;
          localSoundFX.setUsrFlag(17);
          localSoundFX.setPosition(this.sfxPos);
        }
      }
    }
  }

  protected void buzzerFX(boolean paramBoolean)
  {
    SoundFX localSoundFX = this.sounds[17];
    if (localSoundFX != null) localSoundFX.setPlay(paramBoolean);
  }

  public void onDoorMoved(float paramFloat)
  {
    if ((this.acoustics != null) && 
      (this.acoustics.globFX != null)) this.acoustics.globFX.set(1.0F - paramFloat);
  }

  public void doToggleDim()
  {
    Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    sfxClick(9);
    for (int i = 0; i < arrayOfCockpit.length; i++) {
      Cockpit localCockpit = arrayOfCockpit[i];
      if (Actor.isValid(localCockpit))
        localCockpit.toggleDim(); 
    }
  }

  public boolean isToggleDim() {
    return this.cockpitDimControl;
  }
  public void doToggleLight() {
    Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    sfxClick(1);
    for (int i = 0; i < arrayOfCockpit.length; i++) {
      Cockpit localCockpit = arrayOfCockpit[i];
      if (Actor.isValid(localCockpit))
        localCockpit.toggleLight(); 
    }
  }

  public boolean isToggleLight() {
    return this.cockpitLightControl;
  }
  public void doReflectCockitState() {
    Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    for (int i = 0; i < arrayOfCockpit.length; i++) {
      Cockpit localCockpit = arrayOfCockpit[i];
      if (Actor.isValid(localCockpit))
        localCockpit.reflectCockpitState();
    }
  }

  protected void setNightMats(boolean paramBoolean) {
    if (this.cockpitNightMats == null) return;

    for (int i = 0; i < this.cockpitNightMats.length; i++) {
      int j = this.mesh.materialFind(this.cockpitNightMats[i] + "_night");
      if (j < 0) {
        if (!World.cur().isDebugFM()) continue; System.out.println(" * * * * * did not find " + this.cockpitNightMats[i] + "_night");
      }
      else {
        Mat localMat = this.mesh.material(j);
        if (localMat.isValidLayer(0)) {
          localMat.setLayer(0);
          localMat.set(0, paramBoolean);
        } else {
          if (!World.cur().isDebugFM()) continue; System.out.println(" * * * * * " + this.cockpitNightMats[i] + "_night layer 0 invalid");
        }
      }
    }
  }

  public void reflectWorldToInstruments(float paramFloat) {
  }

  public void toggleDim() {
  }

  public void toggleLight() {
  }

  public void reflectCockpitState() {
  }

  public boolean isNullShow() {
    return this.bNullShow;
  }
  public void setNullShow(boolean paramBoolean) { Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    for (int i = 0; i < arrayOfCockpit.length; i++)
      arrayOfCockpit[i]._setNullShow(paramBoolean);
    if (this.bFocus) {
      Aircraft localAircraft = aircraft();
      if (Actor.isValid(localAircraft))
        localAircraft.drawing(!paramBoolean);
    } }

  protected void _setNullShow(boolean paramBoolean) {
    this.bNullShow = paramBoolean;
  }

  public boolean isEnableRendering() {
    return this.bEnableRendering;
  }
  public void setEnableRendering(boolean paramBoolean) { Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    for (int i = 0; i < arrayOfCockpit.length; i++)
      arrayOfCockpit[i]._setEnableRendering(paramBoolean); }

  protected void _setEnableRendering(boolean paramBoolean) {
    this.bEnableRendering = paramBoolean;
  }

  public boolean isEnableRenderingBall() {
    return this.bEnableRenderingBall;
  }
  public void setEnableRenderingBall(boolean paramBoolean) { Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    for (int i = 0; i < arrayOfCockpit.length; i++)
      arrayOfCockpit[i]._setEnableRenderingBall(paramBoolean); }

  protected void _setEnableRenderingBall(boolean paramBoolean) {
    this.bEnableRenderingBall = paramBoolean;
  }

  public boolean isFocused()
  {
    return this.bFocus;
  }

  public boolean isEnableFocusing() {
    if (!Actor.isValid(aircraft())) return false;
    return !aircraft().FM.AS.isPilotParatrooper(astatePilotIndx());
  }

  public boolean focusEnter()
  {
    if (!isEnableFocusing()) return false;
    if (this.bFocus) return true;
    if (!doFocusEnter()) return false;
    this.bFocus = true;
    Main3D.cur3D().enableCockpitHotKeys();
    return true;
  }

  public void focusLeave() {
    if (!this.bFocus) return;
    doFocusLeave();
    this.bFocus = false;
    aircraft().stopMorseSounds();
    if (!isEnableHotKeysOnOutsideView())
      Main3D.cur3D().disableCockpitHotKeys();
  }

  protected boolean doFocusEnter() {
    return true;
  }
  protected void doFocusLeave() {
  }

  public boolean existPadlock() {
    return false;
  }

  public boolean isPadlock() {
    return this.bFocus;
  }

  public Actor getPadlockEnemy() {
    return null;
  }
  public boolean startPadlock(Actor paramActor) {
    return this.bFocus;
  }
  public void stopPadlock() {
  }
  public void endPadlock() {
  }
  public void setPadlockForward(boolean paramBoolean) {
  }
  public boolean isToggleAim() {
    return this.bFocus;
  }

  public void doToggleAim(boolean paramBoolean) {
  }

  public boolean isToggleUp() {
    return this.bFocus;
  }

  public void doToggleUp(boolean paramBoolean)
  {
  }

  public String nameOfActiveMirrorSurfaceChunk()
  {
    return "Mirror";
  }

  public String nameOfActiveMirrorBaseChunk() {
    return "BaseMirror";
  }

  public static Hook getHookCamera3DMirror(boolean paramBoolean)
  {
    return new HookCamera3DMirror(paramBoolean);
  }

  public void grabMirrorFromScreen(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mirrorMat.grabFromScreen(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void preRender(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.bNullShow)) {
      Aircraft localAircraft = World.getPlayerAircraft();
      if (Actor.isValid(localAircraft)) {
        localAircraft.pos.getRender(this.__l);
        localAircraft.draw.soundUpdate(localAircraft, this.__l);
        ((Aircraft)localAircraft).updateLLights();
      }
    }
    if (!this.bEnableRendering) return;
    if (this.bNullShow) this.drawNullMesh.preRender(); else
      this.drawMesh.preRender(); 
  }

  public void render(boolean paramBoolean) {
    if (!this.bEnableRendering) return;
    if (this.bNullShow) this.drawNullMesh.render(); else
      this.drawMesh.render(paramBoolean);
  }

  public Aircraft aircraft()
  {
    if (this.fm == null) return null;
    return (Aircraft)this.fm.actor;
  }

  public boolean isExistMirror() {
    return this.bExistMirror;
  }

  public void destroy() {
    if (isFocused())
      Main3D.cur3D().setView(this.fm.actor, true);
    this.fm = null;
    super.destroy();
  }

  public Cockpit(String paramString1, String paramString2)
  {
    this.fm = _newAircraft.FM;
    this.pos = new ActorPosMove(this);

    if (paramString1 != null) {
      this.mesh = new HierMesh(paramString1);
      int i = this.mesh.materialFind("MIRROR");
      if (i != -1) {
        this.bExistMirror = true;
        this.mirrorMat = this.mesh.material(i);
      }
    }

    this.nullMesh = new HierMesh("3DO/Cockpit/Nill/hier.him");
    this.nullMeshIL2 = new Mesh("3DO/Cockpit/null/mono.sim");
    try
    {
      this.acoustics = new Acoustics(paramString2);
      this.acoustics.setParent(Engine.worldAcoustics());
      initSounds();
    } catch (Exception localException) {
      System.out.println("Cockpit Acoustics NOT initialized: " + localException.getMessage());
    }
    if ((this instanceof CockpitPilot))
      AircraftLH.printCompassHeading = false;
  }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  protected float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }

  protected float interp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat2 + (paramFloat1 - paramFloat2) * paramFloat3;
  }

  protected float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0) return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F) return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public boolean useRealisticNavigationInstruments()
  {
    return World.cur().diffCur.RealisticNavigationInstruments;
  }

  public static void lightningStrike(Point3d paramPoint3d)
  {
    lightningPoint = paramPoint3d;
    lightningStriked = true;
  }

  private void updateBeacons()
  {
    if (lightningStriked)
    {
      lightningStriked = false;
      V.sub(this.fm.Loc, lightningPoint);
      float f = (float)V.length();

      atmosphereInterference = cvt(f, 1000.0F, 9000.0F, 1.0F, 0.0F);
    }
    else if (atmosphereInterference > 0.01F) {
      atmosphereInterference = (float)(atmosphereInterference * 0.98D);
    }
    if (!this.bFocus)
      return;
    Aircraft localAircraft = aircraft();
    if (localAircraft.FM.AS.listenLorenzBlindLanding)
    {
      listenLorenzBlindLanding(localAircraft);
    }
    else if (localAircraft.FM.AS.listenNDBeacon)
    {
      listenNDBeacon(localAircraft, false);
    }
    else if (localAircraft.FM.AS.listenRadioStation)
    {
      listenNDBeacon(localAircraft, true);
    }
    else if (localAircraft.FM.AS.listenYGBeacon)
    {
      this.ndBeaconRange = 1.0F;
      this.ndBeaconDirection = 0.0F;
      Actor localActor = (Actor)Main.cur().mission.getBeacons(localAircraft.getArmy()).get(localAircraft.FM.AS.getBeacon() - 1);
      if (!localActor.isAlive())
        return;
      playYEYGMorseCode(localAircraft, localActor, "DWRKANUGMLFS".toCharArray());
    }
    else if ((localAircraft.FM.AS.hayrakeCarrier != null) && (localAircraft.FM.AS.hayrakeCarrier.isAlive()))
    {
      this.ndBeaconRange = 1.0F;
      this.ndBeaconDirection = 0.0F;
      playYEYGMorseCode(localAircraft, localAircraft.FM.AS.hayrakeCarrier, localAircraft.FM.AS.hayrakeCode.toCharArray());
    }
    else
    {
      this.ndBeaconRange = 1.0F;
      this.ndBeaconDirection = 0.0F;
    }
  }

  private void playYEYGMorseCode(Aircraft paramAircraft, Actor paramActor, char[] paramArrayOfChar)
  {
    float f1 = cvt((float)Time.current() % 30000.0F, 0.0F, 30000.0F, 0.0F, 360.0F);

    int i = 0;
    if ((float)Time.current() % 300000.0F <= 30000.0F) {
      i = 1;
    }
    paramActor.pos.getAbs(this.tempPoint1);
    paramAircraft.pos.getAbs(this.tempPoint2);
    this.tempPoint2.sub(this.tempPoint1);
    float f2 = 57.324841F * (float)Math.atan2(this.tempPoint2.x, this.tempPoint2.y);
    f2 -= 180.0F;
    for (f2 = (f2 + 180.0F) % 360.0F; f2 < 0.0F; f2 += 360.0F);
    while (f2 >= 360.0F) f2 -= 360.0F;

    float f3 = Math.abs(f1 - f2);
    Point3d localPoint3d = new Point3d();
    localPoint3d.x = paramActor.pos.getAbsPoint().x;
    localPoint3d.y = paramActor.pos.getAbsPoint().y;
    localPoint3d.z = (paramActor.pos.getAbsPoint().z + 40.0D);

    float f4 = 15.0F;

    if (f3 > f4)
    {
      BeaconGeneric.getSignalAttenuation(localPoint3d, paramAircraft, false, false, true, true);
      paramAircraft.playYEYGCarrier(false, 0.0F);
      this.clearToPlay = true;
      return;
    }

    float f5 = BeaconGeneric.getSignalAttenuation(localPoint3d, paramAircraft, false, false, true, false);

    if (f5 == 1.0F)
    {
      return;
    }

    float f6 = (1.0F - f5) * ((f4 - f3) / f4);
    paramAircraft.playYEYGCarrier(true, f6 * 0.5F);

    int j = (int)f1;
    if (j % 15 == 0) {
      this.clearToPlay = true;
    }
    if (j % 13 != 0) {
      return;
    }
    if ((paramAircraft.isMorseSequencePlaying()) && (!this.clearToPlay))
    {
      return;
    }

    if (i != 0)
    {
      String str1 = Beacon.getBeaconID(paramAircraft.FM.AS.getBeacon() - 1);
      String str2 = "";
      if (this.morseCharsPlayed % 2 == 0)
        str2 = "" + str1.charAt(0);
      else {
        str2 = "" + str1.charAt(1);
      }
      paramAircraft.morseSequenceStart(str2, f6);
      this.clearToPlay = false;
      this.morseCharsPlayed += 1;
    }
    else
    {
      this.morseCharsPlayed = 0;
      float f7 = 0.0F;
      if (paramArrayOfChar.length == 12)
        f7 = 0.03333334F * f1;
      else if (paramArrayOfChar.length == 24)
        f7 = 0.0666667F * f1;
      if (f7 >= paramArrayOfChar.length)
        f7 = 0.0F;
      char c = paramArrayOfChar[(int)f7];
      String str3 = "" + c;
      paramAircraft.morseSequenceStart(str3, f6);
      this.clearToPlay = false;
    }
  }

  private void listenLorenzBlindLanding(Aircraft paramAircraft)
  {
    this.blData.reset();
    Beacon.LorenzBLBeacon localLorenzBLBeacon = (Beacon.LorenzBLBeacon)getBeacon();
    if ((localLorenzBLBeacon == null) || (!localLorenzBLBeacon.isAlive()))
    {
      paramAircraft.stopMorseSounds();
      return;
    }
    localLorenzBLBeacon.rideBeam(paramAircraft, this.blData);

    float f1 = this.blData.blindLandingAzimuthBP;
    float f2 = (float)Math.random() * (0.5F - this.blData.signalStrength);
    float f3 = cvt(this.blData.signalStrength * 2.0F, 0.0F, 0.75F, 0.0F, 1.2F) - f2;

    float f4 = 12.0F;
    float f5 = 0.3F;
    float f6 = 0.0F;
    float f7 = 0.0F;
    if ((f1 < f5) && (f1 > -f5))
    {
      paramAircraft.playLorenzDash(false, 0.0F);
      paramAircraft.playLorenzDot(false, 0.0F);
      paramAircraft.playLorenzSolid(true, f3);
      return;
    }
    if (f1 > f4)
    {
      f6 = 1.0F;
      f7 = 0.0F;
    }
    else if (f1 < -f4)
    {
      f7 = 1.0F;
      f6 = 0.0F;
    }
    else
    {
      f6 = cvt(f1, -f4 / 2.0F, f5 * 10.0F, 0.0F, 1.0F);
      f7 = cvt(f1, -f5 * 10.0F, f4 / 2.0F, 1.0F, 0.0F);
    }

    paramAircraft.playLorenzSolid(true, 0.0F);
    paramAircraft.playLorenzDash(true, f7 * f3);
    paramAircraft.playLorenzDot(true, f6 * f3);
  }

  public boolean isOnBlindLandingMarker()
  {
    Aircraft localAircraft = aircraft();
    if (this.blData.isOnInnerMarker)
    {
      localAircraft.playLorenzInnerMarker(true, 1.0F);
      return true;
    }
    if (this.blData.isOnOuterMarker)
    {
      localAircraft.playLorenzOuterMarker(true, 1.0F);
      return true;
    }

    localAircraft.playLorenzInnerMarker(false, 0.0F);
    localAircraft.playLorenzOuterMarker(false, 0.0F);
    return false;
  }

  public float getBeaconRange()
  {
    Aircraft localAircraft = aircraft();
    if (localAircraft.FM.AS.listenLorenzBlindLanding)
    {
      return 7.0422F * (1.0F - this.blData.signalStrength + World.Rnd().nextFloat(-atmosphereInterference * 2.0F, atmosphereInterference * 2.0F));
    }
    if (useRealisticNavigationInstruments())
    {
      return this.ndBeaconRange + (float)Math.random() * 0.2F * this.ndBeaconRange + World.Rnd().nextFloat(-atmosphereInterference * 2.0F, atmosphereInterference * 2.0F);
    }
    return 1.0F;
  }

  public float getGlidePath()
  {
    Aircraft localAircraft = aircraft();
    if (localAircraft.FM.AS.listenLorenzBlindLanding)
    {
      int i = this.fm.AS.getBeacon();
      ArrayList localArrayList = Main.cur().mission.getBeacons(this.fm.actor.getArmy());
      Actor localActor = (Actor)localArrayList.get(i - 1);
      double d1 = localActor.pos.getAbsPoint().z + 10.0D;
      float f1 = (1.0F - this.blData.signalStrength) * 100.0F;
      double d2 = Math.abs(localAircraft.pos.getAbsPoint().x - localActor.pos.getAbsPoint().x);
      double d3 = Math.abs(localAircraft.pos.getAbsPoint().y - localActor.pos.getAbsPoint().y);
      float f2 = 1700.0F + World.Rnd().nextFloat(-f1, f1);
      double d4 = Math.sqrt(d2 * d2 + d3 * d3) - f2;
      double d5 = localAircraft.pos.getAbsPoint().z - d1;
      double d6 = Math.asin(d5 / d4);
      double d7 = glideScopeInRads - d6;
      float f3 = (float)Math.toDegrees(d7) * this.blData.signalStrength + World.Rnd().nextFloat(-atmosphereInterference * 2.0F, atmosphereInterference * 2.0F);
      if (f3 > 1.0F)
        f3 = 1.0F;
      else if (f3 < -1.0F)
        f3 = -1.0F;
      return f3;
    }
    return 0.0F;
  }

  public float getBeaconDirection()
  {
    Aircraft localAircraft = aircraft();
    if ((this.bFocus) && (localAircraft.FM.AS.listenLorenzBlindLanding))
    {
      return this.blData.blindLandingAzimuthPB + World.Rnd().nextFloat(-atmosphereInterference * 90.0F, atmosphereInterference * 90.0F);
    }
    if ((useRealisticNavigationInstruments()) && (Main.cur().mission.hasBeacons(this.fm.actor.getArmy())))
    {
      return this.ndBeaconDirection + World.Rnd().nextFloat(-atmosphereInterference * 90.0F, atmosphereInterference * 90.0F);
    }
    return 0.0F;
  }

  private Actor getBeacon()
  {
    int i = this.fm.AS.getBeacon();
    if (i == 0)
      return null;
    ArrayList localArrayList1 = Main.cur().mission.getBeacons(this.fm.actor.getArmy());
    Object localObject = (Actor)localArrayList1.get(i - 1);
    if (((localObject instanceof TypeHasYGBeacon)) || ((localObject instanceof TypeHasHayRake)))
      return null;
    ArrayList localArrayList2 = Main.cur().mission.getMeacons(this.fm.actor.getArmy());
    if ((localArrayList2.size() >= i) && (!(localObject instanceof Beacon.LorenzBLBeacon)))
    {
      Actor localActor = (Actor)localArrayList2.get(i - 1);
      if (localActor.isAlive())
      {
        this.distanceV.sub(localActor.pos.getAbsPoint(), this.fm.Loc);
        double d1 = this.distanceV.length();
        this.distanceV.sub(((Actor)localObject).pos.getAbsPoint(), this.fm.Loc);
        double d2 = this.distanceV.length();
        if ((d1 < d2) || (!((Actor)localObject).isAlive()))
          localObject = localActor;
      }
    }
    return (Actor)localObject;
  }

  private void listenNDBeacon(Aircraft paramAircraft, boolean paramBoolean)
  {
    Actor localActor = getBeacon();

    if ((localActor == null) || (!localActor.isAlive()))
    {
      this.ndBeaconRange = 1.0F;
      this.ndBeaconDirection = 0.0F;
      if (paramBoolean)
        CmdMusic.setCurrentVolume(0.001F);
      else
        paramAircraft.playBeaconCarrier(false, 0.0F);
      return;
    }

    this.tempPoint1.x = localActor.pos.getAbsPoint().x;
    this.tempPoint1.y = localActor.pos.getAbsPoint().y;
    this.tempPoint1.z = (localActor.pos.getAbsPoint().z + 20.0D);

    paramAircraft.pos.getAbs(this.acPoint);
    this.acPoint.sub(this.tempPoint1);
    float f1 = paramAircraft.pos.getAbsOrient().getYaw() + 180.0F;
    float f2 = 57.324841F * (float)Math.atan2(this.acPoint.y, this.acPoint.x) - f1;

    for (f2 = (f2 + 180.0F) % 360.0F; f2 < 0.0F; f2 += 360.0F);
    while (f2 >= 360.0F) f2 -= 360.0F;

    if (f2 > 270.0F)
      f2 -= 360.0F;
    if (f2 > 90.0F) {
      f2 = -(f2 - 180.0F);
    }

    this.ndBeaconRange = BeaconGeneric.getSignalAttenuation(this.tempPoint1, paramAircraft, !paramBoolean, paramBoolean, false, false);
    if (Math.random() < 0.02D)
      terrainAndNightError = BeaconGeneric.getTerrainAndNightError(paramAircraft);
    f2 += terrainAndNightError;

    float f3 = floatindex(cvt(1.0F - this.ndBeaconRange, 0.0F, 1.0F, 0.0F, 9.0F), volumeLogScale);

    float f4 = AudioDevice.vMusic.get();
    float f5 = (f4 + 1.0F) / 15.0F;
    if (!paramBoolean)
    {
      float f6 = (float)Math.random() * this.ndBeaconRange;
      float f7 = (float)Time.current() % 60000.0F;
      if ((f7 <= 500.0F) && (!paramAircraft.isMorseSequencePlaying()))
      {
        String str = Beacon.getBeaconID(paramAircraft.FM.AS.getBeacon() - 1);
        f5 = f3 * f5 * 0.75F;
        paramAircraft.morseSequenceStart(str, f5);
      }
      else
      {
        f5 = f3 * f5 * 0.75F - f6;
        paramAircraft.playBeaconCarrier(true, f5);
      }
    }
    else
    {
      CmdMusic.setCurrentVolume(f3);
      paramAircraft.playRadioStatic(true, (-0.5F + (1.0F - f3 * f5)) * 2.0F);
    }
    this.ndBeaconDirection = (f3 * f2 + ((float)Math.random() - 0.5F) * this.ndBeaconRange);
  }

  private float getRadioCompassWaypoint(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    Actor localActor = getBeacon();

    if ((localActor == null) || (!localActor.isAlive()))
    {
      if (paramBoolean3)
      {
        this.prevWaypointF = aircraft().FM.Or.azimut();
        return this.prevWaypointF;
      }

      this.prevWaypointF = 0.0F;
      return this.prevWaypointF;
    }

    Aircraft localAircraft = aircraft();

    this.tempPoint1.x = localActor.pos.getAbsPoint().x;
    this.tempPoint1.y = localActor.pos.getAbsPoint().y;
    this.tempPoint1.z = (localActor.pos.getAbsPoint().z + 20.0D);
    V.sub(this.tempPoint1, this.fm.Loc);

    if (paramBoolean1)
    {
      if (paramBoolean2)
        for (f = (float)(57.295779513082323D * Math.atan2(-V.y, V.x)); f <= -180.0F; f += 180.0F);
      for (f = (float)(57.295779513082323D * Math.atan2(V.y, V.x)); f <= -180.0F; f += 180.0F);
    }
    for (float f = (float)(57.295779513082323D * Math.atan2(V.x, V.y)); f <= -180.0F; f += 180.0F);
    while (f > 180.0F) f -= 180.0F;

    f += terrainAndNightError;
    if (this.ndBeaconRange > 0.99D)
      f = aircraft().FM.Or.azimut();
    else
      f += World.Rnd().nextFloat(-this.ndBeaconRange - atmosphereInterference * 5.0F, this.ndBeaconRange + atmosphereInterference * 5.0F) * 30.0F;
    return f;
  }

  private float getWaypoint(boolean paramBoolean1, boolean paramBoolean2, float paramFloat, boolean paramBoolean3)
  {
    if (useRealisticNavigationInstruments())
    {
      if (paramBoolean3)
      {
        if (Main.cur().mission.hasBeacons(this.fm.actor.getArmy()))
        {
          this.skip = (!this.skip);
          if (this.skip)
          {
            return this.prevWaypointF;
          }

          float f1 = getRadioCompassWaypoint(paramBoolean1, paramBoolean2, paramBoolean3);
          this.prevWaypointF = f1;
          return f1;
        }
        return aircraft().FM.Or.azimut();
      }

      return aircraft().headingBug;
    }

    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(P1);
    V.sub(P1, this.fm.Loc);

    if (paramBoolean1)
    {
      if (paramBoolean2)
        for (f2 = (float)(57.295779513082323D * Math.atan2(-V.y, V.x)); f2 <= -180.0F; f2 += 180.0F);
      for (f2 = (float)(57.295779513082323D * Math.atan2(V.y, V.x)); f2 <= -180.0F; f2 += 180.0F);
    }
    for (float f2 = (float)(57.295779513082323D * Math.atan2(V.x, V.y)); f2 <= -180.0F; f2 += 180.0F);
    while (f2 > 180.0F) f2 -= 180.0F;

    return f2 + World.Rnd().nextFloat(-paramFloat, paramFloat);
  }

  protected float waypointAzimuth()
  {
    return getWaypoint(false, false, 0.0F, false);
  }

  protected float waypointAzimuth(float paramFloat)
  {
    return getWaypoint(false, false, paramFloat, false);
  }

  protected float waypointAzimuthInvert(float paramFloat)
  {
    return getWaypoint(true, false, paramFloat, false);
  }

  protected float waypointAzimuthInvertMinus(float paramFloat)
  {
    return getWaypoint(true, true, paramFloat, false);
  }

  protected float radioCompassAzimuthInvertMinus() {
    return getWaypoint(true, true, 0.0F, true);
  }

  public static class HookOnlyOrient extends Hook
  {
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
    {
      paramLoc2.set(Cockpit.nullP, paramLoc1.getOrient());
    }
  }

  class NullDraw extends ActorDraw
  {
    private int iPreRender = 0;

    NullDraw() {  } 
    public void preRender() { if (!Actor.isValid(Cockpit.this.pos.base())) return;
      if (Cockpit.this.nullMesh == null) return;
      if (Cockpit.this.nullMeshIL2 == null) return;
      Cockpit.this.pos.getRender(Cockpit.this.__l);
      Main3D.cur3D().cameraCockpit.pos.getRender(Cockpit.this.__p);
      Cockpit.this.__l.set(Cockpit.this.__p);
      if (Cockpit.this.bEnableRenderingBall)
        Cockpit.this.nullMesh.setPos(Cockpit.this.__l);
      else {
        Cockpit.this.nullMeshIL2.setPos(Cockpit.this.__l);
      }

      if (Cockpit.this.aircraft() != null) {
        Cockpit.this.nullMesh.chunkSetAngles("Ball", Cockpit.this.aircraft().FM.getAOS(), -Cockpit.this.aircraft().FM.getAOA(), 0.0F);
      }

      if (Cockpit.this.bEnableRenderingBall)
        this.iPreRender = Cockpit.this.nullMesh.preRender();
      else
        this.iPreRender = Cockpit.this.nullMeshIL2.preRender(); }

    public void render()
    {
      if (this.iPreRender == 0) return;
      if (!Actor.isValid(Cockpit.this.pos.base())) return;
      if (Cockpit.this.nullMesh == null) return;
      if (Cockpit.this.nullMeshIL2 == null) return;
      if (Cockpit.this.bEnableRenderingBall)
        Cockpit.this.nullMesh.render();
      else
        Cockpit.this.nullMeshIL2.render();
    }
  }

  class Draw extends ActorDraw
  {
    protected double[] _modelMatrix3D = new double[16];
    protected double[] _modelMatrix3DMirror = new double[16];
    protected double[] _projMatrix3DMirror = new double[16];
    private int iPreRender = 0;

    Draw() {  } 
    public void preRender() { if (!Actor.isValid(Cockpit.this.pos.base())) return;
      if (Cockpit.this.mesh == null) return;
      Cockpit.this.pos.getRender(Cockpit.this.__l);
      Cockpit.this.mesh.setPos(Cockpit.this.__l);
      Cockpit.this.updateBeacons();
      Cockpit.this.reflectWorldToInstruments(Time.tickOffset());
      this.iPreRender = Cockpit.this.mesh.preRender(); }

    public void render(boolean paramBoolean)
    {
      if (this.iPreRender == 0) return;
      if (!Actor.isValid(Cockpit.this.pos.base())) return;
      if (Cockpit.this.mesh == null) return;
      Render.currentCamera().activateWorldMode(0);
      gl.GetDoublev(2982, paramBoolean ? this._modelMatrix3DMirror : this._modelMatrix3D);
      if (paramBoolean)
        gl.GetDoublev(2983, this._projMatrix3DMirror);
      Render.currentCamera().deactivateWorldMode();
      Cockpit.this.pos.getRender(Cockpit.this.__l);
      lightUpdate(Cockpit.this.__l, false);
      Cockpit.this.pos.base().pos.getRender(Cockpit.this.__p, Cockpit.this.__o);
      LightPoint.setOffset((float)Cockpit.this.__p.x, (float)Cockpit.this.__p.y, (float)Cockpit.this.__p.z);
      Cockpit.this.pos.base().pos.getRender(Cockpit.this.__l);
      Cockpit.this.pos.base().draw.lightUpdate(Cockpit.this.__l, false);
      Render.currentLightEnv().prepareForRender(Cockpit.this.__p, Cockpit.this.mesh.visibilityR());

      if (paramBoolean) {
        if (!Cockpit.this.bExistMirror) {
          System.out.println("*** Internal error: mirr exist");
        }
        if (!Main3D.cur3D().isViewMirror()) {
          System.out.println("*** Internal error: mirr isview");
        }

        String str1 = Cockpit.this.nameOfActiveMirrorSurfaceChunk();
        str2 = Cockpit.this.nameOfActiveMirrorBaseChunk();

        if (str1 != null) {
          Cockpit.this.mesh.setCurChunk(str1);
          Cockpit.this.mesh.chunkVisible(false);
        }
        if (str2 != null) {
          Cockpit.this.mesh.setCurChunk(str2);
          Cockpit.this.mesh.chunkVisible(true);
        }

        Cockpit.this.mesh.render();
        return;
      }

      if (!Cockpit.this.bExistMirror) {
        Cockpit.this.mesh.render();
        return;
      }

      boolean bool = Main3D.cur3D().isViewMirror();

      String str2 = Cockpit.this.nameOfActiveMirrorSurfaceChunk();
      String str3 = Cockpit.this.nameOfActiveMirrorBaseChunk();

      if (bool) {
        if (str2 != null) {
          Cockpit.this.mesh.setCurChunk(str2);
          Cockpit.this.mesh.chunkVisible(true);
          Cockpit.this.mesh.renderChunkMirror(this._modelMatrix3D, this._modelMatrix3DMirror, this._projMatrix3DMirror);
          Cockpit.this.mesh.chunkVisible(false);
        }
        if (str3 != null) {
          Cockpit.this.mesh.setCurChunk(str3);
          Cockpit.this.mesh.chunkVisible(true);
        }
        Cockpit.this.mesh.render();
      } else {
        if (str2 != null) {
          Cockpit.this.mesh.setCurChunk(str2);
          Cockpit.this.mesh.chunkVisible(false);
        }
        if (str3 != null) {
          Cockpit.this.mesh.setCurChunk(str3);
          Cockpit.this.mesh.chunkVisible(false);
        }
        Cockpit.this.mesh.render();
      }
    }
  }

  static class HookCamera3DMirror extends HookRender
  {
    Matrix4d cam2w = new Matrix4d();
    Matrix4d mir2w = new Matrix4d();
    Matrix4d cam2mir = new Matrix4d();
    Point3d p = new Point3d();
    Vector3d X = new Vector3d();
    Vector3d Y = new Vector3d();
    Vector3d Z = new Vector3d();
    Matrix4d cmm2w = new Matrix4d();
    Matrix4d cmm2mir = new Matrix4d();
    Matrix4d mir2cmm = new Matrix4d();
    double[] Eul = new double[3];

    Point3f mirLowP = new Point3f();
    Point3f mirHigP = new Point3f();
    float resultNearClipDepth;
    Loc mir2w_loc = new Loc();
    Loc aLoc = new Loc();
    boolean bInCockpit;

    private float computeMirroredCamera(Loc paramLoc1, Loc paramLoc2, Point3f paramPoint3f1, Point3f paramPoint3f2, int paramInt1, int paramInt2, float paramFloat, Loc paramLoc3, int[] paramArrayOfInt)
    {
      paramLoc1.getMatrix(this.cam2w);
      paramLoc2.getMatrix(this.mir2w);

      this.cam2mir.set(this.mir2w);
      this.cam2mir.invert();
      this.cam2mir.mul(this.cam2mir, this.cam2w);

      this.cmm2mir.setIdentity();

      this.cmm2mir.m00 = 0.0D;
      this.cmm2mir.m10 = 0.0D;
      this.cmm2mir.m20 = 1.0D;

      this.cmm2mir.m01 = 1.0D;
      this.cmm2mir.m11 = 0.0D;
      this.cmm2mir.m21 = 0.0D;

      this.cmm2mir.m02 = 0.0D;
      this.cmm2mir.m12 = 1.0D;
      this.cmm2mir.m22 = 0.0D;

      this.cmm2mir.m03 = this.cam2mir.m03;
      this.cmm2mir.m13 = this.cam2mir.m13;
      this.cmm2mir.m23 = (-this.cam2mir.m23);

      this.cmm2mir.m03 *= 0.45D;
      this.cmm2mir.m13 *= 0.45D;
      this.cmm2mir.m23 *= 0.45D;

      this.cmm2w.mul(this.mir2w, this.cmm2mir);

      this.mir2cmm.set(this.cmm2mir);
      this.mir2cmm.invert();

      float f1 = (float)(-this.cmm2mir.m23);

      if (f1 <= 0.001F)
        return -1.0F;
      float f5;
      float f4;
      float f3;
      float f2 = f3 = f4 = f5 = 0.0F;

      for (int i = 0; i < 8; i++) {
        switch (i) { case 0:
          this.p.set(paramPoint3f1.x, paramPoint3f1.y, paramPoint3f1.z); break;
        case 1:
          this.p.set(paramPoint3f2.x, paramPoint3f1.y, paramPoint3f1.z); break;
        case 2:
          this.p.set(paramPoint3f1.x, paramPoint3f2.y, paramPoint3f1.z); break;
        case 3:
          this.p.set(paramPoint3f1.x, paramPoint3f1.y, paramPoint3f2.z); break;
        case 4:
          this.p.set(paramPoint3f2.x, paramPoint3f2.y, paramPoint3f2.z); break;
        case 5:
          this.p.set(paramPoint3f1.x, paramPoint3f2.y, paramPoint3f2.z); break;
        case 6:
          this.p.set(paramPoint3f2.x, paramPoint3f1.y, paramPoint3f2.z); break;
        case 7:
          this.p.set(paramPoint3f2.x, paramPoint3f2.y, paramPoint3f1.z);
        }

        this.mir2cmm.transform(this.p);

        f7 = -(float)(f1 * this.p.y / this.p.x);
        f8 = (float)(f1 * this.p.z / this.p.x);

        if (i == 0) {
          f2 = f3 = f7;
          f4 = f5 = f8;
        } else {
          if (f7 < f2) {
            f2 = f7;
          }
          if (f7 > f3) {
            f3 = f7;
          }
          if (f8 < f4) {
            f4 = f8;
          }
          if (f8 > f5) {
            f5 = f8;
          }
        }

      }

      float f6 = f3 - f2;
      float f7 = f5 - f4;

      if ((f6 <= 0.001F) || (f7 <= 0.001F)) {
        return -1.0F;
      }

      f2 -= 0.001F;
      f3 += 0.001F;
      f4 -= 0.001F;
      f5 += 0.001F;

      f6 = f3 - f2;
      f7 = f5 - f4;

      float f8 = f6 / paramInt1;
      float f9 = f8 * paramFloat;
      float f10 = f9 * paramInt2;
      if (f10 > f7) {
        f7 = f10;
        f4 = (f4 + f5) * 0.5F - f7 * 0.5F;
        f5 = f4 + f7;
      } else {
        f6 *= f7 / f10;
        f2 = (f2 + f3) * 0.5F - f6 * 0.5F;
        f3 = f2 + f6;
      }

      float f11 = 2.0F * Math.max(Math.abs(f2), Math.abs(f3));
      float f12 = 2.0F * Math.max(Math.abs(f4), Math.abs(f5));

      int j = (int)(0.5F + paramInt1 * f11 / f6);
      int k = (int)(0.5F + paramInt2 * f12 / f7);

      float f13 = 2.0F * Geom.RAD2DEG((float)Math.atan2(f11 * 0.5F, f1));

      int m = (int)(-((f2 - -f11 / 2.0F) / f11) * j);
      int n = (int)(-((f4 - -f12 / 2.0F) / f12) * k);

      this.cmm2w.getEulers(this.Eul);
      this.Eul[0] *= -57.295777918682049D;
      this.Eul[1] *= -57.295777918682049D;
      this.Eul[2] *= 57.295777918682049D;
      paramLoc3.set(this.cmm2w.m03, this.cmm2w.m13, this.cmm2w.m23, (float)this.Eul[0], (float)this.Eul[1], (float)this.Eul[2]);

      paramArrayOfInt[0] = m;
      paramArrayOfInt[1] = n;
      paramArrayOfInt[2] = j;
      paramArrayOfInt[3] = k;

      this.resultNearClipDepth = f1;

      return f13;
    }
    public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
      computePos(paramActor, paramLoc1, paramLoc2, true);
      return true;
    }
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
      computePos(paramActor, paramLoc1, paramLoc2, false);
    }
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2, boolean paramBoolean) {
      if (!Actor.isValid(Main3D.cur3D().cockpitCur)) return;
      if (!Main3D.cur3D().cockpitCur.bExistMirror) return;
      if (!Main3D.cur3D().isViewMirror()) return;
      if (!Main3D.cur3D().cockpitCur.isFocused()) return;
      if (!Actor.isValid(World.getPlayerAircraft())) return;
      Loc localLoc = paramLoc1;
      Main3D.cur3D().cockpitCur.mesh.setCurChunk(Main3D.cur3D().cockpitCur.nameOfActiveMirrorSurfaceChunk());

      Main3D.cur3D().cockpitCur.mesh.getChunkLocObj(this.mir2w_loc);
      if (paramBoolean) {
        if (this.bInCockpit)
          Main3D.cur3D().cockpitCur.pos.getRender(this.aLoc);
        else
          World.getPlayerAircraft().pos.getRender(this.aLoc);
      }
      else if (this.bInCockpit)
        Main3D.cur3D().cockpitCur.pos.getAbs(this.aLoc);
      else {
        World.getPlayerAircraft().pos.getAbs(this.aLoc);
      }
      this.mir2w_loc.add(this.mir2w_loc, this.aLoc);

      Main3D.cur3D().cockpitCur.mesh.getChunkCurVisBoundBox(this.mirLowP, this.mirHigP);

      float f = computeMirroredCamera(localLoc, this.mir2w_loc, this.mirLowP, this.mirHigP, Main3D.cur3D().mirrorWidth(), Main3D.cur3D().mirrorHeight(), 1.0F, paramLoc2, this.bInCockpit ? Main3D.cur3D().cockpitCur.cockpit_renderwin : Main3D.cur3D().cockpitCur.world_renderwin);

      if (!paramBoolean) return;
      if (this.bInCockpit) {
        Main3D.cur3D().cameraCockpitMirror.set(f);
        Main3D.cur3D().cameraCockpitMirror.ZNear = this.resultNearClipDepth;
      } else {
        Main3D.cur3D().camera3DMirror.set(f);
        Main3D.cur3D().camera3DMirror.ZNear = this.resultNearClipDepth;
      }
    }

    public HookCamera3DMirror(boolean paramBoolean)
    {
      this.bInCockpit = paramBoolean;
    }
  }

  public static class Camera3DMirror extends Camera3D
  {
    public boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
    {
      if (!Actor.isValid(Main3D.cur3D().cockpitCur)) {
        return super.activate(paramFloat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
      }

      this.pos.getRender(_tmpLoc);
      int[] arrayOfInt = Main3D.cur3D().cockpitCur.world_renderwin;
      if (this == Main3D.cur3D().cameraCockpitMirror)
        arrayOfInt = Main3D.cur3D().cockpitCur.cockpit_renderwin;
      return super.activate(paramFloat, paramInt1, paramInt2, Main3D.cur3D().mirrorX0() + arrayOfInt[0], Main3D.cur3D().mirrorY0() + arrayOfInt[1], arrayOfInt[2], arrayOfInt[3], Main3D.cur3D().mirrorX0(), Main3D.cur3D().mirrorY0(), Main3D.cur3D().mirrorWidth(), Main3D.cur3D().mirrorHeight());
    }
  }
}
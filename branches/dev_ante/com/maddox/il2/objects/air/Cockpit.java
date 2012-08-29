package com.maddox.il2.objects.air;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.gl;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import java.io.PrintStream;

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
    if ((this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null) && 
      (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX != null)) this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX.set(1.0F - paramFloat);
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
    return !aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.isPilotParatrooper(astatePilotIndx());
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
    return (Aircraft)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor;
  }

  public boolean isExistMirror() {
    return this.bExistMirror;
  }

  public void destroy() {
    if (isFocused())
      Main3D.cur3D().setView(this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor, true);
    this.fm = null;
    super.destroy();
  }

  public Cockpit(String paramString1, String paramString2)
  {
    this.fm = _newAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
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
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics = new Acoustics(paramString2);
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.setParent(Engine.worldAcoustics());
      initSounds();
    } catch (Exception localException) {
      System.out.println("Cockpit Acoustics NOT initialized: " + localException.getMessage());
    }
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

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(P1);
    V.sub(P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(V.x, V.y));
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
    public void preRender() { if (!Actor.isValid(Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base())) return;
      if (Cockpit.this.nullMesh == null) return;
      if (Cockpit.this.nullMeshIL2 == null) return;
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__l);
      Main3D.cur3D().cameraCockpit.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__p);
      Cockpit.this.__l.set(Cockpit.this.__p);
      if (Cockpit.this.bEnableRenderingBall)
        Cockpit.this.nullMesh.setPos(Cockpit.this.__l);
      else {
        Cockpit.this.nullMeshIL2.setPos(Cockpit.this.__l);
      }

      if (Cockpit.this.aircraft() != null) {
        Cockpit.this.nullMesh.chunkSetAngles("Ball", Cockpit.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOS(), -Cockpit.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 0.0F);
      }

      if (Cockpit.this.bEnableRenderingBall)
        this.iPreRender = Cockpit.this.nullMesh.preRender();
      else
        this.iPreRender = Cockpit.this.nullMeshIL2.preRender(); }

    public void render()
    {
      if (this.iPreRender == 0) return;
      if (!Actor.isValid(Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base())) return;
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
    public void preRender() { if (!Actor.isValid(Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base())) return;
      if (Cockpit.this.mesh == null) return;
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__l);
      Cockpit.this.mesh.setPos(Cockpit.this.__l);
      Cockpit.this.reflectWorldToInstruments(Time.tickOffset());
      this.iPreRender = Cockpit.this.mesh.preRender(); }

    public void render(boolean paramBoolean)
    {
      if (this.iPreRender == 0) return;
      if (!Actor.isValid(Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base())) return;
      if (Cockpit.this.mesh == null) return;
      Render.currentCamera().activateWorldMode(0);
      gl.GetDoublev(2982, paramBoolean ? this._modelMatrix3DMirror : this._modelMatrix3D);
      if (paramBoolean)
        gl.GetDoublev(2983, this._projMatrix3DMirror);
      Render.currentCamera().deactivateWorldMode();
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__l);
      lightUpdate(Cockpit.this.__l, false);
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__p, Cockpit.this.__o);
      LightPoint.setOffset((float)Cockpit.this.__p.x, (float)Cockpit.this.__p.y, (float)Cockpit.this.__p.z);
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Cockpit.this.__l);
      Cockpit.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightUpdate(Cockpit.this.__l, false);
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
          this.p.set(paramPoint3f1.jdField_x_of_type_Float, paramPoint3f1.jdField_y_of_type_Float, paramPoint3f1.jdField_z_of_type_Float); break;
        case 1:
          this.p.set(paramPoint3f2.jdField_x_of_type_Float, paramPoint3f1.jdField_y_of_type_Float, paramPoint3f1.jdField_z_of_type_Float); break;
        case 2:
          this.p.set(paramPoint3f1.jdField_x_of_type_Float, paramPoint3f2.jdField_y_of_type_Float, paramPoint3f1.jdField_z_of_type_Float); break;
        case 3:
          this.p.set(paramPoint3f1.jdField_x_of_type_Float, paramPoint3f1.jdField_y_of_type_Float, paramPoint3f2.jdField_z_of_type_Float); break;
        case 4:
          this.p.set(paramPoint3f2.jdField_x_of_type_Float, paramPoint3f2.jdField_y_of_type_Float, paramPoint3f2.jdField_z_of_type_Float); break;
        case 5:
          this.p.set(paramPoint3f1.jdField_x_of_type_Float, paramPoint3f2.jdField_y_of_type_Float, paramPoint3f2.jdField_z_of_type_Float); break;
        case 6:
          this.p.set(paramPoint3f2.jdField_x_of_type_Float, paramPoint3f1.jdField_y_of_type_Float, paramPoint3f2.jdField_z_of_type_Float); break;
        case 7:
          this.p.set(paramPoint3f2.jdField_x_of_type_Float, paramPoint3f2.jdField_y_of_type_Float, paramPoint3f1.jdField_z_of_type_Float);
        }

        this.mir2cmm.transform(this.p);

        f6 = -(float)(f1 * this.p.y / this.p.jdField_x_of_type_Double);
        f7 = (float)(f1 * this.p.z / this.p.jdField_x_of_type_Double);

        if (i == 0) {
          f2 = f3 = f6;
          f4 = f5 = f7;
        } else {
          if (f6 < f2) {
            f2 = f6;
          }
          if (f6 > f3) {
            f3 = f6;
          }
          if (f7 < f4) {
            f4 = f7;
          }
          if (f7 > f5) {
            f5 = f7;
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
          Main3D.cur3D().cockpitCur.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(this.aLoc);
        else
          World.getPlayerAircraft().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(this.aLoc);
      }
      else if (this.bInCockpit)
        Main3D.cur3D().cockpitCur.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.aLoc);
      else {
        World.getPlayerAircraft().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.aLoc);
      }
      this.mir2w_loc.add(this.mir2w_loc, this.aLoc);

      Main3D.cur3D().cockpitCur.mesh.getChunkCurVisBoundBox(this.mirLowP, this.mirHigP);

      float f = computeMirroredCamera(localLoc, this.mir2w_loc, this.mirLowP, this.mirHigP, Main3D.cur3D().mirrorWidth(), Main3D.cur3D().mirrorHeight(), 1.0F, paramLoc2, this.bInCockpit ? Main3D.cur3D().cockpitCur.cockpit_renderwin : Main3D.cur3D().cockpitCur.world_renderwin);

      if (!paramBoolean) return;
      if (this.bInCockpit) {
        Main3D.cur3D().cameraCockpitMirror.set(f);
        Main3D.cur3D().cameraCockpitMirror.jdField_ZNear_of_type_Float = this.resultNearClipDepth;
      } else {
        Main3D.cur3D().camera3DMirror.set(f);
        Main3D.cur3D().camera3DMirror.jdField_ZNear_of_type_Float = this.resultNearClipDepth;
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

      this.pos.getRender(Actor._tmpLoc);
      int[] arrayOfInt = Main3D.cur3D().cockpitCur.world_renderwin;
      if (this == Main3D.cur3D().cameraCockpitMirror)
        arrayOfInt = Main3D.cur3D().cockpitCur.cockpit_renderwin;
      return super.activate(paramFloat, paramInt1, paramInt2, Main3D.cur3D().mirrorX0() + arrayOfInt[0], Main3D.cur3D().mirrorY0() + arrayOfInt[1], arrayOfInt[2], arrayOfInt[3], Main3D.cur3D().mirrorX0(), Main3D.cur3D().mirrorY0(), Main3D.cur3D().mirrorWidth(), Main3D.cur3D().mirrorHeight());
    }
  }
}
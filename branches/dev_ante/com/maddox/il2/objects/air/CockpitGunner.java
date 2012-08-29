package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookGunner.Move;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Main3D.RenderCockpit;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;

public class CockpitGunner extends Cockpit
  implements HookGunner.Move
{
  private String[] hotKeyEnvs = { "gunner" };

  private String[] hotKeyEnvsAll = { "gunner", "pilot", "move" };
  protected Hook cameraHook;
  protected boolean bGunFire;
  protected BulletEmitter emitter;
  private Orient _tmpOrient;
  private HookGunner hookGunner;
  private boolean bNetMirror;
  protected int _aiTuretNum;
  protected int _weaponControlNum;
  private static Loc _tmpLoc1 = new Loc();
  private static Loc _tmpLoc2 = new Loc();
  private static Point3d _tmpP1 = new Point3d();
  private static Point3d _tmpP2 = new Point3d();
  private static Vector3d _tmpV1 = new Vector3d();
  private static Shot _tmpShot = new Shot();

  public boolean isEnableHotKeysOnOutsideView()
  {
    return false;
  }

  public String[] getHotKeyEnvs()
  {
    return this.hotKeyEnvs;
  }

  public HookGunner hookGunner()
  {
    return this.hookGunner;
  }

  public Turret aiTurret()
  {
    if (this._aiTuretNum == -1)
      this._aiTuretNum = Property.intValue(getClass(), "aiTuretNum", 0);
    return this.fm.turret[this._aiTuretNum];
  }

  public int weaponControlNum()
  {
    if (this._weaponControlNum == -1)
      this._weaponControlNum = Property.intValue(getClass(), "weaponControlNum", 10);
    return this._weaponControlNum;
  }

  public boolean isRealMode()
  {
    return !aiTurret().bIsAIControlled;
  }

  public void setRealMode(boolean paramBoolean)
  {
    if (aiTurret().bIsAIControlled != paramBoolean)
      return;
    aiTurret().bIsAIControlled = (!paramBoolean);
    aiTurret().target = null;
    this.fm.CT.WeaponControl[weaponControlNum()] = false;
    this.bGunFire = false;
    if (paramBoolean)
    {
      hookGunner().resetMove(0.0F, 0.0F);
    }
    else {
      aiTurret().tu[0] = 0.0F;
      aiTurret().tu[1] = 0.0F;
    }
  }

  public boolean isNetMirror()
  {
    return this.bNetMirror;
  }

  public void setNetMirror(boolean paramBoolean)
  {
    this.bNetMirror = paramBoolean;
  }

  public void moveGun(Orient paramOrient)
  {
    if (isRealMode())
    {
      aiTurret().tu[0] = paramOrient.getAzimut();
      aiTurret().tu[1] = paramOrient.getTangage();
    }
  }

  public void clipAnglesGun(Orient paramOrient)
  {
  }

  public Hook getHookCameraGun()
  {
    if (!isRealMode())
    {
      this._tmpOrient.set(aiTurret().tu[0], aiTurret().tu[1], 0.0F);
      moveGun(this._tmpOrient);
    }
    return this.cameraHook;
  }

  public void doGunFire(boolean paramBoolean)
  {
    if ((this.emitter != null) && (!this.emitter.haveBullets()))
      this.bGunFire = false;
    else
      this.bGunFire = paramBoolean;
  }

  protected void doHitMasterAircraft(Aircraft paramAircraft, Hook paramHook, String paramString)
  {
    _tmpLoc1.set(0.1D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    paramAircraft.pos.getAbs(_tmpLoc2);
    paramHook.computePos(paramAircraft, _tmpLoc2, _tmpLoc1);
    _tmpLoc1.get(_tmpP1);
    _tmpLoc1.set(48.899999999999999D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    paramHook.computePos(paramAircraft, _tmpLoc2, _tmpLoc1);
    _tmpLoc1.get(_tmpP2);
    int i = paramAircraft.hierMesh().detectCollisionLineMulti(_tmpLoc2, _tmpP1, _tmpP2);
    if (i > 0)
    {
      Gun localGun = paramAircraft.getGunByHookName(paramString);
      if (localGun.getTickShot())
      {
        _tmpShot.powerType = 0;
        _tmpShot.mass = localGun.bulletMassa();
        paramAircraft.hierMesh(); _tmpShot.p.interpolate(_tmpP1, _tmpP2, HierMesh.collisionDistMulti(0));
        _tmpShot.v.x = (float)(_tmpP2.x - _tmpP1.x);
        _tmpShot.v.y = (float)(_tmpP2.y - _tmpP1.y);
        _tmpShot.v.z = (float)(_tmpP2.z - _tmpP1.z);
        _tmpShot.v.normalize();
        _tmpShot.v.scale(localGun.bulletSpeed());
        paramAircraft.getSpeed(_tmpV1);
        _tmpShot.v.x += (float)_tmpV1.x;
        _tmpShot.v.y += (float)_tmpV1.y;
        _tmpShot.v.z += (float)_tmpV1.z;
        _tmpShot.tickOffset = 1.0D;
        _tmpShot.chunkName = "CF_D0";
        _tmpShot.initiator = this.fm.actor;
        paramAircraft.msgShot(_tmpShot);
      }
    }
  }

  protected void _setNullShow(boolean paramBoolean)
  {
  }

  protected void _setEnableRendering(boolean paramBoolean)
  {
  }

  protected void interpTick()
  {
  }

  protected boolean doFocusEnter()
  {
    HookGunner localHookGunner = hookGunner();
    Aircraft localAircraft = aircraft();
    Main3D localMain3D = Main3D.cur3D();
    localHookGunner.setMover(this);
    localHookGunner.reset();
    localHookGunner.use(true);
    localAircraft.setAcoustics(this.acoustics);
    if (this.acoustics != null)
    {
      localAircraft.enableDoorSnd(true);
      if (this.acoustics.getEnvNum() == 2)
        localAircraft.setDoorSnd(1.0F);
    }
    localMain3D.camera3D.pos.setRel(new Point3d(), new Orient());
    localMain3D.camera3D.pos.setBase(localAircraft, localHookGunner, false);
    localMain3D.camera3D.pos.resetAsBase();
    this.pos.resetAsBase();
    localMain3D.cameraCockpit.pos.setRel(new Point3d(), new Orient());
    localMain3D.cameraCockpit.pos.setBase(this, localHookGunner, false);
    localMain3D.cameraCockpit.pos.resetAsBase();
    localMain3D.overLoad.setShow(true);
    localMain3D.renderCockpit.setShow(true);
    localAircraft.drawing(!isNullShow());
    return true;
  }

  protected void doFocusLeave()
  {
    HookGunner localHookGunner = hookGunner();
    Aircraft localAircraft = aircraft();
    Main3D localMain3D = Main3D.cur3D();
    localHookGunner.use(false);
    localMain3D.camera3D.pos.setRel(new Point3d(), new Orient());
    localMain3D.camera3D.pos.setBase(null, null, false);
    localMain3D.cameraCockpit.pos.setRel(new Point3d(), new Orient());
    localMain3D.cameraCockpit.pos.setBase(null, null, false);
    localMain3D.overLoad.setShow(false);
    localMain3D.renderCockpit.setShow(false);
    if (Actor.isValid(localAircraft))
      localAircraft.drawing(true);
    if (localAircraft != null) {
      if (localAircraft.FM.CT.getCockpitDoor() < 0.5D) {
        localAircraft.setDoorSnd(0.0F);
      }
      else {
        localAircraft.setDoorSnd(1.0F);
      }
      localAircraft.setAcoustics(null);
    }
  }

  public CockpitGunner(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    this.bGunFire = false;
    this._tmpOrient = new Orient();
    this.bNetMirror = false;
    this._aiTuretNum = -1;
    this._weaponControlNum = -1;
    this.cameraHook = new HookNamed(this.mesh, "CAMERA");
    this.pos.setBase(aircraft(), new Cockpit.HookOnlyOrient(), false);
    this.hookGunner = new HookGunner(Main3D.cur3D().cameraCockpit, Main3D.cur3D().camera3D);
    hookGunner().setMover(this);
    interpPut(new CockpitGunner.Interpolater(this), null, Time.current(), null);
    Aircraft localAircraft = aircraft();
    BulletEmitter[] arrayOfBulletEmitter = localAircraft.FM.CT.Weapons[weaponControlNum()];
    if (arrayOfBulletEmitter != null)
      this.emitter = arrayOfBulletEmitter[0];
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Property.set(CockpitGunner.class, "aiTuretNum", 0);
    Property.set(CockpitGunner.class, "weaponControlNum", 10);
    Property.set(CockpitGunner.class, "astatePilotIndx", 1);
  }
}
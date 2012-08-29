package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.BaseObject;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;

public class SndAircraft extends ActorHMesh
{
  public FlightModel FM = null;

  protected SoundFX sndRoot = null;
  protected float doorSndControl = 0.0F;

  protected Point3d sndRelPos = new Point3d(3.0D, 0.0D, 0.0D);

  protected MotorSound[] motorSnd = null;
  private static final float divSpeed = 0.3F;
  private boolean bDiving = false;
  private boolean sndDivingState = false;
  private float divState = 0.0F; private float divIncr = 0.0F;
  private static final float GV0 = 0.4F;
  private long prevRt = 0L;
  public static final int FEED_PNEUMATIC = 0;
  public static final int FEED_ELECTRIC = 1;
  public static final int FEED_HYDRAULIC = 2;
  protected AudioStream sndGear = null;
  protected AudioStream sndFlaps = null;
  protected AudioStream sndAirBrake = null;
  protected SoundFX smokeSound = null;
  protected SoundFX doorSound = null;
  private float dShake = 0.0F;
  private float dWheels = 0.0F;
  private boolean prevWG = true;

  protected static SoundPreset prsHit = null;

  private float doorPrev = 0.0F;
  private boolean doorEnabled = false;
  private Point3d doorSndPos = null;
  private int[] smf;

  public SoundFX getRootFX()
  {
    return this.sndRoot;
  }

  public void sfxInit(int paramInt)
  {
    if (this.sndRoot != null) {
      SectFile localSectFile = new SectFile("presets/sounds/aircraft.misc.prs");
      String str = paramInt == 0 ? "p_" : "e_";
      this.sndGear = new Sample(localSectFile, str + "gear").get();
      this.sndRoot.add(this.sndGear);
      if (!haveFlaps()) str = "m_";
      this.sndFlaps = new Sample(localSectFile, str + "flaps").get();
      this.sndRoot.add(this.sndFlaps);
      if ((this.FM instanceof RealFlightModel)) {
        this.sndAirBrake = new Sample(localSectFile, "air_brake").get();
        this.sndRoot.add(this.sndAirBrake);
      }
    }
    if (prsHit == null)
      prsHit = new SoundPreset("hit.air");
  }

  protected boolean haveFlaps()
  {
    return true;
  }

  public void sfxCrash(Point3d paramPoint3d)
  {
    SoundFX localSoundFX = newSound("crash.cutoff", true);
    if (localSoundFX != null) {
      localSoundFX.setPosition(paramPoint3d);
      localSoundFX.setParent(this.sndRoot);
    }
  }

  public void sfxHit(float paramFloat, Point3d paramPoint3d)
  {
    if (this.sndRoot != null) {
      SoundFX localSoundFX = newSound(prsHit, false, true);
      localSoundFX.setParent(this.sndRoot);
      localSoundFX.setPosition(paramPoint3d);
      localSoundFX.setUsrFlag(paramFloat > 0.05F ? 1 : 0);
      localSoundFX.play();
    }
  }

  public void sfxTow()
  {
    this.dShake = 1.0F;
    SoundFX localSoundFX = newSound("aircraft.tow", true);
    if (localSoundFX != null) {
      localSoundFX.setPosition(new Point3d(0.0D, 0.0D, -1.0D));
      localSoundFX.setParent(this.sndRoot);
    }
  }

  public void sfxWheels()
  {
    SoundFX localSoundFX = newSound("aircraft.wheels", true);
    if (localSoundFX != null) {
      localSoundFX.setPosition(new Point3d(0.0D, 0.0D, -1.5D));
      localSoundFX.setParent(this.sndRoot);
    }
  }

  public void setDoorSnd(float paramFloat)
  {
    if (this.FM != null) {
      this.doorSndControl = paramFloat;
      if ((this.doorSound == null) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.dvCockpitDoor > 0.0F)) {
        int i = 1;
        float f = 1.0F / this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.dvCockpitDoor;
        this.doorSndPos = new Point3d(-1.0D, 0.0D, 0.0D);
        this.doorSound = newSound("aircraft.door", false);
        if (this.doorSound != null) {
          this.doorSound.setParent(getRootFX());
          if (f <= 1.1F) i = 0;
          else if (f >= 1.8F) i = 2;
          this.doorSound.setUsrFlag(i);
        }
      }
      if (((paramFloat != 0.0F) && (this.doorPrev == 0.0F)) || ((paramFloat != 1.0F) && (this.doorPrev == 1.0F) && 
        (this.doorSound != null))) {
        this.doorSound.play(this.doorSndPos);
      }

      this.doorPrev = paramFloat;
    }
  }

  public void enableDoorSnd(boolean paramBoolean)
  {
    this.doorEnabled = paramBoolean;
  }

  public void sfxSmokeState(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.smf[paramInt1] |= 1 << paramInt2;
      if (this.smokeSound == null)
        this.smokeSound = newSound("objects.flame", true);
    }
    else {
      int i = 1;
      this.smf[paramInt1] &= (1 << paramInt2 ^ 0xFFFFFFFF);
      for (int j = 0; j < 3; j++) i = (i != 0) && (this.smf[j] == 0) ? 1 : 0;
      if (paramInt1 == 0) i = 1;
      if ((i != 0) && (this.smokeSound != null)) {
        this.smokeSound.cancel();
        this.smokeSound = null;
      }
    }
  }

  public void sfxGear(boolean paramBoolean)
  {
    if (this.sndGear != null) this.sndGear.setPlay(paramBoolean);
  }

  public void sfxFlaps(boolean paramBoolean)
  {
    if (this.sndFlaps != null) this.sndFlaps.setPlay(paramBoolean);
  }

  public void sfxAirBrake()
  {
    if (this.sndAirBrake != null) this.sndAirBrake.play(); 
  }

  public void updateLLights()
  {
  }

  public void update(float paramFloat)
  {
    if (this.dShake > 0.0F) {
      this.dShake -= paramFloat / 1.7F;
      if (this.dShake <= 0.05F) this.dShake = 0.0F;
    }
    if (this.dWheels > 0.0F) {
      this.dWheels -= paramFloat;
      if (this.dWheels < 0.0F) this.dWheels = 0.0F;
    }
  }

  public SndAircraft()
  {
    this.smf = new int[3];
    for (int i = 0; i < 3; i++) this.smf[i] = 0;

    this.draw = new ActorMeshDraw()
    {
      public int preRender(Actor paramActor) {
        SndAircraft.this.updateLLights();
        return super.preRender(paramActor);
      }

      public void soundUpdate(Actor paramActor, Loc paramLoc)
      {
        if ((!Config.cur.isSoundUse()) || (SndAircraft.this.sndRoot == null)) return;
        int i;
        if (SndAircraft.this.FM != null) {
          if (SndAircraft.this.bDiving) {
            if (SndAircraft.this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl > 0.4F) {
              if ((SndAircraft.this.divState == 0.0F) || (SndAircraft.this.divIncr < 0.0F)) {
                SndAircraft.access$202(SndAircraft.this, 0.3F);
              }
            }
            else if (SndAircraft.this.divState > 0.0F) SndAircraft.access$202(SndAircraft.this, -0.39F); else {
              SndAircraft.access$202(SndAircraft.this, 0.0F);
            }
            if (SndAircraft.this.divState > 1.0F) {
              SndAircraft.access$202(SndAircraft.this, 0.0F);
              SndAircraft.access$102(SndAircraft.this, 1.0F);
            }
            else if (SndAircraft.this.divState < 0.0F) {
              SndAircraft.access$202(SndAircraft.this, 0.0F);
              SndAircraft.access$102(SndAircraft.this, 0.0F);
            }
            SndAircraft.this.sndRoot.setControl(102, SndAircraft.this.divState);
            long l = Time.real();
            if (SndAircraft.this.divIncr != 0.0F) SndAircraft.access$116(SndAircraft.this, (float)(l - SndAircraft.this.prevRt) * SndAircraft.this.divIncr / 1000.0F);
            SndAircraft.access$302(SndAircraft.this, l);
          } else {
            SndAircraft.this.sndRoot.setControl(102, 0.0F);
          }
          if ((SndAircraft.this.FM instanceof RealFlightModel)) {
            float f = ((RealFlightModel)SndAircraft.this.FM).shakeLevel + SndAircraft.this.dShake;
            if (f > 1.0F) f = 1.0F;
            SndAircraft.this.sndRoot.setControl(103, f);
          }
          SndAircraft.this.sndRoot.setControl(104, ((SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[0] > 0.4000000059604645D) && (SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear)) || ((SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[1] > 0.4000000059604645D) && (SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear)) || (SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.gVelocity[2] > 0.4000000059604645D) ? SndAircraft.this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl : 0.0F);

          SndAircraft.this.sndRoot.setControl(105, SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.getLandingState());
          SndAircraft.this.sndRoot.setControl(111, (float)SndAircraft.this.FM.Vrel.length());
          i = SndAircraft.this.FM.jdField_Gears_of_type_ComMaddoxIl2FmGear.getWheelsOnGround();
          if ((!SndAircraft.this.prevWG) && (i != 0) && (SndAircraft.this.dWheels == 0.0F)) {
            SndAircraft.this.sfxWheels();
          }
          if (i != 0) SndAircraft.access$602(SndAircraft.this, 4.0F);
          SndAircraft.access$502(SndAircraft.this, i);
        }
        if (SndAircraft.this.doorEnabled)
          SndAircraft.this.sndRoot.setControl(110, SndAircraft.this.doorSndControl);
        else {
          SndAircraft.this.sndRoot.setControl(110, 0.0F);
        }
        if (SndAircraft.this.motorSnd != null) {
          for (i = 0; i < SndAircraft.this.motorSnd.length; i++) {
            SndAircraft.this.motorSnd[i].update();
          }
        }
        super.soundUpdate(paramActor, paramLoc);
      }
    };
  }

  public boolean hasInternalSounds() {
    return false;
  }

  public void setMotorPos(Point3d paramPoint3d)
  {
    Point3d localPoint3d = null;
    if (paramPoint3d != null) {
      localPoint3d = new Point3d(this.pos.getAbsPoint());
      localPoint3d.sub(paramPoint3d);
    }
    for (int i = 0; i < this.motorSnd.length; i++)
      this.motorSnd[i].setPos(localPoint3d);
  }

  protected void initSound(SectFile paramSectFile)
  {
    if (!BaseObject.isEnabled()) return;
    if ((this instanceof TypeSailPlane))
      this.sndRoot = newSound("aircraft.common_w", true);
    else {
      this.sndRoot = newSound("aircraft.common", true);
    }
    int i = this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
    this.motorSnd = new MotorSound[i];
    for (int j = 0; j < i; j++) {
      this.motorSnd[j] = new MotorSound(this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j], this);
    }
    this.bDiving = (paramSectFile.get("SOUND", "Diving", "").length() > 0);
    sfxInit(0);
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    super.destroy();
    if (this.sndRoot != null) this.sndRoot.cancel();
    if (this.sndGear != null) this.sndGear.cancel();
    if (this.sndFlaps != null) this.sndFlaps.cancel();
    if (this.sndAirBrake != null) this.sndAirBrake.cancel();
    if (this.smokeSound != null) this.smokeSound.cancel();
  }
}
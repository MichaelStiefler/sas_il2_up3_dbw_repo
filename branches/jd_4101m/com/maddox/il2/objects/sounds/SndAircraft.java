package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
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
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
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

  protected static SoundPreset morsePreset = null;
  protected static SoundPreset morsePresetExtra = null;
  protected SoundFX[] morseSounds = null;
  protected SoundFX[] morseSoundsExtra = null;
  protected Point3d morsePos = new Point3d(0.0D, 0.0D, 0.0D);
  private int[] morseSequence = null;
  private int currentMorse = 0;
  private float morseSequenceVolume = 0.0F;
  public static final int MORSE_A = 0;
  public static final int MORSE_B = 1;
  public static final int MORSE_C = 2;
  public static final int MORSE_D = 3;
  public static final int MORSE_E = 4;
  public static final int MORSE_F = 5;
  public static final int MORSE_G = 6;
  public static final int MORSE_H = 7;
  public static final int MORSE_I = 8;
  public static final int MORSE_J = 9;
  public static final int MORSE_K = 10;
  public static final int MORSE_L = 11;
  public static final int MORSE_M = 12;
  public static final int MORSE_N = 13;
  public static final int MORSE_O = 14;
  public static final int MORSE_P = 15;
  public static final int MORSE_Q = 16;
  public static final int MORSE_R = 17;
  public static final int MORSE_S = 18;
  public static final int MORSE_T = 19;
  public static final int MORSE_U = 20;
  public static final int MORSE_V = 21;
  public static final int MORSE_W = 22;
  public static final int MORSE_X = 23;
  public static final int MORSE_Y = 24;
  public static final int MORSE_Z = 25;
  public static final int LORENZ_DASH = 26;
  public static final int LORENZ_DOT = 27;
  public static final int LORENZ_SOLID = 28;
  public static final int LORENZ_OUTER_MARKER = 29;
  public static final int LORENZ_INNER_MARKER = 30;
  public static final int BEACON_CARRIER = 31;
  public static final int MORSE_COUNT = 32;
  public static final int MORSE_1 = 32;
  public static final int MORSE_2 = 33;
  public static final int MORSE_3 = 34;
  public static final int MORSE_4 = 35;
  public static final int MORSE_5 = 36;
  public static final int MORSE_6 = 37;
  public static final int MORSE_7 = 38;
  public static final int MORSE_8 = 39;
  public static final int MORSE_9 = 40;
  public static final int MORSE_0 = 41;
  public static final int MORSE_COMMA = 42;
  public static final int MORSE_PERIOD = 43;
  public static final int MORSE_QUESTION = 44;
  public static final int MORSE_HYPHEN = 45;
  public static final int MORSE_SPACE = 46;
  public static final int RADIO_STATIC1 = 47;
  public static final int RADIO_STATIC2 = 48;
  public static final int BEACON_STATIC = 49;
  public static final int YEYG_STATIC = 50;
  public static final int MORSE_EXTRA_COUNT = 19;
  private static float radio_static1 = 1.0F;
  private static float radio_static2 = 0.0F;
  private static int radio_static1dir = 0;
  private static int radio_static2dir = 0;

  private static final char[] VALID_MORSE_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '.', ',', '-', '?', ' ', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

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
      if ((this.doorSound == null) && (this.FM.CT.dvCockpitDoor > 0.0F)) {
        int i = 1;
        float f = 1.0F / this.FM.CT.dvCockpitDoor;
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
            if (SndAircraft.this.FM.CT.AirBrakeControl > 0.4F) {
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
          SndAircraft.this.sndRoot.setControl(104, ((SndAircraft.this.FM.Gears.gVelocity[0] > 0.4000000059604645D) && (SndAircraft.this.FM.Gears.lgear)) || ((SndAircraft.this.FM.Gears.gVelocity[1] > 0.4000000059604645D) && (SndAircraft.this.FM.Gears.rgear)) || (SndAircraft.this.FM.Gears.gVelocity[2] > 0.4000000059604645D) ? SndAircraft.this.FM.CT.BrakeControl : 0.0F);

          SndAircraft.this.sndRoot.setControl(105, SndAircraft.this.FM.Gears.getLandingState());
          SndAircraft.this.sndRoot.setControl(111, (float)SndAircraft.this.FM.Vrel.length());
          i = SndAircraft.this.FM.Gears.getWheelsOnGround();
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
        SndAircraft.this.updateMorseSequence();
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
    int i = this.FM.EI.getNum();
    this.motorSnd = new MotorSound[i];
    for (int j = 0; j < i; j++) {
      this.motorSnd[j] = new MotorSound(this.FM.EI.engines[j], this);
    }
    this.bDiving = (paramSectFile.get("SOUND", "Diving", "").length() > 0);
    sfxInit(0);
    initMorse();
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

  protected void initMorse()
  {
    if (morsePreset == null)
      morsePreset = new SoundPreset("aircraft.morse");
    this.morseSounds = new SoundFX[32];

    if (morsePresetExtra == null)
      morsePresetExtra = new SoundPreset("aircraft.morse.extra");
    this.morseSoundsExtra = new SoundFX[19];
  }

  private void updateMorseSequence()
  {
    if (this.morseSequence == null)
      return;
    if (!isMorsePlaying(this.morseSequence[this.currentMorse]))
    {
      if (this.currentMorse < this.morseSequence.length - 1)
      {
        this.currentMorse += 1;
        playMorseEffect(this.morseSequence[this.currentMorse], true, this.morseSequenceVolume);
      }
      else
      {
        this.morseSequence = null;
        if (World.cur().showMorseAsText)
          HUD.training("");
      }
    }
  }

  public boolean isMorseSequencePlaying()
  {
    return this.morseSequence != null;
  }

  private int[] getCharArray(String paramString)
  {
    int[] arrayOfInt = new int[paramString.length()];
    for (int i = 0; i < paramString.length(); i++)
    {
      int j = paramString.charAt(i);
      switch (j)
      {
      case 65:
        arrayOfInt[i] = 0;
        break;
      case 66:
        arrayOfInt[i] = 1;
        break;
      case 67:
        arrayOfInt[i] = 2;
        break;
      case 68:
        arrayOfInt[i] = 3;
        break;
      case 69:
        arrayOfInt[i] = 4;
        break;
      case 70:
        arrayOfInt[i] = 5;
        break;
      case 71:
        arrayOfInt[i] = 6;
        break;
      case 72:
        arrayOfInt[i] = 7;
        break;
      case 73:
        arrayOfInt[i] = 8;
        break;
      case 74:
        arrayOfInt[i] = 9;
        break;
      case 75:
        arrayOfInt[i] = 10;
        break;
      case 76:
        arrayOfInt[i] = 11;
        break;
      case 77:
        arrayOfInt[i] = 12;
        break;
      case 78:
        arrayOfInt[i] = 13;
        break;
      case 79:
        arrayOfInt[i] = 14;
        break;
      case 80:
        arrayOfInt[i] = 15;
        break;
      case 81:
        arrayOfInt[i] = 16;
        break;
      case 82:
        arrayOfInt[i] = 17;
        break;
      case 83:
        arrayOfInt[i] = 18;
        break;
      case 84:
        arrayOfInt[i] = 19;
        break;
      case 85:
        arrayOfInt[i] = 20;
        break;
      case 86:
        arrayOfInt[i] = 21;
        break;
      case 87:
        arrayOfInt[i] = 22;
        break;
      case 88:
        arrayOfInt[i] = 23;
        break;
      case 89:
        arrayOfInt[i] = 24;
        break;
      case 90:
        arrayOfInt[i] = 25;
        break;
      case 45:
        arrayOfInt[i] = 45;
        break;
      case 46:
        arrayOfInt[i] = 43;
        break;
      case 44:
        arrayOfInt[i] = 42;
        break;
      case 63:
        arrayOfInt[i] = 44;
        break;
      case 32:
        arrayOfInt[i] = 46;
        break;
      case 49:
        arrayOfInt[i] = 32;
        break;
      case 50:
        arrayOfInt[i] = 33;
        break;
      case 51:
        arrayOfInt[i] = 34;
        break;
      case 52:
        arrayOfInt[i] = 35;
        break;
      case 53:
        arrayOfInt[i] = 36;
        break;
      case 54:
        arrayOfInt[i] = 37;
        break;
      case 55:
        arrayOfInt[i] = 38;
        break;
      case 56:
        arrayOfInt[i] = 39;
        break;
      case 57:
        arrayOfInt[i] = 40;
        break;
      case 48:
        arrayOfInt[i] = 41;
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 47:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 64: }  } return arrayOfInt;
  }

  private static String getMorseString(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      return "Morse: A  · —";
    case 1:
      return "Morse: B  — · · ·";
    case 2:
      return "Morse: C  — · — ·";
    case 3:
      return "Morse: D  — · ·";
    case 4:
      return "Morse: E  ·";
    case 5:
      return "Morse: F  · · — ·";
    case 6:
      return "Morse: G  — — ·";
    case 7:
      return "Morse: H  · · · ·";
    case 8:
      return "Morse: I  · ·";
    case 9:
      return "Morse: J  · — — —";
    case 10:
      return "Morse: K  — · —";
    case 11:
      return "Morse: L  · — · ·";
    case 12:
      return "Morse: M  — —";
    case 13:
      return "Morse: N  — ·";
    case 14:
      return "Morse: O  — — —";
    case 15:
      return "Morse: P  · — — ·";
    case 16:
      return "Morse: Q  — — · —";
    case 17:
      return "Morse: R  · — ·";
    case 18:
      return "Morse: S  · · ·";
    case 19:
      return "Morse: T  —";
    case 20:
      return "Morse: U  · · —";
    case 21:
      return "Morse: V  · · · —";
    case 22:
      return "Morse: W  · — —";
    case 23:
      return "Morse: X  — · · —";
    case 24:
      return "Morse: Y  — · — —";
    case 25:
      return "Morse: Z  — — · ·";
    case 45:
      return "Morse: <hyphen>   — · · · · —";
    case 43:
      return "Morse: .  · — · — · —";
    case 42:
      return "Morse: ,  — — · · — —";
    case 44:
      return "Morse: ?  · · — — · ·";
    case 46:
      return "";
    case 32:
      return "Morse: 1  · — — — —";
    case 33:
      return "Morse: 2  · · — — —";
    case 34:
      return "Morse: 3  · · · — —";
    case 35:
      return "Morse: 4  · · · · —";
    case 36:
      return "Morse: 5  · · · · ·";
    case 37:
      return "Morse: 6  — · · · ·";
    case 38:
      return "Morse: 7  — — · · ·";
    case 39:
      return "Morse: 8  — — — · ·";
    case 40:
      return "Morse: 9  — — — — ·";
    case 41:
      return "Morse: 0  — — — — —";
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31: } return "";
  }

  public void playChatMsgAsMorse(String paramString)
  {
    if ((isMorseSequencePlaying()) || (paramString == null))
      return;
    String str = paramString.substring(6).trim().toUpperCase();

    if (str.length() == 0) {
      return;
    }
    str = cleanMorseString(str);
    morseSequenceStart(getCharArray(str), 0.5F);
  }

  private String cleanMorseString(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      for (int j = 0; j < VALID_MORSE_CHARS.length; j++)
      {
        if (VALID_MORSE_CHARS[j] != c)
          continue;
        localStringBuffer.append(c);
        break;
      }
    }

    return localStringBuffer.toString();
  }

  public void morseSequenceStart(String paramString, float paramFloat)
  {
    if (isMorseSequencePlaying())
      morseSequenceStop();
    morseSequenceStart(getCharArray(cleanMorseString(paramString.toUpperCase())), paramFloat);
  }

  private void morseSequenceStart(int[] paramArrayOfInt, float paramFloat)
  {
    this.morseSequence = paramArrayOfInt;
    this.morseSequenceVolume = paramFloat;
    this.currentMorse = 0;
    playMorseEffect(this.morseSequence[this.currentMorse], true, this.morseSequenceVolume);
  }

  protected void morseSequenceStop()
  {
    morseStop(this.currentMorse);
    this.morseSequence = null;
  }

  private void showMorseAsText(int paramInt)
  {
    if (!World.cur().showMorseAsText)
      return;
    if ((Main.cur().netServerParams != null) && (!Main.cur().netServerParams.isSingle()) && (!Main.cur().netServerParams.allowMorseAsText)) {
      return;
    }
    if (((paramInt >= 0) && (paramInt <= 25)) || ((paramInt >= 32) && (paramInt <= 46)))
    {
      HUD.training(getMorseString(paramInt));
    }
  }

  protected void playMorseEffect(int paramInt, boolean paramBoolean, float paramFloat)
  {
    if ((paramBoolean) && (paramFloat >= 0.1F))
      showMorseAsText(paramInt);
    SoundFX localSoundFX;
    if (paramInt < 32)
    {
      if ((this.morseSounds != null) && (this.morseSounds.length > paramInt))
      {
        localSoundFX = this.morseSounds[paramInt];
        if (localSoundFX == null)
        {
          localSoundFX = newSound(morsePreset, false, false);
          if (localSoundFX == null) {
            return;
          }
          localSoundFX.setParent(this.sndRoot);
          this.morseSounds[paramInt] = localSoundFX;
          localSoundFX.setUsrFlag(paramInt);
        }
        localSoundFX.setVolume(paramFloat);
        localSoundFX.setPlay(paramBoolean);
      }
    }
    else
    {
      paramInt -= 32;
      if ((this.morseSoundsExtra != null) && (this.morseSoundsExtra.length > paramInt))
      {
        localSoundFX = this.morseSoundsExtra[paramInt];
        if (localSoundFX == null)
        {
          localSoundFX = newSound(morsePresetExtra, false, false);
          if (localSoundFX == null) {
            return;
          }
          localSoundFX.setParent(this.sndRoot);
          this.morseSoundsExtra[paramInt] = localSoundFX;
          localSoundFX.setUsrFlag(paramInt);
        }
        localSoundFX.setVolume(paramFloat);
        localSoundFX.setPlay(paramBoolean);
      }
    }
  }

  protected boolean isMorsePlaying(int paramInt)
  {
    if (paramInt < 32)
    {
      if ((this.morseSounds != null) && (this.morseSounds.length > paramInt) && (this.morseSounds[paramInt] != null))
      {
        return this.morseSounds[paramInt].isPlaying();
      }
    }
    else {
      paramInt -= 32;
      if ((this.morseSoundsExtra != null) && (this.morseSoundsExtra.length > paramInt) && (this.morseSoundsExtra[paramInt] != null))
      {
        return this.morseSoundsExtra[paramInt].isPlaying();
      }
    }
    return false;
  }

  protected void morseStop(int paramInt)
  {
    if (paramInt < 32)
    {
      if ((this.morseSounds != null) && (this.morseSounds.length > paramInt) && (this.morseSounds[paramInt] != null))
      {
        this.morseSounds[paramInt].stop();
      }
    }
    else {
      paramInt -= 32;
      if ((this.morseSoundsExtra != null) && (this.morseSoundsExtra.length > paramInt) && (this.morseSoundsExtra[paramInt] != null))
      {
        this.morseSoundsExtra[paramInt].stop();
      }
    }
  }

  public void playLorenzDash(boolean paramBoolean, float paramFloat) {
    playMorseEffect(26, paramBoolean, paramFloat);
  }

  public void playLorenzDot(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(27, paramBoolean, paramFloat);
  }

  public void playLorenzSolid(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(28, paramBoolean, paramFloat);
  }

  public void playLorenzInnerMarker(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(30, paramBoolean, paramFloat);
  }

  public void playLorenzOuterMarker(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(29, paramBoolean, paramFloat);
  }

  public void playYEYGCarrier(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(50, paramBoolean, paramFloat);
  }

  public void playBeaconCarrier(boolean paramBoolean, float paramFloat)
  {
    playMorseEffect(31, paramBoolean, paramFloat * 0.85F);
    playMorseEffect(49, paramBoolean, (-0.7F + (1.0F - paramFloat)) * 3.333F);
  }

  public void playRadioStatic(boolean paramBoolean, float paramFloat) {
    if (paramBoolean)
    {
      double d1 = Math.random();
      double d2 = Math.random();

      if (d1 < 0.2D)
        radio_static1dir = -1;
      else if (d1 > 0.8D)
        radio_static1dir = 1;
      else {
        radio_static1dir = 0;
      }
      if (d2 < 0.2D)
        radio_static2dir = -1;
      else if (d2 > 0.8D)
        radio_static2dir = 1;
      else {
        radio_static2dir = 0;
      }
      radio_static1 += radio_static1dir * 0.1F;
      radio_static2 += radio_static2dir * 0.1F;

      if (radio_static1 < 0.0F) {
        radio_static1 = 0.0F;
      }
      if (radio_static2 < 0.0F) {
        radio_static2 = 0.0F;
      }
      if (radio_static1 > radio_static2)
      {
        radio_static1 = paramFloat;
      }
      else
      {
        radio_static2 = paramFloat;
      }
    }
    playMorseEffect(47, paramBoolean, radio_static1);
    playMorseEffect(48, paramBoolean, radio_static2);
  }

  public void stopMorseSounds()
  {
    playLorenzDot(false, 0.0F);
    playLorenzDash(false, 0.0F);
    playLorenzSolid(false, 0.0F);
    playLorenzInnerMarker(false, 0.0F);
    playLorenzOuterMarker(false, 0.0F);
    playBeaconCarrier(false, 0.0F);
    playRadioStatic(false, 0.0F);
    playYEYGCarrier(false, 0.0F);
  }
}
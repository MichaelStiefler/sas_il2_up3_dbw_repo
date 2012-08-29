package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.il2.fm.FmSounds;
import com.maddox.il2.fm.Motor;
import com.maddox.sound.BaseObject;
import com.maddox.sound.SamplePool;
import com.maddox.sound.SoundFX;
import java.util.Random;

public class MotorSound
  implements FmSounds
{
  protected SndAircraft owner = null;
  protected Motor motor = null;

  protected int sndStartState = 0;

  protected Random dmgRnd = new Random();
  protected long nextTime = 0L; protected long prevTime = 0L;
  protected float tDmg = 0.5F;
  protected float kDmg = 0.0F;

  protected SoundFX sndMotor = null;
  protected SoundFX sndProp = null;
  protected SamplePool spStart = null;
  protected SamplePool spEnd = null;

  private int prevState = 0;
  private boolean bRunning = false;

  public MotorSound(Motor paramMotor, SndAircraft paramSndAircraft)
  {
    this.owner = paramSndAircraft;
    this.motor = paramMotor;

    if (!BaseObject.isEnabled()) return;

    String str1 = paramMotor.soundName;
    String str2 = paramMotor.propName;
    String str3 = paramMotor.startStopName;

    if (str1 == null) str1 = "db605";
    str1 = "motor." + str1;

    if (str3 == null) str3 = "std_p";
    else if (str3.compareToIgnoreCase("none") == 0) str3 = null;
    if (str3 != null) {
      this.spStart = new SamplePool("motor." + str3 + ".start.begin");
      this.spEnd = new SamplePool("motor." + str3 + ".start.end");
    }

    this.sndMotor = paramSndAircraft.newSound(str1, true);
    if (this.sndMotor != null) {
      this.sndMotor.setParent(paramSndAircraft.getRootFX());
      this.sndMotor.setPosition(paramMotor.getEnginePos());

      this.sndMotor.setControl(500, 0.0F);
    }
    if ((str2 != null) && (!"".equals(str2))) {
      str2 = "propeller." + str2;
      this.sndProp = paramSndAircraft.newSound(str2, true);
      if (this.sndProp != null) {
        this.sndProp.setParent(paramSndAircraft.getRootFX());
        this.sndProp.setPosition(paramMotor.getEnginePos());
      }
    }
    paramMotor.isnd = this;
  }

  public void setPos(Point3d paramPoint3d)
  {
  }

  public void onEngineState(int paramInt)
  {
    if (this.sndMotor != null) {
      if (paramInt != this.prevState) {
        if ((paramInt == 2) && 
          (this.spStart != null)) this.sndMotor.play(this.spStart);

        if ((paramInt == 4) && 
          (this.spEnd != null)) this.sndMotor.play(this.spEnd);
      }

      if ((paramInt > 2) && (paramInt <= 6)) {
        if (paramInt == 3) {
          this.sndMotor.setControl(500, 1.0F);
          this.sndMotor.setControl(501, 10.0F);
        }
        if (paramInt == 5) this.sndMotor.setControl(501, 20.0F);
        if (paramInt == 6) {
          this.sndMotor.setControl(500, 1.0F);
          this.bRunning = true;
        }
      }
      this.sndMotor.setControl(106, (paramInt > 1) && (paramInt < 4) ? 1.0F : 0.0F);
      this.sndMotor.setControl(112, paramInt);
    }
    this.prevState = paramInt;
  }

  public void update()
  {
    float f1 = this.motor.getRPM();
    if (this.sndMotor != null) {
      int i = this.motor.getType();
      int j = (i == 0) || (i == 1) ? 1 : 0;
      float f3 = this.motor.getReadyness();
      float f4 = f1;
      if (f3 < 0.0F) f3 = 0.0F;
      f3 = 1.0F - f3;
      if ((j != 0) && (this.prevState == 0)) this.bRunning = (f1 > 5.0F);
      if (this.bRunning) {
        if ((j == 0) && 
          (f1 < 60.0F) && (this.prevState == 0)) this.bRunning = false;

        if (f1 < 1200.0F) {
          this.sndMotor.setControl(501, f1 / 30.0F);
          this.sndMotor.setControl(502, f3 * 0.7F);
          if (f1 > 400.0F) this.sndMotor.setControl(503, 0.8F * (1200.0F - f1) / 800.0F); else
            this.sndMotor.setControl(503, 0.8F);
        } else {
          this.sndMotor.setControl(501, 0.0F);
        }
      } else {
        this.sndMotor.setControl(501, 0.0F);
      }
      float f5 = f1;
      if (f3 > 0.0F) f5 *= (1.0F - f3 * 0.15F * this.dmgRnd.nextFloat());
      this.sndMotor.setControl(100, f5);
      this.sndMotor.setControl(101, f3);
    }

    if (this.sndProp != null) {
      float f2 = this.motor.getPropRPM();
      this.sndProp.setControl(100, f2);
    }
  }
}
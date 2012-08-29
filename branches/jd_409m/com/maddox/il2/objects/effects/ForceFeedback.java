package com.maddox.il2.objects.effects;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.JoyFF;
import com.maddox.rts.JoyFF.Effect;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class ForceFeedback
{
  private static JoyFF.Effect[] triggerFX = new JoyFF.Effect[20];
  private static JoyFF.Effect punch;
  private static JoyFF.Effect shake;
  private static JoyFF.Effect spring;
  private static float shakeGain = 0.0F;
  private static float springGain = 1.0F;
  private static float springX = 0.0F;
  private static float springY = 0.0F;
  private static int[] triggerFXType = new int[20];
  private static long punchPrevTime = 0L;
  private static long shakePrevTime = 0L;
  private static final String pathToFXDir = "ForceFeedback/";
  private static final int FFT_NONE = 0;
  private static final int FFT_MACHINEGUN = 1;
  private static final int FFT_AUTOCANNON = 2;
  private static final int FFT_MORTAR = 3;
  private static final int FFT_ROCKET = 4;
  private static final int FFT_BOMB = 5;
  private static RangeRandom rnd = new RangeRandom();
  private static boolean bEnabled = false;

  public static void fxTriggerShake(int paramInt, boolean paramBoolean)
  {
    if (!bEnabled) return;
    if (!JoyFF.isStarted()) return;

    if (triggerFXType[paramInt] == 0) {
      World.cur(); BulletEmitter[] arrayOfBulletEmitter = World.getPlayerFM().CT.Weapons[paramInt];
      if (arrayOfBulletEmitter == null) return;
      for (int i = 0; i < arrayOfBulletEmitter.length; i++) {
        if (arrayOfBulletEmitter[i] != null) {
          if ((arrayOfBulletEmitter[i] instanceof GunGeneric)) {
            float f = ((GunGeneric)arrayOfBulletEmitter[i]).prop.shotFreq;
            if (f > 10.83F)
            {
              if (World.cur().isDebugFM()) System.out.println("Trigger " + paramInt + " assessed as MACHINEGUN..");
              triggerFX[paramInt] = new JoyFF.Effect("ForceFeedback/machinegun.ffe");
              triggerFXType[paramInt] = 1; break;
            }if (f > 4.16F)
            {
              if (World.cur().isDebugFM()) System.out.println("Trigger " + paramInt + " assessed as AUTOCANNON (" + (int)(f * 60.0F) + ")..");
              triggerFX[paramInt] = new JoyFF.Effect("ForceFeedback/autocannon.ffe");
              triggerFX[paramInt].setSamplePeriod((int)(1.0F / f) * 1000);
              triggerFXType[paramInt] = 2; break;
            }

            if (World.cur().isDebugFM()) System.out.println("Trigger " + paramInt + " assessed as MORTAR..");
            triggerFX[paramInt] = new JoyFF.Effect("ForceFeedback/mortar.ffe");
            triggerFXType[paramInt] = 3;

            break;
          }
          if ((arrayOfBulletEmitter[i] instanceof BombGun))
          {
            triggerFX[paramInt] = new JoyFF.Effect("ForceFeedback/bomb.ffe");
            triggerFXType[paramInt] = 5;
            break;
          }
          if (!(arrayOfBulletEmitter[i] instanceof RocketGun))
            continue;
          triggerFX[paramInt] = new JoyFF.Effect("ForceFeedback/bomb.ffe");
          triggerFXType[paramInt] = 4;
          break;
        }
      }
    }

    if (paramBoolean) {
      if (triggerFXType[paramInt] == 3) triggerFX[paramInt].stop();
      triggerFX[paramInt].start(1);
    } else {
      triggerFX[paramInt].stop();
    }
  }

  public static void fxPunch(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (!bEnabled) return;
    if (!JoyFF.isStarted()) return;
    long l = Time.real();
    if (l - punchPrevTime < 300L) return;
    punchPrevTime = l;
    punch.setDirection(paramFloat1, paramFloat2);
    punch.setGain(paramFloat3);
    punch.start(1);
  }

  public static void fxShake(float paramFloat) {
    if (!bEnabled) return;
    if (!JoyFF.isStarted()) return;
    long l = Time.real();
    if (l - shakePrevTime < 300L) return;
    shakePrevTime = l;
    if (Math.abs(paramFloat - shakeGain) > 0.025F) {
      shake.setGain(paramFloat);
      if (World.cur().isDebugFM()) System.out.println("Shake gain = " + paramFloat);
      shake.setDirection(rnd.nextFloat(-0.5F, 0.5F), rnd.nextFloat(-0.5F, 0.5F));
      if (shakeGain == 0.0F) shake.start(1);
      if (paramFloat < 0.05F) {
        shake.stop();
        paramFloat = 0.0F;
      }
      shakeGain = paramFloat;
    }
  }

  public static void fxSetSpringZero(float paramFloat1, float paramFloat2) {
    if (!bEnabled) return;
    if (!JoyFF.isStarted()) return;
    if ((Math.abs(springX - paramFloat1) > 0.1F) && (Math.abs(springY - paramFloat2) > 0.1F)) {
      spring.setOffset(paramFloat1, paramFloat2);
      springX = paramFloat1;
      springY = paramFloat2;
    }
  }

  public static void fxSetSpringGain(float paramFloat) {
    if (!bEnabled) return;
    if (!JoyFF.isStarted()) return;
    if (Math.abs(springGain - paramFloat) < 0.05F) return;
    springGain = paramFloat;
    spring.setCoefficient(paramFloat, paramFloat);
  }

  private static void startStaticEffects()
  {
    if (bEnabled) return;
    fxSetSpringGain(0.0F);
    fxSetSpringZero(0.0F, 0.0F);
    spring.start(1);
    fxShake(0.0F);
    for (int i = 0; i < triggerFXType.length; i++) {
      triggerFXType[i] = 0;
      if (triggerFX[i] != null) triggerFX[i].destroy();
      triggerFX[i] = null;
    }
    bEnabled = true;
  }
  private static void stopAllEffects() {
    if (!bEnabled) return;
    shake.stop();
    punch.stop();
    spring.stop();
    for (int i = 0; i < triggerFXType.length; i++) {
      if (triggerFX[i] == null) continue; triggerFX[i].stop();
    }
    bEnabled = false;
  }

  private static void loadAllEffects() {
    if (spring != null) return;
    punch = new JoyFF.Effect("ForceFeedback/punch.ffe");
    shake = new JoyFF.Effect("ForceFeedback/shake.ffe");
    spring = new JoyFF.Effect("ForceFeedback/spring.ffe");
  }

  public static void startMission() {
    if (!Config.isUSE_RENDER()) return;
    if (!JoyFF.isStarted()) return;
    startStaticEffects();
  }
  public static void stopMission() {
    if (!Config.isUSE_RENDER()) return;
    if (!JoyFF.isStarted()) return;
    stopAllEffects();
  }

  public static void start() {
    if (!Config.isUSE_RENDER()) return;
    if (JoyFF.isStarted()) return;
    if (!JoyFF.isEnable()) return;
    if (!JoyFF.isAttached()) return;
    JoyFF.start();
    JoyFF.setAutoCenter(false);
    loadAllEffects();
  }
  public static void stop() {
    if (!Config.isUSE_RENDER()) return;
    if (!JoyFF.isStarted()) return;
    JoyFF.stop();
  }
}
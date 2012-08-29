package com.maddox.il2.objects.sounds;

import com.maddox.JGP.Point3d;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;

public class SfxExplosion
{
  private static SoundPreset crashTank;
  private static SoundPreset crashAir;
  private static SoundPreset explodeZenitka;
  private static SoundPreset explodeBullet;
  private static SoundPreset explodeShell;
  private static SoundPreset explodeBig;
  private static SoundPreset explodeMiddle;

  private static boolean init()
  {
    if (crashTank != null)
      return true;
    try {
      crashTank = new SoundPreset("crash.tank");
      crashAir = new SoundPreset("crash.air");
      explodeZenitka = new SoundPreset("explode.zenitka");
      explodeBullet = new SoundPreset("explode.bullet");
      explodeShell = new SoundPreset("explode.shell");
      explodeBig = new SoundPreset("explode.big");
      explodeMiddle = new SoundPreset("explode.middle");
      return true; } catch (Exception localException) {
    }
    return false;
  }

  public static void crashTank(Point3d paramPoint3d, int paramInt)
  {
    if (!init()) return;
    SoundFX localSoundFX = new SoundFX(crashTank);
    localSoundFX.setPosition(paramPoint3d);
    localSoundFX.play();
  }

  public static void crashAir(Point3d paramPoint3d, int paramInt)
  {
    if (!init()) return;
    SoundFX localSoundFX = new SoundFX(crashAir);
    localSoundFX.setPosition(paramPoint3d);
    localSoundFX.setUsrFlag(paramInt);
    localSoundFX.play();
  }

  public static void crashParts(Point3d paramPoint3d, int paramInt)
  {
  }

  public static void zenitka(Point3d paramPoint3d, int paramInt)
  {
    if (!init()) return;

    SoundFX localSoundFX = new SoundFX(explodeZenitka);
    localSoundFX.setPosition(paramPoint3d);
    localSoundFX.setUsrFlag(paramInt > 1 ? 1 : 0);
    localSoundFX.play();
  }

  public static void shell(Point3d paramPoint3d, int paramInt1, float paramFloat1, int paramInt2, float paramFloat2)
  {
    if (!init()) return;

    if (paramFloat1 < 0.001F) return;
    SoundFX localSoundFX;
    if (paramFloat1 < 0.02F) localSoundFX = new SoundFX(explodeBullet);
    else if (paramFloat1 < 0.5F) localSoundFX = new SoundFX(explodeShell);
    else if (paramFloat1 < 50.0F) localSoundFX = new SoundFX(explodeMiddle); else
      localSoundFX = new SoundFX(explodeBig);
    localSoundFX.setPosition(paramPoint3d);
    localSoundFX.setUsrFlag(paramInt1);
    localSoundFX.play();
  }

  public static void building(Point3d paramPoint3d, int paramInt, float[] paramArrayOfFloat)
  {
  }

  public static void bridge(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat)
  {
  }

  public static void wagon(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat, int paramInt)
  {
  }
}
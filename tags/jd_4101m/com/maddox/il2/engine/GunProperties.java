package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Time;

public class GunProperties
{
  public int weaponType = 0;

  public boolean bCannon = false;

  public boolean bUseHookAsRel = false;
  public String fireMesh;
  public String fire;
  public String sprite;
  public String fireMeshDay;
  public String fireDay;
  public String spriteDay;
  public String smoke;
  public String shells;
  public String sound;
  public Color3f emitColor;
  public float emitI;
  public float emitR;
  public float emitTime;
  public float aimMinDist;
  public float aimMaxDist;
  public float maxDeltaAngle;
  public float shotFreq;
  public float shotFreqDeviation = 0.0F;
  public int traceFreq;
  public boolean bEnablePause = false;
  public int bullets;
  public int bulletsCluster;
  public BulletProperties[] bullet;

  public void calculateSteps()
  {
    if (this.emitTime <= Time.tickConstLenFs()) {
      this.emitTime = Time.tickConstLenFs();
    }
    if (this.aimMinDist < 0.0F) {
      this.aimMinDist = 10.0F;
    }
    if (this.aimMaxDist < 0.0F) {
      this.aimMaxDist = 1000.0F;
    }
    if (this.aimMinDist >= this.aimMaxDist) {
      this.aimMinDist = 10.0F;
      this.aimMaxDist = 1000.0F;
    }

    if (this.bulletsCluster < 1) {
      this.bulletsCluster = 1;
    }
    this.bulletsCluster = 1;

    this.traceFreq /= this.bulletsCluster;
    if (this.traceFreq <= 0)
      this.traceFreq = 1;
  }
}
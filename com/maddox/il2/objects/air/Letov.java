package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public abstract class Letov extends Scheme1
  implements TypeScout, TypeBomber, TypeTransport, TypeStormovik
{
  float lookoutTimeLeft = 2.0F;
  float lookoutAz = 0.0F;
  float lookoutEl = 0.0F;
  float lookoutAnim;
  float lookoutMax;
  float lookoutAzSpd;
  float lookoutElSpd;
  int lookoutIndex;
  float[][] lookoutPos = new float[3][''];
  private float turnPos;
  private float lookout;
  float turretLRpos;
  float turretFront;
  float turretLRposW;
  float[] turretPos = new float[3];
  float[] turretAng = new float[3];
  float[] tu = new float[3];
  float[] afBk = new float[3];
  protected float kangle;
  private int turretState;
  private static final int SLEEPING = 0;
  private static final int GOING_OUT_M = 1;
  private static final int GOING_OUT_PULL = 2;
  private static final int GOING_OUT_T = 3;
  private static final int ACTIVE = 4;
  private static final int GOING_IN_T = 5;
  private static final int GOING_IN_PULL = 6;
  private static final int GOING_IN_M = 7;
  private float fGunPos;
  private boolean finishedTurn;
  private long btme;
  private static float MAX_DANGLE = 100.0F;
  protected int gunOutOverride;
  boolean bGunnerKilled;

  public Letov()
  {
    this.turretPos[0] = 0.0F;
    this.turretPos[1] = 0.0F;
    this.turretPos[2] = 0.0F;
    this.turretAng[0] = 0.0F;
    this.turretAng[1] = 0.0F;
    this.turretAng[2] = 0.0F;
    this.turretLRposW = 0.0F;

    this.btme = -1L;
    this.fGunPos = 0.0F;
    this.turretState = 6;
    this.tu[0] = 0.0F;
    this.tu[1] = 0.0F;
    this.tu[2] = 0.0F;
    this.afBk[0] = 0.0F;
    this.afBk[1] = 0.0F;
    this.afBk[2] = 0.0F;
    this.finishedTurn = false;

    this.lookout = 0.0F;
    this.bGunnerKilled = false;

    this.gunOutOverride = 0;

    for (int i = 0; i < 128; i++)
    {
      this.lookoutPos[0][i] = (World.Rnd().nextFloat() * 180.0F - 90.0F);
      this.lookoutPos[1][i] = (World.Rnd().nextFloat() * 100.0F - 50.0F);

      if (this.lookoutPos[1][i] > 0.0F)
        this.lookoutPos[2][i] = (World.Rnd().nextFloat() * 2.0F);
      else
        this.lookoutPos[2][i] = ((World.Rnd().nextFloat() + World.Rnd().nextFloat() + World.Rnd().nextFloat() + World.Rnd().nextFloat()) * 3.0F);
    }
  }

  public boolean cut(String paramString)
  {
    boolean bool = super.cut(paramString);
    if (paramString.equalsIgnoreCase("WingLIn"))
      hierMesh().chunkVisible("WingLMid_CAP", true);
    else if (paramString.equalsIgnoreCase("WingRIn"))
      hierMesh().chunkVisible("WingRMid_CAP", true);
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 19:
      this.FM.Gears.hitCentreGear();
      break;
    case 3:
      if (World.Rnd().nextInt(0, 99) < 1)
      {
        this.FM.AS.hitEngine(this, 0, 4);
        hitProp(0, paramInt2, paramActor);
        this.FM.EI.engines[0].setEngineStuck(paramActor);
        return cut("engine1");
      }

      this.FM.AS.setEngineDies(this, 0);
      return false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
  }

  protected void moveFlap(float paramFloat)
  {
  }

  protected void moveRudder(float paramFloat) {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -29.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35.0F * paramFloat, 0.0F);

    float f = this.FM.CT.getAileron();
    hierMesh().chunkSetAngles("pilot1_arm2_d0", cvt(f, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(paramFloat, -1.0F, 1.0F, -37.0F, 35.0F));

    hierMesh().chunkSetAngles("pilot1_arm1_d0", 0.0F, 0.0F, cvt(f, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(paramFloat, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(paramFloat, 0.0F, 1.0F, 0.0F, 43.0F));

    if (paramFloat < 0.0F) {
      paramFloat /= 2.0F;
    }
    hierMesh().chunkSetAngles("Stick_D0", 0.0F, 15.0F * f, cvt(paramFloat, -1.0F, 1.0F, -16.0F, 20.0F));
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL1_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneL2_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneL3_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR1_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR2_D0", 0.0F, -35.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR3_D0", 0.0F, -35.0F * paramFloat, 0.0F);

    float f = this.FM.CT.getElevator();
    hierMesh().chunkSetAngles("pilot1_arm2_d0", cvt(paramFloat, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(paramFloat, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f, -1.0F, 1.0F, -37.0F, 35.0F));

    hierMesh().chunkSetAngles("pilot1_arm1_d0", 0.0F, 0.0F, cvt(paramFloat, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f, 0.0F, 1.0F, 0.0F, 43.0F));

    if (f < 0.0F) {
      f /= 2.0F;
    }
    hierMesh().chunkSetAngles("Stick_D0", 0.0F, 15.0F * paramFloat, cvt(f, -1.0F, 1.0F, -16.0F, 20.0F));
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilot1_arm2_d0", false);
    hierMesh().chunkVisible("pilot1_arm1_d0", false);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    default:
      break;
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("pilot1_arm2_d0", false);
      hierMesh().chunkVisible("pilot1_arm1_d0", false);
      break;
    case 1:
      this.bGunnerKilled = true;
      this.FM.turret[0].bIsOperable = false;
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Head2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
    }
  }

  private void moveAndTurnTurret(float[] paramArrayOfFloat)
  {
    float f = Math.abs(paramArrayOfFloat[0]);

    if (!this.bGunnerKilled)
    {
      this.turretAng[1] = 0.0F;
      this.turretAng[2] = 0.0F;
      this.turretPos[0] = 0.0F;
      this.turretPos[2] = 0.0F;

      if (this.turretFront > 0.5F)
      {
        if (paramArrayOfFloat[0] < 0.0F)
          this.turretAng[0] = ((720.0F - paramArrayOfFloat[0]) / 5.0F + this.turretLRpos * 25.0F);
        else {
          this.turretAng[0] = ((-720.0F - paramArrayOfFloat[0]) / 5.0F + this.turretLRpos * 25.0F);
        }
        this.turretPos[1] = (-0.6F + 0.2F * (float)Math.sin(Math.toRadians(f)));
        this.turretAng[2] = 20.0F;
      }
      else
      {
        this.turretAng[0] = (-paramArrayOfFloat[0] / 5.0F - this.turretLRpos * 25.0F);
        this.turretPos[1] = (-0.4F * (float)Math.sin(Math.toRadians(f)));
      }

      hierMesh().chunkSetLocate("Pilot2_D0", this.turretPos, this.turretAng);
    }

    this.turretAng[0] = 0.0F;
    this.turretAng[1] = 0.0F;
    this.turretAng[2] = 0.0F;

    this.turretPos[2] = 0.0F;
    this.turretPos[1] = (-0.6F * this.turretFront);
    this.turretPos[0] = 0.0F;
    hierMesh().chunkSetLocate("tyc", this.turretPos, this.turretAng);

    this.turretPos[1] = 0.0F;
    this.turretPos[0] = (0.35F * this.turretLRpos);
    hierMesh().chunkSetLocate("buben", this.turretPos, this.turretAng);

    hierMesh().chunkSetAngles("Turret1A_D0X", -paramArrayOfFloat[0], 0.0F, 0.0F);

    hierMesh().chunkSetAngles("Turret1B_D0X", 0.0F, paramArrayOfFloat[1], 0.0F);
  }

  private boolean turretMoves(float[] paramArrayOfFloat)
  {
    int i = 1;
    float f1 = Math.abs(paramArrayOfFloat[0]);

    if ((paramArrayOfFloat[0] == 0.0F) && (paramArrayOfFloat[1] == 0.0F)) {
      this.turretLRposW = 0.0F;
    }
    else {
      this.turretLRposW = (float)Math.cos(Math.toRadians(f1));
      this.turretLRposW *= this.turretLRposW;
      this.turretLRposW *= this.turretLRposW;
      this.turretLRposW *= this.turretLRposW;

      if (paramArrayOfFloat[0] < 0.0F)
        this.turretLRposW -= 1.0F;
      else {
        this.turretLRposW = (1.0F - this.turretLRposW);
      }
      if ((this.turretState < 5) && (((paramArrayOfFloat[1] > -25.0F) && (f1 < 20.0F)) || (paramArrayOfFloat[1] > -5.0F)))
      {
        if (paramArrayOfFloat[0] < 0.0F)
        {
          this.turretLRposW = -1.0F;
        }
        else this.turretLRposW = 1.0F;
      }
    }

    if (this.turretLRposW < -1.0F) {
      this.turretLRposW = -1.0F;
    }
    if (this.turretLRposW > 1.0F) {
      this.turretLRposW = 1.0F;
    }

    this.turretLRpos = (0.95F * this.turretLRpos + 0.05F * this.turretLRposW);

    float f2 = 0.0F;

    if (f1 > 100.0F - 20.0F * this.turretFront) {
      f2 = 1.0F;
    }
    if (this.turretFront < 0.0F)
      this.turretFront = 0.0F;
    else if (this.turretFront > 1.0F) {
      this.turretFront = 1.0F;
    }
    if ((f2 - this.turretFront > 0.01D) || (f2 - this.turretFront < -0.01D))
    {
      if (f2 > this.turretFront)
        this.turretFront += 0.15F * (0.3F - (0.5F - this.turretFront) * (0.5F - this.turretFront));
      else {
        this.turretFront -= 0.15F * (0.3F - (0.5F - this.turretFront) * (0.5F - this.turretFront));
      }
      if ((f2 - this.turretFront > 0.1D) || (f2 - this.turretFront < -0.1D))
      {
        i = 0;
      }
    }
    return i;
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    while (paramArrayOfFloat[0] > 180.0F) {
      paramArrayOfFloat[0] -= 360.0F;
    }
    while (paramArrayOfFloat[0] < -180.0F) {
      paramArrayOfFloat[0] += 360.0F;
    }

    float f1 = Math.abs(paramArrayOfFloat[0]);

    if (paramArrayOfFloat[1] < -50.0F)
    {
      paramArrayOfFloat[1] = -50.0F;
      bool = false;
    }
    float f2;
    if (f1 < 7.0F)
    {
      if (f1 < 5.0F)
        f2 = -2.0F;
      else
        f2 = f1 * 7.5F - 39.5F;
    }
    else if (f1 < 55.0F)
      f2 = f1 + 6.0F;
    else if (f1 < 95.0F)
      f2 = 50.0F + f1 / 5.0F;
    else if (f1 < 155.0F) {
      f2 = 206.7F - 1.46F * f1;
    }
    else {
      f2 = -20.0F;
    }
    if (paramArrayOfFloat[1] > f2)
    {
      paramArrayOfFloat[1] = f2;
      bool = false;
    }

    if ((f1 < 34.0F) && 
      (paramArrayOfFloat[1] < 3.0F) && (paramArrayOfFloat[1] > -3.0F)) {
      bool = false;
    }

    if (f1 > 100.0F)
    {
      if (paramArrayOfFloat[1] > -20.0F)
        bool = false;
    }
    else if ((f1 > 90.0F) && (paramArrayOfFloat[1] > 12.0F)) {
      bool = false;
    }

    this.afBk[0] = paramArrayOfFloat[0];
    this.afBk[1] = paramArrayOfFloat[1];

    if (this.turretState != 4) {
      return false;
    }

    bool &= turretMoves(paramArrayOfFloat);

    moveAndTurnTurret(paramArrayOfFloat);

    return bool;
  }

  private boolean anglesMoveTo(float[] paramArrayOfFloat, float paramFloat)
  {
    float f2 = paramFloat * MAX_DANGLE;
    this.finishedTurn = true;
    float f1 = paramArrayOfFloat[0] - this.tu[0];

    while (f1 < -180.0F) {
      f1 += 360.0F;
    }
    while (f1 > 180.0F) {
      f1 -= 360.0F;
    }
    if (f1 > f2)
    {
      f1 = f2;
      this.finishedTurn = false;
    }

    if (f1 < -f2)
    {
      f1 = -f2;
      this.finishedTurn = false;
    }

    this.tu[0] += f1;

    f1 = paramArrayOfFloat[1] - this.tu[1];

    while (f1 < -180.0F) {
      f1 += 360.0F;
    }
    while (f1 > 180.0F) {
      f1 -= 360.0F;
    }
    if (f1 > f2)
    {
      f1 = f2;
      this.finishedTurn = false;
    }

    if (f1 < -f2)
    {
      f1 = -f2;
      this.finishedTurn = false;
    }

    this.tu[1] += f1;

    return this.finishedTurn;
  }

  private void gunStoreMoveAnim()
  {
    float f = this.fGunPos * this.fGunPos * (3.0F - 2.0F * this.fGunPos);
    hierMesh().chunkSetAngles("buben", 0.0F, 0.0F, 70.0F * (1.0F - f));

    if (this.fGunPos > 0.8F) {
      hierMesh().chunkSetAngles("Turret1B_D0X", 0.0F, 0.0F, 0.0F);
    }
    else {
      f = 1.0F - this.fGunPos * 1.25F;
      hierMesh().chunkSetAngles("Turret1B_D0X", 0.0F, 70.0F * f * f * (2.0F * f - 3.0F), 0.0F);
    }
  }

  private void setGunnerSitting(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.turretAng[0] = 180.0F;
      this.turretAng[1] = 0.0F;
      this.turretAng[2] = 15.0F;
      this.turretPos[0] = 0.0F;
      this.turretPos[1] = -0.3F;
      this.turretPos[2] = -0.15F;
      hierMesh().chunkSetLocate("Pilot2_D0", this.turretPos, this.turretAng);

      this.lookoutAz = 0.0F;
      this.lookoutEl = 0.0F;
      turnHead(this.lookoutAz, this.lookoutEl);
      this.lookoutAnim = -1.0F;
      this.lookoutTimeLeft = -1.0F;
    }
  }

  private boolean gunStoreTurnAnim(float[] paramArrayOfFloat, float paramFloat)
  {
    int i;
    if (anglesMoveTo(paramArrayOfFloat, paramFloat))
      i = 1;
    else {
      i = 0;
    }

    turretMoves(this.tu);
    moveAndTurnTurret(this.tu);

    return i;
  }

  private void turnHead(float paramFloat1, float paramFloat2)
  {
    this.turretAng[0] = 0.0F;
    this.turretAng[1] = paramFloat1;
    this.turretAng[2] = 0.0F;
    this.turretPos[0] = (0.3F * (1.0F - (float)Math.cos(Math.toRadians(paramFloat1))));

    this.turretPos[1] = 0.0F;
    this.turretPos[2] = (-0.28F * (float)Math.sin(Math.toRadians(paramFloat1)));

    hierMesh().chunkSetLocate("Head2_D0", this.turretPos, this.turretAng);
  }

  void gunnerLookout(float paramFloat)
  {
    this.lookoutTimeLeft -= paramFloat;

    if (this.lookoutTimeLeft > 0.0F) {
      return;
    }
    if (this.lookoutAnim < 0.0F)
    {
      this.lookoutIndex += 1;

      if (this.lookoutIndex > 127) {
        this.lookoutIndex = 0;
      }

      this.lookoutTimeLeft = this.lookoutPos[2][this.lookoutIndex];

      f1 = this.lookoutPos[0][this.lookoutIndex] - this.lookoutAz;
      float f2 = this.lookoutPos[1][this.lookoutIndex] - this.lookoutEl;
      this.lookoutAnim = 0.001F;
      this.lookout = 0.0F;

      if (f1 * f1 > f2 * f2)
        this.lookoutMax = Math.abs(f1);
      else {
        this.lookoutMax = Math.abs(f2);
      }
      if (this.lookoutMax == 0.0F) {
        this.lookoutMax = 1.0E-005F;
      }
      this.lookoutAzSpd = (f1 / this.lookoutMax);
      this.lookoutElSpd = (f2 / this.lookoutMax);

      return;
    }

    if (2.0F * this.lookout > this.lookoutMax)
      this.lookoutAnim -= paramFloat;
    else {
      this.lookoutAnim += paramFloat;
    }
    float f1 = 1.0F - 5.0F / (this.lookoutAnim + 5.0F);
    f1 = paramFloat * f1 * 800.0F;
    this.lookoutAz += f1 * this.lookoutAzSpd;
    this.lookoutEl += f1 * this.lookoutElSpd;
    this.lookout += f1;

    turnHead(this.lookoutAz, this.lookoutEl);
  }

  private void gunnerTurnInit(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.turnPos = 1.0F;
    }
    else {
      this.lookoutAz = 0.0F;
      this.lookoutEl = 0.0F;
      this.turnPos = 0.0F;
    }
  }

  private void gunnerTurn(float paramFloat)
  {
    float f = paramFloat * paramFloat * (3.0F - 2.0F * paramFloat);
    turnHead(f * this.lookoutAz, f * this.lookoutEl);

    if (this.lookoutAz > 0.0F)
      this.turretAng[0] = (f * 180.0F);
    else
      this.turretAng[0] = (-f * 180.0F);
    this.turretAng[1] = 0.0F;
    this.turretAng[2] = (f * 15.0F);
    this.turretPos[0] = 0.0F;
    this.turretPos[1] = (-f * 0.3F);
    this.turretPos[2] = (-f * 0.15F);
    hierMesh().chunkSetLocate("Pilot2_D0", this.turretPos, this.turretAng);
  }

  public void update(float paramFloat)
  {
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());

    if (this.kangle > 1.0F) {
      this.kangle = 1.0F;
    }
    hierMesh().chunkSetAngles("radiator1_D0", 0.0F, -55.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("radiator2_D0", 0.0F, -40.0F * this.kangle, 0.0F);

    if ((!this.bGunnerKilled) && (!this.FM.AS.isPilotParatrooper(this.FM.AS.astatePilotStates[1])))
    {
      if ((this.FM.AS.isMaster()) || (NetMissionTrack.isPlaying()))
      {
        if ((this.turretState == 0) && ((this.gunOutOverride == 1) || (this.FM.turret[0].target != null)))
        {
          this.turretState = 1;
          setGunnerSitting(false);
          gunnerTurnInit(true);
        }

        if (Time.current() > this.btme)
        {
          this.btme = (Time.current() + World.Rnd().nextLong(5000L, 12000L));

          if ((this.FM.turret[0].target == null) && (this.turretState == 4) && (this.gunOutOverride == 0))
          {
            this.turretState = 5;
            this.tu[0] = this.afBk[0];
            this.tu[1] = this.afBk[1];
            this.afBk[0] = 0.0F;
            this.afBk[1] = 0.0F;
          }
        }
      }

      switch (this.turretState)
      {
      case 0:
        gunnerLookout(paramFloat);
        break;
      case 1:
        this.turnPos -= paramFloat;
        if (this.turnPos < 0.0F)
        {
          this.turretState = 2;
          this.turnPos = 0.0F;
        }

        gunnerTurn(this.turnPos);

        break;
      case 2:
        this.fGunPos += 1.0F * paramFloat;
        gunStoreMoveAnim();

        if (this.fGunPos <= 0.999D) break;
        this.turretState = 3; break;
      case 3:
        if (!gunStoreTurnAnim(this.afBk, paramFloat)) break;
        this.turretState = 4; break;
      case 4:
        break;
      case 5:
        if (!gunStoreTurnAnim(this.afBk, paramFloat)) break;
        this.turretState = 6; break;
      case 6:
        if ((this.turretLRpos < 0.1F) && (this.turretLRpos > -0.1F))
        {
          this.fGunPos -= 1.0F * paramFloat;
          gunStoreMoveAnim();
        }
        else {
          gunStoreTurnAnim(this.afBk, paramFloat);
        }
        if (this.fGunPos >= 0.001D)
          break;
        this.turretState = 7;
        setGunnerSitting(true);
        gunnerTurnInit(false); break;
      case 7:
        this.turnPos += paramFloat;
        if (this.turnPos > 1.0F)
        {
          this.turretState = 0;
          this.turnPos = 1.0F;
        }

        gunnerTurn(this.turnPos);
      }

    }

    super.update(paramFloat);
  }

  public void missionStarting()
  {
    super.missionStarting();
    hierMesh().chunkVisible("pilot1_arm2_d0", true);
    hierMesh().chunkVisible("pilot1_arm1_d0", true);
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilot1_arm2_d0", true);
    hierMesh().chunkVisible("pilot1_arm1_d0", true);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        if (paramString.endsWith("p1")) {
          getEnergyPastArmor(9.96F / (1.0E-005F + (float)Math.abs(Aircraft.v1.x)), paramShot);
        }

        return;
      }

      if (paramString.startsWith("xxcontrols"))
      {
        if (paramString.endsWith("7")) {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);

            Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#7)");
          }
        }
        else if (paramString.endsWith("8"))
        {
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);

            Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#8)");
          }
          if (World.Rnd().nextFloat() < 0.2F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 0);

            Aircraft.debugprintln(this, "*** Arone Controls Out.. (#8)");
          }
        }
        else if (paramString.endsWith("5")) {
          if (World.Rnd().nextFloat() < 0.5F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);

            Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#5)");
          }
        }
        else if (paramString.endsWith("6")) {
          if (World.Rnd().nextFloat() < 0.5F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);

            Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#6)");
          }
        }
        else if (((paramString.endsWith("2")) || (paramString.endsWith("4"))) && 
          (World.Rnd().nextFloat() < 0.5F))
        {
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);

          Aircraft.debugprintln(this, "*** Arone Controls Out.. (#2/#4)");
        }

        return;
      }

      if ((paramString.startsWith("xxeng")) || (paramString.startsWith("xxEng")))
      {
        Aircraft.debugprintln(this, "*** Engine Module: Hit..");

        if (paramString.endsWith("prop")) {
          Aircraft.debugprintln(this, "*** Prop hit");
        } else if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(2.1F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 175000.0F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
            }

            if (World.Rnd().nextFloat() < paramShot.power / 50000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
              Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }

            this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 48000.0F));

            Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
          }

          getEnergyPastArmor(12.7F, paramShot);
        }
        else if (paramString.startsWith("xxeng1cyls"))
        {
          if ((getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 4.4F), paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 1.12F))
          {
            this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));

            Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

            if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, 0, 3);
              Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
            }

            if (World.Rnd().nextFloat() < 0.005F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, 0);
              Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
            }

            getEnergyPastArmor(22.5F, paramShot);
          }
        }
        else if (paramString.endsWith("Oil1"))
        {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
        }
        return;
      }
      if (paramString.startsWith("xxoil"))
      {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        getEnergyPastArmor(0.22F, paramShot);
        Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
      }
      else if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';

        if ((getEnergyPastArmor(0.4F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.99F))
        {
          if (this.FM.AS.astateTankStates[i] == 0)
          {
            Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
          }

          if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.25F))
          {
            this.FM.AS.hitTank(paramShot.initiator, i, 2);
            Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
          }
        }
      }
      else if (paramString.startsWith("xxlock"))
      {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");

        if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }

      }
      else if (paramString.startsWith("xxmgun"))
      {
        if (paramString.endsWith("01"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #1: Disabled..");
          this.FM.AS.setJamBullets(0, 0);
        }

        if (paramString.endsWith("02"))
        {
          Aircraft.debugprintln(this, "*** Fixed Gun #2: Disabled..");
          this.FM.AS.setJamBullets(0, 1);
        }

        getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
      }
      else if (paramString.startsWith("xxspar"))
      {
        Aircraft.debugprintln(this, "*** Spar Construction: Hit..");

        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(9.5F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparli")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxspar2i")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparlm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparrm")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparlo")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), paramShot.initiator);
        }
        else if ((paramString.startsWith("xxsparro")) && (World.Rnd().nextFloat() < 0.25F) && (getEnergyPastArmor(9.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), paramShot.initiator);
        }

      }

      return;
    }

    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      i = 0;
      int j;
      if (paramString.endsWith("a"))
      {
        i = 1;
        j = paramString.charAt(6) - '1';
      }
      else if (paramString.endsWith("b"))
      {
        i = 2;
        j = paramString.charAt(6) - '1';
      }
      else {
        j = paramString.charAt(5) - '1';
      }
      Aircraft.debugprintln(this, "*** hitFlesh..");
      hitFlesh(j, paramShot, i);
    }
    else if ((paramString.startsWith("xcf")) || (paramString.startsWith("xcockpit")))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xturret1b"))
    {
      Aircraft.debugprintln(this, "*** Turret Gun: Disabled.. (xturret1b)");
      this.FM.AS.setJamBullets(10, 0);
      getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), paramShot);
    }
    else if (paramString.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel"))
    {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xrudder"))
    {
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstab"))
    {
      if ((paramString.startsWith("xstabl")) && (chunkDamageVisible("StabL") < 2)) {
        hitChunk("StabL", paramShot);
      }
      if ((paramString.startsWith("xstabr")) && (chunkDamageVisible("StabR") < 2))
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvator"))
    {
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1)) {
        hitChunk("VatorL", paramShot);
      }
      if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
        hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xWing"))
    {
      Aircraft.debugprintln(this, "*** xWing: " + paramString);

      if ((paramString.startsWith("xWingLIn")) && (chunkDamageVisible("WingLIn") < 3)) {
        hitChunk("WingLIn", paramShot);
      }
      if ((paramString.startsWith("xWingRIn")) && (chunkDamageVisible("WingRIn") < 3)) {
        hitChunk("WingRIn", paramShot);
      }
      if ((paramString.startsWith("xWingLmid")) && (chunkDamageVisible("WingLMid") < 3)) {
        hitChunk("WingLMid", paramShot);
      }
      if ((paramString.startsWith("xWingRmid")) && (chunkDamageVisible("WingRMid") < 3))
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwing"))
    {
      Aircraft.debugprintln(this, "*** xwing: " + paramString);

      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3)) {
        hitChunk("WingLOut", paramShot);
      }
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xarone"))
    {
      if ((paramString.startsWith("xaronel")) && (chunkDamageVisible("AroneL") < 1))
      {
        hitChunk("AroneL1", paramShot);
        hitChunk("AroneL2", paramShot);
      }

      if ((paramString.startsWith("xaroner")) && (chunkDamageVisible("AroneR") < 1))
      {
        hitChunk("AroneR1", paramShot);
        hitChunk("AroneR2", paramShot);
      }
    }
  }

  static
  {
    Class localClass = Letov.class;
    Property.set(localClass, "originCountry", PaintScheme.countrySlovakia);
  }
}
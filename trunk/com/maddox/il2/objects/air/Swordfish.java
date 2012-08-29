package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class Swordfish extends Scheme1
  implements TypeBomber, TypeStormovik, TypeScout
{
  public boolean bPitUnfocused = true;
  boolean bIsWingTornOff = false;
  public float airBrakePos = 0.0F;

  private Maneuver maneuver = null;
  protected float arrestor = 0.0F;

  float obsLookoutTimeLeft = 2.0F;
  float obsLookoutAz = 0.0F;
  float obsLookoutEl = 0.0F;
  float obsLookoutAnim;
  float obsLookoutMax;
  float obsLookoutAzSpd;
  float obsLookoutElSpd;
  int obsLookoutIndex;
  float[][] obsLookoutPos = new float[3][''];
  private float obsLookout;
  private float wheel1 = 0.0F;
  private float wheel2 = 0.0F;
  private float slat = 0.0F;
  private int noenemy = 0;
  private int wait = 0;

  private int obsLookTime = 0;
  private float obsLookAzimuth = 0.0F;
  private float obsLookElevation = 0.0F;
  private float obsAzimuth = 0.0F;
  private float obsElevation = 0.0F;
  private float obsAzimuthOld = 0.0F;
  private float obsElevationOld = 0.0F;
  private float obsMove = 0.0F;
  private float obsMoveTot = 0.0F;

  private int TAGLookTime = 0;
  private float TAGLookAzimuth = 0.0F;
  private float TAGLookElevation = 0.0F;
  private float TAGAzimuth = 0.0F;
  private float TAGElevation = 0.0F;
  private float TAGAzimuthOld = 0.0F;
  private float TAGElevationOld = 0.0F;
  private float TAGMove = 0.0F;
  private float TAGMoveTot = 0.0F;
  boolean bTAGKilled;
  boolean bObserverKilled;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    hierMesh().chunkSetAngles("TurrBase_D0", 0.0F, 70.0F, 0.0F);
    hierMesh().chunkSetAngles("TurrBase1_D0", 70.0F, 0.0F, 0.0F);

    this.FM.Gears.computePlaneLandPose(this.FM);
    mydebuggunnery("H = " + this.FM.Gears.H);
    mydebuggunnery("Pitch = " + this.FM.Gears.Pitch);

    if (this.thisWeaponsName.startsWith("1_"))
    {
      hierMesh().chunkVisible("Torpedo_Support_D0", true);
      return;
    }

    if (this.thisWeaponsName.startsWith("2_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0", true);
      hierMesh().chunkVisible("500lbWingRackL_D0", true);
      hierMesh().chunkVisible("500lbWingRackR_D0", true);
      return;
    }

    if (this.thisWeaponsName.startsWith("3_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0 ", true);
      hierMesh().chunkVisible("250lbWingRackL01_D0", true);
      hierMesh().chunkVisible("250lbWingRackL02_D0", true);
      hierMesh().chunkVisible("250lbWingRackR01_D0", true);
      hierMesh().chunkVisible("250lbWingRackR02_D0", true);
      return;
    }

    if (this.thisWeaponsName.startsWith("4_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0", true);
      hierMesh().chunkVisible("FlareWingRackL_D0", true);
      hierMesh().chunkVisible("FlareWingRackR_D0", true);
    }

    if (this.thisWeaponsName.startsWith("5_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0", true);
      hierMesh().chunkVisible("500lbWingRackL_D0", true);
      hierMesh().chunkVisible("500lbWingRackR_D0", true);
      hierMesh().chunkVisible("FlareWingRackL_D0", true);
      hierMesh().chunkVisible("FlareWingRackR_D0", true);
    }

    if (this.thisWeaponsName.startsWith("6_"))
    {
      hierMesh().chunkVisible("Torpedo_Support_D0", true);
      hierMesh().chunkVisible("FlareWingRackL_D0", true);
      hierMesh().chunkVisible("FlareWingRackR_D0", true);
    }

    if (this.thisWeaponsName.startsWith("7_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0 ", true);
      hierMesh().chunkVisible("250lbWingRackL01_D0", true);
      hierMesh().chunkVisible("250lbWingRackL02_D0", true);
      hierMesh().chunkVisible("250lbWingRackR01_D0", true);
      hierMesh().chunkVisible("250lbWingRackR02_D0", true);
      hierMesh().chunkVisible("FlareWingRackL_D0", true);
      hierMesh().chunkVisible("FlareWingRackR_D0", true);
    }

    if (this.thisWeaponsName.startsWith("8_"))
    {
      hierMesh().chunkVisible("500lbWingRackC_D0 ", true);
      hierMesh().chunkVisible("FlareWingRackL_D0", true);
      hierMesh().chunkVisible("FlareWingRackR_D0", true);
    }
  }

  public Swordfish()
  {
    this.bTAGKilled = false;
    this.bObserverKilled = false;
  }

  public void sfxAirBrake()
  {
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    case 2:
      mydebuggunnery("pilot[2] killed - turret[0] inoperable ");
      this.FM.turret[0].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Head2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      this.bObserverKilled = true;
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3up_D0", false);
      hierMesh().chunkVisible("Head3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      this.bTAGKilled = true;
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (!this.bTAGKilled)
    {
      War localWar = War.cur();

      Actor localActor = War.GetNearestEnemy(this, 16, 6000.0F);
      Aircraft localAircraft = War.getNearestEnemy(this, 5000.0F);
      if (((localActor != null) && (!(localActor instanceof BridgeSegment))) || (localAircraft != null))
      {
        this.noenemy = 0;

        if (this.FM.CT.AirBrakeControl < 0.01F)
        {
          this.wait = World.Rnd().nextInt(0, 30);
          this.FM.CT.AirBrakeControl = 1.0F;
        }

      }
      else
      {
        this.noenemy += 1;
        if ((this.noenemy > 30 + this.wait) && 
          (this.FM.CT.AirBrakeControl > 0.99F))
        {
          this.FM.CT.AirBrakeControl = 0.0F;
        }

      }

    }

    if (!this.bObserverKilled)
    {
      if (this.obsLookTime == 0)
      {
        this.obsLookTime = (2 + World.Rnd().nextInt(1, 3));
        this.obsMoveTot = (1.0F + World.Rnd().nextFloat() * 1.5F);
        this.obsMove = 0.0F;

        this.obsAzimuthOld = this.obsAzimuth;
        this.obsElevationOld = this.obsElevation;
        if (World.Rnd().nextFloat() > 0.8D)
        {
          this.obsAzimuth = 0.0F;
          this.obsElevation = 0.0F;
        }
        else
        {
          this.obsAzimuth = (World.Rnd().nextFloat() * 140.0F - 70.0F);
          this.obsElevation = (World.Rnd().nextFloat() * 50.0F - 20.0F);
        }

      }
      else
      {
        this.obsLookTime -= 1;
      }

    }

    if (!this.bTAGKilled)
    {
      if (this.TAGLookTime == 0)
      {
        this.TAGLookTime = (2 + World.Rnd().nextInt(1, 3));
        this.TAGMoveTot = (1.0F + World.Rnd().nextFloat() * 1.5F);
        this.TAGMove = 0.0F;

        this.TAGAzimuthOld = this.TAGAzimuth;
        this.TAGElevationOld = this.TAGElevation;
        if (World.Rnd().nextFloat() > 0.8D)
        {
          this.TAGAzimuth = 0.0F;
          this.TAGElevation = 0.0F;
        }
        else
        {
          this.TAGAzimuth = (World.Rnd().nextFloat() * 140.0F - 70.0F);
          this.TAGElevation = (World.Rnd().nextFloat() * 50.0F - 20.0F);
        }

      }
      else
      {
        this.TAGLookTime -= 1;
      }

    }

    if (this.FM.getAltitude() < 3000.0F)
    {
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
    }
    else
    {
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
      hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
      hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
    }
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F)) {
        this.FM.AS.hitTank(this, 0, 1);
      }
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }
  }

  public void moveAirBrake(float paramFloat)
  {
    this.airBrakePos = paramFloat;
    if (this.bTAGKilled) {
      return;
    }
    hierMesh().chunkSetAngles("TurrBase_D0", 0.0F, 70.0F * (1.0F - paramFloat), 0.0F);
    hierMesh().chunkSetAngles("TurrBase1_D0", 70.0F * (1.0F - paramFloat), 0.0F, 0.0F);

    this.noenemy = 0;
    this.wait = World.Rnd().nextInt(0, 30);

    if (paramFloat > 0.99D)
    {
      resetYPRmodifier();
      hierMesh().chunkSetLocate("Pilot3_D0", Aircraft.xyz, Aircraft.ypr);
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Head3_D0", false);
      hierMesh().chunkVisible("Pilot3up_D0", true);
    }
    else
    {
      if (!hierMesh().isChunkVisible("Pilot3_D0"))
      {
        hierMesh().chunkVisible("Pilot3_D0", true);
        hierMesh().chunkVisible("Head3_D0", true);
        hierMesh().chunkVisible("Pilot3up_D0", false);
      }
      resetYPRmodifier();
      Aircraft.xyz[2] = (0.45F * paramFloat);
      hierMesh().chunkSetLocate("Pilot3_D0", Aircraft.xyz, Aircraft.ypr);

      this.FM.turret[0].tu[0] = 0.0F;
      this.FM.turret[0].tu[1] = 0.0F;
    }
  }

  public void moveSteering(float paramFloat)
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    this.wheel1 = (0.8F * this.wheel1 + 0.2F * this.FM.Gears.gWheelSinking[0]);
    this.wheel2 = (0.8F * this.wheel2 + 0.2F * this.FM.Gears.gWheelSinking[1]);

    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(this.wheel1, 0.0F, 0.04F, 0.0F, 9.0F), 0.0F);

    hierMesh().chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(this.wheel1, 0.0F, 0.04F, 0.0F, 5.0F), 0.0F);

    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(this.wheel2, 0.0F, 0.04F, 0.0F, -9.0F), 0.0F);

    hierMesh().chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(this.wheel2, 0.0F, 0.04F, 0.0F, -5.0F), 0.0F);
  }

  public void update(float paramFloat)
  {
    float f2 = 0.0F;

    Controls localControls = this.FM.CT;
    float f3 = localControls.getFlap();

    if (this.FM.CT.getArrestor() > 0.2F)
    {
      if (this.FM.Gears.arrestorVAngle != 0.0F)
      {
        f4 = Aircraft.cvt(this.FM.Gears.arrestorVAngle, -26.0F, 11.0F, 1.0F, 0.0F);

        this.arrestor = (0.8F * this.arrestor + 0.2F * f4);
        moveArrestorHook(this.arrestor);
      }
      else {
        f4 = -42.0F * this.FM.Gears.arrestorVSink / 37.0F;
        if ((f4 < 0.0F) && (this.FM.getSpeedKMH() > 50.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }

        if ((f4 > 0.0F) && (this.FM.CT.getArrestor() < 0.95F))
          f4 = 0.0F;
        if (f4 > 0.0F)
          this.arrestor = (0.7F * this.arrestor + 0.3F * (this.arrestor + f4));
        else
          this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f4));
        if (this.arrestor < 0.0F)
          this.arrestor = 0.0F;
        else if (this.arrestor > 1.0F)
          this.arrestor = 1.0F;
        moveArrestorHook(this.arrestor);
      }

    }

    float f1 = localControls.getAileron();
    float f4 = -(f1 * 30.0F + f3 * 17.0F);
    hierMesh().chunkSetAngles("AroneL1_D0", 0.0F, f4, 0.0F);
    hierMesh().chunkSetAngles("AroneL2_D0", 0.0F, f4, 0.0F);

    f4 = -(f1 * 30.0F - f3 * 17.0F);
    hierMesh().chunkSetAngles("AroneR1_D0", 0.0F, f4, 0.0F);
    hierMesh().chunkSetAngles("AroneR2_D0", 0.0F, f4, 0.0F);

    resetYPRmodifier();
    if (this.FM.EI.engines[0].getRPM() > 100.0F) {
      this.slat = (0.96F * this.slat + 0.04F * Aircraft.cvt(this.FM.getSpeedKMH(), 80.0F, 110.0F, -0.18F, 0.0F));
    }
    else {
      this.slat = (0.995F * this.slat);
    }

    Aircraft.xyz[1] = this.slat;
    hierMesh().chunkSetLocate("SlatR_D0", Aircraft.xyz, Aircraft.ypr);
    hierMesh().chunkSetLocate("SlatL_D0", Aircraft.xyz, Aircraft.ypr);

    if ((this.FM.AS.isPilotParatrooper(2)) && (hierMesh().isChunkVisible("Pilot3up_D0"))) {
      hierMesh().chunkVisible("Pilot3up_D0", false);
    }

    if ((this.obsMove < this.obsMoveTot) && (!this.bObserverKilled) && (!this.FM.AS.isPilotParatrooper(1)))
    {
      if ((this.obsMove < 0.2F) || (this.obsMove > this.obsMoveTot - 0.2F))
        this.obsMove = (float)(this.obsMove + 0.3D * paramFloat);
      else if ((this.obsMove < 0.1F) || (this.obsMove > this.obsMoveTot - 0.1F))
        this.obsMove += 0.15F;
      else {
        this.obsMove = (float)(this.obsMove + 1.2D * paramFloat);
      }
      this.obsLookAzimuth = Aircraft.cvt(this.obsMove, 0.0F, this.obsMoveTot, this.obsAzimuthOld, this.obsAzimuth);
      this.obsLookElevation = Aircraft.cvt(this.obsMove, 0.0F, this.obsMoveTot, this.obsElevationOld, this.obsElevation);
      hierMesh().chunkSetAngles("Head2_D0", 0.0F, this.obsLookAzimuth, this.obsLookElevation);
    }

    if ((this.TAGMove < this.TAGMoveTot) && (!this.bTAGKilled) && (!this.FM.AS.isPilotParatrooper(2)))
    {
      if ((this.TAGMove < 0.2F) || (this.TAGMove > this.TAGMoveTot - 0.2F))
        this.TAGMove = (float)(this.TAGMove + 0.3D * paramFloat);
      else if ((this.TAGMove < 0.1F) || (this.TAGMove > this.TAGMoveTot - 0.1F))
        this.TAGMove += 0.15F;
      else {
        this.TAGMove = (float)(this.TAGMove + 1.2D * paramFloat);
      }
      this.TAGLookAzimuth = Aircraft.cvt(this.TAGMove, 0.0F, this.TAGMoveTot, this.TAGAzimuthOld, this.TAGAzimuth);
      this.TAGLookElevation = Aircraft.cvt(this.TAGMove, 0.0F, this.TAGMoveTot, this.TAGElevationOld, this.TAGElevation);
      hierMesh().chunkSetAngles("Head3_D0", 0.0F, this.TAGLookAzimuth, this.TAGLookElevation);
    }

    super.update(paramFloat);
  }

  protected void moveAileron(float paramFloat)
  {
    Controls localControls = this.FM.CT;
    float f1 = localControls.getFlap();

    float f2 = -(paramFloat * 30.0F + f1 * 17.0F);
    hierMesh().chunkSetAngles("AroneL1_D0", 0.0F, f2, 0.0F);
    hierMesh().chunkSetAngles("AroneL2_D0", 0.0F, f2, 0.0F);

    f2 = -(paramFloat * 30.0F - f1 * 17.0F);
    hierMesh().chunkSetAngles("AroneR1_D0", 0.0F, f2, 0.0F);
    hierMesh().chunkSetAngles("AroneR2_D0", 0.0F, f2, 0.0F);
  }

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -25.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    if (paramFloat < 0.0F)
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -20.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -20.0F * paramFloat, 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30.0F * paramFloat, 0.0F);
      hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    }
  }

  public void moveArrestorHook(float paramFloat)
  {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -52.0F * paramFloat, 0.0F);
    this.arrestor = paramFloat;
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLIn_D0", 0.0F, 85.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRIn_D0", 0.0F, -85.0F * paramFloat, 0.0F);
  }

  public void moveWingFold(float paramFloat)
  {
    if (paramFloat < 0.001F) {
      setGunPodsOn(true);
      hideWingWeapons(false);
    } else {
      setGunPodsOn(false);
      this.FM.CT.WeaponControl[0] = false;
      hideWingWeapons(true);
    }
    moveWingFold(hierMesh(), paramFloat);
  }

  protected void setControlDamage(Shot paramShot, int paramInt)
  {
    if (World.Rnd().nextFloat() < 0.002F)
    {
      if (getEnergyPastArmor(4.0F, paramShot) > 0.0F)
      {
        this.FM.AS.setControlsDamage(paramShot.initiator, paramInt);
        mydebuggunnery(paramInt + " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
      }
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d) {
    int i = 0;
    int j;
    if (paramString.startsWith("xx")) {
      if (paramString.startsWith("xxarmor")) {
        mydebuggunnery("Armor: Hit..");
        if (paramString.startsWith("xxarmorp")) {
          j = paramString.charAt(8) - '0';
          switch (j) {
          case 2:
            getEnergyPastArmor(22.760000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

            if (paramShot.power > 0.0F) break;
            doRicochetBack(paramShot); break;
          case 3:
            getEnergyPastArmor(9.366F, paramShot);
            break;
          case 5:
            getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);
          case 4:
          }

        }

      }
      else if (paramString.startsWith("xxspar")) {
        mydebuggunnery("Spar Construction: Hit..");
        if ((paramString.startsWith("xxsparli")) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparri")) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparlo")) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparro")) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(6.960000038146973D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot) > 0.0F))
        {
          mydebuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspart")) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(3.86F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.25F))
        {
          mydebuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
          nextDMGLevels(1, 2, "Tail1_D3", paramShot.initiator);
        }
      } else {
        if (paramString.startsWith("xxlock")) {
          mydebuggunnery("Lock Construction: Hit..");
          if ((paramString.startsWith("xxlockr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorL Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
          }

          if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.5F), paramShot) > 0.0F))
          {
            mydebuggunnery("Lock Construction: VatorR Lock Shot Off..");
            nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
          }

        }

        if (paramString.startsWith("xxeng"))
        {
          if (((paramString.endsWith("prop")) || (paramString.endsWith("pipe"))) && (getEnergyPastArmor(0.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            this.FM.EI.engines[0].setKillPropAngleDevice(paramShot.initiator);
          }
          if ((paramString.endsWith("case")) || (paramString.endsWith("gear")))
          {
            mydebuggunnery("*** Engine Crank Case Hit");
            if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
            {
              if (World.Rnd().nextFloat() < paramShot.power / 140000.0F)
              {
                this.FM.AS.setEngineStuck(paramShot.initiator, 0);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Stucks..");
              }
              else if (World.Rnd().nextFloat() < paramShot.power / 85000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Crank Case Hit - Engine Damaged..");
              }
              else {
                this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.002F);

                mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
              }
            }
            else if (World.Rnd().nextFloat() < 0.05F)
            {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, 1);

              mydebuggunnery("*** Engine Cylinders Damaged..");
            }
            else
            {
              this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - 0.002F);

              mydebuggunnery("*** Engine Crank Case Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + "..");
            }

            getEnergyPastArmor(12.0F, paramShot);
          }
          if (paramString.endsWith("cyls"))
          {
            if ((getEnergyPastArmor(6.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[0].getCylindersRatio() * 0.75F))
            {
              this.FM.EI.engines[0].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 19000.0F)));

              mydebuggunnery("*** Engine Cylinders Hit, " + this.FM.EI.engines[0].getCylindersOperable() + "/" + this.FM.EI.engines[0].getCylinders() + " Left..");

              if (World.Rnd().nextFloat() < paramShot.power / 48000.0F)
              {
                this.FM.AS.hitEngine(paramShot.initiator, 0, 2);
                mydebuggunnery("*** Engine Cylinders Hit - Engine Fires..");
              }
            }
            getEnergyPastArmor(25.0F, paramShot);
          }
          if ((paramString.endsWith("supc")) && 
            (getEnergyPastArmor(0.05F, paramShot) > 0.0F))
          {
            mydebuggunnery("*** Engine Compressor Hit ..");
            this.FM.EI.engines[0].setKillCompressor(paramShot.initiator);
            getEnergyPastArmor(2.0F, paramShot);
          }

          mydebuggunnery("*** Engine state = " + this.FM.AS.astateEngineStates[0]);
        }
        else if (paramString.startsWith("xxoil"))
        {
          this.FM.AS.hitOil(paramShot.initiator, 0);
          mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
        }
        else if (paramString.startsWith("xxtank"))
        {
          j = paramString.charAt(6) - '1';
          if (getEnergyPastArmor(0.4F, paramShot) > 0.0F)
            if (paramShot.power < 14100.0F)
            {
              if (this.FM.AS.astateTankStates[j] < 1) this.FM.AS.hitTank(paramShot.initiator, j, 1);

              if ((this.FM.AS.astateTankStates[j] < 4) && (World.Rnd().nextFloat() < 0.15F)) {
                this.FM.AS.hitTank(paramShot.initiator, j, 1);
              }
              if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[j] > 1) && (World.Rnd().nextFloat() < 0.2F))
                this.FM.AS.hitTank(paramShot.initiator, j, 10);
            }
            else {
              this.FM.AS.hitTank(paramShot.initiator, j, World.Rnd().nextInt(0, (int)(paramShot.power / 35000.0F)));
            }
          mydebuggunnery("*** Tank " + (j + 1) + " state = " + this.FM.AS.astateTankStates[j]);
        }
        else if (paramString.startsWith("xxmgun")) {
          if (paramString.endsWith("01")) {
            mydebuggunnery("Armament System: Forward Machine Gun: Disabled..");
            this.FM.AS.setJamBullets(0, 0);
          }
          if (paramString.endsWith("02")) {
            mydebuggunnery("Armament System: Rear Machine Gun: Disabled..");
            this.FM.AS.setJamBullets(1, 0);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.3F, 12.6F), paramShot);
        }
      }
    }
    else if (paramString.startsWith("xcf"))
    {
      setControlDamage(paramShot, 0);
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("CF") < 3) {
        hitChunk("CF", paramShot);
      }

    }
    else if (paramString.startsWith("xarmorp1"))
    {
      getEnergyPastArmor(20.760000228881836D / (Math.abs(Aircraft.v1.x) + 9.999999747378752E-005D), paramShot);

      if (paramShot.power <= 0.0F) {
        doRicochetBack(paramShot);
      }
    }
    else if (paramString.startsWith("xmgun01"))
    {
      if (getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 8.0F), paramShot) > 0.0F)
      {
        mydebuggunnery("Armament System: Forward Machine Gun: Disabled..");
        this.FM.AS.setJamBullets(0, 0);
      }
    }
    else if (paramString.startsWith("xmgun02"))
    {
      if (getEnergyPastArmor(World.Rnd().nextFloat(2.0F, 8.0F), paramShot) > 0.0F)
      {
        mydebuggunnery("Armament System: Rear Machine Gun: Disabled..");
        this.FM.AS.setJamBullets(0, 1);
      }

    }
    else if (paramString.startsWith("xeng"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    } else if (paramString.startsWith("xtail"))
    {
      setControlDamage(paramShot, 1);
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    } else if (paramString.startsWith("xkeel")) {
      hitChunk("Keel1", paramShot);
    } else if (paramString.startsWith("xrudder"))
    {
      setControlDamage(paramShot, 2);
      if (chunkDamageVisible("Rudder1") < 1)
        hitChunk("Rudder1", paramShot);
    } else if (paramString.startsWith("xstab"))
    {
      if (paramString.startsWith("xstabl"))
        hitChunk("StabL", paramShot);
      if (paramString.startsWith("xstabr"))
        hitChunk("StabR", paramShot);
    } else if (paramString.startsWith("xvator"))
    {
      if ((paramString.startsWith("xvatorl")) && (chunkDamageVisible("VatorL") < 1))
        hitChunk("VatorL", paramShot);
      if ((paramString.startsWith("xvatorr")) && (chunkDamageVisible("VatorR") < 1))
        hitChunk("VatorR", paramShot);
    } else if (paramString.startsWith("xwing"))
    {
      if ((paramString.startsWith("xwinglin")) && (chunkDamageVisible("WingLIn") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingLIn", paramShot);
      }
      if ((paramString.startsWith("xwingrin")) && (chunkDamageVisible("WingRIn") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingRIn", paramShot);
      }
      if ((paramString.startsWith("xwinglmid")) && (chunkDamageVisible("WingLMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingLMid", paramShot);
      }
      if ((paramString.startsWith("xwingrmid")) && (chunkDamageVisible("WingRMid") < 3))
      {
        setControlDamage(paramShot, 0);
        hitChunk("WingRMid", paramShot);
      }
      if ((paramString.startsWith("xwinglout")) && (chunkDamageVisible("WingLOut") < 3))
        hitChunk("WingLOut", paramShot);
      if ((paramString.startsWith("xwingrout")) && (chunkDamageVisible("WingROut") < 3))
        hitChunk("WingROut", paramShot);
    } else if (paramString.startsWith("xarone"))
    {
      if (paramString.startsWith("xaronel1")) {
        hitChunk("AroneL1", paramShot);
      }
      if (paramString.startsWith("xaronel2")) {
        hitChunk("AroneL2", paramShot);
      }
      if (paramString.startsWith("xaroner1")) {
        hitChunk("AroneR1", paramShot);
      }
      if (paramString.startsWith("xaroner2"))
        hitChunk("AroneR2", paramShot);
    }
    else if (paramString.startsWith("xgearr")) {
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        mydebuggunnery("Undercarriage: Stuck..");

        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }hitChunk("GearR2", paramShot);
    }
    else if (paramString.startsWith("xgearl")) {
      if ((World.Rnd().nextFloat() < 0.1F) && (getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), paramShot) > 0.0F))
      {
        mydebuggunnery("Undercarriage: Stuck..");

        this.FM.AS.setInternalDamage(paramShot.initiator, 3);
      }hitChunk("GearL2", paramShot);
    }
    else if (paramString.startsWith("xradiator")) {
      if (World.Rnd().nextFloat() < 0.12F) {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        mydebuggunnery("*** Engine Module: Oil Radiator Hit..");
      }
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
    {
      j = 0;
      int k;
      if (paramString.endsWith("a"))
      {
        j = 1;
        k = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b"))
      {
        j = 2;
        k = paramString.charAt(6) - '1';
      }
      else {
        k = paramString.charAt(5) - '1';
      }
      hitFlesh(k, paramShot, j);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    case 0:
      if (f1 < -70.0F)
      {
        f1 = -70.0F;
        bool = false;
      }
      if (f1 > 70.0F)
      {
        f1 = 70.0F;
        bool = false;
      }
      if (f2 < -45.0F)
      {
        f2 = -45.0F;
        bool = false;
      }
      if (f2 > 70.0F)
      {
        f2 = 70.0F;
        bool = false;
      }
      if (((f1 <= -30.0F) && (f1 >= 30.0F)) || (f2 >= -10.0F))
        break;
      f2 = -10.0F;
      bool = false; break;
    }

    paramArrayOfFloat[0] = f1;
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected void mydebuggunnery(String paramString)
  {
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
  }
}
package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public abstract class JU_88NEW extends Scheme2
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Math.max(-paramFloat * 1600.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    f = paramFloat < 0.5F ? Math.abs(Math.min(paramFloat, 0.1F)) : Math.abs(Math.min(1.0F - paramFloat, 0.1F));
    paramHierMesh.chunkSetAngles("GearR7_D0", 0.0F, -450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL7_D0", 0.0F, 450.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, 1200.0F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -1200.0F * f, 0.0F);
    if (paramFloat < 0.5F)
    {
      paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, 900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -900.0F * f, 0.0F);
      paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, 900.0F * f, 0.0F);
    }
    paramHierMesh.chunkSetAngles("GearR8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, 95.0F * paramFloat);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -90.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -130.0F * paramFloat, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -70.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);
    float f1 = -paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    switch (paramInt)
    {
    default:
      break;
    case 0:
      if (f1 < -30.0F)
      {
        f1 = -30.0F;
        bool = false;
      }
      if (f1 > 35.0F)
      {
        f1 = 35.0F;
        bool = false;
      }
      if (f2 < -10.0F)
      {
        f2 = -10.0F;
        bool = false;
      }
      if (f2 <= 35.0F)
        break;
      f2 = 35.0F;
      bool = false; break;
    case 1:
      f1 = 0.0F;
      f2 = 0.0F;
      bool = false;
      break;
    case 2:
      if (f1 < -45.0F)
      {
        f1 = -45.0F;
        bool = false;
      }
      if (f1 > 25.0F)
      {
        f1 = 25.0F;
        bool = false;
      }
      if (f2 < -10.0F)
      {
        f2 = -10.0F;
        bool = false;
      }
      if (f2 > 60.0F)
      {
        f2 = 60.0F;
        bool = false;
      }
      if (f1 < -2.0F)
      {
        if (f2 >= Aircraft.cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F)) break;
        f2 = Aircraft.cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F);
      }
      else if (f1 < 0.5F)
      {
        if (f2 >= Aircraft.cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F)) break;
        f2 = Aircraft.cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F);
      }
      else if (f1 < 5.3F)
      {
        if (f2 >= Aircraft.cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F)) break;
        f2 = Aircraft.cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F);
      }
      else {
        if (f2 >= Aircraft.cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F)) break;
        f2 = Aircraft.cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F); } break;
    case 3:
      if (f1 < -35.0F)
      {
        f1 = -35.0F;
        bool = false;
      }
      if (f1 > 35.0F)
      {
        f1 = 35.0F;
        bool = false;
      }
      if (f2 < -35.0F)
      {
        f2 = -35.0F;
        bool = false;
      }
      if (f2 <= -0.48F)
        break;
      f2 = -0.48F;
      bool = false;
    }

    paramArrayOfFloat[0] = (-f1);
    paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1)
    {
    case 13:
      killPilot(this, 0);
      killPilot(this, 1);
      killPilot(this, 2);
      killPilot(this, 3);
      return false;
    case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    case 3:
      this.FM.AS.hitEngine(this, 0, 99);
      break;
    case 4:
      this.FM.AS.hitEngine(this, 1, 99);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void mydebuggunnery(String paramString)
  {
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.39F))
        this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateTankStates[0] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[1] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[2] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0, this);
      if ((this.FM.AS.astateTankStates[3] > 4) && (World.Rnd().nextFloat() < 0.1F))
        nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0, this);
    }
    for (int i = 1; i < 4; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
        if (paramString.endsWith("p1"))
        {
          if (Aircraft.v1.z > 0.5D) {
            getEnergyPastArmor(5.0D / Aircraft.v1.z, paramShot);
          }
          else if (Aircraft.v1.x > 0.9396926164627075D)
            getEnergyPastArmor(10.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p2")) {
          getEnergyPastArmor(5.0D / Math.abs(Aircraft.v1.z), paramShot);
        }
        else if (paramString.endsWith("p3")) {
          getEnergyPastArmor(8.0D / Math.abs(Aircraft.v1.x) * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
        }
        else if (paramString.endsWith("p4"))
        {
          if (Aircraft.v1.x > 0.7071067690849304D) {
            getEnergyPastArmor(8.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          }
          else if (Aircraft.v1.x > -0.7071067690849304D)
            getEnergyPastArmor(6.0F, paramShot);
        }
        else if ((paramString.endsWith("o1")) || (paramString.endsWith("o2")))
          if (Aircraft.v1.x > 0.7071067690849304D)
            getEnergyPastArmor(8.0D / Aircraft.v1.x * World.Rnd().nextFloat(1.0F, 1.2F), paramShot);
          else
            getEnergyPastArmor(5.0F, paramShot);
      if (paramString.startsWith("xxcontrols"))
      {
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        default:
          break;
        case 1:
        case 2:
          if (getEnergyPastArmor(1.0F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.08F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            mydebuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.08F)
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          mydebuggunnery("Rudder Controls Out.."); break;
        case 3:
        case 4:
          if ((getEnergyPastArmor(1.0F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.12F))
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          mydebuggunnery("Ailerons Controls Out.."); break;
        case 5:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.25F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            mydebuggunnery("*** Engine1 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F)
            break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
          mydebuggunnery("*** Engine1 Prop Controls Out.."); break;
        case 6:
          if (getEnergyPastArmor(0.1F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            mydebuggunnery("*** Engine2 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.15F)
            break;
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
          mydebuggunnery("*** Engine2 Prop Controls Out..");
        }

      }

      if (paramString.startsWith("xxspar"))
      {
        getEnergyPastArmor(1.0F, paramShot);
        if (((paramString.endsWith("cf1")) || (paramString.endsWith("cf2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("CF") > 2) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          mydebuggunnery("*** CF Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
          msgCollision(this, "WingLIn_D0", "WingLIn_D0");
          msgCollision(this, "WingRIn_D0", "WingRIn_D0");
        }
        if (((paramString.endsWith("ta1")) || (paramString.endsWith("ta2"))) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(15.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          mydebuggunnery("*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && (World.Rnd().nextFloat() < 1.0D - 0.9200000166893005D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(13.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && (World.Rnd().nextFloat() < 1.0D - 0.8600000143051148D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(10.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (World.Rnd().nextFloat() < 1.0D - 0.7900000214576721D * Math.abs(Aircraft.v1.x)) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(8.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          mydebuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("e1")) && ((paramPoint3d.y > 2.79D) || (paramPoint3d.y < 2.32D)) && (getEnergyPastArmor(18.0F, paramShot) > 0.0F))
        {
          mydebuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e2")) && ((paramPoint3d.y < -2.79D) || (paramPoint3d.y > -2.32D)) && (getEnergyPastArmor(18.0F, paramShot) > 0.0F))
        {
          mydebuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
      }
      if ((paramString.startsWith("xxbomb")) && (World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets()))
      {
        mydebuggunnery("*** Bomb Payload Detonates..");
        this.FM.AS.hitTank(paramShot.initiator, 0, 100);
        this.FM.AS.hitTank(paramShot.initiator, 1, 100);
        this.FM.AS.hitTank(paramShot.initiator, 2, 100);
        this.FM.AS.hitTank(paramShot.initiator, 3, 100);
        msgCollision(this, "CF_D0", "CF_D0");
      }
      if (paramString.startsWith("xxprop"))
      {
        i = 0;
        if (paramString.endsWith("2"))
          i = 1;
        if ((getEnergyPastArmor(2.0F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.35F))
        {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 3);
          mydebuggunnery("*** Engine" + (i + 1) + " Governor Failed..");
        }
      }
      if (paramString.startsWith("xxengine"))
      {
        i = 0;
        if (paramString.startsWith("xxengine2"))
          i = 1;
        mydebuggunnery("*** Engine " + i + " " + paramString + " hit");
        if (paramString.endsWith("base"))
        {
          if (getEnergyPastArmor(0.1F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 120000.0F)
            {
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
              mydebuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Stucks..");
            }
            if (World.Rnd().nextFloat() < paramShot.power / 30000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              mydebuggunnery("*** Engine" + (i + 1) + " Crank Case Hit - Engine Damaged..");
            }
          }
        }
        else if (paramString.endsWith("cyl"))
        {
          mydebuggunnery("*** Engine " + i + " " + paramString + " hit");
          if ((getEnergyPastArmor(1.2F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 1.8F))
          {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 4800.0F)));
            mydebuggunnery("*** Engine" + (i + 1) + " Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (this.FM.AS.astateEngineStates[i] < 1)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 1);
              this.FM.AS.doSetEngineState(paramShot.initiator, i, 1);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 960000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 3);
              mydebuggunnery("*** Engine" + (i + 1) + " Cylinders Hit - Engine Fires..");
            }
            mydebuggunnery("*** Engine" + (i + 1) + " state " + this.FM.AS.astateEngineStates[i]);
          }

        }
        else if ((paramString.endsWith("sup")) && (getEnergyPastArmor(0.05F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.89F))
        {
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
          mydebuggunnery("*** Engine" + (i + 1) + " Supercharger Out..");
        }
        if (World.Rnd().nextFloat(0.0F, 18000.0F) < paramShot.power)
          this.FM.AS.hitEngine(paramShot.initiator, i, 1);
        this.FM.AS.hitOil(paramShot.initiator, i);
      }

      if (paramString.startsWith("xxoil"))
      {
        i = 0;
        if (paramString.endsWith("2"))
          i = 1;
        if (getEnergyPastArmor(0.18F, paramShot) > 0.0F)
        {
          this.FM.AS.hitOil(paramShot.initiator, i);
          getEnergyPastArmor(0.42F, paramShot);
        }
      }

      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '1';
        if (getEnergyPastArmor(0.5F, paramShot) > 0.0F)
        {
          if (paramShot.power < 14100.0F)
          {
            if (this.FM.AS.astateTankStates[i] < 1)
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((this.FM.AS.astateTankStates[i] < 4) && (World.Rnd().nextFloat() < 0.1F))
              this.FM.AS.hitTank(paramShot.initiator, i, 1);
            if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[i] > 2) && (World.Rnd().nextFloat() < 0.12F))
              this.FM.AS.hitTank(paramShot.initiator, i, 10);
          } else {
            this.FM.AS.hitTank(paramShot.initiator, i, World.Rnd().nextInt(0, (int)(paramShot.power / 20000.0F)));
          }
        }
        mydebuggunnery("*** Tank " + (i + 1) + " state = " + this.FM.AS.astateTankStates[i]);
      }
    }

    if (paramString.startsWith("xoil"))
    {
      if (paramString.equals("xoil1"))
      {
        this.FM.AS.hitOil(paramShot.initiator, 0);
        paramString = "xengine1";
      }
      if (paramString.equals("xoil2"))
      {
        this.FM.AS.hitOil(paramShot.initiator, 1);
        paramString = "xengine2";
      }
    }
    if (paramString.startsWith("xcf"))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
    }
    else if (paramString.startsWith("xnose"))
    {
      if (chunkDamageVisible("Nose") < 2)
        hitChunk("Nose", paramShot);
      if (paramShot.power > 200000.0F)
      {
        this.FM.AS.hitPilot(paramShot.initiator, 0, World.Rnd().nextInt(3, 192));
        this.FM.AS.hitPilot(paramShot.initiator, 1, World.Rnd().nextInt(3, 192));
      }
      if (World.Rnd().nextFloat() < 0.1F)
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      if (paramPoint3d.x > 4.505000114440918D) {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
      }
      else if (paramPoint3d.y > 0.0D)
      {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
        if (World.Rnd().nextFloat() < 0.1F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
      }
      else {
        this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        if (World.Rnd().nextFloat() < 0.1F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      }
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
    else if (paramString.startsWith("xrudder")) {
      hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xstabl")) {
      hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr")) {
      hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl")) {
      hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr")) {
      hitChunk("VatorR", paramShot);
    }
    else if (paramString.startsWith("xwinglin"))
    {
      if (chunkDamageVisible("WingLIn") < 3)
        hitChunk("WingLIn", paramShot);
    }
    else if (paramString.startsWith("xwingrin"))
    {
      if (chunkDamageVisible("WingRIn") < 3)
        hitChunk("WingRIn", paramShot);
    }
    else if (paramString.startsWith("xwinglmid"))
    {
      if (chunkDamageVisible("WingLMid") < 3)
        hitChunk("WingLMid", paramShot);
    }
    else if (paramString.startsWith("xwingrmid"))
    {
      if (chunkDamageVisible("WingRMid") < 3)
        hitChunk("WingRMid", paramShot);
    }
    else if (paramString.startsWith("xwinglout"))
    {
      if (chunkDamageVisible("WingLOut") < 3)
        hitChunk("WingLOut", paramShot);
    }
    else if (paramString.startsWith("xwingrout"))
    {
      if (chunkDamageVisible("WingROut") < 3)
        hitChunk("WingROut", paramShot);
    }
    else if (paramString.startsWith("xaronel")) {
      hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner")) {
      hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
      if (getEnergyPastArmor(0.2F, paramShot) > 800.0F)
      {
        this.FM.EI.engines[0].setReadyness(paramShot.initiator, this.FM.EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 96000.0F));

        this.FM.AS.hitOil(paramShot.initiator, 0);
        mydebuggunnery("*** Engine1 Hit - Readyness Reduced to " + this.FM.EI.engines[0].getReadyness() + ".. State = " + this.FM.AS.astateEngineStates[0]);

        if ((this.FM.EI.engines[0].getReadyness() < 0.85F) && (this.FM.AS.astateEngineStates[0] == 0))
        {
          this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 0, 1);
        }
      }
    }
    else if (paramString.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
      if (getEnergyPastArmor(0.2F, paramShot) > 800.0F)
      {
        this.FM.EI.engines[1].setReadyness(paramShot.initiator, this.FM.EI.engines[1].getReadyness() - World.Rnd().nextFloat(0.0F, paramShot.power / 96000.0F));

        this.FM.AS.hitOil(paramShot.initiator, 1);
        mydebuggunnery("*** Engine2 Hit - Readyness Reduced to " + this.FM.EI.engines[1].getReadyness() + ".. State = " + this.FM.AS.astateEngineStates[1]);

        if ((this.FM.EI.engines[1].getReadyness() < 0.85F) && (this.FM.AS.astateEngineStates[1] == 0))
        {
          this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
          this.FM.AS.doSetEngineState(paramShot.initiator, 1, 1);
        }
      }

    }
    else if (paramString.startsWith("xgear"))
    {
      if (World.Rnd().nextFloat() < 0.1F)
      {
        mydebuggunnery("*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    }
    else if (paramString.startsWith("xturret"))
    {
      if (paramString.startsWith("xturret1"))
        this.FM.AS.setJamBullets(10, 0);
      if (paramString.startsWith("xturret2"))
        this.FM.AS.setJamBullets(11, 0);
      if (paramString.startsWith("xturret3"))
        this.FM.AS.setJamBullets(12, 0);
      if (paramString.startsWith("xturret4"))
        this.FM.AS.setJamBullets(13, 0);
      if (paramString.startsWith("xturret5"))
        this.FM.AS.setJamBullets(14, 0);
    }
    else if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead")))
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
      hitFlesh(j, paramShot, i);
    }
  }

  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
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
    Class localClass = JU_88NEW.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
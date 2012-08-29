package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;

public abstract class B_25_Strafer extends B_25
{
  private float kangle0;
  private float kangle1;

  public B_25_Strafer()
  {
    this.kangle0 = 0.0F;
    this.kangle1 = 0.0F;
  }

  private boolean isNotPilot3(String paramString)
  {
    int i = 1;

    if ((paramString.startsWith("xpilot3")) || (paramString.startsWith("xhead3")))
    {
      i = 0;
    }

    return i;
  }

  private final void doHitMeATank(Shot paramShot, int paramInt)
  {
    if (getEnergyPastArmor(0.2F, paramShot) > 0.0F)
      if (paramShot.power < 14100.0F) {
        if (this.FM.AS.astateTankStates[paramInt] == 0) {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 1);
          this.FM.AS.doSetTankState(paramShot.initiator, paramInt, 1);
        }
        if ((paramShot.powerType == 3) && (this.FM.AS.astateTankStates[paramInt] > 0) && (World.Rnd().nextFloat() < 0.25F))
        {
          this.FM.AS.hitTank(paramShot.initiator, paramInt, 2);
        }
      } else {
        this.FM.AS.hitTank(paramShot.initiator, paramInt, World.Rnd().nextInt(0, (int)(paramShot.power / 56000.0F)));
      }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    int i;
    int j;
    if (paramString.startsWith("xx"))
    {
      if (paramString.startsWith("xxarmor"))
      {
        if ((paramString.endsWith("p1")) || (paramString.endsWith("p2"))) {
          if (Math.abs(Aircraft.v1.x) > 0.5D)
            getEnergyPastArmor(7.940000057220459D / Math.abs(Aircraft.v1.x), paramShot);
          else {
            getEnergyPastArmor(9.529999732971191D / (1.0D - Math.abs(Aircraft.v1.x)), paramShot);
          }
        }
        if (paramString.endsWith("p3")) {
          getEnergyPastArmor(7.940000057220459D / (Math.abs(Aircraft.v1.z) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("p4")) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if ((paramString.endsWith("p5")) || (paramString.endsWith("p6"))) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.y) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("p7")) {
          getEnergyPastArmor(0.5D / (Math.abs(Aircraft.v1.z) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("p8")) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("a1")) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.y) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("a2")) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.y) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("a3")) {
          getEnergyPastArmor(6.349999904632568D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if ((paramString.endsWith("a4")) || (paramString.endsWith("a5"))) {
          getEnergyPastArmor(12.699999809265137D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if ((paramString.endsWith("a6")) || (paramString.endsWith("a7"))) {
          getEnergyPastArmor(6.35D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if (paramString.endsWith("r1")) {
          getEnergyPastArmor(3.170000076293945D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if ((paramString.endsWith("r2")) || (paramString.endsWith("r3"))) {
          getEnergyPastArmor(9.529999732971191D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }

        if ((paramString.endsWith("c1")) || (paramString.endsWith("c2"))) {
          getEnergyPastArmor(8.729999542236328D / (Math.abs(Aircraft.v1.x) + 9.999999974752427E-007D), paramShot);
        }
        return;
      }
      if (paramString.startsWith("xxcontrols"))
      {
        i = paramString.charAt(10) - '0';
        switch (i)
        {
        default:
          break;
        case 1:
          if (getEnergyPastArmor(3.0F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.12F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 1);
            debuggunnery("Evelator Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.12F)
          {
            this.FM.AS.setControlsDamage(paramShot.initiator, 2);
            debuggunnery("Rudder Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.12F)
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Aileron Controls Out.."); break;
        case 2:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 1);
            debuggunnery("*** Engine1 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 0, 6);
            debuggunnery("*** Engine1 Prop Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.25F)
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out.."); break;
        case 3:
          if (getEnergyPastArmor(1.5F, paramShot) <= 0.0F)
            break;
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 1);
            debuggunnery("*** Engine2 Throttle Controls Out..");
          }
          if (World.Rnd().nextFloat() < 0.15F)
          {
            this.FM.AS.setEngineSpecificDamage(paramShot.initiator, 1, 6);
            debuggunnery("*** Engine2 Prop Controls Out..");
          }
          if (World.Rnd().nextFloat() >= 0.25F)
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 0);
          debuggunnery("Ailerons Controls Out.."); break;
        case 4:
        case 5:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.12F))
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 1);
          debuggunnery("Evelator Controls Out.."); break;
        case 6:
        case 7:
          if ((getEnergyPastArmor(1.5F, paramShot) <= 0.0F) || (World.Rnd().nextFloat() >= 0.12F))
            break;
          this.FM.AS.setControlsDamage(paramShot.initiator, 2);
          debuggunnery("Rudder Controls Out..");
        }

        return;
      }
      if (paramString.startsWith("xxspar"))
      {
        if ((paramString.startsWith("xxspart")) && (World.Rnd().nextFloat() < 0.1F) && (chunkDamageVisible("Tail1") > 2) && (getEnergyPastArmor(19.9F / (float)Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), paramShot) > 0.0F))
        {
          debuggunnery("*** Tail1 Spars Broken in Half..");
          msgCollision(this, "Tail1_D0", "Tail1_D0");
        }
        if (((paramString.endsWith("li1")) || (paramString.endsWith("li2"))) && (chunkDamageVisible("WingLIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingLIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingLIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ri1")) || (paramString.endsWith("ri2"))) && (chunkDamageVisible("WingRIn") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingRIn Spars Damaged..");
          nextDMGLevels(1, 2, "WingRIn_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lm1")) || (paramString.endsWith("lm2"))) && (chunkDamageVisible("WingLMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingLMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingLMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("rm1")) || (paramString.endsWith("rm2"))) && (chunkDamageVisible("WingRMid") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingRMid Spars Damaged..");
          nextDMGLevels(1, 2, "WingRMid_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("lo1")) || (paramString.endsWith("lo2"))) && (chunkDamageVisible("WingLOut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingLOut Spars Damaged..");
          nextDMGLevels(1, 2, "WingLOut_D3", paramShot.initiator);
        }
        if (((paramString.endsWith("ro1")) || (paramString.endsWith("ro2"))) && (chunkDamageVisible("WingROut") > 2) && (getEnergyPastArmor(19.5F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** WingROut Spars Damaged..");
          nextDMGLevels(1, 2, "WingROut_D3", paramShot.initiator);
        }
        if ((paramString.endsWith("e1")) && (getEnergyPastArmor(28.0F, paramShot) > 0.0F))
        {
          debuggunnery("*** Engine1 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine1_D0", paramShot.initiator);
        }
        if ((paramString.endsWith("e2")) && (getEnergyPastArmor(28.0F, paramShot) > 0.0F))
        {
          debuggunnery("*** Engine2 Suspension Broken in Half..");
          nextDMGLevels(3, 2, "Engine2_D0", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspark1")) && (chunkDamageVisible("Keel1") > 1) && (getEnergyPastArmor(9.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** Keel1 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel1_D2", paramShot.initiator);
        }
        if ((paramString.startsWith("xxspark2")) && (chunkDamageVisible("Keel2") > 1) && (getEnergyPastArmor(9.6F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** Keel2 Spars Damaged..");
          nextDMGLevels(1, 2, "Keel2_D2", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparsl")) && (chunkDamageVisible("StabL") > 1) && (getEnergyPastArmor(11.2F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** StabL Spars Damaged..");
          nextDMGLevels(1, 2, "StabL_D2", paramShot.initiator);
        }
        if ((paramString.startsWith("xxsparsr")) && (chunkDamageVisible("StabR") > 1) && (getEnergyPastArmor(11.2F * World.Rnd().nextFloat(1.0F, 2.0F), paramShot) > 0.0F))
        {
          debuggunnery("*** StabR Spars Damaged..");
          nextDMGLevels(1, 2, "StabR_D2", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxbomb"))
      {
        if ((World.Rnd().nextFloat() < 0.01F) && (this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][0].haveBullets()))
        {
          debuggunnery("*** Bomb Payload Detonates..");
          this.FM.AS.hitTank(paramShot.initiator, 0, 100);
          this.FM.AS.hitTank(paramShot.initiator, 1, 100);
          this.FM.AS.hitTank(paramShot.initiator, 2, 100);
          this.FM.AS.hitTank(paramShot.initiator, 3, 100);
          nextDMGLevels(3, 2, "CF_D0", paramShot.initiator);
        }
        return;
      }
      if (paramString.startsWith("xxeng"))
      {
        i = 0;
        if (paramString.startsWith("xxeng2"))
          i = 1;
        debuggunnery("Engine Module[" + i + "]: Hit..");
        if (paramString.endsWith("case"))
        {
          if (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 0.55F), paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat() < paramShot.power / 280000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
              this.FM.AS.setEngineStuck(paramShot.initiator, i);
            }
            if (World.Rnd().nextFloat() < paramShot.power / 100000.0F)
            {
              debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
            }
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 24.0F), paramShot);
        }
        if (paramString.endsWith("cyls"))
        {
          if ((getEnergyPastArmor(0.85F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < this.FM.EI.engines[i].getCylindersRatio() * 0.66F))
          {
            this.FM.EI.engines[i].setCyliderKnockOut(paramShot.initiator, World.Rnd().nextInt(1, (int)(paramShot.power / 32200.0F)));
            debuggunnery("Engine Module: Cylinders Hit, " + this.FM.EI.engines[i].getCylindersOperable() + "/" + this.FM.EI.engines[i].getCylinders() + " Left..");
            if (World.Rnd().nextFloat() < paramShot.power / 1000000.0F)
            {
              this.FM.AS.hitEngine(paramShot.initiator, i, 2);
              debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
            }
          }
          getEnergyPastArmor(25.0F, paramShot);
        }
        if ((paramString.endsWith("eqpt")) || ((paramString.endsWith("cyls")) && (World.Rnd().nextFloat() < 0.01F)))
        {
          if (getEnergyPastArmor(0.5F, paramShot) > 0.0F)
          {
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 4);
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 6);
            if (World.Rnd().nextFloat(0.0F, 26700.0F) < paramShot.power)
              this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 1);
          }
          getEnergyPastArmor(2.0F, paramShot);
        }
        if (paramString.endsWith("gear"))
        {
          if ((getEnergyPastArmor(4.6F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.5F))
          {
            debuggunnery("Engine Module: Bullet Jams Reductor Gear..");
            this.FM.EI.engines[i].setEngineStuck(paramShot.initiator);
          }
          getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 12.44565F), paramShot);
        }
        if ((paramString.endsWith("mag1")) || (paramString.endsWith("mag2")))
        {
          debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
          this.FM.EI.engines[i].setMagnetoKnockOut(paramShot.initiator, i);
        }
        if (paramString.endsWith("oil1"))
        {
          if ((World.Rnd().nextFloat() < 0.5F) && (getEnergyPastArmor(0.25F, paramShot) > 0.0F))
            debuggunnery("Engine Module: Oil Radiator Hit..");
          this.FM.AS.hitOil(paramShot.initiator, i);
        }
        if ((paramString.endsWith("prop")) && (getEnergyPastArmor(0.42F, paramShot) > 0.0F))
          this.FM.EI.engines[i].setKillPropAngleDevice(paramShot.initiator);
        if ((paramString.endsWith("supc")) && (getEnergyPastArmor(World.Rnd().nextFloat(0.2F, 12.0F), paramShot) > 0.0F))
        {
          debuggunnery("Engine Module: Turbine Disabled..");
          this.FM.AS.setEngineSpecificDamage(paramShot.initiator, i, 0);
        }
        return;
      }
      if (paramString.startsWith("xxtank"))
      {
        i = paramString.charAt(6) - '0';
        switch (i)
        {
        case 1:
        case 2:
          doHitMeATank(paramShot, 1);
          break;
        case 3:
          doHitMeATank(paramShot, 0);
          break;
        case 4:
        case 5:
          doHitMeATank(paramShot, 2);
          break;
        case 6:
          doHitMeATank(paramShot, 3);
          break;
        case 7:
          doHitMeATank(paramShot, 0);
          doHitMeATank(paramShot, 1);
          doHitMeATank(paramShot, 2);
          doHitMeATank(paramShot, 3);
        }

        return;
      }
      if (paramString.startsWith("xxpnm"))
      {
        if (getEnergyPastArmor(World.Rnd().nextFloat(0.25F, 1.22F), paramShot) > 0.0F)
        {
          debuggunnery("Pneumo System: Disabled..");
          this.FM.AS.setInternalDamage(paramShot.initiator, 1);
        }
        return;
      }

      if (paramString.startsWith("xxmgun02"))
      {
        this.FM.AS.setJamBullets(0, 0);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun07"))
      {
        this.FM.AS.setJamBullets(0, 0);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun08"))
      {
        this.FM.AS.setJamBullets(0, 1);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun09"))
      {
        this.FM.AS.setJamBullets(0, 2);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun10"))
      {
        this.FM.AS.setJamBullets(0, 3);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun13"))
      {
        this.FM.AS.setJamBullets(0, 4);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun14"))
      {
        this.FM.AS.setJamBullets(0, 5);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun15"))
      {
        this.FM.AS.setJamBullets(0, 6);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun16"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }

      if (paramString.startsWith("xxmgun17"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun18"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun19"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun20"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun21"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun22"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun23"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }
      if (paramString.startsWith("xxmgun24"))
      {
        this.FM.AS.setJamBullets(0, 7);
        getEnergyPastArmor(12.7F, paramShot);
        return;
      }

      if (paramString.startsWith("xxcannon"))
      {
        this.FM.AS.setJamBullets(1, 0);
        getEnergyPastArmor(44.700001F, paramShot);
        return;
      }

      if (paramString.startsWith("xxlock"))
      {
        Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
        if ((paramString.startsWith("xxlockr1")) && (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder1 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockr2")) && (getEnergyPastArmor(6.5F * World.Rnd().nextFloat(1.0F, 3.0F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** Rudder2 Lock Shot Off..");
          nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockvl")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockvr")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
          nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockal")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), paramShot.initiator);
        }
        if ((paramString.startsWith("xxlockar")) && (getEnergyPastArmor(5.5F * World.Rnd().nextFloat(1.0F, 1.2F), paramShot) > 0.0F))
        {
          Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");
          nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), paramShot.initiator);
        }
        return;
      }

      if (paramString.startsWith("xxammo0"))
      {
        i = paramString.charAt(7) - '0';
        int k;
        switch (i)
        {
        case 1:
        default:
          j = 0;
          k = 0;
          break;
        case 2:
          j = 10;
          k = 0;
          break;
        case 3:
          j = 11;
          k = 0;
          break;
        case 4:
          j = 11;
          k = 1;
          break;
        case 5:
          j = 12;
          k = 0;
          break;
        case 6:
          j = 12;
          k = 1;
          break;
        case 7:
          j = 0;
          k = 0;
          break;
        case 8:
          j = 0;
          k = 1;
          break;
        case 9:
          j = 0;
          k = 2;
          break;
        case 10:
          j = 0;
          k = 3;
        }

        if (World.Rnd().nextFloat() < 0.125F)
          this.FM.AS.setJamBullets(j, k);
        getEnergyPastArmor(4.7F, paramShot);
        return;
      }

      return;
    }

    if (paramString.startsWith("xcf"))
    {
      if (chunkDamageVisible("CF") < 3)
        hitChunk("CF", paramShot);
      if (World.Rnd().nextFloat() < 0.0575F)
        if (paramPoint3d.y > 0.0D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x8);
        else
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x20);
      if (paramPoint3d.x > 1.726D)
      {
        if (paramPoint3d.z > 0.444D)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x1);
        if ((paramPoint3d.z > -0.281D) && (paramPoint3d.z < 0.444D))
          if (paramPoint3d.y > 0.0D)
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x4);
          else
            this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x10);
        if ((paramPoint3d.x > 2.774D) && (paramPoint3d.x < 3.718D) && (paramPoint3d.z > 0.425D))
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x2);
        if (World.Rnd().nextFloat() < 0.12F)
          this.FM.AS.setCockpitState(paramShot.initiator, this.FM.AS.astateCockpitState | 0x40);
      }
    }
    else if (paramString.startsWith("xnose"))
    {
      if (chunkDamageVisible("Nose1") < 2)
        hitChunk("Nose1", paramShot);
    }
    else if (paramString.startsWith("xtail"))
    {
      if (chunkDamageVisible("Tail1") < 3)
        hitChunk("Tail1", paramShot);
    }
    else if (paramString.startsWith("xkeel1"))
    {
      if (chunkDamageVisible("Keel1") < 2)
        hitChunk("Keel1", paramShot);
    }
    else if (paramString.startsWith("xkeel2"))
    {
      if (chunkDamageVisible("Keel2") < 2)
        hitChunk("Keel2", paramShot);
    }
    else if (paramString.startsWith("xrudder1"))
    {
      if (chunkDamageVisible("Rudder1") < 2)
        hitChunk("Rudder1", paramShot);
    }
    else if (paramString.startsWith("xrudder2"))
    {
      if (chunkDamageVisible("Rudder2") < 2)
        hitChunk("Rudder2", paramShot);
    }
    else if (paramString.startsWith("xstabl"))
    {
      if (chunkDamageVisible("StabL") < 2)
        hitChunk("StabL", paramShot);
    }
    else if (paramString.startsWith("xstabr"))
    {
      if (chunkDamageVisible("StabR") < 2)
        hitChunk("StabR", paramShot);
    }
    else if (paramString.startsWith("xvatorl"))
    {
      if (chunkDamageVisible("VatorL") < 1)
        hitChunk("VatorL", paramShot);
    }
    else if (paramString.startsWith("xvatorr"))
    {
      if (chunkDamageVisible("VatorR") < 1)
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
    else if (paramString.startsWith("xaronel"))
    {
      if (chunkDamageVisible("AroneL") < 1)
        hitChunk("AroneL", paramShot);
    }
    else if (paramString.startsWith("xaroner"))
    {
      if (chunkDamageVisible("AroneR") < 1)
        hitChunk("AroneR", paramShot);
    }
    else if (paramString.startsWith("xengine1"))
    {
      if (chunkDamageVisible("Engine1") < 2)
        hitChunk("Engine1", paramShot);
    }
    else if (paramString.startsWith("xengine2"))
    {
      if (chunkDamageVisible("Engine2") < 2)
        hitChunk("Engine2", paramShot);
    }
    else if (paramString.startsWith("xgear"))
    {
      if (World.Rnd().nextFloat() < 0.1F)
      {
        debuggunnery("*** Gear Hydro Failed..");
        this.FM.Gears.setHydroOperable(false);
      }
    }
    else if (paramString.startsWith("xturret"))
    {
      if (paramString.startsWith("xturret1"))
        this.FM.AS.setJamBullets(10, 0);
      if (paramString.endsWith("2b1"))
        this.FM.AS.setJamBullets(11, 0);
      if (paramString.endsWith("2b2"))
        this.FM.AS.setJamBullets(11, 1);
      if (paramString.endsWith("3b1"))
        this.FM.AS.setJamBullets(12, 0);
      if (paramString.endsWith("3b2"))
        this.FM.AS.setJamBullets(12, 1);
      if (paramString.endsWith("4a"))
        this.FM.AS.setJamBullets(13, 1);
      if (paramString.endsWith("5a"))
        this.FM.AS.setJamBullets(14, 1);
    } else if ((paramString.startsWith("xpilot")) || ((paramString.startsWith("xhead")) && (isNotPilot3(paramString))))
    {
      i = 0;

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

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if ((this.FM.AS.astateEngineStates[0] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 0, 1);
      if ((this.FM.AS.astateEngineStates[1] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 1, 1);
      if ((this.FM.AS.astateEngineStates[2] > 3) && (World.Rnd().nextFloat() < 0.0023F))
        this.FM.AS.hitTank(this, 2, 1);
      if ((this.FM.AS.astateEngineStates[3] > 3) && (World.Rnd().nextFloat() < 0.0023F)) {
        this.FM.AS.hitTank(this, 3, 1);
      }

      if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode()))
      {
        for (int i = 0; i < this.FM.EI.getNum(); i++)
          if ((this.FM.AS.astateEngineStates[i] > 3) && (World.Rnd().nextFloat() < 0.2F))
            this.FM.EI.engines[i].setExtinguisherFire();
      }
    }
  }
}
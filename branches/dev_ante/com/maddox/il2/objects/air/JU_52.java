package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public abstract class JU_52 extends Scheme6
  implements TypeTransport
{
  private boolean bDynamoOperational;
  private float dynamoOrient;
  private boolean bDynamoRotary;
  private int pk;

  public JU_52()
  {
    this.bDynamoOperational = true;
    this.dynamoOrient = 0.0F;
    this.bDynamoRotary = false;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    Point3d localPoint3d = new Point3d(0.0D, 0.0D, 0.0D);

    Point3f localPoint3f = this.FM.EI.engines[0].getPropPos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = 4.099999904632568D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[0].setPropPos(localPoint3d);

    localPoint3f = this.FM.EI.engines[0].getEnginePos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = 4.0D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[0].setPos(localPoint3d);

    localPoint3f = this.FM.EI.engines[1].getPropPos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = 0.0D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[1].setPropPos(localPoint3d);

    localPoint3f = this.FM.EI.engines[1].getEnginePos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = 0.0D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[1].setPos(localPoint3d);

    localPoint3f = this.FM.EI.engines[2].getPropPos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = -4.099999904632568D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[2].setPropPos(localPoint3d);

    localPoint3f = this.FM.EI.engines[2].getEnginePos();
    localPoint3d.x = localPoint3f.x;
    localPoint3d.y = -4.0D;
    localPoint3d.z = localPoint3f.z;
    this.FM.EI.engines[2].setPos(localPoint3d);
  }

  public void doKillPilot(int paramInt)
  {
    switch (paramInt)
    {
    default:
      break;
    case 2:
      if (this.FM.turret.length <= 0) break;
      this.FM.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational)
    {
      this.pk = Math.abs((int)(this.FM.Vwld.length() / 14.0D));
      if (this.pk >= 1)
        this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1))
    {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Cart_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("CartRot_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.FM.Vwld.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Cart_D0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);
    if ((paramShot.chunkName.startsWith("Engine1")) && (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass))
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass))
      this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Engine3")) && (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass))
      this.FM.AS.hitEngine(paramShot.initiator, 2, 1);
    if (paramShot.chunkName.startsWith("Turret"))
      this.FM.turret[0].bIsOperable = false;
    if ((paramShot.chunkName.startsWith("Tail1")) && (Aircraft.Pd.z > 0.5D) && (Aircraft.Pd.x > -6.0D) && (Aircraft.Pd.x < -4.949999809265137D) && (World.Rnd().nextFloat() < 0.5F))
      this.FM.AS.hitPilot(paramShot.initiator, 2, (int)(paramShot.mass * 1000.0F * 0.5F));
    if ((paramShot.chunkName.startsWith("CF")) && (Aircraft.v1.x < -0.2000000029802322D) && (Aircraft.Pd.x > 2.599999904632568D) && (Aircraft.Pd.z > 0.7350000143051148D) && (World.Rnd().nextFloat() < 0.178F))
      this.FM.AS.hitPilot(paramShot.initiator, Aircraft.Pd.y <= 0.0D ? 1 : 0, (int)(paramShot.mass * 900.0F));
    if ((paramShot.chunkName.startsWith("WingLIn")) && (Math.abs(Aircraft.Pd.y) < 2.099999904632568D))
      this.FM.AS.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(0, (int)(paramShot.mass * 30.0F)));
    if ((paramShot.chunkName.startsWith("WingRIn")) && (Math.abs(Aircraft.Pd.y) < 2.099999904632568D))
      this.FM.AS.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, (int)(paramShot.mass * 30.0F)));
    super.msgShot(paramShot);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  static
  {
    Class localClass = JU_52.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}
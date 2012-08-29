package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.util.AbstractCollection;

public class Bomb extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener
{
  protected float delayExplosion = 0.0F;
  float curTm;
  protected Vector3d speed = new Vector3d();
  protected float S;
  protected float M;
  protected float J;
  protected float DistFromCMtoStab;
  Vector3d rotAxis = new Vector3d(0.0D, 0.0D, 0.0D);
  protected int index;
  private static Point3d p = new Point3d();
  private static Vector3f dir = new Vector3f();
  private static Vector3f dirN = new Vector3f();
  private static Orient or = new Orient();
  private static Loc loc = new Loc();

  private static PlateFilter plateFilter = new PlateFilter(null);
  private static Point3d corn = new Point3d();
  private static Point3d corn1 = new Point3d();
  private static float[] plateBox = new float[6];
  private static boolean bPlateExist = false;
  private static boolean bPlateGround = false;

  private static Loc __loc = new Loc();

  protected SoundFX sound = null;
  protected static final float SND_TIME_BOUND = 5.0F;

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (paramActor == getOwner())
      paramArrayOfBoolean[0] = false;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);

    while ((paramActor != null) && ((paramActor instanceof ActorLand)) && (isPointApplicableForJump()))
    {
      if (this.speed.jdField_z_of_type_Double >= 0.0D) {
        return;
      }

      float f1 = (float)this.speed.length();
      if (f1 < 30.0F)
      {
        break;
      }

      dir.set(this.speed);
      dir.scale(1.0F / f1);
      if (-dir.jdField_z_of_type_Float >= 0.31F)
      {
        break;
      }

      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(or);
      dirN.set(1.0F, 0.0F, 0.0F);
      or.transform(dirN);

      if (dir.dot(dirN) < 0.91F)
      {
        break;
      }

      float f2 = -dir.jdField_z_of_type_Float;
      f2 *= 3.225807F;
      f2 = 0.85F - 0.35F * f2;
      f2 *= World.Rnd().nextFloat(0.85F, 1.0F);

      this.speed.scale(f2);
      this.speed.jdField_z_of_type_Double *= f2;
      if (this.speed.jdField_z_of_type_Double < 0.0D) this.speed.jdField_z_of_type_Double = (-this.speed.jdField_z_of_type_Double);

      p.jdField_z_of_type_Double = Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p);

      if (this.M >= 200.0F) f2 = 1.0F;
      else if (this.M <= 5.0F) f2 = 0.0F; else
        f2 = (this.M - 5.0F) / 195.0F;
      float f3 = 3.5F + f2 * 12.0F;
      if (Engine.land().isWater(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double))
        Explosions.SomethingDrop_Water(p, f3);
      else {
        Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
      }

      return;
    }

    if ((getOwner() == World.getPlayerAircraft()) && (!(paramActor instanceof ActorLand))) {
      World.cur().scoreCounter.bombHit += 1;
      if ((Mission.isNet()) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer())) {
        Chat.sendLogRnd(3, "gore_bombed", (Aircraft)getOwner(), (Aircraft)paramActor);
      }
    }
    if (this.delayExplosion > 0.0F) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), loc);
      collide(false);
      drawing(false);

      DelayParam localDelayParam = new DelayParam(paramActor, paramString2, loc);
      if (p.jdField_z_of_type_Double < Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) + 5.0D) {
        if (Engine.land().isWater(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double))
          Explosions.Explode10Kg_Water(p, 2.0F, 2.0F);
        else {
          Explosions.Explode10Kg_Land(p, 2.0F, 2.0F);
        }
      }
      interpEndAll();
      new MsgInvokeMethod_Object("doDelayExplosion", localDelayParam).post(this, this.delayExplosion);
      if (this.sound != null)
        this.sound.cancel();
    } else {
      doExplosion(paramActor, paramString2);
    }
  }

  private boolean isPointApplicableForJump() {
    if (Engine.land().isWater(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double)) {
      return true;
    }

    float f = 200.0F;
    bPlateExist = false;
    bPlateGround = false;
    p.get(corn);
    Engine.drawEnv().getFiltered((AbstractCollection)null, corn.jdField_x_of_type_Double - f, corn.jdField_y_of_type_Double - f, corn.jdField_x_of_type_Double + f, corn.jdField_y_of_type_Double + f, 1, plateFilter);

    if (bPlateExist) {
      return true;
    }
    int i = Engine.cur.land.HQ_RoadTypeHere(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double);
    switch (i) { case 1:
      return true;
    case 2:
      return true;
    case 3:
      return false;
    }

    return false;
  }

  public void doDelayExplosion(Object paramObject)
  {
    DelayParam localDelayParam = (DelayParam)paramObject;
    if (Actor.isValid(localDelayParam.other)) {
      localDelayParam.other.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), __loc);
      localDelayParam.loc.add(__loc);
      doExplosion(localDelayParam.other, localDelayParam.otherChunk, localDelayParam.loc.getPoint());
    } else {
      doExplosion(Engine.actorLand(), "Body", localDelayParam.p);
    }
  }

  protected void doExplosion(Actor paramActor, String paramString) {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    doExplosion(paramActor, paramString, p);
  }
  protected void doExplosion(Actor paramActor, String paramString, Point3d paramPoint3d) {
    Class localClass = getClass();
    float f1 = Property.floatValue(localClass, "power", 1000.0F);
    int i = Property.intValue(localClass, "powerType", 0);
    float f2 = Property.floatValue(localClass, "radius", 150.0F);
    MsgExplosion.send(paramActor, paramString, paramPoint3d, getOwner(), this.M, f1, i, f2);

    com.maddox.il2.objects.ActorCrater.initOwner = getOwner();
    Explosions.generate(paramActor, paramPoint3d, f1, i, f2);
    com.maddox.il2.objects.ActorCrater.initOwner = null;

    destroy();
  }

  public void interpolateTick()
  {
    this.curTm += Time.tickLenFs();
    Ballistics.updateBomb(this, this.M, this.S, this.J, this.DistFromCMtoStab);
    updateSound();
  }

  static float RndFloatSign(float paramFloat1, float paramFloat2)
  {
    paramFloat1 = World.Rnd().nextFloat(paramFloat1, paramFloat2);
    return World.Rnd().nextFloat(0.0F, 1.0F) > 0.5F ? paramFloat1 : -paramFloat1;
  }

  private static void randomizeStart(Orient paramOrient, Vector3d paramVector3d, float paramFloat, int paramInt)
  {
    if (paramInt != 0) {
      dir.set(RndFloatSign(0.1F, 1.0F), RndFloatSign(0.1F, 1.0F), RndFloatSign(0.1F, 1.0F));

      dir.normalize();
    } else {
      dir.set(1.0F, 0.0F, 0.0F);
      paramOrient.transform(dir);

      f1 = 0.04F;
      dir.add(World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1));

      dir.normalize();
    }
    paramOrient.setAT0(dir);

    paramVector3d.set(RndFloatSign(0.1F, 1.0F), RndFloatSign(0.1F, 1.0F), RndFloatSign(0.1F, 1.0F));

    paramVector3d.normalize();

    float f1 = Geom.DEG2RAD(RndFloatSign(2.0F, 35.0F));
    if (paramFloat > 60.0F) {
      float f2 = 0.05F;
      if (paramFloat < 350.0F) {
        f2 = 1.0F - (paramFloat - 60.0F) / 290.0F;
        f2 = f2 * 0.95F + 0.05F;
      }
      f1 *= f2;
    }
    if (paramInt != 0) {
      f1 *= 0.2F;
    }
    paramVector3d.scale(f1);
  }

  public double getSpeed(Vector3d paramVector3d)
  {
    if (paramVector3d != null)
      paramVector3d.set(this.speed);
    return this.speed.length();
  }
  public void setSpeed(Vector3d paramVector3d) {
    this.speed.set(paramVector3d);
  }

  protected void init(float paramFloat1, float paramFloat2) {
    if ((Actor.isValid(getOwner())) && (World.getPlayerAircraft() == getOwner()))
    {
      setName("_bomb_");
    }
    super.getSpeed(this.speed);

    this.S = (float)(3.141592653589793D * paramFloat1 * paramFloat1 / 4.0D);
    this.M = paramFloat2;

    this.M *= World.Rnd().nextFloat(1.0F, 1.06F);

    float f1 = paramFloat1 * 0.5F;
    float f2 = paramFloat1 * 4.0F;
    float f3 = f1;
    float f4 = f2 * 0.5F;
    this.J = (this.M * 0.1F * (f3 * f3 * f4 * f4));

    this.DistFromCMtoStab = (f2 * 0.05F);
  }

  protected void updateSound()
  {
    if (this.sound != null) {
      this.sound.setControl(200, (float)getSpeed(null));
      if (this.curTm < 5.0F) this.sound.setControl(201, this.curTm);
      else if (this.curTm < 5.0F + 2 * Time.tickConstLen()) this.sound.setControl(201, 5.0F);
    }
  }

  protected boolean haveSound()
  {
    return true;
  }

  public void start() {
    Class localClass = getClass();
    init(Property.floatValue(localClass, "kalibr", 0.082F), Property.floatValue(localClass, "massa", 6.8F));

    this.curTm = 0.0F;

    setOwner(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base(), false, false, false);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(null, null, true);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent());
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(or);
    randomizeStart(or, this.rotAxis, this.M, Property.intValue(localClass, "randomOrient", 0));
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(or);

    collide(true);
    interpPut(new Interpolater(), null, Time.current(), null);
    drawing(true);
    Object localObject;
    if ((Actor.isAlive(World.getPlayerAircraft())) && (getOwner() == World.getPlayerAircraft())) {
      World.cur().scoreCounter.bombFire += 1;
      World.cur(); localObject = World.getPlayerFM();
      ((FlightModel)localObject).M.computeParasiteMass(((FlightModel)localObject).CT.Weapons);

      ((FlightModel)localObject).getW().jdField_y_of_type_Double -= 0.0004F * Math.min(this.M, 50.0F);
    }

    if (Property.containsValue(localClass, "emitColor")) {
      localObject = new LightPointActor(new LightPointWorld(), new Point3d());
      ((LightPointActor)localObject).light.setColor((Color3f)Property.value(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)));
      ((LightPointActor)localObject).light.setEmit(Property.floatValue(localClass, "emitMax", 1.0F), Property.floatValue(localClass, "emitLen", 50.0F));
      this.draw.lightMap().put("light", localObject);
    }

    if (haveSound()) {
      localObject = Property.stringValue(localClass, "sound", null);
      if (localObject != null) this.sound = newSound((String)localObject, true); 
    }
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public Bomb() {
    String str = Property.stringValue(getClass(), "mesh", null);

    setMesh(MeshShared.get(str));
    this.flags |= 224;
    collide(false);
    drawing(true);
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      Bomb.this.interpolateTick();
      return true;
    }
  }

  static class DelayParam
  {
    Actor other;
    String otherChunk;
    Point3d p = new Point3d();
    Loc loc;

    DelayParam(Actor paramActor, String paramString, Loc paramLoc)
    {
      this.other = paramActor;
      this.otherChunk = paramString;
      paramLoc.get(this.p);
      if (Actor.isValid(paramActor)) {
        this.loc = new Loc();
        this.other.pos.getTime(Time.current(), Bomb.__loc);
        this.loc.sub(paramLoc, Bomb.__loc);
      }
    }
  }

  private static class PlateFilter
    implements ActorFilter
  {
    private PlateFilter()
    {
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (!(paramActor instanceof Plate)) return true;
      Mesh localMesh = ((ActorMesh)paramActor).mesh();
      localMesh.getBoundBox(Bomb.plateBox);
      Bomb.corn1.set(Bomb.corn);
      Loc localLoc = paramActor.pos.getAbs();
      localLoc.transformInv(Bomb.corn1);
      if ((Bomb.plateBox[0] - 2.5F < Bomb.corn1.jdField_x_of_type_Double) && (Bomb.corn1.jdField_x_of_type_Double < Bomb.plateBox[3] + 2.5F) && (Bomb.plateBox[1] - 2.5F < Bomb.corn1.jdField_y_of_type_Double) && (Bomb.corn1.jdField_y_of_type_Double < Bomb.plateBox[4] + 2.5F))
      {
        Bomb.access$302(true);
        Bomb.access$402(((Plate)paramActor).isGround());
      }
      return true;
    }

    PlateFilter(Bomb.1 param1)
    {
      this();
    }
  }
}
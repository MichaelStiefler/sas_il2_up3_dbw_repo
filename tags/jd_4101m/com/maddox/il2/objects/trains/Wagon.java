package com.maddox.il2.objects.trains;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTrain;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public abstract class Wagon extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Prey, TgtTrain
{
  private static final float PROBAB_DEATH_WHEN_SHOT = 0.04F;
  private float hook1 = 1.0F;
  private float hook2 = -1.0F;
  private float heightAboveLandSurface = 0.0F;
  private String meshLive;
  private String meshDead;
  protected byte crushSeed;
  protected float life = 0.017F;
  protected float ignoreTNT = 0.35F;
  protected float killTNT = 1.9F;
  protected int bodyMaterial = 2;

  private static float[] tmp_atk = new float[3];
  private static RangeRandom tmp_rnd = new RangeRandom(0L);

  private NetMsgFiltered outCommand = new NetMsgFiltered();

  protected void forgetAllAiming()
  {
  }

  public int HitbyMask()
  {
    return -1;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (IsDamaged()) {
      return -1;
    }

    if (paramArrayOfBulletProperties.length == 1) {
      return 0;
    }

    if (paramArrayOfBulletProperties.length <= 0) {
      return -1;
    }

    if (paramArrayOfBulletProperties[0].power <= 0.0F)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].power <= 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 0)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if (IsDamaged()) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (IsDamaged()) {
      return false;
    }

    if (paramInt != 0) {
      return false;
    }

    if (paramPoint3d != null) {
      paramPoint3d.set(0.0D, 0.0D, 0.0D);
    }
    return true;
  }

  public final boolean isStaticPos()
  {
    return false;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private final boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  final boolean IsDamaged()
  {
    return this.life <= 0.0F;
  }

  final boolean IsDead()
  {
    return this.life < 0.0F;
  }

  final boolean IsDeadOrDying() {
    return this.life <= 0.0F;
  }

  final float getLength()
  {
    return this.hook1 - this.hook2;
  }

  private void changePoseAsCrushed(Point3d paramPoint3d, Orient paramOrient) {
    tmp_rnd.setSeed(211 * this.crushSeed);

    paramOrient.get(tmp_atk);
    tmp_atk[0] += tmp_rnd.nextFloat(-13.0F, 13.0F);
    tmp_atk[1] += tmp_rnd.nextFloat(-2.0F, 2.0F);
    tmp_atk[2] += tmp_rnd.nextFloat(-8.0F, 8.0F);
    paramOrient.set(tmp_atk[0], tmp_atk[1], tmp_atk[2]);

    paramPoint3d.x += tmp_rnd.nextDouble(-0.8D, 0.8D);
    paramPoint3d.y += tmp_rnd.nextDouble(-0.9D, 0.9D);
    paramPoint3d.z += tmp_rnd.nextDouble(-0.25D, 0.0D);
  }

  void place(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2) {
      return;
    }

    Orient localOrient = new Orient();
    Point3d localPoint3d = new Point3d();
    localPoint3d.interpolate(paramPoint3d1, paramPoint3d2, this.hook1 / (this.hook1 - this.hook2));
    localPoint3d.z += this.heightAboveLandSurface;
    Vector3d localVector3d = new Vector3d();
    localVector3d.sub(paramPoint3d1, paramPoint3d2);
    localOrient.setAT0(localVector3d);

    if (this.life < 0.0F) {
      changePoseAsCrushed(localPoint3d, localOrient);
    }

    this.pos.setAbs(localPoint3d, localOrient);

    if (paramBoolean1)
      this.pos.reset();
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof Wagon)) {
      Actor localActor1 = getOwner();
      Actor localActor2 = paramActor.getOwner();
      if (localActor1 == localActor2) {
        paramArrayOfBoolean[0] = false;
        return;
      }
      if ((((Train)localActor1).stoppedForever()) && 
        (((Train)localActor2).stoppedForever())) {
        paramArrayOfBoolean[0] = false;
        return;
      }

      return;
    }

    if ((((Train)getOwner()).stoppedForever()) && ((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos()))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }

    if ((paramActor instanceof BridgeSegment)) {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.life < 0.0F) {
      return;
    }

    if (isNetMirror()) {
      return;
    }

    if ((paramActor instanceof Wagon))
    {
      LifeChanged(false, 0.0F, paramActor, false);
    }
  }

  protected float killProbab(float paramFloat)
  {
    float f1 = this.life;

    float f2 = 3.9E+009F * (f1 * f1 * f1);
    float f3 = paramFloat / f2;
    float f4;
    if (f3 <= 1.0F) {
      f3 = f3 * 2.0F - 1.0F;
      if (f3 <= 0.0F) {
        return 0.0F;
      }
      f4 = f3 * 0.04F;
    } else {
      if (f3 >= 10.0F) {
        return 1.0F;
      }
      f3 = (f3 - 1.0F) / 9.0F;
      f4 = 0.04F + 0.96F * f3;
    }

    return f4;
  }

  public void msgShot(Shot paramShot)
  {
    if ((paramShot.chunkName.startsWith("Armor")) && (paramShot.power <= 20450.0F)) {
      return;
    }
    paramShot.bodyMaterial = this.bodyMaterial;

    if (IsDamaged()) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((isNetMirror()) && 
      (paramShot.isMirage())) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.125F)) {
        return;
      }

      LifeChanged(isNetMirror(), 0.0F, paramShot.initiator, false);
      return;
    }

    if (!RndB(killProbab(paramShot.power))) {
      return;
    }

    LifeChanged(isNetMirror(), 0.0F, paramShot.initiator, false);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (IsDamaged()) {
      return;
    }

    if ((isNetMirror()) && 
      (paramExplosion.isMirage())) {
      return;
    }

    if (Explosion.killable(this, paramExplosion.receivedTNT_1meter(this), this.ignoreTNT, this.killTNT, 0.0F))
    {
      LifeChanged(isNetMirror(), 0.0F, paramExplosion.initiator, false);
    }
  }

  protected void hiddenexplode()
  {
  }

  protected void explode(Actor paramActor)
  {
    new MsgAction(0.0D, this) {
      public void doAction(Object paramObject) { Wagon localWagon = (Wagon)paramObject;
        try
        {
          Eff3DActor.New(localWagon, new HookNamed(localWagon, "Damage"), null, 1.0F, "Effects/Smokes/WagonFC.eff", 60.0F);
        }
        catch (ActorException localActorException)
        {
          Eff3DActor.New(localWagon, new HookNamed(localWagon, "Select1"), null, 1.0F, "Effects/Smokes/WagonFC.eff", 60.0F);
        }
      }
    };
    new MsgAction(2.5D) {
      public void doAction() { Point3d localPoint3d = new Point3d();
        Wagon.this.pos.getAbs(localPoint3d);
        Explosions.ExplodeVagonArmor(localPoint3d, localPoint3d, 2.0F);
      }
    };
    new MsgAction(4.5D, new Pair(this, paramActor)) {
      public void doAction(Object paramObject) {
        Actor localActor = Wagon.this.getOwner();
        if (localActor != null) {
          ((Train)localActor).wagonDied(((Wagon.Pair)paramObject).victim, ((Wagon.Pair)paramObject).initiator);
        }
        Wagon.this.life = -1.0F;
        Wagon.this.ActivateMesh();
      }
    };
  }

  private final void LifeChanged(boolean paramBoolean1, float paramFloat, Actor paramActor, boolean paramBoolean2) {
    if (paramBoolean2) {
      if (paramFloat > 0.0F)
        this.life = paramFloat;
      else {
        this.life = -1.0F;
      }
      if (this.life < 0.0F)
      {
        this.crushSeed = (byte)World.Rnd().nextInt(1, 127);
        World.onActorDied(this, paramActor);
        hiddenexplode();
      }
      ActivateMesh();
      return;
    }

    if (paramFloat <= 0.0F) {
      if (this.life <= 0.0F) {
        return;
      }
      if (paramBoolean1) {
        this.life = 0.001F;
        send_DeathRequest(paramActor);
        return;
      }
      this.life = 0.0F;
    } else {
      this.life = paramFloat;
      return;
    }

    this.crushSeed = (byte)World.Rnd().nextInt(1, 127);

    explode(paramActor);

    World.onActorDied(this, paramActor);

    if (!isNetMirror())
      send_DeathCommand(paramActor);
  }

  public void absoluteDeath(Actor paramActor)
  {
    if (!getDiedFlag()) {
      World.onActorDied(this, paramActor);
    }

    destroy();
  }

  public void destroy()
  {
    super.destroy();
  }

  protected void ActivateMesh()
  {
    boolean bool = IsDead();
    setMesh(bool ? this.meshDead : this.meshLive);
    if (!bool) {
      this.heightAboveLandSurface = 0.0F;
      int i = hierMesh().hookFind("Ground_Level");
      Matrix4d localMatrix4d;
      if (i != -1) {
        localMatrix4d = new Matrix4d();
        hierMesh().hookMatrix(i, localMatrix4d);
        this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
      }
      i = hierMesh().hookFind("Select1");
      if (i != -1) {
        localMatrix4d = new Matrix4d();
        hierMesh().hookMatrix(i, localMatrix4d);
        this.hook1 = (float)localMatrix4d.m03;
      } else {
        throw new ActorException("Wagon: hook Select1 not found in " + this.meshLive);
      }
      i = hierMesh().hookFind("Select2");
      if (i != -1) {
        localMatrix4d = new Matrix4d();
        hierMesh().hookMatrix(i, localMatrix4d);
        this.hook2 = (float)localMatrix4d.m03;
      } else {
        throw new ActorException("Wagon: hook Select2 not found in " + this.meshLive);
      }
      if (this.hook1 <= this.hook2)
        throw new ActorException("Wagon: hooks SelectX placed incorrectly in " + this.meshLive);
    }
  }

  public Wagon(Train paramTrain, String paramString1, String paramString2)
  {
    super(paramString1);

    collide(true);
    drawing(true);
    setOwner(paramTrain);

    setArmy(paramTrain.getArmy());

    this.meshLive = new String(paramString1);
    this.meshDead = new String(paramString2);

    this.life = 1.0E-005F;
    ActivateMesh();

    int i = Mission.cur().getUnitNetIdRemote(this);
    NetChannel localNetChannel = Mission.cur().getNetMasterChannel();
    if (localNetChannel == null)
      this.net = new Master(this);
    else if (i != 0)
      this.net = new Mirror(this, localNetChannel, i);
  }

  private void send_DeathCommand(Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(68);

      localNetMsgGuaranted.writeNetObj(paramActor.net);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  protected void send_FireCommand(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (!isNetMaster()) {
      return;
    }
    if (!this.net.isMirrored()) {
      return;
    }
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet())) {
      return;
    }

    paramInt2 &= 255;

    if (paramFloat < 0.0F)
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(84);
        this.outCommand.writeByte(paramInt1);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt2);
        this.outCommand.setIncludeTime(false);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException1) {
        System.out.println(localException1.getMessage());
        localException1.printStackTrace();
      }
    else
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(70);
        this.outCommand.writeByte(paramInt1);
        this.outCommand.writeFloat(paramFloat);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.writeByte(paramInt2);
        this.outCommand.setIncludeTime(true);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException2) {
        System.out.println(localException2.getMessage());
        localException2.printStackTrace();
      }
  }

  private void send_DeathRequest(Actor paramActor)
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.net.masterChannel() instanceof NetChannelInStream))
      return;
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(68);
      localNetMsgFiltered.writeNetObj(paramActor.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  protected void Track_Mirror(int paramInt1, Actor paramActor, int paramInt2)
  {
    if (IsDamaged())
      return;
  }

  protected void Fire_Mirror(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (IsDamaged())
      return;
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();

    if ((this instanceof LocomotiveVerm))
    {
      Train localTrain = (Train)getOwner();

      localNetMsgGuaranted.writeByte(localTrain.isAnybodyDead() ? 115 : 83);

      localNetMsgGuaranted.writeFloat(this.life);

      Train.TrainState localTrainState = new Train.TrainState();
      localTrain.getStateData(localTrainState);

      localNetMsgGuaranted.writeInt(localTrainState._headSeg);
      localNetMsgGuaranted.writeDouble(localTrainState._headAlong);
      localNetMsgGuaranted.writeFloat(localTrainState._curSpeed);
      localNetMsgGuaranted.writeDouble(localTrainState._milestoneDist);
      localNetMsgGuaranted.writeFloat(localTrainState._requiredSpeed);
      localNetMsgGuaranted.writeFloat(localTrainState._maxAcceler);
    } else {
      localNetMsgGuaranted.writeByte(73);

      localNetMsgGuaranted.writeFloat(this.life);
    }

    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public class MyMsgAction extends MsgAction
  {
    Object obj2;

    public void doAction(Object paramObject) {
    }

    public MyMsgAction(double arg2, Object paramObject2, Object arg5) {
      super(paramObject2);
      Object localObject1;
      this.obj2 = localObject1;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      int k;
      if (paramNetMsgInput.isGuaranted())
      {
        k = paramNetMsgInput.readByte();
        float f1;
        Object localObject;
        switch (k) {
        case 83:
        case 115:
          if (isMirrored()) {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted);
          }
          f1 = paramNetMsgInput.readFloat();
          if (f1 <= 0.0F) {
            f1 = -1.0F;
          }

          Train.TrainState localTrainState = new Train.TrainState();

          localTrainState._headSeg = paramNetMsgInput.readInt();
          localTrainState._headAlong = paramNetMsgInput.readDouble();
          localTrainState._curSpeed = paramNetMsgInput.readFloat();
          localTrainState._milestoneDist = paramNetMsgInput.readDouble();
          localTrainState._requiredSpeed = paramNetMsgInput.readFloat();
          localTrainState._maxAcceler = paramNetMsgInput.readFloat();

          Wagon.this.LifeChanged(false, f1, null, true);

          if (Wagon.this.getOwner() != null) {
            boolean bool = k == 115;
            ((Train)Wagon.this.getOwner()).setStateDataMirror(localTrainState, bool);
          }

          Wagon.this.forgetAllAiming();
          return true;
        case 73:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          f1 = paramNetMsgInput.readFloat();
          if (f1 <= 0.0F) {
            f1 = -1.0F;
          }
          Wagon.this.LifeChanged(false, f1, null, true);

          Wagon.this.forgetAllAiming();
          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }

          if (Wagon.this.life > 0.0F) {
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor2 = localObject == null ? null : ((ActorNet)localObject).actor();
            Wagon.this.LifeChanged(false, 0.0F, localActor2, false);
          }
          return true;
        }
        return false;
      }
      int i;
      NetObj localNetObj;
      Actor localActor1;
      switch (paramNetMsgInput.readByte())
      {
      case 84:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        i = paramNetMsgInput.readByte();

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        int j = paramNetMsgInput.readUnsignedByte();

        Wagon.this.Track_Mirror(i, localActor1, j);

        break;
      case 70:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        i = paramNetMsgInput.readByte();

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();

        float f2 = paramNetMsgInput.readFloat();
        float f3 = 0.001F * (float)(Message.currentGameTime() - Time.current()) + f2;

        k = paramNetMsgInput.readUnsignedByte();

        Wagon.this.Fire_Mirror(i, localActor1, k, f3);

        break;
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
      }

      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return true;
      }
      if (paramNetMsgInput.readByte() != 68)
        return false;
      if (Wagon.this.life <= 0.0F) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      Wagon.this.LifeChanged(false, 0.0F, localActor, false);
      return true;
    }
  }

  public class Pair
  {
    Wagon victim;
    Actor initiator;

    Pair(Wagon paramActor, Actor arg3)
    {
      this.victim = paramActor;
      Object localObject;
      this.initiator = localObject;
    }
  }
}
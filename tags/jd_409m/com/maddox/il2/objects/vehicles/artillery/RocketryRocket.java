package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Time;

public class RocketryRocket extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Prey
{
  private static final int DMG_LWING = 1;
  private static final int DMG_RWING = 2;
  private static final int DMG_LHYRO = 4;
  private static final int DMG_RHYRO = 8;
  private static final int DMG_ENGINE = 16;
  private static final int DMG_FUEL = 32;
  private static final int DMG_DEAD = 64;
  private static final int DMG_ANY_ = 127;
  private static final int STA_HELL = -2;
  private static final int STA_WAIT = -1;
  private static final int STARMP_RAMP = 0;
  private static final int STARMP_GOUP = 1;
  private static final int STARMP_SPEEDUP = 2;
  private static final int STARMP_TRAVEL = 3;
  private static final int STARMP_GODOWN = 4;
  private static final int STARMP_GROUND = 5;
  private static final int STAAIR_TRAVEL = 0;
  private static final int STAAIR_GODOWN = 1;
  private static final int STAAIR_GROUND = 2;
  private int dmg = 0;

  private int fallMode = -1;
  private float fallVal = 0.0F;

  private int sta = -1;

  private RocketryGeneric.TrajSeg[] traj = null;

  private RocketryGeneric ramp = null;

  private RocketryWagon wagon = null;
  int idR;
  int randseed;
  long timeOfStartMS;
  private long countdownTicks = 0L;

  private Eff3DActor eng_trail = null;

  private static Point3d tmpP = new Point3d();
  private static Vector3d tmpV = new Vector3d();
  private static Vector3d tmpV3d0 = new Vector3d();
  private static Vector3d tmpV3d1 = new Vector3d();
  private static Loc tmpL = new Loc();
  private static RangeRandom rndSeed = new RangeRandom();

  private final boolean Corpse()
  {
    return (this.dmg & 0x40) != 0;
  }

  boolean isDamaged() {
    return this.dmg != 0;
  }

  boolean isOnRamp() {
    return (!this.ramp.prop.air) && (this.sta <= 0);
  }

  void silentDeath() {
    if (this.wagon != null) {
      this.wagon.forgetRocket();
      this.wagon = null;
    }
    destroy();
  }

  void forgetWagon() {
    this.wagon = null;
  }

  public void sendRocketStateChange(char paramChar, Actor paramActor) {
    this.ramp.sendRocketStateChange(this, paramChar, paramActor);
  }

  private static final long SecsToTicks(float paramFloat)
  {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (Corpse()) {
      paramArrayOfBoolean[0] = false;
      return;
    }

    if (paramActor == this.ramp) {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (Corpse()) {
      return;
    }
    if (!Actor.isValid(paramActor)) {
      return;
    }
    if (paramString1 == null) {
      return;
    }
    if ((paramActor.net != null) && (paramActor.net.isMirror())) {
      return;
    }

    if ((paramActor instanceof Aircraft))
    {
      if (paramString1.startsWith("Wing"))
      {
        return;
      }
    }

    if (paramString1.startsWith("WingLIn"))
    {
      sendRocketStateChange('l', paramActor);
    } else if (paramString1.startsWith("WingRIn"))
    {
      sendRocketStateChange('r', paramActor);
    } else if (paramString1.startsWith("Engine1"))
    {
      sendRocketStateChange('e', paramActor);
    }
    else
      sendRocketStateChange('x', paramActor);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (Corpse()) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((paramShot.chunkName == null) || (paramShot.chunkName.equals("")) || (paramShot.chunkName.equals("Body")))
    {
      return;
    }

    float f1 = 0.0F;
    float f2 = 0.0F;
    char c = ' ';
    if (paramShot.chunkName.equals("CF_D0")) {
      c = 'x';
      f1 = this.ramp.prop.DMG_WARHEAD_MM;
      f2 = this.ramp.prop.DMG_WARHEAD_PROB;
    } else if (paramShot.chunkName.equals("Tail1_D0")) {
      c = 'f';
      f1 = this.ramp.prop.DMG_FUEL_MM;
      f2 = this.ramp.prop.DMG_FUEL_PROB;
    } else if (paramShot.chunkName.equals("Engine1_D0")) {
      c = 'e';
      f1 = this.ramp.prop.DMG_ENGINE_MM;
      f2 = this.ramp.prop.DMG_ENGINE_PROB;
    } else if (paramShot.chunkName.equals("WingLIn_D0")) {
      c = 'l';
      f1 = this.ramp.prop.DMG_WING_MM;
      f2 = this.ramp.prop.DMG_WING_PROB;
    } else if (paramShot.chunkName.equals("WingRIn_D0")) {
      c = 'r';
      f1 = this.ramp.prop.DMG_WING_MM;
      f2 = this.ramp.prop.DMG_WING_PROB;
    }

    if (f2 <= 0.0F) {
      return;
    }

    if (!Aircraft.isArmorPenetrated(f1, paramShot)) {
      return;
    }

    if (RocketryGeneric.RndF(0.0F, 1.0F) > f2) {
      return;
    }

    sendRocketStateChange(c, paramShot.initiator);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (Corpse()) {
      return;
    }

    if ((this.ramp.prop.air) && (this.sta == -1)) {
      return;
    }

    float f = paramExplosion.receivedTNT_1meter(this);
    if (f <= 0.0F) {
      return;
    }

    if (f >= this.ramp.prop.DMG_WARHEAD_TNT) {
      sendRocketStateChange('x', paramExplosion.initiator);
    } else if (f >= this.ramp.prop.DMG_WING_TNT) {
      char c = RocketryGeneric.RndF(0.0F, 1.0F) > 0.5F ? 'l' : 'r';
      if (isCommandApplicable(c))
        sendRocketStateChange(c, paramExplosion.initiator);
    }
  }

  public int HitbyMask()
  {
    return -1;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (Corpse()) {
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

    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 2)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if (Corpse()) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (Corpse()) {
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

  private boolean isCommandApplicable(char paramChar)
  {
    if (Corpse()) {
      return false;
    }
    switch (paramChar) {
    case 'X':
    case 'x':
      return (this.dmg & 0x40) == 0;
    case 'E':
    case 'e':
      return (this.dmg & 0x10) == 0;
    case 'F':
    case 'f':
      return (this.dmg & 0x20) == 0;
    case 'L':
    case 'l':
      return (this.dmg & 0x1) == 0;
    case 'R':
    case 'r':
      return (this.dmg & 0x2) == 0;
    case 'A':
    case 'a':
      return (this.dmg & 0x4) == 0;
    case 'B':
    case 'b':
      return (this.dmg & 0x8) == 0;
    case 'C':
    case 'D':
    case 'G':
    case 'H':
    case 'I':
    case 'J':
    case 'K':
    case 'M':
    case 'N':
    case 'O':
    case 'P':
    case 'Q':
    case 'S':
    case 'T':
    case 'U':
    case 'V':
    case 'W':
    case 'Y':
    case 'Z':
    case '[':
    case '\\':
    case ']':
    case '^':
    case '_':
    case '`':
    case 'c':
    case 'd':
    case 'g':
    case 'h':
    case 'i':
    case 'j':
    case 'k':
    case 'm':
    case 'n':
    case 'o':
    case 'p':
    case 'q':
    case 's':
    case 't':
    case 'u':
    case 'v':
    case 'w': } return false;
  }

  char handleCommand(char paramChar, int paramInt, Actor paramActor)
  {
    if (!isCommandApplicable(paramChar)) {
      return '\000';
    }

    if (isOnRamp()) {
      paramChar = 'X';
      if (this.wagon != null) {
        this.wagon.silentDeath();
        this.wagon = null;
      }
    }

    switch (paramChar)
    {
    case 'X':
    case 'x':
      this.dmg |= 64;
      hierMesh().chunkVisible("CF_D0", false);
      hierMesh().chunkVisible("CF_D1", true);
      tmpL.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
      Explosions.HydrogenBalloonExplosion(tmpL, null);

      new MyMsgAction(0.0D, paramActor, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint()) {
        public void doAction(Object paramObject) {
          MsgExplosion.send(null, "Body", this.posi, (Actor)paramObject, 0.0F, RocketryRocket.this.ramp.prop.MASS_TNT, 0, RocketryRocket.this.ramp.prop.EXPLOSION_RADIUS);
        }
      };
      this.ramp.forgetRocket(this);
      collide(false);
      drawing(false);
      this.countdownTicks = SecsToTicks(0.5F);
      breakSounds();

      return paramChar;
    case 'E':
    case 'e':
      this.dmg |= 16;
      Eff3DActor.finish(this.eng_trail);
      this.eng_trail = null;
      hierMesh().chunkVisible("Engine1_D0", false);
      hierMesh().chunkVisible("Engine1_D1", true);
      Eff3DActor.New(this, findHook("_Engine1Burn"), null, 1.0F, "3DO/Effects/Aircraft/FireSPD.eff", -1.0F);
      Eff3DActor.New(this, findHook("_Engine1Burn"), null, 1.0F, "3DO/Effects/Aircraft/BlackHeavySPD.eff", -1.0F);
      Eff3DActor.New(this, findHook("_Engine1Burn"), null, 1.0F, "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", -1.0F);
      breakSounds();
      if (this.fallMode < 0) {
        startFalling(paramInt, 0, 0.0F);
      }
      return paramChar;
    case 'F':
    case 'f':
      this.dmg |= 32;
      hierMesh().chunkVisible("Tail1_D0", false);
      hierMesh().chunkVisible("Tail1_D1", true);
      Eff3DActor.New(this, findHook("_Tank1Burn"), null, 1.0F, "3DO/Effects/Aircraft/FireSPD.eff", -1.0F);
      Eff3DActor.New(this, findHook("_Tank1Burn"), null, 1.0F, "3DO/Effects/Aircraft/BlackHeavySPD.eff", -1.0F);
      Eff3DActor.New(this, findHook("_Tank1Burn"), null, 1.0F, "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", -1.0F);
      this.countdownTicks = SecsToTicks(rndSeed.nextFloat(5.0F, 40.0F));
      return paramChar;
    case 'L':
    case 'l':
      this.dmg |= 1;
      hierMesh().chunkVisible("WingLIn_D0", false);
      hierMesh().chunkVisible("WingLIn_D1", true);
      if (this.fallMode < 0) {
        startFalling(paramInt, 1, 90.0F);
      }
      return paramChar;
    case 'R':
    case 'r':
      this.dmg |= 2;
      hierMesh().chunkVisible("WingRIn_D0", false);
      hierMesh().chunkVisible("WingRIn_D1", true);
      if (this.fallMode < 0) {
        startFalling(paramInt, 1, -90.0F);
      }
      return paramChar;
    case 'A':
    case 'a':
      this.dmg |= 4;
      if (this.fallMode < 0) {
        startFalling(paramInt, 2, 5.0F);
      }
      return paramChar;
    case 'B':
    case 'b':
      this.dmg |= 8;
      if (this.fallMode < 0) {
        startFalling(paramInt, 2, -5.0F);
      }
      return paramChar;
    case 'C':
    case 'D':
    case 'G':
    case 'H':
    case 'I':
    case 'J':
    case 'K':
    case 'M':
    case 'N':
    case 'O':
    case 'P':
    case 'Q':
    case 'S':
    case 'T':
    case 'U':
    case 'V':
    case 'W':
    case 'Y':
    case 'Z':
    case '[':
    case '\\':
    case ']':
    case '^':
    case '_':
    case '`':
    case 'c':
    case 'd':
    case 'g':
    case 'h':
    case 'i':
    case 'j':
    case 'k':
    case 'm':
    case 'n':
    case 'o':
    case 'p':
    case 'q':
    case 's':
    case 't':
    case 'u':
    case 'v':
    case 'w': } return '\000';
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private int chooseTrajectorySegment(float paramFloat)
  {
    for (int i = 0; i < this.traj.length; i++) {
      if (paramFloat < this.traj[i].t0) {
        return i - 1;
      }
    }
    if (paramFloat < this.traj[(i - 1)].t0 + this.traj[(i - 1)].t) {
      return i - 1;
    }
    return -2;
  }

  private void computeCurLoc(int paramInt, float paramFloat, Loc paramLoc)
  {
    if (paramInt < 0) {
      paramLoc.getPoint().set(this.traj[0].pos0);
      if (this.traj[0].v0.lengthSquared() > 0.0D)
        paramLoc.getOrient().setAT0(this.traj[0].v0);
      else {
        paramLoc.getOrient().setAT0(this.traj[0].a);
      }
      return;
    }

    paramFloat = (float)(paramFloat - this.traj[paramInt].t0);

    tmpV3d0.scale(this.traj[paramInt].v0, paramFloat);
    tmpV3d1.scale(this.traj[paramInt].a, paramFloat * paramFloat * 0.5D);
    tmpV3d0.add(tmpV3d1);
    tmpV3d0.add(this.traj[paramInt].pos0);
    paramLoc.getPoint().set(tmpV3d0);

    tmpV3d0.scale(this.traj[paramInt].a, paramFloat);
    tmpV3d0.add(this.traj[paramInt].v0);
    if (tmpV3d0.lengthSquared() <= 0.0D) {
      tmpV3d0.set(this.traj[paramInt].a);
    }
    paramLoc.getOrient().setAT0(tmpV3d0);
  }

  private void computeCurPhys(int paramInt, float paramFloat, Point3d paramPoint3d, Vector3d paramVector3d)
  {
    if (paramInt < 0) {
      paramPoint3d.set(this.traj[0].pos0);
      if (this.traj[0].v0.lengthSquared() > 0.0D) {
        paramVector3d.set(this.traj[0].v0);
      } else {
        paramVector3d.set(this.traj[0].a);
        paramVector3d.normalize();
        paramVector3d.scale(0.001D);
      }
      return;
    }

    paramFloat = (float)(paramFloat - this.traj[paramInt].t0);

    tmpV3d0.scale(this.traj[paramInt].v0, paramFloat);
    tmpV3d1.scale(this.traj[paramInt].a, paramFloat * paramFloat * 0.5D);
    tmpV3d0.add(tmpV3d1);
    tmpV3d0.add(this.traj[paramInt].pos0);
    paramPoint3d.set(tmpV3d0);

    tmpV3d0.scale(this.traj[paramInt].a, paramFloat);
    tmpV3d0.add(this.traj[paramInt].v0);
    if (tmpV3d0.lengthSquared() <= 0.0D) {
      paramVector3d.set(this.traj[paramInt].a);
      paramVector3d.normalize();
      paramVector3d.scale(0.001D);
    } else {
      paramVector3d.set(tmpV3d0);
    }
  }

  private void startFalling(int paramInt1, int paramInt2, float paramFloat)
  {
    long l = Time.current();
    float f = (float)(l - this.timeOfStartMS) * 0.001F;

    int i = chooseTrajectorySegment(f);
    if (i == -2)
    {
      this.ramp.forgetRocket(this);
      silentDeath();
      return;
    }

    computeCurPhys(i, f, tmpP, tmpV);

    this.traj = this.ramp._computeFallTrajectory_(paramInt1, tmpP, tmpV);

    this.fallMode = paramInt2;
    this.fallVal = paramFloat;
    this.timeOfStartMS = l;
    this.sta = chooseTrajectorySegment(0.0F);
  }

  private void advanceState(int paramInt1, int paramInt2)
  {
    this.sta = paramInt1;

    while (this.sta < paramInt2) {
      this.sta += 1;

      if (this.ramp.prop.air) {
        switch (this.sta) {
        case 0:
          collide(true);
          drawing(true);
          this.eng_trail = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Tracers/ImpulseRocket/rocket.eff", -1.0F);
          newSound(this.ramp.prop.soundName, true);
          break;
        case 1:
          Eff3DActor.finish(this.eng_trail);
          this.eng_trail = null;
          breakSounds();
        }
      }
      else {
        switch (this.sta) {
        case 0:
          this.eng_trail = Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Tracers/ImpulseRocket/rocket.eff", -1.0F);
          newSound(this.ramp.prop.soundName, true);
          if (this.wagon == null) continue;
          this.wagon.forgetRocket();
          this.wagon = null; break;
        case 1:
          break;
        case 4:
          Eff3DActor.finish(this.eng_trail);
          this.eng_trail = null;
          breakSounds();
        case 2:
        case 3:
        }
      }
    }
    this.sta = paramInt2;
  }

  void changeLaunchTimeIfCan(long paramLong)
  {
    if (Corpse()) {
      return;
    }
    if (this.sta == -1) {
      if (Time.current() < paramLong)
        this.timeOfStartMS = paramLong;
      else {
        this.timeOfStartMS = Time.current();
      }
      if (this.wagon != null)
        this.wagon.timeOfStartMS = this.timeOfStartMS;
    }
  }

  public RocketryRocket(RocketryGeneric paramRocketryGeneric, String paramString, int paramInt1, int paramInt2, long paramLong1, long paramLong2, RocketryGeneric.TrajSeg[] paramArrayOfTrajSeg)
  {
    super(paramString);
    this.ramp = paramRocketryGeneric;

    this.idR = paramInt1;

    this.randseed = paramInt2;
    this.traj = paramArrayOfTrajSeg;
    this.timeOfStartMS = paramLong1;

    this.dmg = 0;
    setArmy(this.ramp.getArmy());
    this.sta = -1;

    float f = (float)(paramLong2 - this.timeOfStartMS) * 0.001F;
    int i = chooseTrajectorySegment(f);

    if ((i == -2) || ((this.ramp.prop.air) && (i >= 2)) || ((!this.ramp.prop.air) && (i >= 5)))
    {
      this.dmg = 64;
      collide(false);
      drawing(false);
      return;
    }

    this.wagon = null;
    if (!this.ramp.prop.air) {
      RocketryGeneric.TrajSeg[] arrayOfTrajSeg = this.ramp._computeWagonTrajectory_(this.randseed);
      if (arrayOfTrajSeg == null)
      {
        this.dmg = 64;
        collide(false);
        drawing(false);
        return;
      }

      this.wagon = new RocketryWagon(this, this.ramp.meshNames.wagon, this.timeOfStartMS, paramLong2, arrayOfTrajSeg);

      if ((this.wagon == null) || (!this.wagon.isDrawing()))
      {
        this.dmg = 64;
        collide(false);
        drawing(false);
        return;
      }

    }

    if (this.ramp.prop.air) {
      collide(false);
      drawing(false);
    } else {
      collide(true);
      drawing(true);
    }

    setName(this.ramp.name() + "_" + paramInt1);
    if (this.sta != i) {
      advanceState(this.sta, i);
    }

    computeCurLoc(this.sta, f, tmpL);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(tmpL);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    dreamFire(true);

    if (!interpEnd("move"))
      interpPut(new Move(), "move", paramLong2, null); 
  }

  class Move extends Interpolate {
    Move() {
    }

    private void disappear() {
      RocketryRocket.this.ramp.forgetRocket((RocketryRocket)this.actor);
      if (RocketryRocket.this.wagon != null) {
        RocketryRocket.this.wagon.forgetRocket();
        RocketryRocket.access$102(RocketryRocket.this, null);
      }
      RocketryRocket.this.collide(false);
      RocketryRocket.this.drawing(false);
      RocketryRocket.this.postDestroy();
    }

    public boolean tick()
    {
      if (RocketryRocket.this.Corpse()) {
        if (RocketryRocket.access$306(RocketryRocket.this) > 0L) {
          return true;
        }
        disappear();
        return false;
      }

      long l = Time.current();
      float f1 = (float)(l - RocketryRocket.this.timeOfStartMS) * 0.001F;

      int i = RocketryRocket.this.chooseTrajectorySegment(f1);
      if (RocketryRocket.this.sta != i) {
        if (i == -2)
        {
          disappear();
          return false;
        }

        RocketryRocket.this.advanceState(RocketryRocket.this.sta, i);
      }

      RocketryRocket.this.computeCurLoc(RocketryRocket.this.sta, f1, RocketryRocket.tmpL);

      if (RocketryRocket.this.fallMode > 0) {
        float f2 = RocketryRocket.tmpL.getOrient().getYaw();
        float f3 = RocketryRocket.tmpL.getOrient().getPitch();
        float f4 = RocketryRocket.tmpL.getOrient().getRoll();
        float f5;
        if (RocketryRocket.this.fallMode == 0) {
          f5 = 0.0F;
        } else if (RocketryRocket.this.fallMode == 1) {
          f5 = f1 * RocketryRocket.this.fallVal;
          if (f5 >= 360.0F) {
            f5 %= 360.0F;
          } else if (f5 < -360.0F) {
            f5 = -f5;
            f5 %= 360.0F;
            f5 = -f5;
          }
        }
        else if (f1 <= 0.0F) {
          f5 = 0.0F;
        } else if (f1 >= RocketryRocket.this.fallVal) {
          f5 = 180.0F;
        } else {
          float f6 = Math.abs(RocketryRocket.this.fallVal);
          f5 = Geom.sinDeg(f1 / f6 * 180.0F);
          f5 = (float)(f5 * (RocketryRocket.this.fallVal < 0.0F ? 180.0D : -180.0D));
        }

        RocketryRocket.tmpL.getOrient().setYPR(f2, f3, f4 + f5);
      }

      int j = 0;
      if (((RocketryRocket.this.dmg & 0x20) != 0) && 
        (RocketryRocket.access$306(RocketryRocket.this) <= 0L)) {
        j = 1;
      }

      double d = Engine.land().HQ_Air(RocketryRocket.tmpL.getPoint().x, RocketryRocket.tmpL.getPoint().y);
      if (RocketryRocket.tmpL.getPoint().jdField_z_of_type_Double <= d) {
        RocketryRocket.tmpL.getPoint().jdField_z_of_type_Double = d;
        RocketryRocket.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(RocketryRocket.tmpL);
        j = 1;
      } else {
        RocketryRocket.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(RocketryRocket.tmpL);
      }

      if (j != 0) {
        RocketryRocket.this.handleCommand('X', RocketryGeneric.RndI(0, 65535), Engine.actorLand());
      }

      return true;
    }
  }

  public class MyMsgAction extends MsgAction
  {
    Point3d posi;

    public void doAction(Object paramObject)
    {
    }

    public MyMsgAction(double arg2, Object paramPoint3d, Point3d arg5)
    {
      super(paramPoint3d);
      Point3d localPoint3d1;
      this.posi = new Point3d(localPoint3d1);
    }
  }
}
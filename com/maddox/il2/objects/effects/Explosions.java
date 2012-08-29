package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3D;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSnapToLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.SfxExplosion;
import com.maddox.il2.objects.weapons.BallisticProjectile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

public class Explosions
{
  private static Orient o = new Orient();
  private static Loc l = new Loc();
  private static Loc rel = new Loc();
  private static Loc tmpLoc = new Loc();
  private static RangeRandom rnd = new RangeRandom();
  private static Point3d ap;
  private static final int LAND = 0;
  private static final int WATER = 1;
  private static final int OBJECT = 2;
  private static final int BOMB250 = 0;
  private static final int BOMB1000 = 2;
  private static final int RS82 = 1;
  public static final int HOUSEEXPL_WOOD_SMALL = 0;
  public static final int HOUSEEXPL_WOOD_MIDDLE = 1;
  public static final int HOUSEEXPL_ROCK_MIDDLE = 2;
  public static final int HOUSEEXPL_ROCK_BIG = 3;
  public static final int HOUSEEXPL_ROCK_HUGE = 4;
  public static final int HOUSEEXPL_FUEL_SMALL = 5;
  public static final int HOUSEEXPL_FUEL_BIG = 6;
  private static boolean bEnableActorCrater = true;

  public static void HydrogenBalloonExplosion(Loc paramLoc, Actor paramActor)
  {
    if (!Config.isUSE_RENDER()) return;
    Loc localLoc = new Loc();
    Vector3d localVector3d = new Vector3d();

    for (int i = 0; i < 2; i++) {
      localLoc.set(paramLoc);
      localLoc.getPoint().jdField_x_of_type_Double += World.Rnd().nextDouble(-12.0D, 12.0D);
      localLoc.getPoint().jdField_y_of_type_Double += World.Rnd().nextDouble(-12.0D, 12.0D);
      localLoc.getPoint().jdField_z_of_type_Double += World.Rnd().nextDouble(-3.0D, 3.0D);
      Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1.0F);
    }
    int j = World.Rnd().nextInt(2, 6);
    for (i = 0; i < j; i++) {
      localVector3d.set(World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-0.5F, 1.5F));
      localVector3d.normalize();
      localVector3d.scale(World.Rnd().nextFloat(4.0F, 15.0F));
      float f = World.Rnd().nextFloat(4.0F, 7.0F);
      BallisticProjectile localBallisticProjectile = new BallisticProjectile(paramLoc.getPoint(), localVector3d, f);
      Eff3DActor.New(localBallisticProjectile, null, null, 1.0F, "3DO/Effects/Aircraft/BlackHeavySPD.eff", f);
      Eff3DActor.New(localBallisticProjectile, null, null, 1.0F, "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", f);
      Eff3DActor.New(localBallisticProjectile, null, null, 1.0F, "3DO/Effects/Aircraft/FireSPD.eff", f);
    }
    SfxExplosion.crashAir(paramLoc.getPoint(), 1);
  }

  public static void runByName(String paramString1, ActorHMesh paramActorHMesh, String paramString2, String paramString3, float paramFloat)
  {
    runByName(paramString1, paramActorHMesh, paramString2, paramString3, paramFloat, null);
  }

  public static void runByName(String paramString1, ActorHMesh paramActorHMesh, String paramString2, String paramString3, float paramFloat, Actor paramActor)
  {
    HookNamed localHookNamed = null;
    if ((paramString2 != null) && (!paramString2.equals(""))) {
      int i = paramActorHMesh.mesh().hookFind(paramString2);
      if (i >= 0) {
        localHookNamed = new HookNamed(paramActorHMesh, paramString2);
      }
      else if ((paramString3 != null) && (!paramString3.equals(""))) {
        i = paramActorHMesh.mesh().hookFind(paramString3);
        if (i >= 0) {
          localHookNamed = new HookNamed(paramActorHMesh, paramString3);
        }

      }

    }

    if (paramString1.equalsIgnoreCase("Tank")) {
      Tank_Explode(paramActorHMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    } else if (paramString1.equalsIgnoreCase("_TankSmoke_")) {
      if (paramFloat > 0.0F) {
        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingFire.eff", paramFloat * 0.7F);

        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingSmoke.eff", paramFloat);
      }
    }
    else if (paramString1.equalsIgnoreCase("Car")) {
      Car_Explode(paramActorHMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      if (paramFloat > 0.0F) {
        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/CarDyingFire.eff", paramFloat * 0.7F);

        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/CarDyingSmoke.eff", paramFloat);
      }

    }
    else if (paramString1.equalsIgnoreCase("CarFuel")) {
      new MsgAction(0.0D, paramActorHMesh) {
        public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
          ((Actor)paramObject).pos.getAbs(localPoint3d);
          Explosions.ExplodeVagonFuel(localPoint3d, localPoint3d, 1.5F);
        }
      };
      if (paramFloat > 0.0F) {
        new MyMsgAction(0.43D, paramActorHMesh, paramActor) {
          public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
            ((Actor)paramObject).pos.getAbs(localPoint3d);
            float f1 = 25.0F;
            int i = 0;
            float f2 = 50.0F;
            MsgExplosion.send((Actor)paramObject, "Body", localPoint3d, (Actor)this.obj2, 0.0F, f1, i, f2);
          }
        };
        new MsgAction(1.2D, new MydataForSmoke(paramActorHMesh, paramFloat)) {
          public void doAction(Object paramObject) {
            Eff3DActor.New(((Explosions.MydataForSmoke)paramObject).a, null, null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((Explosions.MydataForSmoke)paramObject).tim);
          }
        };
      }
    }
    else if (paramString1.equalsIgnoreCase("Artillery")) {
      Antiaircraft_Explode(paramActorHMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      if (paramFloat > 0.0F)
      {
        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingFire.eff", paramFloat * 0.7F);

        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingSmoke.eff", paramFloat);
      }
    }
    else if (paramString1.equalsIgnoreCase("Stationary"))
    {
      Antiaircraft_Explode(paramActorHMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      if (paramFloat > 0.0F) {
        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingFire.eff", paramFloat * 0.7F);

        Eff3DActor.New(paramActorHMesh, localHookNamed, null, 1.0F, "Effects/Smokes/TankDyingSmoke.eff", paramFloat);
      }
    }
    else if (paramString1.equalsIgnoreCase("Aircraft"))
    {
      Antiaircraft_Explode(paramActorHMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    } else if (paramString1.equalsIgnoreCase("Aircraft")) {
      System.out.println("*** Unknown named explode: '" + paramString1 + "'");
    }
    else if (paramString1.equalsIgnoreCase("WagonWoodExplosives")) {
      new MsgAction(0.0D, paramActorHMesh) {
        public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
          ((Actor)paramObject).pos.getAbs(localPoint3d);
          Explosions.ExplodeVagonArmor(localPoint3d, localPoint3d, 2.0F);
        }
      };
      if (paramFloat > 0.0F) {
        new MyMsgAction(0.43D, paramActorHMesh, paramActor) {
          public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
            ((Actor)paramObject).pos.getAbs(localPoint3d);
            float f1 = 180.0F;
            int i = 0;
            float f2 = 140.0F;
            MsgExplosion.send((Actor)paramObject, "Body", localPoint3d, (Actor)this.obj2, 0.0F, f1, i, f2);
          }
        };
        new MsgAction(1.2D, new MydataForSmoke(paramActorHMesh, paramFloat)) {
          public void doAction(Object paramObject) {
            Eff3DActor.New(((Explosions.MydataForSmoke)paramObject).a, null, null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((Explosions.MydataForSmoke)paramObject).tim);
          }
        };
      }
    }
    else if (paramString1.equalsIgnoreCase("WagonWood")) {
      new MsgAction(0.0D, paramActorHMesh) {
        public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
          ((Actor)paramObject).pos.getAbs(localPoint3d);
          Explosions.ExplodeVagonArmor(localPoint3d, localPoint3d, 2.0F);
        }
      };
      if (paramFloat > 0.0F) {
        new MsgAction(1.2D, new MydataForSmoke(paramActorHMesh, paramFloat)) {
          public void doAction(Object paramObject) {
            Eff3DActor.New(((Explosions.MydataForSmoke)paramObject).a, null, null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((Explosions.MydataForSmoke)paramObject).tim);
          }
        };
      }
    }
    else if (paramString1.equalsIgnoreCase("WagonFuel")) {
      new MsgAction(0.0D, paramActorHMesh) {
        public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
          ((Actor)paramObject).pos.getAbs(localPoint3d);
          Explosions.ExplodeVagonFuel(localPoint3d, localPoint3d, 2.0F);
        }
      };
      if (paramFloat > 0.0F) {
        new MyMsgAction(0.43D, paramActorHMesh, paramActor) {
          public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
            ((Actor)paramObject).pos.getAbs(localPoint3d);
            float f1 = 180.0F;
            int i = 0;
            float f2 = 120.0F;
            MsgExplosion.send((Actor)paramObject, "Body", localPoint3d, (Actor)this.obj2, 0.0F, f1, i, f2);
          }
        };
        new MsgAction(1.2D, new MydataForSmoke(paramActorHMesh, paramFloat)) {
          public void doAction(Object paramObject) {
            Eff3DActor.New(((Explosions.MydataForSmoke)paramObject).a, null, null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((Explosions.MydataForSmoke)paramObject).tim);
          }
        };
      }
    }
    else if (paramString1.equalsIgnoreCase("WagonMetal")) {
      new MsgAction(0.0D, paramActorHMesh) {
        public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
          ((Actor)paramObject).pos.getAbs(localPoint3d);
          Explosions.ExplodeVagonArmor(localPoint3d, localPoint3d, 2.0F);
        }
      };
      if (paramFloat > 0.0F)
        new MsgAction(1.2D, new MydataForSmoke(paramActorHMesh, paramFloat)) {
          public void doAction(Object paramObject) {
            Eff3DActor.New(((Explosions.MydataForSmoke)paramObject).a, null, null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((Explosions.MydataForSmoke)paramObject).tim);
          }
        };
    }
  }

  public static void shot(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    l.set(paramPoint3d, o);
    Eff3DActor localEff3DActor = Eff3DActor.New(l, 2.0F, "effects/sprites/spritesun.eff", -1.0F);
    localEff3DActor.postDestroy(Time.current() + 500L);
  }

  public static void HouseExplode(int paramInt, Loc paramLoc, float[] paramArrayOfFloat)
  {
    if (!Config.isUSE_RENDER()) return;

    Point3d localPoint3d = new Point3d();
    String str1 = "";
    int i = 0;
    int j = 0;
    switch (paramInt) {
    case 0:
    case 1:
      str1 = "Wood";
      j = 4;
      break;
    case 2:
    case 3:
    case 4:
      str1 = "Rock";
      j = 3;
      break;
    case 5:
    case 6:
      str1 = "Fuel";
      i = 1;
      j = 5;
      break;
    default:
      System.out.println("WARNING: HouseExplode(): unknown type"); return;
    }

    String str2 = "effects/Explodes/Objects/House/" + str1 + "/Boiling.eff";
    String str3 = "effects/Explodes/Objects/House/" + str1 + "/Boiling2.eff";
    String str4 = "effects/Explodes/Objects/House/" + str1 + "/Pieces.eff";
    float f1 = 4.0F; float f2 = 1.0F;

    Eff3D.initSetBoundBox(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5]);
    Eff3DActor.New(paramLoc, 1.0F, str2, 3.0F);

    Eff3D.initSetBoundBox(paramArrayOfFloat[0] + (paramArrayOfFloat[3] - paramArrayOfFloat[0]) * 0.25F, paramArrayOfFloat[1] + (paramArrayOfFloat[4] - paramArrayOfFloat[1]) * 0.25F, paramArrayOfFloat[2], paramArrayOfFloat[3] - (paramArrayOfFloat[3] - paramArrayOfFloat[0]) * 0.25F, paramArrayOfFloat[4] - (paramArrayOfFloat[4] - paramArrayOfFloat[1]) * 0.25F, paramArrayOfFloat[2] + (paramArrayOfFloat[5] - paramArrayOfFloat[2]) * 0.5F);

    Eff3DActor.New(paramLoc, 1.0F, str3, 3.0F);

    SfxExplosion.building(paramLoc.getPoint(), j, paramArrayOfFloat);
  }

  private static void ExplodeSurfaceWave(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0)
      new ActorSnapToLand("3do/Effects/Explosion/DustRing.sim", true, l, 1.0F, paramFloat1, 1.0F, 0.0F, paramFloat2);
    else if (paramInt == 1)
      new ActorSnapToLand("3do/Effects/Explosion/WaterRing.sim", true, l, 0.2F, paramFloat1, 1.0F, 0.0F, paramFloat2);
  }

  private static void SurfaceLight(int paramInt, float paramFloat1, float paramFloat2)
  {
    new ActorSnapToLand("3do/Effects/Explosion/LandLight.sim", true, l, 1.0F, paramFloat1, paramInt == 0 ? 1.0F : 0.5F, 0.0F, paramFloat2);
  }

  private static void SurfaceCrater(int paramInt, float paramFloat1, float paramFloat2) {
    if (paramInt == 0) {
      new ActorSnapToLand("3do/Effects/Explosion/Crater.sim", true, l, 0.2F, paramFloat1, paramFloat1 + 2.0F, 1.0F, 0.0F, paramFloat2);

      if (bEnableActorCrater) {
        int i = 64;
        while (i >= 2) {
          if (paramFloat1 >= i) break;
          i /= 2;
        }if (i >= 2)
          new ActorCrater("3do/Effects/Explosion/Crater" + i + "/Live.sim", l, paramFloat2);
      }
    }
  }

  private static void fontain(Point3d paramPoint3d, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    int i = 4 + (int)(Math.random() * 2.0D);
    float f1 = 30.0F;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    switch (paramInt1)
    {
    case 0:
      String str1;
      float f2;
      float f4;
      float f3;
      float f5;
      if (paramInt2 == 2) {
        str1 = "Bomb1000"; f2 = 500.0F; f4 = 600.0F; f3 = 36.0F;
        i = 3 + (int)(Math.random() * 3.0D);
        f5 = 1.6F;
      }
      else if (paramInt2 == 0) {
        str1 = "Bomb250"; f2 = 250.0F; f4 = 300.0F; f3 = 18.0F;
        f5 = 0.8F;
      } else {
        str1 = "RS82"; f2 = 125.0F; f4 = 150.0F; f3 = 4.5F;
        i = 2 + (int)(Math.random() * 2.0D);
        f5 = 0.6F;
      }

      String str2 = "effects/Explodes/" + str1 + "/Land/Fontain.eff";
      String str3 = "effects/Explodes/" + str1 + "/Land/Peaces.eff";
      String str4 = "effects/Explodes/" + str1 + "/Land/Burn.eff";

      Eff3DActor localEff3DActor1 = Eff3DActor.New(l, 1.0F, str3, 3.5F);
      for (int j = 0; j < i; j++) {
        float f6 = (float)(360.0D * Math.random());
        float f7 = 90.0F + (2.0F * (float)Math.random() - 1.0F) * f1;
        o.set(f6, f7, 0.0F);
        l.set(paramPoint3d, o);
        Eff3DActor.New(l, 1.0F, str2, paramFloat1);
      }

      o.set(0.0F, 0.0F, 0.0F);
      l.set(paramPoint3d, o);
      ExplodeSurfaceWave(paramInt1, f2, f5);
      SurfaceLight(paramInt1, f4, 2.0F);
      SurfaceCrater(paramInt1, f3, 80.0F);

      o.set(0.0F, 90.0F, 0.0F);
      l.set(paramPoint3d, o);
      Eff3DActor localEff3DActor2 = Eff3DActor.New(l, 1.0F, str4, 1.0F);

      localEff3DActor2.postDestroy(Time.current() + 1500L);
      LightPointActor localLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d());
      localLightPointActor.light.setColor(1.0F, 0.9F, 0.5F);
      localLightPointActor.light.setEmit(1.0F, f4 * 2.0F);
      localEff3DActor2.draw.lightMap().put("light", localLightPointActor);

      break;
    case 1:
      if (paramInt2 == 2) {
        Eff3DActor.New(l, 1.0F, "effects/Explodes/Bomb1000/Water/Fontain.eff", paramFloat1);
      }
      else if (paramInt2 == 0)
        Eff3DActor.New(l, 1.0F, "effects/Explodes/Bomb250/Water/Fontain.eff", paramFloat1);
      else {
        Eff3DActor.New(l, 1.0F, "effects/Explodes/RS82/Water/Fontain.eff", paramFloat1);
      }
      o.set(0.0F, 0.0F, 0.0F);
      l.set(paramPoint3d, o);
      if (paramInt2 == 2) {
        ExplodeSurfaceWave(paramInt1, 750.0F, 15.0F);
      }
      else if (paramInt2 == 0)
        ExplodeSurfaceWave(paramInt1, 325.0F, 10.0F);
      else {
        ExplodeSurfaceWave(paramInt1, 50.0F, 7.0F);
      }

      break;
    case 2:
      Tank_Explode(paramPoint3d);
    }
  }

  public static void Tank_Explode(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);
    Tank_ExplodeCollapse(paramPoint3d);

    float f1 = 31.25F; float f3 = 150.0F; float f2 = 6.75F;
    int i = 0; int j = 1;

    o.set(0.0F, 0.0F, 0.0F); l.set(paramPoint3d, o);
    ExplodeSurfaceWave(i, f1, j == 0 ? 0.8F : 0.6F);
    SurfaceLight(i, f3, 0.3F);
  }

  public static void Antiaircraft_Explode(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    Tank_Explode(paramPoint3d);
  }
  public static void Car_Explode(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    Tank_Explode(paramPoint3d);
  }

  private static void Building_Explode(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    float f1 = 20.0F;
    int i = 3;
    Point3d localPoint3d = new Point3d();
    String str1 = "effects/Explodes/Objects/Building20m/SmokeBoiling.eff";
    String str2 = "effects/Explodes/Objects/Building20m/SmokeBoiling2.eff";
    float f2 = 4.0F; float f3 = 1.0F;
    for (int j = 0; j < i * i; j++)
    {
      double d1 = (Math.random() - 0.5D) * f1;
      double d2 = (Math.random() - 0.5D) * f1;
      localPoint3d.set(paramPoint3d); localPoint3d.jdField_x_of_type_Double += d1; localPoint3d.jdField_y_of_type_Double += d2;
      o.set(0.0F, 90.0F, 0.0F); l.set(localPoint3d, o);
      Eff3DActor.New(l, 1.0F, Math.random() < 0.5D ? str1 : str2, 3.0F);
    }

    o.set(0.0F, 0.0F, 0.0F); l.set(paramPoint3d, o);

    float f4 = 62.5F; float f6 = 150.0F; float f5 = 6.75F;
    int k = 0; int m = 0;
    ExplodeSurfaceWave(k, f4, m == 0 ? 0.8F : 0.6F);
  }

  public static void Tank_ExplodeCollapse(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    SfxExplosion.crashTank(paramPoint3d, 0);
    explodeCollapse(paramPoint3d);
  }

  private static void explodeCollapse(Point3d paramPoint3d)
  {
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);

    int i = 6 + (int)(Math.random() * 2.0D);
    float f1 = 60.0F;

    String str1 = "Objects/Tank_Collapse";

    float f2 = 31.25F; float f4 = 150.0F; float f3 = 6.75F;

    String str2 = "effects/Explodes/" + str1 + "/Peaces1.eff";
    String str3 = "effects/Explodes/" + str1 + "/Peaces2.eff";
    String str4 = "effects/Explodes/" + str1 + "/Sparks.eff";
    String str5 = "effects/Explodes/" + str1 + "/Burn.eff";
    String str6 = "effects/Explodes/" + str1 + "/SmokeBoiling.eff";

    Eff3DActor localEff3DActor1 = Eff3DActor.New(l, 1.0F, str2, 3.5F);
    Eff3DActor localEff3DActor2 = Eff3DActor.New(l, 1.0F, str3, 3.5F);
    localEff3DActor2 = Eff3DActor.New(l, 1.0F, str4, 0.5F);

    localEff3DActor2 = Eff3DActor.New(l, 1.0F, str6, 2.5F);

    Eff3DActor localEff3DActor3 = Eff3DActor.New(l, 1.0F, str5, 0.3F);

    localEff3DActor3.postDestroy(Time.current() + 1500L);

    LightPointActor localLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d(5.0D, 0.0D, 0.0D));
    localLightPointActor.light.setColor(1.0F, 0.9F, 0.5F);
    localLightPointActor.light.setEmit(1.0F, f4 * 2.0F);
    localEff3DActor3.draw.lightMap().put("light", localLightPointActor);
  }

  public static void Car_ExplodeCollapse(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    explodeCollapse(paramPoint3d);
  }

  public static void AirDrop_Land(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    float f1 = 4.0F; float f2 = 1.0F;
    if (Mission.isDeathmatch())
      bEnableActorCrater = false;
    fontain(paramPoint3d, f1, f2, 0, 0);
    bEnableActorCrater = true;
    SfxExplosion.crashAir(paramPoint3d, 0);
  }
  public static void AirDrop_Water(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    float f1 = 4.0F; float f2 = 1.0F;
    fontain(paramPoint3d, f1, f2, 1, 0);
    SfxExplosion.crashAir(paramPoint3d, 2);
  }
  public static void AirDrop_Air(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    explodeCollapse(paramPoint3d);
    SfxExplosion.crashAir(paramPoint3d, 1);
  }

  public static void WreckageDrop_Water(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    float f1 = 3.0F; float f2 = 1.0F;
    fontain(paramPoint3d, f1, f2, 1, 1);
    SfxExplosion.crashParts(paramPoint3d, 2);
  }

  public static void SomethingDrop_Water(Point3d paramPoint3d, float paramFloat) {
    if (!Config.isUSE_RENDER()) return;

    o.set(0.0F, 0.0F, 0.0F);
    l.set(paramPoint3d, o);
    new ActorSnapToLand("3do/Effects/Explosion/WaterRing.sim", true, l, paramFloat * 0.2F, paramFloat, 1.0F, 0.0F, 2.5F);

    SfxExplosion.crashParts(paramPoint3d, 2);
  }

  public static void AirFlak(Point3d paramPoint3d, int paramInt)
  {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);
    String str = "effects/Explodes/Air/Zenitka/";

    switch (paramInt) { case 0:
      str = str + "USSR_85mm/"; break;
    case 1:
      str = str + "Germ_88mm/"; break;
    case 2:
      str = str + "USSR_25mm/"; break;
    default:
      str = str + "Germ_20mm/";
    }
    SfxExplosion.zenitka(paramPoint3d, paramInt);

    float f = -1.0F;
    Eff3DActor.New(l, 1.0F, str + "SmokeBoiling.eff", f);
    Eff3DActor.New(l, 1.0F, str + "Burn.eff", f);
    Eff3DActor.New(l, 1.0F, str + "Sparks.eff", f);
    Eff3DActor.New(l, 1.0F, str + "SparksP.eff", f);
  }

  public static void ExplodeFuel(Point3d paramPoint3d) {
    if (!Config.isUSE_RENDER()) return;
    Loc localLoc = new Loc(paramPoint3d);
    Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1.0F);
    Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1.0F);
    Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1.0F);
    Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1.0F);
    World.cur(); if (paramPoint3d.jdField_z_of_type_Double - World.land().HQ(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double) > 3.0D) {
      SfxExplosion.crashAir(paramPoint3d, 1); } else {
      World.cur(); if (World.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double))
        SfxExplosion.crashAir(paramPoint3d, 2);
      else
        SfxExplosion.crashAir(paramPoint3d, 0); 
    }
  }

  private static void LinearExplode(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat1, float paramFloat2, String paramString1, String paramString2) {
    double d = paramPoint3d1.distance(paramPoint3d2);
    int i = (int)(2.0D * d / paramFloat1 * paramFloat2); if (i < 2) i = 2;
    Point3d localPoint3d = new Point3d();

    float f1 = 4.0F; float f2 = 1.0F;
    for (int j = 0; j < i; j++)
    {
      localPoint3d.interpolate(paramPoint3d1, paramPoint3d2, Math.random()); o.set(0.0F, 90.0F, 0.0F); l.set(localPoint3d, o);
      Eff3DActor.New(l, 1.0F, Math.random() < 0.5D ? paramString1 : paramString2, 3.0F);
    }
  }

  public static void ExplodeBridge(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat) {
    if (!Config.isUSE_RENDER()) return;
    LinearExplode(paramPoint3d1, paramPoint3d2, paramFloat, 1.0F, "effects/Explodes/Objects/Bridges/SmokeBoiling.eff", "effects/Explodes/Objects/Bridges/SmokeBoiling2.eff");

    SfxExplosion.bridge(paramPoint3d1, paramPoint3d2, paramFloat);
  }

  public static void ExplodeVagonArmor(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat)
  {
    if (!Config.isUSE_RENDER()) return;
    Point3d localPoint3d = new Point3d();
    for (int i = 0; i < 3; i++) {
      localPoint3d.interpolate(paramPoint3d1, paramPoint3d2, Math.random());
      AirFlak(localPoint3d, 0);
    }

    LinearExplode(paramPoint3d1, paramPoint3d2, paramFloat, 0.5F, "effects/Explodes/Objects/VagonArmor/SmokeBoiling.eff", "effects/Explodes/Objects/VagonArmor/SmokeBoiling2.eff");

    SfxExplosion.wagon(paramPoint3d1, paramPoint3d2, paramFloat, 6);
  }

  public static void ExplodeVagonFuel(Point3d paramPoint3d1, Point3d paramPoint3d2, float paramFloat) {
    if (!Config.isUSE_RENDER()) return;
    LinearExplode(paramPoint3d1, paramPoint3d2, paramFloat, 0.75F, "effects/Explodes/Objects/VagonFuel/SmokeBoilingFire.eff", "effects/Explodes/Objects/VagonFuel/SmokeBoilingFire2.eff");

    SfxExplosion.wagon(paramPoint3d1, paramPoint3d2, paramFloat, 5);
  }

  public static void bomb50_land(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, paramFloat2, "3DO/Effects/Fireworks/Tank_Burn.eff", -1.0F);

    Eff3DActor.New(l, paramFloat2, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1.0F);

    Eff3DActor.New(l, paramFloat2, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1.0F);

    Eff3DActor.New(l, paramFloat2, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1.0F);
  }

  public static void BOMB250_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 0);
  }
  public static void BOMB250_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 1, 0);
  }
  public static void BOMB250_Object(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 0);
  }

  public static void BOMB1000a_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 2);
  }
  public static void BOMB1000a_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 1, 2);
  }
  public static void BOMB1000a_Object(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 2);
  }

  public static void bomb1000_land(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    SurfaceLight(0, 10000.0F, 1.0F);
    SurfaceCrater(0, 112.1F, 600.0F);
    ExplodeSurfaceWave(0, 2000.0F, 4.6F);
    paramPoint3d.jdField_z_of_type_Double += 5.0D;
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(buff).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(circle).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(column).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(flare).eff", 0.1F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(ring).eff", -1.0F);
  }
  public static void bomb1000_water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    SurfaceLight(0, 10000.0F, 1.0F);
    ExplodeSurfaceWave(1, 3000.0F, 6.6F);
    paramPoint3d.jdField_z_of_type_Double += 5.0D;
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(buff).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(circle).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(column).eff", -1.0F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(flare).eff", 0.1F);
    Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(ring).eff", -1.0F);
  }
  public static void bomb1000_object(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return; 
  }

  public static void bomb5000_land(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return; 
  }

  public static void bomb5000_water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return; 
  }

  public static void bomb5000_object(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return; 
  }

  public static void bomb999999_object(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return; 
  }

  public static void RS82_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 1);
  }
  public static void RS82_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 1, 1);
  }
  public static void RS82_Object(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    fontain(paramPoint3d, paramFloat1, paramFloat2, 0, 1);
  }

  public static void Explode10Kg_Object(Point3d paramPoint3d, Vector3f paramVector3f, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    o.setAT0(paramVector3f); o.set(o.azimut(), o.tangage() + 180.0F, 0.0F); l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Object/Sparks.eff", paramFloat1);
  }

  public static void Explode10Kg_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Land/Fontain.eff", paramFloat1);
  }

  public static void Explode10Kg_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Water/Fontain.eff", paramFloat1);
    o.set(0.0F, 0.0F, 0.0F); l.set(paramPoint3d, o);
    ExplodeSurfaceWave(1, 17.5F, 4.0F);
  }

  public static void Bullet_Object(Point3d paramPoint3d, Vector3d paramVector3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    o.setAT0(paramVector3d); o.set(o.azimut(), o.tangage() + 180.0F, 0.0F); l.set(paramPoint3d, o);

    Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Object/Sparks.eff", paramFloat1);
  }
  public static void Bullet_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Bullet/Water/Fontain.eff", paramFloat1);
  }

  public static void Bullet_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Bullet/Land/Fontain.eff", paramFloat1);
  }

  public static void Cannon_Object(Point3d paramPoint3d, Vector3f paramVector3f, float paramFloat1, float paramFloat2)
  {
    if (!Config.isUSE_RENDER()) return;
    o.setAT0(paramVector3f); o.set(o.azimut(), o.tangage() + 180.0F, 0.0F); l.set(paramPoint3d, o);

    Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Object/Sparks.eff", paramFloat1);
  }
  public static void Cannon_Water(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F); l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Water/Fontain.eff", paramFloat1);
    o.set(0.0F, 0.0F, 0.0F); l.set(paramPoint3d, o);
    ExplodeSurfaceWave(1, 17.5F, 4.0F);
  }
  public static void Cannon_Land(Point3d paramPoint3d, float paramFloat1, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;
    o.set(0.0F, 90.0F, 0.0F);
    l.set(paramPoint3d, o);
    Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Land/Fontain.eff", paramFloat1);
  }

  public static void generateSound(Actor paramActor, Point3d paramPoint3d, float paramFloat1, int paramInt, float paramFloat2)
  {
    if (Config.isUSE_RENDER())
      if (paramActor == null) {
        SfxExplosion.shell(paramPoint3d, 1, paramFloat1, paramInt, paramFloat2);
      }
      else if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double))
        SfxExplosion.shell(paramPoint3d, 2, paramFloat1, paramInt, paramFloat2);
      else
        SfxExplosion.shell(paramPoint3d, 0, paramFloat1, paramInt, paramFloat2);
  }

  public static void generateRocket(Actor paramActor, Point3d paramPoint3d, float paramFloat1, int paramInt, float paramFloat2)
  {
    generate(paramActor, paramPoint3d, paramFloat1 > 15.0F ? paramFloat1 : 15.0F, paramInt, paramFloat2);
  }
  public static void generate(Actor paramActor, Point3d paramPoint3d, float paramFloat1, int paramInt, float paramFloat2) {
    if (!Config.isUSE_RENDER()) return;

    if (paramActor != null)
    {
      generateSound(paramActor, paramPoint3d, paramFloat1, paramInt, paramFloat2);

      rel.set(paramPoint3d);
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpLoc); paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(l); l.interpolate(tmpLoc, 0.5D);
      rel.sub(l);

      if (paramInt == 2) {
        if (paramFloat2 < 3.0F) {
          switch (rnd.nextInt(1, 2)) {
          case 1:
            Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Termit1W.eff", 10.0F);
            break;
          case 2:
            Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Termit1SM.eff", -1.0F);
          }
        }
        else
        {
          Vector3d localVector3d = new Vector3d();
          for (int i = 0; i < 36; i++) {
            localVector3d.set(World.Rnd().nextDouble(-20.0D, 20.0D), World.Rnd().nextDouble(-20.0D, 20.0D), World.Rnd().nextDouble(3.0D, 20.0D));
            float f = World.Rnd().nextFloat(3.0F, 15.0F);
            BallisticProjectile localBallisticProjectile = new BallisticProjectile(paramPoint3d, localVector3d, f);
            Eff3DActor.New(localBallisticProjectile, null, null, 1.0F, "3DO/Effects/Fireworks/PhosfourousFire.eff", f);
          }

        }

        return;
      }

      if ((paramActor instanceof ActorLand))
      {
        if (paramFloat1 < 15.0F) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) Explode10Kg_Water(paramPoint3d, 4.0F, 1.0F); else
            Explode10Kg_Land(paramPoint3d, 4.0F, 1.0F);
        } else if (paramFloat1 < 50.0F) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) RS82_Water(paramPoint3d, 4.0F, 1.0F); else
            RS82_Land(paramPoint3d, 4.0F, 1.0F);
        } else if (paramFloat1 < 450.0F) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) BOMB250_Water(paramPoint3d, 4.0F, 1.0F); else
            BOMB250_Land(paramPoint3d, 4.0F, 1.0F);
        } else if (paramFloat1 < 3000.0F) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) BOMB1000a_Water(paramPoint3d, 4.0F, 1.0F); else
            BOMB1000a_Land(paramPoint3d, 4.0F, 1.0F);
        }
        else if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) bomb1000_water(paramPoint3d, -1.0F, 1.0F); else {
          bomb1000_land(paramPoint3d, -1.0F, 1.0F);
        }

      }
      else if (paramFloat1 < 50.0F) {
        if (paramPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double) < 5.0D) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) RS82_Water(paramPoint3d, 4.0F, 1.0F); else
            RS82_Land(paramPoint3d, 4.0F, 1.0F);
        }
        bomb50_land(paramPoint3d, -1.0F, 1.0F);
      } else if (paramFloat1 < 450.0F) {
        if (paramPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double) < 10.0D) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) BOMB250_Water(paramPoint3d, 4.0F, 1.0F); else
            BOMB250_Land(paramPoint3d, 4.0F, 1.0F);
        }
        bomb50_land(paramPoint3d, -1.0F, 2.0F);
      } else if (paramFloat1 < 3000.0F) {
        if (paramPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double) < 20.0D) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) BOMB1000a_Water(paramPoint3d, 4.0F, 1.0F); else
            BOMB1000a_Land(paramPoint3d, 4.0F, 1.0F);
        }
        bomb50_land(paramPoint3d, -1.0F, 2.0F);
      } else {
        if (paramPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double) < 50.0D) {
          if (Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double)) bomb1000_water(paramPoint3d, -1.0F, 1.0F); else
            bomb1000_land(paramPoint3d, -1.0F, 1.0F);
        }
        bomb50_land(paramPoint3d, -1.0F, 10.0F);
      }
    }
  }

  private static void playShotSound(Shot paramShot)
  {
    double d = paramShot.p.distanceSquared(Engine.soundListener().absPos);
  }

  public static void generateShot(Actor paramActor, Shot paramShot)
  {
    if (!Config.isUSE_RENDER()) return;
    float f = paramShot.mass;
    playShotSound(paramShot);

    rel.set(paramShot.p);
    paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpLoc); paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(l); l.interpolate(tmpLoc, paramShot.tickOffset);
    rel.sub(l);

    if ((World.cur().isArcade()) && (!(paramActor instanceof Aircraft))) {
      Eff3DActor.New(paramActor, null, rel, 0.75F, "3DO/Effects/Fireworks/Sprite.eff", 30.0F);
    }
    if (!(paramActor instanceof ActorLand)) {
      switch (rnd.nextInt(1, 4)) {
      case 1:
        Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Debris1A.eff", -1.0F);
        break;
      case 2:
        Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Debris1B.eff", -1.0F);
        break;
      case 3:
        Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Debris1C.eff", -1.0F);
        break;
      case 4:
        Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/Debris1D.eff", -1.0F);
      }
    }

    if ((paramActor instanceof Aircraft)) return;

    switch (paramShot.bodyMaterial)
    {
    case 0:
      if (f < 1.0F)
        Cannon_Land(paramShot.p, 4.0F, 1.0F);
      else if (f < 5.0F)
        Explode10Kg_Land(paramShot.p, 4.0F, 1.0F);
      else if (f < 50.0F)
        RS82_Land(paramShot.p, 4.0F, 1.0F);
      else {
        BOMB250_Land(paramShot.p, 4.0F, 1.0F);
      }
      break;
    case 1:
      if (f < 0.023F)
        Bullet_Water(paramShot.p, 0.5F, 1.0F);
      else if (f < 0.701F)
        Cannon_Water(paramShot.p, 4.0F, 1.0F);
      else if (f < 8.55F)
        Explode10Kg_Water(paramShot.p, 4.0F, 1.0F);
      else if (f < 24.200001F)
        RS82_Water(paramShot.p, 4.0F, 1.0F);
      else {
        BOMB250_Water(paramShot.p, 4.0F, 1.0F);
      }
      break;
    case 2:
      Bullet_Object(paramShot.p, paramShot.v, 0.5F, 1.0F);
      break;
    case 3:
      break;
    default:
      Bullet_Object(paramShot.p, paramShot.v, 1.0F, 1.0F);
    }
  }

  public static void generateExplosion(Actor paramActor, Point3d paramPoint3d, float paramFloat1, int paramInt, float paramFloat2, double paramDouble)
  {
    if (!Config.isUSE_RENDER()) return;
    generateSound(paramActor, paramPoint3d, paramFloat1, paramInt, paramFloat2);

    rel.set(paramPoint3d);
    if (paramActor != null) {
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpLoc); paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(l); l.interpolate(tmpLoc, paramDouble);
      rel.sub(l);
    }

    if (paramActor == null)
    {
      return;
    }

    if ((paramActor instanceof ActorLand))
    {
      boolean bool = Engine.land().isWater(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double);

      if (paramFloat1 < 0.001F) {
        if (!bool) Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/12_Burn.eff", -1.0F); 
      }
      else if (paramFloat1 < 0.005F) {
        if (!bool) Eff3DActor.New(rel, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1.0F); 
      }
      else if (paramFloat1 < 0.05F) {
        if (bool) Explode10Kg_Water(paramPoint3d, 4.0F, 1.0F); else
          Explode10Kg_Land(paramPoint3d, 4.0F, 1.0F);
      }
      else if (paramFloat1 < 1.0F) {
        if (bool) RS82_Water(paramPoint3d, 4.0F, 1.0F); else
          RS82_Land(paramPoint3d, 4.0F, 1.0F);
      } else if (paramFloat1 < 15.0F) {
        if (bool) Explode10Kg_Water(paramPoint3d, 4.0F, 1.0F); else
          Explode10Kg_Land(paramPoint3d, 4.0F, 1.0F);
      } else if (paramFloat1 < 50.0F) {
        if (bool) RS82_Water(paramPoint3d, 4.0F, 1.0F); else
          RS82_Land(paramPoint3d, 4.0F, 1.0F);
      }
      else if (bool) BOMB250_Water(paramPoint3d, 4.0F, 1.0F); else {
        BOMB250_Land(paramPoint3d, 4.0F, 1.0F);
      }

    }
    else if (paramFloat1 < 0.001F) {
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/12_Burn.eff", -1.0F);
    } else if (paramFloat1 < 0.003F) {
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/12mmPluff.eff", 0.15F);
    }
    else if (paramFloat1 < 0.005F) {
      Eff3DActor.New(paramActor, null, rel, 0.5F, "3DO/Effects/Fireworks/20_Burn.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.5F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.5F, "3DO/Effects/Fireworks/20_Sparks.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.5F, "3DO/Effects/Fireworks/20_SparksP.eff", -1.0F);
    } else if (paramFloat1 < 0.01F) {
      Eff3DActor.New(paramActor, null, rel, 0.75F, "3DO/Effects/Fireworks/20_Burn.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.75F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.75F, "3DO/Effects/Fireworks/20_Sparks.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 0.75F, "3DO/Effects/Fireworks/20_SparksP.eff", -1.0F);
    } else if (paramFloat1 < 0.02F) {
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/20_Burn.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/20_Sparks.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/20_SparksP.eff", -1.0F);
    } else if (paramFloat1 < 1.0F) {
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/37_Burn.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/37_SmokeBoiling.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/37_Sparks.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 1.0F, "3DO/Effects/Fireworks/37_SparksP.eff", -1.0F);
    }
    else if (paramFloat1 < 9999.0F) {
      Eff3DActor.New(paramActor, null, rel, 3.0F, "3DO/Effects/Fireworks/37_Burn.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 3.0F, "3DO/Effects/Fireworks/37_SmokeBoiling.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 3.0F, "3DO/Effects/Fireworks/37_Sparks.eff", -1.0F);
      Eff3DActor.New(paramActor, null, rel, 3.0F, "3DO/Effects/Fireworks/37_SparksP.eff", -1.0F);
    }
  }

  public static void generateComicBulb(Actor paramActor, String paramString, float paramFloat)
  {
    if (!Config.isUSE_RENDER()) return;
    if (!World.cur().isArcade()) return;
    Eff3DActor.New(paramActor, null, null, 1.0F, "3DO/Effects/Debug/msg" + paramString + ".eff", paramFloat);
  }

  static class MydataForSmoke
  {
    ActorHMesh a;
    float tim;

    MydataForSmoke(ActorHMesh paramActorHMesh, float paramFloat)
    {
      this.a = paramActorHMesh;
      this.tim = paramFloat;
    }
  }
}
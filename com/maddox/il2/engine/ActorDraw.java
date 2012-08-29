package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.ObjState;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundList;
import com.maddox.util.HashMapExt;
import java.util.Map.Entry;

public abstract class ActorDraw
  implements Destroy
{
  public static final int INVISIBLE = 0;
  public static final int SOLID = 1;
  public static final int TRANSPARENT = 2;
  public static final int SHADOW = 4;
  public float uniformMaxDist = 0.0F;

  private static Point3d absPos = new Point3d();
  private static Loc absLocate = new Loc();
  private static Loc relLocate = new Loc();

  protected HashMapExt lightMap = null;

  private static Vector3d vspeed = new Vector3d();
  private static Vector3d ahead = new Vector3d();
  private static Vector3d up = new Vector3d();

  protected SoundList sounds = null;

  public int preRender(Actor paramActor)
  {
    return 0;
  }
  public void render(Actor paramActor) {
  }
  public void renderShadowProjective(Actor paramActor) {
  }
  public void renderShadowVolume(Actor paramActor) {
  }
  public void renderShadowVolumeHQ(Actor paramActor) {
  }

  public void lightUpdate(Loc paramLoc, boolean paramBoolean) {
    if (this.lightMap != null) {
      Map.Entry localEntry = this.lightMap.nextEntry(null);
      LightPointActor localLightPointActor;
      Object localObject;
      if (paramBoolean)
      {
        while (localEntry != null) {
          localLightPointActor = (LightPointActor)localEntry.getValue();
          if ((localLightPointActor.light instanceof LightPointWorld)) {
            localObject = (LightPointWorld)localLightPointActor.light;
            if ((((LightPointWorld)localObject).I > 0.0F) && (((LightPointWorld)localObject).R > 0.0F)) {
              computeAbsPos(paramLoc, localLightPointActor.relPos);
              ((LightPointWorld)localObject).setPos(absPos);
            }
          }
          localEntry = this.lightMap.nextEntry(localEntry);
        }
      }
      else
        do {
          localLightPointActor = (LightPointActor)localEntry.getValue();
          if (((localLightPointActor.light instanceof LightPoint)) && (!(localLightPointActor.light instanceof LightPointWorld))) {
            localObject = localLightPointActor.light;
            if ((((LightPoint)localObject).I > 0.0F) && (((LightPoint)localObject).R > 0.0F)) {
              computeAbsPos(paramLoc, localLightPointActor.relPos);
              ((LightPoint)localObject).setPos(absPos);
              ((LightPoint)localObject).addToRender();
            }
          }
          localEntry = this.lightMap.nextEntry(localEntry);
        }
        while (localEntry != null);
    }
  }

  private void computeAbsPos(Loc paramLoc, Point3d paramPoint3d)
  {
    relLocate.set(paramPoint3d);
    absLocate.add(relLocate, paramLoc);
    absLocate.get(absPos);
  }

  public HashMapExt lightMap()
  {
    if (this.lightMap == null)
      this.lightMap = new HashMapExt();
    return this.lightMap;
  }

  public void soundUpdate(Actor paramActor, Loc paramLoc)
  {
    if ((this.sounds != null) && (Config.cur.isSoundUse())) {
      Object localObject = this.sounds.get();
      while (localObject != null) {
        int i = ((SoundFX)localObject).getCaps();
        if (i == -1) {
          SoundFX localSoundFX = ((SoundFX)localObject).next();
          ((SoundFX)localObject).destroy();
          localObject = localSoundFX;
        } else {
          if ((i & 0x1) != 0) {
            if ((paramActor == null) || ((i & 0x2) == 0)) {
              if ((i & 0x4) != 0) {
                if ((i & 0x8) != 0) computeAheadUp(paramLoc.getOrient()); else
                  computeAhead(paramLoc.getOrient());
                ((SoundFX)localObject).setPosition(paramLoc.getPoint());
                ((SoundFX)localObject).setOrientation((float)ahead.jdField_x_of_type_Double, (float)ahead.jdField_y_of_type_Double, (float)ahead.jdField_z_of_type_Double);
                if ((i & 0x8) != 0)
                  ((SoundFX)localObject).setTop((float)up.jdField_x_of_type_Double, (float)up.jdField_y_of_type_Double, (float)up.jdField_z_of_type_Double);
              }
              else {
                ((SoundFX)localObject).setPosition(paramLoc.getPoint());
              }
            } else {
              paramActor.getSpeed(vspeed);
              if ((i & 0x4) != 0) {
                if ((i & 0x8) != 0) computeAheadUp(paramLoc.getOrient()); else
                  computeAhead(paramLoc.getOrient());
                ((SoundFX)localObject).setPosition(paramLoc.getPoint());
                ((SoundFX)localObject).setVelocity((float)vspeed.jdField_x_of_type_Double, (float)vspeed.jdField_y_of_type_Double, (float)vspeed.jdField_z_of_type_Double);
                ((SoundFX)localObject).setOrientation((float)ahead.jdField_x_of_type_Double, (float)ahead.jdField_y_of_type_Double, (float)ahead.jdField_z_of_type_Double);
                if ((i & 0x8) != 0)
                  ((SoundFX)localObject).setTop((float)up.jdField_x_of_type_Double, (float)up.jdField_y_of_type_Double, (float)up.jdField_z_of_type_Double);
              }
              else {
                ((SoundFX)localObject).setPosition(paramLoc.getPoint());
                ((SoundFX)localObject).setVelocity((float)vspeed.jdField_x_of_type_Double, (float)vspeed.jdField_y_of_type_Double, (float)vspeed.jdField_z_of_type_Double);
              }
            }
          }
          localObject = ((SoundFX)localObject).next();
        }
      }
    }
  }

  private void computeAhead(Orient paramOrient)
  {
    ahead.set(1.0D, 0.0D, 0.0D);
    paramOrient.transform(ahead);
  }

  private void computeAheadUp(Orient paramOrient)
  {
    ahead.set(1.0D, 0.0D, 0.0D);
    up.set(0.0D, 0.0D, 1.0D);
    paramOrient.transform(ahead);
    paramOrient.transform(up);
  }

  public SoundList sounds()
  {
    if (this.sounds == null) this.sounds = new SoundList();
    return this.sounds;
  }

  public boolean isDestroyed() {
    return (this.lightMap == null) && (this.sounds == null);
  }

  public void destroy() {
    if (this.lightMap != null) {
      Map.Entry localEntry = this.lightMap.nextEntry(null);
      while (localEntry != null) {
        LightPointActor localLightPointActor = (LightPointActor)localEntry.getValue();
        ObjState.destroy(localLightPointActor);
        localEntry = this.lightMap.nextEntry(localEntry);
      }
      this.lightMap.clear();
      this.lightMap = null;
    }
    if (this.sounds != null) {
      this.sounds.destroy();
      this.sounds = null;
    }
  }

  public ActorDraw(ActorDraw paramActorDraw) {
    if (paramActorDraw != null) {
      this.lightMap = paramActorDraw.lightMap;
      this.sounds = paramActorDraw.sounds;
      this.uniformMaxDist = paramActorDraw.uniformMaxDist;
    }
  }

  public ActorDraw()
  {
  }
}
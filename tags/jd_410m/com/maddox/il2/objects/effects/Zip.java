package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.sounds.SfxZip;
import com.maddox.rts.Destroy;
import com.maddox.rts.MsgAction;

public class Zip
  implements Destroy
{
  private float height = 1000.0F;
  Point3d temp = new Point3d();
  LightPointWorld light1 = null;
  LightPointWorld light2 = null;
  private RangeRandom rnd = new RangeRandom();
  private ActorSimpleMesh zipMesh;
  private static final float dT = 4.0F;

  protected void zipPsss()
  {
    if (isDestroyed()) return;
    this.light1.setEmit(0.0F, 0.0F);
    this.light2.setEmit(0.0F, 0.0F);
    this.zipMesh.drawing(false);
    new MsgAction(this.rnd.nextFloat(4.0F, 8.0F)) {
      public void doAction() {
        Zip.this.zipBuhhh();
      } } ;
  }

  protected void zipBuhhh() {
    if (isDestroyed()) return;
    if (!Mission.isPlaying()) return;
    if (!Main3D.cur3D().clouds.getRandomCloudPos(this.temp)) {
      new MsgAction(this.rnd.nextFloat(4.0F, 8.0F)) {
        public void doAction() {
          Zip.this.zipBuhhh();
        }
      };
      return;
    }
    this.temp.z -= 100.0D;
    this.light2.setPos(this.temp);
    this.light2.setEmit(5.0F, 100.0F);
    this.light2.setColor(1.0F, 1.0F, 1.0F);
    this.temp.z = (this.height * 0.3F);
    new SfxZip(this.temp);
    this.light1.setPos(this.temp);
    this.light1.setEmit(50.0F, 2000.0F);
    this.light1.setColor(1.0F, 1.0F, 1.0F);
    this.temp.z = 0.0D;
    this.zipMesh.pos.setAbs(this.temp);
    this.zipMesh.pos.reset();
    this.zipMesh.mesh().setScale(this.height * 0.001F);
    this.zipMesh.mesh().setFrame(this.rnd.nextInt(0, 4));
    this.zipMesh.drawing(true);
    Cockpit.lightningStrike(this.temp);
    new MsgAction(0.5D) {
      public void doAction() {
        Zip.this.zipPsss();
      } } ;
  }

  public Zip(float paramFloat) {
    this.height = paramFloat;
    this.light1 = new LightPointWorld();
    this.light2 = new LightPointWorld();
    this.zipMesh = new ActorSimpleMesh("3do/effects/fireworks/hammerofthor/mono.sim");
    this.zipMesh.draw = new ActorMeshDraw() {
      public void render(Actor paramActor) {
        if (Main3D.cur3D().bEnableFog) Render.enableFog(false);
        super.render(paramActor);
        if (Main3D.cur3D().bEnableFog) Render.enableFog(true);
      }
    };
    this.zipMesh.drawing(false);
    new MsgAction(15.0D) {
      public void doAction() {
        Zip.this.zipBuhhh();
      } } ;
  }

  public boolean isDestroyed() {
    return this.light1 == null;
  }

  public void destroy() {
    if (isDestroyed()) return;
    this.light1.destroy();
    this.light1 = null;
    this.light2.destroy();
    this.light2 = null;
    if (Actor.isValid(this.zipMesh)) {
      this.zipMesh.destroy();
      this.zipMesh = null;
    }
  }
}
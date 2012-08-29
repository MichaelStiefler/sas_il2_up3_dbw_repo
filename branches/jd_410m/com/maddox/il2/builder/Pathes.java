package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.util.HashMapExt;

public class Pathes extends Actor
{
  public PPoint currentPPoint;
  private HashMapExt pointsMap = new HashMapExt();
  private Point2d p2d = new Point2d();

  private float[] lineNXYZ = new float[6];
  private int lineNCounter;
  private StringBuffer strBuf = new StringBuffer();

  private Object[] pathes = new Object[1];
  private Object[] points = new Object[1];

  public void renderMap2D(boolean paramBoolean, int paramInt)
  {
    Render.drawBeginLines(-1);
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    IconDraw.setScrSize(paramInt, paramInt);
    double d1 = localCameraOrtho2D.left - paramInt / 2;
    double d2 = localCameraOrtho2D.bottom - paramInt / 2;
    double d3 = localCameraOrtho2D.right + paramInt / 2;
    double d4 = localCameraOrtho2D.top + paramInt / 2;
    int i = 0;
    int j = 0;
    int k = 1;

    this.pathes = getOwnerAttached(this.pathes);
    Object localObject1;
    int i3;
    for (int n = 0; n < this.pathes.length; n++) {
      localObject1 = (Path)this.pathes[n];
      this.pathes[n] = null;
      if (localObject1 == null) break;
      ((Path)localObject1).renderPoints = 0;
      if (!((Path)localObject1).isDrawing())
        continue;
      this.points = ((Path)localObject1).getOwnerAttached(this.points);
      Object localObject2 = null;
      int i2 = 0;
      if (this.lineNXYZ.length / 3 <= this.points.length)
        this.lineNXYZ = new float[(this.points.length + 1) * 3];
      this.lineNCounter = 0;
      int m;
      for (i3 = 0; i3 < this.points.length; i3++) {
        PPoint localPPoint = (PPoint)this.points[i3];
        this.points[i3] = null;
        if (localPPoint == null) break;
        Plugin.builder.project2d(localPPoint.pos.getCurrentPoint(), this.p2d);
        localPPoint.screenX = this.p2d.x;
        localPPoint.screenY = this.p2d.y;
        if (localPPoint.screenX < d1) {
          if (localPPoint.screenY < d2) localPPoint.screenQuad = 6;
          else if (localPPoint.screenY > d4) localPPoint.screenQuad = 0; else
            localPPoint.screenQuad = 7;
        } else if (localPPoint.screenX > d3) {
          if (localPPoint.screenY < d2) localPPoint.screenQuad = 4;
          else if (localPPoint.screenY > d4) localPPoint.screenQuad = 2; else
            localPPoint.screenQuad = 3;
        }
        else if (localPPoint.screenY < d2) localPPoint.screenQuad = 5;
        else if (localPPoint.screenY > d4) localPPoint.screenQuad = 1; else
          localPPoint.screenQuad = 8;
        int i6;
        if ((localPPoint instanceof PNodes)) {
          PNodes localPNodes = (PNodes)localPPoint;
          if (localPNodes.posXYZ != null) {
            i6 = (this.currentPPoint != null) && (this.currentPPoint.getOwner() == localObject1) ? 1 : 0;
            if (this.lineNXYZ.length / 3 <= localPNodes.posXYZ.length / 4)
              this.lineNXYZ = new float[(localPNodes.posXYZ.length / 4 + 1) * 3];
            this.lineNCounter = 0;
            for (int i7 = 0; i7 < localPNodes.posXYZ.length / 4; i7++) {
              Plugin.builder.project2d(localPNodes.posXYZ[(i7 * 4 + 0)], localPNodes.posXYZ[(i7 * 4 + 1)], localPNodes.posXYZ[(i7 * 4 + 2)], this.p2d);

              this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)this.p2d.x;
              this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)this.p2d.y;
              this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
              this.lineNCounter += 1;
            }
            if (i6 != 0) { j = Builder.colorSelected(); k = 3; } else {
              j = Army.color(((Path)localObject1).getArmy()); k = 1;
            }Render.drawLines(this.lineNXYZ, this.lineNCounter, k, j, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
          }

        }
        else if (localObject2 != null) {
          int i5 = (localPPoint.screenQuad == 8) || (localObject2.screenQuad == 8) ? 1 : 0;
          while ((i5 == 0) && 
            (localPPoint.screenQuad != localObject2.screenQuad) && 
            (localPPoint.screenQuad != (localObject2.screenQuad + 1 & 0x7)) && 
            (localObject2.screenQuad != (localPPoint.screenQuad + 1 & 0x7)) && (
            ((localPPoint.screenQuad & 0x1) != 0) || ((localObject2.screenQuad & 0x1) != 0) || (
            (localPPoint.screenQuad != (localObject2.screenQuad + 2 & 0x7)) && 
            (localObject2.screenQuad != (localPPoint.screenQuad + 2 & 0x7)))))
          {
            i5 = 1;
          }
          if (i5 != 0) {
            if (i2 == 0) {
              i2 = 1;
              i6 = (this.currentPPoint != null) && (this.currentPPoint.getOwner() == localObject1) ? 1 : 0;
              if (i6 != 0) { j = Builder.colorSelected(); m = 3; } else {
                j = Army.color(((Path)localObject1).getArmy()); m = 1;
              }this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)localObject2.screenX;
              this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)localObject2.screenY;
              this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
              this.lineNCounter += 1;
            }
            this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)localPPoint.screenX;
            this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)localPPoint.screenY;
            this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
            this.lineNCounter += 1;
          } else if (i2 != 0) {
            i2 = 0;
            Render.drawLines(this.lineNXYZ, this.lineNCounter, m, j, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
          }

        }

        if (localPPoint.screenQuad == 8) { localObject1.renderPoints += 1; i++; }
        localObject2 = localPPoint;
      }
      if (i2 != 0) {
        i2 = 0;
        Render.drawLines(this.lineNXYZ, this.lineNCounter, m, j, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
      }

    }

    Render.drawEnd();
    int i1;
    Object localObject3;
    if (i != 0)
    {
      this.pathes = getOwnerAttached(this.pathes);
      for (n = 0; n < this.pathes.length; n++) {
        localObject1 = (Path)this.pathes[n];
        this.pathes[n] = null;
        if (localObject1 == null) break;
        if (((Path)localObject1).renderPoints != 0) {
          this.points = ((Path)localObject1).getOwnerAttached(this.points);
          for (i1 = 0; i1 < this.points.length; i1++) {
            localObject3 = (PPoint)this.points[i1];
            this.points[i1] = null;
            if (localObject3 == null) break;
            if (((PPoint)localObject3).screenQuad == 8) {
              if ((this.currentPPoint == localObject3) && (this.currentPPoint.getOwner() == ((PPoint)localObject3).getOwner()))
                j = Builder.colorSelected();
              else if (Plugin.builder.isMiltiSelected((Actor)localObject3)) j = -16776961; else
                j = Army.color(((Path)localObject1).getArmy());
              IconDraw.setColor(j);

              IconDraw.render((Actor)localObject3, ((PPoint)localObject3).screenX, ((PPoint)localObject3).screenY);
              this.strBuf.delete(0, this.strBuf.length());
              this.strBuf.append(i1);
              if ((Plugin.builder.conf.bShowTime) && (!(localObject3 instanceof PAirdrome))) {
                this.strBuf.append('(');
                i3 = (int)Math.round(((PPoint)localObject3).time / 60.0D) + (int)(World.getTimeofDay() * 60.0F);
                this.strBuf.append(i3 / 60 % 24); this.strBuf.append(':');
                int i4 = i3 % 60;
                if (i4 < 10) this.strBuf.append('0');
                this.strBuf.append(i4);
                this.strBuf.append(')');
              }
              Plugin.builder.smallFont.output(j, (int)((PPoint)localObject3).screenX + IconDraw.scrSizeX() / 2 + 2, (int)((PPoint)localObject3).screenY - IconDraw.scrSizeY() / 2 - 2, 0.0F, this.strBuf.toString());

              if ((!Plugin.builder.conf.bShowName) || 
                (!((Path)localObject1).isNamed())) continue;
              if ((localObject1 instanceof PathAir)) {
                Plugin.builder.smallFont.output(j, (int)((PPoint)localObject3).screenX + IconDraw.scrSizeX() / 2 + 2, (int)((PPoint)localObject3).screenY + Plugin.builder.smallFont.height() - Plugin.builder.smallFont.descender() - IconDraw.scrSizeY() / 2 - 2, 0.0F, ((PathAir)localObject1).typedName);
              }
              else if ((localObject1 instanceof PathChief)) {
                Plugin.builder.smallFont.output(j, (int)((PPoint)localObject3).screenX + IconDraw.scrSizeX() / 2 + 2, (int)((PPoint)localObject3).screenY + Plugin.builder.smallFont.height() - Plugin.builder.smallFont.descender() - IconDraw.scrSizeY() / 2 - 2, 0.0F, Property.stringValue(localObject1, "i18nName", ""));
              }
              else
              {
                Plugin.builder.smallFont.output(j, (int)((PPoint)localObject3).screenX + IconDraw.scrSizeX() / 2 + 2, (int)((PPoint)localObject3).screenY + Plugin.builder.smallFont.height() - Plugin.builder.smallFont.descender() - IconDraw.scrSizeY() / 2 - 2, 0.0F, ((Path)localObject1).name());
              }

            }

          }

        }

      }

    }

    this.pathes = getOwnerAttached(this.pathes);
    for (n = 0; n < this.pathes.length; n++) {
      localObject1 = (Path)this.pathes[n];
      this.pathes[n] = null;
      if (localObject1 == null) break;
      if ((localObject1 instanceof PathAir)) {
        this.points = ((Path)localObject1).getOwnerAttached(this.points);
        for (i1 = 0; i1 < this.points.length; i1++) {
          localObject3 = (PAir)this.points[i1];
          this.points[i1] = null;
          if (localObject3 == null) break;
          if (Actor.isValid(((PAir)localObject3).getTarget())) {
            Plugin.builder.project2d(((PAir)localObject3).getTarget().pos.getCurrentPoint(), this.p2d);
            i3 = Plugin.builder.conf.iconSize;
            Render.drawTile((float)(this.p2d.x - i3 / 2), (float)(this.p2d.y - i3 / 2), i3, i3, 0.0F, Plugin.targetIcon, -16711936, 0.0F, 0.0F, 1.0F, 1.0F);
          }
        }
      }
    }
    if ((i != 0) && (Actor.isValid(Path.player)) && (Path.player.renderPoints != 0)) {
      this.points = Path.player.getOwnerAttached(this.points);
      for (n = 0; n < this.points.length; n++) {
        localObject1 = (PPoint)this.points[n];
        if (localObject1 == null) break;
        if (((PPoint)localObject1).screenQuad == 8) {
          Render.drawTile((float)((PPoint)localObject1).screenX, (float)((PPoint)localObject1).screenY, paramInt, paramInt, 0.0F, Plugin.playerIcon, Army.color(Path.player.getArmy()), 0.0F, 1.0F, 1.0F, -1.0F);

          break;
        }
      }
      for (n = 0; n < this.points.length; n++)
        this.points[n] = null;
    }
  }

  public void renderMap2DTargetLines()
  {
    Render.drawBeginLines(-1);
    this.pathes = getOwnerAttached(this.pathes);
    for (int i = 0; i < this.pathes.length; i++) {
      Path localPath = (Path)this.pathes[i];
      this.pathes[i] = null;
      if (localPath == null) break;
      if ((localPath instanceof PathAir)) {
        this.points = localPath.getOwnerAttached(this.points);
        for (int j = 0; j < this.points.length; j++) {
          PAir localPAir = (PAir)this.points[j];
          this.points[j] = null;
          if (localPAir == null) break;
          if (Actor.isValid(localPAir.getTarget())) {
            Plugin.builder.project2d(localPAir.pos.getCurrentPoint(), this.p2d);
            this.lineNXYZ[0] = (float)this.p2d.x;
            this.lineNXYZ[1] = (float)this.p2d.y;
            this.lineNXYZ[2] = 0.0F;
            Plugin.builder.project2d(localPAir.getTarget().pos.getCurrentPoint(), this.p2d);
            this.lineNXYZ[3] = (float)this.p2d.x;
            this.lineNXYZ[4] = (float)this.p2d.y;
            this.lineNXYZ[5] = 0.0F;
            Render.drawLines(this.lineNXYZ, 2, 3.0F, -16711936, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
          }
        }
      }
    }

    Render.drawEnd();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Pathes() {
    this.flags |= 8192;
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  public void clear() {
    this.pathes = getOwnerAttached(this.pathes);
    for (int i = 0; i < this.pathes.length; i++) {
      Actor localActor = (Actor)this.pathes[i];
      if (localActor == null) break;
      localActor.destroy();
      this.pathes[i] = null;
    }
    this.currentPPoint = null;
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    this.pathes = getOwnerAttached(this.pathes);
    for (int i = 0; i < this.pathes.length; i++) {
      Actor localActor = (Actor)this.pathes[i];
      if (localActor == null) break;
      localActor.destroy();
      this.pathes[i] = null;
    }
    this.currentPPoint = null;
    super.destroy();
  }
}
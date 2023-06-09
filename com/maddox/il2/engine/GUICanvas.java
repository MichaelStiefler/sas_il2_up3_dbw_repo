package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.rts.StringClipboard;

public class GUICanvas extends GCanvas
{
  public Render render;
  public GUITexture clear_set_z;
  public boolean bClearZ = true;

  private static Loc _meshLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);

  private static int[] _viewPort = new int[4];

  public boolean preRender(GTexture paramGTexture, float paramFloat1, float paramFloat2)
  {
    GUITexture localGUITexture = (GUITexture)paramGTexture;
    if (localGUITexture.mat == null) return false;
    if ((paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F)) return false;
    if (this.alpha == 0) return false;
    localGUITexture.mat.preRender();
    return true;
  }

  public boolean draw(GTexture paramGTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (paramGTexture == null) return false;
    GUITexture localGUITexture = (GUITexture)paramGTexture;
    if (localGUITexture.mat == null) return false;
    if ((paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F)) return false;
    if ((paramFloat5 == 0.0F) || (paramFloat6 == 0.0F)) return false;
    if (this.alpha == 0) return false;
    float f1 = paramFloat5 / paramFloat1;
    float f2 = paramFloat6 / paramFloat2;
    float f3 = this.cur.x;
    float f4 = this.cur.y;
    float f5 = f3 - this.clip.x;
    float f6;
    if (f5 < 0.0F) {
      paramFloat1 += f5; if (paramFloat1 <= 0.0F) return false;
      f3 = this.clip.x;
      f6 = f5 * f1;
      paramFloat5 += f6;
      paramFloat3 -= f6;
      f5 = 0.0F;
    }
    f5 = paramFloat1 + f5 - this.clip.dx;
    if (f5 > 0.0F) {
      paramFloat1 -= f5; if (paramFloat1 <= 0.0F) return false;
      paramFloat5 -= f5 * f1;
    }

    f5 = f4 - this.clip.y;
    if (f5 < 0.0F) {
      paramFloat2 += f5; if (paramFloat2 <= 0.0F) return false;
      f4 = this.clip.y;
      f6 = f5 * f2;
      paramFloat6 += f6;
      paramFloat4 -= f6;
      f5 = 0.0F;
    }
    f5 = paramFloat2 + f5 - this.clip.dy;
    if (f5 > 0.0F) {
      paramFloat2 -= f5; if (paramFloat2 <= 0.0F) return false;
      f6 = f5 * f2;
      paramFloat6 -= f6;
    }

    f4 = this.size.dy - f4;

    f3 = Math.round(f3);
    f4 = Math.round(f4);
    paramFloat1 = Math.round(paramFloat1);
    paramFloat2 = Math.round(paramFloat2);

    Render.DrawTile(f3, f4, paramFloat1, -paramFloat2, 0.0F, localGUITexture.mat.cppObject(), this.color.color | (this.alpha & 0xFF) << 24, paramFloat3 * localGUITexture.scalex, paramFloat4 * localGUITexture.scaley, paramFloat5 * localGUITexture.scalex, paramFloat6 * localGUITexture.scaley);

    return true;
  }

  public boolean preRender(GMesh paramGMesh, float paramFloat1, float paramFloat2)
  {
    GUIMesh localGUIMesh = (GUIMesh)paramGMesh;
    if (localGUIMesh.mesh == null) return false;
    if ((paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F)) return false;
    if (this.alpha == 0) return false;
    setMeshPos(localGUIMesh, this.cur.x, this.cur.y, paramFloat1, paramFloat2);
    localGUIMesh.mesh.preRender();
    return true;
  }

  private void setMeshPos(GUIMesh paramGUIMesh, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f1 = paramFloat3 / paramGUIMesh.size.dx;
    float f2 = paramFloat4 / paramGUIMesh.size.dy;
    float f3 = 1.0F;

    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)this.render.getCamera();

    if ((paramGUIMesh.boundBox[2] < localCameraOrtho2D.ZNear) || (paramGUIMesh.boundBox[5] > localCameraOrtho2D.ZFar)) {
      float f4 = localCameraOrtho2D.ZNear - paramGUIMesh.boundBox[2];
      if (f4 < paramGUIMesh.boundBox[5] - localCameraOrtho2D.ZFar)
        f4 = paramGUIMesh.boundBox[5] - localCameraOrtho2D.ZFar;
      f3 = localCameraOrtho2D.ZFar / (f4 + localCameraOrtho2D.ZFar);
    }
    paramGUIMesh.mesh.setScaleXYZ(f1, f2, f3);
    Point3d localPoint3d = _meshLoc.getPoint();
    localPoint3d.x = (paramFloat1 - paramGUIMesh.boundBox[0] * f1);
    localPoint3d.y = (this.size.dy - (paramFloat2 + paramGUIMesh.boundBox[4] * f2));
    paramGUIMesh.mesh.setPos(_meshLoc);
  }

  public boolean draw(GMesh paramGMesh, float paramFloat1, float paramFloat2)
  {
    if (paramGMesh == null) return false;
    GUIMesh localGUIMesh = (GUIMesh)paramGMesh;
    if (localGUIMesh.mesh == null) return false;
    if ((paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F)) return false;
    if (this.alpha == 0) return false;
    float f1 = this.cur.x;
    float f2 = this.cur.y;
    float f3 = paramFloat1;
    float f4 = paramFloat2;
    float f5 = f1 - this.clip.x;
    if (f5 < 0.0F) {
      f3 += f5; if (f3 <= 0.0F) return false;
      f1 = this.clip.x;
      f5 = 0.0F;
    }
    f5 = f3 + f5 - this.clip.dx;
    if (f5 > 0.0F) {
      f3 -= f5; if (f3 <= 0.0F) return false;
    }

    f5 = f2 - this.clip.y;
    if (f5 < 0.0F) {
      f4 += f5; if (f4 <= 0.0F) return false;
      f2 = this.clip.y;
      f5 = 0.0F;
    }
    f5 = f4 + f5 - this.clip.dy;
    if (f5 > 0.0F) {
      f4 -= f5; if (f4 <= 0.0F) return false;
    }

    fill_z(f1, f2, f3, f4, true);
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)this.render.getCamera();
    this.render.getViewPort(_viewPort);
    localCameraOrtho2D.activate(1.0F, this.render.renders().width(), this.render.renders().height(), _viewPort[0], _viewPort[1], _viewPort[2], _viewPort[3], _viewPort[0] + Math.round(f1), _viewPort[1] + _viewPort[3] - Math.round(f2) - Math.round(f4), Math.round(f3), Math.round(f4));

    setMeshPos(localGUIMesh, this.cur.x, this.cur.y, paramFloat1, paramFloat2);
    localGUIMesh.mesh.render();

    localCameraOrtho2D.activate(1.0F, this.render.renders().width(), this.render.renders().height(), _viewPort[0], _viewPort[1], _viewPort[2], _viewPort[3]);

    return true;
  }

  public void fill_z(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean)
  {
    if (!this.bClearZ) return;
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)this.render.getCamera();
    float f = -(paramBoolean ? localCameraOrtho2D.ZFar - 0.01F : localCameraOrtho2D.ZNear + 0.01F);
    Render.DrawTile(paramFloat1, this.size.dy - paramFloat2, paramFloat3, -paramFloat4, f, this.clear_set_z.mat.cppObject(), -1, 0.0F, 0.0F, 1.0F, 1.0F);
  }

  public boolean draw(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) return false;
    return draw(paramString, 0, paramString.length());
  }
  public boolean draw(String paramString, int paramInt1, int paramInt2) {
    if ((paramString == null) || (paramString.length() == 0)) return false;
    if (paramInt2 <= 0) return false;
    if (this.alpha == 0) return false;
    GUIFont localGUIFont = (GUIFont)this.font;
    if ((localGUIFont == null) || (localGUIFont.fnt == null)) return false;
    float f1 = localGUIFont.height;
    float f2 = this.cur.y;
    float f3 = f2 - this.clip.y;
    if (f3 < 0.0F) {
      f1 += f3; if (f1 <= 0.0F) return false;
      f2 = this.clip.y;
      f3 = 0.0F;
    }
    f3 = f1 + f3 - this.clip.dy;
    if (f3 > 0.0F) {
      f1 -= f3; if (f1 <= 0.0F) return false;
    }

    localGUIFont.fnt.outputClip(this.color.color | (this.alpha & 0xFF) << 24, Math.round(this.cur.x), Math.round(this.size.dy - this.cur.y - localGUIFont.height - localGUIFont.descender), 0.0F, paramString, paramInt1, paramInt2, Math.round(this.clip.x), Math.round(this.size.dy - this.clip.y - this.clip.dy), Math.round(this.clip.dx), Math.round(this.clip.dy));

    return true;
  }
  public boolean draw(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
    if (paramArrayOfChar == null) return false;
    if (paramInt2 <= 0) return false;
    if (this.alpha == 0) return false;
    GUIFont localGUIFont = (GUIFont)this.font;
    if ((localGUIFont == null) || (localGUIFont.fnt == null)) return false;
    float f1 = localGUIFont.height;
    float f2 = this.cur.y;
    float f3 = f2 - this.clip.y;
    if (f3 < 0.0F) {
      f1 += f3; if (f1 <= 0.0F) return false;
      f2 = this.clip.y;
      f3 = 0.0F;
    }
    f3 = f1 + f3 - this.clip.dy;
    if (f3 > 0.0F) {
      f1 -= f3; if (f1 <= 0.0F) return false;
    }

    localGUIFont.fnt.outputClip(this.color.color | (this.alpha & 0xFF) << 24, Math.round(this.cur.x), Math.round(this.size.dy - this.cur.y - localGUIFont.height - localGUIFont.descender), 0.0F, paramArrayOfChar, paramInt1, paramInt2, Math.round(this.clip.x), Math.round(this.size.dy - this.clip.y - this.clip.dy), Math.round(this.clip.dx), Math.round(this.clip.dy));

    return true;
  }

  public void copyToClipboard(String paramString) {
    StringClipboard.copy(paramString);
  }

  public String pasteFromClipboard() {
    return StringClipboard.paste();
  }

  public GUICanvas(Render paramRender) {
    super(new GSize(paramRender.getViewPortWidth(), paramRender.getViewPortHeight()));
    this.render = paramRender;
    this.clear_set_z = ((GUITexture)GTexture.New("gui/clear_set_z.mat"));
  }
}
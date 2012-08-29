package com.maddox.il2.engine;

import com.maddox.rts.CfgInt;
import com.maddox.rts.HomePath;
import com.maddox.rts.SectFile;

public class Land2Dn extends Land2D
{
  private Land2D[] land2D;
  private double[] scale;
  private double worldSizeX;
  private double worldSizeY;
  private double mapSizeX;
  private double mapSizeY;
  private double worldOfsX = 0.0D;
  private double worldOfsY = 0.0D;

  public String tgaName() {
    if (this.land2D != null) return this.land2D[0].tgaName();
    return null;
  }
  public double worldSizeX() { return this.worldSizeX; } 
  public double worldSizeY() { return this.worldSizeY; } 
  public double mapSizeX() { return this.mapSizeX; } 
  public double mapSizeY() { return this.mapSizeY; } 
  public double worldOfsX() { return this.worldOfsX; } 
  public double worldOfsY() { return this.worldOfsY; }

  private int selectRender() {
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    double d1 = localCameraOrtho2D.worldScale;
    int i = 0;
    double d2 = this.scale[0] - d1; if (d2 < 0.0D) d2 = -d2;
    for (int j = 1; j < this.land2D.length; j++) {
      double d3 = this.scale[j] - d1; if (d3 < 0.0D) d3 = -d3;
      if (d3 < d2) {
        d2 = d3;
        i = j;
      }
    }
    return i;
  }

  public void render() {
    if ((!isShow()) || (this.land2D == null) || (!(Render.currentCamera() instanceof CameraOrtho2D)))
      return;
    this.land2D[selectRender()].render();
  }

  public void render(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if ((!isShow()) || (this.land2D == null) || (!(Render.currentCamera() instanceof CameraOrtho2D)))
      return;
    this.land2D[selectRender()].render(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public boolean load(String[] paramArrayOfString, double paramDouble1, double paramDouble2) {
    destroy();
    this.land2D = new Land2D[paramArrayOfString.length];
    this.scale = new double[this.land2D.length];

    for (int i = 0; i < this.land2D.length; i++) {
      Land2D localLand2D = new Land2D();
      this.land2D[i] = localLand2D;
      if (!localLand2D.load(paramArrayOfString[i], paramDouble1, paramDouble2)) {
        destroy();
        return false;
      }
    }
    this.worldSizeX = paramDouble1;
    this.worldSizeY = paramDouble2;
    computeScale();
    return true;
  }

  public boolean reload() {
    if (this.land2D == null) return false;
    for (int i = 0; i < this.land2D.length; i++) {
      if (!this.land2D[i].reload()) {
        destroy();
        return false;
      }
    }
    computeScale();
    return true;
  }

  private void computeScale() {
    int i = RenderContext.cfgTxrQual.get();
    i -= RenderContext.cfgTxrQual.countStates() - RenderContext.cfgTxrQual.firstState() - 1;
    double d = 1.0D;
    while (i-- > 0)
      d *= 2.0D;
    for (int j = 0; j < this.land2D.length; j++)
      this.scale[j] = (this.land2D[j].pixelsX() / this.worldSizeX / d);
  }

  public boolean load(String paramString, double paramDouble1, double paramDouble2) {
    return load(new String[] { paramString }, paramDouble1, paramDouble2);
  }
  public void msgGLContext(int paramInt) {
  }
  public Land2Dn() {
  }
  public Land2Dn(String[] paramArrayOfString, double paramDouble1, double paramDouble2) {
    load(paramArrayOfString, paramDouble1, paramDouble2);
  }
  public Land2Dn(String paramString, double paramDouble1, double paramDouble2) {
    SectFile localSectFile = new SectFile("maps/" + paramString);
    String[] arrayOfString = null;
    int i = localSectFile.sectionIndex("MAP2D");
    int j;
    if (i >= 0) {
      j = localSectFile.vars(i);
      if (j > 0) {
        arrayOfString = new String[j];
        for (int k = 0; k < j; k++)
          arrayOfString[k] = HomePath.concatNames("maps/" + paramString, localSectFile.var(i, k));
      }
    }
    if (arrayOfString != null) {
      load(arrayOfString, paramDouble1, paramDouble2);
      j = localSectFile.sectionIndex("MAP2D_BIG");
      String str = null;
      if (j >= 0) {
        str = localSectFile.get("MAP2D_BIG", "tile", (String)null);
        this.mapSizeX = localSectFile.get("MAP2D_BIG", "sizeX", (float)paramDouble1);
        this.mapSizeY = localSectFile.get("MAP2D_BIG", "sizeY", (float)paramDouble2);
        this.worldOfsX = localSectFile.get("MAP2D_BIG", "ofsX", 0.0F);
        this.worldOfsY = localSectFile.get("MAP2D_BIG", "ofsY", 0.0F);
      }
      if (str != null) {
        str = HomePath.concatNames("maps/" + paramString, str);
      } else {
        this.mapSizeX = paramDouble1;
        this.mapSizeY = paramDouble2;
      }
      for (int m = 0; m < this.land2D.length; m++)
        this.land2D[m].setBigMap(str, this.mapSizeX, this.mapSizeY, this.worldOfsX, this.worldOfsY);
    }
  }

  public void destroy() {
    if (this.land2D != null) {
      for (int i = 0; i < this.land2D.length; i++) {
        Land2D localLand2D = this.land2D[i];
        if (localLand2D != null)
          localLand2D.destroy();
      }
      this.land2D = null;
    }
  }

  public boolean isDestroyed() {
    return this.land2D == null;
  }
}
package com.maddox.il2.engine;

import com.maddox.rts.Destroy;
import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSReader;
import com.maddox.util.HashMapXY16List;
import com.maddox.util.NumberTokenizer;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Land2DText
  implements Destroy
{
  public static final int STEP = 10000;
  public static final double LEVEL0_SCALE = 0.01D;
  public static final double LEVEL1_SCALE = 0.05D;
  public static int[] color = new int[20];

  private boolean bShow = true;
  private HashMapXY16List lstXY;

  public static int color(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 & 0xFF | (paramInt2 & 0xFF) << 8 | (paramInt3 & 0xFF) << 16 | 0xFF000000;
  }

  public boolean isShow()
  {
    return this.bShow; } 
  public void show(boolean paramBoolean) { this.bShow = paramBoolean; }

  public void render() {
    if ((!this.bShow) || (isDestroyed()) || (!(Render.currentCamera() instanceof CameraOrtho2D)))
      return;
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    int i = 1;
    if (localCameraOrtho2D.worldScale < 0.01D) i = 0;
    else if (localCameraOrtho2D.worldScale > 0.05D) i = 2;
    double d1 = localCameraOrtho2D.worldXOffset;
    double d2 = localCameraOrtho2D.worldYOffset;
    double d3 = d1 + (localCameraOrtho2D.right - localCameraOrtho2D.left) / localCameraOrtho2D.worldScale;
    double d4 = d2 + (localCameraOrtho2D.top - localCameraOrtho2D.bottom) / localCameraOrtho2D.worldScale;
    int j = (int)d1 / 10000;
    int k = ((int)d3 + 5000) / 10000;
    int m = (int)d2 / 10000;
    int n = ((int)d4 + 5000) / 10000;
    for (int i1 = m; i1 <= n; i1++)
      for (int i2 = j; i2 <= k; i2++) {
        List localList = this.lstXY.get(i1, i2);
        if (localList != null) {
          int i3 = localList.size();
          for (int i4 = 0; i4 < i3; i4++) {
            Item localItem = (Item)localList.get(i4);
            if (localItem.isShowLevel(i)) {
              float f1 = (float)((localItem.x - d1) * localCameraOrtho2D.worldScale);
              float f2 = (float)((localItem.y - d2) * localCameraOrtho2D.worldScale);
              switch (localItem.align()) { case 1:
                f1 -= localItem.w() / 2; break;
              case 2:
                f1 -= localItem.w();
              }
              if ((f1 > localCameraOrtho2D.right) || 
                (f2 > localCameraOrtho2D.top) || 
                (f1 + localItem.w() < localCameraOrtho2D.left) || 
                (f2 + localItem.h() < localCameraOrtho2D.bottom))
                continue;
              TTFont.font[localItem.font()].output(color[localItem.color()], f1, f2, 0.0F, localItem.text);
            }
          }
        }
      }
  }

  public void load(String paramString) {
    if (this.lstXY != null)
      this.lstXY.clear();
    else {
      this.lstXY = new HashMapXY16List(7); } ResourceBundle localResourceBundle = null;
    Object localObject;
    try { int i = paramString.lastIndexOf("/");
      if (i > 0) {
        int j = paramString.lastIndexOf("/", i - 1);
        if (j > 0) {
          localObject = paramString.substring(j + 1, i);
          localResourceBundle = ResourceBundle.getBundle("i18n/" + (String)localObject, RTSConf.cur.locale, LDRres.loader());
        }
      }
    } catch (Exception localException1) {
    }
    try {
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(paramString));
      while (true) {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        localObject = new NumberTokenizer(str1);
        float f1 = ((NumberTokenizer)localObject).next(0);
        float f2 = ((NumberTokenizer)localObject).next(0);
        int k = ((NumberTokenizer)localObject).next(7, 1, 7);
        int m = ((NumberTokenizer)localObject).next(1, 0, 2);
        int n = ((NumberTokenizer)localObject).next(1, 0, 2);
        int i1 = ((NumberTokenizer)localObject).next(0, 0, 19);
        String str2 = ((NumberTokenizer)localObject).nextToken("");
        int i2 = 0;
        int i3 = str2.length() - 1;
        while ((i2 < i3) && (str2.charAt(i2) <= ' ')) i2++;
        while ((i2 < i3) && (str2.charAt(i3) <= ' ')) i3--;
        if (i2 == i3) return;
        if ((i2 != 0) || (i3 != str2.length() - 1))
          str2 = str2.substring(i2, i3 + 1);
        if (localResourceBundle != null)
          try {
            str2 = localResourceBundle.getString(str2);
          } catch (Exception localException3) {
          }
        Item localItem = new Item(f1, f2, str2, k, n, m, i1);
        localItem.computeSizes();
        int i4 = (int)f1 / 10000;
        int i5 = (int)f2 / 10000;
        this.lstXY.put(i5, i4, localItem);
      }
      localBufferedReader.close();
      this.lstXY.allValuesTrimToSize();
    } catch (Exception localException2) {
      System.out.println("Land2DText load failed: " + localException2.getMessage());
      localException2.printStackTrace();
    }
  }

  public void contextResized() {
    if (isDestroyed())
      return;
    ArrayList localArrayList1 = new ArrayList();
    this.lstXY.allValues(localArrayList1);
    for (int i = 0; i < localArrayList1.size(); i++) {
      ArrayList localArrayList2 = (ArrayList)localArrayList1.get(i);
      for (int j = 0; j < localArrayList2.size(); j++) {
        Item localItem = (Item)localArrayList2.get(j);
        localItem.computeSizes();
      }
    }
    localArrayList1.clear();
  }

  public void clear() {
    if (isDestroyed())
      return;
    this.lstXY.clear();
  }

  public void destroy() {
    if (isDestroyed())
      return;
    this.lstXY.clear();
    this.lstXY = null;
  }
  public boolean isDestroyed() {
    return this.lstXY == null;
  }

  static
  {
    color[0] = color(0, 0, 0);
    color[1] = color(128, 0, 0);
    color[2] = color(0, 128, 0);
    color[3] = color(128, 128, 0);
    color[4] = color(0, 0, 128);
    color[5] = color(128, 0, 128);
    color[6] = color(0, 128, 128);
    color[7] = color(192, 192, 192);
    color[8] = color(192, 220, 192);
    color[9] = color(166, 202, 240);
    color[10] = color(255, 251, 240);
    color[11] = color(160, 160, 164);
    color[12] = color(128, 128, 128);
    color[13] = color(255, 0, 0);
    color[14] = color(0, 255, 0);
    color[15] = color(255, 255, 0);
    color[16] = color(0, 0, 255);
    color[17] = color(255, 0, 255);
    color[18] = color(0, 255, 255);
    color[19] = color(255, 255, 255);
  }

  static class Item
  {
    public String text;
    public int commonFilds;
    public float x;
    public float y;

    void setLevels(int paramInt)
    {
      this.commonFilds |= paramInt & 0x7; } 
    boolean isShowLevel(int paramInt) { return (this.commonFilds & 1 << paramInt) != 0; } 
    void setFont(int paramInt) {
      this.commonFilds |= (paramInt & 0x3) << 3; } 
    int font() { return this.commonFilds >> 3 & 0x3; } 
    void setAlign(int paramInt) {
      this.commonFilds |= (paramInt & 0x3) << 5; } 
    int align() { return this.commonFilds >> 5 & 0x3; } 
    void setColor(int paramInt) {
      this.commonFilds |= (paramInt & 0x1F) << 7; } 
    int color() { return this.commonFilds >> 7 & 0x1F; } 
    void setW(int paramInt) {
      this.commonFilds |= (paramInt & 0x3FF) << 12; } 
    int w() { return this.commonFilds >> 12 & 0x3FF; } 
    void setH(int paramInt) {
      this.commonFilds |= (paramInt & 0x3FF) << 22; } 
    int h() { return this.commonFilds >> 22 & 0x3FF; }

    public void computeSizes() {
      if ((this.text == null) || (this.text.length() == 0)) setW(0); else
        setW((int)TTFont.font[font()].width(this.text));
      setH(TTFont.font[font()].height());
    }

    public Item(float paramFloat1, float paramFloat2, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
      this.x = paramFloat1;
      this.y = paramFloat2;
      this.text = paramString;
      setLevels(paramInt1);
      setFont(paramInt2);
      setAlign(paramInt3);
      setColor(paramInt4);
    }
  }
}
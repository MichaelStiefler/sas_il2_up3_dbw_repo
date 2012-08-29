package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GColor;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

public class ActorText extends Actor
  implements ActorAlign
{
  public static String def_text = "";
  public static int def_font = 1;
  public static int def_align = 1;
  public static int def_color = 0;
  public static boolean[] def_bLevel = new boolean[3];
  public static GColor[] gcolor = new GColor[20];

  private String text = def_text;
  private int font = def_font;
  public int align = def_align;
  public int color = def_color;
  public boolean[] bLevel = new boolean[3];
  public static TTFont[] tfont;
  private float w = 0.0F;
  private float h;
  private static int renderLevel;
  private static double x0;
  private static double y0;
  private static double x1;
  private static double y1;
  private static Point2d p2d;

  public static void setupFonts()
  {
    tfont[0] = TTFont.font[0];
    tfont[1] = TTFont.font[1];
    tfont[2] = TTFont.font[2];
  }

  public static void setRenderLevel(int paramInt)
  {
    renderLevel = paramInt;
  }

  public static void setRenderClip(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    x0 = paramDouble1;
    y0 = paramDouble2;
    x1 = paramDouble1 + paramDouble3;
    y1 = paramDouble2 + paramDouble4;
  }

  private boolean isVisible()
  {
    if (p2d.x + this.w < x0) return false;
    if (p2d.x > x1) return false;
    if (p2d.y + this.h < y0) return false;
    return p2d.y <= y1;
  }

  public void render2d()
  {
    if (this.w == 0.0F) return;
    if (this.bLevel[renderLevel] == 0) return;
    p2d.x = ((this.pos.getAbsPoint().x - Plugin.builder.camera2D.worldXOffset) * Plugin.builder.camera2D.worldScale);

    p2d.y = ((this.pos.getAbsPoint().y - Plugin.builder.camera2D.worldYOffset) * Plugin.builder.camera2D.worldScale);

    switch (this.align) { case 1:
      p2d.x -= this.w / 2.0F; break;
    case 2:
      p2d.x -= this.w;
    }
    if (isVisible())
      tfont[this.font].output(0xFF000000 | gcolor[this.color].color, (float)p2d.x, (float)p2d.y, 0.0F, this.text);
  }

  private void computeSizes()
  {
    if ((this.text == null) || (this.text.length() == 0))
      this.w = 0.0F;
    else {
      this.w = tfont[this.font].width(this.text);
    }
    this.h = tfont[this.font].height();
  }

  public void setText(String paramString) {
    this.text = paramString;
    computeSizes();
  }
  public String getText() { return this.text; }

  public void setFont(int paramInt) {
    this.font = paramInt;
    computeSizes();
  }
  public int getFont() { return this.font; }

  public void checkLevels(int paramInt) {
    for (int i = 0; i < 3; i++)
      if (this.bLevel[i] != 0)
        return;
    this.bLevel[paramInt] = true;
  }

  public void saveAsDef() {
    def_text = this.text;
    def_font = this.font;
    def_align = this.align;
    def_color = this.color;
    def_bLevel[0] = this.bLevel[0];
    def_bLevel[1] = this.bLevel[1];
    def_bLevel[2] = this.bLevel[2];
  }

  public void align() {
    alignPosToLand(0.0D, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorText(Point3d paramPoint3d) {
    this.flags |= 8192;
    this.pos = new ActorPosMove(this);
    IconDraw.create(this);
    if (paramPoint3d != null) {
      this.pos.setAbs(paramPoint3d);
      align();
    }
    drawing(true);
    setFont(def_font);
    setText(def_text);
    this.bLevel[0] = def_bLevel[0];
    this.bLevel[1] = def_bLevel[1];
    this.bLevel[2] = def_bLevel[2];
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  static
  {
    int tmp46_45 = (def_bLevel[2] = 1); def_bLevel[1] = tmp46_45; def_bLevel[0] = tmp46_45;
    gcolor[0] = new GColor(0, 0, 0);
    gcolor[1] = new GColor(128, 0, 0);
    gcolor[2] = new GColor(0, 128, 0);
    gcolor[3] = new GColor(128, 128, 0);
    gcolor[4] = new GColor(0, 0, 128);
    gcolor[5] = new GColor(128, 0, 128);
    gcolor[6] = new GColor(0, 128, 128);
    gcolor[7] = new GColor(192, 192, 192);
    gcolor[8] = new GColor(192, 220, 192);
    gcolor[9] = new GColor(166, 202, 240);
    gcolor[10] = new GColor(255, 251, 240);
    gcolor[11] = new GColor(160, 160, 164);
    gcolor[12] = new GColor(128, 128, 128);
    gcolor[13] = new GColor(255, 0, 0);
    gcolor[14] = new GColor(0, 255, 0);
    gcolor[15] = new GColor(255, 255, 0);
    gcolor[16] = new GColor(0, 0, 255);
    gcolor[17] = new GColor(255, 0, 255);
    gcolor[18] = new GColor(0, 255, 255);
    gcolor[19] = new GColor(255, 255, 255);

    tfont = new TTFont[3];

    renderLevel = 0;

    p2d = new Point2d();

    Property.set(ActorText.class, "iconName", "icons/SelectIcon.mat");
  }
}
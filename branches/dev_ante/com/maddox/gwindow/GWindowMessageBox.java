package com.maddox.gwindow;

public class GWindowMessageBox extends GWindowFramed
{
  public static final int RESULT_CANCEL = 1;
  public static final int RESULT_OK = 2;
  public static final int RESULT_YES = 3;
  public static final int RESULT_NO = 4;
  public static final int RESULT_TIMEOUT = 5;
  public static final int BUTTONS_YES_NO_CANCEL = 0;
  public static final int BUTTONS_YES_NO = 1;
  public static final int BUTTONS_OK_CANCEL = 2;
  public static final int BUTTONS_OK = 3;
  public static final int BUTTONS_NONE = 4;
  public static final int BUTTONS_CANCEL = 5;
  public static final float zTEXT_LEFT_SPACE = 1.0F;
  public static final float zTEXT_RIGHT_SPACE = 1.0F;
  public static final float zTEXT_TOP_SPACE = 1.0F;
  public static final float zTEXT_BOTTOM_SPACE = 1.0F;
  public static final float zTEXT_HLINE_SPACE = 0.5F;
  public static final float zBUTTONS_TOP_SPACE = 1.0F;
  public static final float zBUTTONS_BOTTOM_SPACE = 0.5F;
  public static final float zBUTTON_HEIGHT = 2.0F;
  public static final float zBUTTON_WIDTH = 7.0F;
  public static final float zBUTTONS_RIGHT_SPACE = 1.0F;
  public static final float zBUTTONS_SPACE = 1.0F;
  public static final float zSEPARATE_SPACE = 0.5F;
  public String message;
  public int messageFont = 0;
  public int buttons;
  public boolean bTimeOut;
  public float timeOut;
  public float metricWidth;
  public boolean bModal;
  private int result = 0;

  public void result(int paramInt)
  {
  }

  public void doResult(int paramInt) {
    this.result = paramInt;
    close(false);
    result(paramInt);
  }

  public void close(boolean paramBoolean) {
    super.close(paramBoolean);
    if (this.result == 0) {
      this.result = 1;
      result(this.result);
    }
  }

  public void preRender() {
    super.preRender();
    if (this.bTimeOut) {
      this.timeOut -= this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec;
      if (this.timeOut <= 0.0F)
        doResult(5);
    }
  }

  private void computeWin()
  {
    float f1 = lookAndFeel().metric();
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = (this.metricWidth * f1);
    GRegion localGRegion = getClientRegion();
    float f2 = localGRegion.dx - f1 * 2.0F;
    setCanvasFont(this.messageFont);
    int i = computeLines(this.message, 0, this.message.length(), f2);
    if (i == 0) i = 1;
    float f3 = i * this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[this.messageFont].height + (i - 1) * (0.5F * f1);

    f3 += f1 * 5.5F;

    this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGRegion.dy + f3);
    localGRegion = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.getClientRegion();
    if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy > localGRegion.dy)
      this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = localGRegion.dy;
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.x = ((this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx) / 2.0F);
    this.jdField_win_of_type_ComMaddoxGwindowGRegion.y = ((this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) / 2.0F);
  }

  public void created() {
    super.created();
    computeWin();
  }

  public void resolutionChanged()
  {
    computeWin();
    resized();
  }

  public void afterCreated() {
    GRegion localGRegion = getClientRegion();
    float f1 = localGRegion.dx / lookAndFeel().metric() - 8.0F;
    float f2 = localGRegion.dy / lookAndFeel().metric() - 2.5F;
    this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy, false, new Client());
    Client localClient = (Client)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
    switch (this.buttons) {
    case 4:
    default:
      break;
    case 0:
      localClient.addDefault(new Button(localClient, f1 - 16.0F, f2, 7.0F, 2.0F, lAF().i18n("&OK"), null, 2));

      localClient.addControl(new Button(localClient, f1 - 8.0F, f2, 7.0F, 2.0F, lAF().i18n("&No"), null, 4));

      localClient.addEscape(new Button(localClient, f1, f2, 7.0F, 2.0F, lAF().i18n("&Cancel"), null, 1));

      break;
    case 1:
      localClient.addDefault(new Button(localClient, f1 - 8.0F, f2, 7.0F, 2.0F, lAF().i18n("&Yes"), null, 3));

      localClient.addEscape(new Button(localClient, f1, f2, 7.0F, 2.0F, lAF().i18n("&No"), null, 4));

      break;
    case 2:
      localClient.addDefault(new Button(localClient, f1 - 8.0F, f2, 7.0F, 2.0F, lAF().i18n("&OK"), null, 2));

      localClient.addEscape(new Button(localClient, f1, f2, 7.0F, 2.0F, lAF().i18n("&Cancel"), null, 1));

      break;
    case 3:
      localClient.addDefault(new Button(localClient, f1, f2, 7.0F, 2.0F, lAF().i18n("&OK"), null, 2));

      break;
    case 5:
      localClient.addDefault(new Button(localClient, f1, f2, 7.0F, 2.0F, lAF().i18n("&Cancel"), null, 1));
    }

    super.afterCreated();
    if (this.bModal)
      showModal();
  }

  public GWindowMessageBox(GWindow paramGWindow, float paramFloat1, boolean paramBoolean, String paramString1, String paramString2, int paramInt, float paramFloat2)
  {
    this.bSizable = false;
    this.title = paramString1;
    this.message = paramString2;
    this.buttons = paramInt;
    if (paramFloat2 <= 0.0F) { this.bTimeOut = false; } else {
      this.bTimeOut = true; this.timeOut = paramFloat2;
    }this.metricWidth = paramFloat1;
    this.bModal = paramBoolean;
    doNew(paramGWindow, 0.0F, 0.0F, 100.0F, 100.0F, false);
  }

  class Button extends GWindowButton
  {
    int res;

    public boolean notify(int paramInt1, int paramInt2)
    {
      boolean bool = super.notify(paramInt1, paramInt2);
      if (paramInt1 == 2)
        GWindowMessageBox.this.doResult(this.res);
      return bool;
    }

    public Button(GWindow paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramString1, String paramString2, String paramInt, int arg9) {
      super(paramFloat2, paramFloat3, paramFloat4, paramString1, paramString2, paramInt);
      int i;
      this.res = i;
    }
  }

  class Client extends GWindowDialogClient
  {
    Client()
    {
    }

    public void render()
    {
      super.render();
      GRegion localGRegion = getClientRegion();

      lookAndFeel().drawSeparateH(this, localGRegion.x + lookAndFeel().metric(0.5F), localGRegion.dy - lookAndFeel().metric(3.5F), localGRegion.dx - 2.0F * lookAndFeel().metric(0.5F));

      localGRegion.x += lookAndFeel().metric(1.0F);
      localGRegion.dx -= lookAndFeel().metric(2.0F);
      localGRegion.y += lookAndFeel().metric(1.0F);
      localGRegion.dy -= lookAndFeel().metric(5.5F);

      if (pushClipRegion(localGRegion, true, 0.0F)) {
        setCanvasFont(GWindowMessageBox.this.messageFont);
        lookAndFeel().setMessageBoxTextColor(this);
        drawLines(0.0F, 0.0F, GWindowMessageBox.this.message, 0, GWindowMessageBox.this.message.length(), localGRegion.dx, lookAndFeel().metric(0.5F) + this.root.textFonts[GWindowMessageBox.this.messageFont].height);

        popClip();
      }
    }
  }
}
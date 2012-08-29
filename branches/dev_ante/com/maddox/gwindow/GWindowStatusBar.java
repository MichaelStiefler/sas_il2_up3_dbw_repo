package com.maddox.gwindow;

public class GWindowStatusBar extends GWindow
{
  public String help;
  public String defaultHelp = "";

  public void setHelp(String paramString) {
    this.help = paramString;
  }
  public void setDefaultHelp(String paramString) {
    this.defaultHelp = paramString;
  }

  public void render() {
    lookAndFeel().render(this);
  }
  public GSize getMinSize(GSize paramGSize) {
    return lookAndFeel().getMinSize(this, paramGSize);
  }
  public GRegion getClientRegion(GRegion paramGRegion, float paramFloat) {
    return lookAndFeel().getClientRegion(this, paramGRegion, paramFloat);
  }

  public void created() {
    super.created();
    this.bAlwaysOnTop = true;
    this.bTransient = true;
    this.defaultHelp = lAF().i18n("Press_ESC_to_return_to_the_game");
  }
}
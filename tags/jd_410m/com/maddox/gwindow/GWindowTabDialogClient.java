package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowTabDialogClient extends GWindowDialogClient
{
  public ArrayList tabs = new ArrayList();
  public int current = 0;
  public int prev = -1;
  public int firstView = 0;
  public LButton lButton;
  public RButton rButton;

  public int sizeTabs()
  {
    return this.tabs.size();
  }
  public void addTab(String paramString, GWindow paramGWindow) {
    Tab localTab = (Tab)create(new Tab());
    localTab.cap = new GCaption(paramString);
    localTab.client = paramGWindow;
    paramGWindow.parentWindow = this;
    paramGWindow.hideWindow();
    this.tabs.add(localTab);
    resized();
  }
  public void addTab(int paramInt, String paramString, GWindow paramGWindow) {
    Tab localTab = (Tab)create(new Tab());
    localTab.cap = new GCaption(paramString);
    localTab.client = paramGWindow;
    paramGWindow.parentWindow = this;
    paramGWindow.hideWindow();
    this.tabs.add(paramInt, localTab);
    resized();
  }
  public Tab createTab(String paramString, GWindow paramGWindow) {
    Tab localTab = (Tab)create(new Tab());
    localTab.cap = new GCaption(paramString);
    localTab.client = paramGWindow;
    localTab.hideWindow();
    paramGWindow.parentWindow = this;
    paramGWindow.hideWindow();
    return localTab;
  }
  public void addTab(int paramInt, Tab paramTab) {
    paramTab.showWindow();
    this.tabs.add(paramInt, paramTab);
    resized();
  }
  public void removeTab(int paramInt) {
    setCurrent(0);
    Tab localTab = (Tab)this.tabs.get(paramInt);
    localTab.hideWindow();
    this.tabs.remove(paramInt);
    this.prev = -1;
    this.firstView = 0;
    resized();
  }
  public void clearTabs() {
    int i = this.tabs.size();
    while (i-- > 0)
      removeTab(0); 
  }

  public Tab getTab(int paramInt) {
    return (Tab)this.tabs.get(paramInt);
  }
  public GWindow getTabClient(int paramInt) {
    return ((Tab)this.tabs.get(paramInt)).client;
  }
  public int getPrev() { return this.prev; } 
  public int getCurrent() { return this.current; } 
  public void setCurrent(int paramInt) {
    setCurrent(paramInt, true);
  }
  public void setCurrent(int paramInt, boolean paramBoolean) {
    if (this.current == paramInt) return;
    Tab localTab;
    if (this.current >= 0) {
      localTab = (Tab)this.tabs.get(this.current);
      localTab.client.hideWindow();
    }
    this.prev = this.current;
    this.current = paramInt;
    if (this.current >= 0) {
      localTab = (Tab)this.tabs.get(this.current);
      localTab.client.showWindow();
    }
    resized();
    if (paramBoolean)
      notify(2, this.prev); 
  }

  public void scroll(int paramInt) {
    if (paramInt < 0) {
      this.firstView += paramInt;
      if (this.firstView < 0) this.firstView = 0; 
    }
    else {
      int i = this.firstView + paramInt;
      if (i >= sizeTabs()) return;
      float f = 0.0F;
      for (int j = this.firstView; j < this.tabs.size(); j++) {
        Tab localTab = (Tab)this.tabs.get(j);
        f += localTab.win.dx;
      }
      if (f <= this.win.dx) return;
      this.firstView = i;
    }
    resized();
  }

  public void resized() {
    float f1 = 0.0F;
    Object localObject;
    for (int i = 0; i < this.firstView; i++) {
      Tab localTab = (Tab)this.tabs.get(i);
      localObject = localTab.getMinSize();
      f1 += ((GSize)localObject).dx;
    }
    f1 = -f1;
    float f2 = 0.0F;
    for (int j = 0; j < this.tabs.size(); j++) {
      localObject = (Tab)this.tabs.get(j);
      GSize localGSize = ((Tab)localObject).getMinSize();
      if (j == this.current) ((Tab)localObject).setSize(localGSize.dx, localGSize.dy + lookAndFeel().bevelTabDialogClient.T.dy); else
        ((Tab)localObject).setSize(localGSize.dx, localGSize.dy);
      ((Tab)localObject).setPos(f1, 0.0F);
      ((Tab)localObject).client.setPos(lookAndFeel().bevelTabDialogClient.L.dx, ((Tab)localObject).win.dy + lookAndFeel().bevelTabDialogClient.B.dy);

      ((Tab)localObject).client.setSize(this.win.dx - lookAndFeel().bevelTabDialogClient.L.dx - lookAndFeel().bevelTabDialogClient.R.dx, this.win.dy - lookAndFeel().bevelTabDialogClient.B.dy - lookAndFeel().bevelTabDialogClient.T.dy - ((Tab)localObject).win.dy);

      if (j == this.current) ((Tab)localObject).client.showWindow(); else
        ((Tab)localObject).client.hideWindow();
      f1 += ((Tab)localObject).win.dx;
      f2 += ((Tab)localObject).win.dx;
    }
    if ((f2 > this.win.dx) || (this.firstView > 0)) {
      this.lButton.setSize(lookAndFeel().getHScrollBarW(), lookAndFeel().getHScrollBarH());
      this.rButton.setSize(lookAndFeel().getHScrollBarW(), lookAndFeel().getHScrollBarH());
      this.lButton.setPos(this.win.dx - this.lButton.win.dx - this.rButton.win.dx, 0.0F);
      this.rButton.setPos(this.win.dx - this.rButton.win.dx, 0.0F);
      this.lButton.showWindow();
      this.rButton.showWindow();
    } else {
      this.lButton.hideWindow();
      this.rButton.hideWindow();
    }
  }

  public void render()
  {
    super.render();
    lookAndFeel().render(this);
  }

  public void resolutionChanged() {
    for (int i = 0; i < this.tabs.size(); i++) {
      Tab localTab = (Tab)this.tabs.get(i);
      localTab.client.resolutionChanged();
    }
    resized();
  }

  public GSize getMinSize(GSize paramGSize) {
    if (this.tabs == null)
      return super.getMinSize(paramGSize);
    float f1 = 0.0F;
    float f2 = 0.0F;
    for (int i = 0; i < this.tabs.size(); i++) {
      Tab localTab2 = (Tab)this.tabs.get(i);
      localTab2.client.getMinSize(paramGSize);
      if (f1 < paramGSize.dx) f1 = paramGSize.dx;
      if (f2 >= paramGSize.dy) continue; f2 = paramGSize.dy;
    }
    f1 += lookAndFeel().bevelTabDialogClient.L.dx + lookAndFeel().bevelTabDialogClient.R.dx;

    f2 += lookAndFeel().bevelTabDialogClient.T.dy + lookAndFeel().bevelTabDialogClient.B.dx;

    Tab localTab1 = (Tab)this.tabs.get(0);
    localTab1.getMinSize(paramGSize);
    paramGSize.dx += f1;
    paramGSize.dy += f2;
    return paramGSize;
  }

  public void afterCreated() {
    super.afterCreated();
    this.lButton = new LButton(this);
    this.rButton = new RButton(this);
    resized();
  }
  public GWindowTabDialogClient() {
  }

  public GWindowTabDialogClient(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public class RButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
      GWindowTabDialogClient.this.scroll(1);
    }
    public void created() {
      this.bAcceptsKeyFocus = false;
      this.bAlwaysOnTop = true;
      lookAndFeel().setupScrollButtonRIGHT(this);
    }
    public RButton(GWindow arg2) { super();
    }
  }

  public class LButton extends GWindowButtonTexture
  {
    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      if ((paramInt != 0) || (!paramBoolean) || (!this.bEnable)) return;
      GWindowTabDialogClient.this.scroll(-1);
    }
    public void created() {
      this.bAcceptsKeyFocus = false;
      this.bAlwaysOnTop = true;
      lookAndFeel().setupScrollButtonLEFT(this);
    }
    public LButton(GWindow arg2) { super();
    }
  }

  public class Tab extends GWindowDialogControl
  {
    public GWindow client;

    public Tab()
    {
    }

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = GWindowTabDialogClient.this.tabs.indexOf(this);
        if (i >= 0)
          GWindowTabDialogClient.this.setCurrent(i);
      }
      return true;
    }

    public boolean isCurrent() {
      return GWindowTabDialogClient.this.tabs.indexOf(this) == GWindowTabDialogClient.this.current;
    }

    public void render() {
      lookAndFeel().render(this);
    }
    public GSize getMinSize(GSize paramGSize) {
      return lookAndFeel().getMinSize(this, paramGSize);
    }
    public void created() {
      super.created();
    }
  }
}
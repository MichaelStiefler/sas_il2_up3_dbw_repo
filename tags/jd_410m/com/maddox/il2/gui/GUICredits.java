package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;

public class GUICredits extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bExit;
  public ScrollDescript wScrollDescription;
  public Descript wDescript;
  String text = "credits";
  GFont fontCredits;

  public void _enter()
  {
    this.text = I18N.credits("credits");
    this.client.showWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUICredits(GWindowRoot paramGWindowRoot)
  {
    super(16);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("credits.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.fontCredits = GFont.New("courb10");
    this.dialogClient.create(this.wScrollDescription = new ScrollDescript());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bExit = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUICredits.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(648.0F), x1024(264.0F), y1024(48.0F), 0, GUICredits.this.i18n("credits.Back"));
      setCanvasColorWHITE();
      draw(x1024(336.0F), y1024(640.0F), x1024(656.0F), y1024(32.0F), 0, I18N.credits("copyright1"));
      draw(x1024(336.0F), y1024(672.0F), x1024(656.0F), y1024(32.0F), 0, I18N.credits("copyright2"));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUICredits.this.bExit.setPosC(x1024(56.0F), y1024(672.0F));
      GUICredits.this.wScrollDescription.setPosSize(x1024(32.0F), y1024(32.0F), x1024(960.0F), y1024(576.0F));
    }
  }

  public class Descript extends GWindowDialogClient
  {
    public Descript()
    {
    }

    public void render()
    {
      if (GUICredits.this.text != null) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        GFont localGFont = this.root.C.font;
        this.root.C.font = GUICredits.this.fontCredits;
        setCanvasColorBLACK();
        this.root.C.clip.y += localGBevel.T.dy;
        this.root.C.clip.dy -= localGBevel.T.dy + localGBevel.B.dy;
        drawLines(localGBevel.L.dx + 2.0F, localGBevel.T.dy + 2.0F, GUICredits.this.text, 0, GUICredits.this.text.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F, this.root.C.font.height);

        this.root.C.font = localGFont;
      }
    }

    public void computeSize() {
      if (GUICredits.this.text != null) {
        GFont localGFont = this.root.C.font;
        this.root.C.font = GUICredits.this.fontCredits;
        this.win.dx = this.parentWindow.win.dx;
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        int i = computeLines(GUICredits.this.text, 0, GUICredits.this.text.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
        this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        if (this.win.dy > this.parentWindow.win.dy) {
          this.win.dx = (this.parentWindow.win.dx - lookAndFeel().getVScrollBarW());
          i = computeLines(GUICredits.this.text, 0, GUICredits.this.text.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
          this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        }
        this.root.C.font = localGFont;
      } else {
        this.win.dx = this.parentWindow.win.dx;
        this.win.dy = this.parentWindow.win.dy;
      }
    }
  }

  public class ScrollDescript extends GWindowScrollingDialogClient
  {
    public ScrollDescript()
    {
    }

    public void created()
    {
      this.fixed = (GUICredits.this.wDescript = (GUICredits.Descript)create(new GUICredits.Descript(GUICredits.this)));
      this.fixed.bNotify = true;
      this.bNotify = true;
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void resized() {
      if (GUICredits.this.wDescript != null) {
        GUICredits.this.wDescript.computeSize();
      }
      super.resized();
      if (this.vScroll.isVisible()) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        this.vScroll.setPos(this.win.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.vScroll.setSize(lookAndFeel().getVScrollBarW(), this.win.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
  }
}
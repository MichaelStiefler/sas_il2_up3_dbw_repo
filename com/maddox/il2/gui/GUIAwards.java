package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.RTSConf;
import java.util.ResourceBundle;

public class GUIAwards extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bPrev;
  public static int[] indexIcons;
  public GTexture[] texIcons;
  public String[] text;

  public void _enter()
  {
    if (indexIcons != null) {
      this.texIcons = new GTexture[indexIcons.length];
      int i;
      if ((Main.cur().campaign.branch().equals("de")) && (World.cur().isHakenAllowed()))
        for (i = 0; i < indexIcons.length; i++)
          this.texIcons[i] = GTexture.New("missions/campaign/de/awardh" + indexIcons[i] + ".mat");
      else if ((Main.cur().campaign.branch().equals("fi")) && (World.cur().isHakenAllowed()))
        for (i = 0; i < indexIcons.length; i++)
          this.texIcons[i] = GTexture.New("missions/campaign/fi/awardh" + indexIcons[i] + ".mat");
      else {
        for (i = 0; i < indexIcons.length; i++) {
          this.texIcons[i] = GTexture.New("missions/campaign/" + Main.cur().campaign.branch() + "/award" + indexIcons[i] + ".mat");
        }
      }
      this.text = new String[indexIcons.length];
      try {
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("missions/campaign/" + Main.cur().campaign.branch() + "/award", RTSConf.cur.locale);
        for (int j = 0; j < indexIcons.length; j++)
          this.text[j] = localResourceBundle.getString("" + indexIcons[j]); 
      } catch (Exception localException) {
      }
    }
    this.client.activateWindow();
  }
  public void _leave() {
    this.texIcons = null;
    this.client.hideWindow();
  }

  public GUIAwards(GWindowRoot paramGWindowRoot)
  {
    super(32);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("awards.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.dialogClient.activateWindow();
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

      if (paramGWindow == GUIAwards.this.bPrev) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);

      setCanvasColorWHITE();
      setCanvasFont(0);
      float f1 = this.root.C.font.height;
      draw(x1024(96.0F), y1024(658.0F), x1024(320.0F), y1024(48.0F), 0, GUIAwards.this.i18n("awards.Back"));

      if (GUIAwards.this.texIcons != null) {
        GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
        GBevel localGBevel = localGUILookAndFeel.bevelAward;
        float f2 = 40.0F;
        float f3 = 40.0F;
        float f4 = 208.0F;
        float f5 = 240.0F;
        float f6 = 288.0F;
        int i = 0;
        for (int j = 0; j < 2; j++)
          for (int k = 0; k < 4; k++) {
            if (i == GUIAwards.this.texIcons.length)
              return;
            if (GUIAwards.this.texIcons[i] != null) {
              localGUILookAndFeel.drawBevel(this, x1024(f2 + k * f5) - localGBevel.L.dx, y1024(f3 + j * f6) - localGBevel.T.dy, x1024(224.0F) + localGBevel.L.dx + localGBevel.R.dx, y1024(192.0F) + localGBevel.T.dy + localGBevel.B.dy, localGBevel, localGUILookAndFeel.basicelements, false);

              draw(x1024(f2 + k * f5), y1024(f3 + j * f6), x1024(224.0F), y1024(192.0F), GUIAwards.this.texIcons[i]);
            }
            if (GUIAwards.this.text[i] != null)
              drawLines(x1024(f2 + k * f5), y1024(f3 + j * f6 + f4), GUIAwards.this.text[i], 0, GUIAwards.this.text[i].length(), x1024(224.0F), f1, 4);
            i++;
          }
      }
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIAwards.this.bPrev.setPosC(x1024(56.0F), y1024(680.0F));
    }
  }
}
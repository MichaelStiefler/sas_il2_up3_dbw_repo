package com.maddox.il2.gui;

import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HomePath;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ResourceBundle;

public class GUIBriefingGeneric extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public ScrollDescript wScrollDescription;
  public Descript wDescript;
  public GUIButton bLoodout;
  public GUIButton bDifficulty;
  public GUIButton bPrev;
  public GUIButton bNext;
  public String textDescription;
  public String[] textArmyDescription;
  public String curMissionName;
  public int curMissionNum = -1;
  public String curMapName;
  protected String briefSound = null;
  protected Main3D main;
  protected GUIRenders renders;
  protected RenderMap2D renderMap2D;
  protected CameraOrtho2D cameraMap2D;
  protected TTFont gridFont;
  protected Mat emptyMat;
  protected float[] scale = { 0.064F, 0.032F, 0.016F, 0.008F, 0.004F, 0.002F, 0.001F, 0.0005F, 0.00025F };
  protected int scales = this.scale.length;
  protected int curScale = this.scales - 1;
  protected int curScaleDirect = -1;
  protected float landDX;
  protected float landDY;
  private float[] line2XYZ = new float[6];
  private int _gridCount;
  private int[] _gridX = new int[20];
  private int[] _gridY = new int[20];
  private int[] _gridVal = new int[20];

  protected boolean bLPressed = false;
  protected boolean bRPressed = false;

  public void _enter()
  {
    this.client.activateWindow();
    try {
      SectFile localSectFile = Main.cur().currentMissionFile;
      this.briefSound = localSectFile.get("MAIN", "briefSound");
      String str1 = Main.cur().currentMissionFile.fileName();
      String str2 = localSectFile.get("MAIN", "MAP");
      if ((!str1.equals(this.curMissionName)) || (!str2.equals(this.curMapName)) || (this.curMissionNum != Main.cur().missionCounter) || (this.main.land2D == null))
      {
        this.dialogClient.resized();
        fillTextDescription();
        fillMap();
        Front.loadMission(localSectFile);
        this.curMissionName = str1;
        this.curMapName = str2;
        this.curMissionNum = Main.cur().missionCounter;
      }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    Front.setMarkersChanged();
    this.wScrollDescription.resized();
    if (this.wScrollDescription.vScroll.isVisible())
      this.wScrollDescription.vScroll.setPos(0.0F, true); 
  }

  public void _leave() {
    this.client.hideWindow();
  }

  private void setPosCamera(float paramFloat1, float paramFloat2)
  {
    float f1 = (float)((this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale);
    this.cameraMap2D.worldXOffset = (paramFloat1 - f1 / 2.0F);
    float f2 = (float)((this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale);
    this.cameraMap2D.worldYOffset = (paramFloat2 - f2 / 2.0F);
    clipCamera();
  }

  private void scaleCamera() {
    this.cameraMap2D.worldScale = (this.scale[this.curScale] * this.renders.root.win.dx / 1024.0F);
  }

  private void clipCamera() {
    if (this.cameraMap2D.worldXOffset < -Main3D.cur3D().land2D.worldOfsX())
      this.cameraMap2D.worldXOffset = (-Main3D.cur3D().land2D.worldOfsX());
    float f1 = (float)((this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale);
    if (this.cameraMap2D.worldXOffset > Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - f1) {
      this.cameraMap2D.worldXOffset = (Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - f1);
    }
    if (this.cameraMap2D.worldYOffset < -Main3D.cur3D().land2D.worldOfsY())
      this.cameraMap2D.worldYOffset = (-Main3D.cur3D().land2D.worldOfsY());
    float f2 = (float)((this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale);
    if (this.cameraMap2D.worldYOffset > Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - f2)
      this.cameraMap2D.worldYOffset = (Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - f2);
  }

  private void computeScales() {
    float f1 = this.renders.win.dx * 1024.0F / this.renders.root.win.dx;
    float f2 = this.renders.win.dy * 768.0F / this.renders.root.win.dy;
    int i = 0;
    float f3 = 0.064F;
    float f4;
    float f5;
    for (; i < this.scale.length; i++) {
      this.scale[i] = f3;
      f4 = this.landDX * f3;
      if (f4 < f1) break;
      f5 = this.landDY * f3;
      if (f5 < f2) break;
      f3 /= 2.0F;
    }
    this.scales = i;
    if (this.scales < this.scale.length) {
      f4 = f1 / this.landDX;
      f5 = f2 / this.landDY;
      this.scale[i] = f4;
      if (f5 > f4)
        this.scale[i] = f5;
      this.scales = (i + 1);
    }
    this.curScale = (this.scales - 1);
    this.curScaleDirect = -1;
  }

  private void drawGrid2D() {
    int i = gridStep();
    int j = (int)((this.cameraMap2D.worldXOffset + Main3D.cur3D().land2D.worldOfsX()) / i);
    int k = (int)((this.cameraMap2D.worldYOffset + Main3D.cur3D().land2D.worldOfsY()) / i);
    double d1 = (this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale;
    double d2 = (this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale;
    int m = (int)(d1 / i) + 2;
    int n = (int)(d2 / i) + 2;
    float f1 = (float)((j * i - this.cameraMap2D.worldXOffset - Main3D.cur3D().land2D.worldOfsX()) * this.cameraMap2D.worldScale + 0.5D);
    float f2 = (float)((k * i - this.cameraMap2D.worldYOffset - Main3D.cur3D().land2D.worldOfsY()) * this.cameraMap2D.worldScale + 0.5D);
    float f3 = (float)(m * i * this.cameraMap2D.worldScale);
    float f4 = (float)(n * i * this.cameraMap2D.worldScale);
    float f5 = (float)(i * this.cameraMap2D.worldScale);
    this._gridCount = 0;
    Render.drawBeginLines(-1);
    float f6;
    int i2;
    for (int i1 = 0; i1 <= n; i1++) {
      f6 = f2 + i1 * f5;
      i2 = (i1 + k) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f1; this.line2XYZ[1] = f6; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = (f1 + f3); this.line2XYZ[4] = f6; this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i2 << 16 | i2 << 8 | i2, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i2 == 192)
        drawGridText(0, (int)f6, (k + i1) * i);
    }
    for (i1 = 0; i1 <= m; i1++) {
      f6 = f1 + i1 * f5;
      i2 = (i1 + j) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f6; this.line2XYZ[1] = f2; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = f6; this.line2XYZ[4] = (f2 + f4); this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i2 << 16 | i2 << 8 | i2, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i2 == 192)
        drawGridText((int)f6, 0, (j + i1) * i);
    }
    Render.drawEnd();
    drawGridText();
  }

  private int gridStep() {
    float f1 = this.cameraMap2D.right - this.cameraMap2D.left;
    float f2 = this.cameraMap2D.top - this.cameraMap2D.bottom;
    double d = f1;
    if (f2 < f1) d = f2;
    d /= this.cameraMap2D.worldScale;
    int i = 100000;
    for (int j = 0; (j < 5) && 
      (i * 3 > d); j++)
    {
      i /= 10;
    }
    return i;
  }

  private void drawGridText(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 <= 0) || (this._gridCount == 20)) return;
    this._gridX[this._gridCount] = paramInt1;
    this._gridY[this._gridCount] = paramInt2;
    this._gridVal[this._gridCount] = paramInt3;
    this._gridCount += 1;
  }
  private void drawGridText() {
    for (int i = 0; i < this._gridCount; i++)
      this.gridFont.output(-4144960, this._gridX[i] + 2, this._gridY[i] + 2, 0.0F, this._gridVal[i] / 1000 + "." + this._gridVal[i] % 1000 / 100);
    this._gridCount = 0;
  }

  protected void doRenderMap2D()
  {
  }

  protected void doMouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0) {
      this.bLPressed = paramBoolean;
      this.renders.mouseCursor = (this.bLPressed ? 7 : 3);
    } else if ((paramInt == 1) && (this.scales > 1)) {
      this.bRPressed = paramBoolean;
      if ((this.bRPressed) && (!this.bLPressed)) {
        float f1 = (float)(this.cameraMap2D.worldXOffset + paramFloat1 / this.cameraMap2D.worldScale);
        float f2 = (float)(this.cameraMap2D.worldYOffset + (this.renders.win.dy - paramFloat2 - 1.0F) / this.cameraMap2D.worldScale);

        this.curScale += this.curScaleDirect;
        if (this.curScaleDirect < 0) {
          if (this.curScale < 0) {
            this.curScale = 1;
            this.curScaleDirect = 1;
          }
        }
        else if (this.curScale == this.scales) {
          this.curScale = (this.scales - 2);
          this.curScaleDirect = -1;
        }

        scaleCamera();
        f1 = (float)(f1 - (paramFloat1 - this.renders.win.dx / 2.0F) / this.cameraMap2D.worldScale);
        f2 = (float)(f2 + (paramFloat2 - this.renders.win.dy / 2.0F) / this.cameraMap2D.worldScale);
        setPosCamera(f1, f2);
      }
    }
  }

  protected void doMouseMove(float paramFloat1, float paramFloat2) {
    if ((this.bLPressed) && (this.renders.mouseCursor == 7)) {
      this.cameraMap2D.worldXOffset -= this.renders.root.mouseStep.dx / this.cameraMap2D.worldScale;
      this.cameraMap2D.worldYOffset += this.renders.root.mouseStep.dy / this.cameraMap2D.worldScale;
      clipCamera();
    }
  }

  protected void createRenderWindow(GWindow paramGWindow) {
    this.renders = new GUIRenders(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false) {
      public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
        GUIBriefingGeneric.this.doMouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
      }
      public void mouseMove(float paramFloat1, float paramFloat2) {
        GUIBriefingGeneric.this.doMouseMove(paramFloat1, paramFloat2);
      }
    };
    this.renders.mouseCursor = 3;
    this.renders.bNotify = true;

    this.cameraMap2D = new CameraOrtho2D();
    this.cameraMap2D.worldScale = this.scale[this.curScale];
    this.renderMap2D = new RenderMap2D(this.renders.renders, 1.0F);
    this.renderMap2D.setCamera(this.cameraMap2D);
    this.renderMap2D.setShow(true);
    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.renderMap2D.setLightEnv(localLightEnvXY);
    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(1.0F, -2.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);
    this.gridFont = TTFont.font[1];
    this.emptyMat = Mat.New("icons/empty.mat");
    this.main = Main3D.cur3D();
  }

  protected void fillMap() throws Exception {
    SectFile localSectFile1 = Main.cur().currentMissionFile;

    String str1 = localSectFile1.get("MAIN", "MAP");
    if (str1 == null)
      throw new Exception("No MAP in mission file ");
    SectFile localSectFile2 = new SectFile("maps/" + str1);

    String str2 = localSectFile2.get("MAP", "TypeMap", (String)null);
    if (str2 == null)
      throw new Exception("Bad MAP description in mission file ");
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(str2);
    if (localNumberTokenizer.hasMoreTokens()) {
      localNumberTokenizer.next();
      if (localNumberTokenizer.hasMoreTokens())
        str2 = localNumberTokenizer.next();
    }
    str2 = HomePath.concatNames("maps/" + str1, str2);
    int[] arrayOfInt = new int[3];
    if (!Mat.tgaInfo(str2, arrayOfInt))
      throw new Exception("Bad MAP description in mission file ");
    this.landDX = (arrayOfInt[0] * 200.0F);
    this.landDY = (arrayOfInt[1] * 200.0F);

    if (this.main.land2D != null) {
      if (!this.main.land2D.isDestroyed())
        this.main.land2D.destroy();
      this.main.land2D = null;
    }
    str2 = null;
    int i = localSectFile2.sectionIndex("MAP2D");
    if (i >= 0) {
      j = localSectFile2.vars(i);
      if (j > 0) {
        this.main.land2D = new Land2Dn(str1, this.landDX, this.landDY);
        this.landDX = (float)this.main.land2D.mapSizeX();
        this.landDY = (float)this.main.land2D.mapSizeY();
      }

    }

    if (this.main.land2DText == null)
      this.main.land2DText = new Land2DText();
    else
      this.main.land2DText.clear();
    int j = localSectFile2.sectionIndex("text");
    if ((j >= 0) && (localSectFile2.vars(j) > 0)) {
      String str3 = localSectFile2.var(j, 0);
      this.main.land2DText.load(HomePath.concatNames("maps/" + str1, str3));
    }
    computeScales();
    scaleCamera();
    setPosCamera(this.landDX / 2.0F, this.landDY / 2.0F);
  }

  protected void prepareTextDescription(int paramInt)
  {
    if (this.textDescription == null) return;
    if ((this.textArmyDescription == null) || (this.textArmyDescription.length != paramInt)) {
      this.textArmyDescription = new String[paramInt];
    }
    for (int i = 0; i < paramInt; i++) {
      this.textArmyDescription[i] = null;
      prepareTextDescriptionArmy(i);
    }
  }

  private void prepareTextDescriptionArmy(int paramInt) {
    String str = (Army.name(paramInt) + ">").toUpperCase();
    int i = 0;
    int j = this.textDescription.length();
    StringBuffer localStringBuffer = new StringBuffer();
    while (i < j) {
      int k = this.textDescription.indexOf("<ARMY", i);
      if (k >= i) {
        if (k > i)
          subString(localStringBuffer, this.textDescription, i, k);
        int m = this.textDescription.indexOf("</ARMY>", k);
        if (m == -1) m = j;
        k += "<ARMY".length();
        while ((k < j) && (Character.isSpaceChar(this.textDescription.charAt(k))))
          k++;
        if (k == j) { i = j; break; }
        if (this.textDescription.startsWith(str, k)) {
          k += str.length();
          if ((k < m) && (this.textDescription.charAt(k) == '\n')) k++;
          subString(localStringBuffer, this.textDescription, k, m);
        }
        i = m + "</ARMY>".length();
        if ((i < j) && (this.textDescription.charAt(i) == '\n')) i++; 
      }
      else {
        subString(localStringBuffer, this.textDescription, i, j);
        i = j;
      }
    }
    this.textArmyDescription[paramInt] = new String(localStringBuffer);
  }
  private void subString(StringBuffer paramStringBuffer, String paramString, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2)
      paramStringBuffer.append(paramString.charAt(paramInt1++));
  }

  protected void fillTextDescription() {
    try {
      String str = Main.cur().currentMissionFile.fileName();
      for (int i = str.length() - 1; i > 0; i--) {
        int j = str.charAt(i);
        if ((j == 92) || (j == 47)) break;
        if (j == 46) {
          str = str.substring(0, i);
          break;
        }
      }
      ResourceBundle localResourceBundle = ResourceBundle.getBundle(str, RTSConf.cur.locale);
      this.textDescription = localResourceBundle.getString("Description");
    } catch (Exception localException) {
      this.textDescription = null;
      this.textArmyDescription = null;
    }
  }

  protected String textDescription() {
    return this.textDescription;
  }

  protected Descript createDescript(GWindow paramGWindow) {
    return (Descript)paramGWindow.create(new Descript());
  }

  protected void doNext()
  {
  }

  protected void doDiff()
  {
  }

  protected void doBack()
  {
  }

  protected void doLoodout()
  {
  }

  protected void clientRender()
  {
  }

  protected void clientSetPosSize()
  {
  }

  protected void clientInit(GWindowRoot paramGWindowRoot)
  {
  }

  protected String infoMenuInfo()
  {
    return "????????";
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = infoMenuInfo();
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    createRenderWindow(this.dialogClient);

    this.dialogClient.create(this.wScrollDescription = new ScrollDescript());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDifficulty = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bLoodout = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNext = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));

    clientInit(paramGWindowRoot);

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUIBriefingGeneric(int paramInt) {
    super(paramInt);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIBriefingGeneric.this.bPrev) {
        GUIBriefingGeneric.this.doBack();
        return true;
      }if (paramGWindow == GUIBriefingGeneric.this.bNext) {
        GUIBriefingGeneric.this.doNext();
        return true;
      }if (paramGWindow == GUIBriefingGeneric.this.bDifficulty) {
        GUIBriefingGeneric.this.doDiff();
        return true;
      }if (paramGWindow == GUIBriefingGeneric.this.bLoodout) {
        GUIBriefingGeneric.this.doLoodout();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(924.0F), 2.5F);
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(686.0F), x1024(30.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(537.0F), y1024(686.0F), x1024(30.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(640.0F), 1.0F, x1024(46.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(567.0F), y1024(640.0F), 1.0F, x1024(46.0F));

      setCanvasColorWHITE();
      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      localGUILookAndFeel.drawBevel(this, x1024(32.0F), y1024(32.0F), x1024(528.0F), y1024(560.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);

      setCanvasFont(0);
      setCanvasColor(GColor.Gray);
      GUIBriefingGeneric.this.clientRender();
    }

    public void resized() {
      super.resized();
      if (GUIBriefingGeneric.this.renders != null) {
        GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
        GBevel localGBevel = localGUILookAndFeel.bevelComboDown;
        GUIBriefingGeneric.this.renders.setPosSize(x1024(32.0F) + localGBevel.L.dx, y1024(32.0F) + localGBevel.T.dy, x1024(528.0F) - localGBevel.L.dx - localGBevel.R.dx, y1024(560.0F) - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);

      GUIBriefingGeneric.this.bPrev.setPosC(x1024(85.0F), y1024(689.0F));

      GUIBriefingGeneric.this.bDifficulty.setPosC(x1024(298.0F), y1024(689.0F));
      GUIBriefingGeneric.this.bLoodout.setPosC(x1024(768.0F), y1024(689.0F));
      GUIBriefingGeneric.this.bNext.setPosC(x1024(512.0F), y1024(689.0F));

      GUIBriefingGeneric.this.wScrollDescription.setPosSize(x1024(592.0F), y1024(32.0F), x1024(400.0F), y1024(560.0F));
      GUIBriefingGeneric.this.clientSetPosSize();
    }
  }

  public class ScrollDescript extends GWindowScrollingDialogClient
  {
    public ScrollDescript()
    {
    }

    public void created()
    {
      this.fixed = (GUIBriefingGeneric.this.wDescript = GUIBriefingGeneric.this.createDescript(this));
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
      if (GUIBriefingGeneric.this.wDescript != null) {
        GUIBriefingGeneric.this.wDescript.computeSize();
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

  public class Descript extends GWindowDialogClient
  {
    public Descript()
    {
    }

    public void render()
    {
      String str = GUIBriefingGeneric.this.textDescription();
      if (str != null) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        setCanvasColorBLACK();
        this.root.C.clip.y += localGBevel.T.dy;
        this.root.C.clip.dy -= localGBevel.T.dy + localGBevel.B.dy;
        drawLines(localGBevel.L.dx + 2.0F, localGBevel.T.dy + 2.0F, str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F, this.root.C.font.height);
      }
    }

    public void computeSize() {
      String str = GUIBriefingGeneric.this.textDescription();
      if (str != null) {
        this.win.dx = this.parentWindow.win.dx;
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        int i = computeLines(str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
        this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        if (this.win.dy > this.parentWindow.win.dy) {
          this.win.dx = (this.parentWindow.win.dx - lookAndFeel().getVScrollBarW());
          i = computeLines(str, 0, str.length(), this.win.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
          this.win.dy = (this.root.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        }
      } else {
        this.win.dx = this.parentWindow.win.dx;
        this.win.dy = this.parentWindow.win.dy;
      }
    }
  }

  public class RenderMap2D extends Render
  {
    public void preRender()
    {
      if (GUIBriefingGeneric.this.main.land2D == null)
        return;
      Front.preRender(false);
    }
    public void render() {
      if (GUIBriefingGeneric.this.main.land2D == null)
        return;
      GUIBriefingGeneric.this.main.land2D.render();
      if (GUIBriefingGeneric.this.main.land2DText != null)
        GUIBriefingGeneric.this.main.land2DText.render();
      GUIBriefingGeneric.this.drawGrid2D();
      Front.render(false);
      int i = (int)Math.round(32.0D * GUIBriefingGeneric.this.renders.root.win.dx / 1024.0D);
      IconDraw.setScrSize(i, i);
      GUIBriefingGeneric.this.doRenderMap2D();
      SquareLabels.draw(GUIBriefingGeneric.this.cameraMap2D, Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.mapSizeX());
    }

    public RenderMap2D(Renders paramFloat, float arg3) {
      super(localObject);
      useClearDepth(false);
      useClearColor(false);
    }
  }
}
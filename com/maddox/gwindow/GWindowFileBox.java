// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFileBox.java

package com.maddox.gwindow;

import com.maddox.rts.HomePath;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.gwindow:
//            GWindowFramed, GWindowTree, GWindowLabel, GWindowEditControl, 
//            GWindowComboControl, GWindowButton, GTreeModelDir95, GRegion, 
//            GTreeModelDir, GWindowFileBoxExec, GTreePath, GFileFilter, 
//            GWindowLookAndFeel, GWindow, GWindowDialogClient, GWindowRoot, 
//            GWin95LookAndFeel, GBevel, GFont, GSize, 
//            GWindowDialogControl, GWindowHScrollBar

public class GWindowFileBox extends com.maddox.gwindow.GWindowFramed
{
    public class Label extends com.maddox.gwindow.GWindow
    {

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GWin95LookAndFeel gwin95lookandfeel = (com.maddox.gwindow.GWin95LookAndFeel)lookAndFeel();
            gwin95lookandfeel.drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gwin95lookandfeel.bevelDOWN, gwin95lookandfeel.elements, true);
            if(title != null)
            {
                setCanvasColorBLACK();
                setCanvasFont(0);
                com.maddox.gwindow.GFont gfont = root.textFonts[0];
                float f = (win.dy - gwin95lookandfeel.bevelDOWN.TL.dy - gwin95lookandfeel.bevelDOWN.BL.dy - gfont.height) / 2.0F;
                draw(gwin95lookandfeel.bevelDOWN.TL.dx + M(0.5F), gwin95lookandfeel.bevelDOWN.TL.dy + f, win.dx - M(0.5F) - gwin95lookandfeel.bevelDOWN.TL.dx - gwin95lookandfeel.bevelDOWN.TR.dx, gfont.height, 0, title);
            }
        }

        public java.lang.String title;

        public Label(com.maddox.gwindow.GWindow gwindow, java.lang.String s)
        {
            super(gwindow);
            title = s;
        }
    }

    public class Separate extends com.maddox.gwindow.GWindow
    {

        public void mouseMove(float f, float f1)
        {
            super.mouseMove(f, f1);
            if(isMouseCaptured())
            {
                float f2 = root.mouseStep.dx;
                float f3 = m(f2);
                dirDX += f3;
                checkSizes();
                computeWin();
            }
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i == 0)
                if(flag)
                {
                    if(!isMouseCaptured())
                        mouseCapture(true);
                } else
                if(isMouseCaptured())
                    mouseCapture(false);
        }

        public Separate(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            mouseCursor = 11;
        }
    }

    public class Area extends com.maddox.gwindow.GWindowDialogControl
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(gwindow == scroll && i == 2)
            {
                firstView = rows * (int)scroll.pos();
                return true;
            } else
            {
                return false;
            }
        }

        public void mouseDoubleClick(int i, float f, float f1)
        {
            if(i == 0)
            {
                int j = find(f, f1);
                if(j >= 0)
                {
                    selected = j;
                    java.io.File file = (java.io.File)files.get(selected);
                    wEdit.setValue(file.getName());
                    doResult(j);
                }
            }
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i == 0 && flag)
            {
                int j = find(f, f1);
                if(j >= 0)
                {
                    selected = j;
                    java.io.File file = (java.io.File)files.get(selected);
                    wEdit.setValue(file.getName());
                }
            }
        }

        public int find(float f, float f1)
        {
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            f -= look.bevelDOWN.L.dx + (float)(spaceDX / 2);
            f1 -= look.bevelDOWN.T.dy + (float)(spaceDY / 2);
            int i = (int)(f / (maxDX + (float)spaceDX));
            int j = (int)(f1 / (gfont.height + (float)spaceDY));
            int k = j + i * rows + firstView;
            if(k >= files.size())
                return -1;
            else
                return k;
        }

        public void updateFiles(boolean flag)
        {
            if(flag)
            {
                firstView = 0;
                selected = -1;
            }
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            maxDX = 0.0F;
            for(int i = 0; i < files.size(); i++)
            {
                java.io.File file = (java.io.File)files.get(i);
                com.maddox.gwindow.GSize gsize = gfont.size(file.getName());
                if(gsize.dx > maxDX)
                    maxDX = gsize.dx;
            }

            float f = gfont.height + (float)spaceDY;
            com.maddox.gwindow.GRegion gregion = getClientRegion();
            rows = (int)(gregion.dy / f);
            cols = files.size() / rows;
            if(files.size() % rows > 0)
                cols++;
            if((float)cols * maxDX + (float)((cols - 1) * spaceDX) + (float)(spaceDX / 2) > gregion.dx)
            {
                scroll.setSize(gregion.dx, look.getHScrollBarW());
                scroll.setPos(gregion.x, (gregion.y + gregion.dy) - look.getHScrollBarW());
                rows = (int)((gregion.dy - look.getHScrollBarW()) / f);
                cols = files.size() / rows;
                if(files.size() % rows > 0)
                    cols++;
                colsVisible = (int)(((gregion.dx - (float)(spaceDX / 2)) + (float)spaceDX) / (maxDX + (float)spaceDX));
                if(colsVisible == 0)
                    colsVisible = 1;
                firstView = (firstView / rows) * rows;
                if(cols - firstView / rows < colsVisible)
                    firstView = (cols - colsVisible) * rows;
                scroll.setRange(0.0F, cols, colsVisible, 1.0F, firstView / rows);
                scroll.showWindow();
            } else
            {
                colsVisible = cols;
                scroll.hideWindow();
                firstView = 0;
            }
        }

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GWin95LookAndFeel gwin95lookandfeel = (com.maddox.gwindow.GWin95LookAndFeel)lookAndFeel();
            draw(gwin95lookandfeel.bevelDOWN.L.dx, gwin95lookandfeel.bevelDOWN.T.dy, win.dx - gwin95lookandfeel.bevelDOWN.R.dx - gwin95lookandfeel.bevelDOWN.L.dx, win.dy - gwin95lookandfeel.bevelDOWN.B.dy - gwin95lookandfeel.bevelDOWN.T.dy, gwin95lookandfeel.elements, 5F, 17F, 1.0F, 1.0F);
            setCanvasColorBLACK();
            com.maddox.gwindow.GFont gfont = root.textFonts[0];
            setCanvasFont(0);
            float f = gwin95lookandfeel.bevelDOWN.L.dx + (float)(spaceDX / 2);
            float f1 = gwin95lookandfeel.bevelDOWN.T.dy + (float)(spaceDY / 2);
            float f2 = gfont.height + (float)spaceDY;
            for(int i = 0; i < colsVisible + 1; i++)
            {
                for(int j = 0; j < rows; j++)
                {
                    int k = j + i * rows + firstView;
                    if(k >= files.size())
                        break;
                    float f3 = f + (float)i * (maxDX + (float)spaceDX);
                    float f4 = f1 + (float)j * f2;
                    java.io.File file = (java.io.File)files.get(k);
                    if(k == selected)
                    {
                        draw(f3 - (float)(spaceDX / 2), f4 - (float)(spaceDY / 2), maxDX + (float)spaceDX, f2, gwin95lookandfeel.elements, 5F, 17F, 1.0F, 1.0F);
                        setCanvasColorWHITE();
                        draw(f3, f4, file.getName());
                        setCanvasColorBLACK();
                    } else
                    {
                        draw(f3, f4, file.getName());
                    }
                }

            }

            setCanvasColorWHITE();
            gwin95lookandfeel.drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gwin95lookandfeel.bevelDOWN, gwin95lookandfeel.elements, false);
        }

        public void resized()
        {
            super.resized();
            updateFiles(false);
        }

        public void created()
        {
            super.created();
            look = (com.maddox.gwindow.GWin95LookAndFeel)lookAndFeel();
            bEnableDoubleClick[0] = true;
        }

        public void afterCreated()
        {
            super.afterCreated();
            scroll = new GWindowHScrollBar(this);
            scroll.hideWindow();
        }

        public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
        {
            gregion.x = look.bevelDOWN.L.dx + f;
            gregion.y = look.bevelDOWN.T.dy + f;
            gregion.dx = win.dx - gregion.x - look.bevelDOWN.R.dx - f;
            gregion.dy = win.dy - gregion.y - look.bevelDOWN.B.dy - f;
            return gregion;
        }

        public com.maddox.gwindow.GWindowHScrollBar scroll;
        public com.maddox.gwindow.GWin95LookAndFeel look;
        public int spaceDY;
        public int spaceDX;
        float maxDX;
        int cols;
        int rows;
        int firstView;
        int selected;
        int colsVisible;

        public Area(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            spaceDY = 4;
            spaceDX = 6;
            selected = -1;
        }
    }

    public class Client extends com.maddox.gwindow.GWindowDialogClient
    {

        public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
        {
            gsize.dx = M(minFullDX);
            gsize.dy = M(minFullDY);
            return gsize;
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i == 2)
            {
                if(gwindow == wDir)
                {
                    scanFiles();
                    return true;
                }
                if(gwindow == wCFilter)
                {
                    scanFiles();
                    return true;
                }
                if(gwindow == wOk)
                {
                    if(wEdit != null)
                        doResult(wEdit.getValue());
                    else
                        doResult(null);
                    return true;
                }
                if(gwindow == wCancel)
                {
                    doResult(null);
                    return true;
                }
            }
            if(gwindow == wEdit && i == 10 && j == 10)
            {
                if(wEdit != null)
                    doResult(wEdit.getValue());
                else
                    doResult(null);
                return true;
            } else
            {
                return false;
            }
        }

        public Client()
        {
        }
    }


    public void result(java.lang.String s)
    {
        java.lang.System.out.println("FileBox result: " + s);
    }

    public void setSelectFile(java.lang.String s)
    {
        setSelectFile(s, bIncludeHomeNameToResult, false);
    }

    public void setSelectFile(java.lang.String s, boolean flag)
    {
        setSelectFile(s, flag, false);
    }

    public void setSelectFile(java.lang.String s, boolean flag, boolean flag1)
    {
        int i = s.lastIndexOf('/');
        if(i < 0)
            i = s.lastIndexOf('\\');
        java.lang.String s1 = "";
        if(i >= 0)
        {
            if(i < s.length())
                s1 = s.substring(i + 1);
            s = s.substring(0, i);
        } else
        {
            s1 = s;
            s = null;
            wEdit.setValue(s1, false);
            if(flag1)
                doResult(s1);
            return;
        }
        com.maddox.gwindow.GTreePath gtreepath = modelDir.strToPath(s, flag);
        if(gtreepath == null)
            return;
        wDir.setSelect(gtreepath);
        wEdit.setValue(s1, false);
        if(flag1)
            doResult(s1);
    }

    public java.lang.String makeResultName(java.lang.String s)
    {
        if(wDir.selectPath == null)
            return s;
        java.lang.String s1 = modelDir.pathToStr(wDir.selectPath, bIncludeHomeNameToResult);
        if(s1 != null && s1.length() > 0)
            return s1 + "/" + s;
        else
            return s;
    }

    public void endExec()
    {
        if(exec == null)
        {
            close(false);
            return;
        }
        if(exec.isCloseBox())
        {
            iResult = -2;
            close(false);
            if(exec.isReturnResult())
                result(resultFileName);
        } else
        {
            iResult = -2;
            if(exec.isChangedBox())
                scanFiles();
            if(bModal)
                showModal();
        }
    }

    public void doResult(java.lang.String s)
    {
        if(s != null)
            resultFileName = makeResultName(s);
        else
            resultFileName = null;
        if(exec != null)
        {
            exec.exec(this, resultFileName);
            return;
        } else
        {
            iResult = -2;
            close(false);
            result(resultFileName);
            return;
        }
    }

    public void doResult(int i)
    {
        if(i >= 0)
        {
            java.io.File file = (java.io.File)files.get(i);
            resultFileName = makeResultName(file.getName());
        } else
        {
            resultFileName = null;
        }
        if(exec != null)
        {
            exec.exec(this, resultFileName);
            return;
        } else
        {
            iResult = -2;
            close(false);
            result(resultFileName);
            return;
        }
    }

    public void close(boolean flag)
    {
        super.close(flag);
        if(iResult != -2)
        {
            iResult = -2;
            result(null);
        }
    }

    protected void scanFiles()
    {
        files.clear();
        if(wDir == null || wDir.selectPath == null)
        {
            wArea.updateFiles(true);
            return;
        }
        java.io.File file = (java.io.File)wDir.selectPath.getLastPathComponent();
        java.io.File afile[] = file.listFiles();
        if(afile == null || afile.length == 0)
        {
            wArea.updateFiles(true);
            return;
        }
        for(int i = 0; i < afile.length; i++)
            if(filter != null && wCFilter != null)
            {
                com.maddox.gwindow.GFileFilter gfilefilter = filter[wCFilter.getSelected()];
                if(afile[i].isFile() && !afile[i].isHidden() && gfilefilter.accept(afile[i]))
                    _scanMap.put(afile[i].getName(), afile[i]);
            } else
            if(afile[i].isFile() && !afile[i].isHidden())
                _scanMap.put(afile[i].getName(), afile[i]);

        for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); files.add(_scanMap.get(iterator.next())));
        _scanMap.clear();
        wArea.updateFiles(true);
    }

    private boolean checkSizes()
    {
        boolean flag = false;
        if(dirDX < dirDXMin)
        {
            dirDX = dirDXMin;
            flag = true;
        }
        float f = m(win.dx) - spaceLeft - dirDX - separateDX - spaceRight;
        if(f < areaDXMin)
        {
            dirDX -= areaDXMin - f;
            flag = true;
        }
        return flag;
    }

    public float M(float f)
    {
        return lookAndFeel().metric(f);
    }

    public float m(float f)
    {
        return f / lookAndFeel().metric();
    }

    public void computeFolders(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(dirDX);
        gregion.dy = M(titleDY);
        gregion.x = M(spaceLeft);
        gregion.y = M(titleSpaceUP);
    }

    public void computeContents(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = gregion1.dx - M(spaceLeft) - M(spaceRight) - M(dirDX) - M(separateDX);
        gregion.dy = M(titleDY);
        gregion.x = M(spaceLeft) + M(dirDX) + M(separateDX);
        gregion.y = M(titleSpaceUP);
    }

    public void computeDir(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(dirDX);
        gregion.dy = gregion1.dy - M(titleSpaceUP) - M(titleDY) - M(areaSpaceDOWN);
        gregion.x = M(spaceLeft);
        gregion.y = M(titleSpaceUP) + M(titleDY);
    }

    public void computeSeparate(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(separateDX);
        gregion.dy = gregion1.dy - M(titleSpaceUP) - M(areaSpaceDOWN);
        gregion.x = M(spaceLeft) + M(dirDX);
        gregion.y = M(titleSpaceUP);
    }

    public void computeArea(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = gregion1.dx - M(spaceLeft) - M(spaceRight) - M(dirDX) - M(separateDX);
        gregion.dy = gregion1.dy - M(titleSpaceUP) - M(titleDY) - M(areaSpaceDOWN);
        gregion.x = M(spaceLeft) + M(dirDX) + M(separateDX);
        gregion.y = M(titleSpaceUP) + M(titleDY);
    }

    public void computeFile(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(textDX);
        gregion.dy = lookAndFeel().getComboH();
        gregion.x = M(spaceLeft);
        gregion.y = gregion1.dy - M(fileSpaceDOWN);
    }

    public void computeFilter(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(textDX);
        gregion.dy = lookAndFeel().getComboH();
        gregion.x = M(spaceLeft);
        gregion.y = gregion1.dy - M(typeSpaceDOWN);
    }

    public void computeEdit(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = gregion1.dx - M(spaceLeft) - M(textDX) - 2.0F * M(spaceDX) - M(buttonDX) - M(spaceRight);
        gregion.dy = lookAndFeel().getComboH();
        gregion.x = M(spaceLeft) + M(textDX) + M(spaceDX);
        gregion.y = (gregion1.dy - M(fileSpaceDOWN)) + M(0.1F);
    }

    public void computeCFilter(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = gregion1.dx - M(spaceLeft) - M(textDX) - 2.0F * M(spaceDX) - M(buttonDX) - M(spaceRight);
        gregion.dy = lookAndFeel().getComboH();
        gregion.x = M(spaceLeft) + M(textDX) + M(spaceDX);
        gregion.y = (gregion1.dy - M(typeSpaceDOWN)) + M(0.1F);
    }

    public void computeOk(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(buttonDX);
        gregion.dy = M(buttonDY);
        gregion.x = gregion1.dx - gregion.dx - M(spaceRight);
        gregion.y = gregion1.dy - M(fileSpaceDOWN);
    }

    public void computeCancel(com.maddox.gwindow.GRegion gregion, com.maddox.gwindow.GRegion gregion1)
    {
        gregion.dx = M(buttonDX);
        gregion.dy = M(buttonDY);
        gregion.x = gregion1.dx - gregion.dx - M(spaceRight);
        gregion.y = gregion1.dy - M(typeSpaceDOWN);
    }

    public void _setWin(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GRegion gregion)
    {
        gwindow.setPos(gregion.x, gregion.y);
        gwindow.setSize(gregion.dx, gregion.dy);
    }

    public void computeWin()
    {
        com.maddox.gwindow.GRegion gregion = getClientRegion(_client, 0.0F);
        if(wFolders != null)
        {
            computeFolders(_reg, gregion);
            _setWin(wFolders, _reg);
        }
        if(wContents != null)
        {
            computeContents(_reg, gregion);
            _setWin(wContents, _reg);
        }
        if(wDir != null)
        {
            computeDir(_reg, gregion);
            _setWin(wDir, _reg);
        }
        if(wSeparate != null)
        {
            computeSeparate(_reg, gregion);
            _setWin(wSeparate, _reg);
        }
        if(wArea != null)
        {
            computeArea(_reg, gregion);
            _setWin(wArea, _reg);
        }
        if(wFile != null)
        {
            computeFile(_reg, gregion);
            _setWin(wFile, _reg);
        }
        if(wFilter != null)
        {
            computeFilter(_reg, gregion);
            _setWin(wFilter, _reg);
        }
        if(wEdit != null)
        {
            computeEdit(_reg, gregion);
            _setWin(wEdit, _reg);
        }
        if(wCFilter != null)
        {
            computeCFilter(_reg, gregion);
            _setWin(wCFilter, _reg);
        }
        if(wOk != null)
        {
            computeOk(_reg, gregion);
            _setWin(wOk, _reg);
        }
        if(wCancel != null)
        {
            computeCancel(_reg, gregion);
            _setWin(wCancel, _reg);
        }
    }

    public void resized()
    {
        checkSizes();
        computeWin();
        super.resized();
    }

    public void createFolders(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeFolders(_reg, getClientRegion());
        wFolders = new Label(gwindowdialogclient, lAF().i18n("Folders"));
        _setWin(wFolders, _reg);
    }

    public void createContents(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeContents(_reg, getClientRegion());
        wContents = new Label(gwindowdialogclient, lAF().i18n("Contents"));
        _setWin(wContents, _reg);
    }

    public void createDir(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeDir(_reg, getClientRegion());
        wDir = new GWindowTree(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy));
        wDir.setModel(modelDir);
        wDir.metricWin = null;
        wDir.bDrawIcons = true;
        wDir.bNotify = true;
        wDir.setRootVisible(true);
        wDir.setSelect(modelDir.root);
    }

    public void createSeparate(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeSeparate(_reg, getClientRegion());
        wSeparate = new Separate(gwindowdialogclient);
        _setWin(wSeparate, _reg);
    }

    public void createArea(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeArea(_reg, getClientRegion());
        wArea = new Area(gwindowdialogclient);
        _setWin(wArea, _reg);
    }

    public void createFile(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeFile(_reg, getClientRegion());
        wFile = (com.maddox.gwindow.GWindowLabel)gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("File_name_"), null));
        wFile.metricWin = null;
    }

    public void createFilter(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeFilter(_reg, getClientRegion());
        wFilter = (com.maddox.gwindow.GWindowLabel)gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("Files_of_type_"), null));
        wFilter.metricWin = null;
    }

    public void createEdit(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeEdit(_reg, getClientRegion());
        wEdit = (com.maddox.gwindow.GWindowEditControl)gwindowdialogclient.addControl(new GWindowEditControl(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), null));
        wEdit.metricWin = null;
        wEdit.bSelectOnFocus = false;
    }

    public void createCFilter(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeCFilter(_reg, getClientRegion());
        wCFilter = (com.maddox.gwindow.GWindowComboControl)gwindowdialogclient.addControl(new GWindowComboControl(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx)));
        wCFilter.metricWin = null;
        wCFilter.setEditable(false);
        if(filter != null)
        {
            for(int i = 0; i < filter.length; i++)
                wCFilter.add(filter[i].getDescription());

            wCFilter.setSelected(0, true, false);
        }
    }

    public void createOk(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeOk(_reg, getClientRegion());
        wOk = (com.maddox.gwindow.GWindowButton)gwindowdialogclient.addDefault(new GWindowButton(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("&Open"), null));
        wOk.metricWin = null;
    }

    public void createCancel(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        computeCancel(_reg, getClientRegion());
        wCancel = (com.maddox.gwindow.GWindowButton)gwindowdialogclient.addControl(new GWindowButton(gwindowdialogclient, m(_reg.x), m(_reg.y), m(_reg.dx), m(_reg.dy), lAF().i18n("&Cancel"), null));
        wCancel.metricWin = null;
    }

    public void created()
    {
        super.created();
        setMetricSize(defFullDX, defFullDY);
    }

    public void afterCreated()
    {
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        clientWindow = create(gregion.x, gregion.y, gregion.dx, gregion.dy, false, new Client());
        com.maddox.gwindow.Client client = (com.maddox.gwindow.Client)clientWindow;
        createFolders(client);
        createContents(client);
        createCFilter(client);
        createArea(client);
        createSeparate(client);
        createDir(client);
        createFile(client);
        createFilter(client);
        createEdit(client);
        createOk(client);
        createCancel(client);
        super.afterCreated();
        if(bModal)
            showModal();
        if(root == parentWindow)
            clampWin(root.getClientRegion());
    }

    public GWindowFileBox(com.maddox.gwindow.GWindow gwindow, boolean flag, java.lang.String s, java.lang.String s1, com.maddox.gwindow.GFileFilter agfilefilter[])
    {
        bIncludeHomeNameToResult = false;
        iResult = -1;
        files = new ArrayList();
        _scanMap = new TreeMap();
        minFullDX = 24F;
        minFullDY = 16F;
        defFullDX = 30F;
        defFullDY = 24F;
        spaceLeft = 0.2F;
        spaceRight = 0.2F;
        titleSpaceUP = 0.2F;
        titleDY = 1.6F;
        dirDX = 8F;
        separateDX = 0.2F;
        dirDXMin = dirDX;
        areaDXMin = 4F;
        areaSpaceDOWN = 4.6F;
        fileSpaceDOWN = 4F;
        typeSpaceDOWN = 2.0F;
        buttonDX = 6F;
        buttonDY = 1.8F;
        spaceDX = 1.0F;
        textDX = 8F;
        title = s;
        filter = agfilefilter;
        bModal = flag;
        modelDir = new GTreeModelDir95(com.maddox.rts.HomePath.get(0) + "/" + s1);
        iResult = -1;
        doNew(gwindow, 0.0F, 0.0F, 100F, 100F, false);
        dirDXMin = 4F;
    }

    public boolean bModal;
    public boolean bIncludeHomeNameToResult;
    public com.maddox.gwindow.GFileFilter filter[];
    public com.maddox.gwindow.GWindowFileBoxExec exec;
    public int iResult;
    public java.lang.String resultFileName;
    public com.maddox.gwindow.GTreeModelDir modelDir;
    public java.util.ArrayList files;
    protected java.util.TreeMap _scanMap;
    public float minFullDX;
    public float minFullDY;
    public float defFullDX;
    public float defFullDY;
    public float spaceLeft;
    public float spaceRight;
    public float titleSpaceUP;
    public float titleDY;
    public float dirDX;
    public float separateDX;
    public float dirDXMin;
    public float areaDXMin;
    public float areaSpaceDOWN;
    public float fileSpaceDOWN;
    public float typeSpaceDOWN;
    public float buttonDX;
    public float buttonDY;
    public float spaceDX;
    public float textDX;
    public com.maddox.gwindow.Label wFolders;
    public com.maddox.gwindow.Label wContents;
    public com.maddox.gwindow.GWindowTree wDir;
    public com.maddox.gwindow.Separate wSeparate;
    public com.maddox.gwindow.Area wArea;
    public com.maddox.gwindow.GWindowLabel wFile;
    public com.maddox.gwindow.GWindowLabel wFilter;
    public com.maddox.gwindow.GWindowEditControl wEdit;
    public com.maddox.gwindow.GWindowComboControl wCFilter;
    public com.maddox.gwindow.GWindowButton wOk;
    public com.maddox.gwindow.GWindowButton wCancel;
    private static com.maddox.gwindow.GRegion _reg = new GRegion();
    private static com.maddox.gwindow.GRegion _client = new GRegion();


}

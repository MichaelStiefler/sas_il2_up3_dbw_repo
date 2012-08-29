// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Land2DText.java

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

// Referenced classes of package com.maddox.il2.engine:
//            CameraOrtho2D, Render, TTFont

public class Land2DText
    implements com.maddox.rts.Destroy
{
    static class Item
    {

        void setLevels(int i)
        {
            commonFilds |= i & 7;
        }

        boolean isShowLevel(int i)
        {
            return (commonFilds & 1 << i) != 0;
        }

        void setFont(int i)
        {
            commonFilds |= (i & 3) << 3;
        }

        int font()
        {
            return commonFilds >> 3 & 3;
        }

        void setAlign(int i)
        {
            commonFilds |= (i & 3) << 5;
        }

        int align()
        {
            return commonFilds >> 5 & 3;
        }

        void setColor(int i)
        {
            commonFilds |= (i & 0x1f) << 7;
        }

        int color()
        {
            return commonFilds >> 7 & 0x1f;
        }

        void setW(int i)
        {
            commonFilds |= (i & 0x3ff) << 12;
        }

        int w()
        {
            return commonFilds >> 12 & 0x3ff;
        }

        void setH(int i)
        {
            commonFilds |= (i & 0x3ff) << 22;
        }

        int h()
        {
            return commonFilds >> 22 & 0x3ff;
        }

        public void computeSizes()
        {
            if(text == null || text.length() == 0)
                setW(0);
            else
                setW((int)com.maddox.il2.engine.TTFont.font[font()].width(text));
            setH(com.maddox.il2.engine.TTFont.font[font()].height());
        }

        public java.lang.String text;
        public int commonFilds;
        public float x;
        public float y;

        public Item(float f, float f1, java.lang.String s, int i, int j, int k, int l)
        {
            x = f;
            y = f1;
            text = s;
            setLevels(i);
            setFont(j);
            setAlign(k);
            setColor(l);
        }
    }


    public static int color(int i, int j, int k)
    {
        return i & 0xff | (j & 0xff) << 8 | (k & 0xff) << 16 | 0xff000000;
    }

    public boolean isShow()
    {
        return bShow;
    }

    public void show(boolean flag)
    {
        bShow = flag;
    }

    public void render()
    {
        if(!bShow || isDestroyed() || !(com.maddox.il2.engine.Render.currentCamera() instanceof com.maddox.il2.engine.CameraOrtho2D))
            return;
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        byte byte0 = 1;
        if(cameraortho2d.worldScale < 0.01D)
            byte0 = 0;
        else
        if(cameraortho2d.worldScale > 0.050000000000000003D)
            byte0 = 2;
        double d = cameraortho2d.worldXOffset;
        double d1 = cameraortho2d.worldYOffset;
        double d2 = d + (double)(cameraortho2d.right - cameraortho2d.left) / cameraortho2d.worldScale;
        double d3 = d1 + (double)(cameraortho2d.top - cameraortho2d.bottom) / cameraortho2d.worldScale;
        int i = (int)d / 10000;
        int j = ((int)d2 + 5000) / 10000;
        int k = (int)d1 / 10000;
        int l = ((int)d3 + 5000) / 10000;
        for(int i1 = k; i1 <= l; i1++)
        {
            for(int j1 = i; j1 <= j; j1++)
            {
                java.util.List list = lstXY.get(i1, j1);
                if(list != null)
                {
                    int k1 = list.size();
                    for(int l1 = 0; l1 < k1; l1++)
                    {
                        com.maddox.il2.engine.Item item = (com.maddox.il2.engine.Item)list.get(l1);
                        if(item.isShowLevel(byte0))
                        {
                            float f = (float)(((double)item.x - d) * cameraortho2d.worldScale);
                            float f1 = (float)(((double)item.y - d1) * cameraortho2d.worldScale);
                            switch(item.align())
                            {
                            case 1: // '\001'
                                f -= item.w() / 2;
                                break;

                            case 2: // '\002'
                                f -= item.w();
                                break;
                            }
                            if(f <= cameraortho2d.right && f1 <= cameraortho2d.top && f + (float)item.w() >= cameraortho2d.left && f1 + (float)item.h() >= cameraortho2d.bottom)
                                com.maddox.il2.engine.TTFont.font[item.font()].output(color[item.color()], f, f1, 0.0F, item.text);
                        }
                    }

                }
            }

        }

    }

    public void load(java.lang.String s)
    {
        java.util.ResourceBundle resourcebundle;
        if(lstXY != null)
            lstXY.clear();
        else
            lstXY = new HashMapXY16List(7);
        resourcebundle = null;
        try
        {
            int i = s.lastIndexOf("/");
            if(i > 0)
            {
                int j = s.lastIndexOf("/", i - 1);
                if(j > 0)
                {
                    java.lang.String s2 = s.substring(j + 1, i);
                    resourcebundle = java.util.ResourceBundle.getBundle("i18n/" + s2, com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                }
            }
        }
        catch(java.lang.Exception exception) { }
        java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
_L2:
        float f;
        float f1;
        int k;
        int l;
        int i1;
        int j1;
        java.lang.String s3;
        int k1;
        int l1;
        java.lang.String s1 = bufferedreader.readLine();
        if(s1 == null)
            break MISSING_BLOCK_LABEL_412;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s1);
        f = numbertokenizer.next(0);
        f1 = numbertokenizer.next(0);
        k = numbertokenizer.next(7, 1, 7);
        l = numbertokenizer.next(1, 0, 2);
        i1 = numbertokenizer.next(1, 0, 2);
        j1 = numbertokenizer.next(0, 0, 19);
        s3 = numbertokenizer.nextToken("");
        k1 = 0;
        for(l1 = s3.length() - 1; k1 < l1; k1++)
            if(s3.charAt(k1) > ' ')
                break;

        for(; k1 < l1; l1--)
            if(s3.charAt(l1) > ' ')
                break;

        if(k1 == l1)
            return;
        if(k1 != 0 || l1 != s3.length() - 1)
            s3 = s3.substring(k1, l1 + 1);
        if(resourcebundle != null)
            try
            {
                s3 = resourcebundle.getString(s3);
            }
            catch(java.lang.Exception exception2) { }
        com.maddox.il2.engine.Item item = new Item(f, f1, s3, k, i1, l, j1);
        item.computeSizes();
        int i2 = (int)f / 10000;
        int j2 = (int)f1 / 10000;
        lstXY.put(j2, i2, item);
        if(true) goto _L2; else goto _L1
_L1:
        bufferedreader.close();
        lstXY.allValuesTrimToSize();
        break MISSING_BLOCK_LABEL_462;
        java.lang.Exception exception1;
        exception1;
        java.lang.System.out.println("Land2DText load failed: " + exception1.getMessage());
        exception1.printStackTrace();
    }

    public void contextResized()
    {
        if(isDestroyed())
            return;
        java.util.ArrayList arraylist = new ArrayList();
        lstXY.allValues(arraylist);
        for(int i = 0; i < arraylist.size(); i++)
        {
            java.util.ArrayList arraylist1 = (java.util.ArrayList)arraylist.get(i);
            for(int j = 0; j < arraylist1.size(); j++)
            {
                com.maddox.il2.engine.Item item = (com.maddox.il2.engine.Item)arraylist1.get(j);
                item.computeSizes();
            }

        }

        arraylist.clear();
    }

    public void clear()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            lstXY.clear();
            return;
        }
    }

    public void destroy()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            lstXY.clear();
            lstXY = null;
            return;
        }
    }

    public boolean isDestroyed()
    {
        return lstXY == null;
    }

    public Land2DText()
    {
        bShow = true;
    }

    public static final int STEP = 10000;
    public static final double LEVEL0_SCALE = 0.01D;
    public static final double LEVEL1_SCALE = 0.050000000000000003D;
    public static int color[];
    private boolean bShow;
    private com.maddox.util.HashMapXY16List lstXY;

    static 
    {
        color = new int[20];
        color[0] = com.maddox.il2.engine.Land2DText.color(0, 0, 0);
        color[1] = com.maddox.il2.engine.Land2DText.color(128, 0, 0);
        color[2] = com.maddox.il2.engine.Land2DText.color(0, 128, 0);
        color[3] = com.maddox.il2.engine.Land2DText.color(128, 128, 0);
        color[4] = com.maddox.il2.engine.Land2DText.color(0, 0, 128);
        color[5] = com.maddox.il2.engine.Land2DText.color(128, 0, 128);
        color[6] = com.maddox.il2.engine.Land2DText.color(0, 128, 128);
        color[7] = com.maddox.il2.engine.Land2DText.color(192, 192, 192);
        color[8] = com.maddox.il2.engine.Land2DText.color(192, 220, 192);
        color[9] = com.maddox.il2.engine.Land2DText.color(166, 202, 240);
        color[10] = com.maddox.il2.engine.Land2DText.color(255, 251, 240);
        color[11] = com.maddox.il2.engine.Land2DText.color(160, 160, 164);
        color[12] = com.maddox.il2.engine.Land2DText.color(128, 128, 128);
        color[13] = com.maddox.il2.engine.Land2DText.color(255, 0, 0);
        color[14] = com.maddox.il2.engine.Land2DText.color(0, 255, 0);
        color[15] = com.maddox.il2.engine.Land2DText.color(255, 255, 0);
        color[16] = com.maddox.il2.engine.Land2DText.color(0, 0, 255);
        color[17] = com.maddox.il2.engine.Land2DText.color(255, 0, 255);
        color[18] = com.maddox.il2.engine.Land2DText.color(0, 255, 255);
        color[19] = com.maddox.il2.engine.Land2DText.color(255, 255, 255);
    }
}

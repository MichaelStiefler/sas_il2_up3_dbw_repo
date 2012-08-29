// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowTabDialogClient.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogClient, GCaption, GWindow, GRegion, 
//            GSize, GWindowLookAndFeel, GBevel, GWindowButtonTexture, 
//            GWindowDialogControl

public class GWindowTabDialogClient extends com.maddox.gwindow.GWindowDialogClient
{
    public class RButton extends com.maddox.gwindow.GWindowButtonTexture
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag || !bEnable)
            {
                return;
            } else
            {
                scroll(1);
                return;
            }
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            bAlwaysOnTop = true;
            lookAndFeel().setupScrollButtonRIGHT(this);
        }

        public RButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class LButton extends com.maddox.gwindow.GWindowButtonTexture
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag || !bEnable)
            {
                return;
            } else
            {
                scroll(-1);
                return;
            }
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            bAlwaysOnTop = true;
            lookAndFeel().setupScrollButtonLEFT(this);
        }

        public LButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class Tab extends com.maddox.gwindow.GWindowDialogControl
    {

        public boolean notify(int i, int j)
        {
            if(i == 2)
            {
                int k = tabs.indexOf(this);
                if(k >= 0)
                    setCurrent(k);
            }
            return true;
        }

        public boolean isCurrent()
        {
            return tabs.indexOf(this) == current;
        }

        public void render()
        {
            lookAndFeel().render(this);
        }

        public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
        {
            return lookAndFeel().getMinSize(this, gsize);
        }

        public void created()
        {
            super.created();
        }

        public com.maddox.gwindow.GWindow client;

        public Tab()
        {
        }
    }


    public int sizeTabs()
    {
        return tabs.size();
    }

    public void addTab(java.lang.String s, com.maddox.gwindow.GWindow gwindow)
    {
        com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)create(new Tab());
        tab.cap = new GCaption(s);
        tab.client = gwindow;
        gwindow.parentWindow = this;
        gwindow.hideWindow();
        tabs.add(tab);
        resized();
    }

    public void addTab(int i, java.lang.String s, com.maddox.gwindow.GWindow gwindow)
    {
        com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)create(new Tab());
        tab.cap = new GCaption(s);
        tab.client = gwindow;
        gwindow.parentWindow = this;
        gwindow.hideWindow();
        tabs.add(i, tab);
        resized();
    }

    public com.maddox.gwindow.Tab createTab(java.lang.String s, com.maddox.gwindow.GWindow gwindow)
    {
        com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)create(new Tab());
        tab.cap = new GCaption(s);
        tab.client = gwindow;
        tab.hideWindow();
        gwindow.parentWindow = this;
        gwindow.hideWindow();
        return tab;
    }

    public void addTab(int i, com.maddox.gwindow.Tab tab)
    {
        tab.showWindow();
        tabs.add(i, tab);
        resized();
    }

    public void removeTab(int i)
    {
        setCurrent(0);
        com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(i);
        tab.hideWindow();
        tabs.remove(i);
        prev = -1;
        firstView = 0;
        resized();
    }

    public void clearTabs()
    {
        for(int i = tabs.size(); i-- > 0;)
            removeTab(0);

    }

    public com.maddox.gwindow.Tab getTab(int i)
    {
        return (com.maddox.gwindow.Tab)tabs.get(i);
    }

    public com.maddox.gwindow.GWindow getTabClient(int i)
    {
        return ((com.maddox.gwindow.Tab)tabs.get(i)).client;
    }

    public int getPrev()
    {
        return prev;
    }

    public int getCurrent()
    {
        return current;
    }

    public void setCurrent(int i)
    {
        setCurrent(i, true);
    }

    public void setCurrent(int i, boolean flag)
    {
        if(current == i)
            return;
        if(current >= 0)
        {
            com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(current);
            tab.client.hideWindow();
        }
        prev = current;
        current = i;
        if(current >= 0)
        {
            com.maddox.gwindow.Tab tab1 = (com.maddox.gwindow.Tab)tabs.get(current);
            tab1.client.showWindow();
        }
        resized();
        if(flag)
            notify(2, prev);
    }

    public void scroll(int i)
    {
        if(i < 0)
        {
            firstView += i;
            if(firstView < 0)
                firstView = 0;
        } else
        {
            int j = firstView + i;
            if(j >= sizeTabs())
                return;
            float f = 0.0F;
            for(int k = firstView; k < tabs.size(); k++)
            {
                com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(k);
                f += tab.win.dx;
            }

            if(f <= win.dx)
                return;
            firstView = j;
        }
        resized();
    }

    public void resized()
    {
        float f = 0.0F;
        for(int i = 0; i < firstView; i++)
        {
            com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(i);
            com.maddox.gwindow.GSize gsize = tab.getMinSize();
            f += gsize.dx;
        }

        f = -f;
        float f1 = 0.0F;
        for(int j = 0; j < tabs.size(); j++)
        {
            com.maddox.gwindow.Tab tab1 = (com.maddox.gwindow.Tab)tabs.get(j);
            com.maddox.gwindow.GSize gsize1 = tab1.getMinSize();
            if(j == current)
                tab1.setSize(gsize1.dx, gsize1.dy + lookAndFeel().bevelTabDialogClient.T.dy);
            else
                tab1.setSize(gsize1.dx, gsize1.dy);
            tab1.setPos(f, 0.0F);
            tab1.client.setPos(lookAndFeel().bevelTabDialogClient.L.dx, tab1.win.dy + lookAndFeel().bevelTabDialogClient.B.dy);
            tab1.client.setSize(win.dx - lookAndFeel().bevelTabDialogClient.L.dx - lookAndFeel().bevelTabDialogClient.R.dx, win.dy - lookAndFeel().bevelTabDialogClient.B.dy - lookAndFeel().bevelTabDialogClient.T.dy - tab1.win.dy);
            if(j == current)
                tab1.client.showWindow();
            else
                tab1.client.hideWindow();
            f += tab1.win.dx;
            f1 += tab1.win.dx;
        }

        if(f1 > win.dx || firstView > 0)
        {
            lButton.setSize(lookAndFeel().getHScrollBarW(), lookAndFeel().getHScrollBarH());
            rButton.setSize(lookAndFeel().getHScrollBarW(), lookAndFeel().getHScrollBarH());
            lButton.setPos(win.dx - lButton.win.dx - rButton.win.dx, 0.0F);
            rButton.setPos(win.dx - rButton.win.dx, 0.0F);
            lButton.showWindow();
            rButton.showWindow();
        } else
        {
            lButton.hideWindow();
            rButton.hideWindow();
        }
    }

    public void render()
    {
        super.render();
        lookAndFeel().render(this);
    }

    public void resolutionChanged()
    {
        for(int i = 0; i < tabs.size(); i++)
        {
            com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(i);
            tab.client.resolutionChanged();
        }

        resized();
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        if(tabs == null)
            return super.getMinSize(gsize);
        float f = 0.0F;
        float f1 = 0.0F;
        for(int i = 0; i < tabs.size(); i++)
        {
            com.maddox.gwindow.Tab tab1 = (com.maddox.gwindow.Tab)tabs.get(i);
            tab1.client.getMinSize(gsize);
            if(f < gsize.dx)
                f = gsize.dx;
            if(f1 < gsize.dy)
                f1 = gsize.dy;
        }

        f += lookAndFeel().bevelTabDialogClient.L.dx + lookAndFeel().bevelTabDialogClient.R.dx;
        f1 += lookAndFeel().bevelTabDialogClient.T.dy + lookAndFeel().bevelTabDialogClient.B.dx;
        com.maddox.gwindow.Tab tab = (com.maddox.gwindow.Tab)tabs.get(0);
        tab.getMinSize(gsize);
        gsize.dx += f;
        gsize.dy += f1;
        return gsize;
    }

    public void afterCreated()
    {
        super.afterCreated();
        lButton = new LButton(this);
        rButton = new RButton(this);
        resized();
    }

    public GWindowTabDialogClient()
    {
        tabs = new ArrayList();
        current = 0;
        prev = -1;
        firstView = 0;
    }

    public GWindowTabDialogClient(com.maddox.gwindow.GWindow gwindow)
    {
        tabs = new ArrayList();
        current = 0;
        prev = -1;
        firstView = 0;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public java.util.ArrayList tabs;
    public int current;
    public int prev;
    public int firstView;
    public com.maddox.gwindow.LButton lButton;
    public com.maddox.gwindow.RButton rButton;
}

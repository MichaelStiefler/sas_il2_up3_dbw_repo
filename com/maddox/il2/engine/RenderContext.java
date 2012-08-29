// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RenderContext.java

package com.maddox.il2.engine;

import com.maddox.opengl.GLCaps;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.Provider;
import com.maddox.rts.Cfg;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.engine:
//            CfgGObj, Render, GObj

public class RenderContext
{

    public RenderContext()
    {
    }

    public static boolean isProfile()
    {
        return bProfile;
    }

    public static void enableProfile(boolean flag)
    {
        if(flag == bProfile)
        {
            return;
        } else
        {
            bProfile = flag;
            com.maddox.opengl.Provider.setEnableProfile(bProfile);
            return;
        }
    }

    public static void loadConfig(com.maddox.rts.IniFile inifile, java.lang.String s)
    {
        com.maddox.rts.IniFile inifile1 = iniFile;
        java.lang.String s1 = iniSection;
        iniFile = inifile;
        iniSection = s;
        iniChanged = false;
        if(inifile1 != iniFile || s1 == null || !s1.equals(iniSection))
            iniChanged = true;
        com.maddox.il2.engine.RenderContext.enableProfile(inifile.get(s, "Profile", bProfile));
        float f = inifile.get(s, "PolygonOffsetFactor", -0.0625F);
        float f1 = inifile.get(s, "PolygonOffsetUnits", -1F);
        com.maddox.il2.engine.RenderContext.preSetPolygonOffset(f, f1);
    }

    public static void saveConfig()
    {
        com.maddox.il2.engine.RenderContext.CfgList_Save();
    }

    public static int texGetFlags()
    {
        return com.maddox.il2.engine.RenderContext.TexGetFlags();
    }

    public static int shadowGet()
    {
        return cfgShadows.get();
    }

    public static void shadowSet(int i)
    {
        cfgShadows.set(i);
        cfgShadows.apply();
    }

    private static com.maddox.rts.Cfg CfgList_Add(com.maddox.rts.Cfg cfg)
    {
        return CfgList[nCfgList++] = cfg;
    }

    private static com.maddox.rts.CfgInt CfgList_AddInt(java.lang.String s)
    {
        return (com.maddox.rts.CfgInt)com.maddox.il2.engine.RenderContext.CfgList_Add(com.maddox.il2.engine.CfgGObj.findByName(s));
    }

    private static com.maddox.rts.CfgFlags CfgList_AddFlags(java.lang.String s)
    {
        return (com.maddox.rts.CfgFlags)com.maddox.il2.engine.RenderContext.CfgList_Add(com.maddox.il2.engine.CfgGObj.findByName(s));
    }

    private static void CfgList_LoadAndPrepare()
    {
        if(FirstCall || iniChanged)
        {
            for(int i = 0; i < nCfgList; i++)
                if(CfgList[i] != cfgLandFogHaze && CfgList[i] != cfgLandFogLow && CfgList[i] != cfgDrawCollisions)
                    CfgList[i].load(iniFile, iniSection);

            cfgTexFlags.set(5, false);
            cfgTexFlags.set(6, false);
            cfgTexFlags.set(7, false);
        }
        for(int j = 0; j < nCfgList; j++)
            CfgList[j].apply();

        for(int k = 0; k < nCfgList; k++)
            CfgList[k].reset();

    }

    private static void CfgList_Save()
    {
        for(int i = 0; i < nCfgList; i++)
            CfgList[i].save();

    }

    private static void preSetPolygonOffset(float f, float f1)
    {
        polygonOffsetFactor = f;
        polygonOffsetUnuts = f1;
    }

    public static void getPolygonOffset(float af[])
    {
        af[0] = polygonOffsetFactor;
        af[1] = polygonOffsetUnuts;
    }

    public static void setPolygonOffset(float f, float f1)
    {
        com.maddox.il2.engine.RenderContext.SetPolygonOffset(f, f1);
        com.maddox.il2.engine.RenderContext.GetPolygonOffset(polygonOffset);
        polygonOffsetFactor = polygonOffset[0];
        polygonOffsetUnuts = polygonOffset[1];
    }

    public static boolean isActivated()
    {
        return bActivated;
    }

    public static void activate(com.maddox.opengl.GLContext glcontext)
    {
        if(!bActivated)
        {
            bActivated = true;
            com.maddox.il2.engine.RenderContext.Begin(glcontext.getCaps().getColourBits(), glcontext.getCaps().getDepthBits());
            com.maddox.il2.engine.RenderContext.setPolygonOffset(polygonOffsetFactor, polygonOffsetUnuts);
            if(FirstCall)
            {
                cfgShadows = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Shadows");
                cfgShadowsFlags = com.maddox.il2.engine.RenderContext.CfgList_AddFlags("ShadowsFlags");
                cfgTxrQual = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexQual");
                cfgTxrLarge = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexLarge");
                cfgTxrCompress = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexCompress");
                cfgTxrFilter = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexMipFilter");
                cfgTexFlags = com.maddox.il2.engine.RenderContext.CfgList_AddFlags("TexFlags");
                cfgSpecular = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Specular");
                cfgSpecularLight = com.maddox.il2.engine.RenderContext.CfgList_AddInt("SpecularLight");
                cfgDiffuseLight = com.maddox.il2.engine.RenderContext.CfgList_AddInt("DiffuseLight");
                cfgDynamicalLights = com.maddox.il2.engine.RenderContext.CfgList_AddInt("DynamicalLights");
                cfgMeshDetail = com.maddox.il2.engine.RenderContext.CfgList_AddInt("MeshDetail");
                cfgDrawCollisions = com.maddox.il2.engine.RenderContext.CfgList_AddInt("DrawCollisions");
                cfgVisibilityDistance = com.maddox.il2.engine.RenderContext.CfgList_AddInt("VisibilityDistance");
                cfgTxrQual = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexLandQual");
                cfgTxrLarge = com.maddox.il2.engine.RenderContext.CfgList_AddInt("TexLandLarge");
                cfgLandShading = com.maddox.il2.engine.RenderContext.CfgList_AddInt("LandShading");
                cfgLandDetails = com.maddox.il2.engine.RenderContext.CfgList_AddInt("LandDetails");
                cfgLandGeom = com.maddox.il2.engine.RenderContext.CfgList_AddInt("LandGeom");
                cfgSky = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Sky");
                cfgWater = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Water");
                cfgEffects = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Effects");
                cfgForest = com.maddox.il2.engine.RenderContext.CfgList_AddInt("Forest");
                cfgLandFogHaze = com.maddox.il2.engine.RenderContext.CfgList_AddInt("FogHaze");
                cfgLandFogLow = com.maddox.il2.engine.RenderContext.CfgList_AddInt("FogLow");
                cfgHardwareShaders = com.maddox.il2.engine.RenderContext.CfgList_AddInt("HardwareShaders");
                cfgForceShaders1x = com.maddox.il2.engine.RenderContext.CfgList_AddInt("ForceShaders1x");
            }
            com.maddox.il2.engine.RenderContext.CfgList_LoadAndPrepare();
            FirstCall = false;
            com.maddox.il2.engine.Render.prepareStates();
        }
    }

    public static void deactivate()
    {
        if(bActivated)
        {
            bActivated = false;
            com.maddox.il2.engine.RenderContext.End();
        }
    }

    private static native void SetDrawMesh(boolean flag);

    public static void setDrawMesh(boolean flag)
    {
        if(bDrawMesh == flag)
        {
            return;
        } else
        {
            bDrawMesh = flag;
            com.maddox.il2.engine.RenderContext.SetDrawMesh(flag);
            return;
        }
    }

    public static boolean isDrawMesh()
    {
        return bDrawMesh;
    }

    private static native void Begin(int i, int j);

    private static native void End();

    private static native int TexGetFlags();

    private static native void SetPolygonOffset(float f, float f1);

    private static native void GetPolygonOffset(float af[]);

    private static boolean bProfile = false;
    public static final java.lang.String _PROFILE = "Profile";
    public static final java.lang.String _POLYGON_OFFSET_FACTOR = "PolygonOffsetFactor";
    public static final java.lang.String _POLYGON_OFFSET_UNITS = "PolygonOffsetUnits";
    private static com.maddox.rts.IniFile iniFile;
    private static java.lang.String iniSection;
    private static boolean iniChanged = false;
    public static final int TEX_USE_PALETTE = 1;
    public static final int TEX_USE_ALPHA = 2;
    public static final int TEX_USE_INDEX = 4;
    public static final int TEX_USE_DITHER = 8;
    public static final int TEX_POINTSAMPLING = 16;
    public static final int TEX_WIREFRAME = 32;
    public static final int TEX_DISABLE = 64;
    public static final int TEX_SHOW_MATERIALS = 128;
    public static final int TEX_USE_CLAMPED_SPRITES = 256;
    public static final int TEX_VERTEX_ARRAYS = 512;
    public static final int TEX_DISABLE_API_EXTENSIONS = 1024;
    public static final int TEX_ARB_MULTITEXTURE_EXT = 2048;
    public static final int TEX_VERTEX_ARRAY_EXT = 4096;
    public static final int TEX_CLIP_HINT_EXT = 8192;
    public static final int TEX_SECONDARY_COLOR_EXT = 16384;
    public static final int TEX_ENV_COMBINE_EXT = 32768;
    public static final int TEX_ANISOTROPIC_EXT = 0x10000;
    public static final int TEX_TEXCOMPRESS_ARB = 0x20000;
    public static final int TEX_POLYGON_STIPPLE = 0x40000;
    public static final int TEX_HARDWARE_TL = 0x80000;
    public static final int TEX_DRAW_LAND_BY_TRIANGLES = 0x100000;
    public static final int TEX_RENDERER_DIRECTX = 0x800000;
    public static final int SHADOW_NONE = 0;
    public static final int SHADOW_PROJECTIVE = 1;
    public static final int SHADOW_PROJECTIVE_TRANSPARENT = 2;
    public static final int SHADOW_VOLUME = 3;
    public static final int SHADOW_VOLUME_HQ = 4;
    private static float polygonOffsetFactor;
    private static float polygonOffsetUnuts;
    private static float polygonOffset[] = new float[2];
    private static int nCfgList = 0;
    private static boolean FirstCall = true;
    private static com.maddox.rts.Cfg CfgList[] = new com.maddox.rts.Cfg[64];
    private static boolean bActivated = false;
    public static com.maddox.rts.CfgInt cfgTxrLandQual;
    public static com.maddox.rts.CfgInt cfgTxrLandLarge;
    public static com.maddox.rts.CfgInt cfgTxrQual;
    public static com.maddox.rts.CfgInt cfgTxrLarge;
    public static com.maddox.rts.CfgInt cfgTxrCompress;
    public static com.maddox.rts.CfgFlags cfgTexFlags;
    public static com.maddox.rts.CfgInt cfgTxrFilter;
    public static com.maddox.rts.CfgInt cfgShadows;
    public static com.maddox.rts.CfgFlags cfgShadowsFlags;
    public static com.maddox.rts.CfgInt cfgSpecular;
    public static com.maddox.rts.CfgInt cfgSpecularLight;
    public static com.maddox.rts.CfgInt cfgDiffuseLight;
    public static com.maddox.rts.CfgInt cfgDynamicalLights;
    public static com.maddox.rts.CfgInt cfgMeshDetail;
    public static com.maddox.rts.CfgInt cfgDrawCollisions;
    public static com.maddox.rts.CfgInt cfgVisibilityDistance;
    public static com.maddox.rts.CfgInt cfgHardwareShaders;
    public static com.maddox.rts.CfgInt cfgForceShaders1x;
    public static com.maddox.rts.CfgInt cfgLandShading;
    public static com.maddox.rts.CfgInt cfgLandDetails;
    public static com.maddox.rts.CfgInt cfgLandGeom;
    public static com.maddox.rts.CfgInt cfgSky;
    public static com.maddox.rts.CfgInt cfgWater;
    public static com.maddox.rts.CfgInt cfgEffects;
    public static com.maddox.rts.CfgInt cfgLandFogHaze;
    public static com.maddox.rts.CfgInt cfgLandFogLow;
    public static com.maddox.rts.CfgInt cfgForest;
    public static boolean bRenderEnable = true;
    public static boolean bPreRenderEnable = true;
    private static boolean bDrawMesh = true;

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}

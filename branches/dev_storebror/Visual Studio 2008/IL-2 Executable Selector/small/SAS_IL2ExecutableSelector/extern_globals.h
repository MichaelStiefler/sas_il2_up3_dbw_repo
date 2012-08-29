#pragma once
//*************************************************************************
// Globals
//*************************************************************************
extern HWND g_hWnd;
extern HICON g_hIconLarge, g_hIconSmall;
extern int g_iModType;
extern int g_iRamSize;
extern BOOL g_bTIREnabled;
extern BOOL g_bExpertModeEnabled;
extern BOOL g_b4GBAddressSpaceEnabled;
extern BOOL g_bEnableModFilesCache;
extern BOOL g_bMultipleInstancesEnabled;

extern HBRUSH g_hBrushYellow;
extern HBRUSH g_hBrushRed;
extern HBRUSH g_hBrushGreen;
extern HBRUSH g_hBrushOrange;
extern int g_iMessageType;
extern BOOL g_bRamToolTipVisible;
extern TCHAR* g_szModTypes[4];
extern int g_iRamSizes[5];
extern char g_szXmxSetting[5];
extern TCHAR szAppPath[MAX_PATH];
extern TCHAR szExePath[MAX_PATH];
extern TCHAR szDllPath[MAX_PATH];
extern TCHAR szBaseExeModPath[MAX_PATH];
extern TCHAR szBaseExeModTIRPath[MAX_PATH];
extern TCHAR szBaseExeStockPath[MAX_PATH];
extern TCHAR szBaseDllPath[MAX_PATH];
extern TCHAR szIniFile[MAX_PATH];
extern TCHAR szPathName[MAX_PATH];

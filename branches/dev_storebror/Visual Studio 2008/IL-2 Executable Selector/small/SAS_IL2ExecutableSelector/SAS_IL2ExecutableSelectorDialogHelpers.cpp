//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "extern_globals.h"
#include "trace.h"

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    stepRamSize
// FullName:  stepRamSize
// Access:    public 
// Returns:   int
// Qualifier:
// Parameter: int baseRamSize
//************************************
int stepRamSize(int baseRamSize)
{
	if (g_bExpertModeEnabled) return baseRamSize;
	int retVal = g_iRamSizes[0];
	for (int i=lengthof(g_iRamSizes) - 1; i>0; i--)
	{
		if (baseRamSize >= (int)((g_iRamSizes[i] + g_iRamSizes[i-1]) / 2)) {
			retVal = g_iRamSizes[i];
			break;
		}
	}
	if (!g_bExpertModeEnabled) {
		if (retVal > 1024) retVal = 1024;
	}
	return retVal;
}
//************************************
// Method:    SetRamSliderTicks
// FullName:  SetRamSliderTicks
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SetRamSliderTicks()
{
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(g_bExpertModeEnabled ? 64 : 128, g_bExpertModeEnabled ? 2048 : 1024));
	SetDlgItemText(g_hWnd, IDC_STATIC_RAM_MIN, (g_bExpertModeEnabled) ? L"64" : L"128");
	SetDlgItemText(g_hWnd, IDC_STATIC_RAM_MAX, (g_bExpertModeEnabled) ? L"2048" : L"1024");
	//SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETRANGE, (WPARAM)TRUE, (LPARAM)MAKELONG(128, 1024));
	//SetDlgItemText(g_hWnd, IDC_STATIC_RAM_MAX, L"1024");
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_CLEARTICS, (WPARAM)TRUE, 0);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 128);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 256);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 512);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 768);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 1024);
	if (g_bExpertModeEnabled) {
		SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 1200);
		SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETTIC, (WPARAM)TRUE, 2048);
	}
}


//************************************
// Method:    FillDropdown
// FullName:  FillDropdown
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void FillDropdown()
{
	for (int i = 0; i < lengthof(g_szModTypes); i++) {
		SendMessage(GetDlgItem(g_hWnd, IDC_COMBO_MODTYPES), CB_ADDSTRING, 0, (LPARAM)g_szModTypes[i]);
	}
}

//************************************
// Method:    SettingsToControls
// FullName:  SettingsToControls
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void SettingsToControls()
{
	PostMessage(GetDlgItem(g_hWnd, IDC_COMBO_MODTYPES), CB_SETCURSEL, g_iModType, 0);
	if (g_iModType == 0) g_iRamSize = 128;
	g_iRamSize = stepRamSize(g_iRamSize);
	PostMessage(GetDlgItem(g_hWnd, IDC_COMBO_MODTYPES), CB_SETEDITSEL, 0, MAKELPARAM(0, -1));
	EnableWindow(GetDlgItem(g_hWnd, IDC_STATIC_RAM), bShowRamUsage());
	SendMessage(GetDlgItem(g_hWnd, IDC_EDIT_RAM), EM_SETREADONLY, !bShowRamUsageEdit(), 0);
	if (GetFocus() != GetDlgItem(g_hWnd, IDC_EDIT_RAM)) SetDlgItemInt(g_hWnd, IDC_EDIT_RAM, g_iRamSize, FALSE);
	EnableWindow(GetDlgItem(g_hWnd, IDC_STATIC_RAM2), bShowRamUsage());
	EnableWindow(GetDlgItem(g_hWnd, IDC_STATIC_RAM_MIN), bShowRamUsage());
	EnableWindow(GetDlgItem(g_hWnd, IDC_STATIC_RAM_MAX), bShowRamUsage());
	EnableWindow(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), bShowRamUsage());
	SetRamSliderTicks();
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETPOS, (WPARAM) TRUE, (LPARAM) g_iRamSize);
	SendMessage(GetDlgItem(g_hWnd, IDC_SLIDER_RAMSIZE), TBM_SETSEL, (WPARAM) TRUE, (LPARAM) MAKELONG(0, g_iRamSize));
	EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_TIR), bShowTIR());
	CheckDlgButton(g_hWnd, IDC_CHECK_TIR, ( g_bTIREnabled && bShowTIR() )? BST_CHECKED : BST_UNCHECKED);
	EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_EXPERT), bShowExpertMode());
	CheckDlgButton(g_hWnd, IDC_CHECK_EXPERT, ( g_bExpertModeEnabled && bShowExpertMode() )? BST_CHECKED : BST_UNCHECKED);
	EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_LAA), bShowLAA());
	CheckDlgButton(g_hWnd, IDC_CHECK_LAA, ( g_b4GBAddressSpaceEnabled && bShowLAA() )? BST_CHECKED : BST_UNCHECKED);
	EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_CACHED_WRAPPER), bShowModFilesCache());
	CheckDlgButton(g_hWnd, IDC_CHECK_CACHED_WRAPPER, ( g_bEnableModFilesCache && bShowModFilesCache() )? BST_CHECKED : BST_UNCHECKED);
	EnableWindow(GetDlgItem(g_hWnd, IDC_CHECK_MULTI), bShowMultipleInstances());
	CheckDlgButton(g_hWnd, IDC_CHECK_MULTI, ( g_bMultipleInstancesEnabled && bShowMultipleInstances() )? BST_CHECKED : BST_UNCHECKED);
}

//************************************
// Method:    ControlsToSettings
// FullName:  ControlsToSettings
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void ControlsToSettings()
{
	g_iModType = SendMessage(GetDlgItem(g_hWnd, IDC_COMBO_MODTYPES), CB_GETCURSEL, 0, 0);
	if (g_iModType > lengthof(g_szModTypes) -1) g_iModType = 0;
	g_iRamSize = GetDlgItemInt(g_hWnd, IDC_EDIT_RAM, NULL, FALSE);
	if (g_iRamSize < 64) g_iRamSize = 64;
	if (g_iRamSize > 2048) g_iRamSize = 2048;
	g_bTIREnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_TIR);
	g_bExpertModeEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_EXPERT);
	g_b4GBAddressSpaceEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_LAA);
	g_bEnableModFilesCache = IsDlgButtonChecked(g_hWnd, IDC_CHECK_CACHED_WRAPPER);
	g_bMultipleInstancesEnabled = IsDlgButtonChecked(g_hWnd, IDC_CHECK_MULTI);
	if (!g_bExpertModeEnabled) {
		g_b4GBAddressSpaceEnabled = FALSE;
		g_bMultipleInstancesEnabled = FALSE;
	}
}

//************************************
// Control Activation Helper Functions
//************************************
BOOL bShowRamUsage() { return (g_iModType != 0); }
BOOL bShowRamUsageEdit() { return ((g_iModType != 0) && g_bExpertModeEnabled); }
BOOL bShowTIR() { return (g_iModType != 0); }
BOOL bShowExpertMode() { return (g_iModType != 0); }
BOOL bShowLAA() { return ((g_iModType != 0) && g_bExpertModeEnabled); }
BOOL bShowModFilesCache() { return ((g_iModType != 0) && g_bExpertModeEnabled); }
BOOL bShowMultipleInstances() { return ((g_iModType != 0) && (g_bExpertModeEnabled)); }
//************************************
// Method:    BringToFront
// FullName:  BringToFront
// Access:    public 
// Returns:   void
// Qualifier:
// Parameter: HWND hwnd
//************************************
void BringToFront(HWND hwnd) {
	SystemParametersInfo(SPI_SETFOREGROUNDLOCKTIMEOUT, 0, (LPVOID)0, SPIF_SENDWININICHANGE | SPIF_UPDATEINIFILE);
	ShowWindowAsync(hwnd, SW_SHOWNORMAL);
	SetForegroundWindow(hwnd);
	SystemParametersInfo(SPI_SETFOREGROUNDLOCKTIMEOUT, 200000, (LPVOID)0, SPIF_SENDWININICHANGE | SPIF_UPDATEINIFILE);
}

//************************************
// Method:    CheckRamUsage
// FullName:  CheckRamUsage
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void CheckRamUsage() {
	MEMORYSTATUSEX status;
	status.dwLength = sizeof(status);
	GlobalMemoryStatusEx(&status);
	int iMegsFree = (int)(status.ullAvailVirtual / 0x100000);
	if (g_iRamSize <= 1200) {
		PostMessage(g_hWnd, WM_IL2_INSUFFICIENT_RAM, 0, RAM_MESSAGE_GOOD);
	} else if (g_iRamSize < iMegsFree) {
		PostMessage(g_hWnd, WM_IL2_INSUFFICIENT_RAM, 0, RAM_MESSAGE_CRITICAL);
	} else {
		PostMessage(g_hWnd, WM_IL2_INSUFFICIENT_RAM, 0, RAM_MESSAGE_ERROR);
	}
}
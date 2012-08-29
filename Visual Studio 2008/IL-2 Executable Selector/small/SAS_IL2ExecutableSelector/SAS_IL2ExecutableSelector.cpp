//*************************************************************************
// Includes
//*************************************************************************
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <commctrl.h>
#include "common.h"
#include "SAS_IL2ExecutableSelector.h"
#include "trace.h"

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    _tWinMain
// FullName:  _tWinMain
// Access:    public 
// Returns:   int WINAPI
// Qualifier:
// Parameter: HINSTANCE hInstance
// Parameter: HINSTANCE hPrevInstance
// Parameter: LPWSTR lpCmdLine
// Parameter: int nShowCmd
//************************************
int WINAPI _tWinMain(
					 HINSTANCE hInstance,
					 HINSTANCE hPrevInstance,
					 LPWSTR lpCmdLine,
					 int nShowCmd)
{
	// Make Sure IL-2 Executable Selector is only running with one single instance.
	HANDLE hMutex = CreateMutex(NULL, TRUE, SELECTOR_MUTEX);
	if ( (hMutex == NULL) || (GetLastError() == ERROR_ALREADY_EXISTS) )
	{
		if (hMutex != NULL)
		{
			ReleaseMutex(hMutex);
			CloseHandle(hMutex);
		}
		// If the selector is already running, show it's window and bring it to front.
		HWND hWndSelector = FindWindow(NULL, L"SAS IL-2 Executable Selector");
		if (hWndSelector != NULL) {
			ShowWindow(hWndSelector, SW_SHOW);
			SetForegroundWindow(hWndSelector);
			SetActiveWindow(hWndSelector);
		}
		return 0;
	}

	GetFilesAndPaths();
	if (!IsFolderWriteable()) {
		MessageBox(NULL,
			L"Unsufficient file access permissions in your IL-2 game folder!\r\n"
			L"\r\n"
			L"Most probably you've installed IL-2 in the \"C:\\Program Files\\\" folder\r\n"
			L"on a Windows Vista or Windows 7 system.\r\n"
			L"In this case please copy your whole IL-2 game folder to a different\r\n"
			L"location, e.g. \"C:\\IL2\\\", and launch your game there.\r\n"
			L"\r\n"
			L"In any other case, please query SAS or UP staff for assistance.\r\n"
			L"We apologize for any inconvenience.",
			L"IL-2 Executable Selector by SAS & UltraPack",
			MB_ICONEXCLAMATION | MB_SETFOREGROUND | MB_TOPMOST | MB_OK);
	}
	ReadIniSettings();
	g_hIconSmall = (HICON)LoadImage(hInstance, MAKEINTRESOURCE(IDI_ICON1), IMAGE_ICON, 16, 16, LR_DEFAULTSIZE); 
	g_hIconLarge = (HICON)LoadImage(hInstance, MAKEINTRESOURCE(IDI_ICON1), IMAGE_ICON, 32, 32, LR_DEFAULTSIZE); 
	g_hBrushYellow = CreateSolidBrush(RGB(255,255,0));
	g_hBrushRed = CreateSolidBrush(RGB(255,0,0));
	g_hBrushGreen = CreateSolidBrush(RGB(0,255,0));
	g_hBrushOrange = CreateSolidBrush(RGB(255,140,0));

	DialogBox(
		hInstance,
		MAKEINTRESOURCE(IDD_IL2EXESELECTOR),
		GetDesktopWindow(),
		SASES_DialogProc);

	DeleteBrush(g_hBrushYellow);
	DeleteBrush(g_hBrushRed);
	DeleteBrush(g_hBrushGreen);
	DeleteBrush(g_hBrushOrange);

	ReleaseMutex(hMutex);
	CloseHandle(hMutex);
	return 0;
}

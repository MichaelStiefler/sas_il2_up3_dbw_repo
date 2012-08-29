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
#include <Psapi.h>
#pragma comment(lib, "Psapi.lib")
#include <Tlhelp32.h>

#pragma comment(lib, "comctl32.lib")
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    GetFilesAndPaths
// FullName:  GetFilesAndPaths
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void GetFilesAndPaths()
{
	memset(szPathName, 0, sizeof(szPathName));
	GetModuleFileName(NULL,szAppPath,MAX_PATH);
	_tcscpy(szPathName, szAppPath);
	LPTSTR pszFileName = NULL;
	pszFileName = _tcsrchr(szPathName, '\\') + 1;
	*pszFileName = '\0';

	_stprintf(szIniFile, L"%s%s", szPathName, INI_NAME);
	_stprintf(szBaseDllPath, L"%s%s\\%s", szPathName, SELECTOR_FILES_PATH, BASE_DLL_NAME);
	_stprintf(szBaseExeStockPath, L"%s%s\\%s", szPathName, SELECTOR_FILES_PATH, BASE_STOCK_EXE_NAME);
	_stprintf(szBaseExeModPath, L"%s%s\\%s", szPathName, SELECTOR_FILES_PATH, BASE_MOD_EXE_NAME);
	_stprintf(szBaseExeModTIRPath, L"%s%s\\%s", szPathName, SELECTOR_FILES_PATH, BASE_MOD_TIR_EXE_NAME);
	_stprintf(szDllPath, L"%s%s", szPathName, IL2_DLL_NAME);
	_stprintf(szExePath, L"%s%s", szPathName, IL2_EXE_NAME);

}

//************************************
// Method:    ShowLastError
// FullName:  ShowLastError
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void ShowLastError()
{
	LPVOID lpMsgBuf;
	FormatMessage( 
		FORMAT_MESSAGE_ALLOCATE_BUFFER | 
		FORMAT_MESSAGE_FROM_SYSTEM | 
		FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL,
		GetLastError(),
		0, // Default language
		(LPTSTR) &lpMsgBuf,
		0,
		NULL 
		);
	// Process any inserts in lpMsgBuf.
	// ...
	// Display the string.
	MessageBox( NULL, (LPCTSTR)lpMsgBuf, L"SAS IL-2 Executable Selector Error", MB_OK | MB_ICONINFORMATION );
	// Free the buffer.
	LocalFree( lpMsgBuf );
}

//************************************
// Method:    CreateFiles
// FullName:  CreateFiles
// Access:    public 
// Returns:   void
// Qualifier:
//************************************
void CreateFiles()
{
	if (g_iModType == 0) {
		SecureCopyFile(szBaseExeStockPath, szExePath);
	} else {
		if (g_bTIREnabled) {
			SecureCopyFile(szBaseExeModTIRPath, szExePath);
		} else {
			SecureCopyFile(szBaseExeModPath, szExePath);
		}
	}

	if (g_iModType != 0) {
		FILE *outfile = _wfopen (szExePath, L"r+bc");
		char strMemSize[16];
		memset(strMemSize, 0, 6);
		_itoa_s(g_iRamSize, strMemSize, 16, 10);
		strcat(strMemSize, "M");
		_fseek_nolock(outfile, EXE_OFFSET_XMX, SEEK_SET);
		fwrite(strMemSize, sizeof(char), strlen(strMemSize), outfile);

		if (g_b4GBAddressSpaceEnabled) {
			BYTE dataLAA[1] = EXE_DATA_LAA;
			_fseek_nolock(outfile, EXE_OFFSET_LAA, SEEK_SET);
			fwrite(dataLAA, sizeof(BYTE), 1, outfile);
		}
		if (!g_bMultipleInstancesEnabled) {
			_fseek_nolock(outfile, EXE_OFFSET_MUTEX, SEEK_SET);
			fwrite(EXE_DATA_MUTEX, sizeof(BYTE), strlen(EXE_DATA_MUTEX), outfile);
		}
		fflush(outfile);
		fclose(outfile);
	}

	if (g_iModType == 0) {
		SecureDeleteFile(szDllPath);
	} else {
		SecureCopyFile(szBaseDllPath, szDllPath);
	}
}

//************************************
// Method:    IsFolderWriteable
// FullName:  IsFolderWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
//************************************
BOOL IsFolderWriteable()
{
	return (IsFileWriteable(szIniFile) && IsFileWriteable(szExePath) && IsFileWriteable(szDllPath));
}

//************************************
// Method:    IsFileWriteable
// FullName:  IsFileWriteable
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR szFilePath
//************************************
BOOL IsFileWriteable(LPCTSTR szFilePath) {
	BOOL bRetVal = TRUE;
	FILE* fTest;
	fTest = _wfopen(szFilePath, L"a+");
	if (fTest == NULL)
		bRetVal = FALSE;
	else
		fclose(fTest);
	return bRetVal;
}

//************************************
// Method:    SetFileAttributesWithRetry
// FullName:  SetFileAttributesWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
// Parameter: int iAttempts
//************************************
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!SetFileAttributes(lpFileName, dwFileAttributes) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"SetFileAttributes(%s, %08X) failed, %d attempts left\r\n", lpFileName, dwFileAttributes, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    DeleteFileWithRetry
// FullName:  DeleteFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: int iAttempts
//************************************
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	while (!DeleteFile(lpFileName) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"DeleteFile(%s) failed, %d attempts left\r\n", lpFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    MoveFileWithRetry
// FullName:  MoveFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
// Parameter: int iAttempts
//************************************
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	while (!MoveFile(lpExistingFileName, lpNewFileName) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"MoveFile(%s, %s) failed, %d attempts left\r\n", lpExistingFileName, lpNewFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    CopyFileWithRetry
// FullName:  CopyFileWithRetry
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
// Parameter: int iAttempts
//************************************
BOOL CopyFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	SecureDeleteFile(lpNewFileName);
	while (!CopyFile(lpExistingFileName, lpNewFileName, FALSE) && (iAttempts > 0)) {
		iAttempts--;
		TRACE(L"CopyFile(%s, %s) failed, %d attempts left\r\n", lpExistingFileName, lpNewFileName, iAttempts);
		Sleep(100);
	}
	return (iAttempts > 0);
}

//************************************
// Method:    SecureDeleteFile
// FullName:  SecureDeleteFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
//************************************
BOOL SecureDeleteFile(LPCTSTR lpFileName) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return TRUE;
	SecureSetFileAttributes(lpFileName, FILE_ATTRIBUTE_NORMAL);
	return DeleteFileWithRetry(lpFileName, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureSetFileAttributes
// FullName:  SecureSetFileAttributes
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpFileName
// Parameter: DWORD dwFileAttributes
//************************************
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes) {
	if (GetFileAttributes(lpFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	return SetFileAttributesWithRetry(lpFileName, dwFileAttributes, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureMoveFile
// FullName:  SecureMoveFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
//************************************
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	SecureSetFileAttributes(lpNewFileName, FILE_ATTRIBUTE_NORMAL);
	SecureDeleteFile(lpNewFileName);
	return MoveFileWithRetry(lpExistingFileName, lpNewFileName, FILE_OPERATION_RETRIES);
}

//************************************
// Method:    SecureCopyFile
// FullName:  SecureCopyFile
// Access:    public 
// Returns:   BOOL
// Qualifier:
// Parameter: LPCTSTR lpExistingFileName
// Parameter: LPCTSTR lpNewFileName
//************************************
BOOL SecureCopyFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName) {
	if (GetFileAttributes(lpExistingFileName) == INVALID_FILE_ATTRIBUTES) return FALSE;
	return CopyFileWithRetry(lpExistingFileName, lpNewFileName, FILE_OPERATION_RETRIES);
}
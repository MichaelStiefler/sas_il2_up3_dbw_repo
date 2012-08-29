@echo off
Set "modDir=D:\IL2\IL-2 UP 3 RC + DBW\#DBW\000000_AGM_SAM_KS1"
rem Set "modDir=D:\IL2\IL-2 Ultrapack 3 RC\#DBW\000000_AGM_SAM_KS1"
echo "Collecting Files for hashing..."
start /min /wait cmd.exe /c copyfiles.cmd
echo "Hashing Files..."
start /min /wait cmd.exe /c hashclasses.cmd
cd hashed
IF EXIST "%modDir%" GOTO MODDIR_EXISTS
mkdir "%modDir%"
:MODDIR_EXISTS
echo "Removing old Files..."
attrib -r -s -h -a "%modDir%\*."
rem attrib +r "%modDir%\P61_FM."
del /q "%modDir%\????????????????."
rem attrib -r "%modDir%\P61_FM."
echo "Copying Files..."
copy *. "%modDir%" /Y
cd ..
GOTO END
echo "Processing cod files..."
cd cod
@java -jar loadouteditor.jar
cd new_cod
IF EXIST "%modDir%\cod" GOTO CODDIR_EXISTS
mkdir "%modDir%\cod"
:CODDIR_EXISTS
copy *. "%modDir%\cod" /Y
cd ..
cd ..
:END
echo "Postbuild process finished!"
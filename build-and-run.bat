@echo off
SET SRC_DIR=src
SET BUILD_DIR=build
SET DIST_DIR=dist
SET MAIN_CLASS=main.Main
SET JAR_NAME=ConvexHull.jar

IF EXIST "%BUILD_DIR%" rmdir /S /Q "%BUILD_DIR%"
IF EXIST "%DIST_DIR%" rmdir /S /Q "%DIST_DIR%"

mkdir "%BUILD_DIR%"
mkdir "%DIST_DIR%"

FOR /R "%SRC_DIR%" %%F IN (*.java) DO (
    javac -d "%BUILD_DIR%" "%%F"
)

cd /D "%BUILD_DIR%"
jar cvfe ..\%DIST_DIR%\%JAR_NAME% %MAIN_CLASS% .
cd ..

java -jar "%DIST_DIR%\%JAR_NAME%"

pause

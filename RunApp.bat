@echo off
REM Ejecutar la aplicación JavaFX empaquetada en el JAR
java --module-path "C:\Program Files\javafx\javafx-sdk-22.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar target\gist-0.0.1-SNAPSHOT-jar-with-dependencies.jar
pause
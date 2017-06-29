del ..\build\lib
mvn dependency:copy-dependencies -DoutputDirectory=../build/lib  -DincludeScope=runtime
copy target\hs-portal-swing-0.0.1-SNAPSHOT.jar ..\build\lib
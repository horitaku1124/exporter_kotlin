exporter_kotlin
----------------------

### Build

```bash
./gradlew clean build
~/graalvm-ce-java11-20.0.0/Contents/Home/bin/native-image --no-fallback -jar build/libs/*.jar
```


```bash
docker run -it --rm -v `pwd`/build/libs/:/mnt/libs graalvm20 bash
native-image --no-fallback -jar /mnt/libs/*.jar
mv exporter_kotlin-1.0-SNAPSHOT /mnt/libs/
```
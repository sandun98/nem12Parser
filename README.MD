# SimpleNem12Parser

This application contains a Nem12 Parser to read simplified nem12 file and produce meter readings grouped in to NMIs.

## Build
gradlew build

## Test
gradlew test

## Run (Windows)
gradlew bootRun --args='.\src\test\resources\SimpleNem12.csv'


## Assumptiions

* If a meter record is faulty processing of the file will exit with error
* If a meter block start record is faulty processing of the file will exit with error
* If start and end of file is not found processing will stop with error




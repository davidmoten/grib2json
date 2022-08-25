# grib2json
<a href="https://github.com/davidmoten/grib2json/actions/workflows/ci.yml"><img src="https://github.com/davidmoten/grib2json/actions/workflows/ci.yml/badge.svg"/></a><br/>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/grib2json-runtime/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/grib2json)<br/>
[![codecov](https://codecov.io/gh/davidmoten/grib2json/branch/master/graph/badge.svg)](https://codecov.io/gh/davidmoten/grib2json)<br/>

A command line utility that decodes [GRIB2](http://en.wikipedia.org/wiki/GRIB) files as JSON.

This utility uses the netCDF-Java GRIB decoder, part of the [THREDDS](https://github.com/Unidata/thredds) project
by University Corporation for Atmospheric Research/Unidata.

This project is a fork that publishes the jar and the tar.gz to Maven Central using the *com.github.davidmoten* groupId.

Getting started
----------------
Add this dependency to your pom.xml:

```xml
<dependency>
  <groupId>com.github.davidmoten</groupId>
  <artifactId>grib2json</artifactId>
  <version>VERSION_HERE</version>
</dependency>
```
See [releases](../../releases) for the latest version number.

How to build
------------

```
git clone <this project>
mvn clean install 
```

Usage
-----
There are two main usage paths. One is to call java classes using the jar and the other is to use the launch script inside the tar.gz artifact. To use the launch script explode the tar.gz to a folder.

The `grib2json` launch script is located in the `bin` directory and requires the `JAVA_HOME` environment
variable to be defined.

```
> grib2json --help
Usage: grib2json [options] FILE
	[--compact -c] : enable compact Json formatting
	[--data -d] : print GRIB record data
	[--filter.category --fc value] : select records with this numeric category
	[--filter.parameter --fp value] : select records with this numeric parameter
	[--filter.surface --fs value] : select records with this numeric surface type
	[--filter.value --fv value] : select records with this numeric surface value
	[--help -h] : display this help
	[--names -n] : print names of numeric codes
	[--output -o value] : write output to the specified file (default is stdout)
	[--verbose -v] : enable logging to stdout
```

For example, the following command outputs to stdout the records for parameter 2 (U-component_of_wind), with
surface type 103 (Specified height level above ground), and surface value 10.0 meters from the GRIB2 file
_gfs.t18z.pgrbf00.2p5deg.grib2_. Notice the optional inclusion of human-readable _xyzName_ keys and the data array:

```
> grib2json --names --data --fp 2 --fs 103 --fv 10.0 gfs.t18z.pgrbf00.2p5deg.grib2

[
    {
        "header":{
            "discipline":0,
            "disciplineName":"Meteorological products",
            "gribEdition":2,
            "gribLength":27759,
            "center":7,
            "centerName":"US National Weather Service - NCEP(WMC)",
            "parameterNumber":2,
            "parameterNumberName":"U-component_of_wind",
            "parameterUnit":"m.s-1",
            "surface1Type":103,
            "surface1TypeName":"Specified height level above ground",
            "surface1Value":10.0,
            ...
        },
        "data":[
            -2.12,
            -2.27,
            -2.41,
            ...
        ]
    }
]
```

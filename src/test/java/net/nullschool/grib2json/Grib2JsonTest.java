package net.nullschool.grib2json;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

import org.junit.Test;

public class Grib2JsonTest {

	@Test
	public void testWrite() throws IOException {
		File grib = new File("src/test/resources/surface-winds.grb2");
		File t = new File("target/t.grb2");
		new File("target").mkdirs();
		t.delete();
		Files.copy(grib.toPath(), t.toPath());
		File output = new File("target/output");
		Options options = new Options() {

			@Override
			public boolean getShowHelp() {
				return false;
			}

			@Override
			public boolean getPrintNames() {
				return true;
			}

			@Override
			public boolean getPrintData() {
				return true;
			}

			@Override
			public boolean isCompactFormat() {
				return false;
			}

			@Override
			public boolean getEnableLogging() {
				return false;
			}

			@Override
			public File getOutput() {
				return output;
			}

			@Override
			public File getFile() {
				return null;
			}

			@Override
			public Integer getFilterDiscipline() {
				return null;
			}

			@Override
			public Integer getFilterCategory() {
				return null;
			}

			@Override
			public String getFilterParameter() {
				return null;
			}

			@Override
			public Integer getFilterSurface() {
				return null;
			}

			@Override
			public Double getFilterValue() {
				return null;
			}

			@Override
			public File getRecipe() {
				return null;
			}};
		new Grib2Json(t, Collections.singletonList(options)).write();
		System.out.println("input file size grb2  = "+ grib.length());
		System.out.println("output file size json = " + output.length());
		try (Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(output), StandardCharsets.UTF_8))) {
		    JsonParser parser = Json.createParser(r);
		    parser.next();
		    JsonArray a = parser.getArray();
		    JsonObject b = a.getJsonObject(0);
		    JsonObject c = b.getJsonObject("header");
		    assertEquals(245889, c.getInt("gribLength"));
		    JsonArray d = b.getJsonArray("data");
		    assertEquals(-0.84, d.getJsonNumber(0).doubleValue(), 0.0001);
		}
	}

}

package net.nullschool.grib2json;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;

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
		String json = new String(Files.readAllBytes(output.toPath()), StandardCharsets.UTF_8);
		assertTrue(json.contains("\"disciplineName\": \"Meteorological products\""));
	}

}

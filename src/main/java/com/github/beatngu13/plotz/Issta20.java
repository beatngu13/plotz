package com.github.beatngu13.plotz;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.BoxPlot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout.BarMode;

public class Issta20 {

	private static final String FS1_COLUMN_NAME = "Fairly similar 1";
	private static final String FS2_COLUMN_NAME = "Fairly similar 2";
	private static final String FS3_COLUMN_NAME = "Fairly similar 3";
	private static final int MEASUREMENTS_PER_INSTANCE = 400;

	public static void main(final String[] args) throws Exception {
		rq2();
		rq3();
	}

	private static void rq2() throws Exception {
		final IntColumn d = IntColumn.create("Deleted", IntStream.of(586, 9946, 50));
		final IntColumn c = IntColumn.create("Created", IntStream.of(1472, 10881, 43));
		final IntColumn m = IntColumn.create("Maintained", IntStream.of(375, 613, 669));
		final StringColumn i = StringColumn.create("i", Stream.of(FS1_COLUMN_NAME, FS2_COLUMN_NAME, FS3_COLUMN_NAME));

		final Table table = Table.create(d, c, m, i);
		final Figure figure = VerticalBarPlot.create("", table, "i", BarMode.GROUP, "Deleted", "Created", "Maintained");
		Plot.show(figure);
	}

	private static void rq3() throws Exception {
		final IntColumn time = IntColumn.create("time", getAllTimes());
		final StringColumn instance = StringColumn.create("instance", getAllInstances());
		final Table table = Table.create(time, instance);
		final Figure figure = BoxPlot.create("", table, "instance", "time");
		Plot.show(figure);
	}

	private static IntStream getAllTimes() throws Exception {
		final IntStream fs1 = getTime("/fs1.txt");
		final IntStream fs2 = getTime("/fs2.txt");
		final IntStream fs3 = getTime("/fs3.txt");
		return IntStream.concat(IntStream.concat(fs1, fs2), fs3);
	}

	private static IntStream getTime(final String file) throws Exception {
		final URI uri = Issta20.class.getResource(file).toURI();
		final Path path = Paths.get(uri);
		return Files.readAllLines(path).stream() //
				.map(Integer::valueOf) //
				.mapToInt(Integer::intValue);
	}

	private static Stream<String> getAllInstances() {
		final Stream<String> fs1 = Collections.nCopies(MEASUREMENTS_PER_INSTANCE, FS1_COLUMN_NAME).stream();
		final Stream<String> fs2 = Collections.nCopies(MEASUREMENTS_PER_INSTANCE, FS2_COLUMN_NAME).stream();
		final Stream<String> fs3 = Collections.nCopies(MEASUREMENTS_PER_INSTANCE, FS3_COLUMN_NAME).stream();
		return Stream.concat(Stream.concat(fs1, fs2), fs3);
	}

}

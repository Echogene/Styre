package styre.boinc.factory;

import ophelia.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import styre.boinc.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author steven
 */
@Component
public class TaskFactory {

	private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");

	private final String boincLocation;

	@Autowired
	public TaskFactory(@Value("${boinc.location}") String boincLocation) {
		this.boincLocation = boincLocation;
		boolean exists = Files.exists(Paths.get(boincLocation));
		if (!exists) {
			throw new RuntimeException(boincLocation + " did not exist in property boinc.location");
		}
	}

	public List<Task> getTasks() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("./boinccmd", "--get_tasks");

		pb.directory(new File(boincLocation));
		Process start = pb.start();
		start.waitFor();
		String processOutput = IOUtils.convertStreamToString(start.getInputStream());

		String[] split = processOutput.split("\\d+\\) \\-+");
		List<String> tasks = new ArrayList<>();
		tasks.addAll(Arrays.asList(split).subList(1, split.length));
		return tasks.stream()
				.map(s -> s.split("\n"))
				.map(Arrays::asList)
				.map(this::getTask)
				.collect(Collectors.toList());
	}

	private Task getTask(List<String> taskLines) {
		String name = getProperty("name", taskLines);
		LocalDateTime deadline = LocalDateTime.parse(getProperty("report deadline", taskLines), FORMATTER);
		double completion = Double.parseDouble(getProperty("fraction done", taskLines));
		double timeSpent = Double.parseDouble(getProperty("current CPU time", taskLines));
		double timeRemaining = Double.parseDouble(getProperty("estimated CPU time remaining", taskLines));
		return new Task(
				name,
				deadline,
				completion,
				timeSpent,
				timeRemaining
		);
	}

	private String getProperty(String propertyName, List<String> lines) {
		return lines.stream()
				.map(String::trim)
				.filter(line -> line.startsWith(propertyName + ": "))
				.map(line -> line.substring(line.indexOf(":") + 2))
				.map(line -> line.replace("  ", " 0"))
				.collect(Collectors.collectingAndThen(
						Collectors.toList(),
						list -> {
							if (list.size() != 1) {
								throw new IllegalStateException();
							}
							return list.get(0);
						}
				));
	}
}

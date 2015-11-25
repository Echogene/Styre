package styre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import styre.boinc.factory.TaskFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author steven
 */
@Controller
public class TaskController {

	private final TaskFactory taskFactory;
	private final String boincLocation;

	@Autowired
	public TaskController(TaskFactory taskFactory, @Value("${boinc.location}") String boincLocation) {
		this.taskFactory = taskFactory;
		this.boincLocation = boincLocation;
		boolean exists = Files.exists(Paths.get(boincLocation));
		if (!exists) {
			throw new RuntimeException(boincLocation + " did not exist in property boinc.location");
		}
	}

	@RequestMapping("/")
	ModelAndView home() throws IOException, InterruptedException {
		Map<String, Object> model = new HashMap<>();
		model.put("tasks", taskFactory.getTasks());
		return new ModelAndView("views/tasks_view", model);
	}

	@RequestMapping("/play")
	String play() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("./boinccmd", "--set_run_mode", "auto");
		pb.directory(new File(boincLocation));
		pb.start();

		return "redirect:/";
	}

	@RequestMapping("/pause")
	String pause() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("./boinccmd", "--set_run_mode", "never");
		pb.directory(new File(boincLocation));
		pb.start();

		return "redirect:/";
	}
}

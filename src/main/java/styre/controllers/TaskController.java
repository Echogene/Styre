package styre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import styre.boinc.factory.TaskFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author steven
 */
@Controller
public class TaskController {

	private final TaskFactory taskFactory;

	@Autowired
	public TaskController(TaskFactory taskFactory) {
		this.taskFactory = taskFactory;
	}

	@RequestMapping("/")
	ModelAndView home() throws IOException, InterruptedException {
		Map<String, Object> model = new HashMap<>();
		model.put("tasks", taskFactory.getTasks());
		return new ModelAndView("views/tasks_view", model);
	}
}

package styre.boinc.model;

import java.time.LocalDateTime;

/**
 * @author steven
 */
public class Task {

	private final String name;
	private final LocalDateTime deadline;
	private final double completion;
	private final double timeSpent;
	private final double timeRemaining;

	public Task(String name, LocalDateTime deadline, double completion, double timeSpent, double timeRemaining) {
		this.name = name;
		this.deadline = deadline;
		this.completion = completion;
		this.timeSpent = timeSpent;
		this.timeRemaining = timeRemaining;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public double getCompletion() {
		return completion;
	}

	public double getTimeSpent() {
		return timeSpent;
	}

	public double getTimeRemaining() {
		return timeRemaining;
	}
}

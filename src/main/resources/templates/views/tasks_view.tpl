yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title('Tasks')
	}
	body {
		tasks.each { task ->
			div() {
				span(task.name)
				span(task.deadline)
				span(task.completion)
				span(task.timeSpent)
				span(task.timeRemaining)
			}
		}
	}
}
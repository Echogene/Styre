import static styre.Formatting.formatPercentage
import static styre.Formatting.formatTime

yieldUnescaped '<!DOCTYPE html>'
html {
	head {
		title('Tasks')
		link(rel: 'stylesheet', href: '/css/main.css')
	}
	body {
		div(class: 'table') {
			div(class: 'header row') {
				span('Name');
				span('Deadline');
				span('% Complete');
				span('Time Spent');
				span('Estimated Time Remaining');
			}
			tasks.each { task ->
				div(class: 'row') {
					span(task.name)
					span(task.deadline)
					span("${formatPercentage(task.completion)}%", style: "background-image: linear-gradient(to right, #8f6 0, #af8 ${formatPercentage(task.completion)}%, transparent ${formatPercentage(task.completion)}%, transparent 100%);")
					span("${formatTime(task.timeSpent)}")
					span("${formatTime(task.timeRemaining)}")
				}
			}
		}
	}
}
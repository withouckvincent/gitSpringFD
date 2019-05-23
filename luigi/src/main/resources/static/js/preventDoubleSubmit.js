document.querySelector("form").onsubmit = function(event) {
	event.target.querySelector("input[type=submit]").disabled = true;
}

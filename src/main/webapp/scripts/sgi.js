
$(document).ready(function() {
	$("#jMenu").jMenu();
});

function askSubmission(msg, form) {
	if (confirm(msg))
		form.submit();
}

function relativeRedir(loc) {
	var b = document.getElementsByTagName('base');
	if (b && b[0] && b[0].href) {
		if (b[0].href.substr(b[0].href.length - 1) == '/'
			&& loc.charAt(0) == '/')
			loc = loc.substr(1);
		loc = b[0].href + loc;
	}
	window.location.replace(loc);
}

/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function myFunction() {
	document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
	if (!event.target.matches('.dropbtn')) {

		var dropdowns = document.getElementsByClassName("dropdown-content");
		var i;
		for (i = 0; i < dropdowns.length; i++) {
			var openDropdown = dropdowns[i];
			if (openDropdown.classList.contains('show')) {
				openDropdown.classList.remove('show');
			}
		}
	}
};
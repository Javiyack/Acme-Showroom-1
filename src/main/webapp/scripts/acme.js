$(document).ready(function () {
    markOpenLink();
    // Get the Sidebar
    var mySidebar = document.getElementById("mySidebar");

    // Get the DIV with overlay effect
    var overlayBg = document.getElementById("myOverlay");

    var mybtn = document.getElementsByClassName("testbtn")[0];

    var slideIndex = 1;
    showSlides(1);
    ajaxSearch(document.getElementById("combo"), "/Acme-Showroom")

});

function changeLang(lang) {
    var pathname = '';
    pathname += window.location.href;
    if (pathname.includes('?')) {
        if (pathname.includes('language')) {
            pathname = pathname.replace('language=es', "language=" + lang);
            pathname = pathname.replace('language=en', "language=" + lang);
        } else {
            pathname += "&language=" + lang;
        }
    } else {
        pathname += "?language=" + lang;
    }
    window.location.replace(pathname);
}


function openAccordion(element) {
    element.className += " w3-show";
    element.previousElementSibling.className += " w3-flat-silver";

}

function closeAccordion(element) {
    element.className = element.className.replace(" w3-show", "");
    element.previousElementSibling.className =
        element.previousElementSibling.className.replace(" w3-orange", "");
    element.previousElementSibling.className =
        element.previousElementSibling.className.replace(" w3-flat-silver", "");
    markOpenLink();
}

function unselectAll() {
    elementos = document.getElementsByName('menuItem');
    elementos.forEach(unSelect);

}

function unSelect(item, index) {
    if (item.id.includes('Acc')) {
        closeAccordion(item);
    }
    item.className = item.className.replace(" w3-orange", "");
}

function select(item) {
    if (item.id.includes('Acc')) {
        openAccordion(item);
        item.previousElementSibling.className += " w3-orange";
        item.previousElementSibling.className += " w3-text-black";
    } else {
        item.className += " w3-orange";
    }
}

function myAccordionFunc(elementId) {
    var x = document.getElementById(elementId);
    if (x.className.indexOf("w3-show") == -1) {
        openAccordion(x);
    } else {
        closeAccordion(x);
    }
}

function markOpenLink() {
    unselectAll();
    var pathname = '';
    pathname += window.location.pathname;
    if (pathname.includes('showroom')) {
        select(document.getElementById('showrooms'));
    } else if (pathname.includes('item')) {
        select(document.getElementById('items'));
    } else if (pathname.includes('chirp')) {
        select(document.getElementById('chirpsAcc'));
    } else if (pathname.includes('request')) {
        select(document.getElementById('requestsAcc'));
    } else if (pathname.includes('subscription')) {
        select(document.getElementById('subscriptions'));
    } else if (pathname.includes('actor/actor')) {
        select(document.getElementById('actors'));
    } else if (pathname.includes('dashboard')) {
        select(document.getElementById('dashboard'));
    }
}


function setRating(element, rating, max) {
    document.getElementById("rating").value = rating;
    var id = "star";
    var i = rating + 1;
    var next = id + i;
    if (element.classList.contains("checked")) {
        if (i <= max && document.getElementById(next).classList.contains("checked")) {
            for (i = 1; i <= rating; i++) {
                id += i;
                document.getElementById(id).classList.remove("fa-star-o");
                document.getElementById(id).classList.add("fa-star");
                document.getElementById(id).classList.add("checked");
                id = "star";
            }
            for (i = rating + 1; i <= max; i++) {
                id += i;
                document.getElementById(id).classList.remove("checked");
                document.getElementById(id).classList.remove("fa-star");
                document.getElementById(id).classList.add("fa-star-o");
                id = "star";
            }

        } else {
            for (i = 1; i <= rating; i++) {
                id += i;
                document.getElementById(id).classList.remove("fa-star");
                document.getElementById(id).classList.add("fa-star-o");
                document.getElementById(id).classList.remove("checked");
                id = "star";
            }
        }
    } else {
        for (i = 1; i <= rating; i++) {
            id += i;
            document.getElementById(id).classList.remove("fa-star-o");
            document.getElementById(id).classList.add("fa-star");
            document.getElementById(id).classList.add("checked");
            id = "star";
        }
        for (i = rating + 1; i <= max; i++) {
            id += i;
            document.getElementById(id).classList.remove("fa-star");
            document.getElementById(id).classList.add("fa-star-o");
            document.getElementById(id).classList.remove("checked");
            id = "star";
        }
    }

}

function fillRating(rating) {
    var id = "star";
    var i;
    for (i = 1; i <= rating; i++) {
        id += i;
        document.getElementById(id).classList.remove("fa-star-o");
        document.getElementById(id).classList.add("fa-star");
        document.getElementById(id).classList.add("checked");
        id = "star";
    }
    for (i = rating + 1; i <= 10; i++) {
        id += i;
        document.getElementById(id).classList.remove("fa-star");
        document.getElementById(id).classList.add("fa-star-o");
        document.getElementById(id).classList.remove("checked");
        id = "star";
    }
}

function validatePhone(msg) {
    var str = document.getElementById("phone").value;

    //look for "Hello"
    var patt = /^([+][1-9]\d{0,2}[ ]?[(][1-9]\d{0,2}[)][ ]?\d{4,32})$|^([+][0-9]{1,3}[ ]\d{4,32})$/g;
    var matchPatern = patt.test(str);
    if (!matchPatern) {
        if (confirm(msg)) {
            return true;
        } else {
            return false;
        }
    }
}

function selectedCheck(element) {
    switch (element.value) {
        case 'ADMINISTRATOR':
            document.getElementById("Company").disabled = true;
            document.getElementById("photo").disabled = true;
            document.getElementById("genere").disabled = true;
            document.getElementById("birthdate").disabled = true;
            document.getElementById("userData").style.display = 'none';
            document.getElementById("companyData").style.display = 'none';
            break;
        case 'USER':
            document.getElementById("Company").disabled = true;
            document.getElementById("photo").disabled = false;
            document.getElementById("genere").disabled = false;
            document.getElementById("birthdate").disabled = false;
            document.getElementById("userData").style.display = 'block';
            document.getElementById("companyData").style.display = 'none';
            break;
        case 'AGENT':
            document.getElementById("Company").disabled = false;
            document.getElementById("photo").disabled = true;
            document.getElementById("genere").disabled = true;
            document.getElementById("birthdate").disabled = true;
            document.getElementById("userData").style.display = 'none';
            document.getElementById("companyData").style.display = 'block';
            break;
        case 'AUDITOR':
            document.getElementById("Company").disabled = false;
            document.getElementById("photo").disabled = true;
            document.getElementById("genere").disabled = true;
            document.getElementById("birthdate").disabled = true;
            document.getElementById("userData").style.display = 'none';
            document.getElementById("companyData").style.display = 'block';
            break;
        default:
            break;
    }

}

function ajaxCheck(element, contextPath) {
    id = element.value;
    url = contextPath + "/jquery/hostCheck.do?id=" + id;
    $.get(url, function (allowed, status) {
        alert(allowed, status);
        document.getElementById("MANAGER").disabled = !allowed;
        document.getElementById("TECHNICIAN").disabled = !allowed;
        document.getElementById("USER").disabled = allowed;
        document.getElementById("RESPONSABLE").disabled = allowed;
    });
}


function copyText(from, to) {
    var valor = from.value;
    document.getElementById(to).value = valor;
}


function ajaxTopicCreate(element, contextPath) {
    var name = element.value;
    var url = contextPath + "/asynchronous/topic/create.do";
    $.ajax({
        type: "POST",
        url: url,
        data: "topicName=" + name,
        success: function (topic) {
            document.getElementById('topic').value = topic;
        },
        error: function (e) {
            alert(url + ': ' + e);
        }
    });
}

function asynchSearch(contextPath, path, target) {
    url = contextPath + path;
    $.ajax({
        type: "POST",
        url: url,
        success: function (msg) {
            document.getElementById(target).innerText = msg;
        },
        error: function (e) {

        }
    });
}

function ajaxSearch(element, contextPath) {
    id = element.value;
    url = contextPath + "/asynchronous/hostCheck.do";
    $.ajax({
        type: "POST",
        url: url,
        data: "id=" + id,
        success: function (allowed) {
            document.getElementById("MANAGER").disabled = !allowed;
            document.getElementById("TECHNICIAN").disabled = !allowed;
            document.getElementById("USER").disabled = allowed;
            document.getElementById("RESPONSABLE").disabled = allowed;
            if (allowed) {
                document.getElementById("authority").value = document
                    .getElementById("TECHNICIAN").value;
            } else {
                document.getElementById("authority").value = document
                    .getElementById("USER").value;
            }
        },
        error: function (e) {
            alert(url);

        }
    });
}

function toggleDisabled(element) {
    document.getElementById(element).disabled = !document
        .getElementById(element).disabled;
}

function toggleVisible(element) {
    var x = document.getElementById(element);
    if (x.style.display === 'none') {
        x.style.display = 'block';
    } else {
        x.style.display = 'none';
    }
}

function setVisibility(element, visibility) {
    var status = 'none';
    if (visibility === true) {
        status = 'block';
    }
    document.getElementById(element).style.display = status;
}

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
    if (mySidebar.style.display === 'block') {
        mySidebar.style.display = 'none';
        overlayBg.style.display = "none";
    } else {
        mySidebar.style.display = 'block';
        overlayBg.style.display = "block";
    }
}

// Close the sidebar with the close button
function w3_close() {
    mySidebar.style.display = "none";
    overlayBg.style.display = "none";
}

// Script for Tabs, Accordions, Progress bars and slideshows -->

// Tabs
function openCity(evt, cityName) {
    var i;
    var x = document.getElementsByClassName("city");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    var activebtn = document.getElementsByClassName("testbtn");
    for (i = 0; i < x.length; i++) {
        activebtn[i].className = activebtn[i].className.replace(
            " w3-dark-grey", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " w3-dark-grey";
}

// Accordions
function myAccFunc(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else {
        x.className = x.className.replace(" w3-show", "");
    }
}

function plusDivs(n) {
    slideIndex = slideIndex + n;
    showDivs(slideIndex);
}

function showDivs(n) {
    var x = document.getElementsByClassName("mySlides");
    if (n > x.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = x.length
    }
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex - 1].style.display = "block";
}

// Progress Bars
function move() {
    var elem = document.getElementById("myBar");
    var width = 5;
    var id = setInterval(frame, 10);

    function frame() {
        if (width == 100) {
            clearInterval(id);
        } else {
            width++;
            elem.style.width = width + '%';
            elem.innerHTML = width * 1 + '%';
        }
    }
}

function askSubmission(msg, form) {
    if (confirm(msg))
        form.submit();
}

function submitForm(form) {
    if (confirm("Si?"))
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

function overEffect(elemento) {
    elemento.classList.toggle("w3-text-black");
}

function setTexTColor(elemento, color) {
    elemento.classList.toggle("w3-text-white");
}

function ellipsis(elemento) {
    elemento.classList.toggle("ellipsis");
}

/*
 * When the user clicks on the button, toggle between hiding and showing the
 * dropdown content
 */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function (event) {
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

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    slideIndex = n;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
}

/* Drag and Drop */

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
}

function myDrop(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    var imageUrl = evt.dataTransfer.getData('text/html');
    var url = $(imageUrl).attr("src");
    var elemento = ('<div class="mySlides"><img src="' + url + '" class="w3-border w3-card-4 marco" style="width: 100%"></div>');
    var imagen = '<img src="' + url + '" class="tableImg">';

    if (document.getElementById("fotosPath").innerHTML.length > 0) {
        if (!document.getElementById("fotosPath").innerHTML.includes(url)) {
            document.getElementById("fotosPath").innerHTML = document
                .getElementById("fotosPath").innerHTML.concat(", " + url);
            $(document.getElementById("carrusel")).append(elemento);
            $(document.getElementById("fotos")).append(imagen);
            var slides = document.getElementsByClassName("mySlides");
            var punto = ('<span class="dot" onclick="currentSlide('
                + slides.length + ')"></span>');
            $(document.getElementById("punto")).append(punto);

        }
    } else {
        document.getElementById("fotosPath").innerHTML = document
            .getElementById("fotosPath").innerHTML.concat(url);
        $(document.getElementById("carrusel")).append(elemento);
        $(document.getElementById("fotos")).append(imagen);
        var slides = document.getElementsByClassName("mySlides");
        var punto = ('<span class="dot" onclick="currentSlide(' + slides.length + ')"></span>');
        $(document.getElementById("punto")).append(punto);

    }

    showSlides(-1);
}

function checkPassword() {

    if (document.getElementById('password').value.length > 4
        && document.getElementById('password').value.length < 33) {
        document.getElementById('password').style.color = 'green';
    } else {
        document.getElementById('password').style.color = 'red';
    }

    if (document.getElementById('password').value == document
        .getElementById('confirm_password').value) {
        document.getElementById('confirm_password').style.color = 'green';
    } else {
        document.getElementById('confirm_password').style.color = 'red';
    }
}

function checkEdition() {
    var enabled = false;
    var newPassword = document.getElementById('new_password');
    var confirmPassword = document.getElementById('confirm_password');

    if (document.getElementById('password').value.length != 0
        && newPassword.value.length > 4 && newPassword.value.length < 33) {
        newPassword.style.color = 'green';
        if (newPassword.value == confirmPassword.value) {
            confirmPassword.style.color = 'green';
            enabled = true;
        } else
            confirmPassword.style.color = 'red';
    } else
        newPassword.style.color = 'red';

}

function showUserAccount() {

    var changedPassword = document.getElementById("changePassword");
    var photoCard = document.getElementById("photoCard");
    if (changedPassword.style.display == "block") {
        changedPassword.style.display = "none";
        photoCard.style.display = "block";
        document.getElementById("save").className = "formButton toLeft";
    } else {
        changedPassword.style.display = "block";
        photoCard.style.display = "none";
        checkEdition();
    }
}

function showMsg() {
    alert("alert!");

}

function showConfirmationAlert(element, msg, url) {
    var fullMsg = element + " " + msg;
    if (confirm(fullMsg)) {
        relativeRedir(url);
    }
}

function showConditionalAlert(msg, id, url) {

    if (id != '0') {

        relativeRedir(url);
    } else {
        if (confirm(msg)) {
            relativeRedir(url);
        }
    }

}

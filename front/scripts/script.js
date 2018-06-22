function checkInput() {

    var ADMIN = "Admin";
    var ADMIN_HREF = "menus/menu-admin.html";

    var MENTOR = "Mentor";
    var MENTOR_HREF = "menus/menu-mentor.html";

    var STUDENT = "Student";
    var STUDENT_HREF = "menus/menu-student.html";

    var input = document.getElementById("login").value;
    
    if (input == ADMIN) {
        document.getElementById("form").action = ADMIN_HREF;
    } else if (input == MENTOR) {
        document.getElementById("form").action = MENTOR_HREF;
    } else if (input == STUDENT) {
        document.getElementById("form").action = STUDENT_HREF;
    }
}
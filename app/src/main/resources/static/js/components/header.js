    if (window.location.pathname.endsWith("/")) {
      localStorage.removeItem("userRole");
      localStorage.removeItem("token");
    }

    const role = localStorage.getItem("userRole");
    const token = localStorage.getItem("token");

    if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
      localStorage.removeItem("userRole");
      alert("Session expired or invalid login. Please log in again.");
      window.location.href = "/";
      return;
    }

    if (role === "admin") {
      headerContent += `
        <button id="addDocBtn" class="adminBtn" onclick="openModal('addDoctor')">Add Doctor</button>
        <a href="#" onclick="logout()">Logout</a>`;
    }else if (role === "doctor") {
      headerContent += `
        <a href="/doctor/dashboard" class="nav-link">Dashboard</a>
        <a href="#" onclick="logout()">Logout</a>`;
    } else if (role === "loggedPatient") {
      headerContent += `
        <a href="/patient/dashboard" class="nav-link">Dashboard</a>
        <a href="#" onclick="logout()">Logout</a>`;
    } else if (role === "patient") {
      headerContent += `
        <a href="/receptionist/dashboard" class="nav-link">Dashboard</a>
        <a href="#" onclick="logout()">Logout</a>`;
    }

    /*
Start with an empty string: headerContent = ""
If role is admin  
  Add HTML string for "Add Doctor" button and Logout link
If role is doctor  
  Add "Home" button and Logout
If role is patient  
  Add Login and Signup buttons
If role is loggedPatient  
  Add Home, Appointments, and Logout

    */

   headerDiv.innerHTML = headerContent;
    attachHeaderButtonListeners();

/*
Attach Event Listeners because elements were dynamically created, you need to attach listeners after insertion.

Use document.getElementById(“someBtnId”)
Check if the element exists (in case the button is not for all roles)
Use .addEventListener(“click”, …) to attach a handler.

HINT :

1 After rendering the header
2 Find buttons by ID
3 Attach 'click' event listeners (e.g. to open a modal or clear storage)
 
Implementing Logout Functionality for clearing the session and going back to the start

- Remove both token and userRole from localStorage.
- Redirect to homepage using window.location.href = "/".
- For patient we can retain their “role” as just patient, not loggedPatient, to show login/signup again.

HINT:

1 Create a function called logout  
2 Inside it, remove token and userRole
3 Redirect to homepage
4 
5 Create logoutPatient function
6 Remove token
7 Set role back to "patient"
8 Redirect to patient dashboard

*/
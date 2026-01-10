Creating Dashboard
1. adminDashboard.js - Managing Doctors
Open the JS File:
Open adminDashboard.js located at app/src/main/resources/static/js/adminDashboard.js.
 Open adminDashboard.js in IDE

Import Required Modules : At the top of the file, import:
The openModal function from the modal component file ../components/modals.js.
The getDoctors , filterDoctors , saveDoctor functions from the doctorServices component file ./services/doctorServices.js.
The createDoctorCard function from the doctorCard component file ./components/doctorCard.js.
Event Binding :

When the admin clicks the “Add Doctor” button, it triggers openModal(‘addDoctor’)
1
2
3
    document.getElementById('addDocBtn').addEventListener('click', () => {
 openModal('addDoctor');
});

Copied!

Wrap Toggled!
Load Doctor Cards on Page Load:

Use DOMContentLoaded or window.onload to trigger loadDoctorCards() on page load.
loadDoctorCards(): Fetch all doctors and display them in the dashboard.
Calls getDoctors() to fetch doctor list from backend.
Clears existing content (innerHTML = "").
1
2
 const contentDiv = document.getElementById("content");
    contentDiv.innerHTML = ""; 

Copied!

Wrap Toggled!
Iterates through results and injects them using createDoctorCard() of './components/doctorCard.js'.
Appends each card to the contentDiv.
Implement Search and Filter Logic:

Set up event listeners on:
#searchBar
Filter dropdowns
1
2
3
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

Copied!

Wrap Toggled!
Create filterDoctorsOnChange() function:
Gathers current filter/search values
Fetches and displays filtered results using filterDoctors() from './services/doctorServices.js'.
If no match, show message “No doctors found”.
renderDoctorCards(doctors) : Utility function to render doctor cards when passed a list.

It iterates through the list passed doctors and display the cards.
Handle Add Doctor Modal:

When the “Add Doctor” button is clicked:
openModal() opens the modal
The modal form is populated with input fields for:
Name, specialty, email, password, mobile no., availability time.
Collects any checkbox values for doctor availability
On form submission:
Use adminAddDoctor() to collect data.
Verifies that a valid login token exists (to authenticate the admin).
Send a POST request using saveDoctor() from './services/doctorServices.js'
If successful, closes the modal, reloads the page or doctor list, and shows a success message and refresh the doctor list.
If failed, alerts the user with an error message.
Notes
Ensure the openModal() function is imported from ./components/modals.js.
Ensure the createDoctorCard function is imported from ./components/doctorCard.js.
Ensure the getDoctors , filterDoctors , saveDoctor function is imported from ./services/doctorServices.js'.
Use async/await for Api Call functions.
2. doctorDashboard.js – Managing Appointments
Open the JS File:
Open doctorDashboard.js located at app/src/main/resources/static/js/doctorDashboard.js.
 Open doctorDashboard.js in IDE

Import Required Modules : At the top of the file, import:
The  getAllAppointments function from the modal component file ./services/appointmentRecordService.js.
The createPatientRow  function from the doctorCard component file ./components/patientRows.js.
Initialize Global Variables:

Define and store:

A reference to the appointment table body where rows will be rendered (#patientTableBody).
selectedDate, initialized to today’s date.
token, retrieved from localStorage (used for authentication).
patientName, initialized as null, for search filtering.
Setup Search Bar Functionality:

Add an event listener to the search bar (#searchBar):

On input change, update the patientName variable.
If the search input is empty, default patientName to "null".
Call loadAppointments() to refresh the list with the filtered data.
Bind Event Listeners to Filter Controls:

“Today’s Appointments” button (#todayButton):

Resets the selectedDate to today.
Updates the date picker field to reflect today’s date.
Calls loadAppointments().
Date picker (#datePicker):

Updates the selectedDate variable when changed.
Calls loadAppointments() to fetch and display appointments for the selected date.
Define loadAppointments() Function:

This function:

Uses getAllAppointments(selectedDate, patientName, token) to fetch appointment data.

Clears existing content in the table.

If no appointments are found:

Displays a row with a “No Appointments found for today” message.
If appointments exist:

For each appointment, extract the patient’s details.
Use createPatientRow() to create a <tr> for each.
Append each row to the appointment table body.
Wrap this logic in a try-catch block:

In case of error, display a fallback error message row in the table.
Initial Render on Page Load:

When the page is loaded (DOMContentLoaded):

Call renderContent() (if used).
Call loadAppointments() to load today’s appointments by default.
Notes
The getAllAppointments() function is responsible for backend API calls based on the selected date and search term.
The createPatientRow() component is used to dynamically build each row of the appointments table.
All API calls should include the doctor’s token for authentication (retrieved from localStorage).
Always use async/await syntax when working with fetch() to ensure proper flow control and error handling.
Ensure meaningful fallback messages are shown if no appointments are found or if the API fails.
3. patientDashboard.js – Viewing & Filtering Doctors
Open the JS File:
Open patientDashboard.js located at: app/src/main/resources/static/js/patientDashboard.js
 Open patientDashboard.js in IDE

Import Required Modules
At the top of the file, import:

createDoctorCard from ./components/doctorCard.js – for rendering doctor information cards.
openModal from ./components/modals.js – for opening login/signup modals.
getDoctors, filterDoctors from ./services/doctorServices.js – for retrieving doctor data.
patientLogin, patientSignup from ./services/patientServices.js – for handling patient authentication.
Load Doctor Cards on Page Load
When the page loads:

Call loadDoctorCards() inside a DOMContentLoaded event listener.

This function:

Calls getDoctors() to fetch the list of all available doctors.
Clears any existing content inside the #content div.
Iterates over the results and renders each doctor using createDoctorCard().
Appends each card to the #content section.
1
2
3
document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();
});

Copied!

Wrap Toggled!
Bind Modal Triggers for Login and Signup
Add event listeners on:

The Signup button (#patientSignup) → opens the patientSignup modal.
The Login button (#patientLogin) → opens the patientLogin modal.
1
2
3
4
5
6
7
8
9
document.addEventListener("DOMContentLoaded", () => {
  const btn = document.getElementById("patientSignup");
  if (btn) btn.addEventListener("click", () => openModal("patientSignup"));
});
document.addEventListener("DOMContentLoaded", () => {
  const loginBtn = document.getElementById("patientLogin");
  if (loginBtn) loginBtn.addEventListener("click", () => openModal("patientLogin"));
});

Copied!

Wrap Toggled!
Search and Filter Logic
Set up listeners for:

Search bar (#searchBar)
Availability time filter (#filterTime)
Specialty filter (#filterSpecialty)
Each change triggers filterDoctorsOnChange().

1
2
3
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

Copied!

Wrap Toggled!
filterDoctorsOnChange() Function:
Gathers values from all three filter/search inputs.
Uses filterDoctors(name, time, specialty) to fetch filtered results.
Clears the existing content.
If doctors are found, renders them using createDoctorCard().
If not, displays a fallback message.
1
2
3
4
5
6
7
function filterDoctorsOnChange() {
  ...
  filterDoctors(name, time, specialty).then(response => {
    ...
    contentDiv.innerHTML = doctors.length > 0 ? renderedCards : "<p>No doctors found with the given filters.</p>";
  });
}

Copied!

Wrap Toggled!
Render Utility
The renderDoctorCards(doctors) function is available for rendering a given list of doctors dynamically, used optionally by other modules.

Handle Patient Signup
The signupPatient() function is triggered on form submission:

Collects user inputs (name, email, password, phone, address).

Sends the data to the backend via patientSignup().

On success:

Shows an alert with a success message.
Closes the modal and reloads the page.
On failure: Shows an error message.

1
2
3
window.signupPatient = async function () {
  ...
};

Copied!

Wrap Toggled!
Handle Patient Login
The loginPatient() function is triggered on login form submission:

Captures login credentials (email, password).

Calls patientLogin() to authenticate.

On success:

Stores JWT token in localStorage.
Redirects user to loggedPatientDashboard.html.
On failure:

Shows error alert.
1
2
3
window.loginPatient = async function () {
  ...
};

Copied!

Wrap Toggled!
Notes
Ensure all modal IDs (patientSignup, patientLogin) are properly assigned in HTML.
All doctor fetching functions (getDoctors, filterDoctors) should be async/await-based for clean flow and error handling.
Use createDoctorCard() to maintain consistent UI design across dashboards.
Use fallback messages for empty search/filter results or API errors.
Save your work!
Before you finish this lab, make sure to add, commit, and push your updated code to your GitHub repository. You will be asked to provide your public GitHub repo URL for graded evaluation at the end of the capstone.

Follow these steps to push your changes:

Stage your changes:

1
git add .

Copied!

Wrap Toggled!

Executed!
Commit your changes with a meaningful message:

1
git commit -m "Completed Developing Services and Utilities"

Copied!

Wrap Toggled!

Executed!
Push your changes using your GitHub Personal Access Token:

1
git push https://<your_github_username>:<your_personal_access_token>@github.com/<your_github_username>/java-database-capstone.git

Copied!

Wrap Toggled!

Executed!
Replace <your_github_username> with your GitHub username.
Replace <your_personal_access_token> with the token you generated at the start of the lab.
Example
If your GitHub username is johnsmith and your token is ghp_ABC123xyzTOKEN, your push command would look like:

1
git push https://johnsmith:ghp_ABC123xyzTOKEN@github.com/johnsmith/java-database-capstone.git

Copied!

Wrap Toggled!

Executed!
Remember:

Always push your work after completing each lab.
Save your GitHub URL — you will need to submit it for your final project evaluation.




1
Lab: Developing Services and Utilities

2
Services (API/Logic Handlers)

3
Creating Service Modules

4
Creating Dashboard

5
Conclusion and Next Steps
Conclusion and Next Steps
Structure the HTML to support dynamic data rendering.
Use modular JavaScript to fetch, render, and filter patient records.
Apply clean and responsive CSS to support both usability and aesthetics.
Handle real-time filtering and data updates based on date selection or user input.
Maintain separation of concerns by keeping services, rendering, and layout logic in separate files.
Next Steps
Extend Filters: Add dropdowns or checkboxes to filter patients by status ("Consulted" and "pending").
Error Handling: Display user-friendly messages when API calls fail or return unexpected data.
Mobile Optimization: Use media queries to fine-tune layouts for phones and tablets.
Add pagination or infinite scroll to handle large patient lists.
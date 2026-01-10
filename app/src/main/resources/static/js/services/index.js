import {openModal} from "../components/modals.js";

const ADMIN_API = API_BASE_URL + '/admin';
const DOCTOR_API = API_BASE_URL + '/doctor/login'

/*

Interact with backend APIs and improve code organization.

index.js - Role-Based Login Handling
Open the index.js file app/src/main/resources/static/js/services/index.js
 Open index.js in IDE

Import Required Modules : At the top of the file, import:
The openModal function from the modal component file ../components/modals.js.

The API_BASE_URL constant from your configuration file ../config/config.js.

Then, define two constants:

One for the Admin login endpoint (append /admin).


const ADMIN_API = API_BASE_URL + '/admin';

One for the Doctor login endpoint (append /doctor/login).
const DOCTOR_API = API_BASE_URL + '/doctor/login'

Setup Button Event Listeners : Use window.onload to ensure the script runs after the page is fully loaded.
Inside it:
Select the Admin and Doctor login buttons by their id attributes.
Attach click event listeners to these buttons.

window.onload = function () {
          const adminBtn = document.getElementById('adminLogin');
       if (adminBtn) {
        adminBtn.addEventListener('click', () => {
          openModal('adminLogin');
        });
          }     

When clicked:
The Admin button should open the Admin login modal.
The Doctor button should open the Doctor login modal.
Implement Admin Login Handler : Define an asynchronous function named adminLoginHandler and make it accessible globally.

Inside this function:

Read the values entered for username and password.

Create an admin object containing these credentials.

 const admin = { username, password };

Use the fetch() function to make a POST request to the Admin login API.

Example

1
fetch(YOUR_API_URL, {
   method: 'POST',
   headers: { 'Content-Type': 'application/json' },
   body: JSON.stringify(data)
 });

In the request:

Set the method to POST.
Set headers to indicate JSON content.
Send the admin object as a JSON string in the body.

After receiving the response:
 - If it's successful:
   - Extract the response JSON.
   - Store the received token in `localStorage`.
   - Call a helper function `selectRole()` with `"admin"` to save the selected role.
 - If it fails, display an alert saying "Invalid credentials!".
Wrap the request in a `try-catch` block to catch and alert any unexpected errors.

Copied!

Wrap Toggled!
Implement Doctor Login Handler : Similarly, define an asynchronous function named doctorLoginHandler and make it globally accessible.

Inside this function:
Read the email and password values entered by the user.
Create a doctor object with these values.
Send a POST request to the Doctor login endpoint using fetch().
Follow the same process as the Admin handler:
On success: Store the received token in localStorage, call a helper function selectRole() with "doctor" to save the selected role.
On failure: alert the user about invalid credentials.

Handle unexpected issues using try-catch.

This script enables:
    Role-based login selection via buttons.
    Modal interaction to enter credentials.
    Server communication for authentication.
    Local storage of user roles and tokens for later use.
    selectRole() function of render.js setRole in the localStorage and helps in rendering of pages.

This file should be included at the end of the body tag in your index.html using:

<script type="module" src="js/services/index.js" defer></script>

Notes:
Make sure the modal component (openModal) is properly defined in js/components/modals.js.
Ensure that API_BASE_URL is correctly set in config.js.

*/

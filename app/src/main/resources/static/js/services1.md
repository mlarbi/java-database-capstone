

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


Creating Service Modules
1. doctorServices.js
This service module is responsible for handling all API interactions related to doctor data. Instead of writing fetch() calls directly inside your dashboard pages, we organize them here for modularity, reusability, and cleaner code separation.

Open the doctorServices.js file app/src/main/resources/static/js/services/doctorServices.js

 Open doctorServices.js in IDE

Import API Base URL
Start by importing the API base URL from your configuration file ../config/config.js.

1
import { API_BASE_URL } from "../config/config.js";

Copied!

Wrap Toggled!
This ensures you’re not hardcoding URLs in multiple files — you centralize it in one place

Set Doctor API Endpoint
Define a constant for the doctor-related base endpoint.

1
const DOCTOR_API = API_BASE_URL + '/doctor'

Copied!

Wrap Toggled!
Create a Function to Get All Doctors
Build a function getDoctors() that:

Sends a GET request to the doctor endpoint.
Awaits a response from the server.
Extracts and returns the list of doctors from the response JSON.
Handles any errors using a try-catch block.
Returns an empty list ([]) if something goes wrong to avoid breaking the frontend.
This is the function your Admin dashboard and Patient dashboard will use to load all doctors and display them on the page.

Create a Function to Delete a Doctor
Build a function deleteDoctor(id, token) that:

Takes the doctor’s unique id and an authentication token (for security).
Constructs the full endpoint URL using the ID and token.
Sends a DELETE request to that endpoint.
Parses the JSON response and returns a success status and message.
Catches and handles any errors to prevent frontend crashes.
This allows an authenticated Admin to remove doctors from the system securely.

Create a Function to Save (Add) a New Doctor
This function saveDoctor(doctor , token) should:

Accept a doctor object containing all doctor details (like name, email, availability).
Also take in a token for Admin authentication.
Send a POST request with headers specifying JSON data.
Include the doctor data in the request body (converted to JSON).
Return a structured response with success and message.
Catch and log any errors to help during debugging.
It powers the “Add Doctor” modal in the Admin dashboard and saves new doctor records in the database.

Create a Function to Filter Doctors
This function filterDoctors(name ,time ,specialty):

Accepts parameters like name, time, and specialty.
Constructs a GET request URL by passing these values as route parameters.
Sends a GET request to retrieve matching doctor records.
Returns the filtered list of doctors (or an empty list if none are found).
Handles cases where no filters are applied (pass null or empty strings appropriately).
Uses error handling to alert the user if something fails.
This supports real-time search and filter features in the Admin dashboard.

Task
Always use async/await for fetch calls for better readability.
Use try-catch to gracefully handle network or server errors.
Never hardcode API URLs; instead, use a base config file.
Return consistent data formats (e.g., { success, message }) to simplify frontend logic.
Keep your service layer focused on communication logic, not UI logic.
By organizing all your doctor-related API functions in doctorServices.js, you’re:

Making your code more modular and maintainable
Avoiding repetition of fetch logic
Separating responsibilities between UI and backend logic
Preparing your app to scale with ease
2. patientServices.js
This module centralizes all API communication related to patient data. It handles sign-up, login, appointment management. Keeping this logic separated from UI code improves reusability and maintainability.

Open the patientServices.js file app/src/main/resources/static/js/services/patientServices.js

 Open patientServices.js in IDE

Import the API Base URL
Start by importing the base URL from your configuration file.

1
import { API_BASE_URL } from "../config/config.js";

Copied!

Wrap Toggled!
You’ll use this base to construct specific endpoint URLs for patient-related actions.

Set the Base Patient API Endpoint
Define a constant (e.g., PATIENT_API) that represents the base path for all patient-related requests — typically /api/patient.

1
const PATIENT_API = API_BASE_URL + '/patient'

Copied!

Wrap Toggled!
This avoids duplicating the path in multiple places and makes future updates easier.

Create a Function to Handle Patient Signup
This function patientSignup(data) will:

Accept a data object with patient details (name, email, password, etc.).
Send a POST request to the signup endpoint.
Include the patient details as JSON in the request body.
Wait for the response, extract the message, and return a structured object with success and message properties.
Handle any failures with a try-catch block and return an appropriate error message.
This function allows users to register as a patient through your frontend app.

Create a Function for Patient Login
This function patientLogin(data):

Accepts login credentials (typically email and password).
Sends a POST request to the login endpoint.
Includes headers indicating JSON content and passes the login data in the body.
Returns the full fetch response so the frontend can check status, extract token, etc.
Logging the input data can help during development (but should be removed in production).
Used during login to authenticate patients and allow secure access to dashboards or features.

Create a Function to Fetch Logged-in Patient Data
This function getPatientData(token):

Takes an authentication token (from localStorage).
Sends a GET request using this token to retrieve the patient’s details (name, id, etc.).
Returns the patient object if successful.
Handles any errors gracefully and returns null if the request fails.
Used when booking appointments or viewing patient profile information.

Create a Function to Fetch Patient Appointments
This function getPatientAppointments(id, token ,user) is a bit more dynamic:

Accepts three parameters:

id: Patient’s unique identifier
token: Authentication token
user: String indicating who’s requesting (e.g., "patient" or "doctor")
Constructs a dynamic API URL that works for both dashboards — doctor and patient.

Sends a GET request and returns the appointments array.

If unsuccessful, logs the error and returns null.

A single, shared API call supports both dashboards with role-based behavior on the backend.

Create a Function to Filter Appointments
This function filterAppointments(condition ,name ,token):

Accepts condition (like "pending" or "consulted"), name, and a token.
Sends a GET request to a filtered endpoint.
Returns the list of filtered appointments if the request is successful.
Returns an empty list if something fails, and logs errors for easier debugging.
Alerts the user if the error is unexpected.
Helps in real-time filtering and searching of appointments, improving the user experience.

Task
Use clear function names (patientSignup, getPatientAppointments) that reflect the purpose.
Wrap all async code in try-catch to handle API or network errors.
Return structured, consistent outputs from service functions (e.g., { success, message }).
Avoid repeating base URLs; build them from a central config.js.
Use comments inside each function to indicate what step is happening — especially useful for collaboration or learning teams.
By organizing all patient-related API communication in patientServices.js, you:

Keep your UI code (like in dashboards) cleaner and easier to read.
Make the app easier to debug, extend, and maintain.
Enable code reusability across different user roles.
Reduce the risk of introducing bugs by avoiding repeated logic.


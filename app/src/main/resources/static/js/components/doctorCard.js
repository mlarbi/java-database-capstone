/*
Define the Function
1
export function createDoctorCard(doctor) 


Create a named export so other files can import and use this function.
Accept one argument: a doctor object containing info like name, specialty, etc.
Create the Main Card Container

Dynamically create a <div> element.
Add a CSS class doctor-card for styling purposes.

Fetch the User’s Role
const role = localStorage.getItem("userRole");

Read the current user’s role (admin, patient, loggedPatient) from localStorage.
You’ll use this later to decide which buttons to show.
Create Doctor Info Section

const infoDiv = document.createElement("div");
infoDiv.classList.add("doctor-info");

Make a nested container to hold the doctor’s name, specialty, email, and availability.

Then add individual elements:

1
2
const name = document.createElement("h3");
name.textContent = doctor.name;

Copied!

Wrap Toggled!
Create a heading element and set the text to the doctor’s name.
Repeat similarly for:

specialization
email
availability (you can join an array with join(", ") to display multiple times)
Then append them all:

infoDiv.appendChild(name);
infoDiv.appendChild(specialization);
infoDiv.appendChild(email);
infoDiv.appendChild(availability);

Create Button Container

const actionsDiv = document.createElement("div");
actionsDiv.classList.add("card-actions");

A new <div> to hold buttons like “Delete” or “Book Now”.
Conditionally Add Buttons Based on Role


Admin:
if (role === "admin") {
  const removeBtn = document.createElement("button");
  removeBtn.textContent = "Delete";

Add a delete button only for admins.
Attach an event:


removeBtn.addEventListener("click", async () => {
  // 1. Confirm deletion
  // 2. Get token from localStorage
  // 3. Call API to delete
  // 4. On success: remove the card from the DOM
});


Patient (not logged in):

else if (role === "patient") {
  const bookNow = document.createElement("button");
  bookNow.textContent = "Book Now";
  bookNow.addEventListener("click", () => {
    alert("Patient needs to login first.");
  });
}


Shows a button but alerts the user that login is required.
Logged-in Patient:

else if (role === "loggedPatient") {
  const bookNow = document.createElement("button");
  bookNow.textContent = "Book Now";
  bookNow.addEventListener("click", async (e) => {
    const token = localStorage.getItem("token");
    const patientData = await getPatientData(token);
    showBookingOverlay(e, doctor, patientData);
  });
}


Allows real booking by calling a function from another module.
Fetches patient data first

- Final Assembly
Add all the created pieces to the card:

card.appendChild(infoDiv);
card.appendChild(actionsDiv);


Return the final card:

return card;

Notes:
This component uses helper functions imported from service files that will be implemented in the next lab:

deleteDoctor() from: /js/services/doctorServices.js
getPatientData() from: /js/services/patientServices.js

These service modules will handle API interactions and are part of the modular architecture designed for better maintainability and code reuse.
*/

export function createDoctorCard(doctor) {
    const card = document.createElement("div");
    card.classList.add("doctor-card");

    const role = localStorage.getItem("userRole"); 

    const infoDiv = document.createElement("div");
    infoDiv.classList.add("doctor-info");

    const name = document.createElement("h3");
    name.textContent = doctor.name;

    infoDiv.appendChild(name);
    infoDiv.appendChild(specialization);
    infoDiv.appendChild(email);
    infoDiv.appendChild(availability);

    const actionsDiv = document.createElement("div");
    actionsDiv.classList.add("card-actions");

    if (role === "admin") {
        const removeBtn = document.createElement("button");
        removeBtn.textContent = "Delete";

        removeBtn.addEventListener("click", async () => {
        // 1. Confirm deletion
        // 2. Get token from localStorage
        // 3. Call API to delete
        // 4. On success: remove the card from the DOM
        });        
    } else if (role === "patient") {
        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";
        bookNow.addEventListener("click", () => {
            alert("Patient needs to login first.");
        });
    } else if (role === "loggedPatient") {
        const bookNow = document.createElement("button");
        bookNow.textContent = "Book Now";
        bookNow.addEventListener("click", async (e) => {
            const token = localStorage.getItem("token");
            const patientData = await getPatientData(token);
            showBookingOverlay(e, doctor, patientData);
        });
    }  
    
    card.appendChild(infoDiv);
    card.appendChild(actionsDiv);

    return card
}


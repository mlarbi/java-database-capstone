
# Admin User Stories

## **Log into the portal with your username and password to manage the platform securely:**
As an Admin, I want to into the portal with my username and password, so that I can administer the system securely.

**Acceptance Criteria:**
1. Login should succeed with the correct username and password
2. Login should fail with either the wrong username or password
3. Password should be masked when typed in and encrypted 

**Priority:** High

**Story Points:** 8

**Notes:**
-  

## **Log out of the portal to protect system access**
As an Admin, I want to log out of the portal, to prevent unauthorized user access under my account when I am not using the portal.

**Acceptance Criteria:**
1. A visible button on the portal, which should log user out when clicked.
2. Once logged out, user should not be able to use the portal without a fresh login
3. A confirmation message should be displayed on logout.

**Priority:** High

**Story Points:** 1

**Notes:**
- 

## **Add doctors to the portal**
As an Admin, I want to add doctors to the portal, so the added doctors can do their work on the portal

**Acceptance Criteria:**
1. A menu item should be available to admins for adding doctors
2. A form must be on the portal that captures the important details  (first name, last name, email, phone number, address)
3. The data entered must be validated - to ensure required fields are provided
4. Required fields are first name, last name, email, phone number, address, specialty
5. An added doctor should appear in the list of doctors on the portal on searching

**Priority:** High

**Story Points:** 8

**Notes:**


## **Delete doctor's profile from the portal**
As an Admin, I want to delete a doctor from the portal, so the doctor no longer has access when their employment ends.

**Acceptance Criteria:**
1. Be able to find and display the the doctor's profile on the portal through simple search, prior to deletion
2. Delete the doctor by clicking a "Delete" button on their profile
3. Once deleted, a doctor should no longer be found on the portal when searched for.

**Priority:** High

**Story Points:** 3

**Notes:**
- 

## **Run a stored procedure in MySQL CLI to get the number of appointments per month and track usage statistics**
As an Admin, I want to be able to run a specifically named stored procedure in the MySQL CLI, so I can report to management on the number of appointments per month and track usage statistics.
**Acceptance Criteria:**
1. A named parameterized stored procedure that I can run on the MySQL CLI
2. The output of the stored procedure should be tabular for ease of reporting
3. Output should show number of appointments per month over the previous 12 months

**Priority:** High

**Story Points:** 5

**Notes:**
- 

# Patient User Stories

## **View a list of doctors without logging in to explore options before registering**
As a Patient, I want to anonymously (without login) view a list of doctors and their specialties on the portal, to help me decide on whether to go to the clinic for care.

**Acceptance Criteria:**
1. Display of list of doctors on the portal by anonyous users (not logged in)
2. Basic profile of each doctor should be shown - name and specialty

**Priority:** High

**Story Points:** 3
**Notes:**
- 

## **Sign up using your email and password to book appointments**
As a Patient, I want to sign up on the portal using my email and a password, to book appointments at the clinic.

**Acceptance Criteria:**
1. Signup menu item should be available in the navigation menu
2. Signup menu item when clicked should bring up a sign up form
3. Sign up form should capture minimal information - email and password

**Priority:** High
**Story Points:** 5
**Notes:**
- Capture name and phone number as well since that would be required for appointments

## **Log into the portal to manage your bookings**
As a Patient, I want to log into the portal, so I can management my appointment bookings

**Acceptance Criteria:**
1. Login menu item should be available on the navigation menu
2. Login menu item should bring up login form
4. Login should succeed if correct email and password provided
5. Login should fail if the wrong email or password provided
6. Password should be masked when typed in and encrypted 
7. On successful login, show appointments page 

**Priority:** High
**Story Points:** 13
**Notes:**
- Login form should have forgotten password button, to enable password reset if forgotten
- Login form should have Sign up button in case user has not yet signed up.

## **Log out of the portal to secure your account**
As a Patient, I want to log out of the portal, to protect my data on the portal and prevent unauthorized access.

**Acceptance Criteria:**
1. A visible button on the portal, which should log me out when clicked.
2. Once logged out, I should not be able to use the portal without a fresh login
3. A confirmation message should be displayed on logout.

**Priority:** High

**Story Points:** 1

**Notes:**
- 

## **Log in and book an hour-long appointment to consult with a doctor**
As a Patient, I want to log in and book an hour-long appointment, so I can consult with a doctor

**Acceptance Criteria:**
1. On successful login, patient should see their appointments page.
2. The appointments page should have a link/button to click to create a new appointment
3. The new appointment form should enable the patient to select a date and a time slot as well as the doctor they want to consult

**Priority:** High

**Story Points:** 13

**Notes:**
- Login form should have forgotten password button, to enable password reset if forgotten
- Login form should have Sign up button in case user has not yet signed up.

## **View my upcoming appointments so that I can prepare accordingly**
As a Patient, I want to view my upcoming appointments, so that I can prepare accordingly

**Acceptance Criteria:**
1. On successful login, patient should see their appointments page.
2. The appointments page should have a list of all the patient's appointments
3. On clicking an item in the appointment list, the detail of the appointment should displayed

**Priority:** High

**Story Points:** 5

**Notes:**
- Login form should have forgotten password button, to enable password reset if forgotten
- Login form should have Sign up button in case user has not yet signed up.

# Doctor User Stories

## **Log into the portal to manage your appointments**
As a Doctor, I want to view my upcoming appointments, so that I can review and manage my appointments

**Acceptance Criteria:**
1. On successful login, doctor should see their appointments page.
2. The appointments page should have a list of all the doctor's appointments
3. On clicking an item in the appointment list, the detail of the appointment should displayed
4. The doctor should be able to add status information to an appointment

**Priority:** High

**Story Points:** 8

**Notes:**
- Login form should have forgotten password button, to enable password reset if forgotten

## **Log out of the portal to protect my data**
As a Doctor, I want to log out of the portal, to protect my data on the portal and prevent unauthorized access.

**Acceptance Criteria:**
1. A visible button on the portal, which should log me out when clicked.
2. Once logged out, I should not be able to use the portal without a fresh login
3. A confirmation message should be displayed on logout.

**Priority:** High

**Story Points:** 1

**Notes:**
- 

## **View my appointment calendar to stay organized**
As a Doctor, I want to view my appointment calendar, so that I can stay organized to fulfil my appointments.

**Acceptance Criteria:**
1. On successful login, doctor should see their appointments page.
2. The appointments page should have a list of all the doctor's appointments
3. On clicking an item in the appointment list, the detail of the appointment should displayed

**Priority:** High

**Story Points:** 5

**Notes:**
- Viewing appointment calender required prior login

## **Mark your unavailability to inform patients only the available slots**
As a Doctor, I want to mark my unavailability on my calendar, so patients are informed of only available slots and book accordingly.

**Acceptance Criteria:**
1. On successful login, doctor should see their appointments calendar.
2. A doctor should be able to select a date and time slot on their calendar and block that slot as unavailable for appointments.
3. On the portal, anonymous or authenticated view of the doctor's profile and calendar should show the blocked slots as unavailable for appointment booking.

**Priority:** High

**Story Points:** 8

**Notes:**

## **Update your profile with specialization and contact information so that patients have up-to-date information**
As a Doctor, I want to update my profile on the portal with specialization and contact information, so that patients have up to date information.

**Acceptance Criteria:**
1. On successful login, doctor should see their appointments page.
2. The appointments page should have a list of all the doctor's appointments
3. On clicking an item in the appointment list, the detail of the appointment should displayed
4. The doctor should be able to add status information to an appointment

**Priority:** Medium

**Story Points:** 8

**Notes:**

## **View the patient details for upcoming appointments so that I can be prepared**
As a Doctor, I want to view patient details for upcoming appointments, so that I can be well prepared for appointments

**Acceptance Criteria:**
1. On successful login, doctor should see their appointments page.
2. The appointments page should have a list of all the doctor's appointments
3. On clicking an item in the appointment list, the detail of the appointment should displayed
4. The doctor should be able to click the patient name and bring up the profile and medical details/history of the patient

**Priority:** High

**Story Points:** 8

**Notes:**

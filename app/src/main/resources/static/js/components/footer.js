/*

Create the Function

1
function renderFooter() {

Copied!

Wrap Toggled!
Define a reusable function named renderFooter.
Call this on every page that needs a footer.
Access the Footer Container.

1
const footer = document.getElementById("footer");

Copied!

Wrap Toggled!
This locates the DOM element <div> with id=”footer” where we want to inject our content.
Make sure each HTML page has this container present.
Inject HTML Content

1
 footer.innerHTML = `...`;

Copied!

Wrap Toggled!
Replace the contents of the footer container with an HTML template string.
This block includes branding, navigation, and legal info.
You write regular HTML tags (<footer>, <div>, <h4>,<a>) as part of the string.
- Top-level container: <footer class="footer"> wraps the whole thing.
- Branding section:
js <div class="footer-logo"> <img src="..." /> <p>© Copyright ...</p> </div> 
- Link sections divided into 3 columns:
- Company (About, Careers, Press)
- Support (Account, Help Center, Contact)
- Legals (Terms, Privacy Policy, Licensing)
Each column uses a <div class="footer-column"> with a heading and anchor tags.
Call the Function

1
renderFooter();

Copied!

Wrap Toggled!
Call this function at the bottom of your footer.js so it runs when the file loads.
We can also import and call it from each page if needed.

*/
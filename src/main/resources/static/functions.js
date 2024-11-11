
/*
The functions.js file will contain JavaScript that handles the
dynamic fetching and updating of data from the backend.

Maybe helpful Documentation
https://www.w3schools.com/jsref/api_fetch.asp
https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
https://developer.mozilla.org/en-US/docs/Web/API/FormData
https://developer.mozilla.org/en-US/docs/Web/API/FormData/getAll


Use Template Literals: JavaScript template literals (backticks ``)
allow you to embed expressions inside string literals.
This method is clean and easy to read.

*/

//to fold javascript comments on intellij --> strg and + and . (control plus period)
/* PUT method
*/
async function modifyUser(){

    let id = document.getElementById("id").value;
    let userFirstName = document.getElementById("user_first_name").value;
    let userLastName = document.getElementById("user_last_name").value;
    let userEmail = document.getElementById("user_email").value;

    /*
    Construct the fetch URL using a template literal to include the user ID dynamically:
    */
    const url = `/users/${id}`; // Correct use of template literals
    /*
    Structure the JSON body properly by matching keys to your backend's expected format:
    Note that idUser should not be included
    */
    const userData = {

    userFirstName,
    userLastName,
    userEmail

    };

    /*
    This setup informs the server that the body of the request contains JSON data,
    ensuring it is parsed correctly on the server-side.
    */
    const response = await fetch(url, {

        //so not use placeholder, use the id from the label form input field
        method: "PUT", //put for Update
        headers: {
          'Content-Type': 'application/json'
        },

        body: JSON.stringify(userData) //key value pairs

    });
        /*
        Handle the Response:

            Check if the response is OK, then process or log the JSON response.
            Implement error handling to manage cases where the fetch operation fails due to network issues or server errors.
        */


    /*
    This setup includes error handling to manage both
    network errors and non-success HTTP responses.
    */


    if (response.ok) {

              const jsonResponse = await response.json().then(data => {
                                           populateUserTable(data)
              });

              //temp logging
              let userLog = jsonResponse;
              console.log('function modifyUSer, Update successfully: ', userLog);

              alert("temporary alert, modified user correctly");

            } else {
              throw new Error(`Update failed with status: ${response.status}`);
            }

}//close modifyUSer





/* POST method
*/
async function addUser(){
//ensuring the button's type is set to "button" to prevent default form submission

/*
Use document.getElementById("id_user").value to get the value from input fields directly.

Replace FormData.getAll(inputData) with direct access to each

Modify the button's type="button" in the HTML to prevent default form
submission and ensure they trigger the appropriate JavaScript functions.

For error handling, check the response status and parse JSON only if the
fetch was successful to avoid runtime errors.

Dynamically construct the URLs in your fetch calls
by embedding the actual ID values where needed.

Current Issue: Your use of .innerHTML to get form values is incorrect. .innerHTML retrieves the HTML markup within an element, not user input.
Correction: Use .value to correctly retrieve input values from form fields.
*/

    let userFirstName = document.getElementById("user_first_name").value;
    let userLastName = document.getElementById("user_last_name").value;
    let userEmail = document.getElementById("user_email").value;

/*You've set a constant URL using template literals, although in this case,
it is unnecessary as the URL does not vary. Still, it is correctly formatted.
*/
    const url = `/users`; // Correct use of template literals

    const userData = {

        userFirstName,
        userLastName,
        userEmail

        };

        const response = await fetch(url, {
                //so not use placeholder, use the id from the label form input field
                method: "POST", //POST for add, Create
                headers: {
                  'Content-Type': 'application/json'
                },

                body: JSON.stringify(userData) //key value pairs

            });

      //OK (status 200-299)
        if (response.ok) {

                      const jsonResponse = await response.json().then(data => {
                                                   populateUserTable(data)
                      });

                      //temp logging
                      let userLog = jsonResponse;
                      console.log('function addUser, Created User successfully: ', userLog);


                    alert("temporary alert, added user correctly");

         } else {

               throw new Error(`Update failed with status: ${response.status}`);

         }


}//close addUser



/* DELETE method
*/
async function removeUser(){

        let id = document.getElementById("id").value;

        const url = `/users/${id}`; // Correct use of template literals

        const response = await fetch(url, {

            method: "DELETE", //DELETE for Deleting

        });

            /*
            You should handle potential errors and the response status within the .then() block
            or another async/await pattern to correctly manage and relay errors.
            */
              if (response.ok) {

                    const jsonResponse = await response.json();
                    /*
                    call a function or trigger a log for the Client to know the status
                    A simpler UI element like a status message or alert might be appropriate.
                    */

                    //temp logging
                    let statusLog = jsonResponse;
                    console.log('function removeUser, deleted User successfully: ', statusLog);


                    alert("temporary alert, removed user correctly");

              } else {
                     throw new Error(`Update failed with status: ${response.status}`);
              }



}//close removeUSer




/* GET Method by default
*/
async function retrieveUser(){

    /*
    Reads the user ID from the input field.
    Sends a GET request to /users/{id} where {id} is replaced dynamically with the actual ID.
    */
    let id = document.getElementById("id").value;

/*You've set a constant URL using template literals, although in this case,
it is unnecessary as the URL does not vary. Still, it is correctly formatted.
*/
    const url = `/users/${id}`; // Correct use of template literals

        const response = await fetch(url, {

                //so not use placeholder, use the id from the label form input field

                method: "GET", //GET for Read, Retrieve
/*
You've specified headers indicating that the request expects JSON. This is generally unnecessary for a GET request unless
your server explicitly requires it for all requests.

Correction: Typically, you wouldn’t include the 'Content-Type': 'application/json'
header in a GET request, as you’re not sending any content to the
server that needs to be described by the content type.
*/
/*
A GET request should not have a body.
It is typically used to retrieve data from the server based on the URL.
*/

            });

       //checking if the response is successful before attempting to parse it as JSON.
      //OK (status 200-299)
    if (response.ok) {

          const jsonResponse = await response.json().then(data => {
                                       populateUserTable(data)
          });


          //temp logging
          let userLog = jsonResponse;
          console.log('function retrieveUser, retrieved User successfully:', userLog);

        } else {

          throw new Error(`Update failed with status: ${response.status}`);

        }

/*
consider how you might display this information on the
webpage or handle errors visually for users.
*/

}//close retrieveUser

function populateUserTable (user) { //object user to be accesed directly

    console.log("function populateUserTable, userObject: ", user);
/*
For a Single User Object:

    Direct Access: If user is a single object (not an array),
    directly access its properties.
        Use user.id, user.userFirstName, etc., to access properties.
        This avoids the complexity and potential errors of
        dealing with index-based access which you've implemented.
        Avoid Unnecessary Conversions: If you're working with a
        single user object, there’s no need to convert it to an array.
        Access properties directly.

*/

/*
1.Clear existing content in the <tbody> element.
2.Loop through the user data.
For each user, create a new row and cells containing user information.
Append each new row to the <tbody>.

try $('#table').detach(); or $("#table").remove();
https://www.geeksforgeeks.org/how-to-remove-all-rows-from-a-table-in-javascript/

here it uses textContent instead of innerHTML
https://stackoverflow.com/questions/58239610/iterate-and-append-to-table
*/
/*
using innerHtml to clear the tbody
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises

*/


    /*
    By creating elements (createElement), setting their text (textContent),
    and appending them to the table (appendChild),
    you dynamically adjust the webpage to display the data fetched from the server.

    In your populateUsersTable function, instead of setting textContent
    to usersObject[i] (which just converts the user object to a string,
    resulting in [object Object]), you should:

        Access each attribute of the user object individually.
        Create a cell for each attribute and set the textContent of
        that cell to the value of the attribute.
    */

        //Clearing the Table:
        document.getElementById('tBody').innerHTML = '';

            //for(let i = 0; i < userArray.length; i++){

                        const tBody = document.getElementById('tBody');
                        //create a new row
                        const tr = document.createElement('tr');

                            const tdId = document.createElement('td');
                            tdId.textContent = user.id;
                            tr.appendChild(tdId);

                            const tdUserFirstName = document.createElement('td');
                            tdUserFirstName.textContent = user.userFirstName;
                            tr.appendChild(tdUserFirstName);

                            const tdUserLastName = document.createElement('td');
                            tdUserLastName.textContent = user.userLastName;
                            tr.appendChild(tdUserLastName);

                            const tdUserEmail = document.createElement('td');
                            tdUserEmail.textContent = user.userEmail;
                            tr.appendChild(tdUserEmail);

                                tBody.appendChild(tr);

                    //}
}//end of populateUserTable





/* Get Method is not implemented in Controller, Service, UserService
Maybe is automatically findAll in the JPA Repository?
*/
async function retrieveAllUsers(){

    /*
    Reads the user ID from the input field.
    Sends a GET request to /users/{id} where {id} is replaced dynamically with the actual ID.
    */


/*You've set a constant URL using template literals, although in this case,
it is unnecessary as the URL does not vary. Still, it is correctly formatted.
*/
    const url = `/users`; // Correct use of template literals

        const response = await fetch(url, {

                //so not use placeholder, use the id from the label form input field

                method: "GET", //GET for Read, Retrieve
/*
You've specified headers indicating that the request expects JSON. This is generally unnecessary for a GET request unless
your server explicitly requires it for all requests.

Correction: Typically, you wouldn’t include the 'Content-Type': 'application/json'
header in a GET request, as you’re not sending any content to the
server that needs to be described by the content type.
*/
/*
A GET request should not have a body.
It is typically used to retrieve data from the server based on the URL.
*/
            });

       /*
       checking if the response is successful before attempting to parse it as JSON.
      OK (status 200-299)
      */
    if (response.ok) {

           /*
           After the response.ok check, parse the JSON directly inside the if block
           use the .then from the promise to call the function to populate the table
           */
          const jsonResponse = await response.json().then(data => {
                                       populateAllUsersTable(data)

          });

          //temp logging, json.parse is done in response.json() function
          let usersLog = jsonResponse;
          console.log('function retrieveAllUsers, retrieved all Users successfully:', usersLog);

        } else {
          throw new Error(`Update failed with status: ${response.status}`);
        }

/*
consider how you might display this information on the
webpage or handle errors visually for users.


In the .then() block or after you check response.ok in your retrieveAllUsers() function,
call populateUsersTable(users) with the data you received from the server.

*/

/*
After verifying response.ok, parse the JSON within the same block and then
call populateUsersTable(jsonResponse).
 This should happen synchronously in the fetch block.

 maybe helpful
 https://stackoverflow.com/questions/69874045/fetch-api-then-functions-runs-even-if-response-ok-is-false

*/

/*
You should not use .then() directly on response since
response is not a Promise itself but the object returned from the fetch call.

Promise and .then documentation
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise/then
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise

*/



}//retrieveAllUsers


/*
function to populate the tables
This function takes an array of user objects as its parameter.
Its job is to clear the existing entries in the table and repopulate it based on the array provided.
*/
function populateAllUsersTable (users) { //array of user objects

    console.log("function populateUsersTable, usersObject: ", users);



/*
1.Clear existing content in the <tbody> element.
2.Loop through the user data.
For each user, create a new row and cells containing user information.
Append each new row to the <tbody>.

try $('#table').detach(); or $("#table").remove();
https://www.geeksforgeeks.org/how-to-remove-all-rows-from-a-table-in-javascript/

here it uses textContent instead of innerHTML
https://stackoverflow.com/questions/58239610/iterate-and-append-to-table
*/
/*
using innerHtml to clear the tbody
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises

*/


    /*
    By creating elements (createElement), setting their text (textContent),
    and appending them to the table (appendChild),
    you dynamically adjust the webpage to display the data fetched from the server.

    In your populateUsersTable function, instead of setting textContent
    to usersObject[i] (which just converts the user object to a string,
    resulting in [object Object]), you should:

        Access each attribute of the user object individually.
        Create a cell for each attribute and set the textContent of
        that cell to the value of the attribute.
    */

        //Clearing the Table:
        document.getElementById('tBody').innerHTML = '';

        for(let i = 0; i < users.length; i++){

            const tBody = document.getElementById('tBody');
            //create a new row
            const tr = document.createElement('tr');

                    const tdId = document.createElement('td');
                    tdId.textContent = users[i].id;
                    tr.appendChild(tdId);

                    const tdUserFirstName = document.createElement('td');
                    tdUserFirstName.textContent = users[i].userFirstName;
                    tr.appendChild(tdUserFirstName);

                    const tdUserLastName = document.createElement('td');
                    tdUserLastName.textContent = users[i].userLastName;
                    tr.appendChild(tdUserLastName);

                    const tdUserEmail = document.createElement('td');
                    tdUserEmail.textContent = users[i].userEmail;
                    tr.appendChild(tdUserEmail);


                    tBody.appendChild(tr);

        }

}//end of populateAllUsersTable






function clearTable (){

        //Clearing the Table:
        document.getElementById('tBody').innerHTML = '';
}


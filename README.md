# cpsc5011-01-fq18
# Dylan Gnatz

### Seattle U, CPSC 5011, Section 1, Fall Quarter 2018 -- HW2 ###

### 1. is your solution fully working? 

Yes, my Vault class is fully functional. Each of its operations work correctly, and invalid inputs throw the correct exceptions. 

### 2. Do you believe your unit tests to be complete to test the module's contract?  If not, why not?

My unit tests are thorough in testing the module's contract. Each of the public methods are tested with valid inputs, and also with invalid inputs to ensure that they throw the correct exceptions. 

### 3. What extra credit problem(s) did you work on?  If so, how do you demonstrate the functionality?

#### Unlocking
I have fully implemented an unlocking feature for the Vault. In addition to the User class, I added a class AdminUser that extends User. To create an admin user, you call .newAdmin(String username, String password, String controlpass), where username and password are the desired credentials for the admin account, and controlpass is a designated password in the source code that allows the end user to create admin accounts. 

To unlock an account, call .unlockUser(String adminusername, String adminPassword, String username), where the first two parameters are the login credentials of an admin account and the last parameter is the username of a locked account. 

#### Console App 

I began constructing a console app for the Vault class using the Java-Console-View library, but time did not permit me to complete the implementation. The beginnings of this console app are in the driver package.  

### 4. How much time did you spend on this assignment?

About 10 hours.

### 5. Overall, how did you like this assignment in terms of growing as a developer and applying class concepts?

I really enjoyed this assignment, as larger projects like these definitely push us to utilize OO design best practices. I wish that the console app library had more thorough documentation (or that I knew how to interpret it better), as I struggled to understand how to implement an app with multiple menus from the README alone. I am also still somewhat unclear about your expectations regarding documentation for our code, so I did my best to infer what kind of comments you wanted. 


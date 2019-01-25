# ROUND CONTROLLER

Round Controller built with Android studio ,Node and MongoDB. 

REST Server with NodeJS and Express provides access to resource and REST client accesses and modifies the resources using HTTP protocol.
MongoDB stores the data.
  
## Requirements

- [Android Studio](https://www.tutorialspoint.com/android/android_studio.html)
- [Node and npm](http://nodejs.org)
- MongoDB: Make sure you have your own local or remote MongoDB database URI configured in `/db.js`

## Installation

1. Clone the repository: `git clone https://github.com/Fezaii/RoundController.git`
2. Install all packages: `npm install`
3. Create your database MongoDb (admin) , on mongodb shell: `use admin`
4. Add a new user(admin) and password(admin) for your database(admin) : `db.createUser(
  {
    user: "admin",
    pwd: "admin",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)`
5. Insert the first Admin for the Android App into your database(admin) : `db.admin.insert(
	{
		"name" : "yourname",
		"email" : "yourmail",
		"password" : "yourpassword",
		"role" : "admin"
	}
)` 
6. Verify the own MongoDB URI in `/db.js` : `mongodb://admin:admin@127.0.0.1:27017/admin`
7. Start the server: `node server.js`
8. Make sure the server is runnig correctly without errors.
9. Place your pc IP in  `/LoginActivity.java` with name `URL`
10. Make sure the device is connected to the same Network of your PC
11. Run the Android App
12. Connect  with your email "yourmail" and password "yourpassword" 



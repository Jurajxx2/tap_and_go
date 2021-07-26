<h1>Tap And Go app</h1>

minSdkVersion - 23
targetSdkVersion - 30

<h2>Description</h2>
App developed as a test project. 
App should provide car renting functionality with car and date selection. 
User can view his reservations, he can make new reservations, check the vehicle on the map and check the car details.

<h2>Some of used libraries</h2>
<h3>1. Maps</h3> - osmdroid (free opensource)
<h3>2. Places API(reversed geocoding)</h3> - osm bonus back - com.github.MKergall (free)
<h3>3. Networking</h3> - retrofit(there is ready implementation for firebase database)
<h3>4. Local data persistance</h3> - Room database
<h3>5. Navigation</h3> - android navigation component
<h3>6. Lists of data</h3> - epoxy

<h2>Notes</h2>
Currently missing feature for backend is user profile photo upload.
All confidental data(tokens) are secured by Encrypted shared preferences.

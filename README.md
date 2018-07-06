# Calendar

Calendar Backend Requirements
- Python3

Dependencies
- flask_sqlalchemy
- flask_marshmallow
- marshmallow-sqlalchemy

Dependencies can be installed using pip command
Ex- pip install flask_sqlalchemy

To setup database use terminal to naviagate to the CalendarBackend folder
- Enter python interactive shell by using command python3
- Enter command "from backend import db"
- Enter command "db.create_all()"

Then exit python Interactive Shell using Ctrl + D
- Run backend.py by using command "python3 backend.py"
- To View JSON data you can use a browser or postman to navigate to 
"your-machines-ipaddress:5000/events"

Calendar APP 
Change the IP address in the java file ApiUtils to the ip address of the machine running the backend python script.
Ex - "http://YOUR_IP_HERE:5000/"

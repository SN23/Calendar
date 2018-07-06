from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'calendar.sqlite')
db = SQLAlchemy(app)
ma = Marshmallow(app)


class Event(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    date = db.Column(db.String(120), )
    start_time = db.Column(db.String(120))
    end_time = db.Column(db.String(120))
    description = db.Column(db.String(300))

    def __init__(self, date, start_time, end_time, description):
        self.date = date
        self.start_time = start_time
        self.end_time = end_time
        self.description = description


class EventSchema(ma.Schema):
    class Meta:
        # Fields to expose
        fields = ('date', 'start_time', 'end_time', 'description')


event_schema = EventSchema()
events_schema = EventSchema(many=True)


# endpoint to create a event
@app.route("/events", methods=["POST"])
def add_event():
    date = request.json['date']
    start_time = request.json['start_time']
    end_time = request.json['end_time']
    description = request.json['description']

    new_event = Event(date, start_time, end_time, description)

    db.session.add(new_event)
    db.session.commit()

    return event_schema.jsonify(new_event)


# endpoint to show all events
@app.route("/events", methods=["GET"])
def get_event():
    all_events = Event.query.all()
    result = events_schema.dump(all_events)
    return jsonify(result.data)


# endpoint to get event detail by id
@app.route("/events/<id>", methods=["GET"])
def event_detail(id):
    event = Event.query.get(id)
    return event_schema.jsonify(event)


# endpoint to update event
@app.route("/events/<id>", methods=["PUT"])
def event_update(id):
    event = Event.query.get(id)
    date = request.json['date']
    start_time = request.json['start_time']
    end_time = request.json['end_time']
    description = request.json['description']

    event.date = date
    event.start_time = start_time
    event.end_time = end_time
    event.description = description

    db.session.commit()
    return event_schema.jsonify(event)


# endpoint to delete event
@app.route("/events/<id>", methods=["DELETE"])
def event_delete(id):
    event = Event.query.get(id)
    db.session.delete(event)
    db.session.commit()

    return event_schema.jsonify(event)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)

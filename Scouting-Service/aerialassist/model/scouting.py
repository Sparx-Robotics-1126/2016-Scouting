from google.appengine.ext import ndb

class ScoutingAuto(ndb.Model):
    startingLocationX = ndb.FloatProperty()
    startingLocationY = ndb.FloatProperty()
    startedWithBall = ndb.BooleanProperty()
    ballsAcquired = ndb.IntegerProperty()
    ballsShot = ndb.IntegerProperty()
    ballsScored = ndb.IntegerProperty()
    ballsScoredHotHigh = ndb.IntegerProperty()
    ballsScoredHotLow = ndb.IntegerProperty()
    ballsScoredHigh = ndb.IntegerProperty()
    ballsScoredLow = ndb.IntegerProperty()
    endingLocationX = ndb.FloatProperty()
    endingLocationY = ndb.FloatProperty()

class ScoutingTele(ndb.Model):
    ballsAcquiredFromFloor = ndb.IntegerProperty()
    completedAssistsFromFloor = ndb.IntegerProperty()
    ballsAcquiredFromHuman = ndb.IntegerProperty()
    completedAssistsFromHuman = ndb.IntegerProperty()
    shotHigh = ndb.IntegerProperty()
    scoredHigh = ndb.IntegerProperty()
    shotLow = ndb.IntegerProperty()
    scoredLow = ndb.IntegerProperty()
    ballsCaughtOverTruss = ndb.IntegerProperty()
    ballsThrownOverTruss = ndb.IntegerProperty() 
    stayedInZone = ndb.StringProperty()    

class ScoutingGeneral(ndb.Model):
    playsDefense = ndb.BooleanProperty()
    numberOfPenalties = ndb.IntegerProperty()
    commentsOnPenalties = ndb.StringProperty()
    numberOfTechnicalFouls = ndb.IntegerProperty()
    commentsOnTechnicalFouls = ndb.StringProperty()
    generalComments = ndb.StringProperty()

class Scouting(ndb.Model):
    nameOfScouter = ndb.StringProperty()
    teamKey = ndb.StringProperty()
    eventKey = ndb.StringProperty()
    matchKey = ndb.StringProperty()

    auto = ndb.StructuredProperty(ScoutingAuto, repeated=False)
    tele = ndb.StructuredProperty(ScoutingTele, repeated=False)
    general = ndb.StructuredProperty(ScoutingGeneral, repeated=False)

import googlemaps
import json
import re
from googlemaps.convert import latlng
#def ejercicio1(origin, destinatio):
def get_restaurants (lat, lng):
    gmaps = googlemaps.Client(key='AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM')

    #directions_result = gmaps.directions(origin, destinatio, mode="driving")
    location = latlng((lat,lng))
    places = gmaps.places_nearby(location, 300, type='restaurant')
    restaurants = '{\"restaurantes\":['
    for i in range(len(places['results'])):
        restaurants +='{\"name\":\"'
        restaurants += places['results'][i]['name']
        restaurants +='\", '
        restaurants +='\"lat\":'
        restaurants += str(places['results'][i]['geometry']['location']['lat'])
        restaurants +=', '
        restaurants +='\"lng\":'
        restaurants += str(places['results'][i]['geometry']['location']['lng'])
        restaurants +='}'
        
        if i < (len(places['results'])-1):
            restaurants += ', '
    restaurants += ']}'
    #print restaurants
    return restaurants
    #jsonResult = json.loads(restaurants)
    #print json.dumps(jsonResult,sort_keys=True, indent=4)

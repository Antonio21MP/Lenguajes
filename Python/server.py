from bottle import route, run, post, request, HTTPResponse
from PIL import Image
import json
import sys
import googlemaps
import base64
#sys.path.append("./Ejercicios/ejercicio1")
from ejercicio1 import get_route
@post('/ejercicio1')
def ejercicio1():
    print "ejercicio 1"
    body = request.body.read()
    print type(body)
    directions = json.loads(body)
    origin = directions['origen']
    destination = directions['destino']
    print origin
    print destination
    routes = get_route(origin, destination)
    json_value = json.loads(routes)
    return_value = json.dumps(json_value,sort_keys=True, indent=4)
    return HTTPResponse(status=200, body=return_value)

#sys.path.append("./Ejercicios/ejercicio2")
from ejercicio2 import get_restaurants
@post('/ejercicio2')
def ejercicio2():
    print "ejercicio 2"
    body = request.body.read()
    print type(body)
    directions = json.loads(body)
    origin = directions['origen']
    print origin
    gmaps = googlemaps.Client(key='AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM')
    directions_result = gmaps.directions(origin, origin, mode="driving")
    lat = directions_result[0]['bounds']['northeast']['lat']
    lng =  directions_result[0]['bounds']['northeast']['lng']
    restaurants = get_restaurants(lat, lng)
    json_value = json.loads(restaurants)
    return_value = json.dumps(json_value,sort_keys=True, indent=4)
    return HTTPResponse(status=200, body=return_value)

#sys.path.append("./Ejercicios/ejercicio3")
from ejercicio3 import black_and_white
@post('/ejercicio3')
def ejercicio3():
    print "ejercicio 3"
    body = request.body.read()
    print type(body)
    directions = json.loads(body)
    print 'llego'
    name = directions['nombre']
    data = directions['data']    
    print name
    print data

    black_white = black_and_white(name, data)
    json_value = json.loads(black_white)
    return_value = json.dumps(json_value,sort_keys=True, indent=4)
    return HTTPResponse(status=200, body=return_value)

#sys.path.append("./Ejercicios/ejercicio4")
from ejercicio4 import do_resize
@post('/ejercicio4')
def ejercicio4():
    print "ejercicio 4"
    body = request.body.read()
    print type(body)
    directions = json.loads(body)
    print 'llego'
    name = directions['nombre']
    data = directions['data']    
    lat = directions['tamanio']['alto']
    lng = directions['tamanio']['ancho']
    print name
    print data
    print lat
    print lng
    dec = data.decode('base64', 'strict')
    with open(name, 'wb') as f:
        f.write(dec)
    img = Image.open(name)
    lat_lng = img.size
    if lat < int(lat_lng[0]) and lng < int(lat_lng[1]):
        do_resize(img, (lat, lng), True, name)
        with open(name, "rb") as f:
            img_open = f.read()
        enc = img_open.encode('base64', 'strict')
        #enc.replace('\n','')
        enc = base64.b64encode(bytes(enc),"utf-8")
        json_result = '{\"nombre\":\"'+name+'\",\"data\":\"'+enc+'\"}'
        json_value = json.loads(json_result)
        return_value = json.dumps(json_value,sort_keys=True, indent=4)
        return HTTPResponse(status=200, body=return_value)
    msg = '{\"error\":\"el tamanio pedido supera al de la imagen\"}'
    return HTTPResponse(status=400, body=msg)

run(host='0.0.0.0', port=8080, debug=True)

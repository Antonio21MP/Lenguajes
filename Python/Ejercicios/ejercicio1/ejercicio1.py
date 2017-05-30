import googlemaps
import json
import re

def get_route(origin, destinatio):

    gmaps = googlemaps.Client(key='AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM')

    directions_result = gmaps.directions(origin, destinatio, mode="driving")

    convert = json.dumps(directions_result)
    print type(convert)

    result = re.search('\"end_location\"(.*)', convert)
    if result:
        found = result.group(1);

    final = found.split('\"end_location\"')
    #print final[0]
    complete = []
    resultJ = []
    for i in range(0,len(final)):
        tmp = final[i]
        complete.append(tmp[2:43])

    for i in range(0, len(complete)):
        print complete[i]
        tmp2 = complete[i]
        for j in range(0, len(tmp2)):
            if tmp2[j] == '}':
                if j+1 < len(tmp2):
                    resultJ.append(tmp2[:(j+1)])
                    print j
                    break
            elif j+1 >= len(tmp2):
                resultJ.append((tmp2+'}'))
                print j
                break 
    #print resultJ
    jsonString = '{ \"ruta\":[ '
    for i in range(0, len(resultJ)):
        if i < (len(resultJ)-1):
            jsonString += resultJ[i]+ ', '
        else:
            jsonString += resultJ[i]

    jsonString += ']}' 
    #print jsonString
    return jsonString
    #routes = json.loads(jsonString)

    #json.dumps(routes,sort_keys=True, indent=4)

#"Torocolor SA, 4 Avenida NE, San Pedro Sula 21101, Honduras"   
# "Zizima Eco Water Park, KM 3, Autopista Salida a la Lima, ninguno, San Pedro Sula, Honduras" 
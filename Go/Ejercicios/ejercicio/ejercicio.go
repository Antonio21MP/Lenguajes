package ejercicio

import (
	"log"
	"googlemaps.github.io/maps"
	"golang.org/x/net/context"
	"fmt"
	"encoding/json"
	"strings"
	"regexp"

)

func GetRuts(origin string, destination string)string {
	c, err := maps.NewClient(maps.WithAPIKey("AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM"))
	if err != nil {
		log.Fatalf("fatal error: %s", err)
	}
	r := &maps.DirectionsRequest{
		Origin: origin,
		Destination: destination,
	}
	resp, _, err := c.Directions(context.Background(), r)
	if err != nil {
		log.Fatalf("fatal error: %s", err)
	}

	bytes, err := json.Marshal(resp)
    if err != nil {
        fmt.Println(err)
        return "error"
    }
    text := string(bytes)
    //fmt.Println(text)

    partitionOfText := strings.Split(text, "duration")
    i:= 0 
    s :=make ([]string, len(partitionOfText))
    
    for i<len(partitionOfText){
		pat := regexp.MustCompile("\"end_location\":{(.*)}")
		s[i] = pat.FindString(partitionOfText[i])
		fmt.Println(s[i])
		i += 1
	}
	tmp := s[0]
	finalArray := tmp[15:51]
	fmt.Println(len(partitionOfText))
	fmt.Println(len(s))
	j := 1
	k := 16
	for j<(len(partitionOfText)-2){
		tmp = s[j]
		for k < len(tmp){
			if(tmp[k]=='}'){
				//fmt.Println(string(tmp[k+1]))
				finalArray +=","+tmp[15:k+1]
				k = 16
				break	
			}
			k += 1
		}
		j+=1
	}
	fmt.Println(finalArray)

	in := []byte(`{"rutas":[`+finalArray+`]}`)
	var raw map[string]interface{}
	json.Unmarshal(in, &raw)
	out, _ := json.Marshal(raw)
	fmt.Println(string(out))
	return string(out)
//"end_location":{"lat":15.5073376,"lng":-88.0140764} 14:50
}
package ejercicio2

import (
	"log"
	"googlemaps.github.io/maps"
	"golang.org/x/net/context"
	"fmt"
	"encoding/json"
	"strings"
	"regexp"

)

func GetRestaurants(origin string)string {
	fmt.Println("LLego")
	l, err2 := maps.ParseLatLng(origin)
	if err2 != nil {
		log.Fatalf("fatal error: %s", err2)
	}
	fmt.Println("llego2")
	c, err := maps.NewClient(maps.WithAPIKey("AIzaSyDxjE_B2p2t199wTZPwBErRiOmaqL1hkhM"))
	if err != nil {
		log.Fatalf("fatal error: %s", err)
	}
	r := &maps.TextSearchRequest{
		Query: "restaurant",
		Radius: 300,
		Location: &l,

	}
	resp, err := c.TextSearch(context.Background(), r)
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
    partitionOfText := strings.Split(text, "photos")
    i:= 0 
    s :=make ([]string, len(partitionOfText))
    fmt.Println("localiciones")
    for i<len(partitionOfText){
		pat := regexp.MustCompile("\"location\":{(.*)}")
		s[i] = pat.FindString(partitionOfText[i])
		fmt.Println(s[i])
		i += 1
	}

	tmp := s[0]
	locationArray :=make ([]string, (len(partitionOfText)-2))
	locationArray[0] =","+tmp[12:47]
	fmt.Println(len(partitionOfText))
	fmt.Println(len(s))
	j := 1
	k := 12
	m := 1
	for j<(len(partitionOfText)-2){
		tmp = s[j]
		for k < len(tmp){
			if(tmp[k]=='}' && tmp[k+1]==','){
				//fmt.Println(string(tmp[k+1]))
				locationArray[m] +=","+tmp[12:k+1]
				k = 16
				m +=1
				break	
			}
			k += 1
		}
		j+=1
	}
	fmt.Println(locationArray)

	fmt.Println("nombres")
	partitionOfText2 := strings.Split(text, "photos")
    i2:= 0 
    s2 :=make ([]string, len(partitionOfText2))
    
    for i2<len(partitionOfText){
		pat2 := regexp.MustCompile("\"name\":(.*)")
		s2[i2] = pat2.FindString(partitionOfText2[i2])
		fmt.Println(s2[i2])
		i2 += 1
	}
	tmp2 := s2[0]
	locationArray2 :=make ([]string, (len(partitionOfText2)-2))
	fmt.Println(len(partitionOfText2))
	fmt.Println(len(s2))
	x := 0
	y := 0
	z := 0
	for x<(len(partitionOfText2)-2){
		tmp2 = s2[x]
		for y < len(tmp2){
			if(tmp2[y]==','){
				//fmt.Println(string(tmp[k+1]))
				locationArray2[z] +="{"+tmp2[:y]
				y = 0
				z +=1
				break	
			}
			y += 1
		}
		x+=1
	}
	fmt.Println(locationArray2)
	fmt.Println(len(locationArray))
	fmt.Println(len(locationArray2))
	finalArray := "{\"restaurantes\":["
	finalArray += locationArray2[0] + locationArray[0]
	for q:=1; q<len(locationArray); q++{
		if locationArray[q]!="" && locationArray2[q]!=""{
			finalArray += ","+locationArray2[q] + locationArray[q]
		}
	}
	finalArray += "]}"
	//fmt.Println(finalArray)

	in := []byte(``+finalArray)
	var raw map[string]interface{}
	json.Unmarshal(in, &raw)
	out, _ := json.Marshal(raw)
	fmt.Println(string(out))
	return string(out)
	
//"end_location":{"lat":15.5073376,"lng":-88.0140764} 14:50
//"location":{"lat":15.4966886,"lng":-88.0359541} 11-47
}
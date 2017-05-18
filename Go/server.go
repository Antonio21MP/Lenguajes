package main

import (
	"github.com/go-martini/martini"
	"./Ejercicios/ejercicio"
	"./Ejercicios/ejercicio2"
	"net/http"
	"github.com/martini-contrib/render"
	"encoding/json"
	"fmt"
	"reflect"
)

func main() {
  m := martini.Classic()
  m.Use(render.Renderer())
  m.Get("/", func() string {
    return "Hello world!"
  })
  m.Post("/ejercicio1", func(r render.Render, request *http.Request) {
  	origin := request.FormValue("origin")
  	destination := request.FormValue("destination")
  	if origin !="" && destination !=""{
  		result := ejercicio.GetRuts(origin, destination)	
	  	in := []byte(``+result+``)
		var raw map[string]interface{}
		json.Unmarshal(in, &raw)
		out, _ := json.Marshal(raw)
	  	r.Data(http.StatusOK, out)
  	}
  	in := []byte(`{"error":{}`)
		var raw map[string]interface{}
		json.Unmarshal(in, &raw)
		out, _ := json.Marshal(raw)
  	r.Data(http.StatusBadRequest, out)
  	//Origin:      "Torocolor SA, 4 Avenida NE, San Pedro Sula 21101, Honduras",
	//Destination: "Zizima Eco Water Park, KM 3, Autopista Salida a la Lima, ninguno, San Pedro Sula, Honduras",
  })
  
  m.Post("/ejercicio2", func(r render.Render, request *http.Request) {
  	
	origin := request.FormValue("origin")
	if origin !=""{
	  	fmt.Println(reflect.TypeOf(origin))
	  	result := ejercicio2.GetRestaurants(origin)	
		in := []byte(``+result+``)
		var raw map[string]interface{}
		json.Unmarshal(in, &raw)
		out, _ := json.Marshal(raw)
		r.Data(http.StatusOK, out)
  	}
  	in := []byte(`{"error":{}`)
		var raw map[string]interface{}
		json.Unmarshal(in, &raw)
		out, _ := json.Marshal(raw)
  	r.Data(http.StatusBadRequest, out)
  	//"15.5073376,-88.0140764"
  })
  m.Run()
}
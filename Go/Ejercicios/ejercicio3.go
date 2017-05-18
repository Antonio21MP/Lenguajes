package main

import (
	"fmt"
	"os"
)

func main() {
	path := "youtube.bmp"
	image, err := os.OpenFile(path, os.O_RDWR, 0666)
	defer image.Close()
	if err != nil {
		fmt.Println("Hubo un error no se puede leer la imagen")
	}else{
		fmt.Println("imagen leida con exito")
	}
	
	
}
FROM golang:1.8

RUN go get github.com/go-martini/martini

RUN go get github.com/martini-contrib/render

RUN go get googlemaps.github.io/maps

RUN go get golang.org/x/net/context

RUN git clone https://github.com/Antonio21MP/Lenguajes

EXPOSE 8080	

CMD cd Lenguajes/Go/ && go run server.go

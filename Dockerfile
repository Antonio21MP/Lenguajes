FROM python:2

RUN pip install -U googlemaps

RUN pip install Pillow

RUN git clone https://github.com/Antonio21MP/Lenguajes.git

EXPOSE 8080

CMD [ "python", "Lenguajes/Python/server.py" ]

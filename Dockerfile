FROM python:2

RUN pip install -U googlemaps

RUN pip install Pillow

RUN pip install bottle

RUN git clone https://github.com/Antonio21MP/Lenguajes.git

EXPOSE 8080

CMD cd Lenguajes/Python && python server.py

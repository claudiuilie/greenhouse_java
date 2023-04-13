import Adafruit_DHT as dht
from time import sleep

# Define the sensor type and GPIO pin
#Set DATA pin
DHT = 4

while True:
    #Read Temp and Hum from DHT22
    h,t = dht.read_retry(dht.DHT22, DHT)
    #Print Temperature and Humidity on Shell window
    print('Temperature: {:.1f}°C  Humidity: {:.1f}%'.format(t,h))
    sleep(3) #Wait 5 seconds and read again
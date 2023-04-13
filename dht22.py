import Adafruit_DHT
import RPi.GPIO as GPIO
import json

# Set sensor type : Options are DHT11,DHT22 or AM2302
sensor = Adafruit_DHT.DHT22

# Dictionary of GPIO pin numbers and their corresponding BCM numbers
PIN_MAP = {
    3: 2,
    5: 3,
    7: 4,
    8: 14,
    10: 15,
    11: 17,
    12: 18,
    13: 27,
    15: 22,
    16: 23,
    18: 24,
    19: 10,
    21: 9,
    22: 25,
    23: 11,
    24: 8,
    26: 7
}

# Initialize a list to store the data for each sensor
sensor_data = []

# Set up the GPIO pins
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# Iterate over each pin in the PIN_MAP dictionary
for pin, bcm_pin in PIN_MAP.items():
    try:
        # Attempt to read the data from the DHT22 sensor on this pin
        humidity, temperature = Adafruit_DHT.read_retry(sensor, bcm_pin)

        # If data was successfully read, append it to the sensor_data list
        if humidity is not None and temperature is not None:
            sensor_data.append({
                "pin": pin,
                "temperature": temperature,
                "humidity": humidity
            })
    except Exception as e:
        # If an error occurred, print a message indicating which pin had the error
        print(f"Error reading from pin {pin}: {str(e)}")

# Print the sensor data as a JSON object
print(json.dumps(sensor_data))
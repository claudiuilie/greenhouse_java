import Adafruit_DHT
from gpiozero import DigitalInputDevice
import json

# Set sensor type : Options are DHT11,DHT22 or AM2302
sensor = Adafruit_DHT.DHT22

# Initialize a list to store the data for each sensor
sensor_data = []

# Iterate over each pin on the Raspberry Pi
for pin in range(2, 28):
    device = None
    try:
        # Attempt to read the data from the DHT22 sensor on this pin
        device = DigitalInputDevice(pin, pull_up_down=None)
        humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

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
    finally:
        # Close the device to avoid errors on subsequent reads
        if device is not None:
            device.close()

# Print the sensor data as a JSON object
print(json.dumps(sensor_data))
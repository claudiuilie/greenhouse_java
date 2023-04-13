package com.claudiu.greenhouse;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.platform.Platforms;
import com.pi4j.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenhouseApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(GreenhouseApplication.class, args);

		final Console console = new Console();

		Context pi4j = Pi4J.newAutoContext();

		Platforms platforms = pi4j.platforms();
		platforms.describe().print(System.out);

		DigitalInputConfig config = DigitalInput.newConfigBuilder(pi4j)
				.id("my-dout")
				.name("My Digital Output")
				.address(4)
				.pull(PullResistance.PULL_DOWN)
				.build();

		// get a Digital Input I/O provider from the Pi4J context
		DigitalInputProvider digitalInputProvider = pi4j.provider("pigpio-digital-input");

		DigitalInput input = digitalInputProvider.create(config);

		// setup a digital output listener to listen for any state changes on the digital input
		input.addListener(event -> {
			Integer count = (Integer) event.source().metadata().get("count").value();
			console.println(event + " === " + count);
		});

		// lets read the analog output state
		console.print("THE STARTING DIGITAL INPUT STATE IS [");
		console.println(input.state() + "]");

		console.println("CHANGE INPUT STATES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

		// wait (block) for user to exit program using CTRL-C
		console.waitForExit();

		// shutdown Pi4J
		System.out.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
		pi4j.shutdown();
	}

}

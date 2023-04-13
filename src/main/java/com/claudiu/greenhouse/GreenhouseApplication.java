package com.claudiu.greenhouse;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.platform.Platforms;
import com.pi4j.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@SpringBootApplication
public class GreenhouseApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(GreenhouseApplication.class, args);

		final Console console = new Console();
		Context pi4j = Pi4J.newAutoContext();

		int dataPinNum = 4;
		String traceLevel = "info";
		console.title("<-- The Pi4J V2 Project Extension  -->", "DHT22_App");
		String helpString = " parms:  -d  data GPIO number  -t trace " +
				" \n -t  trace values : \"trace\", \"debug\", \"info\", \"warn\", \"error\" \n " +
				" or \"off\"  Default \"info\"";
		Signal.handle(new Signal("INT"), new SignalHandler() {
			public void handle(Signal sig) {
				System.out.println("Performing ctl-C shutdown");
				try {
					pi4j.shutdown();
				} catch (LifecycleException e) {
					e.printStackTrace();
				}
				// Thread.dumpStack();
				System.exit(2);
			}
		});

		for (int i = 0; i < args.length; i++) {
			String o = args[i];
			if (o.contentEquals("-d")) {
				String a = args[i + 1];
				dataPinNum = Integer.parseInt(a);
				i++;
			} else if (o.contentEquals("-t")) {
				String a = args[i + 1];
				i++;
				traceLevel = a;
				if (a.contentEquals("trace") | a.contentEquals("debug") | a.contentEquals("info") | a.contentEquals("warn") | a.contentEquals("error") | a.contentEquals("off")) {
					console.println("Changing trace level to : " + traceLevel);
				} else {
					console.println("Changing trace level invalid  : " + traceLevel);
					System.exit(41);
				}
			} else if (o.contentEquals("-h")) {
				console.println(helpString);
				System.exit(41);
			} else {
				console.println("  !!! Invalid Parm " + o);
				console.println(helpString);
				System.exit(43);
			}
		}
		DHT22 sensor = new DHT22(pi4j, console, dataPinNum,  traceLevel);
		sensor.readAndDisplayData();

		 console.waitForExit();
		pi4j.shutdown();



//		Platforms platforms = pi4j.platforms();
//		platforms.describe().print(System.out);
//
//		DigitalInputConfig config = DigitalInput.newConfigBuilder(pi4j)
//				.id("my-dout")
//				.name("My Digital Output")
//				.address(4)
//				.pull(PullResistance.PULL_UP)
//				.build();
//
//		// get a Digital Input I/O provider from the Pi4J context
//		DigitalInputProvider digitalInputProvider = pi4j.provider("pigpio-digital-input");
//
//		DigitalInput input = digitalInputProvider.create(config);
//
//		// setup a digital output listener to listen for any state changes on the digital input
//		input.addListener(event -> {
//			Integer count = (Integer) event.source().metadata().get("count").value();
//			console.println(event + " === " + count);
//		});
//
//		// lets read the analog output state
//		console.print("THE STARTING DIGITAL INPUT STATE IS [");
//		console.println(input.state() + "]");
//
//		console.println("CHANGE INPUT STATES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");
//
//		// wait (block) for user to exit program using CTRL-C
//		console.waitForExit();
//
//		// shutdown Pi4J
//		System.out.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
//		pi4j.shutdown();
	}

}

package com.claudiu.greenhouse.constants;

public class DHT22_Declares {


    // MCU/Pi, initiate sensor OP
    public final static int BEGIN_READINGS_MILLS = 20;
    // these two signal the sensor is prepared to send data
    public final static int PREPARE_DATA_LOW_PULSE_MICS = 80;
    public final static int PREPARE_DATA_HIGH_PULSE_MICS = 80;

    // data signals '0 or '1' by pulse length
    public final static int ZERO_PULSE_MICS = 27;  // zero bit
    public final static int ONE_PULSE_MICS = 70;   //  one bit


    public final static int TOTAL_NUM_BITS = 40;
    public final static int RH_NUM_BITS = 16;
    public final static int T_NUM_BITS = 16;
    public final static int CKSUM_NUM_BITS = 8;


}
package com.example.sdksamples;

import com.impinj.octane.*;

import java.util.Scanner;
//
//
//public class ReadTags implements TagReportListener {
//
//    public void initReader(String[] args) {
//
//        try {
//            String hostname = "192.168.1.127";
//
//            if (hostname == null) {
//                throw new Exception("Must specify the '"
//                        + hostname + "' property");
//            }
//
//            ImpinjReader reader = new ImpinjReader();
//
//            System.out.println("Connecting");
//            reader.connect(hostname);
//
//            Settings settings = reader.queryDefaultSettings();
//
//            ReportConfig report = settings.getReport();
//            report.setIncludeAntennaPortNumber(true);
//            report.setMode(ReportMode.Individual);
//
//            // The reader can be set into various modes in which reader
//            // dynamics are optimized for specific regions and environments.
//            // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
//            // and continuously optimizes the reader's configuration
//            settings.setReaderMode(ReaderMode.AutoSetDenseReader);
//
//            // set some special settings for antenna 1
//            AntennaConfigGroup antennas = settings.getAntennas();
//            antennas.disableAll();
//            antennas.enableById(new short[]{3});
//            antennas.getAntenna((short) 3).setIsMaxRxSensitivity(false);
//            antennas.getAntenna((short) 3).setIsMaxTxPower(false);
//            antennas.getAntenna((short) 3).setTxPowerinDbm(20.0);
//            antennas.getAntenna((short) 3).setRxSensitivityinDbm(-70);
//
//
////            reader.setTagReportListener(this);
//
//            System.out.println("Applying Settings");
//            reader.applySettings(settings);
//
//            System.out.println("Starting");
//            reader.start();
//
//            System.out.println("Press Enter to exit.");
//            Scanner s = new Scanner(System.in);
//            s.nextLine();
//
//            reader.stop();
//            reader.disconnect();
//        } catch (OctaneSdkException ex) {
//            System.out.println(ex.getMessage());
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace(System.out);
//        }
//    }
//
//
//    public static final void main(String args[]) {
//        try {
//            new ReadTags();
//            initReader();
//        } catch (Error e) {
//            System.out.println(e.toString());
//        }
//    }
//}

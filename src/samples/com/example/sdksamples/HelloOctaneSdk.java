package samples.com.example.sdksamples;

import com.impinj.octane.*;

import java.io.Reader;
import java.util.Scanner;
import java.util.*;



public class HelloOctaneSdk {

    public static void main(String[] args) {

        try {

            System.out.println(Arrays.toString(args));
            String hostname = args[0];


            if (hostname == null) {
                throw new Exception("Must specify the '"
                        + hostname + "' property");
            }

            ImpinjReader reader = new ImpinjReader();

            // Connect
            System.out.println("Connecting to " + hostname);
            reader.connect(hostname);

            // Get the default settings
            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();
            report.setIncludeAntennaPortNumber(true);

            report.setMode(reportModeMap(args[1]));

            // The reader can be set into various modes in which reader
            // dynamics are optimized for specific regions and environments.
            // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
            // and continuously optimizes the reader's configuration
            settings.setReaderMode(readerModeMap(args[2]));

            // set some special settings for antenna 1
            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{3});
            antennas.getAntenna((short) 3).setIsMaxRxSensitivity(false);
            antennas.getAntenna((short) 3).setIsMaxTxPower(false);
            antennas.getAntenna((short) 3).setTxPowerinDbm(20.0);
            antennas.getAntenna((short) 3).setRxSensitivityinDbm(-70);


            // Apply the new settings
            reader.applySettings(settings);

            // connect a listener
            reader.setTagReportListener(new ReportTags());

            // Start the reader
            reader.start();

            System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            System.out.println("Stopping  " + hostname);
            reader.stop();

            System.out.println("Disconnecting from " + hostname);
            reader.disconnect();

            System.out.println("Done");
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }

    public static ReportMode reportModeMap(String str) {
        Map map=new HashMap();
        map.put("WaitForQuery", ReportMode.WaitForQuery);
        map.put("Individual", ReportMode.Individual);
        map.put("BatchAfterStop", ReportMode.BatchAfterStop);

        ReportMode val = (ReportMode)map.get(str);
        return val;
    }

    public static ReaderMode readerModeMap(String str) {
        Map map= new HashMap();
        map.put("AutoSetDenseReader", ReaderMode.AutoSetDenseReader);
        map.put("AutoSetDenseReaderDeepScan", ReaderMode.AutoSetDenseReaderDeepScan);
        map.put("AutoSetStaticDRM", ReaderMode.AutoSetStaticDRM);
        map.put("AutoSetStaticFast", ReaderMode.AutoSetStaticFast);
        map.put("DenseReaderM4", ReaderMode.DenseReaderM4);
        map.put("DenseReaderM4Two", ReaderMode.DenseReaderM4Two);
        map.put("DenseReaderM8", ReaderMode.DenseReaderM8);
        map.put("Hybrid", ReaderMode.Hybrid);
        map.put("MaxMiller", ReaderMode.MaxMiller);
        map.put("MaxThroughput", ReaderMode.MaxThroughput);

        ReaderMode val = (ReaderMode)map.get(str);
        return val;
    }


    private static class ReportTags implements TagReportListener {

        public void onTagReported(ImpinjReader impinjReader, TagReport tagReport) {
            List<Tag> tags = tagReport.getTags();
            for (Tag t : tags) {

                String epc = t.getEpc().toString();
                System.out.print(epc);

            }
        }
    }

}

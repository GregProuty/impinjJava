package samples.com.example.sdksamples;

import com.impinj.octane.*;

import java.util.Scanner;
import java.util.*;



public class HelloOctaneSdk {

    public static void main(String[] args) {

        try {

//            System.out.println(Arrays.toString(args));
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

            report.setMode(ReportMode.Individual);

            // The reader can be set into various modes in which reader
            // dynamics are optimized for specific regions and environments.
            // The following mode, AutoSetDenseReader, monitors RF noise and interference and then automatically
            // and continuously optimizes the reader's configuration
            settings.setReaderMode(ReaderMode.AutoSetDenseReader);

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

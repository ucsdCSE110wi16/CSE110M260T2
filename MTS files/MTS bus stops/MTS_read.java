/**
 * @(#)MTS_read.java
 *
 *
 * @author
 * @version 1.00 2016/2/12
 */
import java.io.*;

public class MTS_read {

    /**
     * Creates a new instance of <code>MTS_read</code>.
     */
    public MTS_read() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

         // The name of the file to open.
        String fileName = "stops.txt";
		String outName = "stops_filter.txt";
        // This will reference one line at a time
        String line = null;
        String lat=null, lon=null, name=null, code=null;
        int index=0, last=0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            FileWriter fileWriter =
                new FileWriter(outName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

           line=bufferedReader.readLine();
           System.out.println("Format original: "+line+"\nwritten as:");

           index=line.indexOf(",");
           last=line.indexOf(",",index+1);
           System.out.println(line.substring(index+1,last));
           index=last;
		   last=line.indexOf(",",index+1);
   		   index=last;
		   last=line.indexOf(",",index+1);
		   index=last;
		   last=line.indexOf(",",index+1);
		   System.out.println(line.substring(index+1,last));
		   index=last;
		   last=line.indexOf(",",index+1);
		   System.out.println(line.substring(index+1,last));
		   index=last;
		   last=line.indexOf(",",index+1);
 		   index=last;
		   last=line.indexOf(",",index+1);
		   index=last;
		   last=line.indexOf(",",index+1);
		   System.out.println(line.substring(index+1,last));
            // Always close files.


            while((line = bufferedReader.readLine()) != null) {
                //TODO read and aprse
                // Note that write() does not automatically
            // append a newline character.
            //TODO write to file

            //random index=last; last=line.indexof() is skipping items we don't need
            //
			index=line.indexOf(",");
           last=line.indexOf(",",index+1);

           //lattitude of stop
           lat=line.substring(index+1,last);

           index=last;
		   last=line.indexOf(",",index+1);
   		   index=last;
		   last=line.indexOf(",",index+1);
		   index=last;
		   last=line.indexOf(",",index+1);

		   //longitude
		   lon=line.substring(index+1,last);

		   index=last;
		   last=line.indexOf(",",index+1);

		   //stopid
		   code=line.substring(index+1,last);

		   index=last;
		   last=line.indexOf(",",index+1);
 		   index=last;
		   last=line.indexOf(",",index+1);
		   index=last;
		   last=line.indexOf(",",index+1);
		   //stop name
		   name=line.substring(index+1,last);

		   //writes
		   bufferedWriter.write(lat+","+lon+","+code+","+name);
           bufferedWriter.newLine();

            }

            // Always close files.
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading/writing file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }






    }
}

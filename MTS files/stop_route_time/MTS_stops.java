/**
 * @(#)MTS_stops.java
 *
 *
 * @author
 * @version 1.00 2016/2/29
 */
import java.io.*;
import java.util.ArrayList;

public class MTS_stops {

    /**
     * Creates a new instance of <code>MTS_stops</code>.
     */
    public MTS_stops() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String fileName = "trips.txt";
		String fileName2="stops_filter.txt";
		String f3="stop_times.txt";
		String outName = "output.txt";
		// This will reference one line at a time
		String line = null;

		//to read in route, trip id
		String r=null,t=null,s=null,a=null;
		//lat long stopid name
		String lat=null,lon=null,stopID=null,name=null;
		//holds the routes
		ArrayList<Route> routes=new ArrayList<Route>();
		//reading many routes, increases capacity for faster time
		routes.ensureCapacity(20000);

		//holds the stops
		ArrayList<Stop> stops=new ArrayList<Stop>();
		//index in line
		int index=0, last=0;
		//counters
		int temp=0;
		int count=0;

		Route tempR=null;
		Stop tempS=null;


        try {

			//FILE READERS HERE

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

			// FileReader reads text files in the default encoding.
			FileReader fRead =
				new FileReader(fileName2);
			// Always wrap FileReader in BufferedReader.
			BufferedReader reader =
				new BufferedReader(fRead);

					// FileReader reads text files in the default encoding.
			FileReader fileReader2 =
				new FileReader(f3);
			// Always wrap FileReader in BufferedReader.
			BufferedReader buffread =
				new BufferedReader(fileReader2);


			//READING FROM FILE
			//getting routes and each which trip belongs to which route
		   	line=bufferedReader.readLine();
		   	System.out.println("Format original: "+line+"\nwritten as:");

		   	index=line.indexOf(",");
		   	last=line.indexOf(",",index+1);
			//route id found
		   	System.out.println(line.substring(index+1,last));

			//skip un-needed stuff
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	index=last;
		   	last=line.indexOf(",",index+1);
			//trip id found
		   	System.out.println(line.substring(index+1,last));

			while((line=bufferedReader.readLine())!=null){
				index=line.indexOf(",");
		   		last=line.indexOf(",",index+1);
				//route id found
			   	r=(line.substring(index+1,last));

				//skip un-needed stuff
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);

				//trip id found
			   	t=(line.substring(index+1,last));
			   	tempR=new Route(r);
				tempR.addTrip(t);
			   	if ( (temp=routes.indexOf(tempR))==-1){
			   		//if route not exist, add new route
			   		routes.add(tempR);

			   			//System.out.println("not found. Creating new route:"+r);
			   	}else{

			   		((Route)routes.get(temp)).addTrip(t);

			   	}

			   	count++;
			   	if (count%6000==0){
			   		System.out.println("trips count:"+count);
			   	}
			}//end while

			//close first file
			bufferedReader.close();



			//START READ FROM SECOND FILE
			//getting stops
			count=0;
		   	while((line=reader.readLine())!=null){
		   		index=0;
				last=line.indexOf(",",index+1);
				//lattitude
				lat=(line.substring(index+1,last));

			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//longitude
			   	lon=(line.substring(index+1,last));

			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//stopid
				stopID=(line.substring(index+1,last));

			   	index=last;
			   	last=line.length();
			   	//name
			   	name=(line.substring(index+1,last));
			   	stops.add(new Stop(lat,lon,stopID,name));

		   	}//end while

			//SECOND FILE CLOSE
			reader.close();

			line=buffread.readLine();
		   	System.out.println("Format original: \n"+line+"\nwritten as:\n");

			index=0;
			last=line.indexOf(",",index+1);
			//found trip id
			System.out.println(line.substring(index,last));
		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	// found time
		   	System.out.println(line.substring(index+1,last));
		   	index=last;
		   	last=line.indexOf(",",index+1);

		   	index=last;
		   	last=line.indexOf(",",index+1);
		   	//stopID
		   	System.out.println(line.substring(index+1,last));

			tempS=null;
			tempR=null;
			count=0;

			while((line=buffread.readLine())!=null){
		   		index=0;
				last=line.indexOf(",",index+1);
				//trip id
				t=(line.substring(index,last));

			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//arrival time
			   	a=(line.substring(index+1,last));

			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	index=last;
			   	last=line.indexOf(",",index+1);
			   	//stopID
			   	s=(line.substring(index+1,last));


			   	for(Stop sp:stops){
			   		if(s.equals(sp.getID())){
			   			tempS=sp;
			   			break;
			   		}
			   	}
			   	if(tempR==null || !(tempR.containsTrip(t))){
			   		for(Route rt:routes){

			   			if (rt.containsTrip(t)){
			   				tempR=rt;

			   				break;
			   			}
			   		}
			   	}
			   	tempR=tempS.addRoute(tempR.clone());
			   	tempR.addTime(a);
			   	//System.out.println(tempR.getID());



			   	count++;
			   	if (count%50000==0){
			   		System.out.println(count);
			   	}

			}//end while
			buffread.close();
			//END READING FILES

			//TESTING
			System.out.println("stops size: "+stops.size());
			for(Stop pot:stops){
				System.out.println(pot.routes.size());
			}

			count=0;
			//START WRITING
			System.out.println("writing.....");
			ArrayList<Route> tempRoutes=null;
			ArrayList<String>tempTimes=null;
			for(Stop st:stops){
				bufferedWriter.write(st.string()+"+");
           		bufferedWriter.newLine();
				st.printRoutes(bufferedWriter);


			}


			// Always close files.
			bufferedWriter.close();

		}//end try
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


class Stop{
	String lat,lon,id,name;
	public ArrayList<Route> routes;
	public Stop(String lat, String lon,String id,String name){
		this.lat=lat;
		this.lon=lon;
		this.id=id;
		this.name=name;
		routes=new ArrayList<Route>();
	}

	public Stop(String id){
		this.id=id;
	}
	public String string(){
		return(lat+","+lon+","+id+","+name);
	}

	public Route addRoute(Route r){
		if(routes.indexOf(r)!=-1){
			return (Route)routes.get(routes.indexOf(r));

		}else{
			routes.add(r);
			return r;
		}
	}

	public void printRoutes(BufferedWriter b){
		try{
			for(Route r:routes){
				b.write(r.getID()+"_");
				r.printTimes(b);
				b.newLine();
			}
		}
		catch(IOException ex) {
			System.out.println("Error");
		}
	}

		@Override
	public boolean equals(Object o){
		if (o instanceof Stop){
			Stop x=(Stop)o;
			if (x.getID().equals(id)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	@Override
	public int hashCode(){
		return Integer.parseInt(id);
	}

	public String getID(){
		return id;
	}

	public Route findR(Route r){
		return (Route)routes.get(routes.indexOf(r));
	}
	public Route findR(String t){
		for(Route r:routes){
			if(r.containsTrip(t)){
				return r;
			}
		}
		return null;
	}
}


class Route{
	ArrayList<String> times;
	public ArrayList<String> tripID;
	String id;
	public Route(String id){
		this.id=id;
		times=new ArrayList<String>();
		tripID=new ArrayList<String>();
	}

	public void addTrip(String id){
		tripID.add(id);
	}

	public void addTime(String time){
		times.add(time);
	}

	public Route clone(){
		return new Route(id);
	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Route){
			Route x=(Route)o;
			if (x.getID().equals(id)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public int hashCode(){
		return Integer.parseInt(id);
	}

	public String getID(){
		return id;
	}

	public boolean containsTrip(String t){
		if(tripID.indexOf(t)!= -1){
			return true;
		}else{
			return false;
		}
	}

	public ArrayList getTimesList(){
		return times;
	}

	public void printTimes(BufferedWriter b){
		try{
			for(String t:times){
			b.write(t+",");
			}
		}
		catch(IOException ex) {
			System.out.println("Error");
		}

	}
}//end route


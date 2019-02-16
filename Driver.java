
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Driver {
	static ArrayList<String> arrList = new ArrayList<>();
	static int frames;
	//public static LinkedHashMultimap<String, String>  SeedSet = LinkedHashMultimap.create();
	//HIST
	public static Map<String, String> FeatureMap = new LinkedHashMap<String, String>();
	public static ArrayList<String> SeedSet = new ArrayList<>();
	//SIFT
	public static Map<String, String> SIFTFeatureMap = new LinkedHashMap<String, String>();
	public static ArrayList<String> SIFTSeedSet = new ArrayList<>();
	
	public static int occurence = 0;
	
	
	//HIST
	//public static File tuple_file = new File("D:\\MavStream\\output\\readabletuples.txt");
	public static File seed_file = new File("D:\\Synthetic_Video_Gen\\input\\readable_seeds_hist.txt");
	public static File feature_file = new File("D:\\Synthetic_Video_Gen\\output_with_feature_vectors\\HISToutputwithfeatures.txt");
	//public static File tuple_file = new File(arrList.get(25));
	
	//SIFT
	public static File seed_fileSIFT = new File("D:\\Synthetic_Video_Gen\\input\\readable_seeds_sift.txt");
	public static File SIFTfeature_file = new File("D:\\Synthetic_Video_Gen\\output_with_feature_vectors\\SIFToutwithfeatures.txt");
	
	
	
	
    public static BufferedReader br = null;	
   
    public static String Algorithm = null;
    public static final int hist_size = 766; 
    static double[][] Arr = null;
	

	// Setting the Frame Dimensions;
	/**
	 * Active List contains objects that are Alive at the moment in a segment.
	 * Bucket is a pool of objects from which we use Uniform Distribution to
	 * select. K Objects based on some criteria.
	 * 
	 * @param args
	 *            Basic String Args	 *            
	 *            */	
    
    public static String Type_of_Algorithm(String algo) {
 	   String algorithm ;
 	  
 	   algorithm = (arrList.get(23));
 	   
 	   if(algorithm.equals("Human")) {
 		   algorithm = "HIST";
 		  System.out.println("Algo is " + algorithm);
 	   }
 	   else {
 		   algorithm = "SIFT";
 		  System.out.println("Algo is " + algorithm);
 	   }   
 	   return algorithm;
    }	
 	
    
    
    public static int Frame_Count(int fr) {
 	   int segments = (Integer.parseInt(arrList.get(3))*60)/10;
 		//System.out.println("Segments is " + segments);
 		
 		int totalframes = segments * 30;
 		//System.out.println("Total frames is "+ totalframes);
 		return totalframes;
    }
    
    //HIST
    public static ArrayList<String> readSeedFile(File seed_file) throws IOException {
    	
    	if(Type_of_Algorithm(Algorithm).equals("HIST")) {
    	try {
 		   br = new BufferedReader(new FileReader(seed_file));
 			String st=null;
 			String[] s= null;
 			st= br.readLine();
 			int count = 0;
 					
 			while ((st=br.readLine()) !=null) {					
 				s = st.split(" ");
 				String c1 = Integer.toString(count);
 				SeedSet.add(s[0].toString());											    
 			}			
 			br.close();			
 	   }
 		catch (Exception e) {
 			System.out.println("Error: " + e.getMessage());
 		}
 	return SeedSet;
 	}else {
 		
 		System.out.println("Seed is SIFT");
 		try {
  		   br = new BufferedReader(new FileReader(seed_fileSIFT));
  			String st=null;
  			String[] s= null;
  			st= br.readLine();
  			int count = 0;
  					
  			while ((st=br.readLine()) !=null) {					
  				s = st.split(" ");
  				String c1 = Integer.toString(count);
  				SIFTSeedSet.add(s[0].toString());											    
  			}			
  			br.close();			
  	   }
  		catch (Exception e) {
  			System.out.println("Error: " + e.getMessage());
  		}
 		return SIFTSeedSet;
 	}
    }
   
    //HIST
    public static void readFeatures(File read_features) throws IOException{
    	if(Type_of_Algorithm(Algorithm).equals("HIST")) {
    	try {    		
 		   br = new BufferedReader(new FileReader(feature_file));
 			String st=null;
 			String[] s= null;
 			st= br.readLine();
 			//int count = 1;
 						
 			while ((st=br.readLine()) !=null) {					
 				s = st.split(" ");
 				String c1 = s[0].toString() + "," + s[1].toString();
 				FeatureMap.put(c1, s[2].toString());
 											    
 			}
 			br.close();	    		
 			/*for (Map.Entry entry: FeatureMap.entrySet()) {
				System.out.println(entry.getKey().toString() + "......." + entry.getValue().toString());
	   }*/
 	   }
 		catch (Exception e) {
 			System.out.println("Error: " + e.getMessage());
 		}
    }else {
    	System.out.println("Feature File is SIFT");
    	try {    		
  		   br = new BufferedReader(new FileReader(feature_file));
  			String st=null;
  			String[] s= null;
  			st= br.readLine();
  			//int count = 1;  						
  			while ((st=br.readLine()) !=null) {					
  				s = st.split(" ");
  				String c1 = s[0].toString() + "," + s[1].toString();
  				SIFTFeatureMap.put(c1, s[2].toString());
  											    
  			}
  			br.close();	    		
  			/*for (Map.Entry entry: FeatureMap.entrySet()) {
 				System.out.println(entry.getKey().toString() + "......." + entry.getValue().toString());
 	   }*/
  	   }
  		catch (Exception e) {
  			System.out.println("Error: " + e.getMessage());
  		}
    	
    }
    }
    
   
  
   //SIFT  
  /* public static void readSIFTFeatures(File read_features) throws IOException{
   	try {
   		
		   br = new BufferedReader(new FileReader(SIFTfeature_file));
			String st=null;
			String[] s= null;
			st= br.readLine();
			//int count = 1;
						
			while ((st=br.readLine()) !=null) {					
				s = st.split(" ");
				//System.out.println(s[3].toString());
				String c1 = s[0].toString() + "," + s[1].toString();
				//System.out.println("C1 is " + c1);
				FeatureMap.put(c1, s[2].toString());
											    
			}
			br.close();	
   		
			for (Map.Entry entry: FeatureMap.entrySet()) {
				System.out.println(entry.getKey().toString() + "......." + entry.getValue().toString());
	   }
	   }
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
   }
    
    public static void readSIFTSeedFile(File seed_fileSIFT) throws IOException {
   	   try {
   		   String algo = null;
   		   
   		   br = new BufferedReader(new FileReader(seed_fileSIFT));
   			String st=null;
   			String[] s= null;
   			st= br.readLine();
   			int count = 0;
   					
   			while ((st=br.readLine()) !=null) {					
   				s = st.split(" ");
   				String c1 = Integer.toString(count);
   				SIFTSeedSet.add(s[0].toString());	
   				  			}			
   			br.close();		
   			
   	   }
   		catch (Exception e) {
   			System.out.println("Error: " + e.getMessage());
   		}
   	//return SIFTSeedSet;
   	}
*/   
    
  
   //Perturb
   public static void perturb() {	      
	   String no_of_pertrubations = arrList.get(25);	
	  
		   //initialise matrix
	   if(Type_of_Algorithm(Algorithm) == "HIST") {
	 					   Mat source = new Mat(252,3,CvType.CV_64F);
						   Mat destination = new Mat(source.rows(),source.cols(),source.type());						   
						 //extract the features from the map and put it in source matrix
						   for (Map.Entry entry: FeatureMap.entrySet()) {
						   String[] seperated_features = entry.getValue().toString().substring(1,entry.getValue().toString().length()-1).split(",");						   
						// System.out.println(seperated_features.length);
						   Arr = new double[1][seperated_features.length];						   
								  for( int l=0;l<=seperated_features.length-1;l++) {										
									  Arr[0][l] = Double.parseDouble(seperated_features[l]); 
									  source.put(0,l,Arr[0][l]);
								  }							 
								  //pertrub the amount required times, mentioned in config
									  for (int i =0; i<= Integer.parseInt(no_of_pertrubations); i++) {
									  Imgproc.GaussianBlur(source, destination, new Size(7,7), 0);
									  }
									  String s_final  =  entry.getKey().toString() + destination.dump().toString();
									  //System.out.println("HIST...." + s_final);
									  log_to_output.log_output(s_final);
		}
   }else {
	   System.out.println("Perturb a SIFT file");
	   Mat SIFTsource = new Mat(1,128,CvType.CV_64F);
	   Mat SIFTdestination = new Mat(SIFTsource.rows(),SIFTsource.cols(),SIFTsource.type());						   
	 //extract the features from the map and put it in source matrix
	   for (Map.Entry entry: SIFTFeatureMap.entrySet()) {
	   String[] seperated_features = entry.getValue().toString().substring(1,entry.getValue().toString().length()-1).split(",");						   
	// System.out.println(seperated_features.length);
	   Arr = new double[1][seperated_features.length];						   
			  for( int l=0;l<=seperated_features.length-1;l++) {										
				  Arr[0][l] = Double.parseDouble(seperated_features[l]); 
				  SIFTsource.put(0,l,Arr[0][l]);
			  }							 
			  //pertrub the amount required times, mentioned in config
				  for (int i =0; i<= Integer.parseInt(no_of_pertrubations); i++) {
				  Imgproc.GaussianBlur(SIFTsource, SIFTdestination, new Size(7,7), 0);
				  }
				  String s_final  =  entry.getKey().toString() + SIFTdestination.dump().toString();
				  //System.out.println("HIST...." + s_final);
				  log_to_outputSIFT.log_output(s_final);
	   	}
	   
   }
 }
   
   //SIFT
  /* public static void perturb_SIFT() {
	   String  algo = null;	   
	   String no_of_pertrubations = arrList.get(25);
						   Mat sourceSIFT = new Mat(1,128,CvType.CV_64F);
						   Mat destinationSIFT = new Mat(sourceSIFT.rows(),sourceSIFT.cols(),sourceSIFT.type());
						   for (Map.Entry entry: SIFTFeatureMap.entrySet()) {
							   String[] seperated_features = entry.getValue().toString().substring(1,entry.getValue().toString().length()-1).split(",");						   
							// System.out.println(seperated_features.length);
							   Arr = new double[1][seperated_features.length];						   
									  for( int l=0;l<=seperated_features.length-1;l++) {										
										  Arr[0][l] = Double.parseDouble(seperated_features[l]); 
										  sourceSIFT.put(0,l,Arr[0][l]);
									  }							 
									  //pertrub the amount required times, mentioned in config
										  for (int i =0; i<= Integer.parseInt(no_of_pertrubations); i++) {
										  Imgproc.GaussianBlur(sourceSIFT, destinationSIFT, new Size(7,7), 0);
										  }
										  String s_final  =  entry.getKey().toString() + destinationSIFT.dump().toString();
										  System.out.println("SIFT ....." + s_final);
										  log_to_outputSIFT.log_output(s_final);
						   }
	   	//}
	 
   } // end perturb							  
*/					 
	  

	
	
	
	public static void main(String[] args) throws IOException {
		// New lines of code to be added below
				// The code to read from file and parameterize few things
				//File config = new File("D:\\Adv.DB\\ParseGeneratorFromSri\\ParseGenerator\\ParseGenerator\\SyntheticVideoDataGenerator\\src\\config.txt");
				File config = new File("D:\\MavStream\\ParseGenerator\\ParseGenerator\\SyntheticVideoDataGenerator\\src\\config.txt");

				try {
					BufferedReader br = new BufferedReader(new FileReader(config));
					String st;
					while ((st = br.readLine()) != null) {
						String s[] = st.split("\\r?\\n");
						for (int i = 0; i < s.length; i++) {
							arrList.add(s[i]);
						}
					}
					br.close();
				}
				catch (Exception e) {
				}
				
				if(Type_of_Algorithm(Algorithm).equals("HIST")) {
					System.out.println("Algo is HIST");					
				}else {
					System.out.println("Algo is SIFT");
				}

		//HIST
		final ArrayList<String> featMap = new ArrayList<String>();
		featMap.addAll(readSeedFile(seed_file));
		
		//SIFT
		final ArrayList<String> SIFTfeatMap = new ArrayList<String>();
		SIFTfeatMap.addAll(readSeedFile(seed_fileSIFT));
		
		
		// Code to initialize parameters end here
		// New code added ends here
		ArrayList<ObjectInstance> activeList = new ArrayList<ObjectInstance>();
		System.out.println(arrList);
		ObjectCreator objectCreator = new ObjectCreator(Integer.parseInt(arrList.get(15)),
				Integer.parseInt(arrList.get(17)));
		HashMap<Integer, Integer> lifeTime = objectCreator.getObjectLifeTimeHashMap();
		HashMap<Integer, Integer> deadTime = objectCreator.getObjectDeadTimeHashMap();
		HashMap<Integer, Integer> objectBoolean = objectCreator.getBooleanObject();
		ArrayList<Integer> changeDeadTime = new ArrayList<>();
		 System.out.println("Life time is");
		System.out.println(lifeTime);
		 System.out.println("deadTime is");
		System.out.println(deadTime);
		 System.out.println("Boolean value");
		 System.out.println(objectBoolean);
		// Instantiating Segment Generator Class.
		SegmentGenerator sGenerator = new SegmentGenerator();
		// To send mean and sd values to Segment Generator, which in turn calls
		// the noofsegments in distribution utility
		if(Integer.parseInt(arrList.get(7))> Integer.parseInt(arrList.get(9))){
			
		
		sGenerator.receiveMeanandSd(Integer.parseInt(arrList.get(7)), Integer.parseInt(arrList.get(9)));
		// The ArrayList below is to hold the Segment Objects returned from
		// sGenerator.
		ArrayList<Segment> segmentList = new ArrayList<Segment>();
		segmentList = sGenerator.getSegmentsAfterInitialisation();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long timestamp1 = timestamp.getTime();
		
		for (Segment seg : segmentList) {
			
			// Max Number Of Objects Per Segment.
			int avgSegObjects = seg.getAverageNoOfObjects();
			System.out.println("SEG __ " + avgSegObjects);
			// No of Objects in the Active List.
			int activeListLength = activeList.size();
			//System.out.println("Active List Length " + activeListLength);
			// Temp Array List Created per segment.
			ArrayList<ObjectInstance> a1 = new ArrayList<ObjectInstance>();
			if (avgSegObjects > activeListLength) {
				a1 = objectCreator.getKObjects(avgSegObjects - activeListLength, activeList);
				// We need a function here that will initialise the other
				// parameters for an Object in the
				// Active List.
				InitialiseObjectParamenters(a1);
				activeList.addAll(a1);
				//activeList.addAll(Feature_Map(feature));
			} else if (avgSegObjects < activeListLength) {
				// System.out.println("In the second loop");
				// We need to figure out a method to delete an Element from the
				// the Active List.
				// We Remove the Object which has the least # leftOver#
				Collections.sort(activeList, new MyComparator());
				int elementsToBeRemoved = activeListLength - avgSegObjects;
				for (int i = 0; i < elementsToBeRemoved; i++) {
					// To Remove the Last Element From the List We need to use
					// the Following.
					// :: Earlier we were using remove which was followed by
					// Left Shift in an
					// arrayList.
					ObjectInstance objTemp = activeList.get(activeList.size() - 1);
					int xxxx = objTemp.getObjectId();
					objTemp.setLeftOver(lifeTime.get(xxxx));
					activeList.remove(activeList.size() - 1);
				}
			}
			if(!changeDeadTime.isEmpty()){
				objectCreator.setObjectIdNottoNull(changeDeadTime);
				changeDeadTime.clear();
			}
			// Time to add Frame Code here. Every Timestamp must be present in
			// 30 frames.
			// Considering 30 FPS, Frames == 270 Total Frames
			//System.out.println(arrList.get(3));
			
	
		
			int segments = (Integer.parseInt(arrList.get(3))*60)/10;
					
			int totalframes = segments * 30;
			
			
			//System.out.println("Total frames is "+ totalframes);
			
			
			//System.out.println(frames);
			

			for (int frame = 1; frame <= totalframes; frame++) {
				for (Iterator<ObjectInstance> iterator = activeList.iterator(); iterator.hasNext();) {
					ObjectInstance obj = iterator.next();
					int m = obj.getLeftOver();
					m--;
					if (m > 0) {
						obj.setLeftOver(m);
						movement(obj);
					} else {
						int ppp = obj.getObjectId();
						obj.setLeftOver(lifeTime.get(ppp));
						iterator.remove();
					}
					// function to move the object around based on the direction
					// parameter.
					if (m == 1) {
						//System.out.println(obj.getObjectId() + " Lifetime over");
						objectCreator.getObjectIdTosetToNull(obj.getObjectId());
						changeDeadTime.add(obj.getObjectId());
					}
				}
				// objectCreator.setObjectIdNottoNull(obj.getObjectId());
				if ((frame - 1) % 30 == 0) {
					timestamp1 = timestamp1 + 1;
				}			
			
				
				
				
				for (ObjectInstance jj : activeList) {	
					
					File out = new File("D:\\output\\out.txt");
					try {
						PrintWriter outputstream = new PrintWriter(new FileWriter(out, true));
						// outputstream.println("SEG ["+seg.getSegId()+
						// "-"+frame+"] Object Id "+jj.getObjectId()+" Left
						// Over: {"+jj.getLeftOver()+"}time "+timestamp1+"
						// Bounding Box [ "+jj.getTop_left_x()+" , "+
						// jj.getTop_left_y()+" , "+jj.getLength()+" ,
						// "+jj.getBreadth()+" ]" );
						String output =null;
						output = seg.getSegId() + "," + frame + "," + jj.getObjectId() + ","
								+ jj.getLeftOver() + "," + timestamp1 + "," + jj.getTop_left_x() + ","
								+ jj.getTop_left_y() + "," + jj.getLength() + "," + jj.getBreadth() ;	
						
						outputstream.println(output);
						// outputstream.println(seg.getSegId()+","+frame+","+jj.getObjectId(`));
						// System.out.println("SEG ["+seg.getSegId()+
						// "-"+frame+"] Object Id "+jj.getObjectId()+" Left
						// Over: {"+jj.getLeftOver()+"}time "+timestamp1+"
						// Bounding Box [ "+jj.getTop_left_x()+" , "+
						// jj.getTop_left_y()+" , "+jj.getLength()+" ,
						// "+jj.getBreadth()+" ]");

						outputstream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String[] feature = null;
				//System.out.println("After each segment");;
				} // End of iterating over Active list for printing to console
				
			} // End of Frame For loop
			//System.out.println("###################################################");
			//
			
			//;
		}
		}// End of segment For loop
		else{
			System.out.println("There was some error in the configuration file");
			System.out.println("Hint : Check the mean and sd values");
			System.exit(0);
		}
		//System.out.println(" New Segment  Spanned");
		
		
			
		int segments = (Integer.parseInt(arrList.get(3))*60)/10;		
		int totalframes = segments * 30;
		
		
					//System.out.println(addFeat);
					//HIST
		if(Type_of_Algorithm(Algorithm).equals("HIST")) {
					for (int segmentID = 1; segmentID<= segments; segmentID ++) {	
						System.out.println("Segment is "+ segmentID);
													
							for(String addFeat : featMap) {
								for(int frame =1 ; frame<= totalframes; frame++) {
							try {
								File out1 = new File("D:\\Synthetic_Video_Gen\\output_with_feature_vectors\\HISToutputwithfeatures.txt");
								PrintWriter outputstream = new PrintWriter(new FileWriter(out1, true));	
								
								String output =  segmentID+ " " + frame + " " + addFeat;
								outputstream.println(output);
								outputstream.close();
							}
							catch (IOException e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					}
					
				}
		}else {//SIFT	
					for (int segmentID = 1; segmentID<= segments; segmentID ++) {	
						System.out.println("Segment is "+ segmentID);
													
							for(String addFeat : SIFTfeatMap) {
								for(int frame =1 ; frame<= totalframes; frame++) {
							try {
								File out1 = new File("D:\\Synthetic_Video_Gen\\output_with_feature_vectors\\SIFToutwithfeatures.txt");
								PrintWriter outputstream = new PrintWriter(new FileWriter(out1, true));	
								
								String output =  segmentID+ " " + frame + " " + SIFTfeatMap;
								outputstream.println(output);
								outputstream.close();
							}
							catch (IOException e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					}
					
				}
		}
		
		
		
		
		
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


readFeatures(feature_file);
readSeedFile(seed_file);
perturb();

//readSIFTFeatures(SIFTfeature_file);
//readSIFTSeedFile(seed_fileSIFT);
//perturb_SIFT();
		
		
} // End of Main Function

	/**
	 * Movement Of the Object in the Canvas. Object Spawned From a Side Can Move
	 * in 3 Directions.
	 * 
	 * @param obj
	 *            Object Instance
	 */
	private static void movement(ObjectInstance obj) {
		// Random Assignment Attached to Object.
		int pixelMovement = obj.getSpeed();
		int startEdge = obj.getStartEdge();
		int dir = obj.getDirection();

		if (startEdge == 0) { // LEFT 0
			// when the object enters from left(start edge = 0), it can either
			// go E(0), NE(1) or SE(2)
			// Including the movement of an object in all three directions
			if (dir == 0) {
				// Direction is East - x is incremented by pixel movement and y
				// remains constant
				obj.setTop_left_x(obj.getTop_left_x() + pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y());
			} else if (dir == 1) {
				// Direction is North East - Both the coordinates would be
				// incremented by pixel movement
				obj.setTop_left_x(obj.getTop_left_x() + pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() + pixelMovement);
			} else {
				// Direction is South-East - x coordinate would be incremented
				// and y cooridnate would be decremented by pixel movement
				obj.setTop_left_x(obj.getTop_left_x() + pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() - pixelMovement);
			}
		}

		if (startEdge == 1) {
			// When the object enters from the bottom(start edge =1) of the
			// screen it can either go N(0), NW(1), NE(2)
			if (dir == 0) {
				// Direction is North- x remains constant and y is incremented
				// by pixel movement
				obj.setTop_left_x(obj.getTop_left_x());
				obj.setTop_left_y(obj.getTop_left_y() + pixelMovement);
			} else if (dir == 1) {
				// Direction is North East - x would decrease and y would
				// increase by pixel movement
				obj.setTop_left_x(obj.getTop_left_x() - pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() + pixelMovement);
			} else {
				// Direction is North West - x and y would increase by pixel
				// movement
				obj.setTop_left_x(obj.getTop_left_x() + pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() + pixelMovement);
			}
		}

		if (startEdge == 2) {
			// when the object enters from Right(start edge = 2), it can either
			// go W(0), NW(1) or SW(2)
			if (dir == 0) {
				// Direction is West
				obj.setTop_left_x(obj.getTop_left_x() - pixelMovement);// y-coordinate
																		// would
																		// remain
																		// constant,
																		// but
																		// x-coordinate
																		// would
																		// be
																		// decremented
																		// by
																		// pixel
																		// movement
				obj.setTop_left_y(obj.getTop_left_y());
			} else if (dir == 1) {
				// Direction is North West - X would decrement and Y would
				// increment
				obj.setTop_left_x(obj.getTop_left_x() - pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() + pixelMovement);
			} else {
				// Direction is South-West - y and x would be decremented by
				// pixel movement.
				obj.setTop_left_x(obj.getTop_left_x() - pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() - pixelMovement);
			}

		}

		if (startEdge == 3) {
			// When the object enters from Top- It can either go S(0),
			// SW(1),SE(2)
			if (dir == 0) {
				// Direction is South - x would remain constant and y would
				// decrease by pixel movement
				obj.setTop_left_x(obj.getTop_left_x());
				obj.setTop_left_y(obj.getTop_left_y() - pixelMovement);
			} else if (dir == 1) {
				// Direction is South-West - x and y would decrease by pixel
				// movement
				obj.setTop_left_x(obj.getTop_left_x() - pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() - pixelMovement);
			} else {
				// Direction is South-East- x would increase and y would
				// decrease by pixel movement
				obj.setTop_left_x(obj.getTop_left_x() + pixelMovement);
				obj.setTop_left_y(obj.getTop_left_y() - pixelMovement);
			}

		}
	}

	/**
	 * This method Initialises Various Object Attributes Like Speed, Side,
	 * Length, Side ETC.
	 * 
	 * @param a1
	 *            Object instance Array Of All Active Objects
	 */
	private static void InitialiseObjectParamenters(ArrayList<ObjectInstance> a1) {
		// TODO Auto-generated method stub
		DistributionUtility distributionUtility = new DistributionUtility();
		for (ObjectInstance a : a1) {
			int side = distributionUtility.getStartEdge();
			a.setStartEdge(side);
			int speed = distributionUtility.getObjectSpeed(arrList.get(13));
			a.setSpeed(speed);
			if (side == 0 || side == 2) {
				// Left 0 and right 2
				// Setting the start pixel point of the object
				int pixelPosition = distributionUtility.getVerticalStartPoint();
				a.setStartPixel(pixelPosition);
				a.setTop_left_y(pixelPosition);
				// Setting the size of the object
				int sizeMultiplier = distributionUtility.getObjectSize(arrList.get(11));
				// initial length of object
				a.setLength(5 * sizeMultiplier);
				a.setBreadth(5 * sizeMultiplier);
				// Setting the direction of movement of the object.
				int direction = distributionUtility.getObjectDirection();
				a.setDirection(direction);
				if (side == 2) {
					// Right direction
					a.setTop_left_x(960 + a.getLength());
				}
				if (side == 0) {
					// Left Direction
					a.setTop_left_x(0 - a.getLength());
				}
			}
			if (side == 1 || side == 3) {
				// top 3 and bottom 1
				int pixelPosition = distributionUtility.getHorizontalStartPoint();
				a.setStartPixel(pixelPosition);
				// a.setTop_left_x(pixelPosition);
				// Setting the size of object
				int sizeMultiplier = distributionUtility.getObjectSize(arrList.get(11));
				a.setLength(a.getLength() * sizeMultiplier);
				a.setBreadth(a.getBreadth() * sizeMultiplier);
				// Setting the direction of movement of object
				int direction = distributionUtility.getObjectDirection();
				a.setDirection(direction);
				if (side == 1) {
					// Bottom direction
					a.setTop_left_y(0 - a.getBreadth());
				}
				if (side == 3) {
					// Top Direction
					a.setTop_left_y(640 + a.getBreadth());
				}
			}
		}
	}
}
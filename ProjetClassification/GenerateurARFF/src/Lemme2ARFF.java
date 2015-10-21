import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Lemme2ARFF {

	public String  arffContent;
	public ArrayList<String> typesValides;
	
	public Lemme2ARFF(String foldersPath, String targetPath){
		
		typesValides = new ArrayList<String>();
		typesValides.add("NN");
		typesValides.add("NNS");
		typesValides.add("VB");
		typesValides.add("VBN");
		typesValides.add("VBD");
		typesValides.add("VBG");
//		typesValides.add("JJ");
//		typesValides.add("JJR");
//		typesValides.add("JJS");
		
		arffContent = "% Ceci représente la classification de séries en fonction de leur genre.";
		arffContent += "\n\n@relation series";
		arffContent += "\n\n@attribute nom_fichier string";
		arffContent += "\n@attribute contenu string";
		arffContent += "\n@attribute classe {animation, crime, medical}";
		arffContent += "\n\n@data";
		
		File file = new File("");
		ArrayList<String> folderNames = new ArrayList<String>();
		folderNames.add("animation");
		folderNames.add("crime");
		folderNames.add("medical");
		
		for(String folderName: folderNames){
			File[] files= getListOfFiles(foldersPath + "/" + folderName);
			for(File f: files){
				
				try {
					String fileName = f.getAbsolutePath();
					BufferedReader bf = new BufferedReader(new FileReader(fileName));
					
					String ligne = bf.readLine();
					String fileContent = new String();
					
					while (ligne != null)
					{
						String[] triplet = ligne.split("\t");
						
						if (motValide(triplet))
						{
							fileContent = fileContent + triplet[triplet.length-1] + " ";
						}
						
						
						ligne = bf.readLine();
					}
					
					arffContent += "\n\'" + fileName.split("/")[fileName.split("/").length - 1] + "\',\'"+ fileContent.replace("\'", "\\'") + "\'," + folderName;
					System.out.println(arffContent);
					
					bf.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		FileWriter fw;
		try {
			
			file = new File(targetPath);
			 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} 
			
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(arffContent);
			bw.close();
			
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static String readFile(String path, Charset encoding) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	
	public static File[] getListOfFiles(String folderPath){
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		System.out.println(folderPath);
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }	
	    
	    return listOfFiles;
	}
	
	public boolean motValide(String[] triplet)
	{
		return (typesValides.contains(triplet[triplet.length-2]) && !triplet[triplet.length-1].equals("<unknown>"));
	}
	
}

import java.io.File;


public class Main {
	
	public static void main(String[] args){
//		String projectPath = "/auto_home/llafon/Cours/semestre8/ECD/ECD/corpus";
//		ARFF arff = new ARFF(projectPath, projectPath + "/result.arff");
		
		String projectPath = "/home/asapinux/Bureau/Semestre_2/ECD/Projet/ECD/corpus";
		Lemme2ARFF l2a = new Lemme2ARFF(projectPath+"/lemmatisation",projectPath+"/resultLemme.arff");
	}
}


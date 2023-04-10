package edu.ntnu.paths.FileHandling;


import edu.ntnu.paths.StoryDetails.Link;
import edu.ntnu.paths.StoryDetails.Story;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReadFile {

    public static void main(String[] args) {

        String fileName = "The last person on earth";
        try {
            File myObj = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
                    + "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
                    + "java" + System.getProperty("file.separator")  + "edu" + System.getProperty("file.separator") +
                    "ntnu" + System.getProperty("file.separator") + "paths" + System.getProperty("file.separator")  + "FileHandling"
                    +  System.getProperty("file.separator") + fileName + ".paths");

            Scanner myReader = new Scanner(myObj);
            StringBuilder storyInfo = new StringBuilder();

            while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 storyInfo.append(data + "\n");
            }
            myReader.close();
            getStory(storyInfo.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Story getStory(String storyInfo){

        String[] storyInfoArr = storyInfo.split("\n");

 String[] storyArr = Arrays.stream(storyInfoArr).filter((s) -> !Objects.equals(s, " ")).toArray(String[]::new);
       // System.out.println(Arrays.toString(storyArr));


        int titleOpeningPassageIndex = 0;
        int contentOpeningPassageIndex = 0;


        for (int i = storyArr.length - 1 ; i >= 0 ; i--) {
            if(storyInfoArr[i].contains("::"))  {
                titleOpeningPassageIndex = i;
                break;
            }
        }

        contentOpeningPassageIndex = titleOpeningPassageIndex + 1;


        List<Link> linksFromOpeningPassage = new ArrayList<>();
        int linksOpeningPassageIndex = contentOpeningPassageIndex + 1;
       // System.out.println(linksOpeningPassageIndex);

        for (int i = linksOpeningPassageIndex; i <= storyArr.length; i++) {
            String[] linkInfo =  storyInfoArr[i].split("[\\s@&.?$+-]+");
           /* System.out.println(linkInfo[0].replaceAll("[^a-zA-Z]+", ""));
            System.out.println(linkInfo[1].replaceAll("[^a-zA-Z]+", ""));*/
            System.out.println(Arrays.toString(linkInfo));


        }

      /*  Link linkFromOpeningPassage = LinkBuilder.newInstance()
                .set*/


       /* Passage openingPassage = PassageBuilder.newInstance()
                .setTitle(storyInfoArr[titleOpeningPassageIndex])
                .setContent(storyInfoArr[contentOpeningPassageIndex])
                .setLinks()*/


        return null;
    }

}
/*  String[] linkInfo =  storyInfoArr[i].split(" ");
            System.out.println(linkInfo[0].replaceAll("[^a-zA-Z]+", ""));
            System.out.println(linkInfo[1].replaceAll("[^a-zA-Z]+", ""));
            */
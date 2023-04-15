package edu.ntnu.paths.FileHandling;

import edu.ntnu.paths.Actions.GoldAction;
import edu.ntnu.paths.Actions.HealthAction;
import edu.ntnu.paths.Actions.InventoryAction;
import edu.ntnu.paths.Actions.ScoreAction;
import edu.ntnu.paths.StoryDetails.*;
import org.junit.jupiter.api.BeforeEach;

class WriteFileTest {

    Story story, copyStoryWriteToFile;

    GoldAction goldActionIncrease10 = new GoldAction();

    GoldAction goldActionIncrease20 = new GoldAction();

    ScoreAction scoreActionIncrease10 = new ScoreAction();

    InventoryAction inventoryActionSword = new InventoryAction();
    HealthAction healthActionIncrease10 = new HealthAction();
    Passage openingPassage, porchPassage, kitchenPassage;

    Link linkToPorchPassage, linkToKitchenPassage;

    WriteFile writeFile = new WriteFile();


    @BeforeEach
    void setUp() {
        openingPassage = PassageBuilder.newInstance()
                .setTitle("Beginning")
                .setContent("You woke up and felt at once all alone")
                .build();

        porchPassage = PassageBuilder.newInstance()
                .setTitle("The porch")
                .setContent("There are no people outside, and its all so quite")
                .build();

        kitchenPassage = PassageBuilder.newInstance()
                .setTitle("The Kitchen")
                .setContent("The kitchen has a wierd smell, and on the counter lies a letter")
                .build();

        story = StoryBuilder.newInstance()
                .setTitle("The last person on earth")
                .setOpeningPassage(openingPassage)
                .build();

        story.addPassage(openingPassage);

        story.addPassage(porchPassage);

        story.addPassage(kitchenPassage);


        linkToPorchPassage = LinkBuilder.newInstance()
                .setText("Go outside")
                .setReference("The porch")
                .build();

        linkToKitchenPassage = LinkBuilder.newInstance()
                .setText("Go to the kitchen")
                .setReference("The kitchen")
                .build();

        openingPassage.addLink(linkToPorchPassage);
        openingPassage.addLink(linkToKitchenPassage);

        porchPassage.addLink(linkToKitchenPassage);

        goldActionIncrease10.goldAction(10);
        healthActionIncrease10.healthAction(10);
        scoreActionIncrease10.scoreAction(10);
        inventoryActionSword.inventoryAction("Sword");

        goldActionIncrease20.goldAction(20);

        linkToPorchPassage.addAction(goldActionIncrease10);
        linkToPorchPassage.addAction(healthActionIncrease10);
        linkToPorchPassage.addAction(scoreActionIncrease10);
        linkToPorchPassage.addAction(inventoryActionSword);
        linkToPorchPassage.addAction(goldActionIncrease10);

        linkToKitchenPassage.addAction(goldActionIncrease20);

        copyStoryWriteToFile = new Story(story);

        writeFile.writeGameToFile(copyStoryWriteToFile);
    }

    //Teste at det er tittel på første linje, også mellomrom hvis ikke kaste exception

    //Teste at

    //passa på at tekst strenger til objektene ikke kan inneha annet enn spesial tegn

}
package sample.data.jpa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.assertj.core.util.Lists;

import sample.data.jpa.model.Question;
import sample.data.jpa.utils.PointsAvg;
import sample.data.jpa.web.TestController;


public class Annealing {

    private List<Question> testToServe = new ArrayList<>();

    private List<Question> allQuestionsFromGroup = new ArrayList<>();

    private static final double E = 2.71828152;

    private Random random;

    private String courseGroup;

    private final static float MIN_TEMP = 0.09f;

    public List<Question> findTestsWithSamePointsAmount(List<Question> questsFromGroup, String courseGenere) {
        double temp = 50;
        this.courseGroup = courseGenere;
        allQuestionsFromGroup = questsFromGroup;
        random = new Random(System.nanoTime());
        testToServe = splitQuestionList();

        while (temp > MIN_TEMP) {
            List<Question> beforeSwap = Lists.newArrayList(testToServe);
            List<Question> beforeSwapOtherQuest = Lists.newArrayList(allQuestionsFromGroup);
            swap();
            List<Question> afterSwap = Lists.newArrayList(testToServe);
            if (PointsAvg.isCloser(beforeSwap, afterSwap, courseGenere)) {
                testToServe = beforeSwap;
                allQuestionsFromGroup = beforeSwapOtherQuest;
            } else if (random.nextFloat() * 1.0 < probality(temp, beforeSwap, afterSwap)) {
                testToServe = beforeSwap;
            }
            temp = cooling(temp);
        }
        return testToServe;
    }

    private double probality(double temp, List<Question> beforeList, List<Question> afterList) {
        int beforePoints = PointsAvg.getPointsFromTest(beforeList);
        int afterPoints = PointsAvg.getPointsFromTest(afterList);

          return Math.pow(E, -(PointsAvg.accuracy(afterPoints, this.courseGroup) - PointsAvg.accuracy(beforePoints, this.courseGroup)));
    }

    private double cooling(double temp) {
        return temp * 0.55;
    }

    private List<Question> splitQuestionList() {
        if (TestController.QUEST_AMOUNT > allQuestionsFromGroup.size()) {
            throw new IllegalArgumentException(String.valueOf(allQuestionsFromGroup.size()));
        }

        List<Question> outputList = new ArrayList<Question>();

        outputList = TestCreator.addWithoutDuplicates(allQuestionsFromGroup);

        allQuestionsFromGroup.removeAll(outputList); // deleted questions unused
                                                     // in first test

        return outputList;
    }

    private void swap() {
        
        int range = (testToServe.size() - 1) > 0 ? testToServe.size() - 1 : 1;
        int indexToSwap = random.nextInt(range);
        range = (allQuestionsFromGroup.size() - 1) > 0 ? allQuestionsFromGroup.size() - 1 : 1;
        int indexToSwapWhith = random.nextInt(range);

        Question questToSwap = testToServe.remove(indexToSwap);
        Question questToSwapWhith = allQuestionsFromGroup.remove(indexToSwapWhith);

        allQuestionsFromGroup.add(questToSwap);
        testToServe.add(questToSwapWhith);
    }
}
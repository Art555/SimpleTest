package diffprocessor;
public class Processor {

    public Processor(long limit) {

    }

    public void doProcess(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput) {

        SortedLimitedList.Entry<Double> mustBeEqualEntry = mustBeEqualTo.getFirst();
        SortedLimitedList.Entry<Double> expectedEntry = expectedOutput.getFirst();
        SortedLimitedList.Entry<Double> helper;
        boolean returnToStart = false;

        while (mustBeEqualEntry != null && expectedEntry != null){
            int compareResult = mustBeEqualEntry.getValue().compareTo(expectedEntry.getValue());
            switch (compareResult){
                case 0:
                    mustBeEqualEntry = mustBeEqualEntry.getNext();
                    expectedEntry = expectedEntry.getNext();
                    break;
                case -1:
                    helper = mustBeEqualEntry;
                    mustBeEqualEntry = mustBeEqualEntry.getNext();
                    mustBeEqualTo.remove(helper);
                    break;
                case 1:
                    returnToStart = true;
                    while (expectedEntry != null &&
                            expectedEntry.getValue().compareTo(mustBeEqualEntry.getValue()) == -1){
                        expectedEntry = expectedEntry.getNext();
                    }
            }
        }

        if(expectedEntry == null){
            while (mustBeEqualEntry != null){
                helper = mustBeEqualEntry;
                mustBeEqualEntry = mustBeEqualEntry.getNext();
                mustBeEqualTo.remove(helper);
            }
        }

        if (returnToStart){
            mustBeEqualEntry = mustBeEqualTo.getFirst();
            expectedEntry = expectedOutput.getFirst();

            while (mustBeEqualEntry != null && expectedEntry != null){
                int compareResult = mustBeEqualEntry.getValue().compareTo(expectedEntry.getValue());
                switch (compareResult) {
                    case 0:
                        mustBeEqualEntry = mustBeEqualEntry.getNext();
                        expectedEntry = expectedEntry.getNext();
                        break;
                    case 1:
                        mustBeEqualTo.addBefore(mustBeEqualEntry, expectedEntry.getValue());
                        expectedEntry = expectedEntry.getNext();
                        break;
                }
            }
        }

        if (mustBeEqualEntry == null){
            while (expectedEntry != null){
                mustBeEqualTo.addLast(expectedEntry.getValue());
                expectedEntry = expectedEntry.getNext();
            }
        }
    }
}

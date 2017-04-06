package competitionscheduling;

import java.util.Arrays;

/**
 *
 * @author LuongDucTu
 * @student 01649122
 * @homework #3
 */
public class CompetitionScheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* 
         * contestants data in order of
         * [ID, swimming, biking, running]
         */
        int[][] contestants;
        contestants = new int[][]{{1, 8, 4, 10},
                                  {2, 6, 7, 11},
                                  {3, 7, 8, 12},
                                  {4, 5, 9, 10},
                                  {5, 9, 10, 9}};
        
        System.out.println("Total completion time: " + getShortestCompletionTime(contestants));   
    }
    
    public static int getShortestCompletionTime(int[][] contestants) {
        int totalSwim = 0;
        int total = 0;
        
        /* sorting data */
        Arrays.sort(contestants, (int[] o1, int[] o2) -> Integer.compare(o2[2] + o2[3], o1[2] + o1[3]));
        
        System.out.println("Sorted data:");
        for (int[] contestant : contestants) {
            System.out.println(Arrays.toString(contestant));
            totalSwim += contestant[1];
            int currentTotal = totalSwim + contestant[2] + contestant[3];
            if (currentTotal > total) {
                total = currentTotal;
            }
        }
        return total;
    }
}

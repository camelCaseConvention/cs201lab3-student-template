import java.util.Arrays;
import java.util.List;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     
    static int[] parse(String s) {
        return Arrays.stream(s.split("-"))
                     .mapToInt(Integer::parseInt)
                     .toArray();
    }

    static boolean sorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] <= a[i - 1]) return false;
        }
        return true;
    }

    // Reconstruct postorder using preorder and inorder
    static boolean buildPost(int[] pre, int pL, int pR,
                             int[] in, int iL, int iR,
                             int[] post, int[] index) {
        if (pR - pL != iR - iL) return false;
        if (pL == pR) return true;

        int root = pre[pL];
        int mid = Arrays.binarySearch(in, iL, iR, root);
        if (mid < 0) return false;

        int leftSize = mid - iL;

        if (!buildPost(pre, pL + 1, pL + 1 + leftSize,
                       in, iL, mid, post, index))
            return false;

        if (!buildPost(pre, pL + 1 + leftSize, pR,
                       in, mid + 1, iR, post, index))
            return false;

        post[index[0]++] = root;
        return true;
    }

    public static String verify(String traversal1,
                                String traversal2,
                                String traversal3) {
        String[] raw = {traversal1, traversal2, traversal3};
        int[][] t = new int[3][];

        try {
            for (int i = 0; i < 3; i++)
                t[i] = parse(raw[i]);
        } catch (NumberFormatException e) {
            return "Invalid traversals";
        }

        int n = t[0].length;
        if (t[1].length != n || t[2].length != n)
            return "Invalid traversals";

        // Try every possible assignment of traversal types
        for (int in = 0; in < 3; in++) {
            // Inorder of a BST must be sorted
            if (!sorted(t[in])) continue;

            for (int pre = 0; pre < 3; pre++) {
                if (pre == in) continue;

                int post = 3 - in - pre;

                int[] generated = new int[n];
                int[] index = {0};

                if (buildPost(t[pre], 0, n,
                              t[in], 0, n,
                              generated, index)
                    && Arrays.equals(generated, t[post])) {

                    String[] types = new String[3];
                    types[in] = "Inorder";
                    types[pre] = "Preorder";
                    types[post] = "Postorder";

                    return "Traversal 1 - " + types[0]
                         + ", Traversal 2 - " + types[1]
                         + ", Traversal 3 - " + types[2];
                }
            }
        }

        return "Invalid traversals";
    }
}

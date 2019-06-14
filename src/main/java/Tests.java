import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;
import java.util.TreeMap;

public class Tests {

  public static void main(String[] args) {
    TreeMap<String, Integer> map = new TreeMap<>();

    map.put("S", 1);
    map.put("E", 2);
    map.put("A", 3);
    map.put("R", 4);
    map.put("C", 5);
    map.put("H", 5);

    System.out.println(map);
  }

}


import java.util.Comparator;

/**
 * Words class stores the word and its count which is accessed by the
 * countWordFrequency method of the WordFrequencies class.
 */

public class Words implements Comparator<Words>, Comparable<Words> {
	private String word;
	private int count;
	private int previousCount;

	Words() {
	}

	Words(String n, int a, int b) {
		word = n;
		count = a;
		previousCount = b;

	}

	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	public int getPreviousCount() {
		return previousCount;
	}

	public void setWord(String word) {
		this.word = word;

	}

	public void setCount(int count) {
		this.count = count;

	}

	public void setPreviousCount(int previousCount) {
		this.previousCount = previousCount;
	}

	// compareTo method

	public int compareTo(Words d) {
		return (this.word).compareTo(d.word);
	}

	// compare method to sort the count

	public int compare(Words d, Words d1) {
		return d1.count - d.count; // descending order sort
	}

}

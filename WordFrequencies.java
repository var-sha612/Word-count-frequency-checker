

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class WordFrequencies {

	/**
	 * This is the main method which makes use of countWordFrequency method.
	 * 
	 * @param args
	 * @exception IOException
	 *                On input files error.
	 */

	public static void main(String[] args) {

		WordFrequencies test = new WordFrequencies();

		try {

			/**
			 * If the user does not specify at least one file to be processed,
			 * then terminate the program with an appropriate message.
			 **/

			if (args.length == 0) {
				System.err
						.println("File name not specified Please specify a valid file name");
				return;
			} else {

				test.countWordFrequency(args);

			}

		} // end try
		catch (IOException e) {

			e.printStackTrace();
		}// end catch

	} // end main

	/**
	 * This method is used to find the twenty most frequently used words across
	 * all files, where each word must appear at least once in each file. This
	 * is done based on the word count.
	 * 
	 * @param Filenames
	 *            Sequence of names of ASCII text files as command- line
	 *            arguments. This is the set of parameters to countWordFrequency
	 *            method.
	 */

	public void countWordFrequency(String[] fileNames) throws IOException {

		int i;

		Scanner fileReader = null;

		/**
		 * Data Structure used : Create a hash map, It is used to store
		 * combination of words and their count where String being the key and
		 * Words(word,count) being the value. It has a advantage over Hash table
		 * that is Hash maps are unsynchronized and permits nulls. Iterator that
		 * is used in hash map is a fail-fast iterator. When execution time
		 * efficiency is considered HashMaps are faster since they are not
		 * synchronized.
		 * */

		Map<String, Words> map = new java.util.HashMap<String, Words>();

		/**
		 * Reading the sequence of ASCII text files as command line arguments
		 * Initializing the fileArray
		 */

		File[] myfiles = new File[fileNames.length];

		for (i = 0; i < fileNames.length; i++) {
			myfiles[i] = new File(fileNames[i]);
		}

		/**
		 * To enable the condition "Each word must appear at least once in each
		 * file"
		 * */
		/**
		 * Step 1: Search for the smaller file based on the size and put into
		 * map the frequently used words of that file.
		 * */
		/**
		 * Step 2: Read the other files and increase the count of the words in
		 * the map every time its encountered while traversing the other files.
		 * */
		/**
		 * Step 3: Use a Entry set and Iterator to remove the word if it is not
		 * even present at-least once in all the input files.
		 * 
		 **/

		// Finding the smallest file
		File smallFile = myfiles[0];
		for (i = 0; i < fileNames.length; i++) {
			myfiles[i] = new File(fileNames[i]);
			if (myfiles[i].length() < smallFile.length()) {
				smallFile = myfiles[i];

			}// if condition

		}// for loop

		// Reads the content of the small file using Scanner

		fileReader = new Scanner(new FileInputStream(smallFile));

		// reading line by line from the small file

		while (fileReader.hasNextLine()) { // while starts

			String line = fileReader.nextLine();

			String word = null;

			/**
			 * Non words in the file are ignored That is a word starts with A-Z
			 * or a-z and continues till EOF.
			 */

			String[] wordsarr = line.split("[^a-zA-Z]+");

			for (int m = 0; m < wordsarr.length; m++) {

				/**
				 * Before processing a string Converts all of the characters to
				 * lower case.
				 */

				word = wordsarr[m].toLowerCase();

				/**
				 * map.containsKey : presence of another instance of the word in
				 * the map is tested if true the count is increased.
				 */

				if (map.containsKey(word)) {

					Words myWord = map.get(word);
					int currentCount = myWord.getCount();
					int mypreviousCount = myWord.getPreviousCount();

					myWord.setCount(currentCount + 1);
					myWord.setPreviousCount(mypreviousCount + 1);

				}// end if
				else {
					/**
					 * Now check for the length of the word, if the length is
					 * six or more and less than fifty characters then put the
					 * word to the map.
					 * */

					if ((word.length() >= 6) && (word.length() < 50)) {

						Words myWord = new Words();
						myWord.setWord(word);
						myWord.setCount(1);
						myWord.setPreviousCount(1);
						map.put(word, myWord);

					} // end if

				}// end else

			} // end for

		} // end while

		/**
		 * for loop designed to read other files
		 */

		for (i = 0; i < fileNames.length; i++) {

			/**
			 * Compares file names if the name does not match with the small
			 * file name then it proceeds.
			 */

			if (!myfiles[i].getName().equals(smallFile.getName()))

			{

				fileReader = new Scanner(new FileInputStream(myfiles[i]));

				// Read line by line

				while (fileReader.hasNextLine()) { // while starts

					String line = fileReader.nextLine();

					String word = null;

					String[] wordsarr = line.split("[^a-zA-Z]+");

					for (int l = 0; l < wordsarr.length; l++) {

						word = wordsarr[l].toLowerCase();

						if (map.containsKey(word)) {

							Words myWord = map.get(word);
							int currentCount = myWord.getCount();
							myWord.setCount(currentCount + 1);

						}// end if

					} // end for

				} // end while

				/**
				 * Provides a set view of the mappings contained in the map. any
				 * changes to the map are reflected in the set, and vice-versa.
				 */
				Set<Entry<String, Words>> set = map.entrySet();

				/**
				 * Iterator is used to traverse the map elements in the entry
				 * set. Iterators allow the caller to remove elements from the
				 * underlying collection during the iteration.
				 */

				for (Iterator<Map.Entry<String, Words>> it = set.iterator(); it
						.hasNext();) {

					// To iterate the next element in the EntrySet

					Entry<String, Words> entry = it.next();

					Words mapEntryWord = entry.getValue();

					int currentCount = mapEntryWord.getCount();
					int mypreviousCount = mapEntryWord.getPreviousCount();

					if (currentCount > mypreviousCount) {
						mypreviousCount = currentCount;
						mapEntryWord.setPreviousCount(mypreviousCount);
					}

					else if (currentCount == mypreviousCount) {
						it.remove();
					} // end else

				} // end for

			}// end getNames if

		}// end for

		/**
		 * Provides a Collection view of the values contained in this map. The
		 * collection is backed by the map, so changes to the map are reflected
		 * in the collection and vice-versa.
		 */

		Collection<Words> wordsCollection = map.values();

		/**
		 * Provides a sequence and control over where in the list each element
		 * is inserted. The user can access elements by their integer index and
		 * search for elements in the list.
		 */

		List<Words> newList = new ArrayList<Words>();

		for (Words wd : wordsCollection) {

			newList.add(wd);

		}

		/**
		 * Sorts the given list according to the order of the specified
		 * comparator.
		 */

		Collections.sort(newList, new Words());

		int j = 0;

		/**
		 * To print 20 most frequently present words
		 */
		int k;
		int listSize = newList.size() >= 20 ? 20 : newList.size();

		for (k = 0; k < listSize; k++) {

			Words w = newList.get(k);

			System.out.println("Word : " + ++j + " " + w.getWord() + " "
					+ w.getCount());

		}
		if (k > 0) {

			/**
			 * To report ties at the last position.
			 */

			int lastCount = newList.get(k - 1).getCount(); // last printed count

			// print words from the k-th

			while (k < newList.size() && newList.get(k).getCount() == lastCount) {

				Words w = newList.get(k);

				System.out.println("Word : " + ++j + " " + w.getWord() + " "
						+ w.getCount());

				++k;
			}
		}

		fileReader.close();

	} // end countWordFrequency method

}// end WordFrequencies class
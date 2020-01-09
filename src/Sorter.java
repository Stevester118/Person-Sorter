import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

//Sorter class where all of the user input and sorting occurs
public class Sorter {

	// Main method, Allows the user to enter a file name, then checks which list
	// type the ASCII file is, then calls the
	// correct format method.
	public static void main(String[] args) {
		String[] listContents = null;
		Scanner scan = new Scanner(System.in);

		boolean again = true;
		while (again) {
			again = false;
			System.out.println("Enter file path");
			String input = scan.next();
			try {
				String contentGrab = new String(Files.readAllBytes(Paths.get(input)));
				if (contentGrab.charAt(0) == '1') {
					contentGrab = contentGrab.substring(3);
					contentGrab = contentGrab.replace("\n", "").replace("\r", "");
					ArrayList<String> choppedList = new ArrayList<String>();
					for (int i = 0; i < (contentGrab.length() / 80); i++) {
						choppedList.add(contentGrab.substring(i * 80, (1 + i) * 80));
					}
					format1(choppedList);
				} else if (contentGrab.charAt(0) == '2') {
					contentGrab = contentGrab.substring(3);
					contentGrab = contentGrab.replace("\n", ",").replace("\r", "");
					listContents = contentGrab.split(",");
					format2(listContents);
				}
			} catch (IOException e) {
				again = true;
				System.out.println("File not found, enter a correct file path");
			}
		}
		scan.close();
	}

	// Creates an ArrayList of people based on list type 1.
	private static void format1(ArrayList<String> l) {
		ArrayList<String> listContents = l;
		ArrayList<Person> personList = new ArrayList<Person>();
		for (int i = 0; i < listContents.size(); i++) {
			Person person = new Person();

			person.setFirstName(listContents.get(i).substring(0, 10).replaceFirst("\\s++$", ""));
			person.setLastName(listContents.get(i).substring(10, 27).replaceFirst("\\s++$", ""));
			person.setStartDate(listContents.get(i).substring(27, 35).replaceFirst("\\s++$", ""));
			person.setAddress(listContents.get(i).substring(35, 45).replaceFirst("\\s++$", ""));
			person.setAptNum(listContents.get(i).substring(45, 55).replaceFirst("\\s++$", ""));
			person.setCity(listContents.get(i).substring(55, 65).replaceFirst("\\s++$", ""));
			person.setState(listContents.get(i).substring(65, 67).replaceFirst("\\s++$", ""));
			person.setCountry(listContents.get(i).substring(67, 70).replaceFirst("\\s++$", ""));
			person.setZipCode(listContents.get(i).substring(70, 80).replaceFirst("\\s++$", ""));

			personList.add(person);
		}
		selectSortingOption(personList);
	}

	// Creates an ArrayList of people based on list type 2.
	private static void format2(String[] l) {
		String[] listContents = l;
		ArrayList<Person> personList = new ArrayList<Person>();

		for (int i = 0; i < (listContents.length / 9); i++) {
			Person person = new Person();

			person.setFirstName(listContents[i * 9]);
			person.setLastName(listContents[1 + i * 9]);
			person.setStartDate(listContents[2 + i * 9]);
			person.setAddress(listContents[3 + i * 9]);
			person.setAptNum(listContents[4 + i * 9]);
			person.setCity(listContents[5 + i * 9]);
			person.setState(listContents[6 + i * 9]);
			person.setCountry(listContents[7 + i * 9]);
			person.setZipCode(listContents[8 + i * 9]);

			personList.add(person);
		}
		selectSortingOption(personList);
	}

	// Allows the user to select which element the list is sorted by.
	private static void selectSortingOption(ArrayList<Person> p) {
		ArrayList<Person> personList = p;
		Scanner scan = new Scanner(System.in);

		boolean again = true;
		while (again) {
			again = false;
			System.out
					.println("Pick your sorting option\n" + "1: First Name\n" + "2: Last Name\n" + "3: Starting Date");

			int input = scan.nextInt();

			switch (input) {
			case 1:
				Collections.sort(personList, new Comparator<Person>() {
					@Override
					public int compare(final Person object1, final Person object2) {
						return object1.getFirstName().toUpperCase().compareTo(object2.getFirstName().toUpperCase());
					}
				});
				sortingOptions(personList);
				break;
			case 2:
				Collections.sort(personList, new Comparator<Person>() {
					@Override
					public int compare(final Person object1, final Person object2) {
						return object1.getLastName().toUpperCase().compareTo(object2.getLastName().toUpperCase());
					}
				});
				sortingOptions(personList);
				break;
			case 3:
				Collections.sort(personList, new Comparator<Person>() {
					DateFormat f = new SimpleDateFormat("yyyyMMdd");

					@Override
					public int compare(Person o1, Person o2) {
						try {
							return f.parse(o1.getStartDate()).compareTo(f.parse(o2.getStartDate()));
						} catch (ParseException e) {
							throw new IllegalArgumentException(e);
						}
					}
				});
				sortingOptions(personList);
				break;
			default:
				again = true;
				System.out.println("Enter a number between 1 and 3");
				break;
			}
		}
		scan.close();
	}

	// Allows the user to sort by ascending or descending order.
	private static void sortingOptions(ArrayList<Person> p) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Person> personList = p;
		boolean again = true;
		while (again) {
			again = false;
			System.out.println("Ascending or descending?\n" + "1: Ascending\n" + "2: Descending");
			int input = scan.nextInt();

			switch (input) {
			case 1:
				printDataFormat(personList);
				break;
			case 2:
				Collections.reverse(personList);
				printDataFormat(personList);
				break;
			default:
				again = true;
				System.out.println("Enter either 1 or 2");
			}
		}
		scan.close();
	}

	// Allows the user to choose a file path, then converts dates into English
	// format, then prints the person into a list of
	// people in the ASCII file.
	private static void printDataFormat(ArrayList<Person> p) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter path for output file");
		String input = scan.next();
		boolean again = true;
		while (again) {
			again = false;
			try (PrintWriter out = new PrintWriter(input, "ASCII")) {
				for (int i = 0; i < p.size(); i++) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
					DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
					LocalDate ld = LocalDate.parse(p.get(i).getStartDate(), dtf);
					String month_name = dtf2.format(ld);

					out.println(i + 1 + "\n" + p.get(i).getFirstName() + " " + p.get(i).getLastName() + ", "
							+ month_name + "\n" + p.get(i).getAddress() + ", " + p.get(i).getAptNum() + ",\n"
							+ p.get(i).getCity() + ", " + p.get(i).getState() + "\n" + p.get(i).getCountry() + ", "
							+ p.get(i).getZipCode());
				}
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				again = true;
				System.out.println("Could not create file, enter a correct file path");
			}
		}
		scan.close();
	}
}
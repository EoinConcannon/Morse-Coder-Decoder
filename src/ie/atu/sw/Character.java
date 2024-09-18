package ie.atu.sw;

public record Character(String name, int page) implements Comparable<Character>{

	public int compareTo(Character c) {
		return Integer.compare(page, c.page());
	}

	public String toString() {
		return this.name + " [" + this.page + "]";
	}
}
package fr.raksrinana.utils.base;

public class NumberUtils{
	/**
	 * Perform a modulo that works on negative numbers.
	 *
	 * @param number The number.
	 * @param base   The base of the modulo.
	 *
	 * @return The modulo.
	 */
	private static int mod(int number, int base){
		if(number >= 0){
			return number % base;
		}
		return base + number % base;
	}
}
